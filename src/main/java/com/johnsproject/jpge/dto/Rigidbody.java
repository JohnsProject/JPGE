package com.johnsproject.jpge.dto;

import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class Rigidbody {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	public static final int COLLISION_SPHERE = 0;
	public static final int COLLISION_TERRAIN = 2;
	
	private int mass = 10;
	private int[] velocity = new int[3];
	private int collisionTime = (int)System.currentTimeMillis();
	private int collisionType = COLLISION_SPHERE;
	private int radius = -1;
	private boolean gravity = true;
	private boolean kinematic = false;
	private String[] collisionTargets = new String[5];
	
	public Rigidbody() {
		for (int i = 0; i < collisionTargets.length; i++) {
			collisionTargets[i] = "";
		}
	}
	
	public int getMass() {
		return mass;
	}
	
	public void setMass(int mass) {
		this.mass = mass;
	}
	
	public int[] getVelocity() {
		return velocity;
	}
	
	public void addForce(int[] force) {
		collisionTime = (int)System.currentTimeMillis();
		Vector3MathUtils.divide(force, mass, force);
		Vector3MathUtils.add(this.velocity, force, this.velocity);
	}
	
	public void addForce(int x, int y, int z) {
		collisionTime = (int)System.currentTimeMillis();
		this.velocity[vx] += x / mass;
		this.velocity[vy] += y / mass;
		this.velocity[vz] += z / mass;
	}
	
	public void accelerate(int[] value) {
		Vector3MathUtils.add(this.velocity, value, this.velocity);
	}
	
	public void accelerate(int x, int y, int z) {
		this.velocity[vx] += x;
		this.velocity[vy] += y;
		this.velocity[vz] += z;
	}
	
	public int getLastCollisionTime() {
		return collisionTime;
	}
	
	public void collisionEnter(String objectName) {
		collisionTime = (int)System.currentTimeMillis();
		for (int i = 0; i < collisionTargets.length; i++) {
			if (collisionTargets[i].equals(objectName))
				break;
			if (collisionTargets[i].equals("")) {
				if (!isKinematic()) {
					velocity[vx] = 0;
					velocity[vy] = 0;
					velocity[vz] = 0;
				}
				collisionTargets[i] = objectName;
				break;
			}
		}
	}
	
	public void collisionExit(String objectName) {
		for (int i = 0; i < collisionTargets.length; i++) {
			if (collisionTargets[i].equals(objectName)) {
				collisionTime = (int)System.currentTimeMillis();
				collisionTargets[i] = "";
			}
		}
	}

	public boolean isColliding(){
		for (int i = 0; i < collisionTargets.length; i++) {
			if (!collisionTargets[i].equals("")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isColliding(String objectName){
		for (int i = 0; i < collisionTargets.length; i++) {
			if (collisionTargets[i].equals(objectName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCollidingSimple(String objectName){
		for (int i = 0; i < collisionTargets.length; i++) {
			if (collisionTargets[i].contains(objectName)) {
				return true;
			}
		}
		return false;
	}

	public String[] getCollisionTargets() {
		return collisionTargets;
	}

	public boolean useGravity() {
		return gravity;
	}

	public void useGravity(boolean useGravity) {
		collisionTime = (int)System.currentTimeMillis();
		this.gravity = useGravity;
	}

	public int getCollisionType() {
		return collisionType;
	}

	public void setCollisionType(int collisionType) {
		this.collisionType = collisionType;
	}

	public int getColliderRadius() {
		return radius;
	}

	public void setColliderRadius(int radius) {
		this.radius = radius;
	}

	public boolean isKinematic() {
		return kinematic;
	}

	public void setKinematic(boolean kinematic) {
		this.kinematic = kinematic;
	}
}
