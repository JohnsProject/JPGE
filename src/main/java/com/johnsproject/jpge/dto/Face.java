package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Face class contains data of a face.
 *
 * @author John´s Project - John Salomon
 */
public class Face implements Externalizable {

	private static final long serialVersionUID = -6880148086300072710L;
	
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y;
	
	private int vertex1 = 0;
	private int vertex2 = 0;
	private int vertex3 = 0;
	private int[] uv1 = new int[2];
	private int[] uv2 = new int[2];
	private int[] uv3 = new int[2];
	private int material = 0;
	private boolean culled = false;
	
	public Face() {}
	
	/**
	 * Creates a new instance of the Face class filled with the given values.
	 * 
	 * @param vertex1 	index of the first {@link Vertex} of this face in the {@link Vertex} array of the {@link Mesh} this face belongs to.
	 * @param vertex2 	index of the second {@link Vertex} of this face in the {@link Vertex} array of the {@link Mesh} this face belongs to.
	 * @param vertex3 	index of the third {@link Vertex} of this face in the {@link Vertex} array of the {@link Mesh} this face belongs to.
	 * @param uv1 		first uv of this face.
	 * @param uv2 		second uv of this face.
	 * @param uv3 		third uv of this face.
	 * @param material	index of the {@link Material} of this face in the {@link Material} array of the {@link Mesh} this face belongs to.
	 */
	public Face(int vertex1, int vertex2, int vertex3, int material, int[] uv1, int[] uv2, int[] uv3) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.vertex3 = vertex3;
		this.uv1 = uv1;
		this.uv2 = uv2;
		this.uv3 = uv3;
		this.material = material;
		this.culled = false;
	}

	/**
	 * Returns the index of the first {@link Vertex} of this face in the 
	 * {@link Vertex} array of the {@link Mesh} this face belongs to.
	 * 
	 * @return index of the first {@link Vertex} of this face in the 
	 * {@link Vertex} array of the {@link Mesh} this face belongs to.
	 */
	public int getVertex1() {
		return vertex1;
	}

	/**
	 * Returns the index of the second {@link Vertex} of this face in the 
	 * {@link Vertex} array of the {@link Mesh} this face belongs to.
	 * 
	 * @return index of the second {@link Vertex} of this face in the 
	 * {@link Vertex} array of the {@link Mesh} this face belongs to.
	 */
	public int getVertex2() {
		return vertex2;
	}

	/**
	 * Returns the index of the third {@link Vertex} of this face in the 
	 * {@link Vertex} array of the {@link Mesh} this face belongs to.
	 * 
	 * @return index of the third {@link Vertex} of this face in the 
	 * {@link Vertex} array of the {@link Mesh} this face belongs to.
	 */
	public int getVertex3() {
		return vertex3;
	}

	/**
	 * Returns the first uv of this face.
	 * 
	 * @return first uv of this face.
	 */
	public int[] getUV1() {
		return uv1;
	}

	/**
	 * Returns the second uv of this face.
	 * 
	 * @return second uv of this face.
	 */
	public int[] getUV2() {
		return uv2;
	}

	/**
	 * Returns the third uv of this face.
	 * 
	 * @return third uv of this face.
	 */
	public int[] getUV3() {
		return uv3;
	}

	/**
	 * Returns the index of the {@link Material} of this face in the 
	 * {@link Material} array of the {@link Mesh} this face belongs to.
	 * 
	 * @return index of the {@link Material} of this face in the 
	 * {@link Material} array of the {@link Mesh} this face belongs to.
	 */
	public int getMaterial() {
		return material;
	}

	/**
	 * Returns if this face is culled or not.
	 * 
	 * @return if this face is culled or not.
	 */
	public boolean isCulled() {
		return culled;
	}

	/**
	 * Sets if this face is culled or not.
	 * 
	 * @param culled if this face is culled or not.
	 */
	public void setCulled(boolean culled) {
		this.culled = culled;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(vertex1);
		out.writeInt(vertex2);
		out.writeInt(vertex3);
		out.writeInt(uv1[vx]);
		out.writeInt(uv1[vy]);
		out.writeInt(uv2[vx]);
		out.writeInt(uv2[vy]);
		out.writeInt(uv3[vx]);
		out.writeInt(uv3[vy]);
		out.writeInt(material);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		vertex1 = in.readInt();
		vertex2 = in.readInt();
		vertex3 = in.readInt();
		uv1[vx] = in.readInt();
		uv1[vy] = in.readInt();
		uv2[vx] = in.readInt();
		uv2[vy] = in.readInt();
		uv3[vx] = in.readInt();
		uv3[vy] = in.readInt();
		material = in.readInt();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + material;
		result = prime * result + Arrays.hashCode(uv1);
		result = prime * result + Arrays.hashCode(uv2);
		result = prime * result + Arrays.hashCode(uv3);
		result = prime * result + vertex1;
		result = prime * result + vertex2;
		result = prime * result + vertex3;
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
		Face other = (Face) obj;
		if (material != other.material)
			return false;
		if (!Arrays.equals(uv1, other.uv1))
			return false;
		if (!Arrays.equals(uv2, other.uv2))
			return false;
		if (!Arrays.equals(uv3, other.uv3))
			return false;
		if (vertex1 != other.vertex1)
			return false;
		if (vertex2 != other.vertex2)
			return false;
		if (vertex3 != other.vertex3)
			return false;
		return true;
	}
}
