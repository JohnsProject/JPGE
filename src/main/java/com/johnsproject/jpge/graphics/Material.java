package com.johnsproject.jpge.graphics;

/**
 *The material class stores appearance data of a mesh.
 *It contains the color data, texture data and so on.
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class Material {

	private int color = 0;
	private Image texture;
	
	public Material(int color, Image texture){
		this.color = color;
		this.texture = texture;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}
}
