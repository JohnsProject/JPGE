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
	
	public Light(String name, int[] position, int[] rotation) {
		this.name = name;
		this.transform = new Transform(position, rotation, new int[] {1, 1, 1});
	}
	
	public Light(String name, LightType lightType, int[] position, int[] rotation) {
		this.name = name;
		this.transform = new Transform(position, rotation, new int[] {1, 1, 1});
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

	public Light clone(){
		Transform t = transform.clone();
		Light l = new Light(name, lightType, t.getPosition(), t.getRotation());
		l.setLightStrength(lightStrength);
		return l;
	}	
}
