package com.johnsproject.jpge;

import com.johnsproject.jpge.event.EventDispatcher;
import com.johnsproject.jpge.event.UpdateEvent;

/**
 * The GameManager class manages the game runtime.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class GameManager {
	
	private static GameManager instance;
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	
	public static final int UPDATE_GRAPHICS = 0;
	public static final int UPDATE_INPUT = 1;
	public static final int UPDATE_PHYSICS = 2;
	
	private int graphicsUpdateRate = 30;
	private int inputUpdateRate = 30;
	private int physicsUpdateRate = 30;
	
	private Thread graphicsThread;
	private Thread inputThread;
	private Thread physicsThread;
	
	private long startTime = 0;
	private boolean playing = true;
	
	public GameManager() {
		startTime = System.nanoTime();
		updateGraphics();
		updatePhysics();
		updateInput();
	}
	
	/**
	 * Starts the engine.
	 */
	public void play() {
		if (!isPlaying()) {
			playing = true;
			updateGraphics();
			updatePhysics();
		}
	}
	
	/**
	 * Pauses the engine.
	 */
	public void pause() {
		if (isPlaying()) {
			playing = false;
		}
	}
	
	/**
	 * Returns if the engine is currently playing.
	 * 
	 * @return if the engine is currently playing.
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	/**
	 * Returns the time since the engine started in nanoseconds.
	 * 
	 * @return time since the engine started in nanoseconds.
	 */
	public long getPlayTime() {
		return System.nanoTime() - startTime;
	}
	
	
	/**
	 * Returns the first time got when the engine started in nanoseconds.
	 * 
	 * @return first time got when the engine started in nanoseconds.
	 */
	public long getStartTime() {
		return startTime;
	}
	
	void updateGraphics() {
		graphicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0, before = 0;
				while (isPlaying()) {
					before = (int)System.nanoTime();
					EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UPDATE_GRAPHICS));
					lastElapsed = (int)System.nanoTime() - before;
					Profiler.getInstance().getData().setGraphicsTime(lastElapsed);
					try {
						Thread.sleep(1000/graphicsUpdateRate);
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
					EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UPDATE_INPUT));
					lastElapsed = (int)System.nanoTime() - before;
					Profiler.getInstance().getData().setInputTime(lastElapsed);
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
				while (isPlaying()) {
					before = (int)System.nanoTime();
					EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UPDATE_PHYSICS));
					lastElapsed = (int)System.nanoTime() - before;
					Profiler.getInstance().getData().setPhysicsTime(lastElapsed);
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
