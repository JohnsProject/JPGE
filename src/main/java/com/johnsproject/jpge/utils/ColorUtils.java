package com.johnsproject.jpge.utils;

/**
 * The ColorUtils class provides useful functionalities for handling colors.
 * <br>
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class ColorUtils {

	private static final int MAXFACTOR = 5;
	private static final int MAXVALUE = MAXFACTOR + 1;
	private static final byte GREENSHIFT = 8;
	private static final byte REDSHIFT = 16;
	private static final byte ALPHASHIFT = 24;
	private static final int HEX = 0xFF;

	/**
	 * Converts the rgba values to the java awt int color value and returns it.
	 * 
	 * @param r the red value of the color.
	 * @param g the green value of the color.
	 * @param b the blue value of the color.
	 * @param a the aplha value of the color.
	 * @return rgba values as an int.
	 */
	public static int convert(int r, int g, int b, int a) {
		int color = 0;
		color |= (a & HEX) << ALPHASHIFT;
		color |= (r & HEX) << REDSHIFT;
		color |= (g & HEX) << GREENSHIFT;
		color |= (b & HEX);
		return color;
	}

	/**
	 * Converts the rgb values to the java awt int color value and returns it.
	 * 
	 * @param r the red value of the color.
	 * @param g the green value of the color.
	 * @param b the blue value of the color.
	 * @return rgb values as an int.
	 */
	public static int convert(int r, int g, int b) {
		int color = 0;
		color |= (255 & HEX) << ALPHASHIFT;
		color |= (r & HEX) << REDSHIFT;
		color |= (g & HEX) << GREENSHIFT;
		color |= (b & HEX);
		return color;
	}

	/**
	 * Reads the blue value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return blue value of int color.
	 */
	public static int getBlue(int color) {
		return (color) & HEX;
	}

	/**
	 * Reads the green value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return green value of int color.
	 */
	public static int getGreen(int color) {
		return (color >> GREENSHIFT) & HEX;
	}

	/**
	 * Reads the red value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return red value of int color.
	 */
	public static int getRed(int color) {
		return (color >> REDSHIFT) & HEX;
	}

	/**
	 * Reads the alpha value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return alpha value of int color.
	 */
	public static int getAlpha(int color) {
		return (color >> ALPHASHIFT) & HEX;
	}

	/**
	 * Sets the blue value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @param value the blue value to set.
	 * @return the modified color.
	 */
	public static int setBlue(int color, int value) {
		return (color << GREENSHIFT) | (value & HEX);
	}

	/**
	 * Sets the green value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @param value the green value to set.
	 * @return the modified color.
	 */
	public static int setGreen(int color, int value) {
		return (color << REDSHIFT) | ((value & HEX) << GREENSHIFT);
	}

	/**
	 * Sets the red value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @param value the red value to set.
	 * @return the modified color.
	 */
	public static int setRed(int color, int value) {
		return (color << ALPHASHIFT) | ((value & HEX) << REDSHIFT);
	}

	/**
	 * Sets the alpha value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @param value the alpha value to set.
	 * @return the modified color.
	 */
	public static int setAlpha(int color, int value) {
		return (color << 31) | ((value & HEX) << ALPHASHIFT);
	}

	/**
	 * Adds the given value to the blue value of the java awt int color and returns
	 * it.
	 * 
	 * @param color the color to read from.
	 * @param value the blue value.
	 * @return the modified color.
	 */
	public static int addBlue(int color, int value) {
		return (color << GREENSHIFT) | (getBlue(color) + (value & HEX));
	}

	/**
	 * Adds the given value to the green value of the java awt int color and returns
	 * it.
	 * 
	 * @param color the color to read from.
	 * @param value the green value.
	 * @return the modified color.
	 */
	public static int addGreen(int color, int value) {
		return (color << REDSHIFT) | (getGreen(color) + (value & HEX) << GREENSHIFT);
	}

	/**
	 * Adds the given value to the red value of the java awt int color and returns
	 * it.
	 * 
	 * @param color the color to read from.
	 * @param value the red value.
	 * @return the modified color.
	 */
	public static int addRed(int color, int value) {
		return (color << ALPHASHIFT) | (getRed(color) + (value & HEX) << REDSHIFT);
	}

	/**
	 * Adds the given value to the alpha value of the java awt int color and returns
	 * it.
	 * 
	 * @param color the color to read from.
	 * @param value the alpha value.
	 * @return the modified color.
	 */
	public static int addAlpha(int color, int value) {
		return (color << 31) | (getAlpha(color) + (value & HEX) << ALPHASHIFT);
	}

	/**
	 * Returns a darker version of the color. The factor should be in the range
	 * 0-{@value #MAXFACTOR}, if its > {@value #MAXFACTOR} this method will use
	 * {@value #MAXFACTOR}.
	 * 
	 * @param color  the color to darken.
	 * @param factor the darken factor.
	 * @return darker version of color.
	 */
	public static int darker(int color, int factor) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		if (factor <= 0) factor = 0;
		if (factor > MAXFACTOR)
			factor = MAXFACTOR;
		r -= (r >> (MAXVALUE - factor));
		g -= (g >> (MAXVALUE - factor));
		b -= (b >> (MAXVALUE - factor));
		return convert(r, g, b, a);
	}

	/**
	 * Returns a brighter version of the color. The factor should be in the range
	 * 0-{@value #MAXFACTOR}, if its > {@value #MAXFACTOR} this method will use
	 * {@value #MAXFACTOR}.
	 * 
	 * @param color  the color to darken.
	 * @param factor the brighten factor.
	 * @return brighter version of color.
	 */
	public static int brighter(int color, int factor) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		if (factor < 0) factor = 0;
		if (factor > MAXFACTOR)
			factor = MAXFACTOR;
		r += (r >> (MAXVALUE - factor));
		g += (g >> (MAXVALUE - factor));
		b += (b >> (MAXVALUE - factor));
		return convert(r, g, b, a);
	}
	
	/**
	 * Returns a color that is the result of the linear interpolation 
	 * between color1 and color2 by the given factor.
	 * Only the red, blue and green components are interpolated.
	 * The factor should be in the range 0-255.
	 * 
	 * @param color1 color to interpolate.
	 * @param color2 color to interpolate.
	 * @param factor interpolation factor.
	 * @return a color that is the result of the linear interpolation.
	 */
	public static int lerpRBG(int color1, int color2, int factor) {
		int r = 0, g = 0, b = 0;
		int r1 = getRed(color1), g1 = getGreen(color1), b1 = getBlue(color1), a1 = getAlpha(color1);
		int r2 = getRed(color2), g2 = getGreen(color2), b2 = getBlue(color2);
		factor = MathUtils.clamp(factor, -255, 255);
		r = MathUtils.clamp((r1 + ((r2 - r1) * factor)>>8), 0, 255);
		g = MathUtils.clamp((g1 + ((g2 - g1) * factor)>>8), 0, 255);
		b = MathUtils.clamp((b1 + ((b2 - b1) * factor)>>8), 0, 255);
		return convert(r, g, b, a1);
	}
	
	/**
	 * Returns a color that is the result of the linear interpolation 
	 * between color1 and color2 by the given factor.
	 * The factor should be in the range 0-255.
	 * 
	 * @param color1 color to interpolate.
	 * @param color2 color to interpolate.
	 * @param factor interpolation factor.
	 * @return a color that is the result of the linear interpolation.
	 */
	public static int lerp(int color1, int color2, int factor) {
		int r = 0, g = 0, b = 0, a = 0;
		int r1 = getRed(color1), g1 = getGreen(color1), b1 = getBlue(color1), a1 = getAlpha(color1);
		int r2 = getRed(color2), g2 = getGreen(color2), b2 = getBlue(color2), a2 = getAlpha(color2);
		factor = MathUtils.clamp(factor, -255, 255);
		r = MathUtils.clamp((r1 + ((r2 - r1) * factor)>>8), 0, 255);
		g = MathUtils.clamp((g1 + ((g2 - g1) * factor)>>8), 0, 255);
		b = MathUtils.clamp((b1 + ((b2 - b1) * factor)>>8), 0, 255);
		a = MathUtils.clamp((a1 + ((a2 - a1) * factor)>>8), 0, 255);
		return convert(r, g, b, a);
	}

