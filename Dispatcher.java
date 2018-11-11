package com.stai.concurrency;

import com.stai.concurrency.player_animation.LocalAnimationEvent;
import com.stai.concurrency.player_animation.LocalAnimationListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is the dispatcher for any listener you may make. It's a singleton to make it easier on the user to prevent
 * confusion between multiple dispatchers. In order to add a listener to the Dispatcher simply use the <code>addListener</code>
 * method. Adding a runnable is done using the <code>offer</code> method.
 */
public class Dispatcher{
	
	/**
	 * Status flag for the dispatcher and all of the runnables, you can pause or terminate the runnables using this flag
	 */
	public volatile STATUS STATUS_FLAG = STATUS.UNKNOWN;
	
	private Dispatcher(int nThreads){
		executor = Executors.newScheduledThreadPool(nThreads);
		this.STATUS_FLAG = STATUS.RUNNING;
		offer(checkStatus(), 3, TimeUnit.SECONDS);
	}
	
	/**
	 * Use this method to get the dispatcher
	 *
	 * @return Dispatcher object
	 */
	public static Dispatcher load(){
		if(singleton == null){
			singleton = new Dispatcher(0);
		}
		return singleton;
	}
	
	/**
	 * Use this method to get the dispatcher with a set number of threads
	 *
	 * @param nThreads Amount of threads to use in the core thread pool
	 * @return Dispatcher object
	 */
	public static Dispatcher load(int nThreads){
		if(singleton == null){
			singleton = new Dispatcher(nThreads);
		}
		return singleton;
	}
	
	
	//Singleton Dispatcher object
	private static Dispatcher singleton;
	
	/**
	 * List of listeners given to this script, when events are fired they're dispersed to these
	 */
	private final List<EventListener> listeners = new ArrayList<>();
	
	/**
	 * Service that runnables are offered to
	 */
	private final ScheduledExecutorService executor;
	
	/**
	 * Adds a new runnable to the schedule. Do not add more than one of the same Runnable, simply modify that runnable
	 * to account for the expanded scope.
	 *
	 * @param r Runnable given, e.g. LocalAnimationRunnable or descendant of AbstractRunnable
	 * @param waitTime Time to wait in between running the runnable
	 * @param unit Unit of time the waitTime is given in
	 */
	public void offer(Runnable r, long waitTime, TimeUnit unit){
		executor.scheduleAtFixedRate(r, 0, waitTime, unit);
	}
	
	/**
	 * This function is fired by the runnables whenever an event is triggered, it iterates through the runnables and
	 * disperses the event to the needed listeners
	 *
	 * @param event The action event given from the runnable
	 */
	public void fireEvent(AbstractActionEvent<?> event){
		synchronized(listeners){
			for(EventListener l : listeners){
				if(event instanceof LocalAnimationEvent && l instanceof LocalAnimationListener){
					((LocalAnimationListener) l).onLocalAnimationChange((LocalAnimationEvent)event);
				}
			}
		}
	}
	
	/**
	 * Adds a new listener to the pool of listeners
	 * @param listener Listener given, descendant of EventListener
	 */
	public void addListener(EventListener listener){
		synchronized(listeners){
			listeners.add(listener);
		}
	}
	
	/**
	 * Removes a listener from the pool of listeners
	 * @param listener Listener to remove
	 */
	public void removeListener(EventListener listener){
		synchronized(listeners){
			listeners.remove(listener);
		}
	}
	
	/**
	 * The amount of listeners in the pool
	 * @return Amount of listeners in the pool
	 */
	public int listeners(){
		return listeners.size();
	}
	
	/**
	 * Checks the status on the Dispatcher, shutting it down if the flag is set to terminated, or blocking the queue if
	 * set to paused. This method can be overrid ro provide better support for automatic pausing/terminating
	 *
	 * @return Runnable checking status of Dispatcher and subsequent runnables
	 */
	protected final Runnable checkStatus(){
		return () -> {
			if(Dispatcher.this.STATUS_FLAG.equals(STATUS.TERMINATED)){
				Dispatcher.this.executor.shutdown();
			}else if(Dispatcher.this.STATUS_FLAG.equals(STATUS.PAUSED)){
				while(Dispatcher.this.STATUS_FLAG.equals(STATUS.PAUSED)){
					try{
						Thread.sleep(500);
					}catch(InterruptedException ignored){
					}
				}
			}
		};
	}
	
	public enum STATUS{
		RUNNING,
		PAUSED,
		TERMINATED,
		UNKNOWN
	}
}
