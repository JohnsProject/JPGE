package com.johnsproject.jpge.physics;

import com.johnsproject.jpge.dto.Rigidbody;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class PhysicsAnimator {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	public void animate(Scene scene) {
		for (int i = 0; i < scene.getSceneObjects().size(); i++) {
			SceneObject sceneObject = scene.getSceneObjects().get(i);
			Transform transform = sceneObject.getTransform();
			Rigidbody rigidbody = sceneObject.getRigidbody();
			if (rigidbody.getColliderRadius() == -1)
				rigidbody.setColliderRadius(getRadius(sceneObject));
			PhysicsSettings settings = scene.getPhysicsSettings();
			detectCollision(scene, sceneObject);
			if (!rigidbody.isColliding()) {
				applyGravity(rigidbody, settings.getGravity());
			}
			transform.translate(rigidbody.getVelocity());
		}
	}
	
	
	void applyGravity (Rigidbody rigidbody, int[] gravity) {
		if(rigidbody.useGravity()) {
			long fallTime = System.currentTimeMillis() - rigidbody.getLastCollisionTime();
			int t = (int)(fallTime * fallTime);
			//		((Gravity * Time / 2) / 1024) / 1024
			// use bitshifting because its faster and has no need to be so precise
			int x = (((gravity[vx] * t) >> 1) >> 10) >> 10;
			int y = (((gravity[vy] * t) >> 1) >> 10) >> 10;
			int z = (((gravity[vz] * t) >> 1) >> 10) >> 10;
			rigidbody.accelerate(x, y, z);
		}
	}
	
	void detectCollision(Scene scene, SceneObject sceneObject) {
		for (int j = 0; j < scene.getSceneObjects().size(); j++) {
			SceneObject so = scene.getSceneObjects().get(j);
			if (detectCollision(sceneObject, so)) {
				sceneObject.getRigidbody().collisionEnter(so.getName());
			}else {
				sceneObject.getRigidbody().collisionExit(so.getName());
			}
		}
	}
	
	boolean detectCollision(SceneObject sceneObject1, SceneObject sceneObject2) {
		Rigidbody rb1 = sceneObject1.getRigidbody();
		Rigidbody rb2 = sceneObject2.getRigidbody();
		if(!sceneObject2.getName().equals(sceneObject1.getName())) {
			if(sphereToSphere(sceneObject1, sceneObject2)) {
				if (rb1.getCollisionType() + rb2.getCollisionType() == Rigidbody.COLLISION_SPHERE*2)
					return true;
//				if (rb1.getCollisionType() + rb2.getCollisionType() == Rigidbody.COLLISION_SPHERE + Rigidbody.COLLISION_TERRAIN) { 
//					if (rb1.getCollisionType() == Rigidbody.COLLISION_SPHERE)
//						return sphereToTerrain(sceneObject1, sceneObject2);
//					else 
//						return sphereToTerrain(sceneObject2, sceneObject1);
//				}
//				if (rb1.getCollisionType() + rb2.getCollisionType() == Rigidbody.COLLISION_AABB*2) {
//					return AABBToAABB(sceneObject1, sceneObject2);
//				}
			}
		}
		return false;
	}
	
	boolean sphereToSphere(SceneObject sceneObject1, SceneObject sceneObject2) {
		Rigidbody rigidbody1 = sceneObject1.getRigidbody();
		Rigidbody rigidbody2 = sceneObject2.getRigidbody();
		int[] pos1 = sceneObject1.getTransform().getPosition();
		int[] pos2 = sceneObject2.getTransform().getPosition();
		int distance = Vector3MathUtils.distance(pos2, pos1);
		int radius1 = rigidbody1.getColliderRadius();
		int radius2 = rigidbody2.getColliderRadius();
		int radius = radius1 + radius2;
		if (distance <= radius) {
			return true;
		}
		return false;
	}
	
//	private int[] vector = new int[3];
//	boolean sphereToTerrain(SceneObject sceneObject1, SceneObject sceneObject2) {
//		Rigidbody rigidbody1 = sceneObject1.getRigidbody();
//		int[] pos1 = sceneObject1.getTransform().getPosition();
//		int[] pos2 = sceneObject2.getTransform().getPosition();
////		Vector3MathUtils.subtract(pos2, pos1, vector);
//		int radius = rigidbody1.getColliderRadius();
//		for (int i = 0; i < sceneObject2.getMesh().getVertexes().length; i++) {
//			int[] pos = sceneObject2.getMesh().getVertex(i).getPosition();
//			if (pos[vx] - pos1[vx] <= radius
//				&& pos[vy] - pos1[vy] <= radius
//				&& pos[vz] - pos1[vz] <= radius) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	private int[] bb1 = new int[3];
//	private int[] bb2 = new int[3];
//	boolean AABBToAABB(SceneObject sceneObject1, SceneObject sceneObject2) {
//		int[] pos1 = sceneObject1.getTransform().getPosition();
//		int[] pos2 = sceneObject2.getTransform().getPosition();
//		getBoundingBox(sceneObject1, bb1);
//		getBoundingBox(sceneObject2, bb2);
//		if((pos1[vx] <= pos2[vx] + bb2[vx])
//			&& (pos1[vx] + bb1[vx] >= pos2[vx])
//			&& (pos1[vy] <= pos2[vy] + bb2[vy])
//			&& (pos1[vy] + bb1[vy] >= pos2[vy])
//			&& (pos1[vz] <= pos2[vz] + bb2[vz])
//			&& (pos1[vz] + bb1[vz] >= pos2[vz])) {
//			return true;
//		}
//		return false;
//	}
	
	int getRadius(SceneObject sceneObject) {
		int radius = 0;
		for (int i = 0; i < sceneObject.getMesh().getVertexes().length; i++) {
			int[] pos = sceneObject.getMesh().getVertex(i).getPosition();
			if(radius < pos[vx]) radius = pos[vx];
			if(radius < pos[vy]) radius = pos[vy];
			if(radius < pos[vz]) radius = pos[vz];
		}
		return radius;
	}
	
	private int[] vectorCache = new int[3];
	private int[] bbMin = new int[3];
	private int[] bbMax = new int[3];
	void getBoundingBox(SceneObject sceneObject, int[] out) {
		bbMin[vx] = 0; bbMin[vy] = 0; bbMin[vz] = 0;
		bbMax[vx] = 0; bbMax[vy] = 0; bbMax[vz] = 0;
		for (int i = 0; i < sceneObject.getMesh().getVertexes().length; i++) {
			int[] pos = sceneObject.getMesh().getVertex(i).getPosition();
			Vector3MathUtils.movePointByScale(pos, sceneObject.getTransform().getScale(), vectorCache);
			Vector3MathUtils.movePointByAnglesXYZ(vectorCache, sceneObject.getTransform().getRotation(), vectorCache);
			if(bbMin[vx] > vectorCache[vx]) bbMin[vx] = vectorCache[vx];
			if(bbMin[vy] > vectorCache[vy]) bbMin[vy] = vectorCache[vy];
			if(bbMin[vz] > vectorCache[vz]) bbMin[vz] = vectorCache[vz];
			if(bbMax[vx] < vectorCache[vx]) bbMax[vx] = vectorCache[vx];
			if(bbMax[vy] < vectorCache[vy]) bbMax[vy] = vectorCache[vy];
			if(bbMax[vz] < vectorCache[vz]) bbMax[vz] = vectorCache[vz];
		}
		Vector3MathUtils.subtract(bbMax, bbMin, out);
	}
}
