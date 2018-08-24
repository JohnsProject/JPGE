package com.johnsproject.jpge.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.List;

import com.johnsproject.jpge.io.FileIO;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class SceneRenderer{
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	public enum ProjectionType {
		orthographic, perspective
	}	

	public enum RenderingType {
		wireframe, solid, textured
	}
	
	private boolean log = true;
	
	public SceneRenderer() {
		try {
//			img = convertTo2DWithoutUsingGetRGB(
//					new Image("/home/john/Development/Java/Workspace/JPGE/bin/ButtonTexture.png").getBufferedImage());
			//img = new Image("/home/john/Development/JohnsProjectLogo.png");
			img = new Image(FileIO.loadImage("/home/john/Development/Brick.jpg"));
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
	
	void drawPolygonAffine(int[] vx1, int[] vx2, int[] vx3, int[] uv1, int[] uv2, int[] uv3, Image img, Camera cam) {
		float w = img.getWidth(), h = img.getHeight();
		int tmp, x0 = vx1[vx], y0 = vx1[vy], z0 = vx1[vz],
				x1 = vx2[vx], y1 = vx2[vy], z1 = vx1[vz],
				x2 = vx3[vx], y2 = vx3[vy], z2 = vx1[vz],
				u0 = uv1[vx], v0 = uv1[vy],
				u1 = uv2[vx], v1 = uv2[vy],
				u2 = uv3[vx], v2 = uv3[vy];
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
	    int sy = y0;
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
	
	void drawPolygon(int[] vx1, int[] vx2, int[] vx3, int c, Camera cam) {
		int tmp, x0 = vx1[vx], y0 = vx1[vy], z0 = vx1[vz],
				x1 = vx2[vx], y1 = vx2[vy],
				x2 = vx3[vx], y2 = vx3[vy];
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
	
	void drawLine(int[] v1, int[] v2, int color, Camera camera) {
		int w = v2[vx] - v1[vx];
		int h = v2[vy] - v1[vy];
		int d = v2[vz] - v1[vz];
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
		int x = v1[vx], y = v1[vy], z = v1[vz];
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
		for (int i = 0; i < mesh.getBufferedVertexes().length; i++) {
			int[] vertex = mesh.getBufferedVertex(i);
			VectorMathUtils.movePointByScale(vertex, objt.getScale());
			for (int j = 0; j < vertex[Mesh.BONE_INDEX]; j++) {
				Transform bone = animation.getBone(j, animation.getCurrentFrame());
				VectorMathUtils.movePointByScale(vertex, bone.getScale());
				VectorMathUtils.movePointByAnglesXYZ(vertex, bone.getRotation());
				VectorUtils.add(vertex, bone.getPosition());
			}
			VectorMathUtils.movePointByAnglesXYZ(vertex, objt.getRotation());
			projectVertex(vertex, objt.getPosition(), camera);
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
		int[] v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		int[] v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]);
		int[] v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int[] uv1 = mesh.getUV(polygon[Mesh.UV_1]);
		int[] uv2 = mesh.getUV(polygon[Mesh.UV_2]);
		int[] uv3 = mesh.getUV(polygon[Mesh.UV_3]);
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

	int[][] getLightDistances(int[] sceneObjectPosition, List<Light> lights) {
		if (lights.size() > 0) {
			int[][] distances = new int[lights.size()][3];
			for (int i = 0; i < distances.length; i++) {
				int[] pos = lights.get(i).getTransform().getPosition();
				distances[i] = VectorMathUtils.getDistance(pos, sceneObjectPosition);
			}
			return distances;
		}
		return null;
	}

	void projectVertex(int[] vertex, int[] objectPosition, Camera camera) {
		int px = 0, py = 0, pz = 0;
		switch (camera.getProjectionType()) {
		case perspective: // this projectionType uses depth
			int[] camRot = camera.getTransform().getRotation(),
			camPos = camera.getTransform().getPosition();
			int[] pos = VectorUtils.subtract(VectorUtils.add(vertex, objectPosition),camPos);
			VectorMathUtils.movePointByAnglesXYZ(pos, camRot);
			int fov = camera.getFieldOfView(), rescalef = camera.getRescaleFactor();
			int z = (pos[vz] + fov);
			if (z <= 0) z = 1;
			px = ((pos[vx]) * rescalef * fov) / z;
			py = ((pos[vy]) * rescalef * fov) / z;
			pz = z;
			break;
		case orthographic: // this projectionType ignores depth
			px = ((vertex[vx] * camera.getRescaleFactor()) >> 7);
			py = ((vertex[vy] * camera.getRescaleFactor()) >> 7);
			pz = vertex[vz] + objectPosition[vz];
			break;
		}
		vertex[vx] = (px>>1) + camera.getHalfRect()[vx] + objectPosition[vx];
		vertex[vy] = (py>>1) + camera.getHalfRect()[vy] + objectPosition[vy];
		vertex[vz] = pz;
	}
	
	boolean cullViewFrustum(int[] polygon, Mesh mesh, Camera camera) {
		int[] v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]);
		int ncp = camera.getNearClippingPlane();
		int fcp = camera.getFarClippingPlane();
		int w = camera.getWidth(), h = camera.getHeight();
		int x = v1[vx], y = v1[vy], z = v1[vz];
		if (x > -400 && x < w+400 && y > -400 && y < h+400 && z > ncp && z < fcp)
			return false;
		return true;
	}
	
	//backface culling with sholeance algorithm
	boolean cullBackface(int[] polygon, Mesh mesh) {
		int[] v1 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_1]),
				v2 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_2]),
				v3 = mesh.getBufferedVertex(polygon[Mesh.VERTEX_3]);
		int a = ((v2[vx] - v1[vx])*(v3[vy] - v1[vy])
				- (v3[vx] - v1[vx])*(v2[vy] - v1[vy])) >> 1;
		if (a < 0) return false;
		return true;
	}
}
