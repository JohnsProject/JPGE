package com.johnsproject.jpge.io;

import org.junit.Test;

import com.johnsproject.jpge.dto.Animation;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Material;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.dto.Vertex;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * Test class for {@link SOMImporter}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class SOMImporterTest {
//	
//	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
//	
//	//triangulated default cube
//	String template = 
//			"SOM (SceneObjectMesh) file created by Blender SOM exporter version 1.2\n" + 
//			"project page: https://github.com/JohnsProject/JPGE\n" + 
//			"\n" + 
//			"Wiki : \n" + 
//			" Vertexes contains the vertex data (x, y, z, normal_x, normal_y, normal_z, boneIndex) of all visible objects in the scene\n" + 
//			" Faces contains the face data (vertex1, vertex2, vertex3, material, uv1_x, uv1_y, uv2_x, uv2_y, uv3_x, uv3_y) of all visible objects in the scene.\n" + 
//			" Materials contains the material (red, green, blue, alpha) data of all visible objects in the scene.\n" + 
//			" Animations contains the animation data of all visible objects in the scene,\n" + 
//			" a animation contains the data of bones at each keyframe and a bone is composed of (px, py, pz, rx, ry, rz, sx, sy, sz),\n" + 
//			" where p = position, r = rotation and s = scale.\n" + 
//			"\n" + 
//			"Vertexes < 100,99,-100,57,57,-57,0,100,-100,-100,57,-57,-57,0,-100,-99,-100,-57,-57,-57,0,-99,100,-100,-57,57,-57,0,100,99,100,57,57,57,0,99,-100,100,57,-57,57,0,-100,-99,100,-57,-57,57,0,-99,100,100,-57,57,57,0 > Vertexes\n" + 
//			"\n" + 
//			"Faces < 1,3,0,0,128,0,0,128,0,0,7,5,4,0,128,0,0,128,0,0,4,1,0,0,128,0,0,128,0,0,5,2,1,0,128,0,0,128,0,0,2,7,3,0,0,0,128,128,0,128,0,7,4,0,128,0,0,128,0,0,1,2,3,0,128,0,128,128,0,128,7,6,5,0,128,0,128,128,0,128,4,5,1,0,128,0,128,128,0,128,5,6,2,0,128,0,128,128,0,128,2,6,7,0,0,0,128,0,128,128,0,3,7,0,128,0,128,128,0,128 > Faces\n" + 
//			"\n" + 
//			"Materials < 0,182,204,255 > Materials\n" + 
//			"\n" + 
//			"Animations < \n" + 
//			" BonesCount <0> BonesCount \n" + 
//			"> Animations";
//	
//	@Test
//	public void parseVertexesTest() throws Exception {
//		String rawData = template.replace(" ", "").replace("\n", "");
//		String[] rawVertexesData = rawData.split("Vertexes<")[1].split(">Vertexes", 2)[0].split(",");
//		Vertex[] vertexes = SOMImporter.parseVertexes(rawVertexesData);
//		assert(vertexes[1].getPosition()[vx] == 100);
//		assert(vertexes[1].getPosition()[vy] == -100);
//		assert(vertexes[1].getPosition()[vz] == -100);
//		assert(vertexes[1].getNormal()[vx] == 57);
//		assert(vertexes[1].getNormal()[vy] == -57);
//		assert(vertexes[1].getNormal()[vz] == -57);
//		assert(vertexes[1].getBone() == 0);
//	}
//	
//	@Test
//	public void parsePolygonsTest() throws Exception {
//		String rawData = template.replace(" ", "").replace("\n", "");
//		String[] rawPolygonsData = rawData.split("Faces<")[1].split(">Faces", 2)[0].split(",");
//		Face[] polygons = SOMImporter.parseFaces(rawPolygonsData);
//		assert(polygons[1].getVertex1() == 7);
//		assert(polygons[1].getVertex2() == 5);
//		assert(polygons[1].getVertex3() == 4);
//		assert(polygons[1].getMaterial() == 0);
//		assert(polygons[1].getUV1()[vx] == 128);
//		assert(polygons[1].getUV1()[vy] == 0);
//		assert(polygons[1].getUV2()[vx] == 0);
//		assert(polygons[1].getUV2()[vy] == 128);
//		assert(polygons[1].getUV3()[vx] == 0);
//		assert(polygons[1].getUV3()[vy] == 0);
//	}
//	
//	@Test
//	public void parseMaterialsTest() throws Exception {
//		String rawData = template.replace(" ", "").replace("\n", "");
//		String[] rawMaterialsData = rawData.split("Materials<")[1].split(">Materials", 2)[0].split(",");
//		Material[] materials = SOMImporter.parseMaterials(rawMaterialsData);
//		int color = materials[0].getColor();
//		assert(ColorUtils.getRed(color) == 0);
//		assert(ColorUtils.getGreen(color) == 182);
//		assert(ColorUtils.getBlue(color) == 204);
//		assert(ColorUtils.getAlpha(color) == 255);
//	}
//	
//	@Test
//	public void parseAnimationsTest() throws Exception {
//		String rawData = template.replace(" ", "").replace("\n", "");
//		String[] rawAnimationsData = rawData.split("Animations<")[1].split(">Animations", 2)[0].split("Animation<");
//		Animation[] animations = SOMImporter.parseAnimations(rawAnimationsData);
//		assert (animations[0].getName().equals("Default"));
//		assert (animations[0].getBonesCount() == 0);
//	}
//	
}
