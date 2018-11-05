package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.DisplayBuffer;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Light;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.Scene;
import com.johnsproject.jpge.dto.SceneObject;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.dto.Vertex;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Shader class is used to used shade the vertexes and the polygons that are
 * being rendered by the {@link Renderer}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Shader {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	public static final int LIGHT_DIRECTIONAL = 0;
	public static final int PROJECT_ORTHOGRAPHIC = 0;
	public static final int PROJECT_PERSPECTIVE = 1;
	public static final int SHADE_FLAT = 0;
	public static final int SHADE_GOURAUD = 1;
	public static final int DRAW_VERTEX = 0;
	public static final int DRAW_WIREFRAME = 1;
	public static final int DRAW_FLAT = 2;
	public static final int DRAW_TEXTURED = 3;
	
	private int projectionType = PROJECT_PERSPECTIVE;
	private int shadingType = SHADE_GOURAUD;
	private int drawingType = DRAW_TEXTURED;
	
	/**
	 * This method is called by the {@link Renderer} at the rendering process.
	 * 
	 * @param vertex          	{@link Vertex} to shade.
	 * @param mesh   			{@link Mesh} this face belongs to.
	 * @param objectTransform 	{@link Transform} of the {@link SceneObject} this vertex belongs to.
	 * @param lights All 		{@link Light Lights} of the {@link Scene} being rendered.
	 * @param camera 			{@link Camera} being rendered to.
	 * @return shaded vertex.
	 */
	public Vertex shadeVertex(Vertex vertex, Mesh mesh, Transform objectTransform, List<Light> lights, Camera camera) {
		Transform objt = objectTransform;
		Transform camt = camera.getTransform();
		int[] vector = vertex.getPosition();
		int[] normal = vertex.getNormal();
		// transform vertex in object space
		vector = Vector3MathUtils.movePointByScale(vector, objt.getScale(), vector);
		vector = Vector3MathUtils.movePointByAnglesXYZ(vector, objt.getRotation(), vector);
		// transform normal in object space
		normal = Vector3MathUtils.movePointByAnglesXYZ(normal, objt.getRotation(), normal);
		if(shadingType == SHADE_GOURAUD) {
			// calculate shaded color for every vertex
			vertex.setColor(shade(lights, vector, normal));
		}
		// transform vertex to world space
		vector = Vector3MathUtils.add(vector, objt.getPosition(), vector);
		// transform vertex to camera space
		vector = Vector3MathUtils.subtract(vector, camt.getPosition(), vector);
		vector = Vector3MathUtils.movePointByAnglesXYZ(vector, camt.getRotation(), vector);
		// transform / project vertex to screen space
		if(projectionType == PROJECT_ORTHOGRAPHIC) {
			vector = RenderUtils.orthographicProject(vector, camera);
		}
		if(projectionType == PROJECT_PERSPECTIVE) {
			vector = RenderUtils.perspectiveProject(vector, camera);
		}
		return vertex;
	}

	/**
	 * This method is called by the {@link Renderer} at the rendering process.
	 * 
	 * @param face   			face to shade.
	 * @param mesh   			{@link Mesh} this face belongs to.
	 * @param objectTransform 	{@link Transform} of the {@link SceneObject} this face belongs to.
	 * @param lights All 		{@link Light Lights} of the {@link Scene} being rendered.
	 * @param camera 			{@link Camera} being rendered to.
	 * @param displayBuffer 	{@link DisplayBuffer} to use.
	 * @return shaded polygon.
	 */
	public Face shadeFace(Face face, Mesh mesh, Transform objectTransform, List<Light> lights, Camera camera, DisplayBuffer displayBuffer) {
		// view frustum culling
		if (!RenderUtils.isInsideViewFrustum(face, mesh, camera)) {
			if (!RenderUtils.isBackface(face, mesh)) {
				// get vertexes
				Vertex vt1 = mesh.getBufferedVertex(face.getVertex1());
				Vertex vt2 = mesh.getBufferedVertex(face.getVertex2());
				Vertex vt3 = mesh.getBufferedVertex(face.getVertex3());
				if (shadingType == SHADE_FLAT) {
					// calculate shaded color for 1 vertex
					int result = shade(lights, objectTransform.getPosition(), vt1.getNormal());
					// and apply that color to all vertexes
					vt1.setColor(result);
					vt2.setColor(result);
					vt3.setColor(result);
				}
				// draw face
				int color = mesh.getMaterial(face.getMaterial()).getColor();
				// color used if rendering type is wireframe or vertex
				int shadedColor = ColorUtils.lerpRBG(vt1.getColor(), color, 500);
				// draw based on the cameras rendering type
				switch (drawingType) {
				case DRAW_VERTEX:
					drawVertex(vt1, vt2, vt3, shadedColor, camera, displayBuffer);
					break;

				case DRAW_WIREFRAME:
					drawWireframe(vt1, vt2, vt3, shadedColor, camera, displayBuffer);
					break;

				case DRAW_FLAT:
					RenderUtils.drawFaceGouraud(face, mesh, this, camera, displayBuffer);
					break;

				case DRAW_TEXTURED:
					RenderUtils.drawFaceAffine(face, mesh, this, camera, displayBuffer);
					break;
				}
			}
		}
		return face;
	}
	
	/**
	 * This method is called by the {@link Renderer} at the rendering process.
	 * 
	 * @param x position of pixel in the x axis.
	 * @param y position of pixel in the y axis.
	 * @param z position of pixel in the z axis.
	 * @param color color of pixel.
	 * @param camera {@link Camera} to set pixel.
	 * @param displayBuffer {@link DisplayBuffer} to use.
	 */
	public void shadePixel (int x, int y, int z, int color, Camera camera, DisplayBuffer displayBuffer) {
		// check if pixel is inside camera
		if (x > 0 && x < camera.getWidth() && y > 0 && y < camera.getHeight()) {
			// calculate 1D array position of 2D xy position
			int pos = (camera.getPositionX() + x) + ((camera.getPositionY() + y) * displayBuffer.getWidth());
			int[] depthBuffer = displayBuffer.getDepthBuffer();
			// check if pixel is inside displayBuffer
			if (pos < depthBuffer.length) {
				// z test
				if (depthBuffer[pos] > z) {
					depthBuffer[pos] = z;
					displayBuffer.getFrameBufferData()[pos] = color;
				}
			}
		}
	}
	
	private int shade(List<Light> lights, int[] vector, int[] normal) {
		int factor = 0;
		int lightColor = 0;
		for (int i = 0; i < lights.size(); i++) {
			Light light = lights.get(i);
			int[] lightPosition = light.getTransform().getPosition();
			switch (light.getType()) {
			case LIGHT_DIRECTIONAL:
				factor += Vector3MathUtils.dotProduct(lightPosition, normal);
				factor /= light.getStrength();
				break;
			}
			lightColor = ColorUtils.lerpRBG(light.getColor(), lightColor, 100);
		}
		// return color
		return ColorUtils.lerpRBG(ColorUtils.convert(255, 255, 255), 0, -factor-50);
	}
	
	private void drawVertex(Vertex vt1, Vertex vt2, Vertex vt3, int color, Camera camera, DisplayBuffer displayBuffer) {
		// get position of vertexes
		int[] vp1 = vt1.getPosition();
		int[] vp2 = vt2.getPosition();
		int[] vp3 = vt3.getPosition();
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
	    // color used if rendering type is wireframe or vertex
	    int shadedColor = ColorUtils.lerpRBG(color,  vt1.getColor(), -255);
	    shadePixel(x1, y1, z1, shadedColor, camera, displayBuffer);
	    shadePixel(x2, y2, z2, shadedColor, camera, displayBuffer);
	    shadePixel(x3, y3, z3, shadedColor, camera, displayBuffer);
	}
	
	private void drawWireframe(Vertex vt1, Vertex vt2, Vertex vt3, int color, Camera camera, DisplayBuffer displayBuffer) {
		// get position of vertexes
		int[] vp1 = vt1.getPosition();
		int[] vp2 = vt2.getPosition();
		int[] vp3 = vt3.getPosition();
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
	    // color used if rendering type is wireframe or vertex
	    int shadedColor = ColorUtils.lerpRBG(color,  vt1.getColor(), -255);
	    RenderUtils.drawLine(x1, y1, x2, y2, z1, shadedColor, this, camera, displayBuffer);
		RenderUtils.drawLine(x2, y2, x3, y3, z2, shadedColor, this, camera, displayBuffer);
		RenderUtils.drawLine(x3, y3, x1, y1, z3, shadedColor, this, camera, displayBuffer);
	}
	
	/**
	 * Returns the projection type use by this Shader.
	 * 
	 * @return projection type use by this Shader.
	 */
	public int getProjectionType() {
		return projectionType;
	}

	/**
	 * Stets the projection type of this shader.
	 * 
	 * @param projectionType projection type to set.
	 */
	public void setProjectionType(int projectionType) {
		this.projectionType = projectionType;
	}

	/**
	 * Returns the shading type use by this Shader.
	 * 
	 * @return shading type use by this Shader.
	 */
	public int getShadingType() {
		return shadingType;
	}
	
	/**
	 * Stets the shading type of this shader.
	 * 
	 * @param shadingType shading type to set.
	 */
	public void setShadingType(int shadingType) {
		this.shadingType = shadingType;
	}

	/**
	 * Returns the drawing type use by this Shader.
	 * 
	 * @return drawing type use by this Shader.
	 */
	public int getDrawingType() {
		return drawingType;
	}

	/**
	 * Stets the drawing type of this shader.
	 * 
	 * @param drawingType drawing type to set.
	 */
	public void setDrawingType(int drawingType) {
		this.drawingType = drawingType;
	}

}
