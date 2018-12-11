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
package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class Rigidbody implements Externalizable {

	private static final long serialVersionUID = 6444599308644383247L;

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	public static final int COLLISION_SPHERE = 0;
	public static final int COLLISION_MESH = 2;
	
	private int mass = 10;
	private int[] velocity = new int[3];
	private int radius = -1;
	private boolean gravity = true;
	private boolean kinematic = false;
	
	private String[] collisionTargets = new String[5];
	private int collisionTime = (int)System.currentTimeMillis();
	private int collisionType = COLLISION_SPHERE;
	
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

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(mass);
		out.writeInt(velocity[vx]);
		out.writeInt(velocity[vy]);
		out.writeInt(velocity[vz]);
		out.writeInt(radius);
		out.writeBoolean(gravity);
		out.writeBoolean(kinematic);
		out.writeInt(collisionType);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		mass = in.readInt();
		velocity[vx] = in.readInt();
		velocity[vy] = in.readInt();
		velocity[vz] = in.readInt();
		radius = in.readInt();
		gravity = in.readBoolean();
		kinematic = in.readBoolean();
		collisionType = in.readInt();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + collisionType;
		result = prime * result + (gravity ? 1231 : 1237);
		result = prime * result + (kinematic ? 1231 : 1237);
		result = prime * result + mass;
		result = prime * result + radius;
		result = prime * result + Arrays.hashCode(velocity);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rigidbody other = (Rigidbody) obj;
		if (collisionType != other.collisionType)
			return false;
		if (gravity != other.gravity)
			return false;
		if (kinematic != other.kinematic)
			return false;
		if (mass != other.mass)
			return false;
		if (radius != other.radius)
			return false;
		if (!Arrays.equals(velocity, other.velocity))
			return false;
		return true;
	}
	
}
