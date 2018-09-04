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

/**
 * The SceneFrame class is used to show a {@link Scene}, 
 * its like a window or portal that shows another world.
 * It contains a {@link SceneRenderer} that is called to render the {@link Scene} when 
 * it receives an {@link UpdateEvent} from the {@link GameManager}.
 * It contains a {@link SceneAnimator} that is called to animate the {@link Scene} when 
 * it receives an {@link UpdateEvent} from the {@link GameManager}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneFrame extends Frame implements CameraListener, UpdateListener{

	private static final long serialVersionUID = -841144266539311921L;
	private Scene scene;
	private SceneRenderer renderer;
	private SceneAnimator animator;
	
	/**
	 * Creates a new instance of the SceneFrame class filled with the given values.
	 * 
	 * @param width scene frame width.
	 * @param height scene frame height.
	 */
	public SceneFrame (int width, int height){
		this.scene = new Scene();
		setSize(width, height);
		initializeFrame();
	}
	
	/**
	 * Creates a new instance of the SceneFrame class filled with the given values.
	 * 
	 * @param width scene frame width.
	 * @param height scene frame height.
	 * @param scene scene to be shown by this scene frame.
	 */
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
			e.printStackTrace();
		}
		//this.addKeyListener(KeyInputManager.getInstance());
		GraphicsEventDispatcher.getInstance().addCameraListener(this);
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
	
	/**
	 * Returns the {@link Scene} of this scene frame.
	 * 
	 * @return {@link Scene} of this scene frame.
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Sets the {@link Scene} of this scene frame equals to the given {@link Scene}.
	 * 
	 * @param scene {@link Scene} to set.
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * Returns the {@link SceneRenderer} of this scene frame.
	 * 
	 * @return {@link SceneRenderer} of this scene frame.
	 */
	public SceneRenderer getSceneRenderer() {
		return renderer;
	}
	
	/**
	 * Returns the {@link SceneAnimator} of this scene frame.
	 * 
	 * @return {@link SceneAnimator} of this scene frame.
	 */
	public SceneAnimator getSceneAnimator() {
		return animator;
	}
	
	@Override
	public void update(UpdateEvent event) {
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

	@Override
	public String toString() {
		return "SceneFrame [scene=" + scene.toString() + ", renderer=" + renderer.toString() + ", animator=" + animator.toString() + "]";
	}
}
