package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.johnsproject.jpge.GameManager;
import com.johnsproject.jpge.Profiler;
import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.event.EventDispatcher;
import com.johnsproject.jpge.event.UpdateEvent;
import com.johnsproject.jpge.event.UpdateListener;
import com.johnsproject.jpge.io.FileIO;

/**
 * The SceneWindow class is used to show a {@link Scene}.
 * It contains a {@link Renderer} that is called to render the {@link Scene} when 
 * it receives an {@link UpdateEvent} from the {@link GameManager}.
 * It contains a {@link Animator} that is called to animate the {@link Scene} when 
 * it receives an {@link UpdateEvent} from the {@link GameManager}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneWindow extends JPanel implements UpdateListener{

	private static final long serialVersionUID = -841144266539311921L;
	private int width = 0;
	private int height = 0;
	private JFrame frame;
	private Renderer renderer;
	private Animator animator;
	private int[] zBuffer = null;
	
	/**
	 * Creates a new instance of the SceneWindow class filled with the given values.
	 * 
	 * @param width scene frame width.
	 * @param height scene frame height.
	 */
	public SceneWindow (int width, int height){
		this.width = width;
		this.height = height;
		frame = new JFrame();
		frame.setSize(width, height);
		this.setSize(width, height);
		initializeFrame();
	}
	
	/**
	 * Initializes the components and values of this scene frame.
	 */
	void initializeFrame(){
		zBuffer = new int[width * height];
		Profiler.getInstance().getData().setWidth(width);
		Profiler.getInstance().getData().setHeight(height);
		renderer = new Renderer();
		animator = new Animator();
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
	 * Returns the {@link Renderer} of this scene frame.
	 * 
	 * @return {@link Renderer} of this scene frame.
	 */
	public Renderer getRenderer() {
		return renderer;
	}
	
	/**
	 * Returns the {@link Animator} of this scene frame.
	 * 
	 * @return {@link Animator} of this scene frame.
	 */
	public Animator getAnimator() {
		return animator;
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		zBuffer = new int[width * height];
		Profiler.getInstance().getData().setWidth(width);
		Profiler.getInstance().getData().setHeight(height);
	}

	@Override
	public void update(UpdateEvent event) {
		if(event.getUpdateType() == GameManager.UPDATE_GRAPHICS) {
			renderer.render(event.getScene(), zBuffer);
			this.repaint();
		}
		if(event.getUpdateType() == animator.getUpdateType()) {
			animator.animate(event.getScene());
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Scene scene = GameManager.getInstance().getScene();
		synchronized (scene.getCameras()) {
			for (int i = 0; i < scene.getCameras().size(); i++) {
				Camera camera = scene.getCameras().get(i);
				g.drawImage(camera.getViewBuffer(), camera.getPositionX(), camera.getPositionY(), null);
				camera.clearViewBuffer();
			}
		}
	}
}
