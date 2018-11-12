package com.stai.concurrency.item_listener;

import com.scripts.zulrah.ZulrahStatics;
import com.stai.concurrency.AbstractRunnable;
import com.stai.concurrency.Dispatcher;
import org.powerbot.script.rt4.CacheItemConfig;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.ItemQuery;

import java.util.*;

public class ItemListenerRunnable extends AbstractRunnable{
	
	public ItemListenerRunnable(Dispatcher dispatcher, ClientContext ctx){
		super(dispatcher);
		this.ctx = ctx;
		itemMap = new HashMap<>();
		ItemQuery<Item> query = ctx.inventory.select();
		for(Item i: query){
			itemMap.put(i.id(), itemQuantity(query, i.id()));
		}
	}
	
	private final ClientContext ctx;
	
	private Map<Integer, Integer> itemMap; //mapping id to quantity
	
	@Override
	public void run(){
		Set<Integer> set = new HashSet<>(itemMap.keySet());
		ItemQuery<Item> query = ctx.inventory.select();
		for(Item i: query){
			int quant = itemQuantity(query, i.id());
			if(itemMap.containsKey(i.id())){
				if(quant != itemMap.get(i.id())){
					if(quant > itemMap.get(i.id())){
						fireEvent(new ItemGainedEvent(this, itemMap.get(i.id()), quant, quant - itemMap.get(i.id()), i.id()));
						itemMap.replace(i.id(), quant);
					}else{
						fireEvent(new ItemLostEvent(this, itemMap.get(i.id()), quant, itemMap.get(i.id()) - quant, i.id()));
						if(quant == 0){
							itemMap.remove(i.id());
						}else{
							itemMap.replace(i.id(), quant);
						}
					}
				}
				set.remove(i.id());
			}else{
				fireEvent(new ItemGainedEvent(this, 0, quant, quant, i.id()));
				itemMap.put(i.id(), itemQuantity(query, i.id()));
			}
		}
		for(int i: set){
			fireEvent(new ItemLostEvent(this, itemMap.get(i), 0, itemMap.get(i), i));
			itemMap.remove(i);
		}
	}
	
	@Override
	public int hashCode(){
		return 13;
	}
	
	private int itemQuantity(ItemQuery<Item> query, int i){
		boolean stackable = CacheItemConfig.load(i).stackable;
		if(stackable){
			return query.stream().filter(item -> item.id() == i).findFirst().map(Item::stackSize).orElse(0);
		}else{
			return (int)query.stream().filter(item -> item.id() == i).count();
		}
	}
}
