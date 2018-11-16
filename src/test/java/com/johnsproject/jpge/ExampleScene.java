package com.johnsproject.jpge;

import java.awt.event.KeyEvent;
import java.io.IOException;

import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.Light;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Texture;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.graphics.*;
import com.johnsproject.jpge.io.*;

public class ExampleScene implements JPGE{
	
	public static void main(String[] args){
		new ExampleScene();
	}
	
	SceneObject sceneObject, sceneObject2;
	Camera camera, camera2;
	Light light, light2;
	Mesh mesh1, mesh2;
	int renderWidth = 512, renderHeight = 512;
	int windowWidth = 640, windowHeight = 640;
	public ExampleScene() {		
		try {
			mesh1 = SOMImporter.load("/home/john/Development/test.som");
//			mesh1 = SOMImporter.load(getClass().getResourceAsStream("/monkey.som"));
			mesh2 = SOMImporter.load(getClass().getResourceAsStream("/cube.som"));
		} catch (ImportExeption e) {
			e.printStackTrace();
		}
		sceneObject = new SceneObject("test", new Transform(new int[] {0, 0, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), mesh1);
		sceneObject2 = new SceneObject("test2", new Transform(new int[] {0, -3000, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), mesh2);
//		sceneObject.getRigidbody().setCollisionType(Rigidbody.COLLISION_AABB);
//		sceneObject2.getRigidbody().setCollisionType(Rigidbody.COLLISION_AABB);
//		sceneObject.getRigidbody().setCollisionType(Rigidbody.COLLISION_TERRAIN);
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
		Engine.getInstance().getScene().addSceneObject(sceneObject);
		Engine.getInstance().getScene().addSceneObject(sceneObject2);
		Engine.getInstance().getScene().addCamera(camera);
		Engine.getInstance().getScene().addCamera(camera2);
		Engine.getInstance().addJPGEListener(this);
		Profiler.getInstance().start();
	}
	
	
	@Override
	public void update() {
		KeyInputManager keyInput = Engine.getInstance().getKeyInputManager();
		applyCameraMove(keyInput);
		applySceneObjectMove(keyInput);
		applyLightMove(keyInput);
		MouseInputManager mouseInput = Engine.getInstance().getMouseInputManager();
		if (mouseInput.getKey(MouseInputManager.LEFT)) {
			int x = (mouseInput.getMouseX()) - (windowWidth/2);
			int y = (mouseInput.getMouseY()) - (windowHeight/2);
			sceneObject.getTransform().setPosition(x*5, y*5, 0);
//			camera.getTransform().rotate(y/(windowHeight>>3), x/(windowWidth>>3), 0);
		}
		if (sceneObject2.getRigidbody().isColliding("test")) {
			sceneObject2.getRigidbody().addForce(0, -100, 0);
		}
	}
	
	public void applyCameraMove(KeyInputManager input) {
		if (input.getKey(KeyEvent.VK_W)) {
			camera.getTransform().translateLocal(0, 0, 60);
		}
		if (input.getKey(KeyEvent.VK_S)) {
			camera.getTransform().translateLocal(0, 0, -60);
		}
		if (input.getKey(KeyEvent.VK_A)) {
			camera.getTransform().translateLocal(-60, 0, 0);
		}
		if (input.getKey(KeyEvent.VK_D)) {
			camera.getTransform().translateLocal(60, 0, 0);
		}
		if (input.getKey(KeyEvent.VK_E)) {
			camera.getTransform().translateLocal(0, -60, 0);
		}
		if (input.getKey(KeyEvent.VK_Y)) {
			camera.getTransform().translateLocal(0, 60, 0);
		}
	}
	
	public void applySceneObjectMove(KeyInputManager input) {
		if (input.getKey(KeyEvent.VK_B)) {
			sceneObject.getTransform().rotate(0, 3, 0);
			sceneObject2.getTransform().rotate(0, 3, 0);
		}
		if (input.getKey(KeyEvent.VK_N)) {
			sceneObject.getTransform().rotate(3, 0, 0);
			sceneObject2.getTransform().rotate(3, 0, 0);
		}
		sceneObject.getRigidbody().useGravity(false);
		if (input.getKey(KeyEvent.VK_G)) {
			sceneObject2.getRigidbody().addForce(0, -100, 0);
		}
	}
	
	public void applyLightMove(KeyInputManager input) {
		if (input.getKey(KeyEvent.VK_I)) {
			light.getTransform().translateLocal(0, 0, -1);
		}
		if (input.getKey(KeyEvent.VK_K)) {
			light.getTransform().translateLocal(0, 0, 1);
		}
		if (input.getKey(KeyEvent.VK_J)) {
			light.getTransform().translateLocal(1, 0, 0);
		}
		if (input.getKey(KeyEvent.VK_L)) {
			light.getTransform().translateLocal(-1, 0, 0);
		}
		if (input.getKey(KeyEvent.VK_M)) {
			light.getTransform().translateLocal(0, 1, 0);
		}
		if (input.getKey(KeyEvent.VK_O)) {
			light.getTransform().translateLocal(0, -1, 0);
		}
	}
	
//	public void keyPressed(JPGEKeyEvent event) {
//		switch (event.getKey()) {
//		case 'w':
//			camera.getTransform().translateLocal(0, 0, 60);
//			break;
//		case 's':
//			camera.getTransform().translateLocal(0, 0, -60);
//			break;
//		case 'a':
//			camera.getTransform().translateLocal(-60, 0, 0);
//			break;
//		case 'd':
//			camera.getTransform().translateLocal(60, 0, 0);
//			break;
//		case 'e':
//			camera.getTransform().translateLocal(0, 60, 0);
//			break;
//		case 'y':
//			camera.getTransform().translateLocal(0, -60, 0);
//			break;
//		case 'b':
//			sceneObject.getTransform().rotate(0, 3, 0);
//			sceneObject2.getTransform().rotate(0, 3, 0);
//			break;
//		case 'n':
//			sceneObject.getTransform().rotate(3, 0, 0);
//			sceneObject2.getTransform().rotate(3, 0, 0);
//			break;
//		case 'k':
//			camera.getTransform().rotate(0, 1, 0);
//			break;
//		case 'l':
//			camera.getTransform().rotate(0, -1, 0);
//			break;
//		case 'o':
//			camera.getTransform().rotate(1, 0, 0);
//			break;
//		case '.':
//			camera.getTransform().rotate(-1, 0, 0);
//			break;
//		case 'p':
//			camera.getTransform().rotate(0, 0, 1);
//			break;
//		case ',':
//			camera.getTransform().rotate(0, 0, -1);
//			break;
//		case '1':
//			sceneObject.getShader().setDrawingType(Shader.DRAW_VERTEX);
//			sceneObject2.getShader().setDrawingType(Shader.DRAW_VERTEX);
//			break;
//		case '2':
//			sceneObject.getShader().setDrawingType(Shader.DRAW_WIREFRAME);
//			sceneObject2.getShader().setDrawingType(Shader.DRAW_WIREFRAME);
//			break;
//		case '3':
//			sceneObject.getShader().setDrawingType(Shader.DRAW_FLAT);
//			sceneObject2.getShader().setDrawingType(Shader.DRAW_FLAT);
//			break;
//		case '4':
//			sceneObject.getShader().setDrawingType(Shader.DRAW_TEXTURED);
//			sceneObject2.getShader().setDrawingType(Shader.DRAW_TEXTURED);
//			break;
//		case '6':
//			sceneObject.getShader().setProjectionType(Shader.PROJECT_ORTHOGRAPHIC);
//			sceneObject2.getShader().setProjectionType(Shader.PROJECT_ORTHOGRAPHIC);
//			break;
//		case '7':
//			sceneObject.getShader().setProjectionType(Shader.PROJECT_PERSPECTIVE);
//			sceneObject2.getShader().setProjectionType(Shader.PROJECT_PERSPECTIVE);
//		case '8':
//			sceneObject.getShader().setShadingType(Shader.SHADE_FLAT);
//			sceneObject2.getShader().setShadingType(Shader.SHADE_FLAT);
//			break;
//		case '9':
//			sceneObject.getShader().setShadingType(Shader.SHADE_GOURAUD);
//			sceneObject2.getShader().setShadingType(Shader.SHADE_GOURAUD);
//			break;
//		case '+':
//			light.setStrength(light.getStrength()+1);
//			break;
//		case '-':
//			light.setStrength(light.getStrength()-1);
//			break;
//		}
//		if (event.getKeyCode() == 112) { 
//			Profiler.getInstance().start();
//		}
//		if (event.getKeyCode() == 113) { 
//			Profiler.getInstance().stop();
//		}
//		if (event.getKeyCode() == 114) { 
//			Profiler.getInstance().startLogging();
//		}
//		if (event.getKeyCode() == 115) { 
//			Profiler.getInstance().stopLogging();
//		}
//		if (event.getKeyCode() == 116) { 
//			Engine.getInstance().play();
//		}
//		if (event.getKeyCode() == 117) { 
//			Engine.getInstance().pause();
//		}
//		if (event.getKeyCode() == 38) { 
//			light.getTransform().translate(0, 0, 1);
//		}
//		if (event.getKeyCode() == 40) { 
//			light.getTransform().translate(0, 0, -1);
//		}
//		if (event.getKeyCode() == 37) { 
//			light.getTransform().translate(1, 0, 0);
//		}
//		if (event.getKeyCode() == 39) { 
//			light.getTransform().translate(-1, 0, 0);
//		}
////		System.out.println(event.getKeyCode());
//	}

//	boolean dragged = false;
//	int[] postition = new int[2];
//	@Override
//	public void leftClick(JPGEMouseEvent event) {
//		dragged = !dragged;
//		postition = event.getPosition();
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
//	public void positionUpdate(JPGEMouseEvent event) {
//		if (dragged) {
//			int fx = (int)Engine.getInstance().getSceneWindow().getPositionX();
//			int fy = (int)Engine.getInstance().getSceneWindow().getPositionY();
//			int x = (event.getPosition()[VectorUtils.X]-fx) - (windowWidth/2);
//			int y = (event.getPosition()[VectorUtils.Y]-fy) - (windowHeight/2);
////			int z = camera.getTransform().getRotation()[VectorUtils.Z];
//			//System.out.println("x " + x + ", y " + y + ", z " + z);
//			camera.getTransform().rotate(y/(windowHeight>>3), x/(windowWidth>>3), 0);
//		}
//	}
}
