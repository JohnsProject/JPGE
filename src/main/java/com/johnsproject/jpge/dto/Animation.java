/**
 * 
 */
package com.johnsproject.jpge.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

/**
 * The Animation class contains the data of imported {@link Mesh} animations.
 * 
 * @author John´s Project - John Salomon
 */
public class Animation implements Externalizable{
	
	private static final long serialVersionUID = 5604951472450171754L;
	private String name = "Default";
	private int bonesCount = 0;
	private int framesCount = 0;
	private int currentFrame = 0;
	private Transform[] bones = new Transform[0];
	
	public Animation() {}
	
	/**
	 * Creates a new instance of the Animation class filled with the given values.
	 * 
	 * @param name animation's name.
	 * @param framesCount animation's frames count.
	 * @param bonesCount animation's bones count.
	 * @param bones all bones of the animation at each frame.
	 */
	public Animation(String name, int framesCount, int bonesCount, Transform[] bones) {
		this.name = name;
		this.framesCount = framesCount;
		this.bonesCount = bonesCount;
		this.bones = bones;
	}
	
	/**
	 * Returns the name of this animation.
	 * 
	 * @return animation's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the bones count of this animation.
	 * 
	 * @return animation's bones count.
	 */
	public int getBonesCount() {
		return bonesCount;
	}

	/**
	 * Returns the frames count of this animation.
	 * 
	 * @return animation's frames count.
	 */
	public int getFramesCount() {
		return framesCount;
	}

	/**
	 * Returns the current frame of this animation.
	 * 
	 * @return animation's current frame.
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}
	/**
	 * Sets the current frame of this animation.
	 * 
	 * @param frame current frame.
	 */
	public void setCurrentFrame(int frame) {
		this.currentFrame = frame;
	}

	/**
	 * Returns the bone at the given index and frame of this animation.
	 * 
	 * @param index bone index.
	 * @param frame frame of animation.
	 * @return bone.
	 */
	public Transform getBone(int index, int frame) {
		return bones[index + (frame*bonesCount)];
	}
	
	/**
	 * Returns all bones of this animation.
	 * 
	 * @return all bones of this animation.
	 */
	public Transform[] getBones() {
		return bones;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(name);
		out.writeInt(bonesCount);
		out.writeInt(framesCount);
		out.writeInt(currentFrame);
		final int boneLength = bones.length;
		out.writeInt(boneLength);
		for (int i = 0; i < boneLength; i++) {
			out.writeObject(bones[i]);
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		name = in.readUTF();
		bonesCount = in.readInt();
		framesCount = in.readInt();
		currentFrame = in.readInt();
		final int boneLength = in.readInt();
		bones = new Transform[boneLength];
		for (int i = 0; i < boneLength; i++) {
			bones[i] = (Transform) in.readObject();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bones);
		result = prime * result + bonesCount;
		result = prime * result + currentFrame;
		result = prime * result + framesCount;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Animation other = (Animation) obj;
		if (!Arrays.equals(bones, other.bones))
			return false;
		if (bonesCount != other.bonesCount)
			return false;
		if (currentFrame != other.currentFrame)
			return false;
		if (framesCount != other.framesCount)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
