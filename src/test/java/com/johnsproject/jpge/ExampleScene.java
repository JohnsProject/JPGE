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
package com.johnsproject.jpge;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.Light;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Texture;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.graphics.*;
import com.johnsproject.jpge.io.*;

public class ExampleScene implements JPGE, JPGEKeyListener, JPGEMouseListener{
	
	public static void main(String[] args){
		new ExampleScene();
	}
	
	SceneObject sceneObject1, sceneObject2;
	Camera camera, camera2;
	Light light, light2;
	Mesh mesh1, mesh2;
	int renderWidth = 512, renderHeight = 512;
	int windowWidth = 640, windowHeight = 640;
	public ExampleScene() {		
		try {
//			mesh1 = SOMImporter.load("/home/john/Development/test.som");
			mesh1 = SOMImporter.load(getClass().getResourceAsStream(Mesh.RESOURCES_CUBE));
			mesh2 = SOMImporter.load(getClass().getResourceAsStream(Mesh.RESOURCES_CUBE));
//			mesh2 = SOMImporter.load("/home/john/Development/test.som");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sceneObject1 = new SceneObject("test", new Transform(new int[] {0, 0, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), mesh1);
		sceneObject2 = new SceneObject("test2", new Transform(new int[] {0, -3000, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), mesh2);
//		sceneObject1.getRigidbody().setCollisionType(Rigidbody.COLLISION_MESH);
//		sceneObject2.getRigidbody().setCollisionType(Rigidbody.COLLISION_MESH);
		sceneObject1.getRigidbody().setKinematic(true);
		sceneObject1.getRigidbody().setMass(20);
		try {
			Texture t = new Texture(getClass().getResourceAsStream("/JohnsProject.png"));
			for (int i = 0; i < mesh1.getMaterials().length; i++) {
				mesh1.getMaterial(i).setTexture(t);
			}
			for (int i = 0; i < mesh2.getMaterials().length; i++) {
				mesh2.getMaterial(i).setTexture(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera = new Camera("testCam", new Transform(new int[] {0, 0, -10000}, new int[] {0, 0, 0}, new int[] {1, 1, 1}), 0, 0, renderWidth, renderHeight);
		camera2 = new Camera("testCam2", new Transform(new int[] {0, -10000, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), renderWidth-(renderWidth/3), 0, renderWidth/3, renderHeight/3);	
		light = new Light("testLight", new Transform(new int[] {0, 0, 0}, new int[] {0, 0, 0}, new int[] {1, 1, 1}));
		Engine.getInstance().setSceneWindow(new SceneWindow(windowWidth, windowHeight));
		Engine.getInstance().getRenderBuffer().setSize(renderWidth, renderHeight);
		Engine.getInstance().getScene().addLight(light);
		Engine.getInstance().getScene().addSceneObject(sceneObject1);
		Engine.getInstance().getScene().addSceneObject(sceneObject2);
		Engine.getInstance().getScene().addCamera(camera);
		Engine.getInstance().getScene().addCamera(camera2);
		Engine.getInstance().addJPGEListener(this);
		Engine.getInstance().getInputManager().addKeyListener(this);
		Engine.getInstance().getInputManager().addMouseListener(this);
		new Profiler(Engine.getInstance());
	}
	
	
	@Override
	public void update() {
//		MouseInputManager mouseInput = Engine.getInstance().getMouseInputManager();
//		if (mouseInput.getKey(MouseInputManager.LEFT)) {
//			int x = (mouseInput.getMouseX()) - (windowWidth/2);
//			int y = (mouseInput.getMouseY()) - (windowHeight/2);
//			sceneObject1.getTransform().setLocation(x*5, y*5, 0);
//			camera.getTransform().rotate(y/(windowHeight>>3), x/(windowWidth>>3), 0);
//		}
////		if (sceneObject2.getRigidbody().isColliding("test")) {
////			sceneObject2.getRigidbody().addForce(0, -100, 0);
////		}
	}


	@Override
	public void keyPressed(KeyEvent e) {}


	@Override
	public void keyReleased(KeyEvent e) {}


	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyDown(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
			camera.getTransform().translateLocal(0, 0, 60);
			break;
		case 's':
			camera.getTransform().translateLocal(0, 0, -60);
			break;
		case 'a':
			camera.getTransform().translateLocal(-60, 0, 0);
			break;
		case 'd':
			camera.getTransform().translateLocal(60, 0, 0);
			break;
		case 'e':
			camera.getTransform().translateLocal(0, 60, 0);
			break;
		case 'y':
			camera.getTransform().translateLocal(0, -60, 0);
			break;
		case 'b':
			sceneObject1.getTransform().rotate(0, 3, 0);
			sceneObject2.getTransform().rotate(0, 3, 0);
			break;
		case 'n':
			sceneObject1.getTransform().rotate(3, 0, 0);
			sceneObject2.getTransform().rotate(3, 0, 0);
			break;
		case 'k':
			camera.getTransform().rotate(0, 1, 0);
			break;
		case 'l':
			camera.getTransform().rotate(0, -1, 0);
			break;
		case 'o':
			camera.getTransform().rotate(1, 0, 0);
			break;
		case '.':
			camera.getTransform().rotate(-1, 0, 0);
			break;
		case 'p':
			camera.getTransform().rotate(0, 0, 1);
			break;
		case ',':
			camera.getTransform().rotate(0, 0, -1);
			break;
		case '1':
			sceneObject1.getShader().setDrawingType(Shader.DRAW_VERTEX);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_VERTEX);
			break;
		case '2':
			sceneObject1.getShader().setDrawingType(Shader.DRAW_WIREFRAME);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_WIREFRAME);
			break;
		case '3':
			sceneObject1.getShader().setDrawingType(Shader.DRAW_FLAT);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_FLAT);
			break;
		case '4':
			sceneObject1.getShader().setDrawingType(Shader.DRAW_TEXTURED);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_TEXTURED);
			break;
		case '6':
			sceneObject1.getShader().setProjectionType(Shader.PROJECT_ORTHOGRAPHIC);
			sceneObject2.getShader().setProjectionType(Shader.PROJECT_ORTHOGRAPHIC);
			break;
		case '7':
			sceneObject1.getShader().setProjectionType(Shader.PROJECT_PERSPECTIVE);
			sceneObject2.getShader().setProjectionType(Shader.PROJECT_PERSPECTIVE);
		case '8':
			sceneObject1.getShader().setShadingType(Shader.SHADE_FLAT);
			sceneObject2.getShader().setShadingType(Shader.SHADE_FLAT);
			break;
		case '9':
			sceneObject1.getShader().setShadingType(Shader.SHADE_GOURAUD);
			sceneObject2.getShader().setShadingType(Shader.SHADE_GOURAUD);
			break;
		case 'v':
			sceneObject2.getRigidbody().accelerate(0, -10, 0);
			break;
		case '+':
			light.setStrength(light.getStrength()+1);
			break;
		case '-':
			light.setStrength(light.getStrength()-1);
			break;
		}
		if (e.getKeyCode() == 116) { 
			Engine.getInstance().play();
		}
		if (e.getKeyCode() == 117) { 
			Engine.getInstance().pause();
		}
		if (e.getKeyCode() == 38) { 
			light.getTransform().translate(0, 0, 1);
		}
		if (e.getKeyCode() == 40) { 
			light.getTransform().translate(0, 0, -1);
		}
		if (e.getKeyCode() == 37) { 
			light.getTransform().translate(1, 0, 0);
		}
		if (e.getKeyCode() == 39) { 
			light.getTransform().translate(-1, 0, 0);
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseDown(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = (e.getX()) - (windowWidth/2);
			int y = (e.getY()) - (windowHeight/2);
			sceneObject1.getTransform().setLocation(x*5, y*5, 0);
//			camera.getTransform().rotate(y/(windowHeight>>3), x/(windowWidth>>3), 0);
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseClicked(MouseEvent e) {}


	@Override
	public void mouseExited(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e) {}

//	boolean dragged = false;
//	int[] postition = new int[2];
//	@Override
//	public void leftClick(JPGEMouseEvent event) {
//		dragged = !dragged;
//		postition = event.getLocation();
//	}
//
//	@Override
//	public void middleClick(JPGEMouseEvent event) {
//		
//	}
//
//	@Override
//	public void rightClick(JPGEMouseEvent event) {
//		
//	}
//
//	@Override
//	public void locationUpdate(JPGEMouseEvent event) {
//		if (dragged) {
//			int fx = (int)Engine.getInstance().getSceneWindow().getLocationX();
//			int fy = (int)Engine.getInstance().getSceneWindow().getLocationY();
//			int x = (event.getLocation()[VectorUtils.X]-fx) - (windowWidth/2);
//			int y = (event.getLocation()[VectorUtils.Y]-fy) - (windowHeight/2);
////			int z = camera.getTransform().getRotation()[VectorUtils.Z];
//			//System.out.println("x " + x + ", y " + y + ", z " + z);
//			camera.getTransform().rotate(y/(windowHeight>>3), x/(windowWidth>>3), 0);
//		}
//	}
}
