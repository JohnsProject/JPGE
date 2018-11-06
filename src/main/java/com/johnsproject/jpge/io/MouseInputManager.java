package com.johnsproject.jpge.io;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

public class MouseInputManager {
	
	public static final byte LEFT = 0, MIDDLE = 1, RIGHT = 2;
	private int[] keysBuffer = new int[3];
	private int mouse_x = 0;
	private int mouse_y = 0;

	public MouseInputManager() {
		for (int i = 0; i < keysBuffer.length; i++) {
			keysBuffer[i] = -1;
		}
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if(event instanceof MouseEvent){
                	 MouseEvent e = (MouseEvent)event;
	                 switch (e.getID()) {
	 					case MouseEvent.MOUSE_PRESSED:
		 					for (int i = 0; i < keysBuffer.length; i++) {
		 						if (keysBuffer[i] == e.getButton()-1)
		 								break;
		 						if ((keysBuffer[i] == -1)) {
		 							keysBuffer[i] = e.getButton()-1;
		 		                    break;
		 						}
		 					}
		 					break;

	 					case MouseEvent.MOUSE_MOVED:
	 						mouse_x = (int)e.getX();
	 		                mouse_y = (int)e.getY();
		 					break;
		 					
		 				case MouseEvent.MOUSE_RELEASED:
		 					for (int i = 0; i < keysBuffer.length; i++) {
		 						if ((keysBuffer[i] == e.getButton()-1)) {
		 							keysBuffer[i] = -1;
		 						}
		 					}
		 					break;
	 				}
                }
            }
        }, AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK);
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
	 * Returns the key with the given button as input.
	 * If no key is found it returns null.
	 * 
	 * @param button
	 * @return key with the given button as input.
	 */
	public int getKey(int button) {
		for (int i = 0; i < keysBuffer.length; i++) {
			if (keysBuffer[i] == button) {
				return keysBuffer[i];
			}
		}
		return -1;
	}
	
	/**
	 * Returns the position of the mouse in the x axis in pixel coordinates.
	 * 
	 * @return position of the mouse in the x axis in pixel coordinates.
	 */
	public int getMouseX() {
		return mouse_x;
	}
	
	/**
	 * Returns the position of the mouse in the y axis in pixel coordinates.
	 * 
	 * @return position of the mouse in the y axis in pixel coordinates.
	 */
	public int getMouseY() {
		return mouse_y;
	}
}
