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
package com.johnsproject.jpge.graphics;

import com.johnsproject.jpge.dto.Animation;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;

/**
 * The Animator class updates the frame of the current {@link Animation} 
 * of each {@link SceneObject} in a {@link Scene}.
 *
 * @author John´s Project - John Salomon
 */
public class Animator {
	
	/**
	 * Updates the frame of the current {@link Animation} of each {@link SceneObject} in a {@link Scene}.
	 * 
	 * @param scene {@link Scene} to update.
	 */
	public void animate(Scene scene) {
		for (int i = 0; i < scene.getSceneObjects().size(); i++) {
			SceneObject sceneObject = scene.getSceneObjects().get(i);
			Animation animation = sceneObject.getMesh().getCurrentAnimation();
			animation.setCurrentFrame(animation.getCurrentFrame());
			if (animation.getCurrentFrame() >= animation.getFramesCount()) {
				animation.setCurrentFrame(0);
			}
		}
	}
	
}
