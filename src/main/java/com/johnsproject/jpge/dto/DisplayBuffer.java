package com.johnsproject.jpge.dto;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.johnsproject.jpge.Engine;
import com.johnsproject.jpge.graphics.Renderer;
import com.johnsproject.jpge.graphics.SceneWindow;

/**
 * The DisplayBuffer class contains a frameBuffer and a depthBuffer.
 * The {@link Renderer} draws on it at the rendering process and after that 
 * this buffer is drawn on the {@link SceneWindow} used by the {@link Engine} if available.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class DisplayBuffer {
	
	private int width;
	private int height;
	private BufferedImage frameBuffer;
	private int[] frameBufferData;
	private int[] depthBuffer;
	
	/**
	 * Creates a display buffer with the given size.
	 * 
	 * @param width width to set.
	 * @param height height to set.
	 */
	public DisplayBuffer(int width, int height) {
		setSize(width, height);
	}
	
	/**
	 * Returns the {@link BufferedImage frameBuffer} of this DisplayBuffer.
	 * 
	 * @return {@link BufferedImage frameBuffer} of this DisplayBuffer.
	 */
	public BufferedImage getFrameBuffer() {
		return frameBuffer;
	}
	
	/**
	 * Returns the pixel data of the {@link BufferedImage frameBuffer} of this DisplayBuffer.
	 * 
	 * @return pixel data of the {@link BufferedImage frameBuffer} of this DisplayBuffer.
	 */
	public int[] getFrameBufferData() {
		return frameBufferData;
	}
	
	/**
	 * Returns the depthBuffer of this DisplayBuffer.
	 * The DepthBuffer contains the depth value of all pixels in the frameBuffer.
	 * 
	 * @return depthBuffer of this DisplayBuffer.
	 */
	public int[] getDepthBuffer() {
		return depthBuffer;
	}
	
	/**
	 * Tells this DisplayBuffer to clear its frameBuffer.
	 */
	public void clearFrameBuffer() {
		for (int i = 0; i < frameBufferData.length; i++) {
			frameBufferData[i] = 0;
		}
	}
	
	/**
	 * Tells this DisplayBuffer to clear its depthBuffer.
	 */
	public void clearDepthBuffer() {
		for (int i = 0; i < depthBuffer.length; i++) {
			depthBuffer[i] = Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Resizes this DisplayBuffer.
	 * 
	 * @param width width to set.
	 * @param height height to set.
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.frameBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		this.frameBufferData = ((DataBufferInt)frameBuffer.getRaster().getDataBuffer()).getData();
		this.depthBuffer = new int[width*height];
	}
	
	/**
	 * Returns the width of this DisplayBuffer.
	 * 
	 * @return width of this DisplayBuffer.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of this DisplayBuffer.
	 * 
	 * @return height of this DisplayBuffer.
	 */
	public int getHeight() {
		return height;
	}
}
