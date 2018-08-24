package com.johnsproject.jpge.graphics;

public class SceneObject {
	
	private String name;
	private Transform transform;
	private Mesh mesh;
	private boolean changed = false;

	public SceneObject(String name, Transform transform, Mesh mesh){
		this.name = name;
		this.transform = transform;
		this.mesh = mesh;
		changed = true;
	}

	public Transform getTransform() {
		changed = true;
		return this.transform;
	}
	
	public Mesh getMesh() {
		changed = true;
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
		changed = true;
	}

	public String getName() {
		changed = true;
		return name;
	}
	
	boolean changed() {
		return changed;
	}
	
	void changed(boolean changed) {
		this.changed = changed;
	}
	
	@Override	
	public String toString() {
		return "SceneObject [name=" + name + ", transform=" + transform + ", mesh=" + mesh + "]";
	}	
}
