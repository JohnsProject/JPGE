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

	private static final int SHIFT = 20;
	
	/**
	 * Projects a vector to from world (3D) to screen (2D) coordinates based on the {@link ProjectionType}
	 * of the given {@link Camera}.
	 * 
	 * @param vector vector to be projected.
	 * @param objectPosition position of the {@link SceneObject} containing this vector.
	 *  If no there is no {@link SceneObject} set as 0.
	 * @param camera {@link Camera} this vector will be projected to.
	 * @return
	 */
	public static long project(long vector, long objectPosition, Camera camera) {
		long px = 0, py = 0, pz = 0;
		int fov = camera.getFieldOfView(), rescalef = camera.getScaleFactor();
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			long z = (Vector3Utils.getZ(vector) + fov);
			if (z <= 0) z = 1;
			px = ((Vector3Utils.getX(vector) * rescalef * fov)) / z;
			py = ((Vector3Utils.getY(vector) * rescalef * fov)) / z;
			pz = z + (Vector3Utils.getZ(vector)<<1);
			break;
		case orthographic: // this projectionType ignores depth
			px = (Vector3Utils.getX(vector) * rescalef)>>5;
			py = (Vector3Utils.getY(vector) * rescalef)>>5;
			pz = Vector3Utils.getZ(vector) + Vector3Utils.getZ(objectPosition);
			break;
		}
		long x = (px) + Vector2Utils.getX(camera.getHalfScreenSize()) + Vector3Utils.getX(objectPosition);
		long y = (py) + Vector2Utils.getY(camera.getHalfScreenSize()) + Vector3Utils.getY(objectPosition);
		long z = pz;
		return Vector3Utils.convert(x, y, z);
	}

	/**
	 * Applies the given {@link Animation} to the given vertex.
	 * 
	 * @param vertex vertex to animate.
	 * @param animation {@link Animation} to apply.
	 * @return animated vertex.
	 */
	public static long animate(long vertex, Animation animation) {
		long vector = VertexUtils.getVector(vertex);
		for (int j = 0; j <= VertexUtils.getBoneIndex(vertex); j++) {
			Transform bone = animation.getBone(j, animation.getCurrentFrame());
			vector = VectorMathUtils.movePointByScale(vector, bone.getScale());
			vector = VectorMathUtils.movePointByAnglesXYZ(vector, bone.getRotation());
			vector = VectorMathUtils.add(vector, bone.getPosition());
		}
		return VertexUtils.setVector(vertex, vector);
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
		long v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		int ncp = camera.getNearClippingPlane();
		int fcp = camera.getFarClippingPlane();
		int w = camera.getWidth(), h = camera.getHeight();
		int x = (int)Vector3Utils.getX(v1), y = (int)Vector3Utils.getY(v1), z = (int)Vector3Utils.getZ(v1);
		if (x > -400 && x < w+400 && y > -400 && y < h+400 && z > ncp && z < fcp)
			return false;
		return true;
	}

	//backface culling with sholeance algorithm
	/**
	 * This method checks if the given polygon is a backface.
	 * If this polygon is a backface then this method returns true else it returns false.
	 * 
	 * @param polygon a projected polygon.
	 * @param mesh {@link Mesh} that contains this polygon.
	 * @return if the given polygon is a backface.
	 */
	public static boolean isBackface(int[] polygon, Mesh mesh) {
		long v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]),
				v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]),
				v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int v1x = (int)Vector3Utils.getX(v1), v1y = (int)Vector3Utils.getY(v1);
		int v2x = (int)Vector3Utils.getX(v2), v2y = (int)Vector3Utils.getY(v2);
		int v3x = (int)Vector3Utils.getX(v3), v3y = (int)Vector3Utils.getY(v3);
		int a = ((v2x - v1x) * (v3y - v1y) - (v3x - v1x) * (v2y - v1y)) >> 1;
		if (a < 0) return false;
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
		int px = Vector2Utils.getX(camera.getScreenPosition());
		int py = Vector2Utils.getY(camera.getScreenPosition());
		int width = Vector2Utils.getX(camera.getScreenSize());
		int height = Vector2Utils.getY(camera.getScreenSize());
		long vt1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		long vt2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
		long vt3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int uv1 = mesh.getUV(polygon[Mesh.UV_1]);
		int uv2 = mesh.getUV(polygon[Mesh.UV_2]);
		int uv3 = mesh.getUV(polygon[Mesh.UV_3]);
		int sf1 = VertexUtils.getShadeFactor(vt1);
		int sf2 = VertexUtils.getShadeFactor(vt2);
		int sf3 = VertexUtils.getShadeFactor(vt3);
		int color = mesh.getMaterial(polygon[Mesh.MATERIAL_INDEX]).getColor();
		int tmp, x1 = Vector3Utils.getX(vt1), y1 = Vector3Utils.getY(vt1), z1 = Vector3Utils.getZ(vt1),
				x2 = Vector3Utils.getX(vt2), y2 = Vector3Utils.getY(vt2), z2 = Vector3Utils.getZ(vt2),
				x3 = Vector3Utils.getX(vt3), y3 = Vector3Utils.getY(vt3), z3 = Vector3Utils.getZ(vt3),
				u1 = Vector2Utils.getX(uv1), v1 = Vector2Utils.getY(uv1),
				u2 = Vector2Utils.getX(uv2), v2 = Vector2Utils.getY(uv2),
				u3 = Vector2Utils.getX(uv3), v3 = Vector2Utils.getY(uv3);
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
		if (y2 == y1) y2++;
		if (y3 == y1) y3++;
		int z = z1;
		if (z1 > z2) z = z2;
	    if (z2 > z3) z = z3;
	    if(camera.getRenderingType() == RenderingType.vertex) {
	    	if(y1 > 0 && y1 < height && x1 > 0 && x1 < width)
	    		setPixel(x1, y1, z1, color, px, py, zBuffer, width, camera);
	    	if(y2 > 0 && y2 < height && x2 > 0 && x2 < width)
	    		setPixel(x2, y2, z2, color, px, py, zBuffer, width, camera);
	    	if(y3 > 0 && y3 < height && x3 > 0 && x3 < width)
	    		setPixel(x3, y3, z3, color, px, py, zBuffer, width, camera);
		}
		if(camera.getRenderingType() == RenderingType.wireframe) {
			drawLine(x1, y1, x2, y2, z, color, px, py, zBuffer, width, height, camera);
			drawLine(x2, y2, x3, y3, z, color, px, py, zBuffer, width, height, camera);
			drawLine(x3, y3, x1, y1, z, color, px, py, zBuffer, width, height, camera);
		}
		if(camera.getRenderingType() == RenderingType.solid) {
			drawPolygon(x1, y1, sf1, x2, y2, sf2, x3, y3, sf3, px, py, zBuffer, width, height, z, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.textured) {
			Texture img = mesh.getMaterial(0).getTexture();
			drawPolygonAffine(x1, y1, x2, y2, x3, y3, z3, u1, v1, u2, v2, u3, v3, img, px, py, zBuffer, width, height, camera);
		}
	}
	
	static void setPixel(int x, int y, int z, int color, int cameraX, int cameraY, int[] zBuffer, int width, Camera camera) {
		int pos = (x + cameraX) + ((y + cameraY)*width);
		int pz = camera.getNearClippingPlane()-z;
		if(zBuffer[pos] < pz) {
			zBuffer[pos] = pz;
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
	
	static void drawPolygon(int x1, int y1, int sf1, int x2, int y2, int sf2, int x3, int y3, int sf3,
			int cameraX, int cameraY, int[] zBuffer, int width, int height,int z, int c, Camera cam) {
		int dx1 = 0, dx2 = 0, dx3 = 0; // x deltas
		int df1 = 0, df2 = 0, df3 = 0; // shade factor deltas
		//c = ColorUtils.darker(c, sf1);
		int y2y1 = y2-y1, y3y1 = y3-y1, y3y2 = y3-y2;
	    if (y2y1 > 0) { 
	    	dx1=(((x2-x1)<<SHIFT)/(y2y1));
	    	df1=(((sf2-sf1)<<SHIFT)/(y2y1)); 
	    } else dx1=0;
	    if (y3y1 > 0) { 
	    	dx2=(((x3-x1)<<SHIFT)/(y3y1)); 
	    	df2=(((sf3-sf1)<<SHIFT)/(y2y1)); 
	    }else dx2=0;
	    if (y3y2 > 0) { 
	    	dx3=(((x3-x2)<<SHIFT)/(y3y2)); 
	    	df3=(((sf3-sf2)<<SHIFT)/(y2y1)); 
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
	
	static void drawPolygonAffine(int x1, int y1, int x2, int y2, int x3, int y3, int z, int u1, int v1, int u2, int v2, int u3, int v3,
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
		    	drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx1; eu += du1; ev += dv1;
			}
			ex = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx3; eu += du3; ev += dv3;
			}
	    }else{
			for (; sy <= y2 - 1; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx1; su += du1; sv += dv1;
				ex += dx2; eu += du2; ev += dv2;
			}
			sx = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cameraX, cameraY, zBuffer, width, height, cam);
				sx += dx3; su += du3; sv += dv3;
				ex += dx2; eu += du2; ev += dv2;
			}
	    }
	}
	
	static void drawHLineAffine(int sx, int ex, int sy, int z, int su, int eu, int sv, int ev,
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
