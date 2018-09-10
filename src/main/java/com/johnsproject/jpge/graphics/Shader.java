package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.VertexUtils;

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
	public long shadeVertex(long vertex, Transform sceneObjectTransform, Camera camera, List<Light> lights) {
		Transform objt = sceneObjectTransform;
		Transform camt = camera.getTransform();
		long vector = VertexUtils.getVector(vertex);
		//transforming vertex in object space
		vector = VectorMathUtils.movePointByScale(vector, objt.getScale());
		vector = VectorMathUtils.movePointByAnglesXYZ(vector, objt.getRotation());
		vector = VectorMathUtils.add(vector, objt.getPosition());
		//transforming vertex in camera space
		vector = VectorMathUtils.subtract(vector, camt.getPosition());
		vector = VectorMathUtils.movePointByAnglesXYZ(vector, camt.getRotation());
		//projecting vertex into screen coordinates
		vector = RenderUtils.project(vector, objt.getPosition(), camera);
		vertex = VertexUtils.setVector(vertex, vector);
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
			}
		}
		return polygon;
	}
	
}
