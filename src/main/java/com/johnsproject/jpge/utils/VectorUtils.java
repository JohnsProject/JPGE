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

/**
 * The VectorUtils provides methods for generating and handling vectors.
 * 
 * @author John´s Project - John Salomon
 * 
 */
public class VectorUtils {
	/** Values containing location of axis in a vector. */
	public static final byte X = 0, Y = 1, Z = 2;
	
	/**
	 * Generates a 3D vector using the given values and returns it.
	 * <br><code>
	 * <br> new int[] {x, y, z};
	 * <br></code>
	 * <br>
	 * This vector can be used as location, rotation or scale vector.
	 * 
	 * @param x vector location at x axis.
	 * @param y vector location at y axis.
	 * @param z vector location at z axis.
	 * @return vector generated using the given values.
	 */
	public static int[] generate(int x, int y, int z) {
		return new int[] {x, y, z};
	}
	
	/**
	 * Generates a 2D vector using the given values and returns it.
	 * This method is equivalent to
	 * <br><code>
	 * <br> new int[] {x, y};
	 * <br></code>
	 * <br>
	 * This vector can be used as location, rotation or scale vector.
	 * 
	 * @param x vector location at x axis.
	 * @param y vector location at y axis.
	 * @return vector generated using the given values.
	 */
	public static int[] generate(int x, int y) {
		return new int[] {x, y};
	}
	
	/**
	 * Checks if vector1 is equal to vector2. This method only works with 3D vectors.
	 * 
	 * @param vector1 first vector.
	 * @param vector2 second vector.
	 * @return true if vector1 is equal to vector2 if not false.
	 */
	public static boolean equals3(int[] vector1, int [] vector2) {
		if (vector1[X] != vector2[X]) return false;
		if (vector1[Y] != vector2[Y]) return false;
		if (vector1[Z] != vector2[Z]) return false;
		return true;
	}
	
	/**
	 * Checks if vector1 is equal to vector2. This method only works with 2D vectors.
	 * 
	 * @param vector1 first vector.
	 * @param vector2 second vector.
	 * @return true if vector1 is equal to vector2 if not false.
	 */
	public static boolean equals2(int[] vector1, int [] vector2) {
		if (vector1[X] != vector2[X]) return false;
		if (vector1[Y] != vector2[Y]) return false;
		return true;
	}
	
	/**
	 * Swaps the values of vector1 and vector2. This method only works with 3D vectors.
	 * 
	 * @param vector1 first vector.
	 * @param vector2 second vector.
	 */
	public static void swap3(int[] vector1, int [] vector2) {
		int tmp = 0;
		// swap x values
		tmp = vector1[X];
		vector1[X] = vector2[X];
		vector2[X] = tmp;
		// swap y values
		tmp = vector1[Y];
		vector1[Y] = vector2[Y];
		vector2[Y] = tmp;
		// swap z values
		tmp = vector1[Z];
		vector1[Z] = vector2[Z];
		vector2[Z] = tmp;
	}
	
	/**
	 * Swaps the values of vector1 and vector2. This method only works with 2D vectors.
	 * 
	 * @param vector1 first vector.
	 * @param vector2 second vector.
	 */
	public static void swap2(int[] vector1, int [] vector2) {
		int tmp = 0;
		// swap x values
		tmp = vector1[X];
		vector1[X] = vector2[X];
		vector2[X] = tmp;
		// swap y values
		tmp = vector1[Y];
		vector1[Y] = vector2[Y];
		vector2[Y] = tmp;
	}
	
	/**
     * Returns a vector containing only values with inverted sign. This method only works with 3D vectors.
     * If a value is positive it returns negative.
     * If a value is negative it returns positive.
     * @param vector vector.
     * @return vector containing only values with inverted sign.
     */
    public static int[] invert3(int[] vector) {
    	vector[X] = -vector[X];
    	vector[Y] = -vector[Y];
    	vector[Z] = -vector[Z];
    	return vector;
    }
    
    /**
     * Returns a vector containing only values with inverted sign. This method only works with 2D vectors.
     * If a value is positive it returns negative.
     * If a value is negative it returns positive.
     * @param vector vector.
     * @return vector containing only values with inverted sign.
     */
    public static int[] invert2(int[] vector) {
    	vector[X] = -vector[X];
    	vector[Y] = -vector[Y];
    	return vector;
    }
    
    /**
     * Copies the value of vector to the target. This method only works with 3D vectors.
     * 
     * @param vector vector with values.
     * @param target target vector.
     * @return target containing values of vector.
     */
    public static int[] copy3(int[] target, int[] vector) {
    	target[X] = vector[X];
    	target[Y] = vector[Y];
    	target[Z] = vector[Z];
    	return target;
    }
    
    /**
     * Copies the value of vector to the target. This method only works with 2D vectors.
     * 
     * @param vector vector with values.
     * @param target target vector.
     * @return target containing values of vector.
     */
    public static int[] copy2(int[] target, int[] vector) {
    	target[X] = vector[X];
    	target[Y] = vector[Y];
    	return target;
    }
	
	/**
	 * Returns a string containing the data of the given vector.
	 * 
	 * @param vector
	 * @return string with data.
	 */
	public static String toString(int[] vector) {
		String result = "(";
		for (int i = 0; i < vector.length; i++) {
			result += vector[i] + ", ";
		}
		result += ")";
		return result;
	}
}
