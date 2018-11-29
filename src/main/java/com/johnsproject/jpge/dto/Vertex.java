package com.johnsproject.jpge.dto;

import java.io.Serializable;

/**
 * The Vertex class contains data of a vertex point.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Vertex implements Serializable{

	private static final long serialVersionUID = 6394891669446103431L;
	
	private int[] position;
	private int[] normal;
	private int color;
	private int material;
	private int bone;
	
	/**
	 * Creates a new instance of the Vertex class filled with the given values.
	 * 
	 * @param position	position of this vertex.
	 * @param normal 	normal vector of this vertex.
	 * @param material	index of the {@link Material} of this vertex in the {@link Material} 
	 * array of the {@link Mesh} this vertex belongs to.
	 * @param bone	index of the bone that modifies this vertex in the bone array of the 
	 * {@link Mesh} this vertex belongs to.
	 */
	public Vertex(int[] position, int[] normal, int bone, int material) {
		this.position = position;
		this.normal = normal;
		this.bone = bone;
		this.color = 0;
		this.material = material;
	}

	/**
	 * Returns the position of this vertex.
	 * 
	 * @return position of this vertex.
	 */
	public int[] getPosition() {
		return position;
	}

	/**
	 * Returns the normal vector of this vertex.
	 * 
	 * @return normal vector of this vertex.
	 */
	public int[] getNormal() {
		return normal;
	}
	
	/**
	 * Returns the color of this vertex.
	 * This color is the color used to interpolate
	 * the colors of the pixels at the drawing process.
	 * 
	 * @return color of this vertex.
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * Sets the color of this vertex.
	 * This color is the color used to interpolate
	 * the colors of the pixels at the drawing process.
	 * 
	 * @param color color to set.
	 */
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * Returns the index of the {@link Material} of this vertex in the 
	 * {@link Material} array of the {@link Mesh} this vertex belongs to.
	 * 
	 * @return index of the {@link Material} of this vertex in the 
	 * {@link Material} array of the {@link Mesh} this vertex belongs to.
	 */
	public int getMaterial() {
		return material;
	}

	/**
	 * Returns the index of the bone that modifies this vertex in the bone array of the 
	 * {@link Mesh} this vertex belongs to.
	 * 
	 * @return index of the bone that modifies this vertex in the bone array of the 
	 * {@link Mesh} this vertex belongs to.
	 */
	public int getBone() {
		return bone;
	}
}
