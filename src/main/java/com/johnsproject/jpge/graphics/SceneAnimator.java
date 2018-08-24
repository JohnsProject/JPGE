/**
 * 
 */
package com.johnsproject.jpge.graphics;

import com.johnsproject.jpge.event.UpdateEvent.UpdateType;

/**
 * @author john
 *
 */
public class SceneAnimator {
	
	private int speed = 1;
	private UpdateType updateType = UpdateType.physics;
	
	public SceneAnimator() {
		
	}
	
	public void animate(Scene scene) {
		for (SceneObject sceneObject : scene.getSceneObjects()) {
			Animation animation = sceneObject.getMesh().getCurrentAnimation();
			animation.setCurrentFrame(animation.getCurrentFrame()+speed);
			if (animation.getCurrentFrame() >= animation.getFramesCount()) {
				animation.setCurrentFrame(0);
			}
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public UpdateType getUpdateType() {
		return updateType;
	}

	public void setUpdateType(UpdateType updateType) {
		this.updateType = updateType;
	}
	
}
