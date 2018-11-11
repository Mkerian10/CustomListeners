package com.stai.concurrency.player_animation;

import com.stai.concurrency.AbstractRunnable;
import com.stai.concurrency.Dispatcher;
import org.powerbot.script.rt4.ClientContext;

public class LocalAnimationRunnable extends AbstractRunnable{
	
	public LocalAnimationRunnable(Dispatcher dispatcher, ClientContext ctx){
		super(dispatcher);
		this.ctx = ctx;
	}
	
	private final ClientContext ctx;
	
	private int last = -1;
	
	@Override
	public void run(){
		int animation = ctx.players.local().animation();
		if(animation != last){
			fireEvent(new LocalAnimationEvent(this, last, animation));
			last = animation;
		}
	}
}
