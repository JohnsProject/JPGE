package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.johnsproject.jpge.Engine;
import com.johnsproject.jpge.Profiler;
import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.io.FileIO;

/**
 * The SceneWindow class is used to show a {@link Scene}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneWindow extends JPanel{

	private static final long serialVersionUID = -841144266539311921L;
	private int width = 0;
	private int height = 0;
	private JFrame frame;
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
	}
	
	/**
	 * Returns the depth buffer of this scene frame.
	 * 
	 * @return depth buffer of this scene frame.
	 */
	public int[] getDepthBuffer() {
		return zBuffer;
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		zBuffer = new int[width * height];
		Profiler.getInstance().getData().setWidth(width);
		Profiler.getInstance().getData().setHeight(height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Scene scene = Engine.getInstance().getScene();
		synchronized (scene.getCameras()) {
			for (int i = 0; i < scene.getCameras().size(); i++) {
				Camera camera = scene.getCameras().get(i);
				g.drawImage(camera.getViewBuffer(), camera.getPositionX(), camera.getPositionY(), null);
				camera.clearViewBuffer();
			}
		}
	}
}
