package com.johnsproject.jpge.io;

public class KeyEvent {
	
	private char key;
	private int keyCode;
	
	public KeyEvent(char key, int keyCode) {
		this.key = key;
		this.keyCode = keyCode;
	}

	public char getKey() {
		return key;
	}

	public int getKeyCode() {
		return keyCode;
	}

	
	public KeyEvent clone() {
		return new KeyEvent(key, keyCode);
	}
	
	@Override
	public String toString() {
		return "KeyEvent [key=" + key + ", keyCode=" + keyCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + key;
		result = prime * result + keyCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyEvent other = (KeyEvent) obj;
		if (key != other.key)
			return false;
		if (keyCode != other.keyCode)
			return false;
		return true;
	}
	
}
