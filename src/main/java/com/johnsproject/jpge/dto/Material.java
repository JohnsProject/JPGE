package com.johnsproject.jpge.dto;

import com.johnsproject.jpge.utils.ColorUtils;

/**
 *The Material class contains appearance data of a {@link Mesh}.
 *It contains the color data, texture data and so on.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Material {

	private int color;
	private int ambientColor;
	private Texture texture;
	
	/**
	 * Creates a new instance of the Material class filled with the given values.
	 * 
	 * @param color color of this material.
	 * @param texture {@link Texture} of this material.
	 */
	public Material(int color, Texture texture){
		this.color = color;
		this.ambientColor = ColorUtils.convert(20, 20, 20);
		this.texture = texture;
	}

	/**
	 * Returns the color of this material.
	 * 
	 * @return color of this material.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Sets the color of this material equals to the given color.
	 * 
	 * @param color color to set.
	 */
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * Returns the {@link Texture} of this material.
	 * 
	 * @return {@link Texture} of this material.
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * Sets the {@link Texture} of this material equal to the given texture.
	 * 
	 * @param texture texture to set.
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	@Override
	public String toString() {
		return "Material [color=" + color + ", texture=" + texture + "]";
	}
}
