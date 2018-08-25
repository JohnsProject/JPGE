package com.johnsproject.jpge.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.List;

import com.johnsproject.jpge.io.FileIO;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.UVUtils;
import com.johnsproject.jpge.utils.Vector3MathUtils;
import com.johnsproject.jpge.utils.Vector3Utils;
import com.johnsproject.jpge.utils.VertexUtils;

public class SceneRenderer{
	public enum ProjectionType {
		orthographic, perspective
	}	

	public enum RenderingType {
		wireframe, solid, textured
	}
	
	private static final int vx = Vector3Utils.X, vy = Vector3Utils.Y, vz = Vector3Utils.Z;
	private boolean log = true;
	
	public SceneRenderer() {
		try {
//			img = convertTo2DWithoutUsingGetRGB(
//					new Image("/home/john/Development/Java/Workspace/JPGE/bin/ButtonTexture.png").getBufferedImage());
			//img = new Image("/home/john/Development/JohnsProjectLogo.png");
			img = new Image(FileIO.loadImage(getClass().getResourceAsStream("/JohnsProjectLogo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Scene scene, int lastTime) {
		for (Camera camera : scene.getCameras()) {
			camera.reset();
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
	
	void drawPolygonAffine(long vx1, long vx2, long vx3, int uv1, int uv2, int uv3, Image img, Camera cam) {
		float w = img.getWidth(), h = img.getHeight();
		int tmp, x0 = (int)Vector3Utils.getX(vx1), y0 = (int)Vector3Utils.getY(vx1), z0 = (int)Vector3Utils.getZ(vx1),
				x1 = (int)Vector3Utils.getX(vx2), y1 = (int)Vector3Utils.getY(vx2), z1 = (int)Vector3Utils.getZ(vx2),
				x2 = (int)Vector3Utils.getX(vx3), y2 = (int)Vector3Utils.getY(vx3), z2 = (int)Vector3Utils.getZ(vx3),
				u0 = UVUtils.getU(uv1), v0 = UVUtils.getV(uv1),
				u1 = UVUtils.getU(uv2), v1 = UVUtils.getV(uv2),
				u2 = UVUtils.getU(uv3), v2 = UVUtils.getV(uv3);
		if (y0 > y1) { tmp = y1; y1 = y0; y0 = tmp; 
		   				tmp = x1; x1 = x0; x0 = tmp; }
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;}
		if (y0 > y1) { tmp = y1; y1 = y0; y0 = tmp; 
		   				tmp = x1; x1 = x0; x0 = tmp; }
		if (v0 > v1) { tmp = v1; v1 = v0; v0 = tmp; 
						tmp = u1; u1 = u0; u0 = tmp; }
		if (v1 > v2) { tmp = v2; v2 = v1; v1 = tmp; 
						tmp = u2; u2 = u1; u1 = tmp;}
		if (v0 > v1) { tmp = v1; v1 = v0; v0 = tmp; 
						tmp = u1; u1 = u0; u0 = tmp; }
		float dx1 = 0, dx2 = 0, dx3 = 0;
		float du1 = 0, du2 = 0, du3 = 0, du = 0;
		float dv1 = 0, dv2 = 0, dv3 = 0, dv = 0;
	    if (y1-y0 > 0) {
	    	dx1=((float)(x1-x0)/(float)(y1-y0));
	    	du1=((float)(u1-u0)/(float)(y1-y0));
	    	dv1=((float)(v1-v0)/(float)(y1-y0));
	    } else dx1=du1=dv1=0;
	    if (y2-y0 > 0) {
	    	dx2=((float)(x2-x0)/(float)(y2-y0));
	    	du2=((float)(u2-u0)/(float)(y2-y0));
	    	dv2=((float)(v2-v0)/(float)(y2-y0));
	    } else dx2=du2=dv2=0;
	    if (y2-y1 > 0) {
	    	dx3=((float)(x2-x1)/(float)(y2-y1));
	    	du3=((float)(u2-u1)/(float)(y2-y1));
	    	dv3=((float)(v2-v1)/(float)(y2-y1));
	    } else dx3=du3=dv3=0;
	    float iu = 0, iv = 0;
	    iv = Math.abs((h-1)/(float)(v2-v0));
	    iu = Math.abs((w-1)/(float)(u2-u0));
	    if (iu == Float.POSITIVE_INFINITY) iu = 0;
	    float sx = x0, sv = v0, su = u0,
	    		ex = x0, eu = u0, ev = v0;
	    int sy = (int)y0;
//		if (dx2 - dx1 > 0) {
//			du = (float)(eu-su)/(float)(dx2-dx1);
//			dv = (float)(ev-sv)/(float)(dx2-dx1);
//		}
	    if(dx1 > dx2) {
			for (; sy <= y1 - 1; sy++) {
				drawHLineText((int)sx, (int)ex, sy, z0, su, du, iu, eu, sv, dv, iv, ev, img, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx1; eu += du1; ev += dv1;
			}
			ex = x1;
			for (; sy <= y2; sy++) {
				drawHLineText((int)sx, (int)ex, sy, z0, su, du, iu, eu, sv, dv, iv, ev, img, cam);
				sx += dx2; su += du2; sv += dv2;
				ex += dx3; eu += du3; ev += dv3;
			}
	    }else {
	    	for (; sy <= y1 - 1; sy++) {
	    		drawHLineText((int)sx, (int)ex, sy, z0, su, du, iu, eu, sv, dv, iv, ev, img, cam);
				sx += dx1; su += du1; sv += dv1;
				ex += dx2; eu += du2; ev += dv2;
			}
			sx = x1;
			for (; sy <= y2; sy++) {
				drawHLineText((int)sx, (int)ex, sy, z0, su, du, iu, eu, sv, dv, iv, ev, img, cam);
				sx += dx3; su += du3; sv += dv3;
				ex += dx2; eu += du2; ev += dv2;
			}
	    }
	}
	
	void drawPolygon(long vx1, long vx2, long vx3, int c, Camera cam) {
		int tmp, x0 = (int)Vector3Utils.getX(vx1), y0 = (int)Vector3Utils.getY(vx1), z0 = (int)Vector3Utils.getZ(vx1),
				x1 = (int)Vector3Utils.getX(vx2), y1 = (int)Vector3Utils.getY(vx2),
				x2 = (int)Vector3Utils.getX(vx3), y2 = (int)Vector3Utils.getY(vx3);
		if (y0 > y1) { tmp = y1; y1 = y0; y0 = tmp; 
		   				tmp = x1; x1 = x0; x0 = tmp; }
		if (y1 > y2) { tmp = y2; y2 = y1; y1 = tmp; 
		   				tmp = x2; x2 = x1; x1 = tmp;}
		if (y0 > y1) { tmp = y1; y1 = y0; y0 = tmp; 
		   				tmp = x1; x1 = x0; x0 = tmp; }
		int dx1 = 0, dx2 = 0, dx3 = 0, shift = 10;
		int y1y0 = y1-y0, y2y0 = y2-y0, y2y1 = y2-y1;
	    if (y1y0 > 0) dx1=(((x1-x0)<<shift)/(y1y0)); else dx1=0;
	    if (y2y0 > 0) dx2=(((x2-x0)<<shift)/(y2y0)); else dx2=0;
	    if (y2y1 > 0) dx3=(((x2-x1)<<shift)/(y2y1)); else dx3=0;
	    int x_left = x0<<shift, x_right = x0<<shift;
	    int sy = y0;
	    for (; sy <= y1 - 1; sy++, x_left += dx2, x_right += dx1)
			drawHLine(x_left >> shift, x_right >> shift, sy, z0, c, cam);
		x_right = x1 << shift;
		for (; sy <= y2; sy++, x_left += dx2, x_right += dx3)
			drawHLine(x_left >> shift, x_right >> shift, sy, z0, c, cam);
		x_left = x0<<shift; x_right = x0<<shift; sy = y0;
    	for (; sy <= y1 - 1; sy++, x_left += dx1, x_right += dx2)
			drawHLine(x_left >> shift, x_right >> shift, sy, z0, c, cam);
    	x_left = x1 << shift;
		for (; sy <= y2; sy++, x_left += dx3, x_right += dx2)
			drawHLine(x_left >> shift, x_right >> shift, sy, z0, c, cam);
	}
	
	void drawLine(long v1, long v2, int color, Camera camera) {
		int w = (int)Vector3Utils.getX(v2) - (int)Vector3Utils.getX(v1);
		int h = (int)Vector3Utils.getY(v2) - (int)Vector3Utils.getY(v1);
		int d = (int)Vector3Utils.getZ(v2) - (int)Vector3Utils.getZ(v1);
		int dx1 = 0, dy1 = 0, dz1 = 0, dx2 = 0, dy2 = 0, dz2 = 0;
		if (w < 0) dx1 = -1; else if (w > 0) dx1 = 1;
		if (h < 0) dy1 = -1; else if (h > 0) dy1 = 1;
		if (w < 0) dx2 = -1; else if (w > 0) dx2 = 1;
		if (d < 0) dz1 = -1; else if (d > 0) dz1 = 1;
		if (d < 0) dz2 = -1; else if (d > 0) dz2 = 1;
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
		int x = (int)Vector3Utils.getX(v1),
				y = (int)Vector3Utils.getY(v1),
				z = (int)Vector3Utils.getZ(v1);
		for (int i = 0; i < longest; i++, numerator += shortest) {
			camera.setPixel(x, y, z, color);
			if (numerator > longest) {
				numerator -= longest;
				x += dx1; y += dy1; z += dz1;
			} else {
				x += dx2; y += dy2; z += dz2;
			}
		}
	}

	void drawHLine(int x1, int x2, int y, int z, int color, Camera camera) {
		for (int i = x1; i < x2; i ++)
			camera.setPixel(i, y, z, color);
	}
	
	void drawHLineText(int sx, int ex, int sy, int z, float su, float du, float iu, float eu, float sv, float dv, float iv, float ev, Image img, Camera camera) {
		if (ex-sx > 0) {
			du = (float)(eu-su)/(float)(ex-sx);
			dv = (float)(ev-sv)/(float)(ex-sx);
		}
		for (int i = sx; i < ex; i ++) {
			camera.setPixel(i, sy, z, img.getPixel((int)(iu*su), (int)(iv*sv)));
			su += du; sv += dv;
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
	
	void render(SceneObject sceneObject, Camera camera, List<Light> lights) {
		Mesh mesh = sceneObject.getMesh();
		Animation animation = mesh.getCurrentAnimation();
		Transform objt = sceneObject.getTransform();
		mesh.resetBuffer();
		for (int i = 0; i < mesh.getVertexes().length; i++) {
			long vertex = mesh.getVertex(i);
			long vector = VertexUtils.getVector(vertex);
			vector = Vector3MathUtils.movePointByScale(vector, objt.getScale());
			for (int j = 0; j < VertexUtils.getBoneIndex(vertex); j++) {
				Transform bone = animation.getBone(j, animation.getCurrentFrame());
				vector = Vector3MathUtils.movePointByScale(vector, bone.getScale());
				vector = Vector3MathUtils.movePointByAnglesXYZ(vector, bone.getRotation());
				vector = Vector3Utils.add(vector, bone.getPosition());
			}
			vector = Vector3MathUtils.movePointByAnglesXYZ(vector, objt.getRotation());
			vector = projectVertex(vector, objt.getPosition(), camera);
			vertex = VertexUtils.setVector(vertex, vector);
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
	
	Image img;
	void draw(int[] polygon, Mesh mesh, Camera camera) {
		renderedPolys++;
		long v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		long v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
		long v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int uv1 = mesh.getUV(polygon[Mesh.UV_1]);
		int uv2 = mesh.getUV(polygon[Mesh.UV_2]);
		int uv3 = mesh.getUV(polygon[Mesh.UV_3]);
		int color = mesh.getMaterial(polygon[Mesh.MATERIAL_INDEX]).getColor();
		color = ColorUtils.brighter(color, 3);
		if(camera.getRenderingType() == RenderingType.wireframe) {
			drawLine(v1, v2, color, camera);
			drawLine(v2, v3, color, camera);
			drawLine(v3, v1, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.solid) {
			drawPolygon(v1, v2, v3, color, camera);
		}
		if(camera.getRenderingType() == RenderingType.textured) {
			drawPolygonAffine(v1, v2, v3, uv1, uv2, uv3, img, camera);
		}
	}

//	int[][] getLightDistances(int[] sceneObjectPosition, List<Light> lights) {
//		if (lights.size() > 0) {
//			int[][] distances = new int[lights.size()][3];
//			for (int i = 0; i < distances.length; i++) {
//				int[] pos = lights.get(i).getTransform().getPosition();
//				distances[i] = Vector3MathUtils.getDistance(pos, sceneObjectPosition);
//			}
//			return distances;
//		}
//		return null;
//	}

	long projectVertex(long vertex, long objectPosition, Camera camera) {
		long px = 0, py = 0, pz = 0;
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			long camRot = camera.getTransform().getRotation(),
			camPos = camera.getTransform().getPosition();
			long pos = Vector3Utils.add(vertex, objectPosition);
			pos = Vector3Utils.subtract(pos, camPos);
			pos = Vector3MathUtils.movePointByAnglesXYZ(pos, camRot);
			int fov = camera.getFieldOfView(), rescalef = camera.getRescaleFactor();
			long z = (Vector3Utils.getZ(pos) + fov);
			if (z <= 0) z = 1;
			px = ((Vector3Utils.getX(pos)) * rescalef * fov) / z;
			py = ((Vector3Utils.getY(pos)) * rescalef * fov) / z;
			pz = z;
			break;
		case orthographic: // this projectionType ignores depth
			px = ((Vector3Utils.getX(vertex) * camera.getRescaleFactor()) >> 7);
			py = ((Vector3Utils.getY(vertex) * camera.getRescaleFactor()) >> 7);
			pz = Vector3Utils.getZ(vertex) + Vector3Utils.getZ(objectPosition);
			break;
		}
		long x = (px>>1) + camera.getHalfRect()[vx] + Vector3Utils.getX(objectPosition);
		long y = (py>>1) + camera.getHalfRect()[vy] + Vector3Utils.getY(objectPosition);
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
}
