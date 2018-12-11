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
 * The ColorUtils class provides useful functionalities for handling colors.
 * <br>
 * 
 * @author John´s Project - John Salomon
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
		color |= MathUtils.clamp(a, 0, 255) << ALPHASHIFT;
		color |= MathUtils.clamp(r, 0, 255) << REDSHIFT;
		color |= MathUtils.clamp(g, 0, 255) << GREENSHIFT;
		color |= MathUtils.clamp(b, 0, 255);
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
		color |= (255) << ALPHASHIFT;
		color |= MathUtils.clamp(r, 0, 255) << REDSHIFT;
		color |= MathUtils.clamp(g, 0, 255) << GREENSHIFT;
		color |= MathUtils.clamp(b, 0, 255);
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
		int r = getRed(color), g = getGreen(color), a = getAlpha(color);
		return convert(r, g, value, a);
	}

	/**
	 * Sets the green value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @param value the green value to set.
	 * @return the modified color.
	 */
	public static int setGreen(int color, int value) {
		int r = getRed(color), b = getBlue(color), a = getAlpha(color);
		return convert(r, value, b, a);
	}

	/**
	 * Sets the red value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @param value the red value to set.
	 * @return the modified color.
	 */
	public static int setRed(int color, int value) {
		int g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(value, g, b, a);
	}

	/**
	 * Sets the alpha value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @param value the alpha value to set.
	 * @return the modified color.
	 */
	public static int setAlpha(int color, int value) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color);
		return convert(r, g, b, value);
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
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r, g, b + value, a);
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
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r, g + value, b, a);
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
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r + value, g, b, a);
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
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r, g, b, a + value);
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
	 * The factor should be in the range -255-255.
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
		r = (r1 + ((r2 - r1) * factor)>>8);
		g = (g1 + ((g2 - g1) * factor)>>8);
		b = (b1 + ((b2 - b1) * factor)>>8);
		return convert(r, g, b, a1);
	}
	
	/**
	 * Returns a color that is the result of the linear interpolation 
	 * between color1 and color2 by the given factor.
	 * The factor should be in the range -255-255.
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
		r = (r1 + ((r2 - r1) * factor)>>8);
		g = (g1 + ((g2 - g1) * factor)>>8);
		b = (b1 + ((b2 - b1) * factor)>>8);
		a = (a1 + ((a2 - a1) * factor)>>8);
		return convert(r, g, b, a);
	}
	
	public static String toString(int color) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return "(" + r + ", " + g + ", " + b + ", " + a + ")";
	}
}
