package com.johnsproject.jpge;

import java.util.List;

import com.johnsproject.jpge.graphics.Camera;
import com.johnsproject.jpge.graphics.Light;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.graphics.Shader;
import com.johnsproject.jpge.graphics.Transform;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class TestShader extends Shader{

//	@Override
//	public int[] shadeVertex(int[] vertex, Transform objectTransform) {
////		Transform objt = objectTransform;
////		// transform vertex in object space
////		vertex = Vector3MathUtils.movePointByScale(vertex, objt.getScale(), vertex);
////		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, objt.getRotation(), vertex);
////		// transform vertex to world space
////		vertex = Vector3MathUtils.add(vertex, objt.getPosition(), vertex);
//		return vertex;
//	}
//
//	int[] cache1 = new int[3];
//	int[] cache2 = new int[3];
//	int[] cache3 = new int[3];
//	int[][] shadowVerts = null;
//	@Override
//	public int[] shadeFace(int[] face, Mesh mesh, Camera camera, List<Light> lights) {
////		if (shadowVerts == null) shadowVerts = new int[mesh.getVertexes().length][Mesh.VERTEX_LENGTH];
////		int[] v1 = mesh.getBufferedVertex(face[Mesh.VERTEX_1]);
////		int[] v2 = mesh.getBufferedVertex(face[Mesh.VERTEX_2]);
////		int[] v3 = mesh.getBufferedVertex(face[Mesh.VERTEX_3]);
////		int[] sv1 = shadowVerts[face[Mesh.VERTEX_1]];
////		int[] sv2 = shadowVerts[face[Mesh.VERTEX_2]];
////		int[] sv3 = shadowVerts[face[Mesh.VERTEX_3]];
////		int l = 0;
////		Light light = lights.get(0);
////		int[] lightPosition = light.getDirection();
////		Vector3MathUtils.subtract(v1, v2, cache1);
////		Vector3MathUtils.subtract(v1, v3, cache2);
////		Vector3MathUtils.crossProduct(cache1, cache2, cache3);
////		l += Vector3MathUtils.dotProduct(lightPosition, cache3);
////		l -= light.getLightStrength() << 3;
////		v1[Mesh.SHADE_FACTOR] = l;
////		v2[Mesh.SHADE_FACTOR] = l;
////		v3[Mesh.SHADE_FACTOR] = l;
////		sv1 = VectorUtils.copy3(sv1, v1);
////		sv2 = VectorUtils.copy3(sv2, v2);
////		sv3 = VectorUtils.copy3(sv3, v3);
////		Vector3MathUtils.add(sv1, lightPosition, sv1);
////		Vector3MathUtils.add(sv2, lightPosition, sv2);
////		Vector3MathUtils.add(sv3, lightPosition, sv3);
////		sv1[1] = 200;
////		sv2[1] = 200;
////		sv3[1] = 200;
//		return face;
//	}
//
//	@Override
//	public int[] shadeVertex(int[] vertex, Camera camera) {
////		Transform camt = camera.getTransform();
////		// transform vertex in camera space
////		vertex = Vector3MathUtils.subtract(vertex, camt.getPosition(), vertex);
////		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, camt.getRotation(), vertex);
////		// project vertex into screen space
////		vertex = RenderUtils.project(vertex, camera);
//		return vertex;
//	}
//
//	int[] vertCache = new int[3];
//	int[] vertCache1 = new int[3];
//	int[] vertCache2 = new int[3];
//	int[] vertCache3 = new int[3];
//	@Override
//	public int[] shadeFace(int[] face, Mesh mesh, int[] zBuffer, Camera camera, Transform objectTransform) {
////		Transform camt = camera.getTransform();
////		int[] v1 = mesh.getBufferedVertex(face[Mesh.VERTEX_1]);
////		int[] v2 = mesh.getBufferedVertex(face[Mesh.VERTEX_2]);
////		int[] v3 = mesh.getBufferedVertex(face[Mesh.VERTEX_3]);
////		int[] sv1 = shadowVerts[face[Mesh.VERTEX_1]];
////		int[] sv2 = shadowVerts[face[Mesh.VERTEX_2]];
////		int[] sv3 = shadowVerts[face[Mesh.VERTEX_3]];
////		VectorUtils.copy3(vertCache, sv1);
////		VectorUtils.copy3(vertCache1, v1);
////		Vector3MathUtils.subtract(sv1, camt.getPosition(), sv1);
////		Vector3MathUtils.movePointByAnglesXYZ(sv1, camt.getRotation(), sv1);
////		VectorUtils.copy3(v1, RenderUtils.project(sv1, camera));
////		VectorUtils.copy3(sv1, vertCache);
////		VectorUtils.copy3(vertCache, sv2);
////		VectorUtils.copy3(vertCache2, v2);
////		Vector3MathUtils.subtract(sv2, camt.getPosition(), sv2);
////		Vector3MathUtils.movePointByAnglesXYZ(sv2, camt.getRotation(), sv2);
////		VectorUtils.copy3(v2, RenderUtils.project(sv2, camera));
////		VectorUtils.copy3(sv2, vertCache);
////		VectorUtils.copy3(vertCache, sv3);
////		VectorUtils.copy3(vertCache3, v3);
////		Vector3MathUtils.subtract(sv3, camt.getPosition(), sv3);
////		Vector3MathUtils.movePointByAnglesXYZ(sv3, camt.getRotation(), sv3);
////		VectorUtils.copy3(v3, RenderUtils.project(sv3, camera));
////		VectorUtils.copy3(sv3, vertCache);
////		RenderingType type = camera.getRenderingType();
////		int color = mesh.getMaterial(face[Mesh.MATERIAL_INDEX]).getColor();
////		camera.setRenderingType(RenderingType.solid);
////		mesh.getMaterial(face[Mesh.MATERIAL_INDEX]).setColor(ColorUtils.convert(0, 0, 0, 150));
////		if (!RenderUtils.isInsideViewFrustum(face, mesh, camera)) {
////			if (!RenderUtils.isBackface(face, mesh)) {
////				v1[2] = 10000000;
////				v2[2] = 10000000;
////				v3[2] = 10000000;
////				RenderUtils.drawFace(face, mesh, zBuffer, camera);
////			}
////		}
////		mesh.getMaterial(face[Mesh.MATERIAL_INDEX]).setColor(color);
////		VectorUtils.copy3(v1, vertCache1);
////		VectorUtils.copy3(v2, vertCache2);
////		VectorUtils.copy3(v3, vertCache3);
////		camera.setRenderingType(type);
//		int[] v1 = mesh.getBufferedVertex(face[Mesh.VERTEX_1]);
//		int[] v2 = mesh.getBufferedVertex(face[Mesh.VERTEX_2]);
//		int[] v3 = mesh.getBufferedVertex(face[Mesh.VERTEX_3]);
//		VectorUtils.copy3(vertCache1, v1);
//		VectorUtils.copy3(vertCache2, v2);
//		VectorUtils.copy3(vertCache3, v3);
//		shadeVertexTest(v1, camera, objectTransform);
//		shadeVertexTest(v2, camera, objectTransform);
//		shadeVertexTest(v3, camera, objectTransform);
//		if (!RenderUtils.isInsideViewFrustum(face, mesh, camera)) {
//			if (!RenderUtils.isBackface(face, mesh)) {
//				RenderUtils.drawFace(face, mesh, zBuffer, camera);
//			}
//		}
//		VectorUtils.copy3(v1, vertCache1);
//		VectorUtils.copy3(v2, vertCache2);
//		VectorUtils.copy3(v3, vertCache3);
//		return face;
//	}
//	
//
//	public int[] shadeVertexTest(int[] vertex, Camera camera, Transform objectTransform) {
//		Transform objt = objectTransform;
//		Transform camt = camera.getTransform();
//		// transform vertex in object space
//		vertex = Vector3MathUtils.movePointByScale(vertex, objt.getScale(), vertex);
//		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, objt.getRotation(), vertex);
//		// transform vertex to world space
//		vertex = Vector3MathUtils.add(vertex, objt.getPosition(), vertex);
//		// transform vertex in camera space
//		vertex = Vector3MathUtils.subtract(vertex, camt.getPosition(), vertex);
//		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, camt.getRotation(), vertex);
//		// project vertex into screen space
//		vertex = RenderUtils.project(vertex, camera);
//		return vertex;
//	}
	
}
