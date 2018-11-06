package com.johnsproject.jpge;

import java.util.ArrayList;
import java.util.List;

import com.johnsproject.jpge.dto.DisplayBuffer;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.graphics.Animator;
import com.johnsproject.jpge.graphics.Renderer;
import com.johnsproject.jpge.graphics.SceneWindow;
import com.johnsproject.jpge.io.KeyInputManager;
import com.johnsproject.jpge.io.MouseInputManager;
import com.johnsproject.jpge.physics.PhysicsAnimator;

/**
 * The Engine class keeps everything working.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Engine {
	
	private static Engine instance;
	public static Engine getInstance() {
		if (instance == null) {
			instance = new Engine();
		}
		synchronized (instance) {
			return instance;
		}
	}
	
	private Thread graphicsThread;
	private Thread jpgeThread;
	private Thread physicsThread;
	
	private int graphicsUpdateRate = 30;
	private int jpgeUpdateRate = 30;
	private int physicsUpdateRate = 30;
	
	private long startTime = System.currentTimeMillis();
	private boolean playing = true;
	
	private int lastJPGETime = 0;
	private int lastGraphicsTime = 0;
	private int lastPhysicsTime = 0;
	private int lastRendereredFaces = 0;
	
	private Scene scene = new Scene();
	private Renderer renderer = new Renderer();
	private Animator animator = new Animator();
	private KeyInputManager keyManager = new KeyInputManager();
	private MouseInputManager mouseManager = new MouseInputManager();
	private PhysicsAnimator physicsAnimator = new PhysicsAnimator();
	private DisplayBuffer displayBuffer = new DisplayBuffer(320, 240);
	private SceneWindow sceneWindow = null;
	
	private List<JPGE> jpgeListeners = new ArrayList<JPGE>();
	
	public Engine() {
		startGraphicsThread();
		startPhysicsThread();
		startJPGEThread();
	}
	
	/**
	 * Starts the engine.
	 */
	public void play() {
		if (!isPlaying()) {
			setPlaying(true);
			startGraphicsThread();
			startPhysicsThread();
		}
	}
	
	/**
	 * Pauses the engine.
	 */
	public void pause() {
		if (isPlaying()) {
			setPlaying(false);
		}
	}
	
	private void setPlaying(boolean value) {
		playing = value;
	}
	
	/**
	 * Returns the time since the engine started in miliseconds.
	 * 
	 * @return time since the engine started in miliseconds.
	 */
	public long getPlayTime() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Returns if the engine is running.
	 * 
	 * @return if the engine is running.
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	/**
	 * Returns the {@link Scene} used by the engine.
	 * 
	 * @return {@link Scene} used by the engine.
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Sets the {@link Scene} that the engine should use.
	 * 
	 * @param scene {@link Scene} to set.
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * Returns the {@link Renderer} used by the engine.
	 * 
	 * @return {@link Renderer} used by the engine.
	 */
	public Renderer getRenderer() {
		return renderer;
	}
	
	/**
	 * Returns the {@link Animator} used by the engine.
	 * 
	 * @return {@link Animator} used by the engine.
	 */
	public Animator getAnimator() {
		return animator;
	}
	
	/**
	 * Returns the {@link KeyInputManager} used by the engine.
	 * 
	 * @return {@link KeyInputManager} used by the engine.
	 */
	public KeyInputManager getKeyInputManager() {
		return keyManager;
	}
	
	/**
	 * Returns the {@link MouseInputManager} used by the engine.
	 * 
	 * @return {@link MouseInputManager} used by the engine.
	 */
	public MouseInputManager getMouseInputManager() {
		return mouseManager;
	}
	
	/**
	 * Returns the {@link PhysicsAnimator} used by the engine.
	 * 
	 * @return {@link PhysicsAnimator} used by the engine.
	 */
	public PhysicsAnimator getPhysicsAnimator() {
		return physicsAnimator;
	}
	
	/**
	 * Returns the {@link DisplayBuffer} used by the engine.
	 * 
	 * @return {@link DisplayBuffer} used by the engine.
	 */
	public DisplayBuffer getDisplayBuffer() {
		return displayBuffer;
	}
	
	/**
	 * Returns the {@link SceneWindow} used by the engine.
	 * 
	 * @return {@link SceneWindow} used by the engine.
	 */
	public SceneWindow getSceneWindow() {
		return sceneWindow;
	}

	/**
	 * Sets the {@link SceneWindow} that the engine should use.
	 * 
	 * @param sceneWindow {@link SceneWindow} to set.
	 */
	public void setSceneWindow(SceneWindow sceneWindow) {
		this.sceneWindow = sceneWindow;
	}
	
	/**
	 * Returns how often graphics should be updated in a second.
	 * Default is 30.
	 * 
	 * @return how often graphics should be updated in a second.
	 */
	public int getGraphicsUpdateRate() {
		return graphicsUpdateRate;
	}

	/**
	 * Sets how often graphics should be updated in a second.
	 * Default is 30.
	 * 
	 * @param graphicsUpdateRate value to set.
	 */
	public void setGraphicsUpdateRate(int graphicsUpdateRate) {
		this.graphicsUpdateRate = graphicsUpdateRate;
	}

	/**
	 * Returns how often jpge should be updated in a second.
	 * Default is 30.
	 * 
	 * @return how often jpge should be updated in a second.
	 */
	public int getJPGEUpdateRate() {
		return jpgeUpdateRate;
	}
	
	/**
	 * Sets how often jpge should be updated in a second.
	 * Default is 30.
	 * 
	 * @param jpgeUpdateRate value to set.
	 */
	public void setJPGEUpdateRate(int jpgeUpdateRate) {
		this.jpgeUpdateRate = jpgeUpdateRate;
	}

	/**
	 * Returns how often physics should be updated in a second.
	 * Default is 30.
	 * 
	 * @return how often physics should be updated in a second.
	 */
	public int getPhysicsUpdateRate() {
		return physicsUpdateRate;
	}

	/**
	 * Sets how often physics should be updated in a second.
	 * Default is 30.
	 * 
	 * @param physicsUpdateRate value to set.
	 */
	public void setPhysicsUpdateRate(int physicsUpdateRate) {
		this.physicsUpdateRate = physicsUpdateRate;
	}
	
	/**
	 * Returns how long the graphics thread took in the last update.
	 * 
	 * @return how long the graphics thread took in the last update.
	 */
	public int getLastGraphicsTime() {
		return lastGraphicsTime;
	}
	
	/**
	 * Returns how long the jpge thread took in the last update.
	 * 
	 * @return how long the jpge thread took in the last update.
	 */
	public int getLastJPGETime() {
		return lastJPGETime;
	}
	
	/**
	 * Returns how long the physics thread took in the last update.
	 * 
	 * @return how long the physics thread took in the last update.
	 */
	public int getLastPhysicsTime() {
		return lastPhysicsTime;
	}
	
	/**
	 * Returns the count of faces rendered in the last graphics update.
	 * 
	 * @return count of faces rendered in the last graphics update.
	 */
	public int getLastRenderedFaces() {
		return lastRendereredFaces;
	}
	
	/**
	 * Adds the given {@link JPGE} listener to the listeners list.
	 * 
	 * @param listener listener to add.
	 */
	public void addJPGEListener(JPGE listener) {
		this.jpgeListeners.add(listener);
	}
	
	/**
	 * Removes the given {@link JPGE} listener from the listeners list.
	 * 
	 * @param listener listener to remove.
	 */
	public void removeJPGEListener(JPGE listener) {
		this.jpgeListeners.remove(listener);
	}
	
	private void startJPGEThread() {
		jpgeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					lastJPGETime = updateJPGE(lastJPGETime);
				}
			}
		});
		jpgeThread.start();
	}
	
	private void startGraphicsThread() {
		graphicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isPlaying()) {
					lastGraphicsTime = updateGraphics(lastGraphicsTime);
				}
			}
		});
		graphicsThread.start();
	}
	
	private void startPhysicsThread() {
		physicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isPlaying()) {
					lastPhysicsTime = updatePhysics(lastPhysicsTime);
				}
			}
		});
		physicsThread.start();
	}
	
	private int updateJPGE(int lastElapsed) {
		long before = System.currentTimeMillis();
		for (int i = 0; i < jpgeListeners.size(); i++) {
			jpgeListeners.get(i).update();
		}
		int elapsed = (int)(System.currentTimeMillis() - before);
		try {
			Thread.sleep(1000/getJPGEUpdateRate());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
	
	private int updateGraphics(int lastElapsed) {
		long before = System.currentTimeMillis();
		animator.animate(scene);
		lastRendereredFaces = renderer.render(scene, displayBuffer);
		if (sceneWindow != null) {
			sceneWindow.draw();
		}
		int elapsed = (int)(System.currentTimeMillis() - before);
		try {
			Thread.sleep(1000/getGraphicsUpdateRate());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
	
	private int updatePhysics(int lastElapsed) {
		long before = System.currentTimeMillis();
		physicsAnimator.animate(scene);
		int elapsed = (int)(System.currentTimeMillis() - before);
		try {
			Thread.sleep(1000/getPhysicsUpdateRate());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
}
