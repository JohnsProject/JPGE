package com.johnsproject.jpge.graphics;

/**
 * The PixelShader class is used to used shade the pixels that are set to the {@link Camera}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class PixelShader {
	
	/**
	 * This method is called by the {@link Camera} when a pixel is set.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @param z position of pixel in the z axis.
	 * @param color color of pixel.
	 * @param width the width of the camera at the screen.
	 * @param height the height of the camera at the screen.
	 * @param viewBuffer buffer containing pixels of the {@link Camera}.
	 * @param zBuffer buffer containing depth of the pixels of the {@link Camera}.
	 */
	public void shadePixel (int x, int y, int z, int color, int width, int height, int[] viewBuffer, int[] zBuffer) {
		// check if pixel is inside camera
		if (x > 0 && x < width && y > 0 && y < height) {
			// calculate 1D array position of 2D xy position
			int pos = x + (y * width);
			// z test
			if (zBuffer[pos] > z) {
				zBuffer[pos] = z;
				viewBuffer[pos] = color;
			}
		}
	}
	
}
