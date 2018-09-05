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
	private static short[] valuesSin = {0, 4, 9, 13, 18, 22, 27, 31, 36, 40, 44, 49, 53, 58, 62, 66, 
			71, 75, 79, 83, 88, 92, 96, 100, 104, 108, 112, 116, 120, 124, 128, 
			132, 136, 139, 143, 147, 150, 154, 158, 161, 165, 168, 171, 175, 178, 181, 
			184, 187, 190, 193, 196, 199, 202, 204, 207, 210, 212, 215, 217, 219, 222, 
			224, 226, 228, 230, 232, 234, 236, 237, 239, 241, 242, 243, 245, 246, 247, 
			248, 249, 250, 251, 252, 253, 254, 254, 255, 255, 255, 256, 256, 256, 256, 
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
	public static final byte SHIFT = 8;
	
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
