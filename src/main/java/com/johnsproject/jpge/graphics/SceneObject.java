package com.johnsproject.jpge.graphics;

/**
 *The SceneObject class contains data of a object in the {@link Scene}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneObject {
	
	private String name;
	private Transform transform;
	private Mesh mesh;
	private boolean changed = false;

	/**
	 * Creates a new instance of the SceneObject class filled with the given values.
	 * 
	 * @param name name of this scene object.
	 * @param transform {@link Transform} of this scene object.
	 * @param mesh {@link Mesh} of this scene object.
	 */
	public SceneObject(String name, Transform transform, Mesh mesh){
		this.name = name;
		this.transform = transform;
		this.mesh = mesh;
		changed = true;
	}

	/**
	 * Returns the {@link Transform} of this scene object.
	 * 
	 * @return {@link Transform} of this scene object.
	 */
	public Transform getTransform() {
		changed = true;
		return this.transform;
	}
	
	/**
	 * Returns the {@link Mesh} of this scene object.
	 * 
	 * @return {@link Mesh} of this scene object.
	 */
	public Mesh getMesh() {
		changed = true;
		return mesh;
	}

	/**
	 * Sets the {@link Mesh} of this scene object.
	 * 
	 * @param mesh {@link Mesh} to set.
	 */
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
		changed = true;
	}

	/**
	 * Returns the name of this scene object.
	 * 
	 * @return name of this scene object.
	 */
	public String getName() {
		changed = true;
		return name;
	}
	
	/**
	 * Returns if this scene object has changed since last frame.
	 * 
	 * @return if this scene object has changed since last frame.
	 */
	boolean changed() {
		return changed;
	}
	
	/**
	 * Sets if this scene object has changed since last frame.
	 * 
	 * @param changed if this scene object has changed since last frame.
	 */
	void changed(boolean changed) {
		this.changed = changed;
	}
	
	@Override	
	public String toString() {
		return "SceneObject [name=" + name + ", transform=" + transform.toString() + ", mesh=" + mesh.toString() + "]";
	}	
}
