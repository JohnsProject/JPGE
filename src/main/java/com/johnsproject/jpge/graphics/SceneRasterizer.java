package com.johnsproject.jpge.graphics;

import java.awt.Color;
import java.awt.Graphics;

import com.johnsproject.jpge.graphics.SceneRenderer.RenderingType;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.Vector2Utils;
import com.johnsproject.jpge.utils.Vector3Utils;
import com.johnsproject.jpge.utils.VertexUtils;

/**
 * The SceneRasterizer class draws the {@link Scene} assigned to the {@link SceneFrame}.
 * It takes all the {@link SceneObject SceneObjects} transformed and projected by the 
 * {@link SceneRenderer} and draws them into the view buffer of all {@link Camera Camera}
 * in the {@link Scene}.
 *
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class SceneRasterizer {

	private int[] zBuffer = null;
	private int width = 0, height = 0;
	private static final int SHIFT = 20;
	
	public SceneRasterizer(int width, int height) {
		zBuffer = new int[width*height];
		this.width = width;
		this.height = height;
	}
	
	void draw(Scene scene, int lastRasterizeTime, int lastRenderTime) {
		resetZBuffer();
		for (Camera camera : scene.getCameras()) {
			camera.getViewGraphics().clearRect(0, 0, Vector2Utils.getX(camera.getScreenSize()),  Vector2Utils.getY(camera.getScreenSize()));
			int maxPolys = 0, renderedPolys = 0;
			for (SceneObject obj : scene.getSceneObjects()) {
				if (obj.isActive()) {
					Mesh mesh = obj.getMesh();
					for (int[] polygon : mesh.getPolygons()) {
						if(polygon[Mesh.CULLED] == 0) {
							drawPolygon(polygon, mesh, camera);
							renderedPolys++;
						}
						maxPolys++;
					}
				}
			}
			logData(camera, renderedPolys, maxPolys, lastRasterizeTime, lastRenderTime);
			camera.drawBuffer();
		}
	}
	
	void drawPolygon(int[] polygon, Mesh mesh, Camera camera) {
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
	    		setPixel(x1, y1, z1, color, camera);
	    	if(y2 > 0 && y2 < height && x2 > 0 && x2 < width)
	    		setPixel(x2, y2, z2, color, camera);
	    	if(y3 > 0 && y3 < height && x3 > 0 && x3 < width)
	    		setPixel(x3, y3, z3, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.wireframe) {
			drawLine(x1, y1, x2, y2, z, color, camera);
			drawLine(x2, y2, x3, y3, z, color, camera);
			drawLine(x3, y3, x1, y1, z, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.solid) {
			drawPolygon(x1, y1, sf1, x2, y2, sf2, x3, y3, sf3, z, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.textured) {
			Texture img = mesh.getMaterial(0).getTexture();
			drawPolygonAffine(x1, y1, x2, y2, x3, y3, z, u1, v1, u2, v2, u3, v3, img, camera);
		}
	}
	
	void setPixel(int x, int y, int z, int color, Camera camera) {
		int camPos = camera.getScreenPosition();
		int pos = (x + Vector2Utils.getX(camPos)) + ((y + Vector2Utils.getY(camPos))*width);
		int pz = camera.getNearClippingPlane()-z;
		if(zBuffer[pos] < pz) {
			zBuffer[pos] = pz;
			camera.setPixel(x, y, color);
		}
	}
	
	public void resetZBuffer() {
		for (int i = 0; i < zBuffer.length; i++) {
			zBuffer[i] = Integer.MIN_VALUE;
		}
	}
	
	void drawLine(int x1, int y1, int x2, int y2, int z, int color, Camera camera) {
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
			if(y1 > 1 && y1 < height-1 && x1 > 1 && x1 < width-1) {
				setPixel(x1, y1, z, color, camera);
				if (numerator > longest) {
					numerator -= longest;
					x1 += dx1; y1 += dy1; z += dz1;
				} else {
					x1 += dx2; y1 += dy2; z += dz2;
				}
			}
		}
	}
	
	void drawPolygon(int x1, int y1, int sf1, int x2, int y2, int sf2 ,int x3, int y3, int sf3, int z, int c, Camera cam) {
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
				drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cam);
			ex = x2 << SHIFT;
			for (; sy <= y3; sy++, sx += dx2, ex += dx3, sf += df2, ef += df3)
				drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cam);
	    }else {
	    	for (; sy <= y2-1; sy++, sx += dx1, ex += dx2, sf += df1, ef += df2)
	    		drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cam);
	    	sx = x2 << SHIFT;
			for (; sy <= y3; sy++, sx += dx3, ex += dx2, sf += df3, ef += df2)
				drawHLine(sx >> SHIFT, ex >> SHIFT, sf, ef, sy, z, c, cam);
	    }
	}
	
	void drawHLine(int sx, int ex, int sf, int ef, int sy, int z, int color, Camera camera) {
		int df = 0;
		if (ex-sx > 0) { df = (ef-sf)/(ex-sx); }
		if(sy > 0 && sy < height) {
			for (int i = sx; i < ex; i ++, sf += df) {
				if(i > 0 && i < width) setPixel(i, sy, z, ColorUtils.darker(color, sf >> SHIFT), camera);
			}
		}
	}
	
	void drawPolygonAffine(int x1, int y1, int x2, int y2, int x3, int y3, int z,
							int u1, int v1, int u2, int v2, int u3, int v3, Texture img, Camera cam) {
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
		    	drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx1; eu += du1; ev += dv1;
			}
			ex = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx3; eu += du3; ev += dv3;
			}
	    }else{
			for (; sy <= y2 - 1; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cam);
				sx += dx1; su += du1; sv += dv1;
				ex += dx2; eu += du2; ev += dv2;
			}
			sx = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, eu, sv, ev, img, cam);
				sx += dx3; su += du3; sv += dv3;
				ex += dx2; eu += du2; ev += dv2;
			}
	    }
	}
	
	void drawHLineAffine(int sx, int ex, int sy, int z, int su, int eu, int sv, int ev, Texture img, Camera camera) {
		int du = 0, dv = 0;
		su = Math.abs(su); sv = Math.abs(sv);
		if (ex-sx > 0) {
			du = (eu-su)/(ex-sx);
			dv = (ev-sv)/(ex-sx);
		}
		if(sy > 0 && sy < height) {
			for (int i = sx; i < ex; i++, su += du, sv += dv) {
				if(i > 0 && i < width)setPixel(i, sy, z, img.getPixel(su>>SHIFT, sv>>SHIFT), camera);
			}
		}
	}

	int afps = 1, cycles = 1;
	private final Color backColor = new Color(160, 160, 160, 160);
	void logData(Camera camera, int renderedPolys, int maxPolys, int lastRasterizeTime, int lastRenderTime) {
		Graphics buffer = camera.getViewGraphics();
		 buffer.setColor(backColor);
		 buffer.fillRect(5, 38, 230, 140);
		 buffer.setColor(Color.WHITE);
		 int maxMem = Math.round(Runtime.getRuntime().totalMemory() >> 20);
		 int freeMem = Math.round(Runtime.getRuntime().freeMemory() >> 20);
		 int fps  = 1000/(((lastRenderTime+lastRasterizeTime) / 1000000)+1);
		 int y = 50, step = 17;
		 buffer.drawString("Average FPS : " + (afps/cycles) + " , FPS : " + fps, 10, y);
		 afps += fps;
		 cycles++;
		 resetAFPS();
		 y += step;
		 buffer.drawString("Render Time : " + (lastRenderTime / 1000000) + " ms", 10, y);
		 y += step;
		 buffer.drawString("Rasterize Time : " + (lastRasterizeTime / 1000000) + " ms", 10, y);
		 y += step;
		 buffer.drawString("Rendered Polygons : " + renderedPolys + " / " + maxPolys, 10, y);
		 y += step;
		 buffer.drawString("Rendering resolution : " + camera.getWidth() + "x" + camera.getHeight(), 10, y);
		 y += step;
		 buffer.drawString("Memory usage : " + (maxMem - freeMem) + " / " + maxMem + " MB", 10, y);
		 y += step;
		 buffer.drawString("Projection type : " + camera.getProjectionType().toString(), 10, y);
		 y += step;
		 buffer.drawString("Rendering type : " + camera.getRenderingType().toString(), 10, y);
	}
	
	void resetAFPS() {
		if (cycles > 1000) {
			afps = afps/cycles;
			cycles = 1;
		}
	}
}
