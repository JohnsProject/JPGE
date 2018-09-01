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
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		int z = 0b00000000000000000111; // 7
		
		long result = (0b000000000000000000000111L << 32) | (0b00000000000000000011L << 16) | 0b00000000000000000001L;
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
	public void setXTest() throws Exception {
		int x = 2;
		int y = 2;
		int z = 2;
		int x2 = 2;
		long vector = Vector3Utils.convert(x, y, z);
		long vector2 = Vector3Utils.setX(vector, x2);
		assert(Vector3Utils.getX(vector2) == x2);
	}
	
	@Test
	public void setYTest() throws Exception {
		int x = 2;
		int y = 2;
		int z = 2;
		int y2 = 2;
		long vector = Vector3Utils.convert(x, y, z);
		long vector2 = Vector3Utils.setY(vector, y2);
		assert(Vector3Utils.getY(vector2) == y2);
	}
	
	@Test
	public void setZTest() throws Exception {
		int x = 2;
		int y = 2;
		int z = 2;
		int z2 = 2;
		long vector = Vector3Utils.convert(x, y, z);
		long vector2 = Vector3Utils.setZ(vector, z2);
		assert(Vector3Utils.getZ(vector2) == z2);
	}
}
