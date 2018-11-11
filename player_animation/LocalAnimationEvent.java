package com.stai.concurrency.player_animation;

import com.stai.concurrency.AbstractActionEvent;

public class LocalAnimationEvent extends AbstractActionEvent<LocalAnimationRunnable>{
	
	public LocalAnimationEvent(LocalAnimationRunnable source, int previous, int current){
		super(source);
		this.previous = previous;
		this.current = current;
	}
	
	private final int previous;
	
	private final int current;
	
	public int getPrevious(){
		return previous;
	}
	
	public int getCurrent(){
		return current;
	}
	
	@Override
	public String toString(){
		return "Local animation change: " + previous + "->" + current;
	}
}
