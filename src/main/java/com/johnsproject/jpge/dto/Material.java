package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *The Material class contains appearance data of a {@link Mesh}.
 *It contains the color data, texture data and so on.
 *
 * @author John´s Project - John Salomon
 */
public class Material implements Externalizable{

	private static final long serialVersionUID = 8961543929625431193L;
	private int color;
//	private int ambientColor;
	private Texture texture;
	
	public Material() {}
	
	/**
	 * Creates a new instance of the Material class filled with the given values.
	 * 
	 * @param color color of this material.
	 * @param texture {@link Texture} of this material.
	 */
	public Material(int color, Texture texture){
		this.color = color;
//		this.ambientColor = ColorUtils.convert(20, 20, 20);
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
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(color);
		out.writeObject(texture);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		color = in.readInt();
		texture = (Texture) in.readObject();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + ((texture == null) ? 0 : texture.hashCode());
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
		Material other = (Material) obj;
		if (color != other.color)
			return false;
		if (texture == null) {
			if (other.texture != null)
				return false;
		} else if (!texture.equals(other.texture))
			return false;
		return true;
	}
}
