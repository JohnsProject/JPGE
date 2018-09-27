package com.johnsproject.jpge.io;

import org.junit.Test;

import com.johnsproject.jpge.graphics.Animation;
import com.johnsproject.jpge.graphics.Material;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.Transform;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * Test class for {@link SOMImporter}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class SOMImporterTest {
	
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	//triangulated default cube
	String template = 
			"SOM (SceneObjectMesh) file created by Blender SOM exporter version 1.2\n" + 
			"project page: https://github.com/JohnsProject/JPGE\n" + 
			"\n" + 
			"Wiki : \n" + 
			" Vertexes contains the vertex data (x, y, z, bone) of all visible objects in the scene.\n" + 
			" Polygons contains the polygon data (vertex1, vertex2, vertex3, material, uv1, uv2, uv3) of all visible objects in the scene.\n" + 
			" Materials contains the material (red, green, blue, alpha) data of all visible objects in the scene.\n" + 
			" Animations contains the animation data of all visible objects in the scene,\n" + 
			" a animation contains the data of bones at each keyframe and a bone is composed of (px, py, pz, rx, ry, rz, sx, sy, sz),\n" + 
			" where p = position, r = rotation and s = scale.\n" + 
			"\n" + 
			"Vertexes < -100,-100,-100,0,-100,-100,100,1,-100,100,-100,0,-100,100,100,1,100,-100,-100,0,100,-100,100,1,100,100,-100,0,100,100,100,1 > Vertexes\n" + 
			"\n" + 
			"Polygons < 1,2,0,0,0,1,2,3,6,2,0,3,4,5,7,4,6,0,6,7,8,5,0,4,0,9,10,11,6,0,2,0,12,13,14,3,5,7,0,15,16,17,1,3,2,0,18,19,20,3,7,6,0,21,22,23,7,5,4,0,24,25,26,5,1,0,0,27,28,29,6,4,0,0,30,31,32,3,1,5,0,33,34,35 > Polygons\n" + 
			"\n" + 
			"UVs < 100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,0,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100,100,0,100 > UVs\n" + 
			"\n" + 
			"Materials < 0,133,204,255 > Materials\n" + 
			"\n" + 
			"Animations < \n" + 
			" BonesCount <2> BonesCount \n" + 
			" Animation < \n" + 
			"  Name < Action> Name \n" + 
			"  Bones < 0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0,25,75,0,0,1,1,1 > Bones\n" + 
			" > Animation \n" + 
			"> Animations";
	
	@Test
	public void parseVertexesTest() throws Exception {
		String rawData = template.replace(" ", "").replace("\n", "");
		String[] rawVertexesData = rawData.split("Vertexes<")[1].split(">Vertexes", 2)[0].split(",");
		int[][] vertexes = SOMImporter.parseVertexes(rawVertexesData);
		assert(vertexes[1][vx] == -100);
		assert(vertexes[1][vy] == -100);
		assert(vertexes[1][vz] == 100);
		assert(vertexes[1][Mesh.BONE_INDEX] == 1);
	}
	
	@Test
	public void parsePolygonsTest() throws Exception {
		String rawData = template.replace(" ", "").replace("\n", "");
		String[] rawPolygonsData = rawData.split("Polygons<")[1].split(">Polygons", 2)[0].split(",");
		int[][] polygons = SOMImporter.parseFaces(rawPolygonsData);
		assert(polygons[1].length == Mesh.FACE_LENGTH);
		assert(polygons[1][Mesh.VERTEX_1] == 3);
		assert(polygons[1][Mesh.VERTEX_2] == 6);
		assert(polygons[1][Mesh.VERTEX_3] == 2);
		assert(polygons[1][Mesh.MATERIAL_INDEX] == 0);
		assert(polygons[1][Mesh.UV_1] == 3);
		assert(polygons[1][Mesh.UV_2] == 4);
		assert(polygons[1][Mesh.UV_3] == 5);
	}
	
	@Test
	public void parseUVsTest() throws Exception {
		String rawData = template.replace(" ", "").replace("\n", "");
		String[] rawUVsData = rawData.split("UVs<")[1].split(">UVs", 2)[0].split(",");
		int[][] uvs = SOMImporter.parseUVs(rawUVsData);
		assert(uvs[1][vx] == 0);
		assert(uvs[1][vy] == 100);
	}
	
	@Test
	public void parseMaterialsTest() throws Exception {
		String rawData = template.replace(" ", "").replace("\n", "");
		String[] rawMaterialsData = rawData.split("Materials<")[1].split(">Materials", 2)[0].split(",");
		Material[] materials = SOMImporter.parseMaterials(rawMaterialsData);
		int color = materials[0].getColor();
		assert(ColorUtils.getRed(color) == 0);
		assert(ColorUtils.getGreen(color) == 133);
		assert(ColorUtils.getBlue(color) == 204);
		assert(ColorUtils.getAlpha(color) == 255);
	}
	
	@Test
	public void parseAnimationsTest() throws Exception {
		String rawData = template.replace(" ", "").replace("\n", "");
		String[] rawAnimationsData = rawData.split("Animations<")[1].split(">Animations", 2)[0].split("Animation<");
		Animation[] animations = SOMImporter.parseAnimations(rawAnimationsData);
		assert (animations[0].getName().equals("Action"));
		assert (animations[0].getBonesCount() == 2);
		Transform bone1 = animations[0].getBone(0, 0);
		Transform bone2 = animations[0].getBone(1, 0);
		assert (VectorUtils.equals3(bone1.getPosition(), new int[] {0, 0, 0}));
		assert (VectorUtils.equals3(bone1.getRotation(), new int[] {0, 0, 0}));
		assert (VectorUtils.equals3(bone1.getScale(), new int[] {1, 1, 1}));
		assert (VectorUtils.equals3(bone2.getPosition(), new int[] {0, 0, 25}));
		assert (VectorUtils.equals3(bone2.getRotation(), new int[] {75, 0, 0}));
		assert (VectorUtils.equals3(bone2.getScale(), new int[] {1, 1, 1}));
	}
	
}
