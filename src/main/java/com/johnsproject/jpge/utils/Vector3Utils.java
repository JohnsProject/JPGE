package com.johnsproject.jpge.utils;

/**
 * The VectorUtils class provides useful functionalities for handling 3D vectors. <br>
 * -Basic math operations. (addition, multiplication...) <br>
 * -Swap. <br>
 * -Min, max. <br>
 * -Equals, match. <br>
 * -To string. <br>
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class Vector3Utils {
	
	/**
	 * The default axis indexes of the vectors.
	 */
	public static final byte X = 0, Y = 1, Z = 2;
	private static final int XSHIFT = 32, YSHIFT = 16;
	private static final int HEX = 0xFFFF;
	
	/**
	 * Makes a Vector based on the given values and returns it. 
	 * The vector can be used as position, rotation or scale vector.
	 * 
	 * @param x vector's x value.
	 * @param y vector's y value.
	 * @param z vector's z value.
	 * @return vector made using given values.
	 */
	public static long convert(long x, long y, long z) {
		return ((x & HEX) << XSHIFT) | ((y & HEX) << YSHIFT) | (z & HEX);
	}
	
	/**
	 * Reads the x value of the vector and returns it.
	 * 
	 * @param vector the vector to read from.
	 * @return vector's x value.
	 */
	public static short getX(long vector) {
		return (short)((vector >> XSHIFT) & HEX);
	}
	
	/**
	 * Reads the y value of the vector and returns it.
	 * 
	 * @param vector the vector to read from.
	 * @return vector's y value.
	 */
	public static short getY(long vector) {
		return (short)((vector >> YSHIFT) & HEX);
	}
	
	/**
	 * Reads the z value of the vector and returns it.
	 * 
	 * @param vector the vector to read from.
	 * @return vector's z value.
	 */
	public static short getZ(long vector) {
		return (short)((vector) & HEX);
	}
	
	/**
	 * Adds the given value to the vectors x value and returns it.
	 * 
	 * @param vector the vector to change.
	 * @param value value to add.
	 * @return vector with changed x value.
	 */
	public static long addX(long vector, long value) {
		long x = getX(vector), y = getY(vector), z = getZ(vector);
		return convert(x + value, y, z);
	}
	
	/**
	 * Adds the given value to the vectors y value and returns it.
	 * 
	 * @param vector the vector to change.
	 * @param value value to add.
	 * @return vector with changed y value.
	 */
	public static long addY(long vector, long value) {
		long x = getX(vector), y = getY(vector), z = getZ(vector);
		return convert(x, y + value, z);
	}
	
	/**
	 * Adds the given value to the vectors z value and returns it.
	 * 
	 * @param vector the vector to change.
	 * @param value value to add.
	 * @return vector with changed z value.
	 */
	public static long addZ(long vector, long value) {
		long x = getX(vector), y = getY(vector), z = getZ(vector);
		return convert(x, y, z + value);
	}
	
	/**
	 * Adds the values of b to a and returns a.
	 * 
	 * @param a summand vector.
	 * @param b summand vector.
	 * @return a.
	 */
	public static long add(long a, long b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		long bx = getX(b), by = getY(b), bz = getZ(b);
		return convert(ax + bx, ay + by, az + bz);
	}
	
	/**
	 * Subtracts the values of b from a and returns a.
	 * 
	 * @param a minuend vector.
	 * @param b subtrahend vector.
	 * @return a.
	 */
	public static long subtract(long a, long b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		long bx = getX(b), by = getY(b), bz = getZ(b);
		return convert(ax - bx, ay - by, az - bz);
	}
	
	/**
	 * Multiplies the values of a and b and returns a.
	 * 
	 * @param a multiplier vector.
	 * @param b multiplicand vector.
	 * @return a.
	 */
	public static long multiply(long a, long b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		long bx = getX(b), by = getY(b), bz = getZ(b);
		return convert(ax * bx, ay * by, az * bz);
	}
	
	/**
	 * Divides the values of b from a and returns a.
	 * 
	 * @param a dividend vector.
	 * @param b divisor vector.
	 * @return a.
	 */
	public static long divide(long a, long b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		long bx = getX(b), by = getY(b), bz = getZ(b);
		return convert(ax / bx, ay / by, az / bz);
	}
	
	/**
	 * Adds the values of b to a and returns a.
	 * 
	 * @param a summand vector.
	 * @param b summand value.
	 * @return a.
	 */
	public static long add(long a, int b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		return convert(ax + b, ay + b, az + b);
	}
	
	/**
	 * Subtracts the values of b from a and returns a.
	 * 
	 * @param a minuend vector.
	 * @param b subtrahend value.
	 * @return a.
	 */
	public static long subtract(long a, int b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		return convert(ax - b, ay - b, az - b);
	}
	
	/**
	 * Multiplies the values of a and b and returns a.
	 * 
	 * @param a multiplier vector.
	 * @param b multiplicand value.
	 * @return a.
	 */
	public static long multiply(long a, int b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		return convert(ax * b, ay * b, az * b);
	}
	
	/**
	 * Divides the values of b from a and returns a.
	 * 
	 * @param a dividend vector.
	 * @param b divisor value.
	 * @return a.
	 */
	public static long divide(long a, int b) {
		long ax = getX(a), ay = getY(a), az = getZ(a);
		return convert(ax / b, ay / b, az / b);
	}

	/**
	 * Checks witch of the vectors are greater and returns it.
	 * 
	 * @param a first vector.
	 * @param b second vector.
	 * @return the greatest vector.
	 */
	public static long max(long a, int b) {
		long asum = getX(a) + getY(a) + getZ(a);
		long bsum = getX(b) + getY(b) + getZ(b);
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
	public static long min(long a, int b) {
		long asum = getX(a) + getY(a) + getZ(a);
		long bsum = getX(b) + getY(b) + getZ(b);
		if(asum > bsum) return b;
		else return a;
	}
	
	/**
	 * Returns a string containing the data of the given vector.
	 * 
	 * @param vector vector.
	 * @return Vector as string.
	 */
	public static String toString(long vector) {
		long vx = getX(vector), vy = getY(vector), vz = getZ(vector);
		return "Vector (" + vx + ", " + vy + ", " + vz +")";
	}
}
