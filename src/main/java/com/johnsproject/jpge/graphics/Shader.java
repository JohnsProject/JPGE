package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;

/**
 * The Shader class is used to used shade the vertexes and the polygons that are being rendered by the {@link SceneRenderer}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Shader {
	
	/**
	 * This method by the {@link SceneRenderer} at the rendering process.
	 * 
	 * @param vertex vertex to shade.
	 * @param sceneObjectTransform {@link Transform} of the {@link SceneObject} this vertex belongs to.
	 * @param camera {@link Camera} being rendered to.
	 * @return shaded vertex.
	 */
	public int[] shadeVertex(int[] vertex, Transform sceneObjectTransform, Camera camera) {
		Transform objt = sceneObjectTransform;
		Transform camt = camera.getTransform();
		//transforming vertex in object space
		vertex = Vector3MathUtils.movePointByScale(vertex, objt.getScale(), vertex);
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, objt.getRotation(), vertex);
		vertex = Vector3MathUtils.add(vertex, objt.getPosition(), vertex);
		//transforming vertex in camera space
		vertex = Vector3MathUtils.subtract(vertex, camt.getPosition(), vertex);
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, camt.getRotation(), vertex);
		//projecting vertex into screen coordinates
		vertex = RenderUtils.project(vertex, objt.getPosition(), camera);
		return vertex;
	}
	
	/**
	 * This method by the {@link SceneRenderer} at the rendering process.
	 * 
	 * @param polygon polygon to shade.
	 * @param mesh {@link Mesh} this polygon belongs to.
	 * @param camera {@link Camera} being rendered to.
	 * @param lights All {@link Light Lights} of the {@link Scene} being rendered.
	 * @return shaded polygon.
	 */
	int[] cache1 = new int[3];
	int[] cache2 = new int[3];
	int[] cache3 = new int[3];
	public int[] shadePolygon(int[] polygon, Mesh mesh, int[] zBuffer, Camera camera, List<Light> lights) {
		if (!RenderUtils.isInsideViewFrustum(polygon, mesh, camera)) {
			if (!RenderUtils.isBackface(polygon, mesh)) {
				int[] v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
				int[] v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
				int[] v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
				int l = 0;
				for (Light light : lights) {
					int[] lightPosition = light.getTransform().getPosition();
					cache1 = Vector3MathUtils.subtract(v1, v2, cache1);
					cache2 = Vector3MathUtils.subtract(v1, v3, cache2);
					cache3 = Vector3MathUtils.crossProduct(cache1, cache2, cache3);
					l += Vector3MathUtils.dotProduct(lightPosition, cache3);
					l -= light.getLightStrength() << 3;
				}
				v1[Mesh.SHADE_FACTOR] = l;
				v2[Mesh.SHADE_FACTOR] = l;
				v3[Mesh.SHADE_FACTOR] = l;
				RenderUtils.drawPolygon(polygon, mesh, zBuffer, camera);
			}
		}
		return polygon;
	}
	
}
