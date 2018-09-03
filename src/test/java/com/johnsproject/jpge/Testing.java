package com.johnsproject.jpge;

import java.io.IOException;

import com.johnsproject.jpge.graphics.*;
import com.johnsproject.jpge.io.*;
import com.johnsproject.jpge.utils.Vector2Utils;
import com.johnsproject.jpge.utils.Vector3Utils;

public class Testing implements JPGEKeyListener, JPGEMouseListener {
	
	public static void main(String[] args){
		new Testing();
	}
	
	SceneFrame sceneFrame;
	SceneObject sceneObject;
	SceneObject sceneObject2;
	Camera camera;
	Camera camera2;
	Light light;
	int w = 1024, h = 720;
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
		sceneObject = new SceneObject("test", new Transform(Vector3Utils.convert(0, 0, 1000), Vector3Utils.convert(90, 0, 0), Vector3Utils.convert(5, 5, 5)), mesh);
		try {
			Texture t = new Texture(getClass().getResourceAsStream("/JohnsProject.png"));
			//Texture t = new Texture("/home/john/Dokumente/Earth.jpeg");
			sceneObject.getMesh().getMaterial(0).setTexture(t);
			//sceneObject.getMesh().getMaterial(0).setTexture(new Texture("/home/john/Development/Brick.jpg", 101, 101));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//sceneObject2 = new SceneObject("test2", new Vector3(0, 0, 150), new Vector3(0, 0, 0), new Vector3(1, 1, 1), mesh2);
		camera = new Camera("testCam", new Transform(Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(1, 1, 1)), Vector2Utils.convert(0, 0), Vector2Utils.convert(w, h));
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
		MouseInputManager.getInstance().addMouseListener(this);
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

	public void keyReleased(JPGEKeyEvent event) {
	}

	boolean dragged = false;
	int postition = 0;
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
			int x = (w/2)-Vector2Utils.getX(event.getPosition());
			int y = (h/2)-Vector2Utils.getY(event.getPosition());
			int z = Vector3Utils.getZ(camera.getTransform().getRotation());
			sceneObject.getTransform().rotate(x>>15, y>>15, z);
		}
	}
}
