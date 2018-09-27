package com.johnsproject.jpge.utils;

/**
 * The VectorUtils provides methods for generating and handling vectors.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
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
	 * This vector can be used as position, rotation or scale vector.
	 * 
	 * @param x vector position at x axis.
	 * @param y vector position at y axis.
	 * @param z vector position at z axis.
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
	 * This vector can be used as position, rotation or scale vector.
	 * 
	 * @param x vector position at x axis.
	 * @param y vector position at y axis.
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
