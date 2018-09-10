package com.johnsproject.jpge;

import java.util.List;

import com.johnsproject.jpge.graphics.Camera;
import com.johnsproject.jpge.graphics.Light;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.graphics.Shader;
import com.johnsproject.jpge.graphics.Transform;
import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.VertexUtils;

public class TestShader extends Shader{

	
	
	

	@Override
	public long shadeVertex(long vertex, Transform sceneObjectTransform, Camera camera, List<Light> lights) {
		Transform objt = sceneObjectTransform;
		Transform camt = camera.getTransform();
		long vector = VertexUtils.getVector(vertex);
		//transforming vertex in object space
		vector = VectorMathUtils.movePointByScale(vector, objt.getScale());
		vector = VectorMathUtils.movePointByAnglesXYZ(vector, objt.getRotation());
		vector = VectorMathUtils.add(vector, objt.getPosition());
		//test lighting
//		Light light = lights.get(0);
//		Transform lt = light.getTransform();
//		long lp = lt.getPosition();		
//		long vd = VectorMathUtils.getDistance(VectorMathUtils.normalize(lp),
//				VectorMathUtils.normalize(vector));
//		long d = VectorMathUtils.magnitude(vd)/14;
//		if (d > 15) d = 15;
//		d = 15-d;
//		vertex = VertexUtils.setShadeFactor(vertex, -d);
		//System.out.println(d);
		//transforming vertex in camera space
		vector = VectorMathUtils.subtract(vector, camt.getPosition());
		vector = VectorMathUtils.movePointByAnglesXYZ(vector, camt.getRotation());
		//projecting vertex into screen coordinates
		vector = RenderUtils.project(vector, objt.getPosition(), camera);
		vertex = VertexUtils.setVector(vertex, vector);
		return vertex;
	}

	@Override
	public int[] shadePolygon(int[] polygon, Mesh mesh, int[] zBuffer, Camera camera) {
		if (!RenderUtils.isInsideViewFrustum(polygon, mesh, camera)) {
			if (!RenderUtils.isBackface(polygon, mesh)) {
				RenderUtils.drawPolygon(polygon, mesh, zBuffer, camera);
			}
		}
		return polygon;
		//return super.shadePolygon(polygon, mesh, camera);
	}

}
