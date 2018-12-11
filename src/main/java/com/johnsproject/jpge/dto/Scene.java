package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * The Scene class contains {@link SceneObject sceneobjects}, 
 * {@link Camera cameras} and {@link Light lights}.
 * 
 * @author John´s Project - John Salomon
 */
public class Scene implements Externalizable{

	private static final long serialVersionUID = -209301980209001616L;
	
	private final List<SceneObject> sceneObjects = new ArrayList<SceneObject>(0);
	private final List<Camera> cameras = new ArrayList<Camera>(0);
	private final List<Light> lights = new ArrayList<Light>(0);
	private PhysicsSettings physicsSettings = new PhysicsSettings();
	
	public Scene() {}
	
	/**
	 * Adds the given {@link SceneObject} to this scene.
	 * 
	 * @param sceneObject {@link SceneObject} to add.
	 */
	public void addSceneObject(SceneObject sceneObject){
		sceneObjects.add(sceneObject);
	}
	
	/**
	 * Removes the given {@link SceneObject} from this scene.
	 * 
	 * @param sceneObject {@link SceneObject} to remove.
	 */
	public void removeSceneObject(SceneObject sceneObject){
		sceneObjects.remove(sceneObject);
	}

	/**
	 * Returns all {@link SceneObject SceneObjects} of this scene.
	 * 
	 * @return all {@link SceneObject SceneObjects} of this scene.
	 */
	public List<SceneObject> getSceneObjects() {
		return sceneObjects;
	}
	
	/**
	 * Adds the given {@link Light} to this scene.
	 * 
	 * @param light {@link Light} to add.
	 */
	public void addLight(Light light){
		lights.add(light);
	}
	
	/**
	 * Removes the given {@link Light} from this scene.
	 * 
	 * @param light {@link Light} to remove.
	 */
	public void removeLight(Light light){
		lights.remove(light);
	}
	
	/**
	 * Returns all {@link Light Lights} of this scene.
	 * 
	 * @return all {@link Light Lights} of this scene.
	 */
	public List<Light> getLights() {
		return lights;
	}
	
	/**
	 * Adds the given {@link Camera} to this scene.
	 * 
	 * @param camera {@link Camera} to add.
	 */
	public void addCamera(Camera camera){
		cameras.add(camera);
	}
	
	/**
	 * Removes the given {@link Camera} from this scene.
	 * 
	 * @param camera {@link Camera} to remove.
	 */
	public void removeCamera(Camera camera){
		cameras.remove(camera);
	}

	/**
	 * Returns all {@link Camera Cameras} of this scene.
	 * 
	 * @return all {@link Camera Cameras} of this scene.
	 */
	public List<Camera> getCameras() {
		return cameras;
	}

	/**
	 * Returns the {@link PhysicsSettings} of this scene.
	 * 
	 * @return {@link PhysicsSettings} of this scene.
	 */
	public PhysicsSettings getPhysicsSettings() {
		return physicsSettings;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		final int soSize = sceneObjects.size();
		out.writeInt(soSize);
		for (int i = 0; i < soSize; i++) {
			out.writeObject(sceneObjects.get(i));
		}
		final int camSize = cameras.size();
		out.writeInt(camSize);
		for (int i = 0; i < camSize; i++) {
			out.writeObject(cameras.get(i));
		}
		final int lightSize = lights.size();
		out.writeInt(lightSize);
		for (int i = 0; i < lightSize; i++) {
			out.writeObject(lights.get(i));
		}
		out.writeObject(physicsSettings);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		sceneObjects.clear();
		cameras.clear();
		lights.clear();
		final int soSize = in.readInt();
		for (int i = 0; i < soSize; i++) {
			sceneObjects.add((SceneObject) in.readObject());
		}
		final int camSize = in.readInt();
		for (int i = 0; i < camSize; i++) {
			cameras.add((Camera) in.readObject());
		}
		final int lightSize = in.readInt();
		for (int i = 0; i < lightSize; i++) {
			lights.add((Light) in.readObject());
		}
		physicsSettings = (PhysicsSettings) in.readObject();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cameras == null) ? 0 : cameras.hashCode());
		result = prime * result + ((lights == null) ? 0 : lights.hashCode());
		result = prime * result + ((physicsSettings == null) ? 0 : physicsSettings.hashCode());
		result = prime * result + ((sceneObjects == null) ? 0 : sceneObjects.hashCode());
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
		Scene other = (Scene) obj;
		if (cameras == null) {
			if (other.cameras != null)
				return false;
		} else if (!other.cameras.containsAll(cameras))
			return false;
		if (lights == null) {
			if (other.lights != null)
				return false;
		} else if (!other.lights.containsAll(lights))
			return false;
		if (physicsSettings == null) {
			if (other.physicsSettings != null)
				return false;
		} else if (!physicsSettings.equals(other.physicsSettings))
			return false;
		if (sceneObjects == null) {
			if (other.sceneObjects != null)
				return false;
		} else if (!other.sceneObjects.containsAll(sceneObjects))
			return false;
		return true;
	}
}
