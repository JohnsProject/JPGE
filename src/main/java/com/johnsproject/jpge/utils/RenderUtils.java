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
package com.johnsproject.jpge.utils;

import com.johnsproject.jpge.dto.Animation;
import com.johnsproject.jpge.dto.Camera;
import com.johnsproject.jpge.dto.RenderBuffer;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.Texture;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.dto.Vertex;

/**
 * The RenderUtils class provides methods needed at the rendering process.
 * 
 * @author John´s Project - John Salomon
 */
public class RenderUtils {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	private static final int SHIFT = 11;
	
	/**
	 * Projects a vector to from world (3D) to screen (2D) coordinates
	 *  using perspection projection technic wich takes depth into account.
	 * 
	 * @param vector vector to be projected. This vector should be in camera space.
	 * @param camera {@link Camera} this vector will be projected to.
	 * @return projected vector.
	 */
	public static int[] perspectiveProject(int[] vector, Camera camera) {
		int px = 0, py = 0, pz = 0;
		int fov = camera.getFieldOfView();
		int scalef = fov * camera.getScaleFactor();
		pz = vector[vz] + fov;
		if (pz != 0) {
			px = (vector[vx] * scalef) / pz;
			py = (vector[vy] * scalef) / pz;
		}
		vector[vx] = camera.getHalfWidth() + (px );
		vector[vy] = camera.getHalfHeight() + (py );
		vector[vz] = pz;
		return vector;
	}
	
	/**
	 * Projects a vector to from world (3D) to screen (2D) coordinates
	 *  using orthographic projection technic wich ignores depth.
	 * 
	 * @param vector vector to be projected. This vector should be in camera space.
	 * @param camera {@link Camera} this vector will be projected to.
	 * @return projected vector.
	 */
	public static int[] orthographicProject(int[] vector, Camera camera) {
		int px = 0, py = 0;
		int scalef = camera.getScaleFactor();
		px = (vector[vx] * scalef) >> 8;
		py = (vector[vy] * scalef) >> 8;
		vector[vx] = camera.getHalfWidth() + px;
		vector[vy] = camera.getHalfHeight() + py;
		return vector;
	}

	/**
	 * Applies the given {@link Animation} to the given vertex.
	 * 
	 * @param vertex vertex to animate.
	 * @param animation {@link Animation} to apply.
	 * @return animated vertex.
	 */
	public static Vertex animate(Vertex vertex, Animation animation) {
		int boneIndex = vertex.getBone();
		for (int i = 0; i <= boneIndex; i++) {
			Transform bone = animation.getBone(i, animation.getCurrentFrame());
			int[] pos = vertex.getLocation();
			//Transform bone = animation.getBone(i, animation.getCurrentFrame());
			pos = Vector3MathUtils.subtract(pos, bone.getLocation(), pos);
			pos = Vector3MathUtils.movePointByScale(pos, bone.getScale(), pos);
			pos = Vector3MathUtils.movePointByAnglesXYZ(pos, bone.getRotation(), pos);
			pos = Vector3MathUtils.add(pos, bone.getLocation(), pos);
		}
		return vertex;
	}
	
	/**
	 * This method checks if the given {@link Face} is inside the view frustum of the given camera.
	 * 
	 * @param face 		{@link Face} to check, it should be already projected.
	 * @param mesh 		{@link Mesh} that contains this {@link Face}.
	 * @param camera 	{@link Camera}.
	 * @return if the given face is inside the view frustum of the given camera.
	 */
	public static boolean isInsideViewFrustum(Face face, Mesh mesh, Camera camera) {
		face.setCulled(false);
		int[] v1 = mesh.getVertex(face.getVertex1()).getLocation();
		int[] v2 = mesh.getVertex(face.getVertex2()).getLocation();
		int[] v3 = mesh.getVertex(face.getVertex3()).getLocation();
		int ncp = camera.getNearClippingPlane();
		int fcp = camera.getFarClippingPlane();
		int w = camera.getWidth();
		int h = camera.getHeight();
		// calculate center of face
		int x = (v1[vx] + v2[vx] + v3[vx]) / 3;
		int y = (v1[vy] + v2[vy] + v3[vy]) / 3;
		int z = (v1[vz] + v2[vz] + v3[vz]) / 3;
		// tolerance
		int t = 50;
		// check if its inside view
		if (x > -t && x < w + t && y > -t && y < h + t && z > ncp && z < fcp) {
			return false;
		}
		face.setCulled(true);
		return true;
	}

