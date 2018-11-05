package com.johnsproject.jpge.dto;

import com.johnsproject.jpge.graphics.Shader;

/**
 *The SceneObject class contains data of a object in the {@link Scene}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneObject {
	
	private String name;
	private Transform transform;
	private Mesh mesh;
	private Shader shader;
	private Rigidbody rigidbody;
	private boolean changed = false;
	private boolean active = true;

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
		this.changed = true;
		this.shader = new Shader();
		this.rigidbody = new Rigidbody();
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
	public boolean changed() {
		return changed;
	}
	
	/**
	 * Sets if this scene object has changed since last frame.
	 * 
	 * @param changed if this scene object has changed since last frame.
	 */
	public void changed(boolean changed) {
		this.changed = changed;
	}
	
	/**
	 * Returns the {@link Shader} used by this scene object.
	 * 
	 * @return {@link Shader} used by this scene object.
	 */
	public Shader getShader() {
		return shader;
	}

	/**
	 * Sets the {@link Shader} used by this scene object equals to the given {@link Shader}.
	 * 
	 * @param shader {@link Shader} to set.
	 */
	public void setShader(Shader shader) {
		this.shader = shader;
	}

	/**
	 * Returns the {@link Rigidbody} used by this scene object.
	 * 
	 * @return {@link Rigidbody} used by this scene object.
	 */
	public Rigidbody getRigidbody() {
		return rigidbody;
	}

	/**
	 * Returns if this object is active or not.
	 * 
	 * @return if this object is active or not.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets this object active or not.
	 * This object will only be rendered if its active.
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}	
}
