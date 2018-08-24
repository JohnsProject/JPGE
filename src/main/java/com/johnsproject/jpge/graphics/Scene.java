package com.johnsproject.jpge.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.johnsproject.jpge.graphics.event.*;

public class Scene {

	private final List<SceneObject> sceneObjects = Collections.synchronizedList(new ArrayList<SceneObject>());
	private final List<Camera> cameras = Collections.synchronizedList(new ArrayList<Camera>());
	private final List<GuiComponent> guiComponents = Collections.synchronizedList(new ArrayList<GuiComponent>());
	private final List<Light> lights = Collections.synchronizedList(new ArrayList<Light>());
	
	
	public Scene() {
		
	}
	
	public void addSceneObject(SceneObject sceneObject){
		synchronized (sceneObjects) {
			sceneObjects.add(sceneObject);
		}
	}
	
	public void removeSceneObject(SceneObject sceneObject){
		synchronized (sceneObjects) {
			sceneObjects.remove(sceneObject);
		}
	}

	public List<SceneObject> getSceneObjects() {
		synchronized (sceneObjects) {
			return sceneObjects;
		}
	}
	
	public void addLight(Light light){
		synchronized (lights) {
			lights.add(light);
		}
	}
	
	public void removeLight(Light light){
		synchronized (lights) {
			lights.remove(light);
		}
	}

	public List<Light> getLights() {
		synchronized (lights) {
			return lights;
		}
	}
	
	public void addCamera(Camera camera){
		synchronized (cameras) {
			cameras.add(camera);
			EventDispatcher.getInstance().dispatchAddEvent(new CameraEvent(camera));
		}
	}
	
	public void removeCamera(Camera camera){
		synchronized (cameras) {
			EventDispatcher.getInstance().dispatchRemoveEvent(new CameraEvent(camera));
			cameras.remove(camera);
		}
	}

	public List<Camera> getCameras() {
		synchronized (cameras) {
			return cameras;
		}
	}
	
	public void addGuiComponent(GuiComponent component){
		synchronized (guiComponents) {
			guiComponents.add(component);
		}
	}
	
	public void removeGuiComponent(GuiComponent component){
		synchronized (guiComponents) {
			guiComponents.remove(component);
		}
	}
	
	public List<GuiComponent> getGuiComponents(){
		synchronized (guiComponents) {
			return guiComponents;
		}
	}

	@Override
	public String toString() {
		return "Scene [sceneObjects=" + sceneObjects + ", cameras=" + cameras + ", guiComponents=" + guiComponents
				+ ", lights=" + lights + "]";
	}	
}
