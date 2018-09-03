package com.johnsproject.jpge.graphics;

/**
 * The Light class contains data of a light object in the {@link Scene}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Light {
	
	private String name;
	private Transform transform;
	public enum LightType{
		sun,
		point
	}
	
	private LightType lightType = LightType.sun;
	private int lightStrength = 10;
	
	/**
	 * Creates a new instance of the Light class filled with the given values.
	 * 
	 * @param name name of this light.
	 * @param transform {@link Transform} of this light.
	 */
	public Light(String name, Transform transform) {
		this.name = name;
		this.transform = transform;
	}
	
	/**
	 * Creates a new instance of the Light class filled with the given values.
	 * 
	 * @param name name of this light.
	 * @param lightType the lighting type of this light.
	 * @param transform transform of this light.
	 */
	public Light(String name, LightType lightType, Transform transform) {
		this.name = name;
		this.transform = transform;
		this.lightType = lightType;
	}
	
	/**
	 * Returns the {@link Transform} of this light.
	 * 
	 * @return {@link Transform} of this light.
	 */
	public Transform getTransform() {
		return this.transform;
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
	 * Returns the type of this light.
	 * 
	 * @return type of this light.
	 */
	public LightType getLightType() {
		return lightType;
	}

	/**
	 * Sets the type of this light equals to the given type.
	 * 
	 * @param lightType light type to set.
	 */
	public void setLightType(LightType lightType) {
		this.lightType = lightType;
	}

	/**
	 * Returns the strength of this light.
	 * 
	 * @return strength of this light.
	 */
	public int getLightStrength() {
		return lightStrength;
	}

	/**
	 * Sets the strength of this light equals to the given value.
	 * 
	 * @param lightStrength light strength to set.
	 */
	public void setLightStrength(int lightStrength) {
		this.lightStrength = lightStrength;
	}

	@Override
	public String toString() {
		return "Light [name=" + name + ", transform=" + transform + ", lightType=" + lightType + ", lightStrength="
				+ lightStrength + "]";
	}
}
