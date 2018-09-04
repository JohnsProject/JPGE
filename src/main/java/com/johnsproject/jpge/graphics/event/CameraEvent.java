package com.johnsproject.jpge.graphics.event;

import com.johnsproject.jpge.graphics.Camera;
import com.johnsproject.jpge.graphics.Scene;
import com.johnsproject.jpge.graphics.SceneFrame;

/**
 * The CameraEvent class is used to tell the {@link SceneFrame} 
 * that a {@link Camera} has been added to the {@link Scene}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class CameraEvent {
	private Camera camera;

	/**
	 * Creates a new instance of the CameraEvent class filled with the given values.
	 * 
	 * @param camera camera of this camera event.
	 */
	public CameraEvent(Camera camera) {
		super();
		this.camera = camera;
	}

	/**
	 * Returns the {@link Camera} of this event.
	 * 
	 * @return {@link Camera} of this event.
	 */
	public Camera getCamera() {
		return camera;
	}
}
