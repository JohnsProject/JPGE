package com.johnsproject.jpge.io;

import java.util.EventListener;

public interface JPGEKeyListener extends EventListener{
	void keyPressed(JPGEKeyEvent event);
	void keyReleased(JPGEKeyEvent event);
}
