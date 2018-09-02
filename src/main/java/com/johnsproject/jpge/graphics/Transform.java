package com.johnsproject.jpge.graphics;
import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.Vector3Utils;

/**
 * @author john
 *
 */
public class Transform {
	private long position;
	private long rotation;
	private long scale;
	
	public Transform(long position, long rotation, long scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void translate(int x, int y, int z) {
		position = VectorMathUtils.add(position, Vector3Utils.convert(x, y, z));
	}
	
	public void translateLocal(int x, int y, int z) {
		long dist = Vector3Utils.convert(x, y, z);
		position = VectorMathUtils.add(position, Vector3Utils.convert(x, y, z));
	}
	
	public void rotate(int x, int y, int z) {
		rotation = VectorMathUtils.add(rotation, Vector3Utils.convert(x, y, z));
	}
	
	public void translate(long vector) {
		position = VectorMathUtils.add(position, vector);
	}
	
	public void rotate(long vector) {
		rotation = VectorMathUtils.add(rotation, vector);
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(int x, int y, int z) {
		this.position = Vector3Utils.convert(x, y, z);
	}

	public long getRotation() {
		return rotation;
	}

	public void setRotation(int x, int y, int z) {
		this.rotation = Vector3Utils.convert(x, y, z);
	}

	public long getScale() {
		return scale;
	}

	public void setScale(int x, int y, int z) {
		this.scale = Vector3Utils.convert(x, y, z);
	}
	
}
