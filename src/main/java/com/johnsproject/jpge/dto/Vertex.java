/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Vertex class contains data of a vertex point.
 *
 * @author John´s Project - John Salomon
 */
public class Vertex implements Externalizable {

	private static final long serialVersionUID = 6394891669446103431L;
	
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	private int[] location = new int[3];
	private int[] normal = new int[3];
	private int color = 0;
	private int material = 0;
	private int bone = 0;
	
	public Vertex() {}
	
	/**
	 * Creates a new instance of the Vertex class filled with the given values.
	 * 
	 * @param location	location of this vertex.
	 * @param normal 	normal vector of this vertex.
	 * @param material	index of the {@link Material} of this vertex in the {@link Material} 
	 * array of the {@link Mesh} this vertex belongs to.
	 * @param bone	index of the bone that modifies this vertex in the bone array of the 
	 * {@link Mesh} this vertex belongs to.
	 */
	public Vertex(int[] location, int[] normal, int bone, int material) {
		this.location = location;
		this.normal = normal;
		this.bone = bone;
		this.color = 0;
		this.material = material;
	}

	/**
	 * Returns the location of this vertex.
	 * 
	 * @return location of this vertex.
	 */
	public int[] getLocation() {
		return location;
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

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(location[vx]);
		out.writeInt(location[vy]);
		out.writeInt(location[vz]);
		out.writeInt(normal[vx]);
		out.writeInt(normal[vy]);
		out.writeInt(normal[vz]);
		out.writeInt(color);
		out.writeInt(material);
		out.writeInt(bone);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		location[vx] = in.readInt();
		location[vy] = in.readInt();
		location[vz] = in.readInt();
		normal[vx] = in.readInt();
		normal[vy] = in.readInt();
		normal[vz] = in.readInt();
		color = in.readInt();
		material = in.readInt();
		bone = in.readInt();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bone;
		result = prime * result + color;
		result = prime * result + material;
		result = prime * result + Arrays.hashCode(normal);
		result = prime * result + Arrays.hashCode(location);
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
		Vertex other = (Vertex) obj;
		if (bone != other.bone)
			return false;
		if (color != other.color)
			return false;
		if (material != other.material)
			return false;
		if (!Arrays.equals(normal, other.normal))
			return false;
		if (!Arrays.equals(location, other.location))
			return false;
		return true;
	}
}
