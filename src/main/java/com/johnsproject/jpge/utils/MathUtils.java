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

	private static short[] valuesSin = {0, 17, 35, 53, 71, 89, 107, 124, 142, 160, 177, 195, 212, 230, 247, 265, 
			282, 299, 316, 333, 350, 366, 383, 400, 416, 432, 448, 464, 480, 496, 511, 
			527, 542, 557, 572, 587, 601, 616, 630, 644, 658, 671, 685, 698, 711, 724, 
			736, 748, 760, 772, 784, 795, 806, 817, 828, 838, 848, 858, 868, 877, 886, 
			895, 904, 912, 920, 928, 935, 942, 949, 955, 962, 968, 973, 979, 984, 989, 
			993, 997, 1001, 1005, 1008, 1011, 1014, 1016, 1018, 1020, 1021, 1022, 1023, 1023, 1024, 
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
		return (sin(angle) * 1000)/ cos(angle);
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
