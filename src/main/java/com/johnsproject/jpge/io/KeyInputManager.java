package com.johnsproject.jpge.io;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.johnsproject.jpge.GameManager;
import com.johnsproject.jpge.event.EventDispatcher;
import com.johnsproject.jpge.event.UpdateEvent;
import com.johnsproject.jpge.event.UpdateListener;
import com.johnsproject.jpge.event.UpdateEvent.UpdateType;

public class KeyInputManager implements UpdateListener {

	private static KeyInputManager instance;

	public static KeyInputManager getInstance() {
		if (instance == null) {
			instance = new KeyInputManager();
		}
		return instance;
	}

	JPGEKeyEvent keyEvent = new JPGEKeyEvent(' ', 0);
	
	private List<JPGEKeyListener> keyListeners = Collections.synchronizedList(new ArrayList<JPGEKeyListener>());
	private Map<Integer, Character> pressedKeys = Collections.synchronizedMap(new HashMap<Integer, Character>());
	
	public KeyInputManager() {
		EventDispatcher.getInstance().addUpdateListener(this);
		GameManager.getInstance();
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
							for (JPGEKeyListener keyListener : keyListeners) {
								keyEvent.setKey(e.getKeyChar());
								keyEvent.setKeyCode(e.getKeyCode());
								keyListener.keyReleased(keyEvent);
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

	@Override
	public void update(UpdateEvent event) {
		if (event.getUpdateType() == UpdateType.input) {
			synchronized (keyListeners) {
				synchronized (pressedKeys) {
					for (int key : pressedKeys.keySet()) {
						for (JPGEKeyListener keyListener : keyListeners) {
							keyEvent.setKey(pressedKeys.get(key).charValue());
							keyEvent.setKeyCode(key);
							keyListener.keyPressed(keyEvent);
						}
					}
				}
			}
		}
	}

	public void addKeyListener(JPGEKeyListener listener) {
		synchronized (keyListeners) {
			keyListeners.add(listener);
		}
	}

	@Override
	public String toString() {
		return "KeyInputManager [keyListeners=" + keyListeners + ", pressedKeys=" + pressedKeys + "]";
	}
}
