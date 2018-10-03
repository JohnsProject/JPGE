package com.johnsproject.jpge.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The EventDispatcher class is used to dispatch update events.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class EventDispatcher {
	
	private static EventDispatcher instance;
	
	private List<UpdateListener> updateListeners = Collections.synchronizedList(new ArrayList<UpdateListener>());
	
	/**
	 * Returns a instance of the EventDispatcher class.
	 * 
	 * @return instance of the EventDispatcher class.
	 */
	public static EventDispatcher getInstance() {
		if (instance == null) {
			instance = new EventDispatcher();
		}
		return instance;
	}
	
	/**
	 * Adds the given {@link UpdateListener} to the list of {@link UpdateListener UpdateListeners} 
	 * that are called when dispatch method is called.
	 * 
	 * @param listener listener to add.
	 */
	public void addUpdateListener(UpdateListener listener) {
		synchronized (updateListeners) {
			updateListeners.add(listener);
		}
	}
	
	/**
	 * Tells this EventDispatcher to dispatch the given update event
	 * to all its {@link UpdateListener UpdateListeners}.
	 * 
	 * @param event event to dispatch.
	 */
	public void dispatchUpdateEvent(UpdateEvent event) {
		synchronized (updateListeners) {
			for (int i = 0; i < updateListeners.size(); i++) {
				UpdateListener updateListener = updateListeners.get(i);
				updateListener.update(event);
			}
		}
	}
}
