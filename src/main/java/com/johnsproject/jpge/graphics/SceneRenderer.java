package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.Profiler;
import com.johnsproject.jpge.utils.RenderUtils;

/**
 * The SceneRenderer class renders the {@link Scene} assigned to the {@link SceneFrame}.
 * It takes the {@link SceneObject SceneObjects} in the view of all {@link Camera Cameras} in the {@link Scene}, 
 * transforms, projects and draws them.
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
	
	private int[] zBuffer = null;
	
	/**
	 * Creates a new instance of the SceneRenderer class filled with the given values.
	 * 
	 * @param width window width.
	 * @param height window height.
	 */
	public SceneRenderer(int width, int height) {
		setZBufferSize(width, height);
	}
	
	/**
	 * Sets the size of the depth buffer of this scene renderer.
	 * 
	 * @param width depth buffer width.
	 * @param height depth buffer height.
	 */
	public void setZBufferSize(int width, int height) {
		zBuffer = new int[width*height];
		Profiler.getInstance().getData().setWidth(width);
		Profiler.getInstance().getData().setHeight(height);
	}
	
	/**
	 * Tells this scene renderer to render the given {@link Scene}.
	 * 
	 * @param scene {@link Scene} to render.
	 */
	public void render(Scene scene) {
		// reset z buffer
		resetZBuffer();
		// reseting profiler data
		Profiler.getInstance().getData().setMaxPolys(0);
		Profiler.getInstance().getData().setRenderedPolys(0);
		synchronized (scene.getCameras()) {
			for (Camera camera : scene.getCameras()) {
				for (SceneObject sceneObject : scene.getSceneObjects()) {
					// check if object is active or has changed (no need to render if its the same)
					if (sceneObject.isActive() && (sceneObject.changed() || camera.changed())) {
						render(sceneObject, camera, scene.getLights());
					}
				}
				camera.changed(false);
				// tell camera to draw the buffer that has been used
				camera.drawBuffer();
			}
		}
		synchronized (scene.getSceneObjects()) {
			for (SceneObject sceneObject : scene.getSceneObjects()) {
				sceneObject.changed(false);
			}
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
		Animation animation = mesh.getCurrentAnimation();
		Shader shader = sceneObject.getShader();
		// reset mesh buffer
		mesh.resetBuffer();
		// animate and shade vertexes
		for (int i = 0; i < mesh.getVertexes().length; i++) {
			int[] vertex = mesh.getBufferedVertex(i);
			vertex = RenderUtils.animate(vertex, animation);
			vertex = shader.shadeVertex(vertex, sceneObject.getTransform(), camera);
		}
		// get profiler values to update
		int maxPolys = Profiler.getInstance().getData().getMaxPolys();
		int rendPolys = Profiler.getInstance().getData().getRenderedPolys();
		// shade polygons
		for (int[] polygon : mesh.getPolygons()) {
			polygon = shader.shadePolygon(polygon, mesh, zBuffer, camera, lights);
			maxPolys++;
			if (polygon[Mesh.CULLED] == 0) rendPolys++;
		}
		// update profiler values
		Profiler.getInstance().getData().setMaxPolys(maxPolys);
		Profiler.getInstance().getData().setRenderedPolys(rendPolys);
	}
	
	/**
	 * Resets the depth buffer of this scene renderer.
	 */
	public void resetZBuffer() {
		for (int i = 0; i < zBuffer.length; i++) {
			zBuffer[i] = Integer.MIN_VALUE;
		}
	}
}
