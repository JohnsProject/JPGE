/**
 * 
 */
package com.johnsproject.jpge.dto;

import java.util.Arrays;

/**
 * The Animation class contains the data of imported mesh animations. It contains name, 
 * bones data (position, rotation, scale) at each frame and so on.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Animation {
	
	private String name;
	private int bonesCount = 0;
	private int framesCount = 0;
	private int currentFrame = 0;
	private Transform[] bones;
	
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
	public String toString() {
		return "Animation [name=" + name + ", bonesCount=" + bonesCount + ", framesCount=" + framesCount
				+ ", currentFrame=" + currentFrame + ", bones=" + Arrays.toString(bones) + "]";
	}
}
