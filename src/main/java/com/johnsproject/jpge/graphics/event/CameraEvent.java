package com.johnsproject.jpge.graphics.event;

import com.johnsproject.jpge.graphics.Camera;

public class CameraEvent {
	private Camera camera;

	public CameraEvent(Camera camera) {
		super();
		this.camera = camera;
	}

	public Camera getCamera() {
		return camera;
	}
}
