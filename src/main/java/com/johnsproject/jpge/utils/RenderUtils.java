package com.johnsproject.jpge.utils;

import com.johnsproject.jpge.graphics.Animation;
import com.johnsproject.jpge.graphics.Camera;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.SceneObject;
import com.johnsproject.jpge.graphics.Texture;
import com.johnsproject.jpge.graphics.Transform;
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
	 * @param vector vector to be projected.
	 * @param objectPosition position of the {@link SceneObject} containing this vector.
	 *  If no there is no {@link SceneObject} set as 0.
	 * @param camera {@link Camera} this vector will be projected to.
	 * @return projected vector.
	 */
	public static int[] project(int[] vector, int[] objectPosition, Camera camera) {
		int px = vector[vx];
		int py = vector[vy];
		int pz = vector[vz];
		int fov = camera.getFieldOfView();
		int rescalef = camera.getScaleFactor();
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			int z = (pz + fov);
			if (z <= 0) z = 1;
			px = ((px * rescalef * fov)) / z;
			py = ((py * rescalef * fov)) / z;
			pz = z + (pz << 1);
			break;
		case orthographic: // this projectionType ignores depth
			px = (px * rescalef)>>5;
			py = (py * rescalef)>>5;
			pz = pz + objectPosition[vz];
			break;
		}
		vector[vx] = (px) + camera.getHalfScreenSize()[vx] + objectPosition[vx];
		vector[vy] = (py) + camera.getHalfScreenSize()[vy] + objectPosition[vy];
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
	public static int[] animate(int[] vertex, Animation animation) {
		//for (int j = 0; j <= VertexUtils.getBoneIndex(vertex); j++) {
			Transform bone = animation.getBone(vertex[Mesh.BONE_INDEX], animation.getCurrentFrame());
			//Transform bone = animation.getBone(i, animation.getCurrentFrame());
			vertex = Vector3MathUtils.subtract(vertex, bone.getPosition());
			vertex = Vector3MathUtils.movePointByScale(vertex, bone.getScale());
			vertex = Vector3MathUtils.movePointByAnglesXYZ(vertex, bone.getRotation());
			vertex = Vector3MathUtils.add(vertex, bone.getPosition());
		//}
		return vertex;
	}
	
	/**
	 * This method checks if the given polygon is inside the view frustum of the given camera.
	 * 
	 * @param polygon a projected polygon.
	 * @param mesh {@link Mesh} that contains this polygon.
	 * @param camera {@link Camera}.
	 * @return if the given polygon is inside the view frustum of the given camera.
	 */
	public static boolean isInsideViewFrustum(int[] polygon, Mesh mesh, Camera camera) {
		polygon[Mesh.CULLED] = 0;
		int[] v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		int ncp = camera.getNearClippingPlane();
		int fcp = camera.getFarClippingPlane();
		int w = camera.getWidth();
		int h = camera.getHeight();
		int x = v1[vx];
		int y = v1[vy];
		int z = v1[vz];
		if (x > -400 && x < w+400 && y > -400 && y < h+400 && z > ncp && z < fcp) {
			return false;
		}
		polygon[Mesh.CULLED] = 1;
		return true;
	}

	/**
	 * This method checks if the given polygon is a backface.
	 * If this polygon is a backface then this method returns true else it returns false.
	 * 
	 * @param polygon a projected polygon.
	 * @param mesh {@link Mesh} that contains this polygon.
	 * @return if the given polygon is a backface.
	 */
	//backface culling with sholeance algorithm
	public static boolean isBackface(int[] polygon, Mesh mesh) {
		polygon[Mesh.CULLED] = 0;
		int[] v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		int[] v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
		int[] v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int v1x = v1[vx], v1y = v1[vy];
		int v2x = v2[vx], v2y = v2[vy];
		int v3x = v3[vx], v3y = v3[vy];
		int a = ((v2x - v1x) * (v3y - v1y) - (v3x - v1x) * (v2y - v1y)) >> 1;
		if (a < 0) return false;
		polygon[Mesh.CULLED] = 1;
		return true;
	}
	
	/**
	 * Draws the given polygon to the given {@link Camera} based on the {@link RenderingType} of the {@link Camera}.
	 * 
	 * @param polygon polygon to draw.
	 * @param mesh {@link Mesh} that contains the polygon.
	 * @param zBuffer depth buffer.
	 * @param camera {@link Camera} to draw.
	 */
	public static void drawPolygon(int[] polygon, Mesh mesh, int[] zBuffer, Camera camera) {
		int px = camera.getScreenPosition()[vx];
		int py = camera.getScreenPosition()[vy];
		int width = camera.getScreenSize()[vx];
		int height = camera.getScreenSize()[vy];
		int[] vt1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		int[] vt2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
		int[] vt3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int[] uv1 = mesh.getUV(polygon[Mesh.UV_1]);
		int[] uv2 = mesh.getUV(polygon[Mesh.UV_2]);
		int[] uv3 = mesh.getUV(polygon[Mesh.UV_3]);
		int sf1 = vt1[Mesh.SHADE_FACTOR];
		int sf2 = vt2[Mesh.SHADE_FACTOR];
		int sf3 = vt3[Mesh.SHADE_FACTOR];
		int color = mesh.getMaterial(polygon[Mesh.MATERIAL_INDEX]).getColor();
		int tmp = 0;
		int x1 = vt1[vx], y1 = vt1[vy], z1 = vt1[vz],
			x2 = vt2[vx], y2 = vt2[vy], z2 = vt2[vz],
			x3 = vt3[vx], y3 = vt3[vy], z3 = vt3[vz];
		int u1 = uv1[vx], v1 = uv1[vy],
			u2 = uv2[vx], v2 = uv2[vy],
			u3 = uv3[vx], v3 = uv3[vy];
		// y sorting
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;
		   				tmp = v2; v2 = v1; v1 = tmp; 
		   				tmp = u2; u2 = u1; u1 = tmp;
		   				tmp = sf2; sf2 = sf1; sf1 = tmp;}
		if (y2 > y3) { tmp = y3; y3 = y2; y2 = tmp; 
		   				tmp = x3; x3 = x2; x2 = tmp;
		   				tmp = v3; v3 = v2; v2 = tmp; 
		   				tmp = u3; u3 = u2; u2 = tmp;
		   				tmp = sf3; sf3 = sf2; sf2 = tmp;}
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;
		   				tmp = v2; v2 = v1; v1 = tmp; 
		   				tmp = u2; u2 = u1; u1 = tmp;
		   				tmp = sf2; sf2 = sf1; sf1 = tmp;}
		// hack to fix fill bug
		if (y2 == y1) y2++;
		if (y3 == y1) y3++;
		// get smallest z value for z buffering
		int z = z1;
		if (z1 > z2) z = z2;
	    if (z2 > z3) z = z3;
	    z = camera.getNearClippingPlane() - z;
	    // draw
	    switch (camera.getRenderingType()) {
		case vertex:
			if(y1 > 0 && y1 < height && x1 > 0 && x1 < width)
	    		setPixel(x1, y1, z1, color, px, py, zBuffer, width, camera);
	    	if(y2 > 0 && y2 < height && x2 > 0 && x2 < width)
	    		setPixel(x2, y2, z2, color, px, py, zBuffer, width, camera);
	    	if(y3 > 0 && y3 < height && x3 > 0 && x3 < width)
	    		setPixel(x3, y3, z3, color, px, py, zBuffer, width, camera);
			break;
			
		case wireframe:
			drawLine(x1, y1, x2, y2, z, color, px, py, zBuffer, width, height, camera);
			drawLine(x2, y2, x3, y3, z, color, px, py, zBuffer, width, height, camera);
			drawLine(x3, y3, x1, y1, z, color, px, py, zBuffer, width, height, camera);
			break;
			
		case solid:
			drawPolygon(x1, y1, sf1, x2, y2, sf2, x3, y3, sf3, z, px, py, zBuffer, width, height, color, camera);
			break;
			
		case textured:
			Texture img = mesh.getMaterial(0).getTexture();
			drawPolygonAffine(x1, y1, x2, y2, x3, y3, z, u1, v1, u2, v2, u3, v3, sf1, img, px, py, zBuffer, width, height, camera);
			break;
		}
	}
	
	static void setPixel(int x, int y, int z, int color, int cameraX, int cameraY, int[] zBuffer, int width, Camera camera) {
		int pos = (x + cameraX) + ((y + cameraY)*width);
		if(zBuffer[pos] < z) {
			zBuffer[pos] = z;
			camera.setPixel(x, y, color);
		}
	}
	
	static void drawLine(int x1, int y1, int x2, int y2, int z, int color, int cameraX, int cameraY, int[] zBuffer, int width, int height, Camera camera) {
		int w = x2 - x1;
		int h = y2 - y1;
		int dx1 = 0, dy1 = 0, dz1 = 0, dx2 = 0, dy2 = 0, dz2 = 0;
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
			if(y1 > 0 && y1 < height && x1 > 0 && x1 < width) {
				setPixel(x1, y1, z, color, cameraX, cameraY, zBuffer, width, camera);
				if (numerator > longest) {
					numerator -= longest;
					x1 += dx1; y1 += dy1; z += dz1;
				} else {
					x1 += dx2; y1 += dy2; z += dz2;
				}
			}
		}
	}
	
	static void drawPolygon(int x1, int y1, int sf1, int x2, int y2, int sf2, int x3, int y3, int sf3, int z,
			int cameraX, int cameraY, int[] zBuffer, int width, int height, int c, Camera cam) {
		int dx1 = 0, dx2 = 0, dx3 = 0; // x deltas
		int df1 = 0, df2 = 0, df3 = 0; // shade factor deltas
		c = ColorUtils.darker(c, sf1);
		int y2y1 = y2-y1, y3y1 = y3-y1, y3y2 = y3-y2;
	    if (y2y1 > 0) { 
	    	dx1=(((x2-x1)<<SHIFT)/(y2y1));
	    	df1=(((sf2-sf1)<<SHIFT)/(y2y1)); 
	    } else dx1=0;
	    if (y3y1 > 0) { 
	    	dx2=(((x3-x1)<<SHIFT)/(y3y1)); 
	    	df2=(((sf3-sf1)<<SHIFT)/(y3y1)); 
	    }else dx2=0;
	    if (y3y2 > 0) { 
	    	dx3=(((x3-x2)<<SHIFT)/(y3y2)); 
	    	df3=(((sf3-sf2)<<SHIFT)/(y3y2)); 
	    } else dx3=0;
	    int sx = x1 <<SHIFT, ex = x1 <<SHIFT;
	    int sf = sf1 <<SHIFT, ef = sf1 <<SHIFT;
	    int sy = y1;
	    if (dx1 > dx2) {
		    for (; sy <= y2-1; sy++, sx += dx2, ex += dx1, sf += df2, ef += df1)
		    	drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cameraX, cameraY, zBuffer, width, height, cam);
			ex = x2 << SHIFT;
			for (; sy <= y3; sy++, sx += dx2, ex += dx3, sf += df2, ef += df3)
				drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cameraX, cameraY, zBuffer, width, height, cam);
	    }else {
	    	for (; sy <= y2-1; sy++, sx += dx1, ex += dx2, sf += df1, ef += df2)
	    		drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cameraX, cameraY, zBuffer, width, height, cam);
	    	sx = x2 << SHIFT;
			for (; sy <= y3; sy++, sx += dx3, ex += dx2, sf += df3, ef += df2)
				drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cameraX, cameraY, zBuffer, width, height, cam);
	    }
	}
	
	static void drawHLine(int sx, int ex, int sf, int ef, int sy, int z, int color, int cameraX, int cameraY, int[] zBuffer, int width, int height, Camera camera) {
		int df = 0;
		if (ex-sx > 0) { df = (ef-sf)/(ex-sx); }
		if(sy > 0 && sy < height) {
			for (int i = sx; i < ex; i ++, sf += df) {
				if(i > 0 && i < width) 
					setPixel(i, sy, z, color, cameraX, cameraY, zBuffer, width, camera);
			}
		}
	}
	
	static void drawPolygonAffine(int x1, int y1, int x2, int y2, int x3, int y3, int z, int u1, int v1, int u2, int v2, int u3, int v3, int sf,
							Texture img, int cameraX, int cameraY, int[] zBuffer, int width, int height, Camera cam) {
		int w = img.getWidth()-1, h = img.getHeight()-1;
		u1 = (u1*w)>>7; u2 = (u2*w)>>7; u3 = (u3*w)>>7;
		v1 = (v1*h)>>7; v2 = (v2*h)>>7; v3 = (v3*h)>>7;
		int dx1 = 0, dx2 = 0, dx3 = 0;
		int du1 = 0, du2 = 0, du3 = 0;
		int dv1 = 0, dv2 = 0, dv3 = 0;
		int y2y1 = y2-y1, y3y1 = y3-y1, y3y2 = y3-y2;
	    if (y2y1 > 0) {
	    	dx1=(((x2-x1)<<SHIFT)/(y2y1));
	    	du1=(((u2-u1)<<SHIFT)/(y2y1));
	    	dv1=(((v2-v1)<<SHIFT)/(y2y1));
	    } else dx1=du1=dv1=0;
	    if (y3y1 > 0) {
	    	dx2=(((x3-x1)<<SHIFT)/(y3y1));
	    	du2=(((u3-u1)<<SHIFT)/(y3y1));
	    	dv2=(((v3-v1)<<SHIFT)/(y3y1));
	    } else dx2=du2=dv2=0;
	    if (y3y2 > 0) {
	    	dx3=(((x3-x2)<<SHIFT)/(y3y2));
	    	du3=(((u3-u2)<<SHIFT)/(y3y2));
	    	dv3=(((v3-v2)<<SHIFT)/(y3y2));
	    } else dx3=du3=dv3=0;
	    int sx = x1<<SHIFT, sv = v1<<SHIFT, su = u1<<SHIFT,
	    	ex = x1<<SHIFT, eu = u1<<SHIFT, ev = v1<<SHIFT;
	    int sy = y1;
	    if (dx1 > dx2) {
		    for (; sy <= y2 - 1; sy++) {
		    	drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, sf, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx1; eu += du1; ev += dv1;
			}
			ex = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, sf, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx3; eu += du3; ev += dv3;
			}
	    }else{
			for (; sy <= y2 - 1; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, sf, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx1; su += du1; sv += dv1;
				ex += dx2; eu += du2; ev += dv2;
			}
			sx = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, sf, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx3; su += du3; sv += dv3;
				ex += dx2; eu += du2; ev += dv2;
			}
	    }
	}
	
	static void drawHLineAffine(int sx, int ex, int sy, int z, int su, int eu, int sv, int ev, int sf,
			Texture img, int cameraX, int cameraY, int[] zBuffer, int width, int height, Camera camera) {
		int du = 0, dv = 0;
		su = Math.abs(su); sv = Math.abs(sv);
		if (ex-sx > 0) {
			du = (eu-su)/(ex-sx);
			dv = (ev-sv)/(ex-sx);
		}
		if(sy > 0 && sy < height) {
			for (int i = sx; i < ex; i++, su += du, sv += dv) {
				if(i > 0 && i < width)
					setPixel(i, sy, z, img.getPixel(su>>SHIFT, sv>>SHIFT), cameraX, cameraY, zBuffer, width, camera);
			}
		}
	}

}
