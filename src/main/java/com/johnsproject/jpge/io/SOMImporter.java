/**
 * 
 */
package com.johnsproject.jpge.io;

import java.io.IOException;

import com.johnsproject.jpge.graphics.Material;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.Transform;
import com.johnsproject.jpge.graphics.Animation;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The SOMImporter class provides import methods for the som (Scene Object Mesh) file format
 * that is exported with the blend2SOM som exporter, the som exporter currently only supports Blender.
 * <br>
 * - https://www.blender.org/
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class SOMImporter {
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	
	/**
	 * Loads the som file from the given path, parses it and returns it as a {@link Mesh}.
	 * 
	 * @param path path of the som file.
	 * @return a mesh containing the parsed som file data.
	 * @throws ImportExeption
	 */
	public static Mesh load(String path) throws ImportExeption {
		String content = "";
		try {
			content = FileIO.readFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ImportExeption(e);
		}
		return loadFromRaw(content);
	}
	
	/**
	 * Loads the som file from the given path at the resources folder,
	 *  parses it and returns it as a {@link Mesh}.
	 * 
	 * @param path path of the som file at the resources folder.
	 * @return a mesh containing the parsed som file data.
	 * @throws ImportExeption
	 */
	public static Mesh loadFromResources(String path) throws ImportExeption {
		String content = "";
		try {
			content = FileIO.readStream(content.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ImportExeption(e);
		}
		return loadFromRaw(content);
	}
	
	/**
	 * Parses the som file from the given string and returns it as a {@link Mesh}.
	 * 
	 * @param path path of the som file.
	 * @return a mesh containing the parsed som file data.
	 * @throws ImportExeption
	 */
	public static Mesh loadFromRaw(String data) throws ImportExeption {
		String rawData = data.replace(" ", "").replace("\n", "");
		String[] rawVertexesData = rawData.split("Vertexes<")[1].split(">Vertexes", 2)[0].split(",");
		String[] rawPolygonsData = rawData.split("Polygons<")[1].split(">Polygons", 2)[0].split(",");
		String[] rawUVsData = rawData.split("UVs<")[1].split(">UVs", 2)[0].split(",");
		String[] rawMaterialsData = rawData.split("Materials<")[1].split(">Materials", 2)[0].split(",");
		String[] rawAnimationsData = rawData.split("Animations<")[1].split(">Animations", 2)[0].split("Animation<");
		Mesh result = new Mesh(parseVertexes(rawVertexesData),
								parsePolygons(rawPolygonsData),
								parseUVs(rawUVsData),
								parseMaterials(rawMaterialsData),
								parseAnimations(rawAnimationsData));
		System.gc();
		return result;
	}
	
	/**
	 * Parses the vertexes of a {@link Mesh} from the given string and returns it.
	 * The parameter should contain the content inside the "Vertexes<" and ">Vertexes" 
	 * pieces of the som file and should be by ',' splited because the method only sorts 
	 * and parses the sorted integer values to the right place in the vertex.
	 * 
	 * @param rawVertexesData the string array containing the splited data.
	 * @return an vertex array.
	 * @throws ImportExeption
	 */
	static int[][] parseVertexes(String[] rawVertexesData) throws ImportExeption {
		int step = Mesh.VERTEX_LENGTH-1;
		int[][] vertexes = new int[rawVertexesData.length/step][Mesh.VERTEX_LENGTH];
		if(rawVertexesData.length > 2){
			for (int i = 0; i < rawVertexesData.length; i+= step) {
				int[] vertex = new int[Mesh.VERTEX_LENGTH];
				vertex[vx] = toInt(rawVertexesData[i + vx]);
				vertex[vy] = toInt(rawVertexesData[i + vy]);
				vertex[vz] = toInt(rawVertexesData[i + vz]);
				vertex[Mesh.BONE_INDEX] = toInt(rawVertexesData[i + Mesh.BONE_INDEX]);
				vertex[Mesh.SHADE_FACTOR] = 0;
				vertexes[i/step] = vertex;
			}
		}
		return vertexes;
	}
	
	/**
	 * Parses the polygons of a {@link Mesh} from the given string and returns it.
	 * The parameter should contain the content inside the "Polygons<" and ">Polygons" 
	 * pieces of the som file and should be by ',' splited because the method only sorts 
	 * and parses the sorted integer values to the right place in the polygon.
	 * 
	 * @param rawPolygonsData the string array containing the splited data.
	 * @return an polygon array.
	 * @throws ImportExeption
	 */
	static int[][] parsePolygons(String[] rawPolygonsData) throws ImportExeption {
		int step = Mesh.POLYGON_LENGTH;
		int[][] polygons = new int[rawPolygonsData.length/step][Mesh.POLYGON_LENGTH];
		if(rawPolygonsData.length > 2){
			for (int i = 0; i < rawPolygonsData.length; i+=step) {
				int[] polygon = new int[Mesh.POLYGON_LENGTH];
				polygon[Mesh.VERTEX_1] = toInt(rawPolygonsData[i + Mesh.VERTEX_1]);
				polygon[Mesh.VERTEX_2] = toInt(rawPolygonsData[i + Mesh.VERTEX_2]);
				polygon[Mesh.VERTEX_3] = toInt(rawPolygonsData[i + Mesh.VERTEX_3]);
				polygon[Mesh.MATERIAL_INDEX] = toInt(rawPolygonsData[i + Mesh.MATERIAL_INDEX]);
				polygon[Mesh.UV_1] = toInt(rawPolygonsData[i + Mesh.UV_1]);
				polygon[Mesh.UV_2] = toInt(rawPolygonsData[i + Mesh.UV_2]);
				polygon[Mesh.UV_3] = toInt(rawPolygonsData[i + Mesh.UV_3]);
				polygons[i/step] = polygon;
			}
		}
		return polygons;
	}
	
	/**
	 * Parses the uvs of a {@link Mesh} from the given string and returns it.
	 * The parameter should contain the content inside the "UVs<" and ">UVs" 
	 * pieces of the som file and should be by ',' splited because the method only sorts 
	 * and parses the sorted integer values to the right place in the uv.
	 * 
	 * @param rawUVsData the string array containing the splited data.
	 * @return an uv array.
	 * @throws ImportExeption
	 */
	static int[][] parseUVs(String[] rawUVsData) throws ImportExeption {
		int step = Mesh.UV_LENGTH;
		int[][] uvs = null;
		if(rawUVsData.length > 2){
			uvs = new int[rawUVsData.length/step][Mesh.UV_LENGTH];
			for (int i = 0; i < rawUVsData.length; i+= step) {
				int[] uv = new int[Mesh.UV_LENGTH];
				uv[vx] = toInt(rawUVsData[i + vx]);
				uv[vy] = toInt(rawUVsData[i + vy]);
				uvs[i/step] = uv;
			}
		}else {
			uvs = new int[1][Mesh.UV_LENGTH];
		}
		return uvs;
	}
	
	/**
	 * Parses the {@link Material Materials} of a {@link Mesh} from the given string and returns it.
	 * The parameter should contain the content inside the "Materials<" and ">Materials" 
	 * pieces of the som file and should be by ',' splited because the method only sorts 
	 * and parses the sorted integer values to the right place in the {@link Material}.
	 * 
	 * @param rawMaterialsData the string array containing the splited data.
	 * @return an {@link Material} array.
	 * @throws ImportExeption
	 */
	static Material[] parseMaterials(String[] rawMaterialsData){
		Material[] materials = null;
		if(rawMaterialsData.length > 2) {
			materials = new Material[rawMaterialsData.length/4];
			for (int i = 0; i < rawMaterialsData.length; i+=4) {
				int r = toInt(rawMaterialsData[i]), g = toInt(rawMaterialsData[i+1]),
					b = toInt(rawMaterialsData[i+2]), a = toInt(rawMaterialsData[i+3]);
				materials[i/4] = new Material(ColorUtils.convert(r, g, b, a));
			}
		}else {
			materials = new Material[1];
			materials[0] = new Material(ColorUtils.convert(100, 100, 100, 255));
		}
		return materials;
	}
	
	/**
	 * Parses the {@link Animation Animations} of a {@link Mesh} from the given string and returns it.
	 * The parameter should contain the content inside the "Animations<" and ">Animations" 
	 * pieces of the som file and should be by ',' splited because the method only sorts 
	 * and parses the sorted integer values to the right place in the bones of an {@link Animation}.
	 * 
	 * @param rawAnimationsData the string array containing the splited data.
	 * @return an {@link Animation} array.
	 * @throws ImportExeption
	 */
	static Animation[] parseAnimations(String[] rawAnimationsData) throws ImportExeption {
		int bonesCount = toInt(rawAnimationsData[0].split("BonesCount<")[1].split(">BonesCount")[0]);
		int step = Mesh.BONE_LENGTH;
		Animation[] animations = null;
		if(rawAnimationsData.length > 1){
			animations = new Animation[rawAnimationsData.length-1];
			for (int i = 1; i < rawAnimationsData.length; i++) {
				animations[i-1] = parseAnimation(rawAnimationsData[i].split(">Animation", 2)[0], step, bonesCount);
			}
		}else {
			animations = new Animation[1];
			Transform[] bones = null;
			if(bonesCount > 0) {
				bones = new Transform[bonesCount];
				for (int i = 0; i < bones.length; i++) {
					bones[i] = new Transform(new int[] {0,0,0}, new int[] {0,0,0}, new int[] {1,1,1});
				}
			}else {
				bones = new Transform[1];
				bones[0] = new Transform(new int[] {0,0,0}, new int[] {0,0,0}, new int[] {1,1,1});
			}
			animations[0] = new Animation("Animation", 1, bonesCount, bones);
		}
		return animations;
	}
	
	static Animation parseAnimation(String rawAnimationData, int step, int bonesCount) {
		String animName = rawAnimationData.split("Name<")[1].split(">Name", 2)[0];
		String[] rawBonesData = rawAnimationData.split("Bones<")[1].split(">Bones", 2)[0].split(",");
		int framesCount = rawBonesData.length/(step * bonesCount);
		Transform[] bones = new Transform[bonesCount*framesCount];
		for (int j = 0; j < rawBonesData.length; j+=step) {
			int[] pos = new int[3], rot = new int[3], scale = new int[3];
			pos[vx] = toInt(rawBonesData[j + Mesh.POSITION + vx]);
			pos[vy] = toInt(rawBonesData[j + Mesh.POSITION + vy]);
			pos[vz] = toInt(rawBonesData[j + Mesh.POSITION + vz]);
			rot[vx] = toInt(rawBonesData[j + Mesh.ROTATION + vx]);
			rot[vy] = toInt(rawBonesData[j + Mesh.ROTATION + vy]);
			rot[vz] = toInt(rawBonesData[j + Mesh.ROTATION + vz]);
			scale[vx] = toInt(rawBonesData[j + Mesh.SCALE + vx]);
			scale[vy] = toInt(rawBonesData[j + Mesh.SCALE + vy]);
			scale[vz] = toInt(rawBonesData[j + Mesh.SCALE + vz]);
			bones[j/step] =  new Transform(pos, rot, scale);
		}
		return new Animation(animName, framesCount, bonesCount, bones);
	}
	
	static int toInt(String string) {
		return Integer.parseInt(string);
	}
	
}
