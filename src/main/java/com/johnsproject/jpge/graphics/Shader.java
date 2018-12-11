/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge.graphics;

import java.util.List;

import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.RenderBuffer;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Light;
import com.johnsproject.jpge.dto.Mesh;
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
 * @author John´s Project - John Salomon
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
	
	public int shade(Mesh mesh, Transform objectTransform, Camera camera, RenderBuffer renderBuffer, List<Light> lights) {
		// reset mesh buffer
		mesh.resetBuffer();
		// animate and shade vertexes
		for (int i = 0; i < mesh.getBufferedVertexes().length; i++) {
			Vertex vertex = mesh.getBufferedVertex(i);
			vertex = shadeVertex(vertex, mesh, objectTransform, lights, camera);
		}
		int rendFaces = 0;
		// shade faces
		for (int i = 0; i < mesh.getFaces().length; i++) {
			Face face = mesh.getFace(i);
			face = shadeFace(face, mesh, objectTransform, lights, camera, renderBuffer);
			if (!face.isCulled())
				rendFaces++;
		}
		return rendFaces;
	}
	
	private Vertex shadeVertex(Vertex vertex, Mesh mesh, Transform objectTransform, List<Light> lights, Camera camera) {
		Transform objt = objectTransform;
		Transform camt = camera.getTransform();
		int[] vector = vertex.getLocation();
		int[] normal = vertex.getNormal();
		// transform normal in object space
		normal = Vector3MathUtils.movePointByAnglesXYZ(normal, objt.getRotation(), normal);
		// transform vertex in object space
		vector = Vector3MathUtils.movePointByScale(vector, objt.getScale(), vector);
		vector = Vector3MathUtils.movePointByAnglesXYZ(vector, objt.getRotation(), vector);
		if (shadingType == SHADE_GOURAUD) {
			// calculate shaded color for every vertex
			vertex.setColor(shade(lights, vector, normal));
		}
		// transform vertex to world space
		vector = Vector3MathUtils.add(vector, objt.getLocation(), vector);
		// transform vertex to camera space
		vector = Vector3MathUtils.subtract(vector, camt.getLocation(), vector);
		vector = Vector3MathUtils.movePointByAnglesXYZ(vector, camt.getRotation(), vector);
		// transform / project vertex to screen space
		if (projectionType == PROJECT_ORTHOGRAPHIC) {
			vector = RenderUtils.orthographicProject(vector, camera);
		}
		if (projectionType == PROJECT_PERSPECTIVE) {
			vector = RenderUtils.perspectiveProject(vector, camera);
		}
		return vertex;
	}

	
	private Face shadeFace(Face face, Mesh mesh, Transform objectTransform, List<Light> lights, Camera camera, RenderBuffer renderBuffer) {
		// view frustum culling
		if (!RenderUtils.isInsideViewFrustum(face, mesh, camera)) {
			if (!RenderUtils.isBackface(face, mesh)) {
				// get vertexes
				Vertex vt1 = mesh.getBufferedVertex(face.getVertex1());
				Vertex vt2 = mesh.getBufferedVertex(face.getVertex2());
				Vertex vt3 = mesh.getBufferedVertex(face.getVertex3());
				if (shadingType == SHADE_FLAT) {
					// calculate shaded color for 1 vertex
					int result = shade(lights, objectTransform.getLocation(), vt1.getNormal());
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
					drawVertex(vt1, vt2, vt3, shadedColor, camera, renderBuffer);
					break;

				case DRAW_WIREFRAME:
					drawWireframe(vt1, vt2, vt3, shadedColor, camera, renderBuffer);
					break;

				case DRAW_FLAT:
					RenderUtils.drawFaceGouraud(face, mesh, camera, renderBuffer);
					break;

				case DRAW_TEXTURED:
					RenderUtils.drawFaceAffine(face, mesh, camera, renderBuffer);
					break;
				}
			}
		}
		return face;
	}
	
	private int shade(List<Light> lights, int[] vector, int[] normal) {
		int factor = 0;
		int lightColor = 0;
		for (int i = 0; i < lights.size(); i++) {
			Light light = lights.get(i);
			int[] lightLocation = light.getTransform().getLocation();
			switch (light.getType()) {
			case LIGHT_DIRECTIONAL:
				factor += Vector3MathUtils.dotProduct(lightLocation, normal);
				factor /= light.getStrength();
				break;
			}
			lightColor = ColorUtils.lerpRBG(light.getColor(), lightColor, 100);
		}
		// return color
		return ColorUtils.lerpRBG(ColorUtils.convert(255, 255, 255), 0, -factor-50);
	}
	
	private void drawVertex(Vertex vt1, Vertex vt2, Vertex vt3, int color, Camera camera, RenderBuffer RenderBuffer) {
		// get location of vertexes
		int[] vp1 = vt1.getLocation();
		int[] vp2 = vt2.getLocation();
		int[] vp3 = vt3.getLocation();
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
	    // color used if rendering type is wireframe or vertex
	    int shadedColor = ColorUtils.lerpRBG(color,  vt1.getColor(), -255);
	    RenderUtils.setPixel(x1, y1, z1, shadedColor, camera, RenderBuffer);
	    RenderUtils.setPixel(x2, y2, z2, shadedColor, camera, RenderBuffer);
	    RenderUtils.setPixel(x3, y3, z3, shadedColor, camera, RenderBuffer);
	}
	
	private void drawWireframe(Vertex vt1, Vertex vt2, Vertex vt3, int color, Camera camera, RenderBuffer RenderBuffer) {
		// get location of vertexes
		int[] vp1 = vt1.getLocation();
		int[] vp2 = vt2.getLocation();
		int[] vp3 = vt3.getLocation();
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
	    // color used if rendering type is wireframe or vertex
	    int shadedColor = ColorUtils.lerpRBG(color,  vt1.getColor(), -255);
	    RenderUtils.drawLine(x1, y1, x2, y2, z1, shadedColor, camera, RenderBuffer);
		RenderUtils.drawLine(x2, y2, x3, y3, z2, shadedColor, camera, RenderBuffer);
		RenderUtils.drawLine(x3, y3, x1, y1, z3, shadedColor, camera, RenderBuffer);
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
