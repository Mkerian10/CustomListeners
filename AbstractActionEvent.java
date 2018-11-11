package com.stai.concurrency;

/**
 * This class contains information about the event being sent. For example change in health or inventory space
 * @param <K> Type of the source
 */
public abstract class AbstractActionEvent<K>{
	
	public AbstractActionEvent(K source){
		this.source = source;
	}
	
	protected final K source;
}
