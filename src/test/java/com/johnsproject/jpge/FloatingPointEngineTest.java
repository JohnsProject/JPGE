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
package com.johnsproject.jpge;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.io.SOMImporter;
import com.johnsproject.jpge.utils.ColorUtils;

public class FloatingPointEngineTest extends JPanel{
	
	private static final int FOV = 500;
	private static final int NCP = 90;
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 860;
	private static final int HALFWIDTH = WIDTH / 2;
	private static final int HALFHEIGHT = HEIGHT / 2;
	private static final int vx = 0, vy = 1, vz = 2;

	private JFrame frame;
	private BufferedImage viewBuffer;
	private int[] viewBufferData;
	private int defautColor = ColorUtils.convert(0, 0, 0, 0);
	private int lastElapsed = 0;
	
	private float[] objPos = new float[] {0, 50, -300};
	private float[] objRot = new float[] {90, 0, 0};
	
	private float[][] vertices = new float[0][0];
	private float[][] verticesCache = new float[0][0];
	private int[][] faces = new int[0][0];
	
	@Test
	public void Test() throws Exception {
		this.setSize(WIDTH, HEIGHT);
		this.viewBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB_PRE);
		this.viewBufferData = ((DataBufferInt)viewBuffer.getRaster().getDataBuffer()).getData();
		loadMesh();
		initializeFrame();
		Thread renderLoop = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					clearBuffer();
					int before = (int)System.nanoTime();
					render();
					lastElapsed = (int)System.nanoTime() - before;
					drawBuffer();
					try {
						Thread.sleep(16);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		renderLoop.start();
	}
	
	public void initializeFrame() {
		frame = new JFrame("JPGE - Floating Point Edition");
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.add(this);
	}
	
	public void loadMesh() {
		Mesh mesh = null;
		try {
			mesh = SOMImporter.load(getClass().getResourceAsStream(Mesh.RESOURCES_MONKEY));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mesh != null) {
			vertices = new float[mesh.getVertexes().length][3];
			verticesCache = new float[vertices.length][3];
			for (int i = 0; i < vertices.length; i++) {
				for (int j = 0; j < 3; j++) {
					vertices[i][j] = mesh.getVertex(i).getLocation()[j] / 100;
					verticesCache[i][j] = mesh.getVertex(i).getLocation()[j] / 100;
				}
			}
			faces = new int[mesh.getFaces().length][3];
			for (int i = 0; i < faces.length; i++) {
				faces[i][0] = mesh.getFace(i).getVertex1();
				faces[i][1] = mesh.getFace(i).getVertex2();
				faces[i][2] = mesh.getFace(i).getVertex3();
			}
		}
	}
	
	public void drawBuffer() {
		repaint();
	}
	
	public void clearBuffer() {
		viewBuffer.getGraphics().fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(viewBuffer, 0, 0, null);
		g.drawString("fps : " + (1000/((lastElapsed / 1000000)+1))  + " ms", 10, 20);
		g.drawString("vertices : " + vertices.length, 10, 35);
		g.drawString("faces : " + faces.length, 10, 50);
		clearBuffer();
	}
	
	public void render() {
		last += 1;
		for (int i = 0; i < vertices.length; i ++) {
			float[] cache = verticesCache[i];
			cache[vx] = vertices[i][vx];
			cache[vy] = vertices[i][vy];
			cache[vz] = vertices[i][vz];
			applyObjTransform(cache);
			perspectiveProject(cache);
			// draw vertexes
			setPixel((int)cache[vx], (int)cache[vy], (int)cache[vz], defautColor);
		}
		for (int i = 0; i < faces.length; i ++) {
			int[] face = faces[i];
			float[] v1 = verticesCache[face[0]];
			float[] v2 = verticesCache[face[1]];
			float[] v3 = verticesCache[face[2]];
			int a = (int)((v2[vx] - v1[vx]) * (v3[vy] - v1[vy]) - (v3[vx] - v1[vx]) * (v2[vy] - v1[vy]));
			if (a < 0) {
				drawLine(v1[vx], v1[vy], v1[vz], v2[vx], v2[vy], v2[vz], defautColor);
				drawLine(v2[vx], v2[vy], v2[vz], v3[vx], v3[vy], v3[vz], defautColor);
				drawLine(v3[vx], v3[vy], v3[vz], v1[vx], v1[vy], v1[vz], defautColor);
			}
		}
	}
	
	float last = 0;
	public void applyObjTransform(float[] vector) {
		rotateX(objRot[vx], vector);
		rotateY(objRot[vy] + last, vector);
		rotateZ(objRot[vz], vector);
		vector[vx] += objPos[vx];
		vector[vy] += objPos[vy];
		vector[vz] += objPos[vz];
	}
	
	public void rotateX(float angle, float[] vector) {
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		float y = (float)(vector[vy] * cos - vector[vz] * sin);
		float z = (float)(vector[vz] * cos + vector[vy] * sin);
		vector[vy] = y;
		vector[vz] = z;
	}
	
	public void rotateY(float angle, float[] vector) {
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		float x = (float)(vector[vx] * cos - vector[vz] * sin);
		float z = (float)(vector[vz] * cos + vector[vx] * sin);
		vector[vx] = x;
		vector[vz] = z;
	}
	
	public void rotateZ(float angle, float[] vector) {
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		float x = (float)(vector[vx] * cos - vector[vy] * sin);
		float y = (float)(vector[vy] * cos + vector[vx] * sin);
		vector[vx] = x;
		vector[vy] = y;
	}
	
	public void perspectiveProject(float[] vector) {
		vector[vx] = ((vector[vx] * FOV) / (vector[vz] + FOV)) + HALFWIDTH;
		vector[vy] = ((vector[vy] * FOV) / (vector[vz] + FOV)) + HALFHEIGHT;
	}
	
	public void setPixel(int x, int y, int z, int color) {
		int pos = x + (y * WIDTH);
		if (pos > 0 && pos < viewBufferData.length)
			viewBufferData[x + (y * WIDTH)] = color;
	}
	
	public void drawLine(float x1, float y1, float z1, float x2, float y2, float z2, int color) {
		float lineWidth = x2 - x1;
		float lineHeight = y2 - y1;
		float lineDepth = z2 - z1;
		float d = 0, dz = z1, m = 0, mz = 0;
		if (Math.abs(lineWidth) >= Math.abs(lineHeight)) {
			d = y1;
			m = lineHeight / lineWidth;
			mz = lineDepth / lineWidth;
			float x = x1;
			for (; x < x2; x++) {
				setPixel((int)x, (int)d, (int)dz, color);
				d += m;
				dz += mz;
			}
			d = y1;
			x = x1;
			for (; x > x2; x--) {
				setPixel((int)x, (int)d, (int)dz, color);
				d -= m;
				dz -= mz;
			}
		}else {
			d = x1;
			m = lineWidth / lineHeight;
			mz = lineDepth / lineHeight;
			float y = y1;
			for (; y < y2; y++) {
				setPixel((int)d, (int)y, (int)dz, color);
				d += m;
				dz += mz;
			}
			d = x1;
			y = y1;
			for (; y > y2; y--) {
				setPixel((int)d, (int)y, (int)dz, color);
				d -= m;
				dz -= mz;
			}
		}
	}

}
