package com.johnsproject.jpge.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.Vector2Utils;
import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.Vector3Utils;
import com.johnsproject.jpge.utils.VertexUtils;

public class SceneRenderer{
	public enum ProjectionType {
		orthographic, perspective
	}	

	public enum RenderingType {
		wireframe, solid, textured
	}
	
	private int[] zBuffer = null;
	private int width = 0, height = 0;
	private static final int SHIFT = 20;
	private boolean log = true;
	
	public SceneRenderer(int width, int height) {
		zBuffer = new int[width*height];
		this.width = width;
		this.height = height;
	}
	
	public void render(Scene scene, int lastTime) {
		resetZBuffer();
		for (Camera camera : scene.getCameras()) {
			camera.getScreenGraphics().clearRect(0, 0, Vector2Utils.getX(camera.getRect()),  Vector2Utils.getY(camera.getRect()));
			int maxPolys = 0;
			for (SceneObject sceneObject : scene.getSceneObjects()) {
				if (sceneObject.changed() || camera.changed()) {
					render(sceneObject, camera, scene.getLights());
				}
				maxPolys += sceneObject.getMesh().getPolygons().length;
			}
			camera.changed(false);
			if(log) logData((Graphics2D)camera.getScreenGraphics(), maxPolys, lastTime);
			camera.drawBuffer();
		}
		renderedPolys = 0;
		for (SceneObject sceneObject : scene.getSceneObjects()) {
			sceneObject.changed(false);
		}
	}
	
	void render(SceneObject sceneObject, Camera camera, List<Light> lights) {
		Mesh mesh = sceneObject.getMesh();
		Animation animation = mesh.getCurrentAnimation();
		Transform objt = sceneObject.getTransform();
		mesh.resetBuffer();
		for (int i = 0; i < mesh.getVertexes().length; i++) {
			long vertex = mesh.getVertex(i);
			long vector = VertexUtils.getVector(vertex);
			vector = VectorMathUtils.movePointByScale(vector, objt.getScale());
			for (int j = 0; j < VertexUtils.getBoneIndex(vertex); j++) {
				Transform bone = animation.getBone(j, animation.getCurrentFrame());
				vector = VectorMathUtils.movePointByScale(vector, bone.getScale());
				vector = VectorMathUtils.movePointByAnglesXYZ(vector, bone.getRotation());
				vector = VectorMathUtils.add(vector, bone.getPosition());
			}
			vector = VectorMathUtils.movePointByAnglesXYZ(vector, objt.getRotation());
			vector = projectVertex(vector, objt.getPosition(), camera);
			vertex = VertexUtils.setVector(vertex, vector);
			//System.out.println(VertexUtils.toString(vertex));
			mesh.setBufferedVertex(i, vertex);
		}
		for (int[] polygon : mesh.getPolygons()) {
			if (!cullViewFrustum(polygon, mesh, camera)) {
				if (!cullBackface(polygon, mesh)) {
					draw(polygon, mesh, camera);
				}
			}
		}
	}

