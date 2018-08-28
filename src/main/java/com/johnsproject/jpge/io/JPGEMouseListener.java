package com.johnsproject.jpge.io;

import java.util.EventListener;

public interface JPGEMouseListener extends EventListener{
	void leftClick(JPGEMouseEvent event);
	void middleClick(JPGEMouseEvent event);
	void rightClick(JPGEMouseEvent event);
	void positionUpdate(JPGEMouseEvent event);
}
