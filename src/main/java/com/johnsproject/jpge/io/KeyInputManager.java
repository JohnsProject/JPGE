package com.johnsproject.jpge.io;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class KeyInputManager {
	
	private int[] keysBuffer = new int[8];
	
	public KeyInputManager() {
		for (int i = 0; i < keysBuffer.length; i++) {
			keysBuffer[i] = -1;
		}
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				switch (e.getID()) {
				case KeyEvent.KEY_PRESSED:
					for (int i = 0; i < keysBuffer.length; i++) {
						if (keysBuffer[i] == e.getKeyCode())
							break;
						if (keysBuffer[i] == -1) {
							keysBuffer[i] = e.getKeyCode();
							break;
						}
					}
					break;

				case KeyEvent.KEY_RELEASED:
					for (int i = 0; i < keysBuffer.length; i++) {
						if (keysBuffer[i] == e.getKeyCode()) {
							keysBuffer[i] = -1;
						}
					}
					break;
				}
				return false;
			}
		});
	}
	
	/**
	 * Returns all keys captured since last time reset was called.
	 * 
	 * @return all keys captured since last time reset was called.
	 */
	public int[] getKeys() {
		return keysBuffer;
	}
	
	/**
	 * Returns if a key with the given id is found it returns true else false.
	 * The ID of Keys are in the {@link KeyEvent} class. 
	 * 
	 * @param id
	 * @return if a key with the given id is found it returns true else false.
	 */
	public boolean getKey(int id) {
		for (int i = 0; i < keysBuffer.length; i++) {
			if(keysBuffer[i] == id) {
				return true;
			}
		}
		return false;
	}
}
