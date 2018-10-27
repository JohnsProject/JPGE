package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.RenderUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Shader class is used to used shade the vertexes and the polygons that are
 * being rendered by the {@link SceneRenderer}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Shader {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
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
	private int[] vectorCache = new int[3];
	
	/**
	 * This method is called by the {@link SceneRenderer} at the rendering process.
	 * 
	 * @param vertex          	{@link Vertex} to shade.
	 * @param mesh   			{@link Mesh} this face belongs to.
	 * @param camera 			{@link Camera} being rendered to.
	 * @param objectTransform 	{@link Transform} of the {@link SceneObject} this vertex belongs to.
	 * @param lights All 		{@link Light Lights} of the {@link Scene} being rendered.
	 * @return shaded vertex.
	 */
	public Vertex shadeVertex(Vertex vertex, Mesh mesh, Camera camera, Transform objectTransform, List<Light> lights) {
		Transform objt = objectTransform;
		Transform camt = camera.getTransform();
		int[] pos = vertex.getPosition();
		int[] normal = vertex.getNormal();
		// transform vertex in object space
		pos = Vector3MathUtils.movePointByScale(pos, objt.getScale(), pos);
		pos = Vector3MathUtils.movePointByAnglesXYZ(pos, objt.getRotation(), pos);
		// transform vertex to world space
		pos = Vector3MathUtils.add(pos, objt.getPosition(), pos);
		// transform vertex to camera space
		pos = Vector3MathUtils.subtract(pos, camt.getPosition(), pos);
		pos = Vector3MathUtils.movePointByAnglesXYZ(pos, camt.getRotation(), pos);
		// transform / project vertex to screen space
		if(projectionType == PROJECT_ORTHOGRAPHIC) {
			pos = RenderUtils.orthographicProject(pos, camera);
		}
		if(projectionType == PROJECT_PERSPECTIVE) {
			pos = RenderUtils.perspectiveProject(pos, camera);
		}
		// transform normal in object space
		normal = Vector3MathUtils.movePointByAnglesXYZ(normal, objt.getRotation(), normal);
		if(shadingType == SHADE_GOURAUD) {
			// calculate shaded color for every vertex
			vertex.setColor(shade(lights, objt.getPosition(), normal));
		}
		return vertex;
	}

	/**
	 * This method is called by the {@link SceneRenderer} at the rendering process.
	 * 
	 * @param face   			face to shade.
	 * @param mesh   			{@link Mesh} this face belongs to.
	 * @param camera 			{@link Camera} being rendered to.
	 * @param zBuffer 			depth buffer used at the drawing process.
	 * @param objectTransform 	{@link Transform} of the {@link SceneObject} this face belongs to.
	 * @param lights All 		{@link Light Lights} of the {@link Scene} being rendered.
	 * @return shaded polygon.
	 */
	public Face shadeFace(Face face, Mesh mesh, Camera camera, int[] zBuffer, Transform objectTransform, List<Light> lights) {
		// view frustum culling
		if (!RenderUtils.isInsideViewFrustum(face, mesh, camera)) {
			// get vertexes
			Vertex vt1 = mesh.getBufferedVertex(face.getVertex1());
			Vertex vt2 = mesh.getBufferedVertex(face.getVertex2());
			Vertex vt3 = mesh.getBufferedVertex(face.getVertex3());
			if (!RenderUtils.isBackface(face, mesh)) {
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
				int shadedColor = ColorUtils.lerpRBG(color, vt1.getColor(), -255);
				// draw based on the cameras rendering type
				switch (drawingType) {
				case DRAW_VERTEX:
					drawVertex(vt1, vt2, vt3, shadedColor, zBuffer, camera);
					break;

				case DRAW_WIREFRAME:
					drawWireframe(vt1, vt2, vt3, shadedColor, zBuffer, camera);
					break;

				case DRAW_FLAT:
					RenderUtils.drawFaceGouraud(face, mesh, zBuffer, camera);
					break;

				case DRAW_TEXTURED:
					RenderUtils.drawFaceAffine(face, mesh, zBuffer, camera);
					break;
				}
			}
		}
		return face;
	}
	
	private int shade(List<Light> lights, int[] objectPos, int[] normal) {
		int factor = 0;
		int lightColor = 0;
		for (int i = 0; i < lights.size(); i++) {
			Light light = lights.get(i);
			int[] lightPosition = light.getTransform().getPosition();
			switch (light.getType()) {
			case sun:
				factor += Vector3MathUtils.dotProduct(lightPosition, normal);
				factor /= light.getStrength();
				break;
			case point:
				vectorCache = Vector3MathUtils.distance(objectPos, lightPosition, vectorCache);
				factor += Vector3MathUtils.dotProduct(vectorCache, normal);
				factor /= light.getStrength();
				break;
			}
			lightColor = ColorUtils.lerpRBG(light.getColor(), lightColor, -100);
		}
		// return color
		return ColorUtils.lerpRBG(lightColor, 0, -factor);
	}
	
	private void drawVertex(Vertex vt1, Vertex vt2, Vertex vt3, int color, int[] zBuffer, Camera camera) {
		// get position of vertexes
		int[] vp1 = vt1.getPosition();
		int[] vp2 = vt2.getPosition();
		int[] vp3 = vt3.getPosition();
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
	    // color used if rendering type is wireframe or vertex
	    int shadedColor = ColorUtils.lerpRBG(color,  vt1.getColor(), -255);
		camera.setPixel(x1, y1, z1, shadedColor, zBuffer);
    	camera.setPixel(x2, y2, z2, shadedColor, zBuffer);
    	camera.setPixel(x3, y3, z3, shadedColor, zBuffer);
	}
	
	private void drawWireframe(Vertex vt1, Vertex vt2, Vertex vt3, int color, int[] zBuffer, Camera camera) {
		// get position of vertexes
		int[] vp1 = vt1.getPosition();
		int[] vp2 = vt2.getPosition();
		int[] vp3 = vt3.getPosition();
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
	    // color used if rendering type is wireframe or vertex
	    int shadedColor = ColorUtils.lerpRBG(color,  vt1.getColor(), -255);
	    RenderUtils.drawLine(x1, y1, x2, y2, z1, shadedColor, zBuffer, camera);
		RenderUtils.drawLine(x2, y2, x3, y3, z2, shadedColor, zBuffer, camera);
		RenderUtils.drawLine(x3, y3, x1, y1, z3, shadedColor, zBuffer, camera);
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
