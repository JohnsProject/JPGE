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

import com.johnsproject.jpge.Profiler;
import com.johnsproject.jpge.utils.VectorUtils;

public class MouseInputManager {
	
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y;
	private static final byte LEFT = 0, MIDDLE = 1, RIGHT = 2;
	private List<JPGEMouseListener> mouseListeners = Collections.synchronizedList(new ArrayList<JPGEMouseListener>());
	private Map<Integer, int[]> pressedKeys = Collections.synchronizedMap(new HashMap<Integer, int[]>());

	private int[] cache = new int[2];
	public MouseInputManager() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if(event instanceof MouseEvent){
                	synchronized (pressedKeys) {
	                    MouseEvent evt = (MouseEvent)event;
	                    if(evt.getID() == MouseEvent.MOUSE_CLICKED){
	                    	cache[vx] = (int)evt.getX();
	                    	cache[vy] = (int)evt.getY();
	        				pressedKeys.put(evt.getButton()-1, cache);
	                    }
                	}
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
	}

	/**
	 * Tells this mouse input manager to send an event containing 
	 * the pressed keys to all registered listeners.
	 */
	public void update() {
		synchronized (mouseListeners) {
			synchronized (pressedKeys) {
				for (int key : pressedKeys.keySet()) {
					for (int i = 0; i < mouseListeners.size(); i++) {
						JPGEMouseListener mouseListener = mouseListeners.get(i);
						if (key == LEFT)
							mouseListener.leftClick(new JPGEMouseEvent(cache));
						if (key == MIDDLE)
							mouseListener.middleClick(new JPGEMouseEvent(cache));
						if (key == RIGHT)
							mouseListener.rightClick(new JPGEMouseEvent(cache));
					}
				}
				for (int i = 0; i < mouseListeners.size(); i++) {
					JPGEMouseListener mouseListener = mouseListeners.get(i);
					cache[vx] = (int) MouseInfo.getPointerInfo().getLocation().getX();
					cache[vy] = (int) MouseInfo.getPointerInfo().getLocation().getY();
					mouseListener.positionUpdate(new JPGEMouseEvent(cache));
				}
				pressedKeys.clear();
			}
		}
	}
	
	/**
	 * Adds the given {@link JPGEMouseListener} to this mouse input manager.
	 * 
	 * @param listener {@link JPGEMouseListener} to add.
	 */
	public void addMouseListener(JPGEMouseListener listener) {
		synchronized (mouseListeners) {
			mouseListeners.add(listener);
		}
	}
}
