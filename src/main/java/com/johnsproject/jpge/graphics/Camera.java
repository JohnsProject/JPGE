package com.johnsproject.jpge.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

import com.johnsproject.jpge.graphics.SceneRenderer.ProjectionType;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Camera class is used to view a {@link Scene}.
 * The {@link SceneRenderer} will take all cameras of the {@link Scene} 
 * used by the {@link SceneFrame} and render what is in the camera view.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Camera extends JPanel{
	
	private static final long serialVersionUID = -6288232882538805324L;
	private String name;
	private int[] screenPosition;
	private int width = 0;
	private int height = 0;
	private int halfWidth = 0;
	private int halfHeight = 0;
	private int FieldOfView = 60;
	private int nearClippingPlane = 300;
	private int farClippingPlane = 50000;
	private int scaleFactor;
	private Transform transform;
	private BufferedImage viewBuffer;
	private int[] viewBufferData;
	private boolean changed = false;
	private ProjectionType projectionType = ProjectionType.perspective;
	private RenderingType renderingType = RenderingType.wireframe;
	private PixelShader shader;
	
	/**
	 * Creates a new instance of the Camera class filled with the given values.
	 * 
	 * @param name name of this camera.
	 * @param transform {@link Transform} of this camera.
	 * @param screenPosition the position of this camera at the {@link SceneFrame}.
	 * @param screenSize the size of this camera at the {@link SceneFrame}.
	 */
	public Camera(String name, Transform transform, int x, int y, int width, int height) {
		this.name = name;
		this.transform = transform;
		this.width = width;
		this.height = height;
		this.halfWidth = width >> 1;
		this.halfHeight = height >> 1;
		this.scaleFactor = ((width + height) >> 7) + 1;
		this.screenPosition = new int[] {x, y};
		this.setSize(width, height);
		this.setLocation(x, y);
		this.viewBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		this.viewBufferData = ((DataBufferInt)viewBuffer.getRaster().getDataBuffer()).getData();
		this.changed = true;
		this.shader = new PixelShader();
	}	
	
	public void drawBuffer() {
		repaint();
		//viewBuffer.getGraphics().clearRect(0, 0, screenSize[vx], screenSize[vy]);
	}
	
	public void clearBuffer() {
		//for (int i = 0; i < viewBufferData.length; i++) viewBufferData[i] = testC;
		//viewBuffer.getGraphics().clearRect(0, 0, screenSize[vx], screenSize[vy]);
		viewBuffer.getGraphics().fillRect(0, 0, width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(viewBuffer, 0, 0, null);
		clearBuffer();
	}

	/**
	 * Sets the pixel of the view buffer of this camera at the given position with given colors.
	 * This method tells the camera to call the shadePixel method of this cameras shader.
	 * 
	 * @param x position of pixel at the x axis.
	 * @param y position of pixel at the y axis.
	 * @param z position of pixel at the z axis.
	 * @param color color of pixel to set.
	 * @param zBuffer buffer containing depth of pixels of this camera.
	 */
	public void setPixel(int x, int y, int z, int color, int[] zBuffer){
		shader.shadePixel(x, y, z, color,  width, height, viewBufferData, zBuffer);
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
	 * Returns the width of this camera in screen.
	 * 
	 * @return width of this camera in screen.
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the height of this camera in screen.
	 * 
	 * @return height of this camera in screen.
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Returns the half width of this camera in screen.
	 * This is used by the {@link SceneRenderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return half width of this camera in screen.
	 */
	public int getHalfWidth() {
		return this.halfWidth;
	}
	
	/**
	 * Returns the half height of this camera in screen.
	 * This is used by the {@link SceneRenderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return half height of this camera in screen.
	 */
	public int getHalfHeight() {
		return this.halfHeight;
	}
	
	/**
	 * Returns the position of this camera at the {@link SceneFrame}.
	 * 
	 * @return position of this camera at the {@link SceneFrame}.
	 */
	public int[] getScreenPosition() {
		return this.screenPosition;
	}
	
	/**
	 * Returns the scale factor of this camera.
	 * This is used by the {@link SceneRenderer} at the rendering process 
	 * and is precalculated here for performance reasons.
	 * 
	 * @return scale factor of this camera.
	 */
	public int getScaleFactor() {
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
	public void setScreenSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.halfWidth = width >> 1;
		this.halfHeight = height >> 1;
		setSize(width, height);
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
	
	/**
	 * Returns the {@link PixelShader} used by this camera.
	 * 
	 * @return {@link PixelShader} used by this camera.
	 */
	public PixelShader getShader() {
		return shader;
	}

	/**
	 * Sets the {@link PixelShader} of this camera.
	 * 
	 * @param shader {@link PixelShader} to set.
	 */
	public void setShader(PixelShader shader) {
		this.shader = shader;
	}
}
