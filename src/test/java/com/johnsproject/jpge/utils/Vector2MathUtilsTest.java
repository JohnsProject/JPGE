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
 * Test class for {@link Vector2MathUtils}.
 * 
 * @author John's Project - John Salomon
 *
 */
public class Vector2MathUtilsTest {
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y;
	
	@Test
	public void addVectorTest() throws Exception {
		int x1 = 2, y1 = 2;
		int x2 = 3, y2 = 3;
		int[] vector1 = new int[] {x1, y1};
		int[] vector2 = new int[] {x2, y2};
		int[] result = new int[2];
		result = Vector2MathUtils.add(vector1, vector2, result);
		assert(result[vx] == x1 + x2);
		assert(result[vy] == y1 + y2);
	}
	
	@Test
	public void subtractVectorTest() throws Exception {
		int x1 = 2, y1 = 2;
		int x2 = 3, y2 = 3;
		int[] vector1 = new int[] {x1, y1};
		int[] vector2 = new int[] {x2, y2};
		int[] result = new int[2];
		result = Vector2MathUtils.subtract(vector1, vector2, result);
		assert(result[vx] == x1 - x2);
		assert(result[vy] == y1 - y2);
	}
	
	@Test
	public void multiplyVectorTest() throws Exception {
		int x1 = 2, y1 = 2;
		int x2 = 3, y2 = 3;
		int[] vector1 = new int[] {x1, y1};
		int[] vector2 = new int[] {x2, y2};
		int[] result = new int[2];
		result = Vector2MathUtils.multiply(vector1, vector2, result);
		assert(result[vx] == x1 * x2);
		assert(result[vy] == y1 * y2);
	}
	
	@Test
	public void divideVectorTest() throws Exception {
		int x1 = 2, y1 = 2;
		int x2 = 3, y2 = 3;
		int[] vector1 = new int[] {x1, y1};
		int[] vector2 = new int[] {x2, y2};
		int[] result = new int[2];
		result = Vector2MathUtils.divide(vector1, vector2, result);
		assert(result[vx] == x1 / x2);
		assert(result[vy] == y1 / y2);
	}
	
	@Test
	public void addValueTest() throws Exception {
		int x1 = 2, y1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.add(vector1, value, result);
		assert(result[vx] == x1 + value);
		assert(result[vy] == y1 + value);
	}
	
	@Test
	public void subtractValueTest() throws Exception {
		int x1 = 2, y1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.subtract(vector1, value, result);
		assert(result[vx] == x1 - value);
		assert(result[vy] == y1 - value);
	}
	
	@Test
	public void multiplyValueTest() throws Exception {
		int x1 = 2, y1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.multiply(vector1, value, result);
		assert(result[vx] == x1 * value);
		assert(result[vy] == y1 * value);
	}
	
	@Test
	public void divideValueTest() throws Exception {
		int x1 = 2, y1 = 2;
		int value = 5;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.divide(vector1, value, result);
		assert(result[vx] == x1 / value);
		assert(result[vy] == y1 / value);
	}
	
	@Test
	public void maxTest() throws Exception {
		int[] vector1 = new int[] {2, 2};
		int[] vector2 = new int[] {3, 3};
		assert (Vector2MathUtils.max(vector1, vector2) == vector2);
	}
	
	@Test
	public void minTest() throws Exception {
		int[] vector1 = new int[] {2, 2};
		int[] vector2 = new int[] {3, 3};
		assert (Vector2MathUtils.min(vector1, vector2) == vector1);
	}
	
	@Test
	public void movePointByAngleZTest() throws Exception {
		int x1 = 100, y1 = 0;
		int precision = 5;
		int[] vector = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.movePointByAngleZ(vector, 90, result);
		assert (result[vx] <= 0 + precision);
		assert (result[vx] >= 0 - precision);
		assert (result[vy] <= 100 + precision);
		assert (result[vy] >= 100 - precision);
	}
	
	@Test
	public void movePointByScaleTest() throws Exception {
		int x1 = 100, y1 = 100;
		int x2 = 10, y2 = 10;
		int[] vector = new int[] {x1, y1};
		int[] scale = new int[] {x2, y2};
		int[] result = new int[2];
		result = Vector2MathUtils.movePointByScale(vector, scale, result);
		assert (result[vx] == x1 * x2);
		assert (result[vy] == y1 * y2);
	}
	
	@Test
	public void getDistanceTest() throws Exception {
		int x1 = 100, y1 = 100;
		int x2 = 10, y2 = 10;
		int[] vector1 = new int[] {x1, y1};
		int[] vector2 = new int[] {x2, y2};
		int[] result = new int[2];
		result = Vector2MathUtils.distance(vector1, vector2, result);
		assert (result[vx] == x1 - x2);
		assert (result[vy] == y1 - y2);
	}
	
	@Test
	public void getDotProductTest() throws Exception {
		int x1 = -6, y1 = 8;
		int x2 = 5, y2 = 12;
		int[] vector1 = new int[] {x1, y1};
		int[] vector2 = new int[] {x2, y2};
		int result = Vector2MathUtils.dotProduct(vector1, vector2);
		assert(result == 66); // result from google
	}
	
	@Test
	public void getMagnitudeTest() throws Exception {
		int x1 = 2, y1 = 2;
		int[] vector1 = new int[] {x1, y1};
		int result = Vector2MathUtils.magnitude(vector1);
		assert(result == 2);
	}
	
	@Test
	public void normalizeTest() throws Exception {
		int x1 = 2, y1 = 2;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.normalize(vector1, result);
		assert (result[vx] >> MathUtils.SHIFT == 1);
		assert (result[vy] >> MathUtils.SHIFT == 1);
	}
	
	@Test
	public void absTest() throws Exception {
		int x1 = 2, y1 = -2;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.abs(vector1, result);
		assert (result[vx] == 2);
		assert (result[vy] == 2);
	}
	
	@Test
	public void iabsTest() throws Exception {
		int x1 = 2, y1 = -2;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.iabs(vector1, result);
		assert (result[vx] == -2);
		assert (result[vy] == -2);
	}
	
	@Test
	public void clampTest() throws Exception {
		int x1 = 2, y1 = -2;
		int[] vector1 = new int[] {x1, y1};
		int[] result = new int[2];
		result = Vector2MathUtils.clamp(vector1, -1, 1, result);
		assert (result[vx] == 1);
		assert (result[vy] == -1);
	}
}
