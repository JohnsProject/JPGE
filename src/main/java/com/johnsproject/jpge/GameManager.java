package com.johnsproject.jpge;

import com.johnsproject.jpge.dto.Scene;
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
	private Scene scene;
	
	public GameManager() {
		startTime = System.nanoTime();
		scene = new Scene();
		startGraphicsThread();
		startPhysicsThread();
		startInputThread();
	}
	
	/**
	 * Starts the engine.
	 */
	public void play() {
		if (!isPlaying()) {
			playing = true;
			startGraphicsThread();
			startPhysicsThread();
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
	
	/**
	 * Returns the currently used {@link Scene}.
	 * 
	 * @return currently used {@link Scene}.
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Sets the {@link Scene} to use.
	 * 
	 * @param scene {@link Scene} to set.
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	private void startGraphicsThread() {
		graphicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0;
				while (isPlaying()) {
					lastElapsed = updateGraphics(lastElapsed);
				}
			}
		});
		graphicsThread.start();
	}
	
	private void startInputThread() {
		inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0;
				while (true) {
					lastElapsed = updateInput(lastElapsed);
				}
			}
		});
		inputThread.start();
	}
	
	private void startPhysicsThread() {
		physicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0;
				while (isPlaying()) {
					lastElapsed = updatePhysics(lastElapsed);
				}
			}
		});
		physicsThread.start();
	}
	
	private int updateGraphics(int lastElapsed) {
		long before = System.nanoTime();
		EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, scene, UPDATE_GRAPHICS));
		int elapsed = (int)(System.nanoTime() - before);
		Profiler.getInstance().getData().setGraphicsTime(elapsed);
		try {
			Thread.sleep(1000/graphicsUpdateRate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
	
	private int updateInput(int lastElapsed) {
		long before = System.nanoTime();
		EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, scene, UPDATE_INPUT));
		int elapsed = (int)(System.nanoTime() - before);
		Profiler.getInstance().getData().setInputTime(elapsed);
		try {
			Thread.sleep(1000/inputUpdateRate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
	
	private int updatePhysics(int lastElapsed) {
		long before = System.nanoTime();
		EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, scene, UPDATE_PHYSICS));
		int elapsed = (int)(System.nanoTime() - before);
		Profiler.getInstance().getData().setPhysicsTime(elapsed);
		try {
			Thread.sleep(1000/physicsUpdateRate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
}
