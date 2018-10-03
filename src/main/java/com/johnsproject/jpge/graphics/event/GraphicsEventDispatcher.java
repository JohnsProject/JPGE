package com.johnsproject.jpge.graphics.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.johnsproject.jpge.event.UpdateListener;

/**
 * The GraphicsEventDispatcher class is used to dispatch graphics events.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class GraphicsEventDispatcher {
	
	private static GraphicsEventDispatcher instance;
	
	private List<CameraListener> cameraListeners = Collections.synchronizedList(new ArrayList<CameraListener>());
	
	/**
	 * Returns a instance of the GraphicsEventDispatcher class.
	 * 
	 * @return instance of the GraphicsEventDispatcher class.
	 */
	public static GraphicsEventDispatcher getInstance() {
		if (instance == null) {
			instance = new GraphicsEventDispatcher();
		}
		return instance;
	}
	
	/**
	 * Adds the given {@link CameraListener} to the list of {@link CameraListener CameraListeners} 
	 * that are called when dispatch method is called.
	 * 
	 * @param listener listener to add.
	 */
	public void addCameraListener(CameraListener listener) {
		synchronized (cameraListeners) {
			cameraListeners.add(listener);
		}
	}
	
	/**
	 * Tells this GraphicsEventDispatcher to dispatch the given camera add event 
	 * to all its {@link CameraListener CameraListeners}.
	 * 
	 * @param event event to dispatch.
	 */
	public void dispatchAddEvent(CameraEvent event) {
		synchronized (cameraListeners) {
			for (int i = 0; i < cameraListeners.size(); i++) {
				CameraListener cameraListener = cameraListeners.get(i);
				cameraListener.add(event);
			}
		}
	}
	
	/**
	 * Tells this GraphicsEventDispatcher to dispatch the given camera remove event 
	 * to all its {@link CameraListener CameraListeners}.
	 * 
	 * @param event event to dispatch.
	 */
	public void dispatchRemoveEvent(CameraEvent event) {
		synchronized (cameraListeners) {
			for (int i = 0; i < cameraListeners.size(); i++) {
				CameraListener cameraListener = cameraListeners.get(i);
				cameraListener.remove(event);
			}
		}
	}
}
