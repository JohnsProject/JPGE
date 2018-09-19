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
	public int[] shadeVertex(int[] vertex, Transform sceneObjectTransform, Camera camera, List<Light> lights) {
		Transform objt = sceneObjectTransform;
		Transform camt = camera.getTransform();
		//transforming vertex in object space
		vertex = Vector3MathUtils.movePointByScale(vertex, objt.getScale());
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, objt.getRotation());
		//transforming vertex to world space
		vertex = Vector3MathUtils.add(vertex, objt.getPosition());
		//test lighting
//		Light light = lights.get(0);
//		Transform lt = light.getTransform();
//		long lp = lt.getPosition();
//		lp = VectorMathUtils.getDistance(lp,objt.getPosition());
//		long vd = VectorMathUtils.getDistance(VectorMathUtils.normalize(lp),
//				VectorMathUtils.normalize(vector));
//		//System.out.println(Vector3Utils.toString(vd));
//		long d = VectorMathUtils.magnitude(vd)>>5;
//		if (d > 15) d = 15;
//		d = 15-d;
		//vertex = VertexUtils.setShadeFactor(vertex, -d);
//		int d = -Vector3Utils.getZ(vector)>>6;
//		vertex = VertexUtils.setShadeFactor(vertex, d);
//		System.out.println(d);
		//transforming vertex in camera space
		vertex = Vector3MathUtils.subtract(vertex, camt.getPosition());
		vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, camt.getRotation());
		//projecting vertex into screen coordinates
		vertex = RenderUtils.project(vertex, objt.getPosition(), camera);
		return vertex;
	}

	@Override
	public int[] shadePolygon(int[] polygon, Mesh mesh, int[] zBuffer, Camera camera) {
//		camera.setRenderingType(RenderingType.solid);
		if (!RenderUtils.isInsideViewFrustum(polygon, mesh, camera)) {
			if (!RenderUtils.isBackface(polygon, mesh)) {
//				long v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
//				long v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
//				long v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
//				int v1x = (int)Vector3Utils.getX(v1), v1y = (int)Vector3Utils.getY(v1);
//				int v2x = (int)Vector3Utils.getX(v2), v2y = (int)Vector3Utils.getY(v2);
//				int v3x = (int)Vector3Utils.getX(v3), v3y = (int)Vector3Utils.getY(v3);
//				int a = ((v2x - v1x) * (v3y - v1y) - (v3x - v1x) * (v2y - v1y)) / 2;
//				if (a > -1) v1 = VertexUtils.setShadeFactor(v1, 15);
//				mesh.setBufferedVertex(polygon[Mesh.VERTEX_1], v1);
				RenderUtils.drawPolygon(polygon, mesh, zBuffer, camera);
			}
		}
		return polygon;
		//return super.shadePolygon(polygon, mesh, camera);
	}

}
