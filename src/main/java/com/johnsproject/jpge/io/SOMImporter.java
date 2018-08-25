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
import com.johnsproject.jpge.utils.UVUtils;
import com.johnsproject.jpge.utils.Vector3Utils;
import com.johnsproject.jpge.utils.VertexUtils;

/**
 * The SOMImporter class provides import methods for the som (Scene Object Mesh) file format
 * that is exported with the blend2SOM som exporter, the som exporter currently only supports Blender.
 * <br>
 * - https://www.blender.org/
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class SOMImporter {
	private static final int vx = Vector3Utils.X, vy = Vector3Utils.Y, vz = Vector3Utils.Z;
	
	
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
	static long[] parseVertexes(String[] rawVertexesData) throws ImportExeption {
		int step = Mesh.VERTEX_LENGTH-1;
		long[] vertexes = new long[rawVertexesData.length/step];
		if(rawVertexesData.length > 2){
			for (int i = 0; i < rawVertexesData.length; i+= step) {
				int x = toInt(rawVertexesData[i + vx]);
				int y = toInt(rawVertexesData[i + vy]);
				int z = toInt(rawVertexesData[i + vz]);
				int boneIndex = toInt(rawVertexesData[i + Mesh.BONE_INDEX]);
				long vector = Vector3Utils.convert(x, y, z);
				vertexes[i/step] = VertexUtils.convert(vector, boneIndex, 0);
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
	static int[] parseUVs(String[] rawUVsData) throws ImportExeption {
		int step = Mesh.UV_LENGTH;
		int[] uvs = null;
		if(rawUVsData.length > 2){
			uvs = new int[rawUVsData.length/step];
			for (int i = 0; i < rawUVsData.length; i+= step) {
				int u = toInt(rawUVsData[i + vx]);
				int v = toInt(rawUVsData[i + vy]);
				uvs[i/step] = UVUtils.convert(u, v);
			}
		}else {
			uvs = new int[1];
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
					long vector = Vector3Utils.convert(1, 1, 1);
					bones[i] = new Transform(vector, vector, vector);
				}
			}else {
				bones = new Transform[1];
				long vector = Vector3Utils.convert(1, 1, 1);
				bones[0] = new Transform(vector, vector, vector);
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
			long vector = Vector3Utils.convert(0, 0, 0);
			long pos = vector, rot = vector, scale = vector;
			pos = Vector3Utils.addX(pos, toInt(rawBonesData[j + Mesh.POSITION + vx]));
			pos = Vector3Utils.addY(pos, toInt(rawBonesData[j + Mesh.POSITION + vy]));
			pos = Vector3Utils.addZ(pos, toInt(rawBonesData[j + Mesh.POSITION + vz]));
			rot = Vector3Utils.addX(rot, toInt(rawBonesData[j + Mesh.ROTATION + vx]));
			rot = Vector3Utils.addY(rot, toInt(rawBonesData[j + Mesh.ROTATION + vy]));
			rot = Vector3Utils.addZ(rot, toInt(rawBonesData[j + Mesh.ROTATION + vz]));
			scale = Vector3Utils.addX(scale, toInt(rawBonesData[j + Mesh.SCALE + vx]));
			scale = Vector3Utils.addY(scale, toInt(rawBonesData[j + Mesh.SCALE + vy]));
			scale = Vector3Utils.addZ(scale, toInt(rawBonesData[j + Mesh.SCALE + vz]));
			bones[j/step] =  new Transform(pos, rot, scale);
		}
		return new Animation(animName, framesCount, bonesCount, bones);
	}
	
	static int toInt(String string) {
		return Integer.parseInt(string);
	}
	
}
