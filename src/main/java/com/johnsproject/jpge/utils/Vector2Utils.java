package com.johnsproject.jpge.utils;

/**
 * The Vector2Utils class provides methods for generating and reading 2D vectors.
 * This 2D vectors can be used as 2D position, rotation and scale.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Vector2Utils {

	private static final int YSHIFT = 16;
	private static final int HEX = 0xFFFF;
	
	/**
	 * Generates a vector based on the given values and returns it. 
	 * The vector can be used as position, rotation or scale vector.
	 * (Bits 0-15 are x, 16-31 are y)
	 * 
	 * @param x vector's x value.
	 * @param y vector's y value.
	 * @return vector generated from given values.
	 */
	public static int convert(int x, int y) {
		return ((y & HEX)<<YSHIFT)|(x & HEX);
	}
	
	/**
	 * Reads the x value of the given vector and returns it in the range -32768-32767.
	 * 
	 * @param vector vector to read from.
	 * @return vector's x value.
	 */
	public static int getX(int vector) {
		return (short)(vector & HEX);
	}
	
	/**
	 * Reads the y value of the given vector and returns it in the range -32768-32767.
	 * 
	 * @param vector vector to read from.
	 * @return vector's y value.
	 */
	public static int getY(int vector) {
		return (short)((vector >> YSHIFT) & HEX);
	}
	
	/**
	 * Sets the given value as the vectors x value and returns it.
	 * 
	 * @param vector vector to change.
	 * @param value value to set.
	 * @return vector with changed x value.
	 */
	public static int setX(int vector, int value) {
		return convert(value, getY(vector));
	}
	
	/**
	 * Sets the given value as the vectors y value and returns it.
	 * 
	 * @param vector vector to change.
	 * @param value value to set.
	 * @return vector with changed y value.
	 */
	public static int setY(int vector, int value) {
		return convert(getX(vector), value);
	}
	
	/**
	 * Returns a string containing the data of the given vector.
	 * 
	 * @param vector vector.
	 * @return Vector as string.
	 */
	public static String toString(int vector) {
		int vx = getX(vector), vy = getY(vector);
		return "Vector (" + vx + ", " + vy + ")";
	}
	
}
