package com.johnsproject.jpge.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.johnsproject.jpge.graphics.event.*;

/**
 * The Scene class contains {@link SceneObject sceneobjects}, 
 * {@link Camera cameras} and {@link Light lights}, its like a world or map.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Scene {

	private final List<SceneObject> sceneObjects = Collections.synchronizedList(new ArrayList<SceneObject>());
	private final List<Camera> cameras = Collections.synchronizedList(new ArrayList<Camera>());
	private final List<GuiComponent> guiComponents = Collections.synchronizedList(new ArrayList<GuiComponent>());
	private final List<Light> lights = Collections.synchronizedList(new ArrayList<Light>());
	
	/**
	 * Creates a new empty instance of the Scene class.
	 */
	public Scene() {}
	
	/**
	 * Adds the given {@link SceneObject} to this scene.
	 * 
	 * @param sceneObject {@link SceneObject} to add.
	 */
	public void addSceneObject(SceneObject sceneObject){
		synchronized (sceneObjects) {
			sceneObjects.add(sceneObject);
		}
	}
	
	/**
	 * Removes the given {@link SceneObject} from this scene.
	 * 
	 * @param sceneObject {@link SceneObject} to remove.
	 */
	public void removeSceneObject(SceneObject sceneObject){
		synchronized (sceneObjects) {
			sceneObjects.remove(sceneObject);
		}
	}

	/**
	 * Returns all {@link SceneObject SceneObjects} of this scene.
	 * 
	 * @return all {@link SceneObject SceneObjects} of this scene.
	 */
	public List<SceneObject> getSceneObjects() {
		synchronized (sceneObjects) {
			return sceneObjects;
		}
	}
	
	/**
	 * Adds the given {@link Light} to this scene.
	 * 
	 * @param light {@link Light} to add.
	 */
	public void addLight(Light light){
		synchronized (lights) {
			lights.add(light);
		}
	}
	
	/**
	 * Removes the given {@link Light} from this scene.
	 * 
	 * @param light {@link Light} to remove.
	 */
	public void removeLight(Light light){
		synchronized (lights) {
			lights.remove(light);
		}
	}
	
	/**
	 * Returns all {@link Light Lights} of this scene.
	 * 
	 * @return all {@link Light Lights} of this scene.
	 */
	public List<Light> getLights() {
		synchronized (lights) {
			return lights;
		}
	}
	
	/**
	 * Adds the given {@link Camera} to this scene.
	 * 
	 * @param camera {@link Camera} to add.
	 */
	public void addCamera(Camera camera){
		synchronized (cameras) {
			cameras.add(camera);
			GraphicsEventDispatcher.getInstance().dispatchAddEvent(new CameraEvent(camera));
		}
	}
	
	/**
	 * Removes the given {@link Camera} from this scene.
	 * 
	 * @param camera {@link Camera} to remove.
	 */
	public void removeCamera(Camera camera){
		synchronized (cameras) {
			GraphicsEventDispatcher.getInstance().dispatchRemoveEvent(new CameraEvent(camera));
			cameras.remove(camera);
		}
	}

	/**
	 * Returns all {@link Camera Cameras} of this scene.
	 * 
	 * @return all {@link Camera Cameras} of this scene.
	 */
	public List<Camera> getCameras() {
		synchronized (cameras) {
			return cameras;
		}
	}

	@Override
	public String toString() {
		return "Scene [sceneObjects=" + sceneObjects + ", cameras=" + cameras + ", guiComponents=" + guiComponents
				+ ", lights=" + lights + "]";
	}
	
//	public void addGuiComponent(GuiComponent component){
//		synchronized (guiComponents) {
//			guiComponents.add(component);
//		}
//	}
//	
//	public void removeGuiComponent(GuiComponent component){
//		synchronized (guiComponents) {
//			guiComponents.remove(component);
//		}
//	}
//	
//	public List<GuiComponent> getGuiComponents(){
//		synchronized (guiComponents) {
//			return guiComponents;
//		}
//	}

		
}
