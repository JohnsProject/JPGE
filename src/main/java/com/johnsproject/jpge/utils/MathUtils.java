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
	private static final short[] valuesSin = {0, 4, 9, 13, 18, 22, 27, 31, 36, 40, 44, 49, 53, 58, 62, 66, 
			71, 75, 79, 83, 88, 92, 96, 100, 104, 108, 112, 116, 120, 124, 128, 
			132, 136, 139, 143, 147, 150, 154, 158, 161, 165, 168, 171, 175, 178, 181, 
			184, 187, 190, 193, 196, 199, 202, 204, 207, 210, 212, 215, 217, 219, 222, 
			224, 226, 228, 230, 232, 234, 236, 237, 239, 241, 242, 243, 245, 246, 247, 
			248, 249, 250, 251, 252, 253, 254, 254, 255, 255, 255, 256, 256, 256, 256, 
			};
	// used by the power method
	private static final short[] highest_bit_set = {
	        0, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
	        5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
	        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 255, // anything past 63 is a guaranteed overflow with base > 1
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
	        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
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
		int a = wrap0to(angle, 360);
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
		int a = wrap0to(angle, 360);
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
		return angle != 89 ? ((sin(angle)<<SHIFT)/ cos(angle)) : 15000;
	}
	
	/**
	 * Returns the cotangent value of the given angle.
	 * To use it correctly do like this : <br>
	 * 
	 * <code> 
	 * <br> int i = 1;
	 * <br> int angle = 90;
	 * <br> i = (i * MathUtils.cot(angle)) >> MathUtils.SHIFT;
	 * </code>
	 * 
	 * @param angle angle to get tangent from.
	 * @return tangent of angle.
	 */
	public static int cot(int angle) {
		return angle != 0 ? tan(90-angle) : -1;
	}
	
	/**
	 * Returns the secant value of the given angle.
	 * To use it correctly do like this : <br>
	 * 
	 * <code> 
	 * <br> int i = 1;
	 * <br> int angle = 90;
	 * <br> i = (i * MathUtils.sec(angle)) >> MathUtils.SHIFT;
	 * </code>
	 * 
	 * @param angle angle to get tangent from.
	 * @return tangent of angle.
	 */
	public static int sec(int angle) {
		return angle != 89 ? ((256<<SHIFT)/ cos(angle)) : 15000;
	}
	
	/**
	 * Returns the cosecant value of the given angle.
	 * To use it correctly do like this : <br>
	 * 
	 * <code> 
	 * <br> int i = 1;
	 * <br> int angle = 90;
	 * <br> i = (i * MathUtils.cosec(angle)) >> MathUtils.SHIFT;
	 * </code>
	 * 
	 * @param angle angle to get tangent from.
	 * @return tangent of angle.
	 */
	public static int cosec(int angle) {
		return angle != 0 ? (angle != 1 ? ((256<<SHIFT)/ sin(angle)) : 15000) : -1;
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
	 * Returns the given value in the range min-max.
	 * 
	 * @param value value to wrap.
	 * @param min min value.
	 * @param max max value.
	 * @return value in the range min-max.
	 */
	public static int wrap(int value, int min, int max) {
		int range = max - min;
		return (min + ((((value - min) % range) + range) % range));
	}
	
	/**
	 * Returns the given value in the range 0-max.
	 * 
	 * @param value angle to wrap.
	 * @param max max value.
	 * @return value in a range from 0-max.
	 */
	public static int wrap0to(int value, int max) {
		int a = Math.abs(value);
		int i = 0;
		for (i = 0; i < a; i += max);
		if (a == 0)
			return 0;
		return Math.abs(a - i + max);
	}
	
	/**
	 * Returns the given value as min or max.
	 * if value < min return min.
	 * if value > max return max.
	 * 
	 * @param value value to clamp.
	 * @param min min value.
	 * @param max max value.
	 * @return clamped value.
	 */
	public static int clamp(int value, int min, int max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}

	static int r = 545;
	/**
	 * Returns a pseudo generated random value.
	 * 
	 * @return a pseudo generated random value.
	 */
	public static int random() {
		r += r + (r & r);
		return r;
	}
	
	/**
	 * Returns a pseudo generated random value in the range min-max.
	 * 
	 * @param min lowest random value.
	 * @param max highest random value.
	 * @return a pseudo generated random value.
	 */
	public static int random(int min, int max) {
		r += r + (r & r);
		return min + (r & (max - min));
	}
	
	/**
	 * Returns the square root of the given number.
	 * If number < 0 the method returns 0.
	 * 
	 * @param number number.
	 * @return square root of the given number.
	 */
	public static int sqrt(int number) {
		if (number < 0) return 0;
		if (number < 2) return number;
		int sc = sqrt(number >> 2) << 1;
		int lc = sc + 1;
		if (lc * lc > number) return sc;
		return lc;
	}
	
	/**
	 * Returns the power of the given number.
	 * 
	 * @param base base.
	 * @param exp exp.
	 * @return power of the given number.
	 */
	public static int pow(int base, int exp) {
	    int result = 1;
	    switch (highest_bit_set[exp]) {
	    case 255: // we use 255 as an overflow marker and return 0 on overflow/underflow
	        if (base == 1) return 1;
	        if (base == -1) return 1 - 2 * (exp & 1);
	        return 0;
	    case 6:
	        if ((exp & 1) != 0) result *= base;
	        exp >>= 1;
	        base *= base;
	    case 5:
	        if ((exp & 1) != 0) result *= base;
	        exp >>= 1;
	        base *= base;
	    case 4:
	        if ((exp & 1) != 0) result *= base;
	        exp >>= 1;
	        base *= base;
	    case 3:
	        if ((exp & 1) != 0) result *= base;
	        exp >>= 1;
	        base *= base;
	    case 2:
	        if ((exp & 1) != 0) result *= base;
	        exp >>= 1;
	        base *= base;
	    case 1:
	        if ((exp & 1) != 0) result *= base;
	    default:
	        return result;
	    }
	}
}
