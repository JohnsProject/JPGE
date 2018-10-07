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
	 * @param vertex          	{@link Vertex} to shade.
	 * @param mesh   			{@link Mesh} this face belongs to.
	 * @param camera 			{@link Camera} being rendered to.
	 * @param objectTransform 	{@link Transform} of the {@link SceneObject} this vertex belongs to.
	 * @param lights All 		{@link Light Lights} of the {@link Scene} being rendered.
	 * @return shaded vertex.
	 */
	public Vertex shadeVertex(Vertex vertex, Mesh mesh, Camera camera, Transform objectTransform, List<Light> lights) {
		Transform objt = objectTransform;
		Transform camt = camera.getTransform();
		int[] pos = vertex.getPosition();
		int[] normal = vertex.getNormal();
		// transform vertex in object space
		pos = Vector3MathUtils.movePointByScale(pos, objt.getScale(), pos);
		pos = Vector3MathUtils.movePointByAnglesXYZ(pos, objt.getRotation(), pos);
		// transform vertex to world space
		pos = Vector3MathUtils.add(pos, objt.getPosition(), pos);
		// transform vertex to camera space
		pos = Vector3MathUtils.subtract(pos, camt.getPosition(), pos);
		pos = Vector3MathUtils.movePointByAnglesXYZ(pos, camt.getRotation(), pos);
		// project vertex into screen space
		pos = RenderUtils.project(pos, camera);
		// transform normal in object space
		normal = Vector3MathUtils.movePointByScale(normal, objt.getScale(), normal);
		normal = Vector3MathUtils.movePointByAnglesXYZ(normal, objt.getRotation(), normal);
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
	public Face shadeFace(Face face, Mesh mesh, Camera camera, int[] zBuffer, Transform objectTransform, List<Light> lights) {
		// view frustum culling
		if (!RenderUtils.isInsideViewFrustum(face, mesh, camera)) {
//			Vertex v1 = mesh.getBufferedVertex(face.getVertex1());
//			Vertex v2 = mesh.getBufferedVertex(face.getVertex2());
//			Vertex v3 = mesh.getBufferedVertex(face.getVertex3());
//			cache3 = v1.getNormal();
			if (!RenderUtils.isBackface(face, mesh)) {
//				int l = 0;
//				// flat shading
//				for (Light light : lights) {
//					int[] lightPosition = light.getDirection();
//					l += Vector3MathUtils.dotProduct(lightPosition, cache3);
//					l -= light.getLightStrength() << 3;
//				}
//				// set shade factor for each vertex
//				v1.setShadeFactor(l);
//				v2.setShadeFactor(l);
//				v3.setShadeFactor(l);
				// draw face
				RenderUtils.drawFace(face, mesh, zBuffer, camera);
			}
		}
		return face;
	}

}
