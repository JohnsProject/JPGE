package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link Vector3Utils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class Vector3UtilsTest {
	
	@Test
	public void convertTest() throws Exception {
		System.out.println(1 << 1);
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		
		long result = (0b000000000000000000000001L << 32) | (0b00000000000000000011L << 16) | 0b00000000000000000111L;
		long vector = Vector3Utils.convert(x, y, z);
		assert(result == vector);
	}
	
	@Test
	public void getXTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		
		long vector = Vector3Utils.convert(x, y, z);
		long gx = Vector3Utils.getX(vector);
		assert(gx == x);
	}
	
	@Test
	public void getYTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		
		long vector = Vector3Utils.convert(x, y, z);
		long gy = Vector3Utils.getY(vector);
		assert(gy == y);
	}
	
	@Test
	public void getZTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		
		long vector = Vector3Utils.convert(x, y, z);
		long gz = Vector3Utils.getZ(vector);
		assert(gz == z);
	}
	
	@Test
	public void addXTest() throws Exception {
		int x = 2;
		int y = 2;
		int z = 2;
		int x2 = 2;
		long vector = Vector3Utils.convert(x, y, z);
		long vector2 = Vector3Utils.addX(vector, x2);
		assert(Vector3Utils.getX(vector2) == x + x2);
	}
	
	@Test
	public void addYTest() throws Exception {
		int x = 2;
		int y = 2;
		int z = 2;
		int y2 = 2;
		long vector = Vector3Utils.convert(x, y, z);
		long vector2 = Vector3Utils.addY(vector, y2);
		assert(Vector3Utils.getY(vector2) == x + y2);
	}
	
	@Test
	public void addZTest() throws Exception {
		int x = 2;
		int y = 2;
		int z = 2;
		int z2 = 2;
		long vector = Vector3Utils.convert(x, y, z);
		long vector2 = Vector3Utils.addZ(vector, z2);
		assert(Vector3Utils.getZ(vector2) == x + z2);
	}
	
	@Test
	public void addVectorTest() throws Exception {
		int x1 = 0, y1 = 0, z1 = 0;
		int x2 = 2, y2 = 2, z2 = 2;
		long vector1 = Vector3Utils.convert(x1, y1, z1);
		long vector2 = Vector3Utils.convert(x2, y2, z2);
		long resultVector = Vector3Utils.add(vector1, vector2);
		assert(Vector3Utils.getX(resultVector) == x1 + x2);
		assert(Vector3Utils.getY(resultVector) == y1 + y2);
		assert(Vector3Utils.getZ(resultVector) == z1 + z2);
	}
	
	@Test
	public void subtractVectorTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 2, y2 = 2, z2 = 2;
		long vector1 = Vector3Utils.convert(x1, y1, z1);
		long vector2 = Vector3Utils.convert(x2, y2, z2);
		long resultVector = Vector3Utils.subtract(vector1, vector2);
		assert(Vector3Utils.getX(resultVector) == x1 - x2);
		assert(Vector3Utils.getY(resultVector) == y1 - y2);
		assert(Vector3Utils.getZ(resultVector) == z1 - z2);
	}
}
