package com.johnsproject.jpge.io;

public class JPGEKeyEvent {
	
	private char key;
	private int keyCode;
	
	public JPGEKeyEvent(char key, int keyCode) {
		this.key = key;
		this.keyCode = keyCode;
	}

	public char getKey() {
		return key;
	}

	public int getKeyCode() {
		return keyCode;
	}
	
	void setKey(char key) {
		this.key = key;
	}

	void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public String toString() {
		return "KeyEvent [key=" + key + ", keyCode=" + keyCode + "]";
	}	
}
