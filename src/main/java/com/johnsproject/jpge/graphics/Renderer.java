package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.dto.Animation;
import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.DisplayBuffer;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Light;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Vertex;
import com.johnsproject.jpge.utils.RenderUtils;

/**
 * The Renderer class renders the {@link Scene} assigned to the {@link SceneWindow}.
 * It takes the {@link SceneObject SceneObjects} in the view of all {@link Camera Cameras} in the {@link Scene}, 
 * transforms, projects and draws them.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Renderer {
	
	/**
	 * Tells this scene renderer to render the given {@link Scene}.
	 * 
	 * @param scene {@link Scene} to render.
	 * @param displayBuffer {@link DisplayBuffer} to use.
	 * 
	 * @return rendered {@link Face faces}. (faces that are not culled)
	 */
	public int render(Scene scene, DisplayBuffer displayBuffer) {
		int rendFaces = 0;
		displayBuffer.clearFrameBuffer();
		for (int i = 0; i < scene.getCameras().size(); i++) {
			displayBuffer.clearDepthBuffer();
			Camera camera = scene.getCameras().get(i);
			for (int j = 0; j < scene.getSceneObjects().size(); j++) {
				SceneObject sceneObject = scene.getSceneObjects().get(j);
				// check if object is active or has changed (no need to render if its the same)
				if (sceneObject.isActive() && (sceneObject.changed() || camera.changed())) {
					rendFaces = render(sceneObject, camera, scene.getLights(), displayBuffer);
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
	
	/**
	 * Tells this scene renderer to render the given {@link SceneObject} 
	 * to the given {@link Camera} taking in account the given {@link Light Lights}.
	 * 
	 * @param sceneObject {@link SceneObject} to render.
	 * @param camera {@link Camera} that the {@link SceneObject} will be rendered to.
	 * @param lights {@link Light Lights} influencing the {@link SceneObject}.
	 * @param displayBuffer {@link DisplayBuffer} to use.
	 * 
	 * @return rendered {@link Face faces}. (faces that are not culled)
	 */
	int render(SceneObject sceneObject, Camera camera, List<Light> lights, DisplayBuffer displayBuffer) {
		Mesh mesh = sceneObject.getMesh();
		Animation animation = mesh.getCurrentAnimation();
		Shader shader = sceneObject.getShader();
		// reset mesh buffer
		mesh.resetBuffer();
		// animate and shade vertexes
		for (int i = 0; i < mesh.getBufferedVertexes().length; i++) {
			Vertex vertex = mesh.getBufferedVertex(i);
			vertex = RenderUtils.animate(vertex, animation);
			vertex = shader.shadeVertex(vertex, mesh, sceneObject.getTransform(), lights, camera);
		}
		int rendFaces = 0;
		// shade faces
		for (int i = 0; i < mesh.getFaces().length; i++) {
			Face face = mesh.getFace(i);
			face = shader.shadeFace(face, mesh, sceneObject.getTransform(), lights, camera, displayBuffer);
			if (!face.isCulled()) rendFaces++;
		}
		return rendFaces;
	}
}
