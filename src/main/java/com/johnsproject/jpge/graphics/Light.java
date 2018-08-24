package com.johnsproject.jpge.graphics;

public class Light {
	
	private String name;
	private Transform transform;
	public enum LightType{
		sun,
		point
	}
	
	private LightType lightType = LightType.sun;
	private int lightStrength = 10;
	
	public Light(String name, Transform transform) {
		this.name = name;
		this.transform = transform;
	}
	
	public Light(String name, LightType lightType, Transform transform) {
		this.name = name;
		this.transform = transform;
		this.lightType = lightType;
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public String getName() {
		return name;
	}

	public LightType getLightType() {
		return lightType;
	}

	public void setLightType(LightType lightType) {
		this.lightType = lightType;
	}

	public int getLightStrength() {
		return lightStrength;
	}

	public void setLightStrength(int lightStrength) {
		this.lightStrength = lightStrength;
	}
}
