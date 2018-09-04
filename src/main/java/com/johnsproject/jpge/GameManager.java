package com.johnsproject.jpge;

import com.johnsproject.jpge.event.EventDispatcher;
import com.johnsproject.jpge.event.UpdateEvent;
import com.johnsproject.jpge.event.UpdateEvent.UpdateType;

public class GameManager {
	
	private static GameManager instance;
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	
	private int targetFPS = 30;
	private int inputUpdateRate = 30;
	private int physicsUpdateRate = 30;
	
	private Thread graphicsThread;
	private Thread inputThread;
	private Thread physicsThread;
	
	public GameManager() {
		updateGraphics();
		updateInput();
		updatePhysics();
	}
	
//	public void play() {
//		graphicsThread.start();
//		inputTimer.start();
//		physicsTimer.start();
//	}
	
//	public void pause() {
//		graphicsTimer.();
//		inputTimer.stop();
//		physicsTimer.stop();
//	}
	
	void updateGraphics() {
		graphicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0, before = 0;
				while (true) {
					before = (int)System.nanoTime();
					EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UpdateType.graphics));
					lastElapsed = (int)System.nanoTime() - before;
					try {
						Thread.sleep(1000/targetFPS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		graphicsThread.start();
	}
	
	void updateInput() {
		inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0, before = 0;
				while (true) {
					before = (int)System.nanoTime();
					EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UpdateType.input));
					lastElapsed = (int)System.nanoTime() - before;
					try {
						Thread.sleep(1000/inputUpdateRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		inputThread.start();
	}
	
	void updatePhysics() {
		physicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0, before = 0;
				while (true) {
					before = (int)System.nanoTime();
					EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UpdateType.physics));
					lastElapsed = (int)System.nanoTime() - before;
					try {
						Thread.sleep(1000/physicsUpdateRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		physicsThread.start();
	}
}
