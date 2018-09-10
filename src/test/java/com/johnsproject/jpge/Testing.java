package com.johnsproject.jpge;

import java.io.IOException;

import com.johnsproject.jpge.graphics.*;
import com.johnsproject.jpge.graphics.SceneRenderer.ProjectionType;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
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
	Mesh cube;
	Mesh monkey;
	int w = 1200, h = 920;
	public Testing() {
		try {
			cube = SOMImporter.load("/home/john/Development/Cube.som");
			monkey = SOMImporter.load("/home/john/Development/Monkey.som");
		} catch (ImportExeption e) {
			e.printStackTrace();
		}
		//Mesh mesh2 = new Mesh(FileIO.readFile("/media/john/HDD/Development/test.som"));
		sceneObject = new SceneObject("test", new Transform(Vector3Utils.convert(-100, 0, 1000), Vector3Utils.convert(90, 0, 0), Vector3Utils.convert(1, 1, 1)), cube);
		sceneObject2 = new SceneObject("test2", new Transform(Vector3Utils.convert(100, 0, 1000), Vector3Utils.convert(90, 0, 0), Vector3Utils.convert(1, 1, 1)), monkey);
		sceneObject.setShader(new TestShader());
		try {
			Texture t = new Texture(getClass().getResourceAsStream("/JohnsProject.png"));
			//Texture t = new Texture("/home/john/Development/Brick.jpg");
			//Texture t = new Texture("/home/john/Development/TestMonkey.png", 512, 512);
			sceneObject.getMesh().getMaterial(0).setTexture(t);
			sceneObject2.getMesh().getMaterial(0).setTexture(t);
			//sceneObject.getMesh().getMaterial(0).setTexture(new Texture("/home/john/Development/Brick.jpg", 101, 101));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//sceneObject2 = new SceneObject("test2", new Vector3(0, 0, 150), new Vector3(0, 0, 0), new Vector3(1, 1, 1), mesh2);
		camera = new Camera("testCam", new Transform(Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(1, 1, 1)), Vector2Utils.convert(0, 0), Vector2Utils.convert(w, h));
		camera.setShader(new TestPixelShader());
		camera2 = new Camera("testCam2", new Transform(Vector3Utils.convert(0, -2000, 1200), Vector3Utils.convert(90, 0, 0), Vector3Utils.convert(1, 1, 1)), Vector2Utils.convert(w-(w/3), 0), Vector2Utils.convert(w/3, h/3));	
		light = new Light("testLight", new Transform(Vector3Utils.convert(800, 0, 1000), Vector3Utils.convert(0, 0, 0), Vector3Utils.convert(1, 1, 1)));
		sceneFrame = new SceneFrame(w, h);
		sceneFrame.getScene().addSceneObject(sceneObject);
		sceneFrame.getScene().addSceneObject(sceneObject2);
		sceneFrame.getScene().addCamera(camera2);
		sceneFrame.setTitle("JPGE Test");
		sceneFrame.getScene().addCamera(camera);
		sceneFrame.getScene().addLight(light);
		//sceneObject.getMesh().playAnimation(1);
		KeyInputManager.getInstance().addKeyListener(this);
		MouseInputManager mim = new MouseInputManager(sceneFrame);
		mim.addMouseListener(this);
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
			sceneObject2.getTransform().rotate(0, 4, 0);
			break;
		case 'n':
			sceneObject.getTransform().rotate(4, 0, 0);
			sceneObject2.getTransform().rotate(4, 0, 0);
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
		}
		//System.out.println(event.getKeyCode());
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
			int x = Vector2Utils.getX(event.getPosition()) - (w/2);
			int y = Vector2Utils.getY(event.getPosition()) - (h/2);
			int z = Vector3Utils.getZ(camera.getTransform().getRotation());
			//System.out.println("x " + x + ", y " + y + ", z " + z);
			camera.getTransform().rotate(y/(h/5), x/(w/5), z);
		}
	}
}
