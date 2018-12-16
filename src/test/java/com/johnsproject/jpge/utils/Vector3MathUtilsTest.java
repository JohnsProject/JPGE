/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link Vector3MathUtils}.
 * 
 * @author John's Project - John Salomon
 *
 */
public class Vector3MathUtilsTest {
	
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	@Test
	public void addVectorTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] result = new int[3];
		result = Vector3MathUtils.add(vector1, vector2, result);
		assert(result[vx] == x1 + x2);
		assert(result[vy] == y1 + y2);
		assert(result[vz] == z1 + z2);
	}
	
	@Test
	public void subtractVectorTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] result = new int[3];
		result = Vector3MathUtils.subtract(vector1, vector2, result);
		assert(result[vx] == x1 - x2);
		assert(result[vy] == y1 - y2);
		assert(result[vz] == z1 - z2);
	}
	
	@Test
	public void multiplyVectorTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] result = new int[3];
		result = Vector3MathUtils.multiply(vector1, vector2, result);
		assert(result[vx] == x1 * x2);
		assert(result[vy] == y1 * y2);
		assert(result[vz] == z1 * z2);
	}
	
	@Test
	public void divideVectorTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int x2 = 3, y2 = 3, z2 = 3;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] result = new int[3];
		result = Vector3MathUtils.divide(vector1, vector2, result);
		assert(result[vx] == x1 / x2);
		assert(result[vy] == y1 / y2);
		assert(result[vz] == z1 / z2);
	}
	
	@Test
	public void addValueTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.add(vector1, value, result);
		assert(result[vx] == x1 + value);
		assert(result[vy] == y1 + value);
		assert(result[vz] == z1 + value);
	}
	
	@Test
	public void subtractValueTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.subtract(vector1, value, result);
		assert(result[vx] == x1 - value);
		assert(result[vy] == y1 - value);
		assert(result[vz] == z1 - value);
	}
	
	@Test
	public void multiplyValueTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.multiply(vector1, value, result);
		assert(result[vx] == x1 * value);
		assert(result[vy] == y1 * value);
		assert(result[vz] == z1 * value);
	}
	
	@Test
	public void divideValueTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.divide(vector1, value, result);
		assert(result[vx] == x1 / value);
		assert(result[vy] == y1 / value);
		assert(result[vz] == z1 / value);
	}
	
	@Test
	public void maxTest() throws Exception {
		int[] vector1 = new int[] {2, 2, 2};
		int[] vector2 = new int[] {3, 3, 3};
		assert (Vector3MathUtils.max(vector1, vector2) == vector2);
	}
	
	@Test
	public void minTest() throws Exception {
		int[] vector1 = new int[] {2, 2, 2};
		int[] vector2 = new int[] {3, 3, 3};
		assert (Vector3MathUtils.min(vector1, vector2) == vector1);
	}
	
	@Test
	public void movePointByAngleZTest() throws Exception {
		int x1 = 100, y1 = 0, z1 = 0;
		int precision = 5;
		int[] vector = new int[] {x1, y1, z1};
		int[] result = new int[3];
		vector = Vector3MathUtils.movePointByAngleZ(vector, 90, result);
		assert (vector[vx] <= 0 + precision);
		assert (vector[vx] >= 0 - precision);
		assert (vector[vy] <= 100 + precision);
		assert (vector[vy] >= 100 - precision);
	}
	
	@Test
	public void movePointByAngleYTest() throws Exception {
		int x1 = 100, y1 = 0, z1 = 0;
		int precision = 5;
		int[] vector = new int[] {x1, y1, z1};
		int[] result = new int[3];
		vector = Vector3MathUtils.movePointByAngleY(vector, 90, result);
		assert (vector[vx] <= 0 + precision);
		assert (vector[vx] >= 0 - precision);
		assert (vector[vz] <= 100 + precision);
		assert (vector[vz] >= 100 - precision);
	}
	
	@Test
	public void movePointByAngleXTest() throws Exception {
		int x1 = 0, y1 = 100, z1 = 0;
		int precision = 5;
		int[] vector = new int[] {x1, y1, z1};
		int[] result = new int[3];
		vector = Vector3MathUtils.movePointByAngleX(vector, 90, result);
		assert (vector[vy] <= 0 + precision);
		assert (vector[vy] >= 0 - precision);
		assert (vector[vz] <= 100 + precision);
		assert (vector[vz] >= 100 - precision);
	}
	
	@Test
	public void movePointByScaleTest() throws Exception {
		int x1 = 100, y1 = 100, z1 = 100;
		int x2 = 10, y2 = 10, z2 = 10;
		int[] vector = new int[] {x1, y1, z1};
		int[] scale = new int[] {x2, y2, z2};
		int[] result = new int[3];
		vector = Vector3MathUtils.movePointByScale(vector, scale, result);
		assert (vector[vx] == x1 * x2);
		assert (vector[vy] == y1 * y2);
		assert (vector[vz] == z1 * z2);
	}
	
	@Test
	public void getDistanceTest() throws Exception {
		int x1 = 100, y1 = 100, z1 = 100;
		int x2 = 10, y2 = 10, z2 = 10;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] result = new int[3];
		result = Vector3MathUtils.distance(vector1, vector2, result);
		assert (result[vx] == x1 - x2);
		assert (result[vy] == y1 - y2);
		assert (result[vz] == z1 - z2);
	}
	
	@Test
	public void getCrossProductTest() throws Exception {
		int x1 = 2, y1 = 3, z1 = 4;
		int x2 = 5, y2 = 6, z2 = 7;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int[] result = new int[3];
		result = Vector3MathUtils.crossProduct(vector1, vector2, result);
		// results from google
		assert (result[vx] == -3);
		assert (result[vy] == 6);
		assert (result[vz] == -3);
	}
	
	@Test
	public void getDotProductTest() throws Exception {
		int x1 = 4, y1 = 8, z1 = 10;
		int x2 = 9, y2 = 2, z2 = 7;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] vector2 = new int[] {x2, y2, z2};
		int result = Vector3MathUtils.dotProduct(vector1, vector2);
		assert (result == 122); // result from google
	}
	
	@Test
	public void getMagnitudeTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int result = Vector3MathUtils.magnitude(vector1);
		assert(result == 3);
	}
	
	@Test
	public void normalizeTest() throws Exception {
		int x1 = 2, y1 = 2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.normalize(vector1, result);
		assert (result[vx] == 170);
		assert (result[vy] == 170);
		assert (result[vz] == 170);
	}
	
	@Test
	public void absTest() throws Exception {
		int x1 = 2, y1 = -2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.abs(vector1, result);
		assert (result[vx] == 2);
		assert (result[vy] == 2);
		assert (result[vz] == 2);
	}
	
	@Test
	public void iabsTest() throws Exception {
		int x1 = 2, y1 = -2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.iabs(vector1, result);
		assert (result[vx] == -2);
		assert (result[vy] == -2);
		assert (result[vz] == -2);
	}
	
	@Test
	public void clampTest() throws Exception {
		int x1 = 2, y1 = -2, z1 = 2;
		int[] vector1 = new int[] {x1, y1, z1};
		int[] result = new int[3];
		result = Vector3MathUtils.clamp(vector1, -1, 1, result);
		assert (result[vx] == 1);
		assert (result[vy] == -1);
		assert (result[vz] == 1);
	}
}
