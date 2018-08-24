package com.johnsproject.jpge.event;

public class UpdateEvent {
	
	private int time = 0;
	private UpdateType type;
	
	public enum UpdateType{
		graphics,
		input,
		physics
	}
	
	public UpdateEvent(int elapsedTime, UpdateType eventType) {
		this.time = elapsedTime;
		this.type = eventType;
	}
	
	public int getElapsedTime() {
		return time;
	}
	
	public UpdateType getUpdateType() {
		return type;
	}
}
