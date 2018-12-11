package com.johnsproject.jpge.io;

import java.awt.event.MouseEvent;

/**
 * The listener interface for receiving {@link MouseEvent MouseEvents} from the
 * {@link InputManager}.
 * 
 * @author John´s Project - John Salomon
 */
public interface JPGEMouseListener {

	/**
	 * Called when a mouse button is pressed.
	 * 
	 * @param e mouse event.
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Called when a mouse button is being pressed.
	 * 
	 * @param e mouse event.
	 */
	public void mouseDown(MouseEvent e);

	/**
	 * Called when a mouse button is released.
	 * 
	 * @param e mouse event.
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Called when a mouse button is clicked.
	 * 
	 * @param e mouse event.
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Called when the mouse exits a component.
	 * 
	 * @param e mouse event.
	 */
	public void mouseExited(MouseEvent e);

	/**
	 * Called when the mouse enters a component.
	 * 
	 * @param e mouse event.
	 */
	public void mouseEntered(MouseEvent e);

}
