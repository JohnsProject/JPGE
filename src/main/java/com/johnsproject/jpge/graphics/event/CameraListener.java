package com.johnsproject.jpge.graphics.event;

import java.util.EventListener;

/**
 * The CameraEvent interface is used to dispatch {@link CameraEvent CameraEvents} 
 * by the {@link GraphicsEventDispatcher}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public interface CameraListener extends EventListener{
	void add(CameraEvent event);
	void remove(CameraEvent event);
}
