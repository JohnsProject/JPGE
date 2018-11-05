package com.johnsproject.jpge.physics;

import com.johnsproject.jpge.dto.Rigidbody;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.utils.VectorUtils;

public class PhysicsAnimator {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	public void animate(Scene scene) {
		synchronized (scene.getSceneObjects()) {
			for (int i = 0; i < scene.getSceneObjects().size(); i++) {
				SceneObject sceneObject = scene.getSceneObjects().get(i);
				Transform transform = sceneObject.getTransform();
				Rigidbody rigidbody = sceneObject.getRigidbody();
				PhysicsSettings settings = scene.getPhysicsSettings();
				applyGravity(rigidbody, settings.getGravity());
				transform.translate(rigidbody.getVelocity());
			}
		}
	}
	
	
	void applyGravity (Rigidbody rigidbody, int[] gravity) {
		long fallTime = System.currentTimeMillis() - rigidbody.getCollisionTime();
		int t = (int)(fallTime * fallTime);
		//		((Gravity * Time / 2) / 1024) / 1024
		// use bitshifting because its faster and has no need to be so precise
		int x = (((gravity[vx] * t) >> 1) >> 10) >> 10;
		int y = (((gravity[vy] * t) >> 1) >> 10) >> 10;
		int z = (((gravity[vz] * t) >> 1) >> 10) >> 10;
		rigidbody.accelerate(x, y, z);
	}
}
