package com.stai.concurrency.item_listener;

import java.util.EventListener;

public interface ItemListener extends EventListener{
	void onItemGained(ItemGainedEvent e);
	void onItemLost(ItemLostEvent e);
}
