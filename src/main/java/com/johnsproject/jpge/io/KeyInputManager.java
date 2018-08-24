package com.johnsproject.jpge.io;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
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

	private List<KeyListener> keyListeners = new ArrayList<KeyListener>();
	private Map<Integer, Character> pressedKeys = new HashMap<Integer, Character>();

	Semaphore semaphore = new Semaphore(1);

	public static KeyInputManager getInstance() {
		if (instance == null) {
			instance = new KeyInputManager();
		}
		return instance;
	}

	public KeyInputManager() {
		EventDispatcher.getInstance().addUpdateListener(this);
		GameManager.getInstance();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(java.awt.event.KeyEvent e) {
				switch (e.getID()) {
				case java.awt.event.KeyEvent.KEY_PRESSED:
					if (getSemaphore()) {
						if (!pressedKeys.containsKey(e.getKeyCode())) {
							pressedKeys.put(e.getKeyCode(), e.getKeyChar());
						}
						semaphore.release();
					}
					break;

				case java.awt.event.KeyEvent.KEY_RELEASED:
					for (KeyListener keyListener : keyListeners) {
						KeyEvent keyEvent = new KeyEvent(e.getKeyChar(), e.getKeyCode());
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
		// TODO Auto-generated method stub
		if (event.getUpdateType() == UpdateType.input) {
			if (getSemaphore()) {
				for (KeyListener keyListener : keyListeners) {
					for (int key : pressedKeys.keySet()) {
						KeyEvent keyEvent = new KeyEvent(pressedKeys.get(key).charValue(), key);
						keyListener.keyPressed(keyEvent);
					}
				}
				semaphore.release();
			}
		}
	}

	public void addKeyListener(KeyListener listener) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyListeners == null) ? 0 : keyListeners.hashCode());
		result = prime * result + ((pressedKeys == null) ? 0 : pressedKeys.hashCode());
		result = prime * result + ((semaphore == null) ? 0 : semaphore.hashCode());
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
		KeyInputManager other = (KeyInputManager) obj;
		if (keyListeners == null) {
			if (other.keyListeners != null)
				return false;
		} else if (!keyListeners.equals(other.keyListeners))
			return false;
		if (pressedKeys == null) {
			if (other.pressedKeys != null)
				return false;
		} else if (!pressedKeys.equals(other.pressedKeys))
			return false;
		if (semaphore == null) {
			if (other.semaphore != null)
				return false;
		} else if (!semaphore.equals(other.semaphore))
			return false;
		return true;
	}
}
