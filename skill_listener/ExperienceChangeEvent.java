package com.stai.concurrency.skill_listener;

import com.stai.concurrency.AbstractActionEvent;

public class ExperienceChangeEvent extends AbstractActionEvent<SkillRunnable>{
	
	public ExperienceChangeEvent(SkillRunnable source, int startExperience, int endExperience, int skill){
		super(source);
		this.startExperience = startExperience;
		this.endExperience = endExperience;
		this.skill = skill;
	}
	
	private final int startExperience;
	
	private final int endExperience;
	
	private final int skill;
	
	public int getStartExperience(){
		return startExperience;
	}
	
	public int getEndExperience(){
		return endExperience;
	}
	
	public int getSkill(){
		return skill;
	}
	
	@Override
	public String toString(){
		return "Skill: " + getSkill() + " Exp: " + getStartExperience() + "->" + getEndExperience();
	}
}
