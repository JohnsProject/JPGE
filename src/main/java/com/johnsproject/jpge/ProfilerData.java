package com.johnsproject.jpge;

public class ProfilerData {
	
	// Graphics data
	private int renderTime = 1;
	private int renderedPolys = 1, maxPolys = 1;
	private int cameras = 1;
	private int width = 1, height = 1;

	// Input data
	private int inputTime = 1;

	// Physics data
	private int physicsTime = 1;

	public int getRenderTime() {
		return renderTime;
	}

	public void setRenderTime(int renderTime) {
		this.renderTime = renderTime;
	}

	public int getRenderedPolys() {
		return renderedPolys;
	}

	public void setRenderedPolys(int renderedPolys) {
		this.renderedPolys = renderedPolys;
	}

	public int getMaxPolys() {
		return maxPolys;
	}

	public void setMaxPolys(int maxPolys) {
		this.maxPolys = maxPolys;
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
}
