/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