	/**
	 * This method checks if the given {@link Face} is a backface.
	 * If this {@link Face} is a backface then this method returns true else it returns false.
	 * 
	 * @param face 	{@link Face} to check, it should be already projected.
	 * @param mesh 	{@link Mesh} that contains this {@link Face}.
	 * @return if the given face is a backface.
	 */
	public static boolean isBackface(Face face, Mesh mesh) {
		face.setCulled(false);
		int[] v1 = mesh.getVertex(face.getVertex1()).getLocation();
		int[] v2 = mesh.getVertex(face.getVertex2()).getLocation();
		int[] v3 = mesh.getVertex(face.getVertex3()).getLocation();
		int v1x = v1[vx], v1y = v1[vy];
		int v2x = v2[vx], v2y = v2[vy];
		int v3x = v3[vx], v3y = v3[vy];
		// calculate area of face
		int a = (v2x - v1x) * (v3y - v1y) - (v3x - v1x) * (v2y - v1y);
		// if area is negative then its a backface
		if (a < 0) return false;
		face.setCulled(true);
		return true;
	}
	
	// line drawing with fixed point Brenseham's
	
	/**
	 * Draws a line on the given {@link Camera} using Brenseham's line algorithm.
	 * 
	 * @param x1 start x location.
	 * @param y1 start y location.
	 * @param x2 end x location.
	 * @param y2 end y location.
	 * @param z z location used by the zBuffer.
	 * @param color color of the line.
	 * @param camera {@link Camera} to draw on.
	 * @param renderBuffer {@link RenderBuffer} to use.
	 */
	public static void drawLine(int x1, int y1, int x2, int y2, int z, int color,
						Camera camera, RenderBuffer renderBuffer) {		
		int dx = Math.abs(x2 - x1), sx = x1 < x2 ? 1 : -1;
		int dy = -Math.abs(y2 - y1), sy = y1 < y2 ? 1 : -1;
		int err = dx + dy, e2; /* error value e_xy */

		while (true) {
			setPixel(x1, y1, z, color, camera, renderBuffer);
			if (x1 == x2 && y1 == y2)
				break;
			e2 = 2 * err;
			if (e2 > dy) {
				err += dy;
				x1 += sx;
			} /* e_xy+e_x > 0 */
			if (e2 < dx) {
				err += dx;
				y1 += sy;
			} /* e_xy+e_y < 0 */
		}
	}
	
