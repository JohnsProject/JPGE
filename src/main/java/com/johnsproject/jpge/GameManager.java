package com.johnsproject.jpge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.johnsproject.jpge.event.EventDispatcher;
import com.johnsproject.jpge.event.UpdateEvent;
import com.johnsproject.jpge.event.UpdateEvent.UpdateType;

public class GameManager {
	
	private static GameManager instance;
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	
	private int targetFPS = 30;
	private int inputUpdateRate = 30;
	private int physicsUpdateRate = 30;
	
	private Timer graphicsTimer;
	private Timer inputTimer;
	private Timer physicsTimer;
	
	public GameManager() {
		updateGraphics();
		updateInput();
		updatePhysics();
	}
	
	public void play() {
		graphicsTimer.start();
		inputTimer.start();
		physicsTimer.start();
	}
	
	public void pause() {
		graphicsTimer.stop();
		inputTimer.stop();
		physicsTimer.stop();
	}
	
	void updateGraphics() {
		graphicsTimer = new Timer(0, null);
		graphicsTimer.setInitialDelay((1000/targetFPS));
		graphicsTimer.addActionListener(new ActionListener() {
			int delay = (1000/targetFPS);
			int lastElapsed = 0;
	        public void actionPerformed(ActionEvent evt) {
	        	int before = (int)System.nanoTime();
	        	EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UpdateType.graphics));
	        	//graphicsTimer.setInitialDelay(Math.abs(((int)lastElapsed>>1) / delay)>>48);
	        	lastElapsed = (int)System.nanoTime() - before;
	        	graphicsTimer.restart();
	        }
	    });
		graphicsTimer.start();
	}
	
	void updateInput() {
		inputTimer = new Timer(1000/inputUpdateRate, null);
		inputTimer.addActionListener(new ActionListener() {
			int lastElapsed = 0;
	        public void actionPerformed(ActionEvent evt) {
	        	int before = (int)System.nanoTime();
	        	EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UpdateType.input));
	        	lastElapsed = (int)System.nanoTime() - before;
	        	inputTimer.restart();
	        }
	    });
		inputTimer.start();
	}
	
	void updatePhysics() {
		physicsTimer = new Timer(1000/physicsUpdateRate, null);
		physicsTimer.addActionListener(new ActionListener() {
			int lastElapsed = 0;
	        public void actionPerformed(ActionEvent evt) {
	        	int before = (int)System.nanoTime();
	        	EventDispatcher.getInstance().dispatchUpdateEvent(new UpdateEvent(lastElapsed, UpdateType.physics));
	        	lastElapsed = (int)System.nanoTime() - before;
	        	physicsTimer.restart();
	        }
	    });
		physicsTimer.start();
	}
}
