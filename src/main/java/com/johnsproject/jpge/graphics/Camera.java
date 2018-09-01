package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.awt.image.DataBufferInt;

import com.johnsproject.jpge.graphics.SceneRenderer.ProjectionType;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.utils.Vector2Utils;

public class Camera extends Canvas{
	private String name;
	private int screenPosition;
	private int rect, halfRect;
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
	
	public Camera(String name, Transform transform, int screenPosition, int rect) {
		this.name = name;
		this.transform = transform;
		this.rect = rect;
		int px = Vector2Utils.getX(screenPosition), py = Vector2Utils.getY(screenPosition);
		int sx = Vector2Utils.getX(rect), sy = Vector2Utils.getY(rect);
		halfRect = Vector2Utils.setX(halfRect, sx/2);
		halfRect = Vector2Utils.setY(halfRect, sy/2);
		this.radio = (sx*10)/sy;
		this.rescaleFactor = Math.abs((sx+sy)>>7)+1;
		this.setSize(sx, sy);
		this.screenPosition = screenPosition;
		this.setLocation(px, py);
		screenBuffer = new BufferedImage(sx, sy, BufferedImage.TYPE_INT_ARGB_PRE);
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
		//if((x > 0 && y > 0) && (x < rect[0] && y < rect[1])) {
			screenBufferData[x + (y * Vector2Utils.getX(rect))] = color;
		//}
	}
	
	public Graphics getScreenGraphics() {
		return screenBuffer.getGraphics();
	}

	public int getRect() {
		return this.rect;
	}
	
	public int getHalfRect() {
		return this.halfRect;
	}
	
	public int getScreenPosition() {
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

	public void setRect(int rect) {
		this.rect = rect;
		int x = Vector2Utils.getX(rect);
		int y = Vector2Utils.getY(rect);
		setSize(x, y);
		halfRect = Vector2Utils.setX(halfRect, x/2);
		halfRect = Vector2Utils.setX(halfRect, y/2);
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
