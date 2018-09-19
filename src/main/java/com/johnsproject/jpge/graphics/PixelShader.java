package com.johnsproject.jpge.graphics;

import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The PixelShader class is used to used shade the pixels that are set to the {@link Camera}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class PixelShader {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	/**
	 * This method is called by the {@link Camera} when a pixel is set.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @param color color of pixel.
	 * @param cameraWidth the width of the camera at the screen, used when to calculate pixel position in the viewBuffer.
	 * @param viewBuffer buffer containing pixels of the {@link Camera}.
	 */
	public void shadePixel (int x, int y, int color, int cameraWidth, int[] viewBuffer) {
		setPixel(x, y, color, cameraWidth, viewBuffer);
	}
	
	/**
	 * Sets the color of the pixel at the given position in the viewBuffer equals to the given color.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @param color color of pixel.
	 * @param cameraWidth the width of the camera at the screen, used when to calculate pixel position in the viewBuffer.
	 * @param viewBuffer buffer containing pixels of the {@link Camera}.
	 */
	public void setPixel(int x, int y, int color, int cameraWidth, int[] viewBuffer) {
		int pos = x + (y * cameraWidth);
		if (pos >= 0 && pos < viewBuffer.length) viewBuffer[pos] = color;
	}
	
	/**
	 * Returns the color of the pixel at the given position in the viewBuffer.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @param cameraWidth the width of the camera at the screen, used when to calculate pixel position in the viewBuffer.
	 * @param viewBuffer buffer containing pixels of the {@link Camera}.
	 * @return color of a pixel of the viewBuffer at the given position.
	 */
	public int getPixel(int x, int y, int cameraWidth, int[] viewBuffer) {
		int pos = x + (y * cameraWidth);
		if (pos >= 0 && pos < viewBuffer.length) return viewBuffer[pos];
		return 0;
	}
	
}
