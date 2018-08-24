package com.johnsproject.jpge.event;

import java.util.EventListener;

public interface UpdateListener extends EventListener{
	void update(UpdateEvent event);
}
