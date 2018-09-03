package com.johnsproject.jpge.utils;

/**
 * The MathUtils class provides fast methods for math operations. 
 * Due to performance reasons the results are less precise as of the default {@link Math} class.
 * Most of the methods are for trigonometric functions like getting sin, cos, Quadrant of angle.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 * 
 */
public class MathUtils {

	// sin table from 0-90 degrees
	private static byte[] valuesSin = {0, 2, 4, 6, 8, 11, 13, 15, 17, 20, 22, 24, 26, 28, 30, 33, 
			35, 37, 39, 41, 43, 45, 47, 50, 52, 54, 56, 58, 60, 62, 63, 
			65, 67, 69, 71, 73, 75, 77, 78, 80, 82, 83, 85, 87, 88, 90, 
			92, 93, 95, 96, 98, 99, 100, 102, 103, 104, 106, 107, 108, 109, 110, 
			111, 113, 114, 115, 116, 116, 117, 118, 119, 120, 121, 121, 122, 123, 123, 
			124, 124, 125, 125, 126, 126, 126, 127, 127, 127, 127, 127, 127, 127, 127, 
			};

	
	/**
	 * This value is used to normalize the values that needs to be modified by sin, cos.
	 * Just use like this : <br>
	 * 
	 * <code> 
	 * <br> int i = 1;
	 * <br> int angle = 90;
	 * <br> i = (i * MathUtils.sin(angle)) >> MathUtils.SHIFT;
	 * </code>
	 */
	public static final byte SHIFT = 7;
	
	/**
	 * Returns the sine value of the given angle.
	 * The sine will be in a range from -999 to 999.
	 * To use it correctly do like this : <br>
	 * 
	 * <code> 
	 * <br> int i = 1;
	 * <br> int angle = 90;
	 * <br> i = (i * MathUtils.sin(angle)) >> MathUtils.SHIFT;
	 * </code>
	 * 
	 * @param angle angle to get sine from.
	 * @return sine of angle.
	 */
	public static int sin(int angle) {
		int a = clamp0to360(angle);
		int result = 0;
		if (a <= 90)
			result = valuesSin[a];
		else if (a > 90 && a <= 180)
			result = valuesSin[180 - a];
		else if (a > 180 && a <= 270)
			result = -valuesSin[a - 180];
		else if (a > 270 && a <= 360)
			result = -valuesSin[360 - a];
		if (angle < 0) return -result;
		return result;
	}

	/**
	 * Returns the cosine value of the given angle.
	 * The cosine will be in a range from -999 to 999.
	 * To use it correctly do like this : <br>
	 * 
	 * <code> 
	 * <br> int i = 1;
	 * <br> int angle = 90;
	 * <br> i = (i * MathUtils.cos(angle)) >> MathUtils.SHIFT;
	 * </code>
	 * 
	 * @param angle angle to get cosine from.
	 * @return cosine of angle.
	 */
	public static int cos(int angle) {
		int a = clamp0to360(angle);
		int result = 0;
		if (a <= 90)
			result = valuesSin[90 - a];
		else if (a > 90 && a <= 180)
			result = -valuesSin[a - 90];
		else if (a > 180 && a <= 270)
			result = -valuesSin[270 - a];
		else if (a > 270 && a <= 360)
			result = valuesSin[a - 270];
		return result;
	}
	
	/**
	 * Returns the tangent value of the given angle.
	 * To use it correctly do like this : <br>
	 * 
	 * <code> 
	 * <br> int i = 1;
	 * <br> int angle = 90;
	 * <br> i = (i * MathUtils.tan(angle)) >> MathUtils.SHIFT;
	 * </code>
	 * 
	 * @param angle angle to get tangent from.
	 * @return tangent of angle.
	 */
	public static int tan(int angle) {
		return (sin(angle)<<SHIFT)/ cos(angle);
	}

	/**
	 * Checks if the given angle is in the 1, 2, 3 or 4 quadrant of the given angle and returs it.
	 * 
	 * 
	 * @param angle angle to get quadrant from.
	 * @return the quadrant of given angle.
	 */
	public static int getQuadrant(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		int quadrant = 0;
		while (a >= i) {
			i += 90;
			quadrant++;
			if (quadrant > 4) {
				quadrant = 1;
			}
		}
		return quadrant;
	}

	
	/**
	 * Returns the given angle in a range from 0-360.
	 * The method does this:
	 * 
	 * <br> <code> 
	 * <br> for (i = 0; i < Math.abs(angle); i += 360);
	 * <br> </code> <br>
	 * 
	 * And then subtracts i from the angle and returns it.
	 * 
	 * @param angle angle to clamp.
	 * @return angle in a range from 0-360.
	 */
	public static int clamp0to360(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 360);
		if (a == 0)
			return 0;
		return Math.abs(a - i + 360);
	}

	/**
	 * Returns the given angle in a range from 0-270.
	 * The method does this:
	 * 
	 * <br> <code> 
	 * <br> for (i = 0; i < Math.abs(angle); i += 270);
	 * <br> </code> <br>
	 * 
	 * And then subtracts i from the angle and returns it.
	 * 
	 * @param angle angle to clamp.
	 * @return angle in a range from 0-270.
	 */
	public static int clamp0to270(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 270);
		if (a == 0)
			return 0;
		return Math.abs(a - i + 270);
	}

	/**
	 * Returns the given angle in a range from 0-180.
	 * The method does this:
	 * 
	 * <br> <code> 
	 * <br> for (i = 0; i < Math.abs(angle); i += 180);
	 * <br> </code> <br>
	 * 
	 * And then subtracts i from the angle and returns it.
	 * 
	 * @param angle angle to clamp.
	 * @return angle in a range from 0-180.
	 */
	public static int clamp0to180(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 180);
		if (a == 0)
			return 0;
		return Math.abs(a - i + 180);
	}

	/**
	 * Returns the given angle in a range from 0-90.
	 * The method does this:
	 * 
	 * <br> <code> 
	 * <br> for (i = 0; i < Math.abs(angle); i += 90);
	 * <br> </code> <br>
	 * 
	 * And then subtracts i from the angle and returns it.
	 * 
	 * @param angle angle to clamp.
	 * @return angle in a range from 0-90.
	 */
	public static int clamp0to90(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 90);
		if (a == 0)
			return 0;
		return Math.abs(a - i + 90);
	}

	private static int r = 541648465;
	/**
	 * Returns a random value.
	 * 
	 * @return a random value.
	 */
	public static int random() {
		int x = r + (int) System.nanoTime();
		r *= x >> 1;
		return (int) x;
	}
}
