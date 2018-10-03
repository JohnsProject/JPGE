package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;

/**
 * The Shader class is used to used shade the vertexes and the polygons that are
 * being rendered by the {@link SceneRenderer}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Shader {

	// instead of instantiating a new int[] continuously why not use a cache?
	private int[] cache1 = new int[3];
	private int[] cache2 = new int[3];
	private int[] cache3 = new int[3];
	
	/**
	 * This method is called by the {@link SceneRenderer} at the rendering process.
	 * 
	 * @param vertex          	vertex to shade.
	 * @param camera 			{@link Camera} being rendered to.
	 * @param objectTransform 	{@link Transform} of the {@link SceneObject} this vertex belongs to.
	 * @param lights All 		{@link Light Lights} of the {@link Scene} being rendered.
	 * @return shaded vertex.
	 */
	public int[] shadeVertex(int[] vertex, Camera camera, Transform objectTransform, List<Light> lights) {
		Transform objt = objectTransform;
		Transform camt = camera.getTransform();
		// transform vertex in object space
		vertex = Vector3MathUtils.movePointByScale(vertex, objt.getScale(), vertex);
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, objt.getRotation(), vertex);
		// transform vertex to world space
		vertex = Vector3MathUtils.add(vertex, objt.getPosition(), vertex);
		// transform vertex to camera space
		vertex = Vector3MathUtils.subtract(vertex, camt.getPosition(), vertex);
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, camt.getRotation(), vertex);
		// project vertex into screen space
		vertex = RenderUtils.project(vertex, camera);
		return vertex;
	}

	/**
	 * This method is called by the {@link SceneRenderer} at the rendering process.
	 * 
	 * @param face   			face to shade.
	 * @param mesh   			{@link Mesh} this face belongs to.
	 * @param camera 			{@link Camera} being rendered to.
	 * @param zBuffer 			depth buffer used at the drawing process.
	 * @param objectTransform 	{@link Transform} of the {@link SceneObject} this face belongs to.
	 * @param lights All 		{@link Light Lights} of the {@link Scene} being rendered.
	 * @return shaded polygon.
	 */
	public int[] shadeFace(int[] face, Mesh mesh, Camera camera, int[] zBuffer, Transform objectTransform, List<Light> lights) {
		// view frustum culling
		if (!RenderUtils.isInsideViewFrustum(face, mesh, camera)) {
			int[] v1 = mesh.getBufferedVertex(face[Mesh.F_VERTEX_1]);
			int[] v2 = mesh.getBufferedVertex(face[Mesh.F_VERTEX_2]);
			int[] v3 = mesh.getBufferedVertex(face[Mesh.F_VERTEX_3]);
			// calculate normal
			cache1 = Vector3MathUtils.subtract(v1, v2, cache1);
			cache2 = Vector3MathUtils.subtract(v1, v3, cache2);
			cache3 = Vector3MathUtils.crossProduct(cache1, cache2, cache3);
			// backface culling using normal
			// also can use RenderUtils.isBackface(face, mesh) which is usually faster
			// but the normal is already used for lighting so why not use it here too
			if (cache3[2] < 0) {
				int l = 0;
				// flat shading
				for (Light light : lights) {
					int[] lightPosition = light.getDirection();
					l += Vector3MathUtils.dotProduct(lightPosition, cache3);
					l -= light.getLightStrength() << 3;
				}
				// set shade factor for each vertex
				v1[Mesh.V_SHADE_FACTOR] = l;
				v2[Mesh.V_SHADE_FACTOR] = l;
				v3[Mesh.V_SHADE_FACTOR] = l;
				// draw face
				RenderUtils.drawFace(face, mesh, zBuffer, camera);
			}
		}
		return face;
	}

}
