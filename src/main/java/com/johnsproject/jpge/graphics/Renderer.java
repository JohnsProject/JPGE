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

import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.RenderBuffer;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;

/**
 * The Renderer class renders the {@link Scene} assigned to the {@link SceneWindow}.
 * It takes the {@link SceneObject SceneObjects} in the view of all {@link Camera Cameras} in the {@link Scene}, 
 * transforms, projects and draws them.
 *
 * @author John´s Project - John Salomon
 */
public class Renderer {
	
	/**
	 * Tells this scene renderer to render the given {@link Scene}.
	 * 
	 * @param scene {@link Scene} to render.
	 * @param renderBuffer {@link RenderBuffer} to use.
	 * 
	 * @return rendered {@link Face faces}. (faces that are not culled)
	 */
	public int render(Scene scene, RenderBuffer renderBuffer) {
		int rendFaces = 0;
		renderBuffer.clearFrameBuffer();
		for (int i = 0; i < scene.getCameras().size(); i++) {
			renderBuffer.clearDepthBuffer();
			Camera camera = scene.getCameras().get(i);
			for (int j = 0; j < scene.getSceneObjects().size(); j++) {
				SceneObject sceneObject = scene.getSceneObjects().get(j);
				// check if object is active or has changed (no need to render if its the same)
				if (sceneObject.isActive() && (sceneObject.changed() || camera.changed())) {
					rendFaces += sceneObject.getShader().shade(
							sceneObject.getMesh(), sceneObject.getTransform(), camera, renderBuffer, scene.getLights()
							);
				}
			}
			camera.changed(false);
		}
		for (int i = 0; i < scene.getSceneObjects().size(); i++) {
			SceneObject sceneObject = scene.getSceneObjects().get(i);
			sceneObject.changed(false);
		}
		return rendFaces;
	}
}
