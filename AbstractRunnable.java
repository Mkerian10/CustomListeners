package com.stai.concurrency;

/**
 * Provides a template for the runnables offered to the dispatcher
 * <p>
 * These runnables SHOULD NOT loop. The ScheduledExecutor will handle keeping it running
 */
public abstract class AbstractRunnable implements Runnable{
	
	public AbstractRunnable(Dispatcher dispatcher){
		this.dispatcher = dispatcher;
	}
	
	protected final Dispatcher dispatcher;
	
	protected void fireEvent(AbstractActionEvent<?> event){
		dispatcher.fireEvent(event);
	}
}
