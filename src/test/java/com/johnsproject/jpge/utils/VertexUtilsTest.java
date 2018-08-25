package com.johnsproject.jpge.utils;

import org.junit.Test;

public class VertexUtilsTest {

	@Test
	public void convertTest() throws Exception {
		System.out.println(1 << 11);
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		int shade = 0b00000000000000000001; // 1
		int bone = 0b00000000000000000001; // 1
		
		long result = (0b00000000000000000001L << 60) | (0b00000000000000000001L << 48)
				| (0b000000000000000000000001L << 32) | (0b00000000000000000011L << 16) | 0b00000000000000000111L;
		long vertex = VertexUtils.convert(Vector3Utils.convert(x, y, z), bone, shade);
		assert(result == vertex);
	}
	
	@Test
	public void getVectorTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		int shade = 0b00000000000000000001; // 1
		int bone = 0b00000000000000000001; // 1
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getVector(vertex) == vector);
	}
	
	@Test
	public void getBoneIndexTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		int shade = 0b00000000000000000001; // 1
		int bone = 0b00000000000000000001; // 1
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getBoneIndex(vertex) == bone);
	}
	
	@Test
	public void getShadeFactorTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		int shade = 0b00000000000000000001; // 1
		int bone = 0b00000000000000000001; // 1
		long vector = Vector3Utils.convert(x, y, z);
		long vertex = VertexUtils.convert(vector, bone, shade);
		assert(VertexUtils.getShadeFactor(vertex) == shade);
	}
	
}
