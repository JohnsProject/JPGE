package com.johnsproject.jpge.utils;

/**
 * The Vector3Utils class provides methods for generating and reading 3d vectors.
 * This 3d vectors can be used as 3d position, rotation and scale.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Vector3Utils {
	
	/**
	 * The default axis indexes of the vectors.
	 */
	public static final byte X = 0, Y = 1, Z = 2;
	private static final int ZSHIFT = 32, YSHIFT = 16;
	private static final int HEX = 0xFFFF;
	
	/**
	 * Generates a vector based on the given values and returns it. 
	 * The vector can be used as position, rotation or scale vector.
	 * (Bits 0-15 are x, 16-31 are y, 32-47 are z)
	 * 
	 * @param x vector's x value.
	 * @param y vector's y value.
	 * @param z vector's z value.
	 * @return vector generated from given values.
	 */
	public static long convert(long x, long y, long z) {
		return ((z & HEX) << ZSHIFT) | ((y & HEX) << YSHIFT) | (x & HEX);
	}
	
	/**
	 * Reads the x value of the given vector and returns it in the range -32768-32767.
	 * 
	 * @param vector vector to read from.
	 * @return vector's x value.
	 */
	public static short getX(long vector) {
		return (short)(vector & HEX);
	}
	
	/**
	 * Reads the y value of the given vector and returns it in the range -32768-32767.
	 * 
	 * @param vector vector to read from.
	 * @return vector's y value.
	 */
	public static short getY(long vector) {
		return (short)((vector >> YSHIFT) & HEX);
	}
	
	/**
	 * Reads the z value of the given vector and returns it in the range -32768-32767.
	 * 
	 * @param vector vector to read from.
	 * @return vector's z value.
	 */
	public static short getZ(long vector) {
		return (short)((vector >> ZSHIFT) & HEX);
	}
	
	/**
	 * Sets the given value as the vectors x value and returns it.
	 * 
	 * @param vector vector to change.
	 * @param value value to set.
	 * @return vector with changed x value.
	 */
	public static long setX(long vector, long value) {
		long y = getY(vector), z = getZ(vector);
		return convert(value, y, z);
	}
	
	/**
	 * Sets the given value as the vectors y value and returns it.
	 * 
	 * @param vector vector to change.
	 * @param value value to set.
	 * @return vector with changed y value.
	 */
	public static long setY(long vector, long value) {
		long x = getX(vector), z = getZ(vector);
		return convert(x, value, z);
	}
	
	/**
	 * Sets the given value as the vectors z value and returns it.
	 * 
	 * @param vector vector to change.
	 * @param value value to set.
	 * @return vector with changed z value.
	 */
	public static long setZ(long vector, long value) {
		long x = getX(vector), y = getY(vector);
		return convert(x, y, value);
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