//	public static int blendAlpha(int color1, int color2) {
//		int r = 0, g = 0, b = 0, a = 0;
//		int r1 = getRed(color1), g1 = getGreen(color1), b1 = getBlue(color1), a1 = getAlpha(color1);
//		int r2 = getRed(color2), g2 = getGreen(color2), b2 = getBlue(color2), a2 = getAlpha(color2);
//		int alpha = a1 + 1;
//		int inv_alpha = 256 - a1;
//		r = ((alpha * r1 + inv_alpha * r2) >> 8);
//		g = ((alpha * g1 + inv_alpha * g2) >> 8);
//		b = ((alpha * b1 + inv_alpha * b2) >> 8);
////		r = a1 * r1 + (1 - a1) * r2;
////		g = a1 * g1 + (1 - a1) * g2;
////		b = a1 * b1 + (1 - a1) * b2;
////		a = a1 + (1 - a1) * a2;
////		r = (r2 - a1) + r1;
////		g = (g2 - a1) + g1;
////		b = (b2 - a1) + b1;
////		a = a2 - a1;
////		System.out.println("r1 " + r1 + ", g1 " + g1 + ", b1 " + b1);
//		//System.out.println("r " + r + ", g " + g + ", b " + b);
//		return ColorUtils.convert(r, g, b, 255);
//	}
}
