package com.johnsproject.jpge.graphics.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventDispatcher {
	
	private static EventDispatcher instance;
	
	private List<CameraListener> cameraListeners = Collections.synchronizedList(new ArrayList<CameraListener>());
	
	public static EventDispatcher getInstance() {
		if (instance == null) {
			instance = new EventDispatcher();
		}
		return instance;
	}
	
	public void addCameraListener(CameraListener listener) {
		synchronized (cameraListeners) {
			cameraListeners.add(listener);
		}
	}
	
	public void dispatchAddEvent(CameraEvent event) {
		synchronized (cameraListeners) {
			for (CameraListener cameraListener : cameraListeners) {
				cameraListener.add(event);
			}
		}
	}
	
	public void dispatchRemoveEvent(CameraEvent event) {
		synchronized (cameraListeners) {
			for (CameraListener cameraListener : cameraListeners) {
				cameraListener.remove(event);
			}
		}
	}
}
