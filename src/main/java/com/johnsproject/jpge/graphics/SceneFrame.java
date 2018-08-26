package com.johnsproject.jpge.graphics;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import com.johnsproject.jpge.GameManager;
import com.johnsproject.jpge.event.UpdateEvent;
import com.johnsproject.jpge.event.UpdateEvent.UpdateType;
import com.johnsproject.jpge.event.UpdateListener;
import com.johnsproject.jpge.graphics.event.*;
import com.johnsproject.jpge.io.FileIO;

public class SceneFrame extends Frame implements CameraListener, UpdateListener{
	
	private Scene scene;
	private SceneRenderer renderer;
	private SceneAnimator animator;
	
	public SceneFrame (int width, int height){
		this.scene = new Scene();
		setSize(width, height);
		initializeFrame();
	}
	
	public SceneFrame (int width, int height, Scene scene){
		this.scene = scene;
		setSize(width, height);
		initializeFrame();
	}
	
	void initializeFrame(){
		this.setResizable(false);
		this.setVisible(true);
		renderer = new SceneRenderer(getWidth(), getHeight());
		animator = new SceneAnimator();
		try {
			setIconImage(FileIO.loadImage(getClass().getResourceAsStream("/JohnsProjectLogo.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.addKeyListener(KeyInputManager.getInstance());
		EventDispatcher.getInstance().addCameraListener(this);
		com.johnsproject.jpge.event.EventDispatcher.getInstance().addUpdateListener(this);
		GameManager.getInstance();
		this.setLayout(null);
		this.repaint();
		this.addWindowListener(new WindowAdapter(){
			  public void windowClosing(WindowEvent we){
			    System.exit(0);
			  }
		});
	}
	
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public SceneRenderer getSceneRenderer() {
		return renderer;
	}
	
	public SceneAnimator getSceneAnimator() {
		return animator;
	}
	
	@Override
	public void update(UpdateEvent event) {
		// TODO Auto-generated method stub
		if(event.getUpdateType() == UpdateType.graphics) {
			renderer.render(getScene(), event.getElapsedTime());
		}
		if(event.getUpdateType() == animator.getUpdateType()) {
			animator.animate(scene);
		}
	}
	
	@Override
	public void add(CameraEvent event) {
		this.add(event.getCamera());
		this.repaint();
	}

	@Override
	public void remove(CameraEvent event) {
		this.remove(event.getCamera());
		this.repaint();
	}
}
