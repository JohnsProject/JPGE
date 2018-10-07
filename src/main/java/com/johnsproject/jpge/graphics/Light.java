package com.johnsproject.jpge.graphics;

import com.johnsproject.jpge.utils.ColorUtils;

/**
 * The Light class contains data of a light object in the {@link Scene}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Light {
	
	public enum LightType{
		sun, point
	}
	
	private String name;
	private Transform transform;
	private LightType type = LightType.sun;
	private int strength = 10;
	private int color = 0;
	
	/**
	 * Creates a new instance of the Light class filled with the given values.
	 * 
	 * @param name name of this light.
	 * @param lightDirection direction of this light.
	 */
	public Light(String name, Transform position) {
		this.name = name;
		this.color = ColorUtils.convert(254, 254, 254);
		this.transform = position;
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
	public LightType getType() {
		return type;
	}

	/**
	 * Sets the type of this light equals to the given type.
	 * 
	 * @param type light type to set.
	 */
	public void setType(LightType type) {
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
	public String toString() {
		return "Light [name=" + name + ", transform=" + transform.toString() + ", type=" + type + ", strength="
				+ strength + "]";
	}
}
