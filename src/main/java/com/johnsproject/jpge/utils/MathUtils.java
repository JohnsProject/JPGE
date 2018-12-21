/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge.utils;

/**
 * The MathUtils class provides fast methods for math operations. 
 * Due to performance reasons the methods only use fixed point math.
 * 
 * @author John´s Project - John Salomon
 * 
 */
public class MathUtils {

	// sin table from 0-90 degrees
	private static byte[] sinLUT = {-128, -125, -120, -116, -111, -107, -102, -98, -93, -89, -85, -80, -76, -71, -67, -63, 
			-58, -54, -50, -46, -41, -37, -33, -29, -25, -21, -17, -13, -9, -5, -1, 
			3, 7, 10, 14, 18, 21, 25, 29, 32, 36, 39, 42, 46, 49, 52, 
			55, 58, 61, 64, 67, 70, 73, 75, 78, 81, 83, 86, 88, 90, 93, 
			95, 97, 99, 101, 103, 105, 107, 108, 110, 112, 113, 114, 116, 117, 118, 
			119, 120, 121, 122, 123, 124, 125, 125, 126, 126, 126, 127, 127, 127, 127, 
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
	private static final int HALF_SHIFT = (1 << (SHIFT-1)) + 1;
	
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
		a = (a - i) + 90;
		switch (quadrant) {
		case 1:
			if (angle > 0)
				return sinLUT[a] + HALF_SHIFT;
			return -(sinLUT[a] + HALF_SHIFT);
		case 2:
			if (angle > 0)
				return sinLUT[90 - a] + HALF_SHIFT;
			return -(sinLUT[90 - a] + HALF_SHIFT);
		case 3:
			if (angle > 0)
				return -(sinLUT[a] + HALF_SHIFT);
			return (sinLUT[a] + HALF_SHIFT);
		case 4:
			if (angle > 0)
				return -(sinLUT[90 - a] + HALF_SHIFT);
			return (sinLUT[90 - a] + HALF_SHIFT);
		}
		return 0;
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
		a = (a - i) + 90;
		switch (quadrant) {
		case 1:
			return sinLUT[90 - a] + HALF_SHIFT;
		case 2:
			return -(sinLUT[a] + HALF_SHIFT);
		case 3:
			return -(sinLUT[90 - a] + HALF_SHIFT);
		case 4:
			return (sinLUT[a] + HALF_SHIFT);
		}
		return 0;
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
	 * Returns the given value from min to max.
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

	private static int r = 545;
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
		int res = 0;
		int add = 0x8000;
		int i;
		for (i = 0; i < 16; i++) {
			int temp = res | add;
			int g2 = temp * temp;
			if (number >= g2) {
				res = temp;
			}
			add >>= 1;
		}
		return res;
	}
	
	/**
	 * Returns the power of the given number.
	 * 
	 * @param base base.
	 * @param exp exp.
	 * @return power of the given number.
	 */
	public static int pow(int base, int exp) {
		int result = base;
		for (int i = 1; i < exp; i++) {
			result *= base;
		}
		return result;
	}
}
