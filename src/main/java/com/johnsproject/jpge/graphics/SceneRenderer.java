package com.johnsproject.jpge.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.Vector2Utils;

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
	}
	
	/**
	 * Tells this scene renderer to render the given {@link Scene}.
	 * 
	 * @param scene {@link Scene} to render.
	 */
	public void render(Scene scene, int lastTime) {
		resetZBuffer();
		synchronized (scene.getCameras()) {
			for (Camera camera : scene.getCameras()) {
				camera.getViewGraphics().clearRect(0, 0, Vector2Utils.getX(camera.getScreenSize()),  Vector2Utils.getY(camera.getScreenSize()));
				for (SceneObject sceneObject : scene.getSceneObjects()) {
					if (sceneObject.isActive() && (sceneObject.changed() || camera.changed())) {
						render(sceneObject, camera, scene.getLights());
					}
				}
				camera.changed(false);
				logData(camera, lastTime);
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
		mesh.resetBuffer();
		for (int i = 0; i < mesh.getVertexes().length; i++) {
			long vertex = mesh.getVertex(i);
			vertex = RenderUtils.animate(vertex, animation);
			vertex = shader.shadeVertex(vertex, sceneObject.getTransform(), camera, lights);
			mesh.setBufferedVertex(i, vertex);
		}
		for (int[] polygon : mesh.getPolygons()) {
			polygon = shader.shadePolygon(polygon, mesh, zBuffer, camera);
		}
	}
	
	/**
	 * Resets the depth buffer of this scene renderer.
	 */
	public void resetZBuffer() {
		for (int i = 0; i < zBuffer.length; i++) {
			zBuffer[i] = Integer.MIN_VALUE;
		}
	}

	private final Color backColor = new Color(160, 160, 160, 160);
	
	private int afps = 1, cycles = 1, maxPolys = 0, renderedPolys = 0;
	
	void logData(Camera camera, int lastTime) {
		Graphics buffer = camera.getViewGraphics();
		 buffer.setColor(backColor);
		 buffer.fillRect(5, 38, 230, 120);
		 buffer.setColor(Color.WHITE);
		 int maxMem = Math.round(Runtime.getRuntime().totalMemory() >> 20);
		 int freeMem = Math.round(Runtime.getRuntime().freeMemory() >> 20);
		 int fps  = 1000/(((lastTime) / 1000000)+1);
		 int y = 50, step = 17;
		 buffer.drawString("Average FPS : " + (afps/cycles) + " , FPS : " + fps, 10, y);
		 updateAFPS(fps);
		 y += step;
		 buffer.drawString("Render Time : " + (lastTime / 1000000) + " ms", 10, y);
		 y += step;
		 buffer.drawString("Rendered Polygons : " + renderedPolys + " / " + maxPolys, 10, y);
		 y += step;
		 buffer.drawString("Rendering resolution : " + camera.getWidth() + "x" + camera.getHeight(), 10, y);
		 y += step;
		 buffer.drawString("Memory usage : " + (maxMem - freeMem) + " / " + maxMem + " MB", 10, y);
		 y += step;
		 buffer.drawString("Projection type : " + camera.getProjectionType().toString(), 10, y);
		 y += step;
		 buffer.drawString("Rendering type : " + camera.getRenderingType().toString(), 10, y);
	}
	
	void updateAFPS(int fps) {
		afps += fps;
		cycles++;
		if (cycles > 1000) {
			afps = afps/cycles;
			cycles = 1;
		}
	}
}
