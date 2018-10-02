package com.johnsproject.jpge.graphics;

import com.johnsproject.jpge.utils.MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Light class contains data of a light object in the {@link Scene}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Light {
	
	private String name;
	private int[] lightDirection;
	public enum LightType{
		sun
	}
	
	private LightType lightType = LightType.sun;
	private int lightStrength = 10;
	private static final int CLAMP = 5;
	
	/**
	 * Creates a new instance of the Light class filled with the given values.
	 * 
	 * @param name name of this light.
	 * @param lightDirection direction of this light.
	 */
	public Light(String name, int[] lightDirection) {
		this.name = name;
		int[] pos = lightDirection;
		pos[0] = MathUtils.clamp(pos[0], -CLAMP, CLAMP);
		pos[1] = MathUtils.clamp(pos[1], -CLAMP, CLAMP);
		pos[2] = MathUtils.clamp(pos[2], -CLAMP, CLAMP);
		this.lightDirection = lightDirection;
	}
	
	/**
	 * Returns the direction of this light.
	 * 
	 * @return direction of this light.
	 */
	public int[] getDirection() {
		return this.lightDirection;
	}
	
	/**
	 * Sets the direction of this light equals to the given vector.
	 * 
	 * @param lightDirection direction to set.
	 */
	public void setDirection(int[] lightDirection) {
		int[] pos = lightDirection;
		pos[0] = MathUtils.clamp(pos[0], -CLAMP, CLAMP);
		pos[1] = MathUtils.clamp(pos[1], -CLAMP, CLAMP);
		pos[2] = MathUtils.clamp(pos[2], -CLAMP, CLAMP);
		this.lightDirection = lightDirection;
	}
	
	/**
	 * Sets the direction of this light equals to the given values.
	 * 
	 * @param x direction in the x axis to set.
	 * @param y direction in the y axis to set.
	 * @param z direction in the z axis to set.
	 */
	public void setDirection(int x, int y, int z) {
		lightDirection[0] = MathUtils.clamp(x, -CLAMP, CLAMP);
		lightDirection[1] = MathUtils.clamp(y, -CLAMP, CLAMP);
		lightDirection[2] = MathUtils.clamp(z, -CLAMP, CLAMP);
	}
	
	/**
	 * Translates the direction of this light by the given vector.
	 * 
	 * @param lightDirection direction to translate.
	 */
	public void translateDirection(int[] lightDirection) {
		int[] pos = lightDirection;
		pos[0] = lightDirection[0] + MathUtils.clamp(pos[0], -CLAMP, CLAMP);
		pos[1] = lightDirection[1] + MathUtils.clamp(pos[1], -CLAMP, CLAMP);
		pos[2] = lightDirection[2] + MathUtils.clamp(pos[2], -CLAMP, CLAMP);
		this.lightDirection = lightDirection;
	}
	
	/**
	 * Translates the direction of this light by the given values.
	 * 
	 * @param x direction in the x axis to translate.
	 * @param y direction in the y axis to translate.
	 * @param z direction in the z axis to translate.
	 */
	public void translateDirection(int x, int y, int z) {
		lightDirection[0] = MathUtils.clamp(lightDirection[0] + x, -CLAMP, CLAMP);
		lightDirection[1] = MathUtils.clamp(lightDirection[1] + y, -CLAMP, CLAMP);
		lightDirection[2] = MathUtils.clamp(lightDirection[2] + z, -CLAMP, CLAMP);
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
		return "Light [name=" + name + ", lightDirection=" + VectorUtils.toString(lightDirection) + ", lightType=" + lightType + ", lightStrength="
				+ lightStrength + "]";
	}
}
