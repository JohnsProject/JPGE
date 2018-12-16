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
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class PhysicsAnimator {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	private static int[] vectorCache1 = new int[3];
	private static int[] vectorCache2 = new int[3];

	public void animate(Scene scene) {
		for (int i = 0; i < scene.getSceneObjects().size(); i++) {
			final SceneObject sceneObject = scene.getSceneObjects().get(i);
			final Transform transform = sceneObject.getTransform();
			final Rigidbody rigidbody = sceneObject.getRigidbody();
			if (rigidbody.getColliderRadius() == -1) {
				rigidbody.setColliderRadius(getRadius(sceneObject));
			}
			final PhysicsSettings settings = scene.getPhysicsSettings();
			detectCollision(scene, sceneObject);
			if (!rigidbody.isColliding() && !rigidbody.isKinematic()) {
				applyGravity(rigidbody, settings.getGravity());
			}
			transform.translate(rigidbody.getVelocity());
		}
	}

	private void applyGravity(Rigidbody rigidbody, int[] gravity) {
		if (rigidbody.useGravity()) {
			final long fallTime = System.currentTimeMillis() - rigidbody.getLastCollisionTime();
			final int t = (int) (fallTime * fallTime);
			// ((Gravity * Time / 2) / 1024) / 1024
			// use bitshifting because its faster and has no need to be so precise
			final int x = (((gravity[vx] * t) >> 1) >> 10) >> 10;
			final int y = (((gravity[vy] * t) >> 1) >> 10) >> 10;
			final int z = (((gravity[vz] * t) >> 1) >> 10) >> 10;
			rigidbody.accelerate(x, y, z);
		}
	}

	private void detectCollision(Scene scene, SceneObject sceneObject) {
		for (int j = 0; j < scene.getSceneObjects().size(); j++) {
			final SceneObject sceneObject2 = scene.getSceneObjects().get(j);
//			moveToCorner(sceneObject, sceneObject2);
			if (detectCollision(sceneObject, sceneObject2)) {
				sceneObject.getRigidbody().collisionEnter(sceneObject2.getName());
			} else {
				sceneObject.getRigidbody().collisionExit(sceneObject2.getName());
			}
		}
	}
	
//	private void moveToCorner(SceneObject sceneObject1, SceneObject sceneObject2) {
//		final int[] loc1 = sceneObject1.getTransform().getLocation();
//		final int[] loc2 = sceneObject2.getTransform().getLocation();
//		final Rigidbody rb1 = sceneObject1.getRigidbody();
//		final Rigidbody rb2 = sceneObject2.getRigidbody();
//		final int[] dir1 = Vector3MathUtils.clamp(rb1.getVelocity(), -1, 1, vectorCache1);
//		final int[] dir2 = Vector3MathUtils.clamp(rb2.getVelocity(), -1, 1, vectorCache2);
//		final int rad1 = rb1.getColliderRadius();
//		final int rad2 = rb2.getColliderRadius();
//		final int rad = rad2 + rad1;
//		if ((dir2[vx] < dir1[vx]) && (dir2[vy] < dir1[vy]) && (dir2[vz] < dir1[vz])) {
//			final int x = dir1[vx];
//			final int y = dir1[vy];
//			final int z = dir1[vz];
//			loc2[vx] = (loc1[vx] + rad) * x == 0 ? 1 : x;
//			loc2[vy] = (loc1[vy] + rad) * y == 0 ? 1 : y;
//			loc2[vz] = (loc1[vz] + rad) * z == 0 ? 1 : z;
//		}else if ((dir2[vx] > dir1[vx]) && (dir2[vy] > dir1[vy]) && (dir2[vz] > dir1[vz])){
//			final int x = dir2[vx];
//			final int y = dir2[vy];
//			final int z = dir2[vz];
//			loc1[vx] = (loc2[vx] + rad) * x == 0 ? 1 : x;
//			loc1[vy] = (loc2[vy] + rad) * y == 0 ? 1 : y;
//			loc1[vz] = (loc2[vz] + rad) * z == 0 ? 1 : z;
//		}
//		
//	}

	private boolean detectCollision(SceneObject sceneObject1, SceneObject sceneObject2) {
		final Rigidbody rb1 = sceneObject1.getRigidbody();
		final Rigidbody rb2 = sceneObject2.getRigidbody();
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
		final Rigidbody rigidbody1 = sceneObject1.getRigidbody();
		final Rigidbody rigidbody2 = sceneObject2.getRigidbody();
		final int[] pos1 = sceneObject1.getTransform().getLocation();
		final int[] pos2 = sceneObject2.getTransform().getLocation();
		final int distance = Vector3MathUtils.distance(pos2, pos1);
		final int radius1 = rigidbody1.getColliderRadius();
		final int radius2 = rigidbody2.getColliderRadius();
		final int radius = radius1 + radius2;
		if (distance < radius) {
			return true;
		}
		return false;
	}

	private boolean sphereToMesh(SceneObject sceneObject1, SceneObject sceneObject2) {
		final Transform transform1 = sceneObject1.getTransform();
		final Transform transform2 = sceneObject2.getTransform();
		final int radius = sceneObject1.getRigidbody().getColliderRadius();
		for (int i = 0; i < sceneObject2.getMesh().getVertexes().length; i++) {
			final int[] pos = sceneObject2.getMesh().getVertex(i).getLocation();
			VectorUtils.copy3(vectorCache1, pos);
			Vector3MathUtils.movePointByAnglesXYZ(vectorCache1, transform2.getRotation(), vectorCache1);
			Vector3MathUtils.movePointByScale(vectorCache1, transform2.getScale(), vectorCache1);
			Vector3MathUtils.add(vectorCache1, transform2.getLocation(), vectorCache1);
			final int distance = Vector3MathUtils.distance(vectorCache1, transform1.getLocation());
			if (distance < radius) {
				return true;
			}
		}
		return false;
	}

	private boolean meshToMesh(SceneObject sceneObject1, SceneObject sceneObject2) {
		final Transform transform1 = sceneObject1.getTransform();
		final Transform transform2 = sceneObject2.getTransform();
		for (int i = 0; i < sceneObject1.getMesh().getVertexes().length; i++) {
			final int[] pos1 = sceneObject1.getMesh().getVertex(i).getLocation();
			VectorUtils.copy3(vectorCache1, pos1);
			Vector3MathUtils.movePointByAnglesXYZ(vectorCache1, transform1.getRotation(), vectorCache1);
			Vector3MathUtils.movePointByScale(vectorCache1, transform1.getScale(), vectorCache1);
			Vector3MathUtils.add(vectorCache1, transform1.getLocation(), vectorCache1);
			for (int j = 0; j < sceneObject2.getMesh().getVertexes().length; j++) {
				final int[] pos2 = sceneObject2.getMesh().getVertex(j).getLocation();
				VectorUtils.copy3(vectorCache2, pos2);
				Vector3MathUtils.movePointByAnglesXYZ(vectorCache2, transform2.getRotation(), vectorCache2);
				Vector3MathUtils.movePointByScale(vectorCache2, transform2.getScale(), vectorCache2);
				Vector3MathUtils.add(vectorCache2, transform2.getLocation(), vectorCache2);
				final int distance = Vector3MathUtils.distance(vectorCache2, vectorCache1);
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
			final int[] pos = sceneObject.getMesh().getVertex(i).getLocation();
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
