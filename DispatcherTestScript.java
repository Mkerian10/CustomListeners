package com.stai.concurrency;

import com.stai.concurrency.item_listener.ItemGainedEvent;
import com.stai.concurrency.item_listener.ItemListener;
import com.stai.concurrency.item_listener.ItemListenerRunnable;
import com.stai.concurrency.item_listener.ItemLostEvent;
import com.stai.concurrency.listeners.inventory_listener.InventoryEvent;
import com.stai.concurrency.listeners.inventory_listener.InventoryListener;
import com.stai.concurrency.player_animation.LocalAnimationEvent;
import com.stai.concurrency.player_animation.LocalAnimationListener;
import com.stai.concurrency.player_animation.LocalAnimationRunnable;
import com.stai.concurrency.skill_listener.ExperienceChangeEvent;
import com.stai.concurrency.skill_listener.LevelChangeEvent;
import com.stai.concurrency.skill_listener.SkillListener;
import com.stai.concurrency.skill_listener.SkillRunnable;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.TimeUnit;

@Script.Manifest(name = "DispatcherTest", description = "Tests listeners and dispatcher", properties = "client: 4; hidden=true;")
public class DispatcherTestScript extends PollingScript<ClientContext> implements LocalAnimationListener, SkillListener, ItemListener{
	
	@Override
	public void start(){
		super.start();
		Dispatcher d = Dispatcher.load(ctx);
		d.addListener(this);
		d.offer(new LocalAnimationRunnable(d, ctx), 500, TimeUnit.MILLISECONDS);
		d.offer(new SkillRunnable(d, ctx), 500, TimeUnit.MILLISECONDS);
		d.offer(new ItemListenerRunnable(d, ctx), 250, TimeUnit.MILLISECONDS);
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
	
	@Override
	public void onExperienceChange(ExperienceChangeEvent e){
		System.out.println(e.toString());
	}
	
	@Override
	public void onLevelUp(LevelChangeEvent e){
		System.out.println(e.toString());
	}
	
	@Override
	public void onItemGained(ItemGainedEvent e){
		System.out.println(e.toString());
	}
	
	@Override
	public void onItemLost(ItemLostEvent e){
		System.out.println(e.toString());
	}
}
