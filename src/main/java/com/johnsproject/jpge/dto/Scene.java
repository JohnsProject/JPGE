package com.johnsproject.jpge.dto;

import java.util.ArrayList;
import java.util.List;

import com.johnsproject.jpge.physics.PhysicsSettings;

/**
 * The Scene class contains {@link SceneObject sceneobjects}, 
 * {@link Camera cameras} and {@link Light lights}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Scene {

	private final List<SceneObject> sceneObjects = new ArrayList<SceneObject>();
	private final List<Camera> cameras = new ArrayList<Camera>();
	private final List<Light> lights = new ArrayList<Light>();
	private PhysicsSettings physicsSettings = new PhysicsSettings();
	
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
}
