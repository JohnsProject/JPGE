package com.johnsproject.jpge;

import java.io.IOException;

import com.johnsproject.jpge.graphics.*;
import com.johnsproject.jpge.graphics.SceneRenderer.ProjectionType;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.io.*;
import com.johnsproject.jpge.utils.VectorUtils;

public class Test implements JPGEKeyListener, JPGEMouseListener {
	
	public static void main(String[] args){
		new Test();
	}
	
	SceneFrame sceneFrame;
	SceneObject sceneObject, sceneObject2;
	Camera camera, camera2;
	Light light, light2;
	Mesh cube, monkey;
	int w = 1024, h = 860;
	public Test() {		
		try {
			cube = SOMImporter.load("/home/john/Development/test.som");
			monkey = SOMImporter.load("/home/john/Development/Monkey.som");
		} catch (ImportExeption e) {
			e.printStackTrace();
		}
		//Mesh mesh2 = new Mesh(FileIO.readFile("/media/john/HDD/Development/test.som"));
		sceneObject = new SceneObject("test", new Transform(new int[] {-200, 0, 1000}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), cube);
		sceneObject2 = new SceneObject("test2", new Transform(new int[] {200, 0, 1000}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), monkey);
		sceneObject.setShader(new TestShader());
		sceneObject2.setShader(new TestShader());
		try {
			Texture t = new Texture(getClass().getResourceAsStream("/JohnsProject.png"));
			//Texture t = new Texture("/home/john/Development/Brick.jpg");
			//Texture t = new Texture("/home/john/Development/TestMonkey.png", 512, 512);
			//t = new Texture(50, 50);
			for (int i = 0; i < cube.getMaterials().length; i++) {
				cube.getMaterial(i).setTexture(t);
			}
			for (int i = 0; i < monkey.getMaterials().length; i++) {
				monkey.getMaterial(i).setTexture(t);
			}
			//sceneObject.getMesh().getMaterial(0).setTexture(new Texture("/home/john/Development/Brick.jpg", 101, 101));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//sceneObject2 = new SceneObject("test2", new Vector3(0, 0, 150}, new Vector3(0, 0, 0}, new Vector3(1, 1, 1}, mesh2);
		camera = new Camera("testCam", new Transform(new int[] {0, 0, 0}, new int[] {0, 0, 0}, new int[] {1, 1, 1}), 0, 0, w, h);
		camera.setShader(new TestPixelShader());
		camera2 = new Camera("testCam2", new Transform(new int[] {0, -2000, 1200}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), w-(w/3), 0, w/3, h/3);	
		light = new Light("testLight", new Transform(new int[] {0, 0, 0}, new int[] {0, 0, 0}, new int[] {1, 1, 1}));
		//light2 = new Light("testLight2", new int[] {10, 0, 0});
		sceneFrame = new SceneFrame(w, h);
		sceneFrame.getScene().addLight(light);
		//sceneFrame.getScene().addLight(light2);
		sceneFrame.getScene().addSceneObject(sceneObject);
		sceneFrame.getScene().addSceneObject(sceneObject2);
		//sceneFrame.getScene().addCamera(camera2);
		//sceneFrame.setTitle("JPGE Test");
		sceneFrame.getScene().addCamera(camera);
//		sceneObject.getMesh().playAnimation(1);
		KeyInputManager.getInstance().addKeyListener(this);
		MouseInputManager.getInstance().addMouseListener(this);
		GameManager.getInstance();
		//Profiler.getInstance().start();
	}
	
	public void keyPressed(JPGEKeyEvent event) {
		switch (event.getKey()) {
		case 'w':
			camera.getTransform().translateLocal(0, 0, 6);
			break;
		case 's':
			camera.getTransform().translateLocal(0, 0, -6);
			break;
		case 'a':
			camera.getTransform().translateLocal(-6, 0, 0);
			break;
		case 'd':
			camera.getTransform().translateLocal(6, 0, 0);
			break;
		case 'e':
			camera.getTransform().translateLocal(0, 6, 0);
			break;
		case 'y':
			camera.getTransform().translateLocal(0, -6, 0);
			break;
		case 'b':
			sceneObject.getTransform().rotate(0, 1, 0);
			sceneObject2.getTransform().rotate(0, 1, 0);
			break;
		case 'n':
			sceneObject.getTransform().rotate(1, 0, 0);
			sceneObject2.getTransform().rotate(1, 0, 0);
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
			camera.setRenderingType(RenderingType.vertex);
			break;
		case '2':
			camera.setRenderingType(RenderingType.wireframe);
			break;
		case '3':
			camera.setRenderingType(RenderingType.solid);
			break;
		case '4':
			camera.setRenderingType(RenderingType.textured);
			break;
		case '6':
			camera.setProjectionType(ProjectionType.orthographic);
			break;
		case '7':
			camera.setProjectionType(ProjectionType.perspective);
		case '8':
			sceneObject.setActive(true);
			sceneObject2.setActive(true);
			break;
		case '9':
			sceneObject.setActive(true);
			sceneObject2.setActive(false);
			break;
		case '0':
			sceneObject.setActive(false);
			sceneObject2.setActive(true);
			break;
		case '+':
			light.setStrength(light.getStrength()+1);
			break;
		case '-':
			light.setStrength(light.getStrength()-1);
			break;
		}
		if (event.getKeyCode() == 112) { 
			Profiler.getInstance().start();
		}
		if (event.getKeyCode() == 113) { 
			Profiler.getInstance().stop();
		}
		if (event.getKeyCode() == 114) { 
			Profiler.getInstance().startLogging();
		}
		if (event.getKeyCode() == 115) { 
			Profiler.getInstance().stopLogging();
		}
		if (event.getKeyCode() == 116) { 
			GameManager.getInstance().play();
		}
		if (event.getKeyCode() == 117) { 
			GameManager.getInstance().pause();
		}
		if (event.getKeyCode() == 38) { 
			light.getTransform().translate(0, 0, 6);
		}
		if (event.getKeyCode() == 40) { 
			light.getTransform().translate(0, 0, -6);
		}
		if (event.getKeyCode() == 37) { 
			light.getTransform().translate(6, 0, 0);
		}
		if (event.getKeyCode() == 39) { 
			light.getTransform().translate(-6, 0, 0);
		}
		//System.out.println(event.getKeyCode());
	}

	public void keyReleased(JPGEKeyEvent event) {
	}

	boolean dragged = false;
	int[] postition = new int[2];
	@Override
	public void leftClick(JPGEMouseEvent event) {
		dragged = !dragged;
		postition = event.getPosition();
	}

	@Override
	public void middleClick(JPGEMouseEvent event) {
		
	}

	@Override
	public void rightClick(JPGEMouseEvent event) {
		
	}

	@Override
	public void positionUpdate(JPGEMouseEvent event) {
		if (dragged) {
			int fx = (int)sceneFrame.getLocationOnScreen().getX();
			int fy = (int)sceneFrame.getLocationOnScreen().getY();
			int x = (event.getPosition()[VectorUtils.X]-fx) - (w/2);
			int y = (event.getPosition()[VectorUtils.Y]-fy) - (h/2);
			int z = camera.getTransform().getRotation()[VectorUtils.Z];
			//System.out.println("x " + x + ", y " + y + ", z " + z);
			camera.getTransform().rotate(y/(h>>3), x/(w>>3), z);
		}
	}
}
