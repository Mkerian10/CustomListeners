package com.stai.concurrency.player_animation;

import java.util.EventListener;

public interface LocalAnimationListener extends EventListener{
	void onLocalAnimationChange(LocalAnimationEvent e);
}
