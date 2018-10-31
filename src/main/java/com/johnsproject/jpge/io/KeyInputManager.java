package com.johnsproject.jpge.io;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.johnsproject.jpge.Profiler;

public class KeyInputManager {
	
	private List<JPGEKeyListener> keyListeners = Collections.synchronizedList(new ArrayList<JPGEKeyListener>());
	private Map<Integer, Character> pressedKeys = Collections.synchronizedMap(new HashMap<Integer, Character>());
	
	public KeyInputManager() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				switch (e.getID()) {
				case KeyEvent.KEY_PRESSED:
					synchronized (pressedKeys) {
						if (!pressedKeys.containsKey(e.getKeyCode())) {
							pressedKeys.put(e.getKeyCode(), e.getKeyChar());
						}
					}
					break;

				case KeyEvent.KEY_RELEASED:
					synchronized (keyListeners) {
						synchronized (pressedKeys) {
							for (int i = 0; i < keyListeners.size(); i++) {
								JPGEKeyListener keyListener = keyListeners.get(i);
								keyListener.keyReleased(new JPGEKeyEvent(e.getKeyChar(), e.getKeyCode()));
							}
							pressedKeys.remove(e.getKeyCode());
						}
					}
					break;
				}
				return false;
			}
		});
	}

	/**
	 * Tells this key input manager to send an event containing 
	 * the pressed keys to all registered listeners.
	 */
	public void update() {
		synchronized (keyListeners) {
			synchronized (pressedKeys) {
				String data = "";
				for (int key : pressedKeys.keySet()) {
					char keyChar = pressedKeys.get(key).charValue();
					data += "(" + keyChar + ", " + key + "), ";
					for (int i = 0; i < keyListeners.size(); i++) {
						JPGEKeyListener keyListener = keyListeners.get(i);
						keyListener.keyPressed(new JPGEKeyEvent(keyChar, key));
					}
				}
				if (data.equals("")) data = "no keys pressed";
				Profiler.getInstance().getData().setKeyData(data);
			}
		}
	}

	/**
	 * Adds the given {@link JPGEKeyListener} to this key input manager.
	 * 
	 * @param listener {@link JPGEKeyListener} to add.
	 */
	public void addKeyListener(JPGEKeyListener listener) {
		synchronized (keyListeners) {
			keyListeners.add(listener);
		}
	}
}
