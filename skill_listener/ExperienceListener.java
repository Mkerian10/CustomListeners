package com.stai.concurrency.skill_listener;

import java.util.EventListener;

public interface ExperienceListener extends EventListener{
	void onExperienceChange(ExperienceChangeEvent e);
}
