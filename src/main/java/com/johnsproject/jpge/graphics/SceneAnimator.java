/**
 * 
 */
package com.johnsproject.jpge.graphics;

import com.johnsproject.jpge.Animation;
import com.johnsproject.jpge.GameManager;
import com.johnsproject.jpge.Scene;
import com.johnsproject.jpge.SceneObject;
import com.johnsproject.jpge.event.UpdateEvent;

/**
 * The SceneAnimator class updates the frame of the current {@link Animation} 
 * of each {@link SceneObject} in a {@link Scene}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneAnimator {
	
	private int speed = 1;
	private int updateType = GameManager.UPDATE_PHYSICS;
	
	/**
	 * Creates a new instance of the SceneAnimator class.
	 */
	public SceneAnimator() {
		
	}
	
	/**
	 * Updates the frame of the current {@link Animation} of each {@link SceneObject} in a {@link Scene}.
	 * 
	 * @param scene {@link Scene} to update.
	 */
	public void animate(Scene scene) {
		for (SceneObject sceneObject : scene.getSceneObjects()) {
			Animation animation = sceneObject.getMesh().getCurrentAnimation();
			animation.setCurrentFrame(animation.getCurrentFrame()+speed);
			if (animation.getCurrentFrame() >= animation.getFramesCount()) {
				animation.setCurrentFrame(0);
			}
		}
	}

	/**
	 * Returns the current animation play speed.
	 * 
	 * @return current animation play speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Sets the current animation play speed.
	 * 
	 * @param speed speed to set.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns wich {@link UpdateEvent} type this scene animator will respond to by animating the scene.
	 * 
	 * @return wich {@link UpdateEvent} type this scene animator will respond to by animating the scene.
	 */
	public int getUpdateType() {
		return updateType;
	}

	/**
	 * Sets wich {@link UpdateEvent} type this scene animator will respond to by animating the scene.
	 * 
	 * @param updateType {@link UpdateEvent} type. 
	 */
	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}
	
}
