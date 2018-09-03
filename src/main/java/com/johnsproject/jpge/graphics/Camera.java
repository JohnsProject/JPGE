package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import com.johnsproject.jpge.graphics.SceneRenderer.ProjectionType;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.utils.Vector2Utils;

/**
 * The Camera class is used to view a {@link Scene}.
 * The {@link SceneRenderer} will take all cameras of the {@link Scene} 
 * used by the {@link SceneFrame} and render what is in the camera view.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Camera extends Canvas{
	private static final long serialVersionUID = -6288232882538805324L;
	private String name;
	private int screenPosition;
	private int screenSize, halfscreenSize;
	private int FieldOfView = 90;
	private int nearClippingPlane = 50;
	private int farClippingPlane = 90000;
	private int scaleFactor;
	private Transform transform;
	private BufferedImage viewBuffer;
	private int[] viewBufferData;
	private boolean changed = false;
	private ProjectionType projectionType = ProjectionType.perspective;
	private RenderingType renderingType = RenderingType.textured;
	
	/**
	 * Creates a new instance of the Camera class filled with the given values.
	 * 
	 * @param name name of this camera.
	 * @param transform {@link Transform} of this camera.
	 * @param screenPosition the position of this camera at the {@link SceneFrame}.
	 * @param screenSize the size of this camera at the {@link SceneFrame}.
	 */
	public Camera(String name, Transform transform, int screenPosition, int screenSize) {
		this.name = name;
		this.transform = transform;
		this.screenSize = screenSize;
		int px = Vector2Utils.getX(screenPosition), py = Vector2Utils.getY(screenPosition);
		int sx = Vector2Utils.getX(screenSize), sy = Vector2Utils.getY(screenSize);
		halfscreenSize = Vector2Utils.setX(halfscreenSize, sx/2);
		halfscreenSize = Vector2Utils.setY(halfscreenSize, sy/2);
		this.scaleFactor = Math.abs((sx+sy)>>7)+1;
		this.setSize(sx, sy);
		this.screenPosition = screenPosition;
		this.setLocation(px, py);
		viewBuffer = new BufferedImage(sx, sy, BufferedImage.TYPE_INT_ARGB_PRE);
		viewBuffer.setAccelerationPriority(1);
		viewBufferData = ((DataBufferInt)viewBuffer.getRaster().getDataBuffer()).getData();
		this.changed = true;
	}
	
	/**
	 * Tells this camera to draw its view buffer on its {@link Graphics}.
	 */
	public void drawBuffer() {
		if(this.getBufferStrategy() == null) {
			this.createBufferStrategy(2);
		}
		BufferStrategy s = this.getBufferStrategy();
		Graphics g = s.getDrawGraphics();
		g.drawImage(viewBuffer, 0, 0, null);
		s.show();
	} 
	
	/**
	 * Sets the pixel of the view buffer of this camera at the given position with given colors.
	 * 
	 * @param x position of pixel at the x axis.
	 * @param y position of pixel at the y axis.
	 * @param color color of pixel to set.
	 */
	public void setPixel(int x, int y, int color){
		//if((x > 0 && y > 0) && (x < rect[0] && y < rect[1])) {
			viewBufferData[x + (y * Vector2Utils.getX(screenSize))] = color;
		//}
	}
	
	/**
	 * Returns the {@link Graphics} of the view buffer of this camera.
	 * 
	 * @return {@link Graphics} of the view buffer of this camera.
	 */
	public Graphics getViewGraphics() {
		return viewBuffer.getGraphics();
	}

	/**
	 * Returns the size of this camera at the {@link SceneFrame}.
	 * 
	 * @return size of this camera at the {@link SceneFrame}.
	 */
	public int getScreenSize() {
		return this.screenSize;
	}
	
	/**
	 * Returns the half size of this camera at the {@link SceneFrame}.
	 * This is used by the {@link SceneRenderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return half size of this camera at the {@link SceneFrame}.
	 */
	int getHalfScreenSize() {
		return this.halfscreenSize;
	}
	
	/**
	 * Returns the position of this camera at the {@link SceneFrame}.
	 * 
	 * @return position of this camera at the {@link SceneFrame}.
	 */
	public int getScreenPosition() {
		return this.screenPosition;
	}
	
	/**
	 * Returns the scale factor of this camera.
	 * This is used by the {@link SceneRenderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return scale factor of this camera.
	 */
	int getScaleFactor() {
		return this.scaleFactor;
	}
	
	/**
	 * Returns the projection type of this camera.
	 * 
	 * @return projection type of this camera.
	 */
	public ProjectionType getProjectionType() {
		return projectionType;
	}

	/**
	 * Sets the projection type of this camera.
	 * 
	 * @param projectionType projection type to set.
	 */
	public void setProjectionType(ProjectionType projectionType) {
		this.projectionType = projectionType;
	}
	
	/**
	 * Returns the rendering type of this camera.
	 * 
	 * @return rendering type of this camera.
	 */
	public RenderingType getRenderingType() {
		return renderingType;
	}

	/**
	 * Sets the rendering type of this camera.
	 * 
	 * @param renderingType rendering type to set.
	 */
	public void setRenderingType(RenderingType renderingType) {
		this.renderingType = renderingType;
	}

	/**
	 * Sets the screen size of this camera.
	 * 
	 * @param size screen size to set.
	 */
	public void setScreenSize(int size) {
		this.screenSize = size;
		int x = Vector2Utils.getX(size);
		int y = Vector2Utils.getY(size);
		setSize(x, y);
		halfscreenSize = Vector2Utils.setX(halfscreenSize, x/2);
		halfscreenSize = Vector2Utils.setX(halfscreenSize, y/2);
		changed = true;
	}

	/**
	 * Returns the field of view of this camera.
	 * 
	 * @return field of view of this camera.
	 */
	public int getFieldOfView() {
		return FieldOfView;
	}

	/**
	 * Sets the field of view of this camera.
	 * 
	 * @param fieldOfView field of view to set.
	 */
	public void setFieldOfView(int fieldOfView) {
		FieldOfView = fieldOfView;
		changed = true;
	}

	/**
	 * Returns the near clipping plane of this camera.
	 * 
	 * @return near clipping plane of this camera.
	 */
	public int getNearClippingPlane() {
		return nearClippingPlane;
	}

	/**
	 * Sets the near clipping plane of this camera.
	 * 
	 * @param nearClippingPlane near clipping plane to set.
	 */
	public void setNearClippingPlane(int nearClippingPlane) {
		this.nearClippingPlane = nearClippingPlane;
		changed = true;
	}

	/**
	 * Returns the far clipping plane of this camera.
	 * 
	 * @return far clipping plane of this camera.
	 */
	public int getFarClippingPlane() {
		return farClippingPlane;
	}

	/**
	 * Sets the far clipping plane of this camera.
	 * 
	 * @param farClippingPlane far clipping plane to set.
	 */
	public void setFarClippingPlane(int farClippingPlane) {
		this.farClippingPlane = farClippingPlane;
		changed = true;
	}

	/**
	 * Returns the {@link Transform} of this camera.
	 * 
	 * @return {@link Transform} of this camera.
	 */
	public Transform getTransform() {
		changed = true;
		return transform;
	}

	/**
	 * Returns the name of this camera.
	 * 
	 * @return name of this camera.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the if this camera has changed since last frame.
	 * 
	 * @return if this camera has changed since last frame.
	 */
	boolean changed() {
		return changed;
	}
	
	/**
	 * Sets that this camera has changed since last frame.
	 * 
	 * @param changed if this camera has changed since last frame.
	 */
	void changed(boolean changed) {
		this.changed = changed;
	}

	@Override
	public String toString() {
		return "Camera [name=" + name + ", screenPosition=" + screenPosition + ", screenSize=" + screenSize
				+ ", halfscreenSize=" + halfscreenSize + ", FieldOfView=" + FieldOfView + ", nearClippingPlane="
				+ nearClippingPlane + ", farClippingPlane=" + farClippingPlane + ", scaleFactor=" + scaleFactor
				+ ", transform=" + transform + ", viewBuffer=" + viewBuffer + ", viewBufferData="
				+ Arrays.toString(viewBufferData) + ", changed=" + changed + ", projectionType=" + projectionType
				+ ", renderingType=" + renderingType + "]";
	}
}
