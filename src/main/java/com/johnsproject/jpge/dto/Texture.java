package com.johnsproject.jpge.dto;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.johnsproject.jpge.io.FileIO;
import com.johnsproject.jpge.utils.MathUtils;

/**
 * The Texture class contains data of a texture used by the {@link Material} class.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Texture implements Serializable{

	private static final long serialVersionUID = -1930284191151225776L;
	
	private int[] image = null;
	private int width = 0, height = 0;
	
	/**
	 * Creates a new empty instance of the Texture class with the given width and height.
	 * 
	 * @param width width of this texture.
	 * @param height height of this texture.
	 */
	public Texture (int width, int height){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	/**
	 * Creates a new instance of the Texture class with data of the image loaded at the given path.
	 * 
	 * @param path path of image to load.
	 * @throws IOException
	 */
	public Texture (String path) throws IOException{
		BufferedImage img = FileIO.loadImage(path);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	/**
	 * Creates a new instance of the Texture class with data of the resized image loaded at the given path.
	 * 
	 * @param path path of image to load.
	 * @param width width of this texture.
	 * @param height height of this texture.
	 * @throws IOException
	 */
	public Texture (String path, int width, int height) throws IOException{
		BufferedImage img = FileIO.loadImage(path, width, height);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	/**
	 * Creates a new instance of the Texture class with data of the image loaded from the given {@link InputStream}.
	 * 
	 * @param stream {@link InputStream} to load image from.
	 * @throws IOException
	 */
	public Texture (InputStream stream) throws IOException{
		BufferedImage img = FileIO.loadImage(stream);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	/**
	 * Creates a new instance of the Texture class with data of the resized image loaded from the given {@link InputStream}.
	 * 
	 * @param stream {@link InputStream} to load image from.
	 * @param width width of this texture.
	 * @param height height of this texture.
	 * @throws IOException
	 */
	public Texture (InputStream stream, int width, int height) throws IOException{
		BufferedImage img = FileIO.loadImage(stream, width, height);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	/**
	 * Sets the pixel this texture at the given position with given colors.
	 * 
	 * @param x position of pixel at the x axis.
	 * @param y position of pixel at the y axis.
	 * @param color color of pixel to set.
	 */
	public void setPixel(int x, int y, int color){
		image[MathUtils.clamp((x + (y*width)), 0, image.length-1)] = color;
	}
	
	/**
	 * Returns the color of the pixel at the given position.
	 * 
	 * @param x position of pixel at the x axis.
	 * @param y position of pixel at the y axis.
	 * @return color of the pixel at the given position.
	 */
	public int getPixel(int x, int y){
		return image[MathUtils.clamp((x + (y*width)), 0, image.length-1)];
	}
	
	/**
	 * Returns all pixels of this texture.
	 * 
	 * @return all pixels of this texture.
	 */
	public int[] getPixels() {
		return image;
	}
	
	/**
	 * Returns the width of this texture.
	 * 
	 * @return width of this texture.
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Returns the height of this texture.
	 * 
	 * @return height of this texture.
	 */
	public int getHeight(){
		return height;
	}
}
