package com.johnsproject.jpge.dto;

import com.johnsproject.jpge.graphics.Shader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The SceneObject class contains data of a object in the {@link Scene}.
 *
 * @author John´s Project - John Salomon
 */
public class SceneObject implements Externalizable {

	private static final long serialVersionUID = 5517684517308413441L;
	
	private String name = "Default";
	private Transform transform = new Transform();
	private Mesh mesh = new Mesh();
	private Shader shader = new Shader();
	private Rigidbody rigidbody = new Rigidbody();
	private boolean changed = false;
	private boolean active = true;

	public SceneObject() {}
	
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
		changed = true;
		this.shader = shader;
	}

	/**
	 * Returns the {@link Rigidbody} used by this scene object.
	 * 
	 * @return {@link Rigidbody} used by this scene object.
	 */
	public Rigidbody getRigidbody() {
		changed = true;
		return rigidbody;
	}

	/**
	 * Returns if this object is active or not.
	 * 
	 * @return if this object is active or not.
	 */
	public boolean isActive() {
		changed = true;
		return active;
	}

	/**
	 * Sets this object active or not.
	 * This object will only be rendered if its active.
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		changed = true;
		this.active = active;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(name);
		out.writeObject(transform);
		out.writeObject(mesh);
		out.writeObject(rigidbody);
		out.writeBoolean(active);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		name = in.readUTF();
		transform = (Transform)in.readObject();
		mesh = (Mesh)in.readObject();
		rigidbody = (Rigidbody)in.readObject();
		active = in.readBoolean();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mesh == null) ? 0 : mesh.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rigidbody == null) ? 0 : rigidbody.hashCode());
		result = prime * result + ((shader == null) ? 0 : shader.hashCode());
		result = prime * result + ((transform == null) ? 0 : transform.hashCode());
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
		SceneObject other = (SceneObject) obj;
		if (mesh == null) {
			if (other.mesh != null)
				return false;
		} else if (!mesh.equals(other.mesh))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rigidbody == null) {
			if (other.rigidbody != null)
				return false;
		} else if (!rigidbody.equals(other.rigidbody))
			return false;
		if (shader == null) {
			if (other.shader != null)
				return false;
		} else if (!shader.getClass().getName().equals(other.shader.getClass().getName()))
			return false;
		if (transform == null) {
			if (other.transform != null)
				return false;
		} else if (!transform.equals(other.transform))
			return false;
		return true;
	}	
}
