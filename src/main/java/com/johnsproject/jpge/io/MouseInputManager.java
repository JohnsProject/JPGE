package com.johnsproject.jpge.io;

import java.awt.AWTEvent;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private List<JPGEMouseListener> mouseListeners = Collections.synchronizedList(new ArrayList<JPGEMouseListener>());
	private Map<Integer, Integer> pressedKeys = Collections.synchronizedMap(new HashMap<Integer, Integer>());

	public MouseInputManager() {
		EventDispatcher.getInstance().addUpdateListener(this);
		GameManager.getInstance();
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if(event instanceof MouseEvent){
                	synchronized (pressedKeys) {
	                    MouseEvent evt = (MouseEvent)event;
	                    if(evt.getID() == MouseEvent.MOUSE_CLICKED){
	                    	int x = (int)evt.getX();
	        				int y = (int)evt.getY();
	        				pressedKeys.put(evt.getButton()-1, Vector2Utils.convert(x, y));
	                    }
                	}
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
	}

	@Override
	public void update(UpdateEvent event) {
		if (event.getUpdateType() == UpdateType.input) {
			synchronized (mouseListeners) {
				synchronized (pressedKeys) {
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
				}
			}
		}
	}
	
	public void addMouseListener(JPGEMouseListener listener) {
		synchronized (mouseListeners) {
			mouseListeners.add(listener);
		}
	}
}
