package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.awt.image.DataBufferInt;

import com.johnsproject.jpge.graphics.SceneRenderer.ProjectionType;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.utils.Vector3Utils;

public class Camera extends Canvas{
	private String name;
	private int[] screenPosition;
	private int[] rect, halfRect = new int[2];
	private int FieldOfView = 90;
	private int nearClippingPlane = 50;
	private int farClippingPlane = 9000;
	private int radio;
	private int rescaleFactor;
	private Transform transform;
	private BufferedImage screenBuffer;
	private int[] screenBufferData;
	private boolean changed = false;
	private ProjectionType projectionType = ProjectionType.perspective;
	private RenderingType renderingType = RenderingType.textured;
	
	public Camera(String name, Transform transform, int[] screenPosition, int[] rect) {
		this.name = name;
		this.transform = transform;
		this.rect = rect;
		this.halfRect[Vector3Utils.X] = rect[Vector3Utils.X] / 2;
		this.halfRect[Vector3Utils.Y] = rect[Vector3Utils.Y] / 2;
		this.radio = (rect[Vector3Utils.X]*10)/rect[Vector3Utils.Y];
		this.rescaleFactor = Math.abs((rect[Vector3Utils.X]+rect[Vector3Utils.Y])>>7)+1;
		this.setSize(rect[0], rect[1]);
		this.screenPosition = screenPosition;
		this.setLocation(screenPosition[0], screenPosition[1]);
		screenBuffer = new BufferedImage(rect[0], rect[1], BufferedImage.TYPE_INT_ARGB_PRE);
		screenBuffer.setAccelerationPriority(1);
		screenBufferData = ((DataBufferInt)screenBuffer.getRaster().getDataBuffer()).getData();
		this.changed = true;
	}
	
	public void drawBuffer() {
		if(this.getBufferStrategy() == null) {
			this.createBufferStrategy(2);
		}
		BufferStrategy s = this.getBufferStrategy();
		Graphics g = s.getDrawGraphics();
		g.drawImage(screenBuffer, 0, 0, null);
		s.show();
	} 
	
	public void setPixel(int x, int y, int color){
		if((x > 0 && y > 0) && (x < rect[0] && y < rect[1])) {
			screenBufferData[x + (y * rect[0])] = color;
		}
	}
	
	public Graphics getScreenGraphics() {
		return screenBuffer.getGraphics();
	}

	public int[] getRect() {
		return this.rect;
	}
	
	public int[] getHalfRect() {
		return this.halfRect;
	}
	
	public int[] getScreenPosition() {
		return this.screenPosition;
	}
	
	public int getRadio() {
		return this.radio;
	}
	
	public int getRescaleFactor() {
		return this.rescaleFactor;
	}
	
	public ProjectionType getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(ProjectionType projectionType) {
		this.projectionType = projectionType;
	}

	public RenderingType getRenderingType() {
		return renderingType;
	}

	public void setRenderingType(RenderingType renderingType) {
		this.renderingType = renderingType;
	}

	public void setRect(int[] rect) {
		this.rect = rect;
		setSize(rect[0], rect[1]);
		this.halfRect[Vector3Utils.X] = rect[Vector3Utils.X] / 2;
		this.halfRect[Vector3Utils.Y] = rect[Vector3Utils.Y] / 2;
		changed = true;
	}

	public int getFieldOfView() {
		return FieldOfView;
	}

	public void setFieldOfView(int fieldOfView) {
		FieldOfView = fieldOfView;
		changed = true;
	}

	public int getNearClippingPlane() {
		return nearClippingPlane;
	}

	public void setNearClippingPlane(int nearClippingPlane) {
		this.nearClippingPlane = nearClippingPlane;
		changed = true;
	}

	public int getFarClippingPlane() {
		return farClippingPlane;
	}

	public void setFarClippingPlane(int farClippingPlane) {
		this.farClippingPlane = farClippingPlane;
		changed = true;
	}

	public Transform getTransform() {
		changed = true;
		return transform;
	}
	
	public String getName() {
		return name;
	}
	
	boolean changed() {
		return changed;
	}
	
	void changed(boolean changed) {
		this.changed = changed;
	}
}
