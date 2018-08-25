package com.johnsproject.jpge.utils;

public class VertexUtils {

	private static final int BONESHIFT = 48, SHADESHIFT = 60;
	private static final long VECTORHEX = 0xFFFFFFFFFFFFL, BONEHEX = 0xFFF, SHADEHEX = 0x3;
	
	public static long convert(long vector, long boneIndex, long shadeFactor) {
		return ((shadeFactor& SHADEHEX)<<SHADESHIFT)|((boneIndex& BONEHEX)<<BONESHIFT)|(vector& VECTORHEX);
	}
	
	public static long getVector(long vertex) {
		return vertex & VECTORHEX;
	}
	
	public static long getBoneIndex(long vertex) {
		return (vertex>>>BONESHIFT) & BONEHEX;
	}
	
	public static long getShadeFactor(long vertex) {
		return (vertex>>>SHADESHIFT) & SHADEHEX;
	}
	
	public static long setVector(long vertex, long vector) {
		long boneIndex = getBoneIndex(vertex);
		long shadeFactor = getShadeFactor(vertex);
		return convert(vector, boneIndex, shadeFactor);
	}
	
	public static long setBoneIndex(long vertex, long boneIndex) {
		long vector = getVector(vertex);
		long shadeFactor = getShadeFactor(vertex);
		return convert(vector, boneIndex, shadeFactor);
	}
	
	public static long setShadeFactor(long vertex, long shadeFactor) {
		long vector = getVector(vertex);
		long boneIndex = getBoneIndex(vertex);
		return convert(vector, boneIndex, shadeFactor);
	}
	
	public static String toString(long vertex) {
		long vector = getVector(vertex);
		long vx = Vector3Utils.getX(vector), vy = Vector3Utils.getY(vector), vz = Vector3Utils.getZ(vector);
		long boneIndex = getBoneIndex(vertex);
		long shadeFactor = getShadeFactor(vertex);
		return "Vertex ( " + Vector3Utils.toString(vector) + ", " + boneIndex + ", " + shadeFactor + ")";
	}
}
