package com.johnsproject.jpge.utils;

/**
 * The VectorUtils class provides useful functionalities for handling vectors. <br>
 * -Basic math operations. (addition, multiplication...) <br>
 * -Swap. <br>
 * -Min, max. <br>
 * -Equals, match. <br>
 * -To string. <br>
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class VectorUtils {
	
	/**
	 * The default axis indexes of the vectors.
	 */
	public static final byte X = 0, Y = 1, Z = 2;
	
	/**
	 * Adds the values of b to a and returns a.
	 * 
	 * @param a summand vector.
	 * @param b summand vector.
	 * @return a.
	 */
	public static int[] add(int[] a, int[] b) {
		a[X] += b[X];
		a[Y] += b[Y];
		a[Z] += b[Z];
		return a;
	}
	
	/**
	 * Subtracts the values of b from a and returns a.
	 * 
	 * @param a minuend vector.
	 * @param b subtrahend vector.
	 * @return a.
	 */
	public static int[] subtract(int[] a, int[] b) {
		a[X] -= b[X];
		a[Y] -= b[Y];
		a[Z] -= b[Z];
		return a;
	}
	
	/**
	 * Multiplies the values of a and b and returns a.
	 * 
	 * @param a multiplier vector.
	 * @param b multiplicand vector.
	 * @return a.
	 */
	public static int[] multiply(int[] a, int[] b) {
		a[X] *= b[X];
		a[Y] *= b[Y];
		a[Z] *= b[Z];
		return a;
	}
	
	/**
	 * Divides the values of b from a and returns a.
	 * 
	 * @param a dividend vector.
	 * @param b divisor vector.
	 * @return a.
	 */
	public static int[] divide(int[] a, int[] b) {
		if(b[X] != 0) a[X] /= b[X];
		if(b[Y] != 0) a[Y] /= b[Y];
		if(b[Z] != 0) a[Z] /= b[Z];
		return a;
	}
	
	/**
	 * Adds the values of b to a and returns a.
	 * 
	 * @param a summand vector.
	 * @param b summand value.
	 * @return a.
	 */
	public static int[] add(int[] a, int b) {
		a[X] += b;
		a[Y] += b;
		a[Z] += b;
		return a;
	}
	
	/**
	 * Subtracts the values of b from a and returns a.
	 * 
	 * @param a minuend vector.
	 * @param b subtrahend value.
	 * @return a.
	 */
	public static int[] subtract(int[] a, int b) {
		a[X] -= b;
		a[Y] -= b;
		a[Z] -= b;
		return a;
	}
	
	/**
	 * Multiplies the values of a and b and returns a.
	 * 
	 * @param a multiplier vector.
	 * @param b multiplicand value.
	 * @return a.
	 */
	public static int[] multiply(int[] a, int b) {
		a[X] *= b;
		a[Y] *= b;
		a[Z] *= b;
		return a;
	}
	
	/**
	 * Divides the values of b from a and returns a.
	 * 
	 * @param a dividend vector.
	 * @param b divisor value.
	 * @return a.
	 */
	public static int[] divide(int[] a, int b) {
		if(b != 0) a[X] /= b;
		if(b != 0) a[Y] /= b;
		if(b != 0) a[Z] /= b;
		return a;
	}

	/**
	 * Checks witch of the vectors are greater and returns it.
	 * 
	 * @param a first vector.
	 * @param b second vector.
	 * @return the greatest vector.
	 */
	public static int[] max(int[] a, int[] b) {
		int asum = a[X] + a[Y] + a[Z];
		int bsum = b[X] + b[Y] + b[Z];
		if(asum > bsum) return a;
		else return b;
	}
	
	/**
	 * Checks witch of the vectors are smaller and returns it.
	 * 
	 * @param a first vector.
	 * @param b second vector.
	 * @return the smallest vector.
	 */
	public static int[] min(int[] a, int[] b) {
		int asum = a[X] + a[Y] + a[Z];
		int bsum = b[X] + b[Y] + b[Z];
		if(asum > bsum) return b;
		else return a;
	}
	
	/**
	 * Matches the values of a and b and returns a.
	 * 
	 * @param a first vector.
	 * @param b second vector.
	 * @return a.
	 */
	public static int[] match(int [] a, int[] b) {
		a[X] = b[X];
		a[Y] = b[Y];
		a[Z] = b[Z];
		return a;
	}
	
	/**
	 * Matches the values of a and b and returns a.
	 * 
	 * @param a first vector.
	 * @param b value to set.
	 * @return a.
	 */
	public static int[] match(int [] a, int b) {
		a[X] = b;
		a[Y] = b;
		a[Z] = b;
		return a;
	}
	
	/**
	 * Swaps the values of a and b.
	 * 
	 * @param a first vector.
	 * @param b second vector.
	 */
	public static void swap(int [] a, int[] b) {
		int tx = a[X], ty = a[Y], tz = a[Z];
		a[X] = b[X]; a[Y] = b[Y]; a[Z] = b[Z];
		b[X] = tx; b[Y] = ty; b[Z] =tz;
	}
	
	/**
	 * Checks if the values of a are equals to b.
	 * 
	 * @param a first vector.
	 * @param b second vector.
	 * @return if they are equals.
	 */
	public static boolean equals(int [] a, int[] b) {
		if(a[X] != b[X]) return false;
		if(a[Y] != b[Y]) return false;
		if(a[Z] != b[Z]) return false;
		return true;
	}
	
	/**
	 * Inverts the sign of the values of a and returns it.
	 * 
	 * @param a vector to invert.
	 * @return a.
	 */
	public static int[] invert(int [] a) {
		a[X] = -a[X];
		a[Y] = -a[Y];
		a[Z] = -a[Z];
		return a;
	}
	
	/**
	 * Returns a string containing the data of the given vector.
	 * 
	 * @param vector vector.
	 * @return Vector as string.
	 */
	public static String toString(int[] vector) {
		String result = "Vector (";
		for (int i = 0; i < vector.length; i++) {
			if (i < vector.length-1) result += " " + vector[i] + ",";
			else  result += " " + vector[i] + "";
		}
		result += ")";
		return result;
	}
}
