package com.johnsproject.jpge.dto;

import java.io.Serializable;

/**
 * The Face class contains data of a face.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Face implements Serializable{

	private static final long serialVersionUID = -6880148086300072710L;
	private int vertex1;
	private int vertex2;
	private int vertex3;
	private int[] uv1;
	private int[] uv2;
	private int[] uv3;
	private int material;
	private boolean culled;
	
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
}
