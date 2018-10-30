package com.johnsproject.jpge;

import java.io.IOException;

import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.Light;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Texture;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.graphics.*;
import com.johnsproject.jpge.io.*;
import com.johnsproject.jpge.utils.VectorUtils;

public class ExampleScene implements JPGEKeyListener, JPGEMouseListener{
	
	public static void main(String[] args){
		new ExampleScene();
	}
	
	SceneWindow sceneWindow;
	SceneObject sceneObject, sceneObject2;
	Camera camera, camera2;
	Light light, light2;
	Mesh mesh1, mesh2;
	int w = 1024, h = 720;
	public ExampleScene() {		
		try {
//			mesh1 = SOMImporter.load("/home/john/Development/test.som");
			mesh1 = SOMImporter.load(getClass().getResourceAsStream("/cube.som"));
			mesh2 = SOMImporter.load(getClass().getResourceAsStream("/monkey.som"));
		} catch (ImportExeption e) {
			e.printStackTrace();
		}
		sceneObject = new SceneObject("test", new Transform(new int[] {-2000, 0, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), mesh1);
		sceneObject2 = new SceneObject("test2", new Transform(new int[] {2000, -3000, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), mesh2);
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
		camera = new Camera("testCam", new Transform(new int[] {10000, -6000, -10000}, new int[] {25, -45, 25}, new int[] {1, 1, 1}), 0, 0, w, h);
		camera2 = new Camera("testCam2", new Transform(new int[] {0, -10000, 0}, new int[] {90, 0, 0}, new int[] {1, 1, 1}), w-(w/3), 0, w/3, h/3);	
		light = new Light("testLight", new Transform(new int[] {0, 0, 0}, new int[] {0, 0, 0}, new int[] {1, 1, 1}));
		sceneWindow = new SceneWindow(w, h);
		Scene scene = new Scene();
		scene.addLight(light);
		scene.addSceneObject(sceneObject);
		scene.addSceneObject(sceneObject2);
		scene.addCamera(camera);
		scene.addCamera(camera2);
		GameManager.getInstance().setScene(scene);
		KeyInputManager.getInstance().addKeyListener(this);
		MouseInputManager.getInstance().addMouseListener(this);
	}
	
	public void keyPressed(JPGEKeyEvent event) {
		switch (event.getKey()) {
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
			sceneObject.getTransform().rotate(0, 3, 0);
			sceneObject2.getTransform().rotate(0, 3, 0);
			break;
		case 'n':
			sceneObject.getTransform().rotate(3, 0, 0);
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
			sceneObject.getShader().setDrawingType(Shader.DRAW_VERTEX);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_VERTEX);
			break;
		case '2':
			sceneObject.getShader().setDrawingType(Shader.DRAW_WIREFRAME);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_WIREFRAME);
			break;
		case '3':
			sceneObject.getShader().setDrawingType(Shader.DRAW_FLAT);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_FLAT);
			break;
		case '4':
			sceneObject.getShader().setDrawingType(Shader.DRAW_TEXTURED);
			sceneObject2.getShader().setDrawingType(Shader.DRAW_TEXTURED);
			break;
		case '6':
			sceneObject.getShader().setProjectionType(Shader.PROJECT_ORTHOGRAPHIC);
			sceneObject2.getShader().setProjectionType(Shader.PROJECT_ORTHOGRAPHIC);
			break;
		case '7':
			sceneObject.getShader().setProjectionType(Shader.PROJECT_PERSPECTIVE);
			sceneObject2.getShader().setProjectionType(Shader.PROJECT_PERSPECTIVE);
		case '8':
			sceneObject.getShader().setShadingType(Shader.SHADE_FLAT);
			sceneObject2.getShader().setShadingType(Shader.SHADE_FLAT);
			break;
		case '9':
			sceneObject.getShader().setShadingType(Shader.SHADE_GOURAUD);
			sceneObject2.getShader().setShadingType(Shader.SHADE_GOURAUD);
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
			light.getTransform().translate(0, 0, 1);
		}
		if (event.getKeyCode() == 40) { 
			light.getTransform().translate(0, 0, -1);
		}
		if (event.getKeyCode() == 37) { 
			light.getTransform().translate(1, 0, 0);
		}
		if (event.getKeyCode() == 39) { 
			light.getTransform().translate(-1, 0, 0);
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
			int fx = (int)sceneWindow.getLocationOnScreen().getX();
			int fy = (int)sceneWindow.getLocationOnScreen().getY();
			int x = (event.getPosition()[VectorUtils.X]-fx) - (w/2);
			int y = (event.getPosition()[VectorUtils.Y]-fy) - (h/2);
//			int z = camera.getTransform().getRotation()[VectorUtils.Z];
			//System.out.println("x " + x + ", y " + y + ", z " + z);
			camera.getTransform().rotate(y/(h>>3), x/(w>>3), 0);
		}
	}
}
