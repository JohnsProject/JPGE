package com.johnsproject.jpge.graphics;

import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.RenderBuffer;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;

/**
 * The Renderer class renders the {@link Scene} assigned to the {@link SceneWindow}.
 * It takes the {@link SceneObject SceneObjects} in the view of all {@link Camera Cameras} in the {@link Scene}, 
 * transforms, projects and draws them.
 *
 * @author John´s Project - John Salomon
 */
public class Renderer {
	
	/**
	 * Tells this scene renderer to render the given {@link Scene}.
	 * 
	 * @param scene {@link Scene} to render.
	 * @param renderBuffer {@link RenderBuffer} to use.
	 * 
	 * @return rendered {@link Face faces}. (faces that are not culled)
	 */
	public int render(Scene scene, RenderBuffer renderBuffer) {
		int rendFaces = 0;
		renderBuffer.clearFrameBuffer();
		for (int i = 0; i < scene.getCameras().size(); i++) {
			renderBuffer.clearDepthBuffer();
			Camera camera = scene.getCameras().get(i);
			for (int j = 0; j < scene.getSceneObjects().size(); j++) {
				SceneObject sceneObject = scene.getSceneObjects().get(j);
				// check if object is active or has changed (no need to render if its the same)
				if (sceneObject.isActive() && (sceneObject.changed() || camera.changed())) {
					rendFaces += sceneObject.getShader().shade(
							sceneObject.getMesh(), sceneObject.getTransform(), camera, renderBuffer, scene.getLights()
							);
				}
			}
			camera.changed(false);
		}
		for (int i = 0; i < scene.getSceneObjects().size(); i++) {
			SceneObject sceneObject = scene.getSceneObjects().get(i);
			sceneObject.changed(false);
		}
		return rendFaces;
	}
}
