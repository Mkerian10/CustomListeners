package com.stai.concurrency.item_listener;

import com.stai.concurrency.AbstractActionEvent;

public class ItemLostEvent extends AbstractActionEvent<ItemListenerRunnable>{
	
	public ItemLostEvent(ItemListenerRunnable source, int startQuantity, int endQuantity, int loss, int id){
		super(source);
		this.startQuantity = startQuantity;
		this.endQuantity = endQuantity;
		this.loss = loss;
		this.id = id;
	}
	
	private final int startQuantity;
	
	private final int endQuantity;
	
	private final int loss;
	
	private final int id;
	
	public int getStartQuantity(){
		return startQuantity;
	}
	
	public int getEndQuantity(){
		return endQuantity;
	}
	
	public int getLoss(){
		return loss;
	}
	
	public int getId(){
		return id;
	}
	
	@Override
	public String toString(){
		return "Item: " + id + " Quant: " + getStartQuantity() + "->" + getEndQuantity() + " Delta: -" + getLoss();
	}
}
