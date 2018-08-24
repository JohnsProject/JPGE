package com.johnsproject.jpge.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.johnsproject.jpge.io.FileIO;

public class Image {
	
	private BufferedImage image;
	private int width = 0, height = 0;
	
	public Image (BufferedImage image){
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public Image (String imagePath) throws IOException{
		this.image = FileIO.loadImage(imagePath);
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public Image (int width, int height){
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public void setPixel(int x, int y, int color){
		if((x > 0 && y > 0) && (x < width && y < height)) {
			image.setRGB(x, y, color);
		}
	}
	
	public int getPixel(int x, int y){
		return image.getRGB(x, y);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void reset() {
		image.getGraphics().clearRect(0, 0, width, height);
	}

	public BufferedImage getBufferedImage() {
		return image;
	}
}
