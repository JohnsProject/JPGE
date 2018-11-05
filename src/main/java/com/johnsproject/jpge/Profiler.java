package com.johnsproject.jpge;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.io.FileIO;
import com.sun.management.OperatingSystemMXBean;

/**
 * The Profiler class shows informations about what the engine is currently doing.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Profiler{
	
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
	
	private static final int WIDTH = 330;
	private static final int HEIGHT = 330;
	private static final int POS_X = 2;
	private static final int POS_X2 = 120;
	private static final int START_Y = 12;
	private static final int STEP = 17;
	
	
	private boolean profile = false;
	private boolean log = false;
	private Thread profileThread;
	private OperatingSystemMXBean osxb = null;
	private JFrame frame;
	private ProfilerPanel panel;
	private BufferedImage backroundImage = null;
	
	/**
	 * Creates a new instance of the profiler class.
	 */
	public Profiler () {
		osxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		frame = new JFrame();
		panel = new ProfilerPanel();
	}
	
	
	
	/**
	 * Tells this profiler to start profiling.
	 */
	private void startProfiling() {
		if (frame.getTitle().equals("")) {
			frame.setTitle("JPGE Profiler");
			try {
				frame.setIconImage(FileIO.loadImage(getClass().getResourceAsStream("/JohnsProjectLogo.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.setResizable(false);
			frame.setSize(WIDTH, HEIGHT);
			panel.setSize(WIDTH, HEIGHT);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(panel);
			try {
				backroundImage = FileIO.loadImage(getClass().getResourceAsStream("/JohnsProject.png"), WIDTH, HEIGHT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		frame.setVisible(true);
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
		panel.repaint();
	}
	
	private int logCommom(Graphics g, int y) {
		int cpuLoad = (int)Math.round((osxb.getProcessCpuLoad() * 100));
		int maxMem = Math.round(Runtime.getRuntime().totalMemory() >> 20);
		int freeMem = Math.round(Runtime.getRuntime().freeMemory() >> 20);
		int usedMem = (maxMem - freeMem);
		String arch =	"" + osxb.getArch();
		String cpu =	"" + getBar(cpuLoad, 100) + cpuLoad + " %";
		String mem =	"" + getBar(usedMem, maxMem) + usedMem + " / " + maxMem + " MB";
		g.drawString("COMMOM DATA", POS_X, y);
		y += STEP;
		g.drawString("- System arch : ", POS_X, y);
		g.drawString(arch, POS_X2, y);
		y += STEP;
		g.drawString("- JVM CPU load : ", POS_X, y);
		g.drawString(cpu, POS_X2, y);
		y += STEP;
		g.drawString("- Memory usage : ", POS_X, y);
		g.drawString(mem, POS_X2, y);
		if (isLogging()) {
			System.out.println("COMMOM DATA");
			System.out.println("- System arch :\t\t" + arch);
			System.out.println("- JVM CPU load :\t" + cpu);
			System.out.println("- Memory usage :\t" + mem);
		}
		return y;
	}
	
	private int logGraphics(Graphics g, int y) {
		Engine engine = Engine.getInstance();
		int renderedPolys = engine.getLastRenderedFaces();
		int maxPolys = 0;
		synchronized (engine.getScene()) {
			List<SceneObject> sceneObjects = engine.getScene().getSceneObjects();
			for (int i = 0; i < sceneObjects.size(); i++) {
				maxPolys += sceneObjects.get(i).getMesh().getFaces().length;
			}
		}
		String fps =	"" + 1000/(engine.getLastGraphicsTime()+1);
		String rTime = 	"" + engine.getLastGraphicsTime() + " ms";
		String rRes =	"" + engine.getDisplayBuffer().getWidth() + " x " + engine.getDisplayBuffer().getHeight();
		String wRes =	"";
		if (engine.getSceneWindow() != null)
			wRes += engine.getSceneWindow().getWidth() + " x " + engine.getSceneWindow().getHeight();
		else wRes += "no SceneWindow";
		String rTris =	"" + getBar(renderedPolys, maxPolys) + renderedPolys + " / " + maxPolys;
		g.drawString("GRAPHICS DATA", POS_X, y);
		y += STEP;
		g.drawString("- FPS : ", POS_X, y);
		g.drawString(fps, POS_X2, y);
		y += STEP;
		g.drawString("- Render time : ", POS_X, y);
		g.drawString(rTime, POS_X2, y);
		y += STEP;
		g.drawString("- Rendering res. : ", POS_X, y);
		g.drawString(rRes, POS_X2, y);
		y += STEP;
		g.drawString("- Window size : ", POS_X, y);
		g.drawString(wRes, POS_X2, y);
		y += STEP;
		g.drawString("- Rendered Faces : ", POS_X, y);
		g.drawString(rTris, POS_X2, y);
		if (isLogging()) {
			System.out.println("GRAPHICS DATA");
			System.out.println("- FPS :\t\t\t" + fps);
			System.out.println("- Render time :\t\t" + rTime);
			System.out.println("- Rendering res. :\t" + rRes);
			System.out.println("- Window size : \t" + wRes);
			System.out.println("- Rendered Faces :\t" + rTris);
		}
		return y;
	}
	
	private int logInput(Graphics g, int y) {
		Engine engine = Engine.getInstance();
		String iTime =	"" + engine.getLastInputTime() + " ms";
		String key =	"";
		String mouse =	"";
		g.drawString("INPUT DATA", POS_X, y);
		y += STEP;
		g.drawString("- Input time : ", POS_X, y);
		g.drawString(iTime, POS_X2, y);
		y += STEP;
		g.drawString("- Pressed Keys : ", POS_X, y);
		g.drawString(key, POS_X2, y);
		y += STEP;
		g.drawString("- Mouse Clicks : ", POS_X, y);
		g.drawString(mouse, POS_X2, y);
		if (isLogging()) {
			System.out.println("INPUT DATA");
			System.out.println("- Input time :\t\t" + iTime);
			System.out.println("- Pressed Keys :\t" + key);
			System.out.println("- Mouse Clicks :\t" + mouse);
		}
		return y;
	}
	
	private int logPhysics(Graphics g, int y) {
		Engine engine = Engine.getInstance();
		String pTime =	"" + engine.getLastPhysicsTime() + " ms";
		g.drawString("PHYSICS DATA", POS_X, y);
		y += STEP;
		g.drawString("- Physics time : ", POS_X, y);
		g.drawString(pTime, POS_X2, y);
		if (isLogging()) {
			System.out.println("PHYSICS DATA");
			System.out.println("- Physics time :\t" + pTime);
		}
		return y;
	}
	
	private String getBar(int current, int max) {
		String result = "", bar = "|";
		int precision = 20, i = 0;
		if (max == 0) max = 1;
		current = (current * precision)/max;
		result += bar;
		for (; i < current; i++) result += bar;
		for (; i < precision; i++) result += " ";
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
			frame.setVisible(false);
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
	 * Returns if this profiler is logging.
	 * 
	 * @return if this profiler is logging.
	 */
	public boolean isLogging() {
		return log;
	}
	
	/**
	 * Tells this profiler to start logging to console.
	 */
	public void startLogging() {
		log = true;
	}
	
	/**
	 * Tells this profiler to stop logging to console.
	 */
	public void stopLogging() {
		log = false;
	}
	
	
	private class ProfilerPanel extends JPanel{
		
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backroundImage, 0, 0, null);
			g.setColor(Color.WHITE);
			int y = START_Y;
			y = logCommom(g, y);
			y += STEP + 5;
			y = logGraphics(g, y);
			y += STEP + 5;
			y = logInput(g, y);
			y += STEP + 5;
			y = logPhysics(g, y);
			if (isLogging()) System.out.println("-------------------------------------------------");
		}
	}
	
}
