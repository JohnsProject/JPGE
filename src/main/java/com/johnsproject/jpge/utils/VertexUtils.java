package com.johnsproject.jpge.utils;

import com.johnsproject.jpge.graphics.Mesh;

/**
 * The VertexUtils class provides methods for generating and reading values from vertexes.
 * This vertexes are used by the {@link Mesh} class.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class VertexUtils {

	private static final int BONESHIFT = 48, SHADESHIFT = 59;
	private static final long VECTORHEX = 0xFFFFFFFFFFFFL, BONEHEX = 0x7FF, SHADEHEX = 0xF;
	
	/**
	 * Generates a vertex based on the given values and returns it.
	 * This vertex is used by the {@link Mesh} class.
	 * The vector can be generated using {@link Vector3Utils} class.
	 * (Bits 0-47 are vector, 48-58 are boneIndex, 59-63 are shadeFactor)
	 * 
	 * @param vector vertex position.
	 * @param boneIndex vertex bone index.
	 * @param shadeFactor vertex shade factor.
	 * @return vertex generated from given values.
	 */
	public static long convert(long vector, long boneIndex, long shadeFactor) {
		return ((shadeFactor& SHADEHEX)<<SHADESHIFT)|((boneIndex& BONEHEX)<<BONESHIFT)|(vector& VECTORHEX);
	}
	
	/**
	 * Reads the position vector of the given vertex and returns it.
	 * The vector can be generated using {@link Vector3Utils} class.
	 * 
	 * @param vertex vertex to read from.
	 * @return vector of vertex.
	 */
	public static long getVector(long vertex) {
		return vertex & VECTORHEX;
	}
	
	/**
	 * Reads the bone index of the given vertex and returns it in the range 0-2047.
	 * 
	 * @param vertex vertex to read from.
	 * @return bone index of vertex.
	 */
	public static int getBoneIndex(long vertex) {
		return (int)((vertex>>BONESHIFT) & BONEHEX);
	}
	
	/**
	 * Reads the shade factor of the given vertex and returns it  in the range 0-16.
	 * 
	 * @param vertex vertex to read from.
	 * @return shade factor of vertex.
	 */
	public static int getShadeFactor(long vertex) {
		return (int)((vertex>>SHADESHIFT) & SHADEHEX);
	}
	
	/**
	 * Sets the vector of the given vertex and returns it.
	 * The vector can be generated using {@link Vector3Utils} class.
	 * 
	 * @param vertex vertex to change.
	 * @param vector vector to set.
	 * @return modified vertex.
	 */
	public static long setVector(long vertex, long vector) {
		long boneIndex = getBoneIndex(vertex);
		long shadeFactor = getShadeFactor(vertex);
		return convert(vector, boneIndex, shadeFactor);
	}
	
	/**
	 * Sets the bone index of the given vertex and returns it.
	 * The range of this value is 0-2047.
	 * 
	 * @param vertex vertex to change.
	 * @param boneIndex bone index to set.
	 * @return modified vertex.
	 */
	public static long setBoneIndex(long vertex, long boneIndex) {
		long vector = getVector(vertex);
		long shadeFactor = getShadeFactor(vertex);
		return convert(vector, boneIndex, shadeFactor);
	}
	
	/**
	 * Sets the shade factor of the given vertex and returns it.
	 * The range of this value is 0-16.
	 * 
	 * @param vertex vertex to change.
	 * @param shadeFactor shade factor to set.
	 * @return modified vertex.
	 */
	public static long setShadeFactor(long vertex, long shadeFactor) {
		long vector = getVector(vertex);
		long boneIndex = getBoneIndex(vertex);
		return convert(vector, boneIndex, shadeFactor);
	}
	
	/**
	 * Returns a string containing the data of the given vertex.
	 * 
	 * @param vertex vertex to read from.
	 * @return Vertex as string.
	 */
	public static String toString(long vertex) {
		long vector = getVector(vertex);
		long boneIndex = getBoneIndex(vertex);
		long shadeFactor = getShadeFactor(vertex);
		return "Vertex ( " + Vector3Utils.toString(vector) + ", " + boneIndex + ", " + shadeFactor + ")";
	}
}
