package com.johnsproject.jpge.event;

import com.johnsproject.jpge.dto.Scene;

/**
 * The UpdateEvent class is used to tell the engine that 
 * it is time to update graphics, input or physics.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class UpdateEvent {
	
	private int time = 0;
	private int type = 0;
	private Scene scene;
	
	/**
	 *  Creates a new instance of the UpdateEvent class filled with the given values.
	 * 
	 * @param elapsedTime last elapsed update time.
	 * @param scene currently used {@link Scene}.
	 * @param eventType type of this event.
	 */
	public UpdateEvent(int elapsedTime, Scene scene, int eventType) {
		this.time = elapsedTime;
		this.scene = scene;
		this.type = eventType;
	}
	
	/**
	 * Returns the last elapsed update time.
	 * 
	 * @return last elapsed update time.
	 */
	public int getElapsedTime() {
		return time;
	}
	
	/**
	 * Returns the scene of this event.
	 * 
	 * @return scene of this event.
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Returns the type of this event.
	 * 
	 * @return type of this event.
	 */
	public int getUpdateType() {
		return type;
	}
}
