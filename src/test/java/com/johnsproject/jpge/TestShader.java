package com.johnsproject.jpge;

import java.util.List;

import com.johnsproject.jpge.graphics.Camera;
import com.johnsproject.jpge.graphics.Light;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.Shader;
import com.johnsproject.jpge.graphics.Transform;
import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;

public class TestShader extends Shader{

	
	
	

	@Override
	public int[] shadeVertex(int[] vertex, Transform sceneObjectTransform, Camera camera) {
		Transform objt = sceneObjectTransform;
		Transform camt = camera.getTransform();
		//transform vertex in object space
		vertex = Vector3MathUtils.movePointByScale(vertex, objt.getScale(), vertex);
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, objt.getRotation(), vertex);
		//transform vertex to world space
		vertex = Vector3MathUtils.add(vertex, objt.getPosition(), vertex);
		//transform vertex in camera space
		vertex = Vector3MathUtils.subtract(vertex, camt.getPosition(), vertex);
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, camt.getRotation(), vertex);
		//project vertex into screen space
		vertex = RenderUtils.project(vertex, camera);
		return vertex;
	}

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
					l -= light.getLightStrength();
				}
				v1[Mesh.SHADE_FACTOR] = l;
				v2[Mesh.SHADE_FACTOR] = l;
				v3[Mesh.SHADE_FACTOR] = l;
				RenderUtils.drawFace(polygon, mesh, zBuffer, camera);
			}
		}
		return polygon;
	}

}
