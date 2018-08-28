package com.johnsproject.jpge.io;

public class JPGEMouseEvent {

	private int position;
	
	public JPGEMouseEvent(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "JPGEMouseEvent [position=" + position + "]";
	}
}
