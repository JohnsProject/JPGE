package com.johnsproject.jpge;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.johnsproject.jpge.io.FileIO;

/**
 * The Profiler class profiles the engine it shows informations about what the engine is currently doing.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Profiler extends Frame{

	private static final long serialVersionUID = -6448985233395265490L;
	private static Profiler instance;

	/**
	 * Returns an instance of the profiler class.
	 * 
	 * @return instance of the profiler class.
	 */
	public static Profiler getInstance() {
		if (instance == null) {
			instance = new Profiler();
		}
		return instance;
	}
	
	private static final int WIDTH = 330, HEIGHT = 260;
	private boolean profile = false;
	private ProfilerData data;
	private Thread profileThread;
	
	/**
	 * Creates a new instance of the profiler class.
	 */
	public Profiler () {
		data = new ProfilerData();
	}
	
	
	
	/**
	 * Tells this profiler to start profiling.
	 */
	private void startProfiling() {
		if (this.getTitle().equals("")) {
			this.setTitle("JPGE Profiler");
			try {
				this.setIconImage(FileIO.loadImage(getClass().getResourceAsStream("/JohnsProjectLogo.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setResizable(false);
			this.setSize(WIDTH, HEIGHT);
			this.setLayout(null);
			this.repaint();
			this.addWindowListener(new WindowAdapter(){
				  public void windowClosing(WindowEvent we){
				    stop();
				  }
			});
			try {
				img = FileIO.loadImage(getClass().getResourceAsStream("/JohnsProject.png"), WIDTH, HEIGHT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.setVisible(true);
		startProfilerUpdate();
	}
	
	/**
	 * Tells this profiler to start its update thread.
	 */
	private void startProfilerUpdate() {
		profileThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isProfiling()) {
					print();
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		profileThread.start();
	}
	
	private void print() {
		this.repaint();
	}
	
	private BufferedImage img = null;
	@Override
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
		g.setColor(Color.WHITE);
		int x = 2, x2 = 120, y = 45, step = 17;
		int maxMem = Math.round(Runtime.getRuntime().totalMemory() >> 20);
		int freeMem = Math.round(Runtime.getRuntime().freeMemory() >> 20);
		int usedMem = (maxMem - freeMem);
		int fps  = 1000/(((getData().getRenderTime()) / 1000000)+1);
		g.drawString("COMMOM DATA", x, y);
		y += step;
		g.drawString("- Memory usage : ", x, y);
		g.drawString(getBar(usedMem, maxMem) + usedMem + " / " + maxMem + " MB", x2, y);
		y += step+10;
		g.drawString("GRAPHICS DATA", x, y);
		y += step;
		g.drawString("- FPS : ", x, y);
		g.drawString("" + fps, x2, y);
		y += step;
		g.drawString("- Render time : ", x, y);
		g.drawString((getData().getRenderTime() / 1000000) + " ms", x2, y);
		y += step;
		g.drawString("- Rendering res. : ", x, y);
		g.drawString(getData().getWidth() + " x " + getData().getHeight(), x2, y);
		y += step;
		g.drawString("- Rendered Tris : ", x, y);
		g.drawString(getBar(getData().getRenderedPolys(), getData().getMaxPolys())
				+ getData().getRenderedPolys() + " / " + getData().getMaxPolys(), x2, y);
		y += step+10;
		g.drawString("INPUT DATA", x, y);
		y += step;
		g.drawString("- Input time : ", x, y);
		g.drawString((getData().getInputTime() / 1000000) + " ms", x2, y);
		y += step+10;
		g.drawString("PHYSICS DATA", x, y);
		y += step;
		g.drawString("- Physics time : ", x, y);
		g.drawString((getData().getPhysicsTime() / 1000000) + " ms", x2, y);
	}
	
	private String getBar(int current, int max) {
		String result = "", bar = "|";
		int precision = 20, i = 0;
		current = (current * precision)/max;
		max = (max * precision)/ max;
		result += bar;
		for (; i < current; i++) result += bar;
		for (; i < max; i++) result += " ";
		result += bar + " ";
		return result;
	}
	
	/**
	 * Tells this profiler to start profiling.
	 */
	public void start() {
		if (!isProfiling()) {
			profile = true;
			startProfiling();
		}
	}
	
	/**
	 * Tells this profiler to stop profiling.
	 */
	public void stop() {
		if (isProfiling()) {
			profile = false;
			this.setVisible(false);
		}
	}
	
	/**
	 * Returns if this profiler is profiling.
	 * 
	 * @return if this profiler is profiling.
	 */
	public boolean isProfiling() {
		return profile;
	}
	
	/**
	 * Returns the data of this profiler.
	 * This is used by the engine to update the profiler value so its not recommended to change the values of it.
	 * 
	 * @return data of this profiler.
	 */
	public ProfilerData getData() {
		return data;
	}
}
