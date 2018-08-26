package com.johnsproject.jpge;

import java.io.IOException;

import com.johnsproject.jpge.graphics.*;
import com.johnsproject.jpge.io.*;
import com.johnsproject.jpge.utils.Vector3Utils;

public class Testing implements KeyListener {
	
	public static void main(String[] args) {
//		int t = Math.round(((Runtime.getRuntime().totalMemory()/ 1024) / 1024));
//		int m = Math.round(((Runtime.getRuntime().maxMemory()/ 1024) / 1024));
//		int m4 = m/4;
//		if (t < m4) {
//			try {
//				 String separator = System.getProperty("file.separator");
//				    String classpath = System.getProperty("java.class.path");
//				    String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
//				    ProcessBuilder processBuilder = 
//				            new ProcessBuilder(path, "-Xms" + (m4+10) + "m", "-Xmx" + m + "m", "-cp", classpath, Testing.class.getName());
//				    Process process = processBuilder.start();
//				    System.exit(0);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		new Testing();
	}
	
	SceneFrame sceneFrame;
	SceneObject sceneObject;
	SceneObject sceneObject2;
	Camera camera;
	Camera camera2;
	Light light;
	int w = 1440, h = 1024;
	public Testing() {
		sceneFrame = new SceneFrame(w, h);
		Mesh mesh = null;
		try {
			mesh = SOMImporter.load("/home/john/Development/test.som");
		} catch (ImportExeption e) {
			e.printStackTrace();
		}
		mesh.playAnimation(0);
		//Mesh mesh2 = new Mesh(FileIO.readFile("/media/john/HDD/Development/test.som"));
		sceneObject = new SceneObject("test", new Transform(Vector3Utils.convert(0, 0, 1000), Vector3Utils.convert(90, 0, 0), Vector3Utils.convert(1, 1, 1)), mesh);
		try {
			//sceneObject.getMesh().getMaterial(0).setTexture(new Image(getClass().getResourceAsStream("/JohnsProjectLogo.png"), 101, 101));
			sceneObject.getMesh().getMaterial(0).setTexture(new Texture("/home/john/Development/Brick.jpg", 101, 101));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sceneObject2 = new SceneObject("test2", new Vector3(0, 0, 150), new Vector3(0, 0, 0), new Vector3(1, 1, 1), mesh2);
		camera = new Camera("testCam", new Transform(Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(1, 1, 1)), new int[] {0, 0}, new int[] {w, h});
		//camera2 = new Camera("testCam2", new int[] {0, 0, 0}, new int[] {0, 0, 0}, new int[] {700, 200}, new int[] {w/2, h/2});	
		light = new Light("testLight", new Transform(Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(1, 1, 1)));
		sceneFrame.getScene().addSceneObject(sceneObject);
		//sceneFrame.getScene().addSceneObject(sceneObject2);
		sceneFrame.getScene().addCamera(camera);
		sceneFrame.setTitle("JPGE Test");
		//sceneFrame.getScene().addCamera(camera2);
		sceneFrame.getScene().addLight(light);
		//sceneObject.getMesh().playAnimation(1);
		KeyInputManager.getInstance().addKeyListener(this);
	}

	public void keyPressed(KeyEvent event) {
		switch (event.getKey()) {
		case 'w':
			camera.getTransform().translate(0, 0, 6);
			break;
		case 's':
			camera.getTransform().translate(0, 0, -6);
			break;
		case 'a':
			camera.getTransform().translate(-6, 0, 0);
			break;
		case 'd':
			camera.getTransform().translate(6, 0, 0);
			break;
		case 'e':
			camera.getTransform().translate(0, 6, 0);
			break;
		case 'y':
			camera.getTransform().translate(0, -6, 0);
			break;
		case 'b':
			sceneObject.getTransform().rotate(0, 4, 0);
			break;
		case 'n':
			sceneObject.getTransform().rotate(4, 0, 0);
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
		}
	}

	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
	}
}
