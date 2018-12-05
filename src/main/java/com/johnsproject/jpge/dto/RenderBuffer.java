package com.johnsproject.jpge.dto;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.johnsproject.jpge.Engine;
import com.johnsproject.jpge.graphics.Renderer;
import com.johnsproject.jpge.graphics.SceneWindow;

/**
 * The RenderBuffer class contains a frameBuffer and a depthBuffer.
 * The {@link Renderer} draws on it at the rendering process and after that 
 * this buffer is drawn on the {@link SceneWindow} used by the {@link Engine} if available.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class RenderBuffer implements Externalizable{
	
	private static final long serialVersionUID = 8349642103859979367L;
	
	private int width;
	private int height;
	private int length;
	private BufferedImage frameBuffer;
	private int[] frameBufferData;
	private int[] depthBuffer;
	
	public RenderBuffer() {
		setSize(1, 1);
	}
	
	/**
	 * Creates a RenderBuffer with the given size.
	 * 
	 * @param width width to set.
	 * @param height height to set.
	 */
	public RenderBuffer(int width, int height) {
		setSize(width, height);
	}
	
	/**
	 * Returns the {@link BufferedImage frameBuffer} of this RenderBuffer.
	 * 
	 * @return {@link BufferedImage frameBuffer} of this RenderBuffer.
	 */
	public BufferedImage getFrameBuffer() {
		return frameBuffer;
	}
	
	/**
	 * Returns the depthBuffer of this RenderBuffer.
	 * The DepthBuffer contains the depth value of all pixels in the frameBuffer.
	 * 
	 * @return depthBuffer of this RenderBuffer.
	 */
	public int[] getDepthBuffer() {
		return depthBuffer;
	}
	
	/**
	 * Tells this RenderBuffer to clear its frameBuffer.
	 */
	public void clearFrameBuffer() {
		for (int i = 0; i < frameBufferData.length; i++) {
			frameBufferData[i] = 0;
		}
	}
	
	/**
	 * Tells this RenderBuffer to clear its depthBuffer.
	 */
	public void clearDepthBuffer() {
		for (int i = 0; i < depthBuffer.length; i++) {
			depthBuffer[i] = Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Resizes this RenderBuffer.
	 * 
	 * @param width width to set.
	 * @param height height to set.
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.length = width * height;
		this.frameBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		this.frameBufferData = ((DataBufferInt)frameBuffer.getRaster().getDataBuffer()).getData();
		this.depthBuffer = new int[length];
	}
	
	/**
	 * Sets a pixel of this RenderBuffer at the given position, but only if it passes the depth test.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @param z position of pixel in the z axis.
	 * @param color color of pixel.
	 */
	public void setPixel(int x, int y, int z, int color) {
		int pos = x + (y * width);
		// check if pixel is inside RenderBuffer
		if (pos < length) {
			// z test
			if (depthBuffer[pos] > z) {
				depthBuffer[pos] = z;
				frameBufferData[pos] = color;
			}
		}
	}
	
	/**
	 * Returns the color of the pixel at the given coordinates.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @return color of the pixel at the given coordinates.
	 */
	public int getPixel(int x, int y) {
		int pos = x + (y * width);
		if (pos < length) 
			return frameBufferData[pos];
		return -1;
	}
	
	/**
	 * Returns the depth of the pixel at the given coordinates.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @return depth of the pixel at the given coordinates.
	 */
	public int getPixelDepth(int x, int y) {
		int pos = x + (y * width);
		if (pos < length) 
			return depthBuffer[pos];
		return -1;
	}
	
	/**
	 * Returns the width of this RenderBuffer.
	 * 
	 * @return width of this RenderBuffer.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of this RenderBuffer.
	 * 
	 * @return height of this RenderBuffer.
	 */
	public int getHeight() {
		return height;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(width);
		out.writeInt(height);
		for (int i = 0; i < length; i++) {
			out.writeInt(frameBufferData[i]);
		}
		for (int i = 0; i < length; i++) {
			out.writeInt(depthBuffer[i]);
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		final int width = in.readInt();
		final int height = in.readInt();
		setSize(width, height);
		for (int i = 0; i < length; i++) {
			frameBufferData[i] = in.readInt();
		}
		for (int i = 0; i < length; i++) {
			depthBuffer[i] = in.readInt();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(depthBuffer);
		result = prime * result + ((frameBuffer == null) ? 0 : frameBuffer.hashCode());
		result = prime * result + Arrays.hashCode(frameBufferData);
		result = prime * result + height;
		result = prime * result + length;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenderBuffer other = (RenderBuffer) obj;
		if (!Arrays.equals(depthBuffer, other.depthBuffer))
			return false;
		if (!Arrays.equals(frameBufferData, other.frameBufferData))
			return false;
		if (height != other.height)
			return false;
		if (length != other.length)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
}
