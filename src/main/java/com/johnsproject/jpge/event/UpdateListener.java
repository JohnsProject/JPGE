package com.johnsproject.jpge.event;

import java.util.EventListener;

/**
 * The CameraEvent interface is used to dispatch {@link UpdateEvent UpdateEvents} 
 * by the {@link EventDispatcher}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public interface UpdateListener extends EventListener{
	void update(UpdateEvent event);
}
