package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link Vector2Utils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class Vector2UtilsTest {

	@Test
	public void convertTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		
		long result = (0b00000000000000000011L << 16) | 0b00000000000000000001L;
		long vector = Vector2Utils.convert(x, y);
		assert(result == vector);
	}
	
	@Test
	public void getXTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		
		int vector = Vector2Utils.convert(x, y);
		int gx = Vector2Utils.getX(vector);
		assert(gx == x);
	}
	
	@Test
	public void getYTest() throws Exception {
		int x = 0b00000000000000000001; // 1
		int y = 0b00000000000000000011; // 3
		
		int vector = Vector2Utils.convert(x, y);
		int gy = Vector2Utils.getY(vector);
		assert(gy == y);
	}
	
	@Test
	public void setXTest() throws Exception {
		int x = 2;
		int y = 2;
		int x2 = 2;
		int vector = Vector2Utils.convert(x, y);
		int vector2 = Vector2Utils.setX(vector, x2);
		assert(Vector2Utils.getX(vector2) == x2);
	}
	
	@Test
	public void setYTest() throws Exception {
		int x = 2;
		int y = 2;
		int y2 = 2;
		int vector = Vector2Utils.convert(x, y);
		int vector2 = Vector2Utils.setY(vector, y2);
		assert(Vector2Utils.getY(vector2) == y2);
	}
		
}
