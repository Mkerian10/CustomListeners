package com.stai.concurrency;

import com.stai.concurrency.player_animation.LocalAnimationEvent;
import com.stai.concurrency.player_animation.LocalAnimationListener;
import com.stai.concurrency.player_animation.LocalAnimationRunnable;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.TimeUnit;

@Script.Manifest(name = "DispatcherTest", description = "Tests listeners and dispatcher", properties = "client: 4; hidden=true;")
public class DispatcherTestScript extends PollingScript<ClientContext> implements LocalAnimationListener{
	
	@Override
	public void start(){
		super.start();
		Dispatcher.load().addListener(this);
		Dispatcher.load().offer(new LocalAnimationRunnable(Dispatcher.load(), ctx), 500, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void poll(){
		try{
			Thread.sleep(10000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onLocalAnimationChange(LocalAnimationEvent e){
		System.out.println(e.toString());
	}
}
