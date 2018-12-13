/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge.physics;

import com.johnsproject.jpge.dto.PhysicsSettings;
import com.johnsproject.jpge.dto.Rigidbody;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.utils.MathUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class PhysicsAnimator {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	private static int[] vectorCache1 = new int[3];
	private static int[] vectorCache2 = new int[3];

	public void animate(Scene scene) {
		for (int i = 0; i < scene.getSceneObjects().size(); i++) {
			SceneObject sceneObject = scene.getSceneObjects().get(i);
			Transform transform = sceneObject.getTransform();
			Rigidbody rigidbody = sceneObject.getRigidbody();
			if (rigidbody.getColliderRadius() == -1) {
				rigidbody.setColliderRadius(getRadius(sceneObject));
			}
			PhysicsSettings settings = scene.getPhysicsSettings();
			detectCollision(scene, sceneObject);
			if (!rigidbody.isColliding() && !rigidbody.isKinematic()) {
				applyGravity(rigidbody, settings.getGravity());
			}
			transform.translate(rigidbody.getVelocity());
		}
	}

	private void applyGravity(Rigidbody rigidbody, int[] gravity) {
		if (rigidbody.useGravity()) {
			long fallTime = System.currentTimeMillis() - rigidbody.getLastCollisionTime();
			int t = (int) (fallTime * fallTime);
			// ((Gravity * Time / 2) / 1024) / 1024
			// use bitshifting because its faster and has no need to be so precise
			int x = (((gravity[vx] * t) >> 1) >> 10) >> 10;
			int y = (((gravity[vy] * t) >> 1) >> 10) >> 10;
			int z = (((gravity[vz] * t) >> 1) >> 10) >> 10;
			rigidbody.accelerate(x, y, z);
		}
	}

	private void detectCollision(Scene scene, SceneObject sceneObject) {
		for (int j = 0; j < scene.getSceneObjects().size(); j++) {
			SceneObject sceneObject2 = scene.getSceneObjects().get(j);
			if (detectCollision(sceneObject, sceneObject2)) {
				sceneObject.getRigidbody().collisionEnter(sceneObject2.getName());
//				moveToCorner(sceneObject, sceneObject2);
			} else {
				sceneObject.getRigidbody().collisionExit(sceneObject2.getName());
			}
		}
	}
	
//	private void moveToCorner(SceneObject sceneObject1, SceneObject sceneObject2) {
//		int[] loc1 = sceneObject1.getTransform().getLocation();
//		int[] loc2 = sceneObject2.getTransform().getLocation();
//		Rigidbody rb1 = sceneObject1.getRigidbody();
//		Rigidbody rb2 = sceneObject2.getRigidbody();
//		int rad1 = rb1.getColliderRadius();
//		int rad2 = rb2.getColliderRadius();
//		int distX = (loc2[vx] + rad2) - (loc1[vx] + rad1);
//		int distY = (loc2[vy] + rad2) - (loc1[vy] + rad1);
//		int distZ = (loc2[vz] + rad2) - (loc1[vz] + rad1);
//		if (rb2.getMass() < rb1.getMass()) {
//			loc2[vx] += distX;
//			loc2[vy] += distY;
//			loc2[vz] += distZ;
//		}else if (rb2.getMass() > rb1.getMass()){
//			loc1[vx] += distX;
//			loc1[vy] += distY;
//			loc1[vz] += distZ;
//		}
//		
//	}

	private boolean detectCollision(SceneObject sceneObject1, SceneObject sceneObject2) {
		Rigidbody rb1 = sceneObject1.getRigidbody();
		Rigidbody rb2 = sceneObject2.getRigidbody();
		if (!sceneObject2.getName().equals(sceneObject1.getName())) {
			if (sphereToSphere(sceneObject1, sceneObject2)) {
				if (rb1.getCollisionType() + rb2.getCollisionType() == Rigidbody.COLLISION_SPHERE * 2)
					return true;
				if (rb1.getCollisionType() + rb2.getCollisionType() == Rigidbody.COLLISION_SPHERE
						+ Rigidbody.COLLISION_MESH) {
					if (rb1.getCollisionType() == Rigidbody.COLLISION_SPHERE)
						return sphereToMesh(sceneObject1, sceneObject2);
					else
						return sphereToMesh(sceneObject2, sceneObject1);
				}
				if (rb1.getCollisionType() + rb2.getCollisionType() == Rigidbody.COLLISION_MESH * 2)
					return meshToMesh(sceneObject1, sceneObject2);
			}
		}
		return false;
	}

	private boolean sphereToSphere(SceneObject sceneObject1, SceneObject sceneObject2) {
		Rigidbody rigidbody1 = sceneObject1.getRigidbody();
		Rigidbody rigidbody2 = sceneObject2.getRigidbody();
		int[] pos1 = sceneObject1.getTransform().getLocation();
		int[] pos2 = sceneObject2.getTransform().getLocation();
		int distance = Vector3MathUtils.distance(pos2, pos1);
		int radius1 = rigidbody1.getColliderRadius();
		int radius2 = rigidbody2.getColliderRadius();
		int radius = radius1 + radius2;
		if (distance < radius) {
			return true;
		}
		return false;
	}

	private boolean sphereToMesh(SceneObject sceneObject1, SceneObject sceneObject2) {
		Transform transform1 = sceneObject1.getTransform();
		Transform transform2 = sceneObject2.getTransform();
		int radius = sceneObject1.getRigidbody().getColliderRadius();
		for (int i = 0; i < sceneObject2.getMesh().getVertexes().length; i++) {
			int[] pos = sceneObject2.getMesh().getVertex(i).getLocation();
			VectorUtils.copy3(vectorCache1, pos);
			Vector3MathUtils.movePointByAnglesXYZ(vectorCache1, transform2.getRotation(), vectorCache1);
			Vector3MathUtils.movePointByScale(vectorCache1, transform2.getScale(), vectorCache1);
			Vector3MathUtils.add(vectorCache1, transform2.getLocation(), vectorCache1);
			int distance = Vector3MathUtils.distance(vectorCache1, transform1.getLocation());
			if (distance < radius) {
				return true;
			}
		}
		return false;
	}

	private boolean meshToMesh(SceneObject sceneObject1, SceneObject sceneObject2) {
		Transform transform1 = sceneObject1.getTransform();
		Transform transform2 = sceneObject2.getTransform();
		for (int i = 0; i < sceneObject1.getMesh().getVertexes().length; i++) {
			int[] pos1 = sceneObject1.getMesh().getVertex(i).getLocation();
			VectorUtils.copy3(vectorCache1, pos1);
			Vector3MathUtils.movePointByAnglesXYZ(vectorCache1, transform1.getRotation(), vectorCache1);
			Vector3MathUtils.movePointByScale(vectorCache1, transform1.getScale(), vectorCache1);
			Vector3MathUtils.add(vectorCache1, transform1.getLocation(), vectorCache1);
			for (int j = 0; j < sceneObject2.getMesh().getVertexes().length; j++) {
				int[] pos2 = sceneObject2.getMesh().getVertex(j).getLocation();
				VectorUtils.copy3(vectorCache2, pos2);
				Vector3MathUtils.movePointByAnglesXYZ(vectorCache2, transform2.getRotation(), vectorCache2);
				Vector3MathUtils.movePointByScale(vectorCache2, transform2.getScale(), vectorCache2);
				Vector3MathUtils.add(vectorCache2, transform2.getLocation(), vectorCache2);
				int distance = Vector3MathUtils.distance(vectorCache2, vectorCache1);
				if (distance < 100) {
					return true;
				}
			}
		}
		return false;
	}

	private int getRadius(SceneObject sceneObject) {
		int radius = 0;
		for (int i = 0; i < sceneObject.getMesh().getVertexes().length; i++) {
			int[] pos = sceneObject.getMesh().getVertex(i).getLocation();
			if (radius < pos[vx])
				radius = pos[vx];
			if (radius < pos[vy])
				radius = pos[vy];
			if (radius < pos[vz])
				radius = pos[vz];
		}
		return radius;
	}
}
