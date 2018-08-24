package com.johnsproject.jpge.utils;

/**
 * The MathUtils class provides useful functionalities for handling math
 * problems. <br>
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 * 
 */
public class MathUtils {
	public static short[] valuesSin = { 00, 17, 34, 52, 69, 87, 104, 121, 139,
			156, 173, 190, 207, 224, 241, 258, 275, 292, 309, 325, 342, 358,
			374, 390, 406, 422, 438, 453, 469, 484, 500, 515, 529, 544, 559,
			573, 587, 601, 615, 629, 642, 656, 669, 681, 694, 707, 719, 731,
			743, 754, 766, 777, 788, 798, 809, 819, 829, 838, 848, 857, 866,
			874, 882, 891, 898, 906, 913, 920, 927, 933, 939, 945, 951, 956,
			961, 965, 970, 974, 978, 981, 984, 987, 990, 992, 994, 996, 997,
			998, 999, 999, 999 };

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

	public static int getQuadrant(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		int quadrant = 1;
		while (a >= i) {
			i += 90;
			quadrant++;
			if (quadrant > 4) {
				quadrant = 1;
			}
		}
		return quadrant;
	}

	public static int clamp0to360(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 360)
			;
		if (a == 0)
			return 0;
		return Math.abs(a - i + 360);
	}

	public static int clamp0to270(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 270)
			;
		if (a == 0)
			return 0;
		return Math.abs(a - i + 270);
	}

	public static int clamp0to180(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 180)
			;
		if (a == 0)
			return 0;
		return Math.abs(a - i + 180);
	}

	public static int clamp0to90(int angle) {
		int a = Math.abs(angle);
		int i = 0;
		for (i = 0; i < a; i += 90)
			;
		if (a == 0)
			return 0;
		return Math.abs(a - i + 90);
	}

	private static int r = 541648465;
	public static int random() {
		int x = r + (int) System.nanoTime();
		r *= x >> 1;
		return (int) x;
	}
}
