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

	private static short[] valuesSin = {
			01, 17, 34, 52, 69, 87, 104, 121, 139, 156, 173, 190, 207, 224, 241, 258, 
			275, 292, 309, 325, 342, 358, 374, 390, 406, 422, 438, 453, 469, 484, 499, 
			515, 529, 544, 559, 573, 587, 601, 615, 629, 642, 656, 669, 681, 694, 707, 
			719, 731, 743, 754, 766, 777, 788, 798, 809, 819, 829, 838, 848, 857, 866, 
			874, 882, 891, 898, 906, 913, 920, 927, 933, 939, 945, 951, 956, 961, 965, 
			970, 974, 978, 981, 984, 987, 990, 992, 994, 996, 997, 998, 999, 999, 1000 
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
	public static final byte SHIFT = 10;

	
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
		if (angle < 0)
			return -result;
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
		return (sin(angle) << SHIFT)/ cos(angle);
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
