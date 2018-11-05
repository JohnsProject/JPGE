package com.johnsproject.jpge.dto;

import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class Rigidbody {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	private int mass = 10;
	private int[] velocity = new int[3];
	private int collisionTime = (int)System.currentTimeMillis();
	
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
		Vector3MathUtils.divide(force, mass, force);
		Vector3MathUtils.add(this.velocity, force, this.velocity);
	}
	
	public void addForce(int x, int y, int z) {
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
	
	public int getCollisionTime() {
		return collisionTime;
	}

	public void setCollisionTime(int collisionTime) {
		this.collisionTime = collisionTime;
	}
}
