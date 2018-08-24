package com.johnsproject.jpge.graphics.event;

import java.util.EventListener;

public interface CameraListener extends EventListener{
	void add(CameraEvent event);
	void remove(CameraEvent event);
}
