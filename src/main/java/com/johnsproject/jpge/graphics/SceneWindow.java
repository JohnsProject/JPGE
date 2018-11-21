package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.johnsproject.jpge.Engine;
import com.johnsproject.jpge.dto.RenderBuffer;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.io.FileIO;

/**
 * The SceneWindow class is used to show a {@link Scene}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private int width = 0;
	private int height = 0;
	private ScenePanel panel;
	private RenderBuffer renderBuffer;
	
	/**
	 * Creates a new instance of the SceneWindow class filled with the given values.
	 * 
	 * @param width scene this width.
	 * @param height scene this height.
	 */
	public SceneWindow (int width, int height){
		setSize(width, height);
		panel = new ScenePanel();
		renderBuffer = Engine.getInstance().getRenderBuffer();
		try {
			this.setIconImage(FileIO.loadImage(getClass().getResourceAsStream("/JohnsProjectLogo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("JPGE");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
	}
	
	public ScenePanel getPanel() {
		return panel;
	}
	
	/**
	 * Tells this SceneWindow to draw the scene being used by the {@link Engine}.
	 */
	public void draw() {
		panel.repaint();
		if (this.getWidth() != width || this.getHeight() != height) {
			width = this.getWidth();
			height = this.getHeight();
			panel.setSize(width, height);
		}
	}
	
	public class ScenePanel extends JPanel{
		
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(renderBuffer.getFrameBuffer(), 0, 0, width, height, null);
		}
	}

}
