package com.johnsproject.jpge.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;

import com.johnsproject.jpge.io.FileIO;

public class Texture {
	
	private int[] image = null;
	private int width = 0, height = 0;
	
	public Texture (int width, int height){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public Texture (String path) throws IOException{
		BufferedImage img = FileIO.loadImage(path);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public Texture (String path, int width, int height) throws IOException{
		BufferedImage img = FileIO.loadImage(path, width, height);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public Texture (InputStream stream) throws IOException{
		BufferedImage img = FileIO.loadImage(stream);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public Texture (InputStream stream, int width, int height) throws IOException{
		BufferedImage img = FileIO.loadImage(stream, width, height);
		this.image = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public void setPixel(int x, int y, int color){
		if((x > 0 && y > 0) && (x < width && y < height)) {
			image[x + (y*width)] = color;
		}
	}
	
	public int getPixel(int x, int y){
		return image[x + (y*width)];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