	long projectVertex(long vertex, long objectPosition, Camera camera) {
		long px = 0, py = 0, pz = 0;
		int fov = camera.getFieldOfView(), rescalef = camera.getRescaleFactor();
		long camRot = camera.getTransform().getRotation(),
				camPos = camera.getTransform().getPosition();
		long pos = VectorMathUtils.add(vertex, objectPosition);
		pos = VectorMathUtils.subtract(pos, camPos);
		pos = VectorMathUtils.movePointByAnglesXYZ(pos, camRot);
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			long z = (Vector3Utils.getZ(pos) + fov);
			if (z <= 0) z = 1;
			px = ((Vector3Utils.getX(pos)) * rescalef * fov) / z;
			py = ((Vector3Utils.getY(pos)) * rescalef * fov) / z;
			pz = z + (Vector3Utils.getZ(pos)<<1);
			break;
		case orthographic: // this projectionType ignores depth
			px = (Vector3Utils.getX(pos) * rescalef)>>5;
			py = (Vector3Utils.getY(pos) * rescalef)>>5;
			pz = Vector3Utils.getZ(pos) + Vector3Utils.getZ(objectPosition);
			break;
		}
		long x = (px) + Vector2Utils.getX(camera.getHalfRect()) + Vector3Utils.getX(objectPosition);
		long y = (py) + Vector2Utils.getY(camera.getHalfRect()) + Vector3Utils.getY(objectPosition);
		long z = pz;
		return Vector3Utils.convert(x, y, z);
	}
	
	boolean cullViewFrustum(int[] polygon, Mesh mesh, Camera camera) {
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
	boolean cullBackface(int[] polygon, Mesh mesh) {
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
	
	void draw(int[] polygon, Mesh mesh, Camera camera) {
		renderedPolys++;
		long vt1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		long vt2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
		long vt3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int uv1 = mesh.getUV(polygon[Mesh.UV_1]);
		int uv2 = mesh.getUV(polygon[Mesh.UV_2]);
		int uv3 = mesh.getUV(polygon[Mesh.UV_3]);
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
		   				tmp = u2; u2 = u1; u1 = tmp;}
		if (y2 > y3) { tmp = y3; y3 = y2; y2 = tmp; 
		   				tmp = x3; x3 = x2; x2 = tmp;
		   				tmp = v3; v3 = v2; v2 = tmp; 
		   				tmp = u3; u3 = u2; u2 = tmp;}
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;
		   				tmp = v2; v2 = v1; v1 = tmp; 
		   				tmp = u2; u2 = u1; u1 = tmp;}
		if (y2 == y1) y2 += 1;
		int z = z1;
		if (z1 > z2) z = z2;
	    if (z2 > z3) z = z3;
	    //System.out.println(VertexUtils.toString(vt1));
		if(camera.getRenderingType() == RenderingType.wireframe) {
			drawLine(x1, y1, x2, y2, z, color, camera);
			drawLine(x2, y2, x3, y3, z, color, camera);
			drawLine(x3, y3, x1, y1, z, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.solid) {
			drawPolygon(x1, y1, x2, y2, x3, y3, z, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.textured) {
			Texture img = mesh.getMaterial(0).getTexture();
			drawPolygonAffine(x1, y1, x2, y2, x3, y3, z, u1, v1, u2, v2, u3, v3, img, camera);
		}
	}
	
	void setPixel(int x, int y, int z, int color, Camera camera) {
		//if((x > 0 && y > 0) && (x < width && y < height)) {
			int camPos = camera.getScreenPosition();
			int pos = (x + Vector2Utils.getX(camPos)) + ((y + Vector2Utils.getY(camPos))*width);
			int pz = camera.getNearClippingPlane()-z;
			if(zBuffer[pos] < pz) {
				zBuffer[pos] = pz;
				camera.setPixel(x, y, color);
			}
		//}
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
			setPixel(x1, y1, z, color, camera);
			if (numerator > longest) {
				numerator -= longest;
				x1 += dx1; y1 += dy1; z += dz1;
			} else {
				x1 += dx2; y1 += dy2; z += dz2;
			}
		}
	}
	
	void drawPolygon(int x1, int y1, int x2, int y2 ,int x3, int y3, int z, int c, Camera cam) {
		int dx1 = 0, dx2 = 0, dx3 = 0;
		int y1y0 = y2-y1, y2y0 = y3-y1, y2y1 = y3-y2;
	    if (y1y0 > 0) {
	    	dx1=(((x2-x1)<<SHIFT)/(y1y0));
	    } else dx1=0;
	    if (y2y0 > 0) { 
	    	dx2=(((x3-x1)<<SHIFT)/(y2y0));
	    }else dx2=0;
	    if (y2y1 > 0) {
	    	dx3=(((x3-x2)<<SHIFT)/(y2y1));
	    } else dx3=0;
	    int sx = x1<<SHIFT, ex = x1<<SHIFT;
	    int sy = y1;
	    if (dx1 > dx2) {
		    for (; sy <= y2-1; sy++, sx += dx2, ex += dx1)
				drawHLine(sx >> SHIFT, ex >> SHIFT, sy, z, c, cam);
			ex = x2 << SHIFT;
			for (; sy <= y3; sy++, sx += dx2, ex += dx3)
				drawHLine(sx >> SHIFT, ex >> SHIFT, sy, z, c, cam);
	    }else {
	    	for (; sy <= y2-1; sy++, sx += dx1, ex += dx2)
	    		drawHLine(sx >> SHIFT, ex >> SHIFT, sy, z, c, cam);
	    	sx = x2 << SHIFT;
			for (; sy <= y3; sy++, sx += dx3, ex += dx2)
				drawHLine(sx >> SHIFT, ex >> SHIFT, sy, z, c, cam);
	    }
	}
	
	void drawHLine(int sx, int ex, int sy, int z, int color, Camera camera) {
		if(sy > 0 && sy < height) {
			for (int i = sx; i < ex; i ++) {
				if(i > 0 && i < width)setPixel(i, sy, z, color, camera);
			}
		}
	}
	
	void drawPolygonAffine(int x1, int y1, int x2, int y2, int x3, int y3, int z,
							int u1, int v1, int u2, int v2, int u3, int v3, Texture img, Camera cam) {
		int w = img.getWidth()-1, h = img.getHeight()-1;
		u1 = (u1*w)/100; u2 = (u2*w)/100; u3 = (u3*w)/100;
		v1 = (v1*h)/100; v2 = (v2*h)/100; v3 = (v3*h)/100;
		int dx1 = 0, dx2 = 0, dx3 = 0;
		int du1 = 0, du2 = 0, du3 = 0, du = 0;
		int dv1 = 0, dv2 = 0, dv3 = 0, dv = 0;
	    if (y2-y1 > 0) {
	    	dx1=(((x2-x1)<<SHIFT)/(y2-y1));
	    	du1=(((u2-u1)<<SHIFT)/(y2-y1));
	    	dv1=(((v2-v1)<<SHIFT)/(y2-y1));
	    } else dx1=du1=dv1=0;
	    if (y3-y1 > 0) {
	    	dx2=(((x3-x1)<<SHIFT)/(y3-y1));
	    	du2=(((u3-u1)<<SHIFT)/(y3-y1));
	    	dv2=(((v3-v1)<<SHIFT)/(y3-y1));
	    } else dx2=du2=dv2=0;
	    if (y3-y2 > 0) {
	    	dx3=(((x3-x2)<<SHIFT)/(y3-y2));
	    	du3=(((u3-u2)<<SHIFT)/(y3-y2));
	    	dv3=(((v3-v2)<<SHIFT)/(y3-y2));
	    } else dx3=du3=dv3=0;
	    int sx = x1<<SHIFT, sv = v1<<SHIFT, su = u1<<SHIFT,
	    		ex = x1<<SHIFT, eu = u1<<SHIFT, ev = v1<<SHIFT;
	    int sy = y1;
	    if (dx1 > dx2) {
		    for (; sy <= y2 - 1; sy++) {
		    	drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, du, eu, sv, dv, ev, img, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx1; eu += du1; ev += dv1;
			}
			ex = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, du, eu, sv, dv, ev, img, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx3; eu += du3; ev += dv3;
			}
	    }else{
			for (; sy <= y2 - 1; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, du, eu, sv, dv, ev, img, cam);
				sx += dx1; su += du1; sv += dv1;
				ex += dx2; eu += du2; ev += dv2;
			}
			sx = x2<<SHIFT;
			for (; sy <= y3; sy++) {
				drawHLineAffine(sx>>SHIFT, ex>>SHIFT, sy, z, su, du, eu, sv, dv, ev, img, cam);
				sx += dx3; su += du3; sv += dv3;
				ex += dx2; eu += du2; ev += dv2;
			}
	    }
	}
	
	void drawHLineAffine(int sx, int ex, int sy, int z, int su, int du, int eu, int sv, int dv, int ev, Texture img, Camera camera) {
		du = Math.abs(du); sv = Math.abs(sv);
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
	
	private int renderedPolys = 0;
	private final Color backColor = new Color(160, 160, 160, 160);
	void logData(Graphics2D buffer, int maxPolys, int lastTime) {
		 buffer.setColor(backColor);
		 buffer.fillRect(5, 38, 230, 50);
		 buffer.setColor(Color.WHITE);
		 int maxMem = Math.round(((Runtime.getRuntime().totalMemory()/ 1024) / 1024));
		 int freeMem = Math.round(((Runtime.getRuntime().freeMemory()/ 1024) / 1024));
		 buffer.drawString("Render Time : " + (lastTime / 1000000) + " ms", 10, 50);
		 //buffer.drawString("FPS : " + (1000/((lastTime / 1000000)+1)), 10, 50);
		 buffer.drawString("rendered Polygons : " + renderedPolys + " / " + maxPolys, 10, 65);
		 buffer.drawString("Memory usage : " + (maxMem - freeMem) + " / " + maxMem + " MB", 10, 80);
	}
}
