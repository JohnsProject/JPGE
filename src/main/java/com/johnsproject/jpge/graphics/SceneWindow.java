package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.johnsproject.jpge.GameManager;
import com.johnsproject.jpge.event.EventDispatcher;
import com.johnsproject.jpge.event.UpdateEvent;
import com.johnsproject.jpge.event.UpdateEvent.UpdateType;
import com.johnsproject.jpge.event.UpdateListener;
import com.johnsproject.jpge.io.FileIO;

/**
 * The SceneWindow class is used to show a {@link Scene}.
 * It contains a {@link SceneRenderer} that is called to render the {@link Scene} when 
 * it receives an {@link UpdateEvent} from the {@link GameManager}.
 * It contains a {@link SceneAnimator} that is called to animate the {@link Scene} when 
 * it receives an {@link UpdateEvent} from the {@link GameManager}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneWindow extends JPanel implements UpdateListener{

	private static final long serialVersionUID = -841144266539311921L;
	private int width = 0;
	private int height = 0;
	private JFrame frame;
	private Scene scene;
	private SceneRenderer renderer;
	private SceneAnimator animator;
	
	/**
	 * Creates a new instance of the SceneWindow class filled with the given values.
	 * 
	 * @param width scene frame width.
	 * @param height scene frame height.
	 */
	public SceneWindow (int width, int height){
		this.width = width;
		this.height = height;
		this.scene = new Scene();
		frame = new JFrame();
		frame.setSize(width, height);
		this.setSize(width, height);
		initializeFrame();
	}
	
	/**
	 * Creates a new instance of the SceneWindow class filled with the given values.
	 * 
	 * @param width scene frame width.
	 * @param height scene frame height.
	 * @param scene scene to be shown by this scene frame.
	 */
	public SceneWindow (int width, int height, Scene scene){
		this.width = width;
		this.height = height;
		this.scene = scene;
		frame = new JFrame();
		frame.setSize(width, height);
		this.setSize(width, height);
		initializeFrame();
	}
	
	/**
	 * Initializes the components and values of this scene frame.
	 */
	void initializeFrame(){
		renderer = new SceneRenderer(width, height);
		animator = new SceneAnimator();
		try {
			frame.setIconImage(FileIO.loadImage(getClass().getResourceAsStream("/JohnsProjectLogo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("JPGE");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		EventDispatcher.getInstance().addUpdateListener(this);
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
			renderer.render(getScene());
			this.repaint();
		}
		if(event.getUpdateType() == animator.getUpdateType()) {
			animator.animate(getScene());
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, width, height);
		synchronized (scene.getCameras()) {
			for (int i = 0; i < scene.getCameras().size(); i++) {
				Camera camera = scene.getCameras().get(i);
				int[] screenPos = camera.getScreenPosition();
				g.drawImage(camera.getViewBuffer(), screenPos[0], screenPos[1], null);
				camera.clearViewBuffer();
			}
		}
	}
}
