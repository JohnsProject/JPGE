/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
 * @author John´s Project - John Salomon
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
