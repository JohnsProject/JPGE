package com.johnsproject.jpge;

/**
 * The JPGE interface is used by the {@link Engine} 
 * to send update from the jpge thread to all jpge listeners.
 * 
 * @author John´s Project - John Salomon
 */
public interface JPGE {
	
	/**
	 * This is called by the {@link Engine} in every JPGE update.
	 * How often this should be called can be set in the with 
	 * jpge update rate in the {@link Engine}.
	 */
	public void update();
	
}
