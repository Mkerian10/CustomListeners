package com.stai.concurrency.item_listener;

import com.stai.concurrency.AbstractActionEvent;
import org.powerbot.script.rt4.Item;

public class ItemGainedEvent extends AbstractActionEvent<ItemListenerRunnable>{
	
	public ItemGainedEvent(ItemListenerRunnable source, int startQuantity, int endQuantity, int gain, int id){
		super(source);
		this.startQuantity = startQuantity;
		this.endQuantity = endQuantity;
		this.gain = gain;
		this.id = id;
	}
	
	private final int startQuantity;
	
	private final int endQuantity;
	
	private final int gain;
	
	private final int id;
	
	public int getStartQuantity(){
		return startQuantity;
	}
	
	public int getEndQuantity(){
		return endQuantity;
	}
	
	public int getGain(){
		return gain;
	}
	
	public int getId(){
		return id;
	}
	
	@Override
	public String toString(){
		return "Item: " + id + " Quant: " + getStartQuantity() + "->" + getEndQuantity() + " Delta: " + getGain();
	}
}
