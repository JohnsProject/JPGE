package com.johnsproject.jpge.graphics;

/**
 *The material class stores appearance data of a mesh.
 *It contains the color data, texture data and so on.
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class Material {

	private int color = 0;
	private Texture texture;
	
	public Material(int color, Texture texture){
		this.color = color;
		this.texture = texture;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
