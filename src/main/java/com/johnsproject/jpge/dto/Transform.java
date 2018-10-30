package com.johnsproject.jpge.dto;
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Transform class contains position, rotation and scale data of a object like {@link SceneObject} or {@link Camera}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Transform {
	
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	private int[] position;
	private int[] rotation;
	private int[] scale;
	private int[] cache = new int[3];
	
	/**
	 * Creates a new instance of the Transform class filled with the given values.
	 * 
	 * @param position position of this transform.
	 * @param rotation rotation of this transform.
	 * @param scale scale of this transform.
	 */
	public Transform(int[] position, int[] rotation, int[] scale) {
		this.position = position;
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
		position[vx] += x;
		position[vy] += y;
		position[vz] += z;
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
		position = Vector3MathUtils.add(position, cache, position);
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
		position = Vector3MathUtils.add(position, vector, position);
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
	 * Returns the position of this transform.
	 * 
	 * @return position of this transform.
	 */
	public int[] getPosition() {
		return position;
	}

	/**
	 * Sets the position of this transform.
	 * 
	 * @param x position at the x axis.
	 * @param y position at the y axis.
	 * @param z position at the z axis.
	 */
	public void setPosition(int x, int y, int z) {
		position[vx] = x;
		position[vy] = y;
		position[vz] = z;
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
		return "Transform [position=" + VectorUtils.toString(position)
		+ ", rotation=" + VectorUtils.toString(rotation)
		+ ", scale=" + VectorUtils.toString(scale) + "]";
	}
	
}
