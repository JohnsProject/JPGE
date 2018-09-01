package com.johnsproject.jpge.utils;

import org.junit.Test;

public class VertexUtilsTest {

	@Test
	public void convertTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		int shade = 0b00000000000000000001; // 1
		int bone = 0b00000000000000000011; // 3
		
		long result = (0b00000000000000000001L << 59) | (0b00000000000000000011L << 48)
				| (0b000000000000000000000001L << 32) | (0b00000000000000000011L << 16) | 0b00000000000000000111L;
		long vertex = VertexUtils.convert(Vector3Utils.convert(x, y, z), bone, shade);
		assert(result == vertex);
	}
	
	@Test
	public void getVectorTest() throws Exception {
		int x = 1, y = 3, z = 7;
		int shade = 1, bone = 1;
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getVector(vertex) == vector);
	}
	
	@Test
	public void getBoneIndexTest() throws Exception {
		int x = 1, y = 3, z = 7;
		int shade = 1, bone = 1;
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getBoneIndex(vertex) == bone);
	}
	
	@Test
	public void getShadeFactorTest() throws Exception {
		int x = 1, y = 3, z = 7;
		int shade = 1, bone = 1;
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getShadeFactor(vertex) == shade);
	}
	
	@Test
	public void setVectorTest() throws Exception {
		int x = 1, y = 3, z = 7;
		int x2 = 5, y2 = 7, z2 = 9;
		int shade = 1, bone = 1;
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getVector(vertex) == vector);
		long vector2 = Vector3Utils.convert(x2, y2, z2);
		vertex = VertexUtils.setVector(vertex, vector2);
		assert(VertexUtils.getVector(vertex) == vector2);
		assert(VertexUtils.getBoneIndex(vertex) == bone);
		assert(VertexUtils.getShadeFactor(vertex) == shade);
	}
	
	@Test
	public void setBoneIndexTest() throws Exception {
		int x = 1, y = 3, z = 7;
		int shade = 1, bone = 1;
		int bone2 = 5;
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getBoneIndex(vertex) == bone);
		vertex = VertexUtils.setBoneIndex(vertex, bone2);
		assert(VertexUtils.getVector(vertex) == vector);
		assert(VertexUtils.getBoneIndex(vertex) == bone2);
		assert(VertexUtils.getShadeFactor(vertex) == shade);
	}
	
	@Test
	public void setShadeFactorTest() throws Exception {
		int x = 1, y = 3, z = 7;
		int shade = 1, bone = 1;
		int shade2 = 15;
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getShadeFactor(vertex) == shade);
		vertex = VertexUtils.setShadeFactor(vertex, shade2);
		assert(VertexUtils.getVector(vertex) == vector);
		assert(VertexUtils.getBoneIndex(vertex) == bone);
		assert(VertexUtils.getShadeFactor(vertex) == shade2);
	}
}
