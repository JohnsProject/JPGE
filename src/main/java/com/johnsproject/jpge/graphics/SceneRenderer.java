package com.johnsproject.jpge.graphics;

import java.util.List;
import com.johnsproject.jpge.utils.Vector2Utils;
import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.Vector3Utils;
import com.johnsproject.jpge.utils.VertexUtils;

/**
 * The SceneRenderer class renders the {@link Scene} assigned to the {@link SceneFrame}.
 * It takes the {@link SceneObject SceneObjects} in the view of all {@link Camera Cameras} in the {@link Scene}, 
 * transforms and projects them. The objects are then drawn by the {@link SceneRasterizer}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneRenderer{
	public enum ProjectionType {
		orthographic, perspective
	}	

	public enum RenderingType {
		wireframe, solid, textured
	}
	
	public void render(Scene scene) {
		for (Camera camera : scene.getCameras()) {
			for (SceneObject sceneObject : scene.getSceneObjects()) {
				if (sceneObject.changed() || camera.changed()) {
					render(sceneObject, camera, scene.getLights());
				}
			}
			camera.changed(false);
		}
		for (SceneObject sceneObject : scene.getSceneObjects()) {
			sceneObject.changed(false);
		}
	}
	
	void render(SceneObject sceneObject, Camera camera, List<Light> lights) {
		Mesh mesh = sceneObject.getMesh();
//		Animation animation = mesh.getCurrentAnimation();
		Transform objt = sceneObject.getTransform();
		mesh.resetBuffer();
		for (int i = 0; i < mesh.getVertexes().length; i++) {
			long vertex = mesh.getVertex(i);
			long vector = VertexUtils.getVector(vertex);
			vector = VectorMathUtils.movePointByScale(vector, objt.getScale());
//			for (int j = 0; j <= VertexUtils.getBoneIndex(vertex); j++) {
//				Transform bone = animation.getBone(j, animation.getCurrentFrame());
//				vector = VectorMathUtils.movePointByScale(vector, bone.getScale());
//				vector = VectorMathUtils.movePointByAnglesXYZ(vector, bone.getRotation());
//				//vector = VectorMathUtils.add(vector, bone.getPosition());
//			}
			vector = VectorMathUtils.movePointByAnglesXYZ(vector, objt.getRotation());
			vector = projectVertex(vector, objt.getPosition(), camera);
			vertex = VertexUtils.setVector(vertex, vector);
			mesh.setBufferedVertex(i, vertex);
		}
		for (int[] polygon : mesh.getPolygons()) {
			polygon[Mesh.CULLED] = 1;
			if (!cullViewFrustum(polygon, mesh, camera)) {
				if (!cullBackface(polygon, mesh)) {
					polygon[Mesh.CULLED] = 0;
				}
			}
		}
	}

	long projectVertex(long vertex, long objectPosition, Camera camera) {
		long px = 0, py = 0, pz = 0;
		int fov = camera.getFieldOfView(), rescalef = camera.getScaleFactor();
		long camRot = camera.getTransform().getRotation(),
				camPos = camera.getTransform().getPosition();
		long pos = VectorMathUtils.add(vertex, objectPosition);
		pos = VectorMathUtils.subtract(pos, camPos);
		pos = VectorMathUtils.movePointByAnglesXYZ(pos, camRot);
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			long z = (Vector3Utils.getZ(pos) + fov);
			if (z <= 0) z = 1;
			px = ((Vector3Utils.getX(pos)) * rescalef * fov) / z;
			py = ((Vector3Utils.getY(pos)) * rescalef * fov) / z;
			pz = z + (Vector3Utils.getZ(pos)<<1);
			break;
		case orthographic: // this projectionType ignores depth
			px = (Vector3Utils.getX(pos) * rescalef)>>5;
			py = (Vector3Utils.getY(pos) * rescalef)>>5;
			pz = Vector3Utils.getZ(pos) + Vector3Utils.getZ(objectPosition);
			break;
		}
		long x = (px) + Vector2Utils.getX(camera.getHalfScreenSize()) + Vector3Utils.getX(objectPosition);
		long y = (py) + Vector2Utils.getY(camera.getHalfScreenSize()) + Vector3Utils.getY(objectPosition);
		long z = pz;
		return Vector3Utils.convert(x, y, z);
	}
	
	boolean cullViewFrustum(int[] polygon, Mesh mesh, Camera camera) {
		long v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		int ncp = camera.getNearClippingPlane();
		int fcp = camera.getFarClippingPlane();
		int w = camera.getWidth(), h = camera.getHeight();
		int x = (int)Vector3Utils.getX(v1), y = (int)Vector3Utils.getY(v1), z = (int)Vector3Utils.getZ(v1);
		if (x > -400 && x < w+400 && y > -400 && y < h+400 && z > ncp && z < fcp)
			return false;
		return true;
	}
	
	//backface culling with sholeance algorithm
	boolean cullBackface(int[] polygon, Mesh mesh) {
		long v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]),
				v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]),
				v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int v1x = (int)Vector3Utils.getX(v1), v1y = (int)Vector3Utils.getY(v1);
		int v2x = (int)Vector3Utils.getX(v2), v2y = (int)Vector3Utils.getY(v2);
		int v3x = (int)Vector3Utils.getX(v3), v3y = (int)Vector3Utils.getY(v3);
		int a = ((v2x - v1x) * (v3y - v1y) - (v3x - v1x) * (v2y - v1y)) >> 1;
		if (a < 0) return false;
		return true;
	}
}
