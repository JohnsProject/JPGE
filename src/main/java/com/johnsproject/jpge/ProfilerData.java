package com.johnsproject.jpge;

public class ProfilerData {
	
	// Graphics data
	private int graphicsTime = 1;
	private int renderedFaces = 1, maxFaces = 1;
	private int cameras = 1;
	private int width = 1, height = 1;

	// Input data
	private int inputTime = 1;
	private String keyData = "";
	private String mouseData = "";
	
	// Physics data
	private int physicsTime = 1;

	public int getGraphicsTime() {
		return graphicsTime;
	}

	public void setGraphicsTime(int graphicsTime) {
		this.graphicsTime = graphicsTime;
	}

	public int getRenderedFaces() {
		return renderedFaces;
	}

	public void setRenderedFaces(int renderedPolys) {
		this.renderedFaces = renderedPolys;
	}

	public int getMaxFaces() {
		return maxFaces;
	}

	public void setMaxFaces(int maxPolys) {
		this.maxFaces = maxPolys;
	}

	public int getCameras() {
		return cameras;
	}

	public void setCameras(int cameras) {
		this.cameras = cameras;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getInputTime() {
		return inputTime;
	}

	public void setInputTime(int inputTime) {
		this.inputTime = inputTime;
	}

	public int getPhysicsTime() {
		return physicsTime;
	}

	public void setPhysicsTime(int physicsTime) {
		this.physicsTime = physicsTime;
	}

	public String getKeyData() {
		return keyData;
	}

	public void setKeyData(String keyData) {
		this.keyData = keyData;
	}

	public String getMouseData() {
		return mouseData;
	}

	public void setMouseData(String mouseData) {
		this.mouseData = mouseData;
	}

}
