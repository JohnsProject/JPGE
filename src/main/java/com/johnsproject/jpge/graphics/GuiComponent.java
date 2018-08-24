package com.johnsproject.jpge.graphics;

import javax.swing.JComponent;

public class GuiComponent {
	
	private String name;
	private JComponent component;
	private int[] position;
	private int[] scale;
	
	public GuiComponent(String name, JComponent component, int[] position, int[] scale){
		this.name = name;
		this.component = component;
		this.position = position;
		this.scale = scale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public int[] getScale() {
		return scale;
	}

	public void setScale(int[] scale) {
		this.scale = scale;
	}

	public GuiComponent clone() {
		return new GuiComponent(name, component, new int[position.length], new int[scale.length]);
	}
	
}
