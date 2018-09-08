package com.johnsproject.jpge.utils;

import com.johnsproject.jpge.graphics.Camera;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.SceneObject;

/**
 * The RenderUtils class provides methods needed at the rendering process.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class RenderUtils {

	/**
	 * Projects a vector to from world (3D) to screen (2D) coordinates.
	 * 
	 * @param vector vector to be projected.
	 * @param objectPosition position of the {@link SceneObject} containing this vector.
	 *  If no there is no {@link SceneObject} set as 0.
	 * @param camera {@link Camera} this vector will be projected to.
	 * @return
	 */
	public static long project(long vector, long objectPosition, Camera camera) {
		long px = 0, py = 0, pz = 0;
		int fov = camera.getFieldOfView(), rescalef = camera.getScaleFactor();
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			long z = (Vector3Utils.getZ(vector) + fov);
			if (z <= 0) z = 1;
			px = ((Vector3Utils.getX(vector) * rescalef * fov)) / z;
			py = ((Vector3Utils.getY(vector) * rescalef * fov)) / z;
			pz = z + (Vector3Utils.getZ(vector)<<1);
			break;
		case orthographic: // this projectionType ignores depth
			px = (Vector3Utils.getX(vector) * rescalef)>>5;
			py = (Vector3Utils.getY(vector) * rescalef)>>5;
			pz = Vector3Utils.getZ(vector) + Vector3Utils.getZ(objectPosition);
			break;
		}
		long x = (px) + Vector2Utils.getX(camera.getHalfScreenSize()) + Vector3Utils.getX(objectPosition);
		long y = (py) + Vector2Utils.getY(camera.getHalfScreenSize()) + Vector3Utils.getY(objectPosition);
		long z = pz;
		return Vector3Utils.convert(x, y, z);
	}

	/**
	 * This method checks if the given polygon is inside the view frustum of the given camera.
	 * 
	 * @param polygon a projected polygon.
	 * @param mesh {@link Mesh} that contains this polygon.
	 * @param camera {@link Camera}.
	 * @return if the given polygon is inside the view frustum of the given camera.
	 */
	public static boolean isInsideViewFrustum(int[] polygon, Mesh mesh, Camera camera) {
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
	/**
	 * This method checks if the given polygon is a backface.
	 * If this polygon is a backface then this method returns true else it returns false.
	 * 
	 * @param polygon a projected polygon.
	 * @param mesh {@link Mesh} that contains this polygon.
	 * @return if the given polygon is a backface.
	 */
	public static boolean isBackface(int[] polygon, Mesh mesh) {
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