	/**
	 * Draws the given {@link Face} on the given {@link Camera} using the gouraud shading technic.
	 * 
	 * @param face {@link Face} to draw.
	 * @param mesh {@link Mesh} that contains the given {@link Face}.
	 * @param camera {@link Camera} to draw on.
	 * @param renderBuffer {@link RenderBuffer} to use.
	 */
	public static void drawFaceGouraud(Face face, Mesh mesh, Camera camera, RenderBuffer renderBuffer) {
		Vertex vt1 = mesh.getVertex(face.getVertex1());
		Vertex vt2 = mesh.getVertex(face.getVertex2());
		Vertex vt3 = mesh.getVertex(face.getVertex3());
		// get location of vertexes
		int[] vp1 = vt1.getLocation();
		int[] vp2 = vt2.getLocation();
		int[] vp3 = vt3.getLocation();
		// get uvs of face
		int[] uv1 = face.getUV1();
		int[] uv2 = face.getUV2();
		int[] uv3 = face.getUV3();
		// get colors of vertexes
		int vc1 = vt1.getColor();
		int vc2 = vt2.getColor();
		int vc3 = vt3.getColor();
		// get face color
		int color = mesh.getMaterial(face.getMaterial()).getColor();
		int tmp = 0;
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
		int u1 = uv1[vx], v1 = uv1[vy],
			u2 = uv2[vx], v2 = uv2[vy],
			u3 = uv3[vx], v3 = uv3[vy];
		// y sorting
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;
		   				tmp = z2; z2 = z1; z1 = tmp;
		   				tmp = v2; v2 = v1; v1 = tmp; 
		   				tmp = u2; u2 = u1; u1 = tmp;
		   				tmp = vc2; vc2 = vc1; vc1 = tmp;}
		if (y2 > y3) { tmp = y3; y3 = y2; y2 = tmp; 
		   				tmp = x3; x3 = x2; x2 = tmp;
		   				tmp = z3; z3 = z2; z2 = tmp;
		   				tmp = v3; v3 = v2; v2 = tmp; 
		   				tmp = u3; u3 = u2; u2 = tmp;
		   				tmp = vc3; vc3 = vc2; vc2 = tmp;}
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;
		   				tmp = z2; z2 = z1; z1 = tmp;
		   				tmp = v2; v2 = v1; v1 = tmp; 
		   				tmp = u2; u2 = u1; u1 = tmp;
		   				tmp = vc2; vc2 = vc1; vc1 = tmp;}
		face(x1, y1, z1, vc1, x2, y2, z2, vc2, x3, y3, z3, vc3, color, camera, renderBuffer);
	}
	
	// face drawing and filling with fixed point scanline algorithm that supports gouraud shading
	static void face(int x1, int y1, int z1, int vc1, int x2, int y2, int z2, int vc2, int x3, int y3, int z3, int vc3,
							int color, Camera cam, RenderBuffer renderBuffer) {				
		// get color values
		int r1 = ColorUtils.getRed(vc1), g1 = ColorUtils.getGreen(vc1), b1 = ColorUtils.getBlue(vc1), a1 = ColorUtils.getAlpha(vc1);
		int r2 = ColorUtils.getRed(vc2), g2 = ColorUtils.getGreen(vc2), b2 = ColorUtils.getBlue(vc2), a2 = ColorUtils.getAlpha(vc2);
		int r3 = ColorUtils.getRed(vc3), g3 = ColorUtils.getGreen(vc3), b3 = ColorUtils.getBlue(vc3), a3 = ColorUtils.getAlpha(vc3);
		// x deltas for each y location
		int dx1 = 0, dx2 = 0, dx3 = 0;
		// z deltas for each y location
		int dz1 = 0, dz2 = 0, dz3 = 0;
		// color deltas for each y location
		int dr1 = 0, dg1 = 0, db1 = 0, da1 = 0;
		int dr2 = 0, dg2 = 0, db2 = 0, da2 = 0;
		int dr3 = 0, dg3 = 0, db3 = 0, da3 = 0;
		// deltas for each x location
		int dz = 0, dr = 0, dg = 0, db = 0, da = 0;
		int dxdx = 0;
		// precalculate used values to save performance
		int y2y1 = y2 == y1 ? 1 : y2 - y1;
		int y3y1 = y3 == y1 ? 1 : y3 - y1;
		int y3y2 = y3 == y2 ? 1 : y3 - y2;
		// calculate deltas needed to get values for each y location
		if (y2y1 > 0) {
			// bitshift left to increase precision
			dx1 = ((x2 - x1) << SHIFT) / (y2y1);
			dz1 = ((z2 - z1) << SHIFT) / (y2y1);
			dr1 = ((r2 - r1) << SHIFT) / (y2y1);
			dg1 = ((g2 - g1) << SHIFT) / (y2y1);
			db1 = ((b2 - b1) << SHIFT) / (y2y1);
			da1 = ((a2 - a1) << SHIFT) / (y2y1);
		}
		if (y3y1 > 0) {
			dx2 = ((x3 - x1) << SHIFT) / (y3y1);
			dz2 = ((z3 - z1) << SHIFT) / (y3y1);
			dr2 = ((r3 - r1) << SHIFT) / (y3y1);
			dg2 = ((g3 - g1) << SHIFT) / (y3y1);
			db2 = ((b3 - b1) << SHIFT) / (y3y1);
			da2 = ((a3 - a1) << SHIFT) / (y3y1);
		}
		if (y3y2 > 0) {
			dx3 = ((x3 - x2) << SHIFT) / (y3y2);
			dz3 = ((z3 - z2) << SHIFT) / (y3y2);
			dr3 = ((r3 - r2) << SHIFT) / (y3y2);
			dg3 = ((g3 - g2) << SHIFT) / (y3y2);
			db3 = ((b3 - b2) << SHIFT) / (y3y2);
			da3 = ((a3 - a2) << SHIFT) / (y3y2);
		}
		// left and right side values starting at the top of face
		// bitshift left to increase precision
		int sx = x1 << SHIFT, ex = x1 << SHIFT;
		int sz = z1 << SHIFT;
		int sr = r1 << SHIFT;
		int sg = g1 << SHIFT;
		int sb = b1 << SHIFT;
		int sa = a1 << SHIFT;
	    if (dx1 > dx2) {
	    	// precalculate used values to save performance
	    	dxdx = dx1 - dx2;
	    	// calculate deltas needed to get shade factor values for each x location
			if (dxdx > 0) {
				dz = ((dz1 - dz2) << SHIFT) / (dxdx);
				dr = ((dr1 - dr2) << SHIFT) / (dxdx);
				dg = ((dg1 - dg2) << SHIFT) / (dxdx);
				db = ((db1 - db2) << SHIFT) / (dxdx);
				da = ((da1 - da2) << SHIFT) / (dxdx);
			}
			for (int y = 0; y < y2y1; y++) {
		    	// bitshift right to get right values
		    	drawHLine(sx >> SHIFT, ex >> SHIFT, sz, dz, sr, dr, sg, dg, sb, db, sa, da, y1 + y, color, cam, renderBuffer);
		    	// increase left and right values by the calculated delta
		    	sx += dx2; ex += dx1;
		    	sz += dz2;
		    	sr += dr2;
		    	sg += dg2;
		    	sb += db2;
		    	sa += da2;
	    	}
		    // make sure that correct x location is used to draw the other part
		    ex = x2 << SHIFT;
			dxdx = dx3 - dx2;
			if (dxdx > 0) {
				dz = ((dz3 - dz2) << SHIFT) / (dxdx);
				dr = ((dr3 - dr2) << SHIFT) / (dxdx);
				dg = ((dg3 - dg2) << SHIFT) / (dxdx);
				db = ((db3 - db2) << SHIFT) / (dxdx);
				da = ((da3 - da2) << SHIFT) / (dxdx);
			}
			for (int y = 0; y < y3y2; y++) {
				drawHLine(sx >> SHIFT, ex >> SHIFT, sz, dz, sr, dr, sg, dg, sb, db, sa, da, y2 + y, color, cam, renderBuffer);
				sx += dx2; ex += dx3;
				sz += dz2;
				sr += dr2;
		    	sg += dg2;
		    	sb += db2;
		    	sa += da2;
			}
	    }else {
	    	dxdx = dx2 - dx1;
			if (dxdx > 0) {
				dz = ((dz2 - dz1) << SHIFT) / (dxdx);
				dr = ((dr2 - dr1) << SHIFT) / (dxdx);
				dg = ((dg2 - dg1) << SHIFT) / (dxdx);
				db = ((db2 - db1) << SHIFT) / (dxdx);
				da = ((da2 - da1) << SHIFT) / (dxdx);
			}
			for (int y = 0; y < y2y1; y++) {
	    		drawHLine(sx >> SHIFT, ex >> SHIFT, sz, dz, sr, dr, sg, dg, sb, db, sa, da, y1 + y, color, cam, renderBuffer);
	    		sx += dx1; ex += dx2;
	    		sz += dz1;
	    		sr += dr1;
		    	sg += dg1;
		    	sb += db1;
		    	sa += da1;
	    	}
	    	sx = x2 << SHIFT;
	    	dxdx = dx2 - dx3;
			if (dxdx > 0) {
				dz = ((dz2 - dz3) << SHIFT) / (dxdx);
				dr = ((dr2 - dr3) << SHIFT) / (dxdx);
				dg = ((dg2 - dg3) << SHIFT) / (dxdx);
				db = ((db2 - db3) << SHIFT) / (dxdx);
				da = ((da2 - da3) << SHIFT) / (dxdx);
			}
			for (int y = 0; y < y3y2; y++) {
				drawHLine(sx >> SHIFT, ex >> SHIFT, sz, dz, sr, dr, sg, dg, sb, db, sa, da, y2 + y, color, cam, renderBuffer);
				sx += dx3; ex += dx2;
				sz += dz3;
				sr += dr3;
		    	sg += dg3;
		    	sb += db3;
		    	sa += da3;
			}
	    }
	}
	
	static void drawHLine(int sx, int ex, int sz, int dz, int sr, int dr, int sg, int dg, int sb, int db, int sa, int da,
			int sy, int color, Camera camera, RenderBuffer renderBuffer) {
		for (; sx < ex; sx++) {
			int iColor = ColorUtils.convert(sr >> SHIFT, sg >> SHIFT, sb >> SHIFT, sa >> SHIFT);
			// no need to shift sz as the z test is just check if its a higher value
			setPixel(sx, sy, sz >> SHIFT, ColorUtils.lerpRBG(iColor, color, 500), camera, renderBuffer);
			sz += dz;
			sr += dr;
			sg += dg;
			sb += db;
			sa += da;
		}
	}
	
	/**
	 * Draws the given {@link Face} on the given {@link Camera} using the 
	 * gouraud shading and affine texture mapping technic.
	 * 
	 * @param face {@link Face} to draw.
	 * @param mesh {@link Mesh} that contains the given {@link Face}.
	 * @param camera {@link Camera} to draw on.
	 * @param renderBuffer {@link RenderBuffer} to use.
	 */
	public static void drawFaceAffine(Face face, Mesh mesh, Camera camera, RenderBuffer renderBuffer) {
		Vertex vt1 = mesh.getVertex(face.getVertex1());
		Vertex vt2 = mesh.getVertex(face.getVertex2());
		Vertex vt3 = mesh.getVertex(face.getVertex3());
		// get location of vertexes
		int[] vp1 = vt1.getLocation();
		int[] vp2 = vt2.getLocation();
		int[] vp3 = vt3.getLocation();
		// get uvs of face
		int[] uv1 = face.getUV1();
		int[] uv2 = face.getUV2();
		int[] uv3 = face.getUV3();
		// get colors of vertexes
		int vc1 = vt1.getColor();
		int vc2 = vt2.getColor();
		int vc3 = vt3.getColor();
		// get face texture
		Texture img = mesh.getMaterial(face.getMaterial()).getTexture();
		int tmp = 0;
		int x1 = vp1[vx], y1 = vp1[vy], z1 = vp1[vz],
			x2 = vp2[vx], y2 = vp2[vy], z2 = vp2[vz],
			x3 = vp3[vx], y3 = vp3[vy], z3 = vp3[vz];
		int u1 = uv1[vx], v1 = uv1[vy],
			u2 = uv2[vx], v2 = uv2[vy],
			u3 = uv3[vx], v3 = uv3[vy];
		// y sorting
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;
		   				tmp = z2; z2 = z1; z1 = tmp;
		   				tmp = v2; v2 = v1; v1 = tmp; 
		   				tmp = u2; u2 = u1; u1 = tmp;
		   				tmp = vc2; vc2 = vc1; vc1 = tmp;}
		if (y2 > y3) { tmp = y3; y3 = y2; y2 = tmp; 
		   				tmp = x3; x3 = x2; x2 = tmp;
		   				tmp = z3; z3 = z2; z2 = tmp;
		   				tmp = v3; v3 = v2; v2 = tmp; 
		   				tmp = u3; u3 = u2; u2 = tmp;
		   				tmp = vc3; vc3 = vc2; vc2 = tmp;}
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;
		   				tmp = z2; z2 = z1; z1 = tmp;
		   				tmp = v2; v2 = v1; v1 = tmp; 
		   				tmp = u2; u2 = u1; u1 = tmp;
		   				tmp = vc2; vc2 = vc1; vc1 = tmp;}
		faceAffine(x1, y1, z1, vc1, x2, y2, z2, vc2, x3, y3, z3, vc3, u1, v1, u2, v2, u3, v3, img, camera, renderBuffer);
	}
	
	// face drawing and filling with fixed point scanline algorithm that supports gouraud shading
	static void faceAffine(int x1, int y1, int z1, int vc1, int x2, int y2, int z2, int vc2, int x3, int y3, int z3, int vc3,
								int u1, int v1, int u2, int v2, int u3, int v3,
								Texture img, Camera cam, RenderBuffer renderBuffer) {
		// interpolate uv coordinates
		int w = img.getWidth() - 1, h = img.getHeight() - 1;
		int sh = 7;
		u1 = (u1 * w) >> sh; u2 = (u2 * w) >> sh; u3 = (u3 * w) >> sh;
		v1 = (v1 * h) >> sh; v2 = (v2 * h) >> sh; v3 = (v3 * h) >> sh;
		// get color values
		int r1 = ColorUtils.getRed(vc1), g1 = ColorUtils.getGreen(vc1), b1 = ColorUtils.getBlue(vc1), a1 = ColorUtils.getAlpha(vc1);
		int r2 = ColorUtils.getRed(vc2), g2 = ColorUtils.getGreen(vc2), b2 = ColorUtils.getBlue(vc2), a2 = ColorUtils.getAlpha(vc2);
		int r3 = ColorUtils.getRed(vc3), g3 = ColorUtils.getGreen(vc3), b3 = ColorUtils.getBlue(vc3), a3 = ColorUtils.getAlpha(vc3);	
		// x deltas for each y location
		int dx1 = 0, dx2 = 0, dx3 = 0;
		// z deltas for each y location
		int dz1 = 0, dz2 = 0, dz3 = 0;
		// u deltas for each y location
		int du1 = 0, du2 = 0, du3 = 0;
		// v deltas for each y location
		int dv1 = 0, dv2 = 0, dv3 = 0;
		// color deltas for each y location
		int dr1 = 0, dg1 = 0, db1 = 0, da1 = 0;
		int dr2 = 0, dg2 = 0, db2 = 0, da2 = 0;
		int dr3 = 0, dg3 = 0, db3 = 0, da3 = 0;
		// deltas for each x location
		int dz = 0,du = 0, dv = 0, dr = 0, dg = 0, db = 0, da = 0;
		int dxdx = 0;
		// precalculate used values to save performance
		int y2y1 = y2 == y1 ? 1 : y2 - y1;
		int y3y1 = y3 == y1 ? 1 : y3 - y1;
		int y3y2 = y3 == y2 ? 1 : y3 - y2;
		// calculate deltas needed to get values for each y location
		if (y2y1 > 0) {
			// bitshift left to increase precision
			dx1 = ((x2 - x1) << SHIFT) / (y2y1);
			dz1 = ((z2 - z1) << SHIFT) / (y2y1);
			du1 = ((u2 - u1) << SHIFT) / (y2y1);
			dv1 = ((v2 - v1) << SHIFT) / (y2y1);
			dr1 = ((r2 - r1) << SHIFT) / (y2y1);
			dg1 = ((g2 - g1) << SHIFT) / (y2y1);
			db1 = ((b2 - b1) << SHIFT) / (y2y1);
			da1 = ((a2 - a1) << SHIFT) / (y2y1);
		};
		if (y3y1 > 0) {
			dx2 = ((x3 - x1) << SHIFT) / (y3y1);
			dz2 = ((z3 - z1) << SHIFT) / (y3y1);
			du2 = ((u3 - u1) << SHIFT) / (y3y1);
			dv2 = ((v3 - v1) << SHIFT) / (y3y1);
			dr2 = ((r3 - r1) << SHIFT) / (y3y1);
			dg2 = ((g3 - g1) << SHIFT) / (y3y1);
			db2 = ((b3 - b1) << SHIFT) / (y3y1);
			da2 = ((a3 - a1) << SHIFT) / (y3y1);
		};
		if (y3y2 > 0) {
			dx3 = ((x3 - x2) << SHIFT) / (y3y2);
			dz3 = ((z3 - z2) << SHIFT) / (y3y2);
			du3 = ((u3 - u2) << SHIFT) / (y3y2);
			dv3 = ((v3 - v2) << SHIFT) / (y3y2);
			dr3 = ((r3 - r2) << SHIFT) / (y3y2);
			dg3 = ((g3 - g2) << SHIFT) / (y3y2);
			db3 = ((b3 - b2) << SHIFT) / (y3y2);
			da3 = ((a3 - a2) << SHIFT) / (y3y2);
		};
		// left and right side values starting at the top of face
		// bitshift left to increase precision
		int sx = x1 << SHIFT, ex = x1 << SHIFT;
		int sz = z1 << SHIFT;
		int su = u1 << SHIFT;
		int sv = v1 << SHIFT;
		int sr = r1 << SHIFT;
		int sg = g1 << SHIFT;
		int sb = b1 << SHIFT;
		int sa = a1 << SHIFT;
	    if (dx1 > dx2) {
	    	// precalculate used values to save performance
	    	dxdx = dx1 - dx2;
	    	// calculate deltas needed to get shade factor values for each x location
	    	if (dxdx > 0) {
				dz = ((dz1 - dz2) << SHIFT) / (dxdx);
				du = ((du1 - du2) << SHIFT) / (dxdx);
				dv = ((dv1 - dv2) << SHIFT) / (dxdx);
				dr = ((dr1 - dr2) << SHIFT) / (dxdx);
				dg = ((dg1 - dg2) << SHIFT) / (dxdx);
				db = ((db1 - db2) << SHIFT) / (dxdx);
				da = ((da1 - da2) << SHIFT) / (dxdx);
			}
		    for (int y = 0; y < y2y1; y++) {
		    	// bitshift right to get right values
		    	drawHLineAffine(sx >> SHIFT, ex >> SHIFT, sz, dz, su, du, sv, dv, sr, dr, sg, dg, sb, db, sa, da, y1 + y, img, cam, renderBuffer);
		    	// increase left and right values by the calculated delta
				sx += dx2; ex += dx1;
				sz += dz2;
				su += du2;
				sv += dv2;
				sr += dr2;
		    	sg += dg2;
		    	sb += db2;
		    	sa += da2;
			}
		    // make sure that correct x location is used to draw the other part
			ex = x2 << SHIFT;
			dxdx = dx3 - dx2;
	    	if (dxdx > 0) {
				dz = ((dz3 - dz2) << SHIFT) / (dxdx);
				du = ((du3 - du2) << SHIFT) / (dxdx);
				dv = ((dv3 - dv2) << SHIFT) / (dxdx);
				dr = ((dr3 - dr2) << SHIFT) / (dxdx);
				dg = ((dg3 - dg2) << SHIFT) / (dxdx);
				db = ((db3 - db2) << SHIFT) / (dxdx);
				da = ((da3 - da2) << SHIFT) / (dxdx);
			}
	    	for (int y = 0; y < y3y2; y++) {
				drawHLineAffine(sx >> SHIFT, ex >> SHIFT, sz, dz, su, du, sv, dv, sr, dr, sg, dg, sb, db, sa, da, y2 + y, img, cam, renderBuffer);
				sx += dx2; ex += dx3;
				sz += dz2;
				su += du2;
				sv += dv2;
				sr += dr2;
		    	sg += dg2;
		    	sb += db2;
		    	sa += da2;
			}
	    }else{
	    	dxdx = dx2 - dx1;
	    	if (dxdx > 0) {
				dz = ((dz2 - dz1) << SHIFT) / (dxdx);
				du = ((du2 - du1) << SHIFT) / (dxdx);
				dv = ((dv2 - dv1) << SHIFT) / (dxdx);
				dr = ((dr2 - dr1) << SHIFT) / (dxdx);
				dg = ((dg2 - dg1) << SHIFT) / (dxdx);
				db = ((db2 - db1) << SHIFT) / (dxdx);
				da = ((da2 - da1) << SHIFT) / (dxdx);
			}
	    	for (int y = 0; y < y2y1; y++) {
				drawHLineAffine(sx >> SHIFT, ex >> SHIFT, sz, dz, su, du, sv, dv, sr, dr, sg, dg, sb, db, sa, da, y1 + y, img, cam, renderBuffer);
				sx += dx1; ex += dx2;
				sz += dz1;
				su += du1;
				sv += dv1;
				sr += dr1;
		    	sg += dg1;
		    	sb += db1;
		    	sa += da1;
			}
			sx = x2 << SHIFT;
			dxdx = dx2 - dx3;
	    	if (dxdx > 0) {
				dz = ((dz2 - dz3) << SHIFT) / (dxdx);
				du = ((du2 - du3) << SHIFT) / (dxdx);
				dv = ((dv2 - dv3) << SHIFT) / (dxdx);
				dr = ((dr2 - dr3) << SHIFT) / (dxdx);
				dg = ((dg2 - dg3) << SHIFT) / (dxdx);
				db = ((db2 - db3) << SHIFT) / (dxdx);
				da = ((da2 - da3) << SHIFT) / (dxdx);
			}
	    	for (int y = 0; y < y3y2; y++) {
				drawHLineAffine(sx >> SHIFT, ex >> SHIFT, sz, dz, su, du, sv, dv, sr, dr, sg, dg, sb, db, sa, da, y2 + y, img, cam, renderBuffer);
				sx += dx3; ex += dx2;
				sz += dz3;
				su += du3;
				sv += dv3;
				sr += dr3;
		    	sg += dg3;
		    	sb += db3;
		    	sa += da3;
			}
	    }
	}
	
	static void drawHLineAffine(int sx, int ex, int sz, int dz, int su, int du, int sv, int dv, int sr,
								int dr, int sg, int dg, int sb, int db, int sa, int da, int sy,
								Texture img, Camera camera, RenderBuffer renderBuffer) {
		for (; sx < ex; sx++) {
			// get texture pixel / texel color
			int color = img.getPixel(su >> SHIFT, sv >> SHIFT);
			int iColor = ColorUtils.convert(sr >> SHIFT, sg >> SHIFT, sb >> SHIFT, sa >> SHIFT);
			// no need to shift sz as the z test is just check if its a higher value
			setPixel(sx, sy, sz >> SHIFT, ColorUtils.lerpRBG(iColor, color, 500), camera, renderBuffer);
			sz += dz;
			su += du;
			sv += dv;
			sr += dr;
			sg += dg;
			sb += db;
			sa += da;
		}
	}
	
	/**
	 * Sets a pixel in the RenderBuffer with given coordinates taking in account the camera.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param color
	 * @param camera
	 * @param renderBuffer
	 */
	public static void setPixel(int x, int y, int z, int color, Camera camera, RenderBuffer renderBuffer) {
		// check if pixel is inside camera
		if (x > 0 && x < camera.getWidth() && y > 0 && y < camera.getHeight()) {
			renderBuffer.setPixel(camera.getLocationX() + x, camera.getLocationY() + y, z, color);
		}
	}
}
