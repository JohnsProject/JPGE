package com.johnsproject.jpge.io;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

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
	
	private List<JPGEKeyListener> keyListeners = new ArrayList<JPGEKeyListener>();
	private Map<Integer, Character> pressedKeys = new HashMap<Integer, Character>();

	Semaphore semaphore = new Semaphore(1);
	
	public KeyInputManager() {
		EventDispatcher.getInstance().addUpdateListener(this);
		GameManager.getInstance();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				switch (e.getID()) {
				case KeyEvent.KEY_PRESSED:
					if (getSemaphore()) {
						if (!pressedKeys.containsKey(e.getKeyCode())) {
							pressedKeys.put(e.getKeyCode(), e.getKeyChar());
						}
						semaphore.release();
					}
					break;

				case KeyEvent.KEY_RELEASED:
					for (JPGEKeyListener keyListener : keyListeners) {
						keyEvent.setKey(e.getKeyChar());
						keyEvent.setKeyCode(e.getKeyCode());
						keyListener.keyReleased(keyEvent);
					}
					if (getSemaphore()) {
						pressedKeys.remove(e.getKeyCode());
						semaphore.release();
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
			if (getSemaphore()) {
				for (int key : pressedKeys.keySet()) {
					for (JPGEKeyListener keyListener : keyListeners) {
						keyEvent.setKey(pressedKeys.get(key).charValue());
						keyEvent.setKeyCode(key);
						keyListener.keyPressed(keyEvent);
					}
				}
				semaphore.release();
			}
		}
	}

	public void addKeyListener(JPGEKeyListener listener) {
		if (getSemaphore()) {
			keyListeners.add(listener);
			semaphore.release();
		}
	}

	public boolean getSemaphore() {
		boolean permit = false;
		try {
			permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
		return permit;
	}

	@Override
	public String toString() {
		return "KeyInputManager [keyListeners=" + keyListeners + ", pressedKeys=" + pressedKeys + ", semaphore="
				+ semaphore + "]";
	}
}
