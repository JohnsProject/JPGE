/**
 * 
 */
package com.johnsproject.jpge.graphics;

import java.util.Arrays;

/**
 * @author john
 *
 */
public class Animation {
	
	private String name;
	private int bonesCount = 0;
	private int framesCount = 0;
	private int currentFrame = 0;
	private Transform[] bones;
	
	public Animation(String name, int framesCount, int bonesCount, Transform[] bones) {
		this.name = name;
		this.framesCount = framesCount;
		this.bonesCount = bonesCount;
		this.bones = bones;
	}

	public String getName() {
		return name;
	}

	public int getBonesCount() {
		return bonesCount;
	}

	public int getFramesCount() {
		return framesCount;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int frame) {
		this.currentFrame = frame;
	}

	public Transform getBone(int index, int frame) {
		return bones[index + (frame*bonesCount)];
	}
	
	public Transform[] getBones() {
		return bones;
	}

	@Override
	public String toString() {
		return "Animation [name=" + name + ", bonesCount=" + bonesCount + ", framesCount=" + framesCount
				+ ", currentFrame=" + currentFrame + ", bones=" + Arrays.toString(bones) + "]";
	}	
}
