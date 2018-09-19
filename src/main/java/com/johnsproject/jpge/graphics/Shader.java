package com.johnsproject.jpge.graphics;

import java.util.Arrays;
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
	 * @param lights All {@link Light Lights} of the {@link Scene} being rendered.
	 * @return shaded vertex.
	 */
	public int[] shadeVertex(int[] vertex, Transform sceneObjectTransform, Camera camera, List<Light> lights) {
		Transform objt = sceneObjectTransform;
		Transform camt = camera.getTransform();
		//transforming vertex in object space
		vertex = Vector3MathUtils.movePointByScale(vertex, objt.getScale());
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, objt.getRotation());
		vertex = Vector3MathUtils.add(vertex, objt.getPosition());
		//transforming vertex in camera space
		vertex = Vector3MathUtils.subtract(vertex, camt.getPosition());
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, camt.getRotation());
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
	 * @return shaded polygon.
	 */
	public int[] shadePolygon(int[] polygon, Mesh mesh, int[] zBuffer, Camera camera) {
		if (!RenderUtils.isInsideViewFrustum(polygon, mesh, camera)) {
			if (!RenderUtils.isBackface(polygon, mesh)) {
				RenderUtils.drawPolygon(polygon, mesh, zBuffer, camera);
				//System.out.println(Arrays.toString(polygon));
			}
		}
		return polygon;
	}
	
}
