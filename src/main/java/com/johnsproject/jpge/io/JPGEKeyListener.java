package com.johnsproject.jpge.io;

import java.awt.event.KeyEvent;

/**
 * The listener interface for receiving {@link KeyEvent KeyEvents} from the
 * {@link InputManager}.
 * 
 * @author John´s Project - John Salomon
 */
public interface JPGEKeyListener {
	
	/**
	 * Called when a key is pressed.
	 * 
	 * @param e key event.
	 */
	public void keyPressed(KeyEvent e);

	/**
	 * Called when a key is being pressed.
	 * 
	 * @param e key event.
	 */
	public void keyDown(KeyEvent e);

	/**
	 * Called when a key is released.
	 * 
	 * @param e key event.
	 */
	public void keyReleased(KeyEvent e);

	/**
	 * Called when a key is typed.
	 * 
	 * @param e key event.
	 */
	public void keyTyped(KeyEvent e);

}
