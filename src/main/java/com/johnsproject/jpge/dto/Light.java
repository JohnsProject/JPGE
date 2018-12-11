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

import com.johnsproject.jpge.utils.ColorUtils;

/**
 * The Light class contains data of a light object in the {@link Scene}.
 * 
 * @author John´s Project - John Salomon
 */
public class Light implements Externalizable{
	
	private static final long serialVersionUID = 3540500112050837747L;
	private String name = "Default";
	private Transform transform = new Transform();
	private int type = 0;
	private int strength = 100;
	private int color = 0;
	
	public Light() {}
	
	/**
	 * Creates a new instance of the Light class filled with the given values.
	 * 
	 * @param name name of this light.
	 * @param transform transform of this light.
	 */
	public Light(String name, Transform transform) {
		this.name = name;
		this.color = ColorUtils.convert(254, 254, 254);
		this.transform = transform;
	}
	
	/**
	 * Returns the name of this light.
	 * 
	 * @return name of this light.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the {@link Transform} of this light.
	 * 
	 * @return {@link Transform} of this light.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	/**
	 * Returns the type of this light.
	 * 
	 * @return type of this light.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type of this light equals to the given type.
	 * 
	 * @param type light type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the strength of this light.
	 * 
	 * @return strength of this light.
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Sets the strength of this light equals to the given value.
	 * 
	 * @param strength light strength to set.
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * Returns the color of this light.
	 * 
	 * @return color of this light.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Sets the color of this light equals to the given value.
	 * 
	 * @param color light color to set.
	 */
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(name);
		out.writeObject(transform);
		out.writeInt(type);
		out.writeInt(strength);
		out.writeInt(color);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		name = in.readUTF();
		transform = (Transform) in.readObject();
		type = in.readInt();
		strength = in.readInt();
		color = in.readInt();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + strength;
		result = prime * result + ((transform == null) ? 0 : transform.hashCode());
		result = prime * result + type;
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
		Light other = (Light) obj;
		if (color != other.color)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (strength != other.strength)
			return false;
		if (transform == null) {
			if (other.transform != null)
				return false;
		} else if (!transform.equals(other.transform))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
