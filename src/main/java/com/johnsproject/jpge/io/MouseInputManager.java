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
import com.johnsproject.jpge.graphics.SceneFrame;
import com.johnsproject.jpge.utils.Vector2Utils;
import com.johnsproject.jpge.event.UpdateEvent.UpdateType;

public class MouseInputManager implements UpdateListener {
	
	private JPGEMouseEvent mouseEvent = new JPGEMouseEvent(0);
	private SceneFrame frame;
	
	private static final byte LEFT = 0, MIDDLE = 1, RIGHT = 2;
	private List<JPGEMouseListener> mouseListeners = Collections.synchronizedList(new ArrayList<JPGEMouseListener>());
	private Map<Integer, Integer> pressedKeys = Collections.synchronizedMap(new HashMap<Integer, Integer>());

	public MouseInputManager(SceneFrame frame) {
		this.frame = frame;
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

	int x = 0, y = 0;
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
//						if (frame != null) {
//							if (frame.getMousePosition() != null) {
//								if (frame.getMousePosition().getLocation() != null) {
//									x = (int)frame.getMousePosition().getX();
//									y = (int)frame.getMousePosition().getY();
//								}
//							}
//						}
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
