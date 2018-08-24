package com.johnsproject.jpge.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventDispatcher {
	
	private static EventDispatcher instance;
	
	private List<UpdateListener> updateListeners = Collections.synchronizedList(new ArrayList<UpdateListener>());
	
	public static EventDispatcher getInstance() {
		if (instance == null) {
			instance = new EventDispatcher();
		}
		return instance;
	}
	
	public void addUpdateListener(UpdateListener listener) {
		synchronized (updateListeners) {
			updateListeners.add(listener);
		}
	}
	
	public void dispatchUpdateEvent(UpdateEvent event) {
		synchronized (updateListeners) {
			for (UpdateListener updateListener : updateListeners) {
				updateListener.update(event);
			}
		}
	}
}
