package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link VectorUtils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class VectorUtilsTest {
	
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	@Test
	public void generate3DTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int[] vector1 = VectorUtils.generate(x1, y1, z1);
		assert(vector1[vx] == x1);
		assert(vector1[vy] == y1);
		assert(vector1[vz] == z1);
	}
	
	@Test
	public void generate2DTest() throws Exception {
		int x1 = 2, y1 = 2;
		int[] vector1 = VectorUtils.generate(x1, y1);
		assert(vector1[vx] == x1);
		assert(vector1[vy] == y1);
	}
	
	@Test
	public void equals3Test() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int x3 = 2, y3 = 2, z3 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] vector3 = new int[] {x3, y3, z3};
		assert(!VectorUtils.equals3(vector1, vector2));
		assert(VectorUtils.equals3(vector1, vector3));
	}
	
	@Test
	public void equals2Test() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int x3 = 2, y3 = 2, z3 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] vector3 = new int[] {x3, y3, z3};
		assert(!VectorUtils.equals2(vector1, vector2));
		assert(VectorUtils.equals2(vector1, vector3));
	}
	
	@Test
	public void swap3Test() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		assert(vector1[vx] == x1);
		assert(vector1[vy] == y1);
		assert(vector1[vz] == z1);
		assert(vector2[vx] == x2);
		assert(vector2[vy] == y2);
		assert(vector2[vz] == z2);
		VectorUtils.swap3(vector1, vector2);
		assert(vector1[vx] == x2);
		assert(vector1[vy] == y2);
		assert(vector1[vz] == z2);
		assert(vector2[vx] == x1);
		assert(vector2[vy] == y1);
		assert(vector2[vz] == z1);
	}
	
	@Test
	public void swap2Test() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		assert(vector1[vx] == x1);
		assert(vector1[vy] == y1);
		assert(vector2[vx] == x2);
		assert(vector2[vy] == y2);
		VectorUtils.swap3(vector1, vector2);
		assert(vector1[vx] == x2);
		assert(vector1[vy] == y2);
		assert(vector2[vx] == x1);
		assert(vector2[vy] == y1);
	}
	
	@Test
	public void invert3Test() throws Exception {
		int x1 = 2, y1 = -2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = VectorUtils.invert3(vector1);
		assert (result[vx] == -2);
		assert (result[vy] == 2);
		assert (result[vz] == -2);
	}
	
	@Test
	public void invert2Test() throws Exception {
		int x1 = 2, y1 = -2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = VectorUtils.invert2(vector1);
		assert (result[vx] == -2);
		assert (result[vy] == 2);
	}
	
	@Test
	public void toString2Test() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		String result = VectorUtils.toString(vector1);
		assert(result.contains("2"));
	}
	
}