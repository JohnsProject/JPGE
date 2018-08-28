package com.johnsproject.jpge.io;

import java.awt.AWTEvent;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
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
import com.johnsproject.jpge.utils.Vector2Utils;
import com.johnsproject.jpge.event.UpdateEvent.UpdateType;

public class MouseInputManager implements UpdateListener {

	private static MouseInputManager instance;

	public static MouseInputManager getInstance() {
		if (instance == null) {
			instance = new MouseInputManager();
		}
		return instance;
	}
	
	private JPGEMouseEvent mouseEvent = new JPGEMouseEvent(0);
	
	private static final byte LEFT = 0, MIDDLE = 1, RIGHT = 2;
	private List<JPGEMouseListener> mouseListeners = new ArrayList<JPGEMouseListener>();
	private Map<Integer, Integer> pressedKeys = new HashMap<Integer, Integer>();
	
	Semaphore semaphore = new Semaphore(1);

	public MouseInputManager() {
		EventDispatcher.getInstance().addUpdateListener(this);
		GameManager.getInstance();
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if(event instanceof MouseEvent){
                	if (getSemaphore()) {
	                    MouseEvent evt = (MouseEvent)event;
	                    if(evt.getID() == MouseEvent.MOUSE_CLICKED){
	                    	int x = (int)evt.getXOnScreen();
	        				int y = (int)evt.getYOnScreen();
	        				pressedKeys.put(evt.getButton()-1, Vector2Utils.convert(x, y));
	                    }
	                    semaphore.release();
                	}
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
	}

	@Override
	public void update(UpdateEvent event) {
		if (event.getUpdateType() == UpdateType.input) {
			if (getSemaphore()) {
				for (JPGEMouseListener mouseListener : mouseListeners) {
					for (int key : pressedKeys.keySet()) {
						mouseEvent.setPosition(pressedKeys.get(key));
						if (key == LEFT) mouseListener.leftClick(mouseEvent);
						if (key == MIDDLE) mouseListener.middleClick(mouseEvent);
						if (key == RIGHT) mouseListener.rightClick(mouseEvent);
					}
					pressedKeys.clear();
					int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
					int y = (int)MouseInfo.getPointerInfo().getLocation().getY();
					mouseEvent.setPosition(Vector2Utils.convert(x, y));
					mouseListener.positionUpdate(mouseEvent);
				}
				semaphore.release();
			}
		}
	}
	
	public void addMouseListener(JPGEMouseListener listener) {
		if (getSemaphore()) {
			mouseListeners.add(listener);
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
}
