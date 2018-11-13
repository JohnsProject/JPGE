package com.johnsproject.jpge.dto;

import com.johnsproject.jpge.graphics.Renderer;
import com.johnsproject.jpge.graphics.SceneWindow;

/**
 * The Camera class is used to view a {@link Scene}.
 * The {@link Renderer} will take all cameras of the {@link Scene} 
 * used by the {@link SceneWindow} and render what is in the camera view.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Camera{
	
	private String name;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private int halfWidth = 0;
	private int halfHeight = 0;
	private int FieldOfView = 60;
	private int nearClippingPlane = 300;
	private int farClippingPlane = 100000;
	private int scaleFactor;
	private Transform transform;
	private boolean changed = false;
	
	/**
	 * Creates a new instance of the Camera class filled with the given values.
	 * 
	 * @param name name of this camera.
	 * @param transform {@link Transform} of this camera.
	 * @param x x position of camera in screen.
	 * @param y y position of camera in screen.
	 * @param width width of camera in screen.
	 * @param height height of camera in screen.
	 */
	public Camera(String name, Transform transform, int x, int y, int width, int height) {
		this.name = name;
		this.transform = transform;
		this.width = width;
		this.height = height;
		this.halfWidth = width >> 1;
		this.halfHeight = height >> 1;
		this.scaleFactor = ((width + height) >> 7) + 1;
		this.x = x;
		this.y = y;
		this.changed = true;
	}

	/**
	 * Returns the width of this camera in screen.
	 * 
	 * @return width of this camera in screen.
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the height of this camera in screen.
	 * 
	 * @return height of this camera in screen.
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Returns the half width of this camera in screen.
	 * This is used by the {@link Renderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return half width of this camera in screen.
	 */
	public int getHalfWidth() {
		return this.halfWidth;
	}
	
	/**
	 * Returns the half height of this camera in screen.
	 * This is used by the {@link Renderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return half height of this camera in screen.
	 */
	public int getHalfHeight() {
		return this.halfHeight;
	}
	
	/**
	 * Returns the x position of this camera in screen.
	 * 
	 * @return x position of this camera in screen.
	 */
	public int getPositionX() {
		return x;
	}

	/**
	 * Returns the y position of this camera in screen.
	 * 
	 * @return y position of this camera in screen.
	 */
	public int getPositionY() {
		return y;
	}

	/**
	 * Sets the postition of this camera in screen.
	 * 
	 * @param x position in the x axis.
	 * @param y position in the y axis.
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the scale factor of this camera.
	 * This is used by the {@link Renderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return scale factor of this camera.
	 */
	public int getScaleFactor() {
		return this.scaleFactor;
	}

	/**
	 * Sets the screen size of this camera.
	 * 
	 * @param width width to set.
	 * @param height height to set.
	 */
	public void setScreenSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.halfWidth = width >> 1;
		this.halfHeight = height >> 1;
		changed = true;
	}

	/**
	 * Returns the field of view of this camera.
	 * 
	 * @return field of view of this camera.
	 */
	public int getFieldOfView() {
		return FieldOfView;
	}

	/**
	 * Sets the field of view of this camera.
	 * 
	 * @param fieldOfView field of view to set.
	 */
	public void setFieldOfView(int fieldOfView) {
		FieldOfView = fieldOfView;
		changed = true;
	}

	/**
	 * Returns the near clipping plane of this camera.
	 * 
	 * @return near clipping plane of this camera.
	 */
	public int getNearClippingPlane() {
		return nearClippingPlane;
	}

	/**
	 * Sets the near clipping plane of this camera.
	 * 
	 * @param nearClippingPlane near clipping plane to set.
	 */
	public void setNearClippingPlane(int nearClippingPlane) {
		this.nearClippingPlane = nearClippingPlane;
		changed = true;
	}

	/**
	 * Returns the far clipping plane of this camera.
	 * 
	 * @return far clipping plane of this camera.
	 */
	public int getFarClippingPlane() {
		return farClippingPlane;
	}

	/**
	 * Sets the far clipping plane of this camera.
	 * 
	 * @param farClippingPlane far clipping plane to set.
	 */
	public void setFarClippingPlane(int farClippingPlane) {
		this.farClippingPlane = farClippingPlane;
		changed = true;
	}

	/**
	 * Returns the {@link Transform} of this camera.
	 * 
	 * @return {@link Transform} of this camera.
	 */
	public Transform getTransform() {
		changed = true;
		return transform;
	}

	/**
	 * Returns the name of this camera.
	 * 
	 * @return name of this camera.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the if this camera has changed since last frame.
	 * 
	 * @return if this camera has changed since last frame.
	 */
	public boolean changed() {
		return changed;
	}
	
	/**
	 * Sets that this camera has changed since last frame.
	 * 
	 * @param changed if this camera has changed since last frame.
	 */
	public void changed(boolean changed) {
		this.changed = changed;
	}
}
