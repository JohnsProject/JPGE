package com.johnsproject.jpge;

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
		return instance;
	}
	
	private Thread graphicsThread;
	private Thread inputThread;
	private Thread physicsThread;
	
	private EngineSettings settings = new EngineSettings();
	private Scene scene = new Scene();
	private Renderer renderer = new Renderer();
	private Animator animator = new Animator();
	private KeyInputManager keyManager = new KeyInputManager();
	private MouseInputManager mouseManager = new MouseInputManager();
	private PhysicsAnimator physicsAnimator = new PhysicsAnimator();
	private SceneWindow sceneWindow = null;
	
	public Engine() {
		startGraphicsThread();
		startPhysicsThread();
		startInputThread();
	}
	
	/**
	 * Starts the engine.
	 */
	public void play() {
		if (!settings.isPlaying()) {
			settings.setPlaying(true);
			startGraphicsThread();
			startPhysicsThread();
		}
	}
	
	/**
	 * Pauses the engine.
	 */
	public void pause() {
		if (settings.isPlaying()) {
			settings.setPlaying(false);
		}
	}
	
	/**
	 * Returns the {@link EngineSettings settings} of this engine.
	 * 
	 * @return {@link EngineSettings settings} of this engine.
	 */
	public EngineSettings getSettings() {
		return settings;
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
	
	private void startGraphicsThread() {
		graphicsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int lastElapsed = 0;
				while (settings.isPlaying()) {
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
				while (settings.isPlaying()) {
					lastElapsed = updatePhysics(lastElapsed);
				}
			}
		});
		physicsThread.start();
	}
	
	private int updateGraphics(int lastElapsed) {
		long before = System.nanoTime();
		if (sceneWindow != null) {
			animator.animate(scene);
			renderer.render(scene, sceneWindow.getDepthBuffer());
			sceneWindow.repaint();
		}
		int elapsed = (int)(System.nanoTime() - before);
		Profiler.getInstance().getData().setGraphicsTime(elapsed);
		try {
			Thread.sleep(1000/settings.getGraphicsUpdateRate());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
	
	private int updateInput(int lastElapsed) {
		long before = System.nanoTime();
		keyManager.update();
		mouseManager.update();
		int elapsed = (int)(System.nanoTime() - before);
		Profiler.getInstance().getData().setInputTime(elapsed);
		try {
			Thread.sleep(1000/settings.getInputUpdateRate());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
	
	private int updatePhysics(int lastElapsed) {
		long before = System.nanoTime();
		
		int elapsed = (int)(System.nanoTime() - before);
		Profiler.getInstance().getData().setPhysicsTime(elapsed);
		try {
			Thread.sleep(1000/settings.getPhysicsUpdateRate());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return elapsed;
	}
}
