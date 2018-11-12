package com.stai.concurrency.skill_listener;

import java.util.EventListener;

public interface LevelUpListener extends EventListener{
	void onLevelUp(LevelChangeEvent e);
}
