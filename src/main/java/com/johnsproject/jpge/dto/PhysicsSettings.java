package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.johnsproject.jpge.utils.VectorUtils;

public class PhysicsSettings implements Externalizable {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	private int[] gravity = new int[] {0, 10, 0};

	public int[] getGravity() {
		return gravity;
	}

	public void setGravity(int[] gravity) {
		this.gravity = gravity;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(gravity[vx]);
		out.writeInt(gravity[vy]);
		out.writeInt(gravity[vz]);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		gravity[vx] = in.readInt();
		gravity[vy] = in.readInt();
		gravity[vz] = in.readInt();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(gravity);
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
		PhysicsSettings other = (PhysicsSettings) obj;
		if (!Arrays.equals(gravity, other.gravity))
			return false;
		return true;
	}	
}
