package com.johnsproject.jpge.graphics;

import java.util.Arrays;

import com.johnsproject.jpge.utils.Vector3Utils;

/**
 * @author john
 *
 */
public class Transform {
	private final int vx = Vector3Utils.X, vy = Vector3Utils.Y, vz = Vector3Utils.Z;
	private int[] position;
	private int[] rotation;
	private int[] scale;
	
	public Transform(int[] position, int[] rotation, int[] scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void translate(int x, int y, int z) {
		position[vx] += x;
		position[vy] += y;
		position[vz] += z;
	}
	
	public void rotate(int x, int y, int z) {
		rotation[vx] += x;
		rotation[vy] += y;
		rotation[vz] += z;
	}
	
	public void translate(int[] vector) {
		position[vx] += vector[vx];
		position[vy] += vector[vy];
		position[vz] += vector[vz];
	}
	
	public void rotate(int[] vector) {
		rotation[vx] += vector[vx];
		rotation[vy] += vector[vy];
		rotation[vz] += vector[vz];
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int x, int y, int z) {
		position[vx] = x;
		position[vy] = y;
		position[vz] = z;
	}

	public int[] getRotation() {
		return rotation;
	}

	public void setRotation(int x, int y, int z) {
		rotation[vx] = x;
		rotation[vy] = y;
		rotation[vz] = z;
	}

	public int[] getScale() {
		return scale;
	}

	public void setScale(int x, int y, int z) {
		scale[vx] = x;
		scale[vy] = y;
		scale[vz] = z;
	}

	@Override
	public String toString() {
		return "Transform [position=" + Arrays.toString(position) + ", rotation=" + Arrays.toString(rotation)
				+ ", scale=" + Arrays.toString(scale) + "]";
	}
	
}
