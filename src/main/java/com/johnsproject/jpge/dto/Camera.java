/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.johnsproject.jpge.graphics.Renderer;

/**
 * The Camera class is a object used to view a {@link Scene}.
 * 
 * @author John´s Project - John Salomon
 */
public class Camera implements Externalizable{
	
	private static final long serialVersionUID = -175574154748016681L;
	private String name = "Default";
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private int halfWidth = 0;
	private int halfHeight = 0;
	private int fieldOfView = 60;
	private int nearClippingPlane = 300;
	private int farClippingPlane = 100000;
	private int scaleFactor = 0;
	private Transform transform = new Transform();
	private boolean changed = false;
	
	public Camera () {}
	
	/**
	 * Creates a new instance of the Camera class filled with the given values.
	 * 
	 * @param name name of this camera.
	 * @param transform {@link Transform} of this camera.
	 * @param x x location of camera in screen.
	 * @param y y location of camera in screen.
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
	 * Returns the x location of this camera in screen.
	 * 
	 * @return x location of this camera in screen.
	 */
	public int getLocationX() {
		return x;
	}

	/**
	 * Returns the y location of this camera in screen.
	 * 
	 * @return y location of this camera in screen.
	 */
	public int getLocationY() {
		return y;
	}

	/**
	 * Sets the postition of this camera in screen.
	 * 
	 * @param x location in the x axis.
	 * @param y location in the y axis.
	 */
	public void setLocation(int x, int y) {
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
		this.scaleFactor = ((width + height) >> 7) + 1;
		changed = true;
	}

	/**
	 * Returns the field of view of this camera.
	 * 
	 * @return field of view of this camera.
	 */
	public int getFieldOfView() {
		return fieldOfView;
	}

	/**
	 * Sets the field of view of this camera.
	 * 
	 * @param fieldOfView field of view to set.
	 */
	public void setFieldOfView(int fieldOfView) {
		this.fieldOfView = fieldOfView;
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

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(name);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(width);
		out.writeInt(height);
		out.writeInt(fieldOfView);
		out.writeInt(nearClippingPlane);
		out.writeInt(farClippingPlane);
		out.writeObject(transform);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		name = in.readUTF();
		x = in.readInt();
		y = in.readInt();
		final int width = in.readInt();
		final int height = in.readInt();
		fieldOfView = in.readInt();
		nearClippingPlane = in.readInt();
		farClippingPlane = in.readInt();
		transform = (Transform) in.readObject();
		setScreenSize(width, height);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + farClippingPlane;
		result = prime * result + fieldOfView;
		result = prime * result + halfHeight;
		result = prime * result + halfWidth;
		result = prime * result + height;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nearClippingPlane;
		result = prime * result + scaleFactor;
		result = prime * result + ((transform == null) ? 0 : transform.hashCode());
		result = prime * result + width;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Camera other = (Camera) obj;
		if (farClippingPlane != other.farClippingPlane)
			return false;
		if (fieldOfView != other.fieldOfView)
			return false;
		if (height != other.height)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nearClippingPlane != other.nearClippingPlane)
			return false;
		if (transform == null) {
			if (other.transform != null)
				return false;
		} else if (!transform.equals(other.transform))
			return false;
		if (width != other.width)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
