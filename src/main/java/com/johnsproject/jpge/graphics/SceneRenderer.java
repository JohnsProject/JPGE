package com.johnsproject.jpge.graphics;

import java.util.List;

/**
 * The SceneRenderer class renders the {@link Scene} assigned to the {@link SceneFrame}.
 * It takes the {@link SceneObject SceneObjects} in the view of all {@link Camera Cameras} in the {@link Scene}, 
 * transforms and projects them. The objects are then drawn by the {@link SceneRasterizer}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneRenderer {
	public enum ProjectionType {
		orthographic, perspective
	}	

	public enum RenderingType {
		vertex, wireframe, solid, textured
	}
	
	/**
	 * Tells this scene renderer to render the given {@link Scene}.
	 * 
	 * @param scene {@link Scene} to render.
	 */
	public void render(Scene scene) {
		for (Camera camera : scene.getCameras()) {
			for (SceneObject sceneObject : scene.getSceneObjects()) {
				if (sceneObject.isActive() && (sceneObject.changed() || camera.changed())) {
					render(sceneObject, camera, scene.getLights());
				}
			}
			camera.changed(false);
		}
		for (SceneObject sceneObject : scene.getSceneObjects()) {
			sceneObject.changed(false);
		}
	}
	
	/**
	 * Renders the given {@link SceneObject} to the given {@link Camera} taking in account the given {@link Light Lights}.
	 * 
	 * @param sceneObject {@link SceneObject} to render.
	 * @param camera {@link Camera} that the {@link SceneObject} will be rendered to.
	 * @param lights {@link Light Lights} influencing the {@link SceneObject}.
	 */
	void render(SceneObject sceneObject, Camera camera, List<Light> lights) {
		Mesh mesh = sceneObject.getMesh();
//		Animation animation = mesh.getCurrentAnimation();
		Shader shader = sceneObject.getShader();
		mesh.resetBuffer();
		for (int i = 0; i < mesh.getVertexes().length; i++) {
			long vertex = mesh.getVertex(i);
//			for (int j = 0; j <= VertexUtils.getBoneIndex(vertex); j++) {
//				Transform bone = animation.getBone(j, animation.getCurrentFrame());
//				vector = VectorMathUtils.movePointByScale(vector, bone.getScale());
//				vector = VectorMathUtils.movePointByAnglesXYZ(vector, bone.getRotation());
//				//vector = VectorMathUtils.add(vector, bone.getPosition());
//			}
			vertex = shader.shadeVertex(vertex, sceneObject.getTransform(), camera, lights);
			mesh.setBufferedVertex(i, vertex);
		}
		for (int[] polygon : mesh.getPolygons()) {
			polygon = shader.shadePolygon(polygon, mesh, camera);
		}
	}
}
