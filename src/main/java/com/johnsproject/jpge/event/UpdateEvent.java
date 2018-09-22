package com.johnsproject.jpge.event;

/**
 * The UpdateEvent class is used to tell the engine that 
 * it is time to update graphics, input or physics.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class UpdateEvent {
	
	private int time = 0;
	private UpdateType type;
	
	public enum UpdateType{
		graphics,
		input,
		physics
	}
	
	/**
	 *  Creates a new instance of the UpdateEvent class filled with the given values.
	 * 
	 * @param elapsedTime last elapsed update time.
	 * @param eventType type of this event.
	 */
	public UpdateEvent(int elapsedTime, UpdateType eventType) {
		this.time = elapsedTime;
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
	 * Returns the type of this event.
	 * 
	 * @return type of this event.
	 */
	public UpdateType getUpdateType() {
		return type;
	}
}
