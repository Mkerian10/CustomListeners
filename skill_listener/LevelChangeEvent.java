package com.stai.concurrency.skill_listener;

import com.stai.concurrency.AbstractActionEvent;

public class LevelChangeEvent extends AbstractActionEvent<SkillRunnable>{
	
	public LevelChangeEvent(SkillRunnable source, int initialLevel, int endLevel, int skill){
		super(source);
		this.initialLevel = initialLevel;
		this.endLevel = endLevel;
		this.skill = skill;
	}
	
	private final int initialLevel;
	
	private final int endLevel;
	
	private final int skill;
	
	public int getInitialLevel(){
		return initialLevel;
	}
	
	public int getEndLevel(){
		return endLevel;
	}
	
	public int getSkill(){
		return skill;
	}
	
	@Override
	public String toString(){
		return "Skill: " + getSkill() + " Level: " + getInitialLevel() + "->" + getEndLevel();
	}
}
