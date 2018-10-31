package com.johnsproject.jpge;

/**
 * The EngineSettings class stores the settings of the {@link Engine}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class EngineSettings {

	private int graphicsUpdateRate = 30;
	private int inputUpdateRate = 30;
	private int physicsUpdateRate = 30;
	
	private long startTime = System.nanoTime();
	private boolean playing = true;

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
	 * Returns how often input should be updated in a second.
	 * Default is 30.
	 * 
	 * @return how often input should be updated in a second.
	 */
	public int getInputUpdateRate() {
		return inputUpdateRate;
	}
	
	/**
	 * Sets how often input should be updated in a second.
	 * Default is 30.
	 * 
	 * @param inputUpdateRate value to set.
	 */
	public void setInputUpdateRate(int inputUpdateRate) {
		this.inputUpdateRate = inputUpdateRate;
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
	 * Returns the time since the engine started in nanoseconds.
	 * 
	 * @return time since the engine started in nanoseconds.
	 */
	public long getPlayTime() {
		return System.nanoTime() - startTime;
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
	 * Sets if the engine should run.
	 * 
	 * @param playing value to set.
	 */
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
}
