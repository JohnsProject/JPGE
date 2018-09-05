package com.johnsproject.jpge.graphics;
import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.Vector3Utils;

/**
 * The Transform class contains position, rotation and scale data of a object like {@link SceneObject} or {@link Camera}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Transform {
	private long position;
	private long rotation;
	private long scale;
	
	/**
	 * Creates a new instance of the Transform class filled with the given values.
	 * 
	 * @param position position of this transform.
	 * @param rotation rotation of this transform.
	 * @param scale scale of this transform.
	 */
	public Transform(long position, long rotation, long scale) {
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
		position = VectorMathUtils.add(position, Vector3Utils.convert(x, y, z));
	}
	
	/**
	 * Translates this transform by the given values in local xyz axis.
	 * 
	 * @param x how much to move in the x axis.
	 * @param y how much to move in the y axis.
	 * @param z how much to move in the z axis.
	 */
	public void translateLocal(int x, int y, int z) {
		long vector = Vector3Utils.convert(x, y, z);
		long rot = Vector3Utils.convert(-Vector3Utils.getX(rotation), -Vector3Utils.getY(rotation), -Vector3Utils.getZ(rotation));
		vector = VectorMathUtils.movePointByAnglesXYZ(vector, rot);
		position = VectorMathUtils.add(position, vector);
	}
	
	/**
	 * Rotates this transform by the given values.
	 * 
	 * @param x how much to rotate in the x axis.
	 * @param y how much to rotate in the y axis.
	 * @param z how much to rotate in the z axis.
	 */
	public void rotate(int x, int y, int z) {
		rotation = VectorMathUtils.add(rotation, Vector3Utils.convert(x, y, z));
	}
	
	
	/**
	 * Translates this transform by the given vector in world xyz axis.
	 * 
	 * @param vector vector containing translate values.
	 */
	public void translate(long vector) {
		position = VectorMathUtils.add(position, vector);
	}
	
	/**
	 * Rotates this transform by the given vector.
	 * 
	 * @param vector vector containing rotate values.
	 */
	public void rotate(long vector) {
		rotation = VectorMathUtils.add(rotation, vector);
	}

	/**
	 * Returns the position of this transform.
	 * 
	 * @return position of this transform.
	 */
	public long getPosition() {
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
		this.position = Vector3Utils.convert(x, y, z);
	}

	/**
	 * Returns the rotation of this transform.
	 * 
	 * @return rotation of this transform.
	 */
	public long getRotation() {
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
		this.rotation = Vector3Utils.convert(x, y, z);
	}

	/**
	 * Returns the scale of this transform.
	 * 
	 * @return scale of this transform.
	 */
	public long getScale() {
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
		this.scale = Vector3Utils.convert(x, y, z);
	}

	@Override
	public String toString() {
		return "Transform [position=" + Vector3Utils.toString(position)
		+ ", rotation=" + Vector3Utils.toString(rotation)
		+ ", scale=" + Vector3Utils.toString(scale) + "]";
	}
	
}
