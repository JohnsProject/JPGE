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
import java.util.Arrays;

import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Transform class contains location, rotation and scale data of a object.
 *
 * @author John´s Project - John Salomon
 */
public class Transform implements Externalizable {
	
	private static final long serialVersionUID = 3177394875298143014L;

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	private int[] location = new int[3];
	private int[] rotation = new int[3];
	private int[] scale = new int[3];
	private int[] cache = new int[3];
	
	public Transform() {}
	
	/**
	 * Creates a new instance of the Transform class filled with the given values.
	 * 
	 * @param location location of this transform.
	 * @param rotation rotation of this transform.
	 * @param scale scale of this transform.
	 */
	public Transform(int[] location, int[] rotation, int[] scale) {
		this.location = location;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	/**
	 * Translates this transform by the given values in world xyz axis.
	 * 
	 * @param x how much to move in the x axis.
	 * @param y how much to move in the y axis.
	 * @param z how much to move in the z axis.
	 */
	public void translate(int x, int y, int z) {
		location[vx] += x;
		location[vy] += y;
		location[vz] += z;
	}
	
	/**
	 * Translates this transform by the given values in local xyz axis.
	 * 
	 * @param x how much to move in the x axis.
	 * @param y how much to move in the y axis.
	 * @param z how much to move in the z axis.
	 */
	public void translateLocal(int x, int y, int z) {
		cache[vx] = x;
		cache[vy] = y;
		cache[vz] = z;
		cache = Vector3MathUtils.movePointByAnglesXYZ(cache, VectorUtils.invert3(rotation), cache);
		location = Vector3MathUtils.add(location, cache, location);
		VectorUtils.invert3(rotation);
	}
	
	/**
	 * Rotates this transform by the given values.
	 * 
	 * @param x how much to rotate in the x axis.
	 * @param y how much to rotate in the y axis.
	 * @param z how much to rotate in the z axis.
	 */
	public void rotate(int x, int y, int z) {
		rotation[vx] += x;
		rotation[vy] += y;
		rotation[vz] += z;
	}
	
	
	/**
	 * Translates this transform by the given vector in world xyz axis.
	 * 
	 * @param vector vector containing translate values.
	 */
	public void translate(int[] vector) {
		location = Vector3MathUtils.add(location, vector, location);
	}
	
	/**
	 * Rotates this transform by the given vector.
	 * 
	 * @param vector vector containing rotate values.
	 */
	public void rotate(int[] vector) {
		rotation = Vector3MathUtils.add(rotation, vector, rotation);
	}

	/**
	 * Returns the location of this transform.
	 * 
	 * @return location of this transform.
	 */
	public int[] getLocation() {
		return location;
	}

	/**
	 * Sets the location of this transform.
	 * 
	 * @param x location at the x axis.
	 * @param y location at the y axis.
	 * @param z location at the z axis.
	 */
	public void setLocation(int x, int y, int z) {
		location[vx] = x;
		location[vy] = y;
		location[vz] = z;
	}

	/**
	 * Returns the rotation of this transform.
	 * 
	 * @return rotation of this transform.
	 */
	public int[] getRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation of this transform.
	 * 
	 * @param x rotation at the x axis.
	 * @param y rotation at the y axis.
	 * @param z rotation at the z axis.
	 */
	public void setRotation(int x, int y, int z) {
		rotation[vx] = x;
		rotation[vy] = y;
		rotation[vz] = z;
	}

	/**
	 * Returns the scale of this transform.
	 * 
	 * @return scale of this transform.
	 */
	public int[] getScale() {
		return scale;
	}

	/**
	 * Sets the scale of this transform.
	 * 
	 * @param x scale at the x axis.
	 * @param y scale at the y axis.
	 * @param z scale at the z axis.
	 */
	public void setScale(int x, int y, int z) {
		scale[vx] = x;
		scale[vy] = y;
		scale[vz] = z;
	}

	@Override
	public String toString() {
		return "Transform [location=" + VectorUtils.toString(location)
		+ ", rotation=" + VectorUtils.toString(rotation)
		+ ", scale=" + VectorUtils.toString(scale) + "]";
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(location[vx]);
		out.writeInt(location[vy]);
		out.writeInt(location[vz]);
		out.writeInt(rotation[vx]);
		out.writeInt(rotation[vy]);
		out.writeInt(rotation[vz]);
		out.writeInt(scale[vx]);
		out.writeInt(scale[vy]);
		out.writeInt(scale[vz]);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		location[vx] = in.readInt();
		location[vy] = in.readInt();
		location[vz] = in.readInt();
		rotation[vx] = in.readInt();
		rotation[vy] = in.readInt();
		rotation[vz] = in.readInt();
		scale[vx] = in.readInt();
		scale[vy] = in.readInt();
		scale[vz] = in.readInt();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(location);
		result = prime * result + Arrays.hashCode(rotation);
		result = prime * result + Arrays.hashCode(scale);
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
		Transform other = (Transform) obj;
		if (!Arrays.equals(location, other.location))
			return false;
		if (!Arrays.equals(rotation, other.rotation))
			return false;
		if (!Arrays.equals(scale, other.scale))
			return false;
		return true;
	}
	
}
