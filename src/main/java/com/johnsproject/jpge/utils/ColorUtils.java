package com.johnsproject.jpge.utils;

/**
 * The ColorUtils class provides useful functionalities for handling colors. <br>
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class ColorUtils {
	
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
		return ((a&0xFF)<<24)|((r&0xFF)<<16)|((g&0xFF)<<8)|((b&0xFF));
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
		return ((255&0xFF)<<24)|((r&0xFF)<<16)|((g&0xFF)<<8)|((b&0xFF));
	}
	
	/**
	 * Reads the blue value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return blue value of int color.
	 */
	public static int getBlue(int color) {
		return (color) & 0xFF;
	}
	
	/**
	 * Reads the green value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return green value of int color.
	 */
	public static int getGreen(int color) {
		return (color>>8) & 0xFF;
	}
	
	/**
	 * Reads the red value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return red value of int color.
	 */
	public static int getRed(int color) {
		return (color>>16) & 0xFF;
	}
	
	/**
	 * Reads the alpha value of the java awt int color and returns it.
	 * 
	 * @param color the color to read from.
	 * @return alpha value of int color.
	 */
	public static int getAlpha(int color) {
		return (color>>24) & 0xFF;
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
	 * Changes the blue value of the java awt int color and returns it.
	 * If value > 0 then the blue value will be added and if value < 0 
	 * it will be subtracted.
	 * 
	 * @param color the color to read from.
	 * @param value the blue value.
	 * @return the modified color.
	 */
	public static int changeBlue(int color, int value) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r, g, b + value, a);
	}
	
	/**
	 * Changes the green value of the java awt int color and returns it.
	 * If value > 0 then the green value will be added and if value < 0 
	 * it will be subtracted.
	 * 
	 * @param color the color to read from.
	 * @param value the green value.
	 * @return the modified color.
	 */
	public static int changeGreen(int color, int value) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r, g + value, b, a);
	}
	
	/**
	 * Changes the red value of the java awt int color and returns it.
	 * If value > 0 then the red value will be added and if value < 0 
	 * it will be subtracted.
	 * 
	 * @param color the color to read from.
	 * @param value the red value.
	 * @return the modified color.
	 */
	public static int changeRed(int color, int value) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r + value, g, b, a);
	}
	
	/**
	 * Changes the alpha value of the java awt int color and returns it.
	 * If value > 0 then the alpha value will be added and if value < 0 
	 * it will be subtracted.
	 * 
	 * @param color the color to read from.
	 * @param value the alpha value.
	 * @return the modified color.
	 */
	public static int changeAlpha(int color, int value) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		return convert(r, g, b, a + value);
	}
	
	/**
	 * Returns a darker version of the color.
	 * 
	 * @param color the color to darken.
	 * @param factor the darken factor.
	 * @return darker version of color.
	 */
	public static int darker(int color, int factor) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		if (factor > 6) factor = 6;
		r -= (r >> (5-factor));
		g -= (g >> (5-factor));
		b -= (b >> (5-factor));
		return convert(r, g, b, a);
	}
	
	/**
	 * Returns a brighter version of the color.
	 * 
	 * @param color the color to darken.
	 * @param factor the darken factor.
	 * @return brighter version of color.
	 */
	public static int brighter(int color, int factor) {
		int r = getRed(color), g = getGreen(color), b = getBlue(color), a = getAlpha(color);
		if (factor > 6) factor = 6;
		r += (r >> (5-factor));
		g += (g >> (5-factor));
		b += (b >> (5-factor));
		return convert(r, g, b, a);
	}
}
