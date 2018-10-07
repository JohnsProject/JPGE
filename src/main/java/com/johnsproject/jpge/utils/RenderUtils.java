package com.johnsproject.jpge.utils;

import com.johnsproject.jpge.graphics.Animation;
import com.johnsproject.jpge.graphics.Camera;
import com.johnsproject.jpge.graphics.Face;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.Texture;
import com.johnsproject.jpge.graphics.Transform;
import com.johnsproject.jpge.graphics.Vertex;
import com.johnsproject.jpge.graphics.SceneRenderer.ProjectionType;
import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;

/**
 * The RenderUtils class provides methods needed at the rendering process.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class RenderUtils {

	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	private static final int SHIFT = 10;
	
	/**
	 * Projects a vector to from world (3D) to screen (2D) coordinates based on the {@link ProjectionType}
	 * of the given {@link Camera}.
	 * 
	 * @param vector vector to be projected. This vector should be in camera space.
	 * @param camera {@link Camera} this vector will be projected to.
	 * @return projected vector.
	 */
	public static int[] project(int[] vector, Camera camera) {
		int px = vector[vx];
		int py = vector[vy];
		int pz = vector[vz];
		int fov = camera.getFieldOfView();
		int rescalef = camera.getScaleFactor();
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			pz =  pz + fov;
			if (pz <= 0) pz = 1;
			px = (px * rescalef * fov) / pz;
			py = (py * rescalef * fov) / pz;
			break;
		case orthographic: // this projectionType ignores depth
			px = (px * rescalef)>>5;
			py = (py * rescalef)>>5;
			break;
		}
		vector[vx] = px + camera.getHalfScreenSize()[vx];
		vector[vy] = py + camera.getHalfScreenSize()[vy];
		vector[vz] = pz;
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
			int[] pos = vertex.getPosition();
			//Transform bone = animation.getBone(i, animation.getCurrentFrame());
			pos = Vector3MathUtils.subtract(pos, bone.getPosition(), pos);
			pos = Vector3MathUtils.movePointByScale(pos, bone.getScale(), pos);
			pos = Vector3MathUtils.movePointByAnglesXYZ(pos, bone.getRotation(), pos);
			pos = Vector3MathUtils.add(pos, bone.getPosition(), pos);
		}
		return vertex;
	}
	
	/**
	 * This method checks if the given {@link Face} is inside the view frustum of the given camera.
	 * 
	 * @param face 		{@link Face} to check.
	 * @param mesh 		{@link Mesh} that contains this {@link Face}.
	 * @param camera 	{@link Camera}.
	 * @return if the given face is inside the view frustum of the given camera.
	 */
	public static boolean isInsideViewFrustum(Face face, Mesh mesh, Camera camera) {
		face.setCulled(false);
		int[] v1 = mesh.getBufferedVertex(face.getVertex1()).getPosition();
		int[] v2 = mesh.getBufferedVertex(face.getVertex2()).getPosition();
		int[] v3 = mesh.getBufferedVertex(face.getVertex3()).getPosition();
		int ncp = camera.getNearClippingPlane();
		int fcp = camera.getFarClippingPlane();
		int w = camera.getWidth();
		int h = camera.getHeight();
		// calculate center of face
		int x = (v1[vx] + v2[vx] + v3[vx]) / 3;
		int y = (v1[vy] + v2[vy] + v3[vy]) / 3;
		int z = (v1[vz] + v2[vz] + v3[vz]) / 3;
		// tolerance
		int t = 100;
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
	 * @param face 	{@link Face} to check.
	 * @param mesh 	{@link Mesh} that contains this {@link Face}.
	 * @return if the given face is a backface.
	 */
	public static boolean isBackface(Face face, Mesh mesh) {
		face.setCulled(false);
		int[] v1 = mesh.getBufferedVertex(face.getVertex1()).getPosition();
		int[] v2 = mesh.getBufferedVertex(face.getVertex2()).getPosition();
		int[] v3 = mesh.getBufferedVertex(face.getVertex3()).getPosition();
		int v1x = v1[vx], v1y = v1[vy];
		int v2x = v2[vx], v2y = v2[vy];
		int v3x = v3[vx], v3y = v3[vy];
		// calculate area of face
		// ">> 1" = "/ 2"
		int a = ((v2x - v1x) * (v3y - v1y) - (v3x - v1x) * (v2y - v1y)) >> 1;
		// if area is negative then its a backface
		if (a < 0) return false;
		face.setCulled(true);
		return true;
	}
	
	/**
	 * Draws the given face to the given {@link Camera} based on the {@link RenderingType} of the given {@link Camera}.
	 * 
	 * @param face face to draw.
	 * @param mesh {@link Mesh} that contains the face.
	 * @param zBuffer depth buffer.
	 * @param camera {@link Camera} to draw.
	 */
	public static void drawFace(Face face, Mesh mesh, int[] zBuffer, Camera camera) {
		int width = camera.getScreenSize()[vx];
		int height = camera.getScreenSize()[vy];
		// get vertexes
		Vertex vt1 = mesh.getBufferedVertex(face.getVertex1());
		Vertex vt2 = mesh.getBufferedVertex(face.getVertex2());
		Vertex vt3 = mesh.getBufferedVertex(face.getVertex3());
		// get position of vertexes
		int[] vp1 = vt1.getPosition();
		int[] vp2 = vt2.getPosition();
		int[] vp3 = vt3.getPosition();
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
		// hack to fix fill bug
		if (y2 == y1) y2++;
		if (y3 == y1) y3++;
	    // color used if rendering type is wireframe of vertex
	    int shadedColor = ColorUtils.lerp(color, vc1, 123);
	    // draw based on the cameras rendering type
	    switch (camera.getRenderingType()) {
		case vertex:
			// if its a vertex just check if its inside the camera and set a pixel
			if(y1 > 0 && y1 < height && x1 > 0 && x1 < width)
	    		setPixel(x1, y1, z1, shadedColor, zBuffer, width, camera);
	    	if(y2 > 0 && y2 < height && x2 > 0 && x2 < width)
	    		setPixel(x2, y2, z2, shadedColor, zBuffer, width, camera);
	    	if(y3 > 0 && y3 < height && x3 > 0 && x3 < width)
	    		setPixel(x3, y3, z3, shadedColor, zBuffer, width, camera);
			break;
			
		case wireframe:
			drawLine(x1, y1, x2, y2, z1, shadedColor, zBuffer, width, height, camera);
			drawLine(x2, y2, x3, y3, z2, shadedColor, zBuffer, width, height, camera);
			drawLine(x3, y3, x1, y1, z3, shadedColor, zBuffer, width, height, camera);
			break;
			
		case solid:
			drawFace(x1, y1, z1, vc1, x2, y2, z2, vc2, x3, y3, z3, vc3, zBuffer, width, height, color, camera);
			break;
			
		case textured:
			Texture img = mesh.getMaterial(face.getMaterial()).getTexture();
			//drawFaceAffine(x1, y1, vc1, x2, y2, vc2, x3, y3, vc3, u1, v1, u2, v2, u3, v3, img, zBuffer, width, height, camera);
			break;
		}
	}
	
	static void setPixel(int x, int y, int z, int color, int[] zBuffer, int width, Camera camera) {
		// get position of pixel in the zBuffer
		int pos = x + (y * width);
		// z test
		if(zBuffer[pos] > z) {
			zBuffer[pos] = z;
			camera.setPixel(x, y, color);
		}
	}
	
	// line drawing with fixed point Brenseham's
	static void drawLine(int x1, int y1, int x2, int y2, int z, int color,
						int[] zBuffer, int width, int height, Camera camera) {
		int w = x2 - x1;
		int h = y2 - y1;
		// deltas
		int dx1 = 0, dy1 = 0, dz1 = 0,
			dx2 = 0, dy2 = 0, dz2 = 0;
		if (w < 0) dx1 = -1; else if (w > 0) dx1 = 1;
		if (h < 0) dy1 = -1; else if (h > 0) dy1 = 1;
		if (w < 0) dx2 = -1; else if (w > 0) dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (longest < shortest) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0) dy2 = -1;
			else if (h > 0) dy2 = 1;
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i < longest; i++, numerator += shortest) {
			// check if its inside the camera
			if(y1 > 0 && y1 < height && x1 > 0 && x1 < width) {
				// set pixel
				setPixel(x1, y1, z, color, zBuffer, width, camera);
				// increase deltas
				if (numerator > longest) {
					numerator -= longest;
					x1 += dx1;
					y1 += dy1;
					z += dz1;
				} else {
					x1 += dx2;
					y1 += dy2;
					z += dz2;
				}
			}
		}
	}
	
	// face drawing and filling with fixed point scanline algorithm that supports gouraud shading
	static void drawFace(int x1, int y1, int z1, int vc1, int x2, int y2, int z2, int vc2, int x3, int y3, int z3, int vc3,
							int[] zBuffer, int width, int height, int color, Camera cam) {
		// get color values
		int r1 = ColorUtils.getRed(vc1), g1 = ColorUtils.getGreen(vc1), b1 = ColorUtils.getBlue(vc1), a1 = ColorUtils.getAlpha(vc1);
		int r2 = ColorUtils.getRed(vc2), g2 = ColorUtils.getGreen(vc2), b2 = ColorUtils.getBlue(vc2), a2 = ColorUtils.getAlpha(vc2);
		int r3 = ColorUtils.getRed(vc3), g3 = ColorUtils.getGreen(vc3), b3 = ColorUtils.getBlue(vc3), a3 = ColorUtils.getAlpha(vc3);
		// x deltas
		int dx1 = 0, dx2 = 0, dx3 = 0;
		// z deltas
		int dz1 = 0, dz2 = 0, dz3 = 0;
		// color deltas
		int dr1 = 0, dg1 = 0, db1 = 0, da1 = 0;
		int dr2 = 0, dg2 = 0, db2 = 0, da2 = 0;
		int dr3 = 0, dg3 = 0, db3 = 0, da3 = 0;
		// precalculate used values to save performance
		int y2y1 = y2 - y1, y3y1 = y3 - y1, y3y2 = y3 - y2;
		// calculate x and shade factor deltas
		// needed to get x and shade factor values for each y position
		if (y2y1 > 0) {
			// bitshift left to increase precision
			dx1 = ((x2 - x1) << SHIFT) / (y2y1);
			dz1 = ((z2 - z1) << SHIFT) / (y2y1);
			dr1 = ((r2 - r1) << SHIFT) / (y2y1);
			dg1 = ((g2 - g1) << SHIFT) / (y2y1);
			db1 = ((b2 - b1) << SHIFT) / (y2y1);
			da1 = ((a2 - a1) << SHIFT) / (y2y1);
		} else dx1 = 0;
		if (y3y1 > 0) {
			dx2 = ((x3 - x1) << SHIFT) / (y3y1);
			dz2 = ((z3 - z1) << SHIFT) / (y3y1);
			dr2 = ((r3 - r1) << SHIFT) / (y3y1);
			dg2 = ((g3 - g1) << SHIFT) / (y3y1);
			db2 = ((b3 - b1) << SHIFT) / (y3y1);
			da2 = ((a3 - a1) << SHIFT) / (y3y1);
		} else dx2 = 0;
		if (y3y2 > 0) {
			dx3 = ((x3 - x2) << SHIFT) / (y3y2);
			dz3 = ((z3 - z2) << SHIFT) / (y3y2);
			dr3 = ((r3 - r2) << SHIFT) / (y3y2);
			dg3 = ((g3 - g2) << SHIFT) / (y3y2);
			db3 = ((b3 - b2) << SHIFT) / (y3y2);
			da3 = ((a3 - a2) << SHIFT) / (y3y2);
		} else dx3 = 0;
		// left and right side values starting at the top of face
		// bitshift left to increase precision
		int sx = x1 << SHIFT, ex = x1 << SHIFT;
		int sz = z1 << SHIFT, ez = z1 << SHIFT;
		int sr = r1 << SHIFT, er = r1 << SHIFT;
		int sg = g1 << SHIFT, eg = g1 << SHIFT;
		int sb = b1 << SHIFT, eb = b1 << SHIFT;
		int sa = a1 << SHIFT, ea = a1 << SHIFT;
	    int sy = y1;
	    if (dx1 > dx2) {
		    for (; sy <= y2-1; sy++) {
		    	// bitshift right to get right values
		    	drawHLine(sx >> SHIFT, ex >> SHIFT, sz, ez, sr, er, sg, eg, sb, eb, sa, ea, sy, color, zBuffer, width, height, cam);
		    	// increase left and right values by the calculated delta
		    	sx += dx2; ex += dx1;
		    	sz += dz2; ez += dz1;
		    	sr += dr2; er += dr1;
		    	sg += dg2; eg += dg1;
		    	sb += db2; eb += db1;
		    	sa += da2; ea += da1;
	    	}
		    // make sure that correct x position is used to draw the other part
			ex = x2 << SHIFT;
			for (; sy <= y3; sy++) {
				drawHLine(sx >> SHIFT, ex >> SHIFT, sz, ez, sr, er, sg, eg, sb, eb, sa, ea, sy, color, zBuffer, width, height, cam);
				sx += dx2; ex += dx3;
				sz += dz2; ez += dz3;
				sr += dr2; er += dr3;
		    	sg += dg2; eg += dg3;
		    	sb += db2; eb += db3;
		    	sa += da2; ea += da3;
			}
	    }else {
	    	for (; sy <= y2-1; sy++) {
	    		drawHLine(sx >> SHIFT, ex >> SHIFT, sz, ez, sr, er, sg, eg, sb, eb, sa, ea, sy, color, zBuffer, width, height, cam);
	    		sx += dx1; ex += dx2;
	    		sz += dz1; ez += dz2;
	    		sr += dr1; er += dr2;
		    	sg += dg1; eg += dg2;
		    	sb += db1; eb += db2;
		    	sa += da1; ea += da2;
	    	}
	    	sx = x2 << SHIFT;
			for (; sy <= y3; sy++) {
				drawHLine(sx >> SHIFT, ex >> SHIFT, sz, ez, sr, er, sg, eg, sb, eb, sa, ea, sy, color, zBuffer, width, height, cam);
				sx += dx3; ex += dx2;
				sz += dz3; ez += dz2;
				sr += dr3; er += dr2;
		    	sg += dg3; eg += dg2;
		    	sb += db3; eb += db2;
		    	sa += da3; ea += da2;
			}
	    }
	}
	
	static void drawHLine(int sx, int ex, int sz, int ez, int sr, int er, int sg, int eg, int sb, int eb, int sa, int ea, int sy, int color,
							int[] zBuffer, int width, int height, Camera camera) {
		int dz = 0, dr = 0, dg = 0, db = 0, da = 0;
		int exsx = ex - sx;
		// calculate shade factor delta needed to get shade factor values for each x position
		if (exsx > 0) {
			dz = (ez - sz) / (exsx);
			dr = (er - sr) / (exsx);
			dg = (eg - sg) / (exsx);
			db = (eb - sb) / (exsx);
			da = (ea - sa) / (exsx);
		}
		if(sy > 0 && sy < height) {
			for (int i = sx; i < ex; i ++) {
				if(i > 0 && i < width) {
					int iColor = ColorUtils.convert(sr >> SHIFT, sg >> SHIFT, sb >> SHIFT, sa >> SHIFT);
					setPixel(i, sy, sz, ColorUtils.lerpRBG(color, iColor, -255), zBuffer, width, camera);
				}
				sz += dz;
				sr += dr;
				sg += dg;
				sb += db;
				sa += da;
			}
		}
	}
	
	// face drawing and filling with fixed point scanline algorithm that supports gouraud shading
	static void drawFaceAffine(int x1, int y1, int vc1, int x2, int y2, int vc2, int x3, int y3, int vc3, int z,
								int u1, int v1, int u2, int v2, int u3, int v3,
								Texture img, int[] zBuffer, int width, int height, Camera cam) {
		// interpolate uv coordinates
		int w = img.getWidth() - 1, h = img.getHeight() - 1;
		u1 = (u1 * w) >> 7; u2 = (u2 * w) >> 7 ; u3 = (u3 * w) >> 7;
		v1 = (v1 * h) >> 7; v2 = (v2 * h) >> 7 ; v3 = (v3 * h) >> 7;
		// get color values
		int r1 = ColorUtils.getRed(vc1), g1 = ColorUtils.getGreen(vc1), b1 = ColorUtils.getBlue(vc1), a1 = ColorUtils.getAlpha(vc1);
		int r2 = ColorUtils.getRed(vc2), g2 = ColorUtils.getGreen(vc2), b2 = ColorUtils.getBlue(vc2), a2 = ColorUtils.getAlpha(vc2);
		int r3 = ColorUtils.getRed(vc3), g3 = ColorUtils.getGreen(vc3), b3 = ColorUtils.getBlue(vc3), a3 = ColorUtils.getAlpha(vc3);	
		// x deltas
		int dx1 = 0, dx2 = 0, dx3 = 0;
		// u deltas
		int du1 = 0, du2 = 0, du3 = 0;
		// v deltas
		int dv1 = 0, dv2 = 0, dv3 = 0;
		// color deltas
		int dr1 = 0, dg1 = 0, db1 = 0, da1 = 0;
		int dr2 = 0, dg2 = 0, db2 = 0, da2 = 0;
		int dr3 = 0, dg3 = 0, db3 = 0, da3 = 0;
		// precalculate used values to save performance
		int y2y1 = y2-y1, y3y1 = y3-y1, y3y2 = y3-y2;
		// calculate x, u, v and shade factor deltas
		// needed to get x, u, v and shade factor values for each y position
		if (y2y1 > 0) {
			// bitshift left to increase precision
			dx1 = (((x2 - x1) << SHIFT) / (y2y1));
			du1 = (((u2 - u1) << SHIFT) / (y2y1));
			dv1 = (((v2 - v1) << SHIFT) / (y2y1));
			dr1 = ((r2 - r1) << SHIFT) / (y2y1);
			dg1 = ((g2 - g1) << SHIFT) / (y2y1);
			db1 = ((b2 - b1) << SHIFT) / (y2y1);
			da1 = ((a2 - a1) << SHIFT) / (y2y1);
		} else dx1 = du1 = dv1 = 0;
		if (y3y1 > 0) {
			dx2 = (((x3 - x1) << SHIFT) / (y3y1));
			du2 = (((u3 - u1) << SHIFT) / (y3y1));
			dv2 = (((v3 - v1) << SHIFT) / (y3y1));
			dr2 = ((r3 - r1) << SHIFT) / (y3y1);
			dg2 = ((g3 - g1) << SHIFT) / (y3y1);
			db2 = ((b3 - b1) << SHIFT) / (y3y1);
			da2 = ((a3 - a1) << SHIFT) / (y3y1);
		} else dx2 = du2 = dv2 = 0;
		if (y3y2 > 0) {
			dx3 = (((x3 - x2) << SHIFT) / (y3y2));
			du3 = (((u3 - u2) << SHIFT) / (y3y2));
			dv3 = (((v3 - v2) << SHIFT) / (y3y2));
			dr3 = ((r3 - r2) << SHIFT) / (y3y2);
			dg3 = ((g3 - g2) << SHIFT) / (y3y2);
			db3 = ((b3 - b2) << SHIFT) / (y3y2);
			da3 = ((a3 - a2) << SHIFT) / (y3y2);
		} else dx3 = du3 = dv3 = 0;
		// left and right side values starting at the top of face
		// bitshift left to increase precision
		int sx = x1 << SHIFT, ex = x1 << SHIFT;
		int su = u1 << SHIFT, eu = u1 << SHIFT;
		int sv = v1 << SHIFT, ev = v1 << SHIFT;
		int sr = r1 << SHIFT, er = r1 << SHIFT;
		int sg = g1 << SHIFT, eg = g1 << SHIFT;
		int sb = b1 << SHIFT, eb = b1 << SHIFT;
		int sa = a1 << SHIFT, ea = a1 << SHIFT;
	    int sy = y1;
	    if (dx1 > dx2) {
		    for (; sy <= y2 - 1; sy++) {
		    	// bitshift right to get right values
		    	drawHLineAffine(sx >> SHIFT, ex >> SHIFT, su, eu, sv, ev, sr, er, sg, eg, sb, eb, sa, ea, sy, z, img, zBuffer, width, height, cam);
		    	// increase left and right values by the calculated delta
				sx += dx2; ex += dx1;
				su += du2; eu += du1;
				sv += dv2; ev += dv1;
				sr += dr2; er += dr1;
		    	sg += dg2; eg += dg1;
		    	sb += db2; eb += db1;
		    	sa += da2; ea += da1;
			}
		    // make sure that correct x position is used to draw the other part
			ex = x2 << SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx >> SHIFT, ex >> SHIFT, su, eu, sv, ev, sr, er, sg, eg, sb, eb, sa, ea, sy, z, img, zBuffer, width, height, cam);
				sx += dx2; ex += dx3;
				su += du2; eu += du3;
				sv += dv2; ev += dv3;
				sr += dr2; er += dr3;
		    	sg += dg2; eg += dg3;
		    	sb += db2; eb += db3;
		    	sa += da2; ea += da3;
			}
	    }else{
			for (; sy <= y2 - 1; sy++) {
				drawHLineAffine(sx >> SHIFT, ex >> SHIFT, su, eu, sv, ev, sr, er, sg, eg, sb, eb, sa, ea, sy, z, img, zBuffer, width, height, cam);
				sx += dx1; ex += dx2;
				su += du1; eu += du2;
				sv += dv1; ev += dv2;
				sr += dr1; er += dr2;
		    	sg += dg1; eg += dg2;
		    	sb += db1; eb += db2;
		    	sa += da1; ea += da2;
			}
			sx = x2 << SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx >> SHIFT, ex >> SHIFT, su, eu, sv, ev, sr, er, sg, eg, sb, eb, sa, ea, sy, z, img, zBuffer, width, height, cam);
				sx += dx3; ex += dx2;
				su += du3; eu += du2;
				sv += dv3; ev += dv2;
				sr += dr3; er += dr2;
		    	sg += dg3; eg += dg2;
		    	sb += db3; eb += db2;
		    	sa += da3; ea += da2;
			}
	    }
	}
	
	static void drawHLineAffine(int sx, int ex, int su, int eu, int sv, int ev, int sr, int er, int sg, int eg, int sb, int eb, int sa, int ea, int sy, int z,
								Texture img, int[] zBuffer, int width, int height, Camera camera) {
		int du = 0, dv = 0, dr = 0, dg = 0, db = 0, da = 0;
		int exsx = ex - sx;
		// calculate u, v and shade factor deltas needed to get u, v and shade factor values for each y position
		if (exsx > 0) {
			du = (eu-su) / (exsx);
			dv = (ev-sv) / (exsx);
			dr = (er - sr) / (exsx);
			dg = (eg - sg) / (exsx);
			db = (eb - sb) / (exsx);
			da = (ea - sa) / (exsx);
		}
		if(sy > 1 && sy < height-1) {
			for (int i = sx; i < ex; i++) {
				if(i > 1 && i < width-1) {
					// get texture pixel / texel color
					int color = img.getPixel(su >> SHIFT, sv >> SHIFT);
					int iColor = ColorUtils.convert(sr >> SHIFT, sg >> SHIFT, sb >> SHIFT, sa >> SHIFT);
					setPixel(i, sy, z, ColorUtils.lerpRBG(color, iColor, -255), zBuffer, width, camera);
				}
				su += du;
				sv += dv;
				sr += dr;
				sg += dg;
				sb += db;
				sa += da;
			}
		}
	}
}
