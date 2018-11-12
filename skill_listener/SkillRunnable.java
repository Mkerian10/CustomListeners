package com.stai.concurrency.skill_listener;

import com.stai.concurrency.AbstractRunnable;
import com.stai.concurrency.Dispatcher;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import java.util.HashMap;
import java.util.Map;

public class SkillRunnable extends AbstractRunnable{
	
	private final static int SKILLS_AMOUNT = 23;
	
	public SkillRunnable(Dispatcher dispatcher, ClientContext ctx){
		super(dispatcher);
		this.ctx = ctx;
		levelMap = new HashMap<>(SKILLS_AMOUNT);
		experienceMap = new HashMap<>(SKILLS_AMOUNT);
		for(int i = 0; i < SKILLS_AMOUNT; i++){
			levelMap.put(i, ctx.skills.realLevel(i));
			experienceMap.put(i, ctx.skills.experience(i));
		}
	}
	
	private final ClientContext ctx;
	
	private Map<Integer, Integer> levelMap;
	
	private Map<Integer, Integer> experienceMap;
	
	@Override
	public void run(){
		for(int i = 0; i < SKILLS_AMOUNT; i++){
			int level = ctx.skills.realLevel(i);
			int exp = ctx.skills.experience(i);
			if(levelMap.get(i) != level){
				fireEvent(new LevelChangeEvent(this, levelMap.get(i), level, i));
				levelMap.replace(i, level);
			}
			if(experienceMap.get(i) != exp){
				fireEvent(new ExperienceChangeEvent(this, experienceMap.get(i), exp, i));
				experienceMap.replace(i, exp);
			}
		}
	}
	
	@Override
	public int hashCode(){
		return 11;
	}
}
