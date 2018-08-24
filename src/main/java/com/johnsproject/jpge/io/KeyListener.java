package com.johnsproject.jpge.io;

import java.util.EventListener;

public interface KeyListener extends EventListener{
	void keyPressed(KeyEvent event);
	void keyReleased(KeyEvent event);
}
