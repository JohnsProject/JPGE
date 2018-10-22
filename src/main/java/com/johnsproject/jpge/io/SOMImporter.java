package com.johnsproject.jpge.io;

import java.io.IOException;
import java.io.InputStream;

import com.johnsproject.jpge.graphics.Material;
import com.johnsproject.jpge.graphics.Mesh;
import com.johnsproject.jpge.graphics.Transform;
import com.johnsproject.jpge.graphics.Vertex;
import com.johnsproject.jpge.graphics.Animation;
import com.johnsproject.jpge.graphics.Face;
import com.johnsproject.jpge.graphics.Texture;
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
			throw new ImportExeption(e);
		}
		return loadFromRaw(content);
	}
	
	/**
	 * Loads the som file from the given path at the resources folder,
	 *  parses it and returns it as a {@link Mesh}.
	 * 
	 * @param stream {@link InputStream} to read from.
	 * @return a mesh containing the parsed som file data.
	 * @throws ImportExeption
	 */
	public static Mesh load(InputStream stream) throws ImportExeption {
		String content = "";
		try {
			content = FileIO.readStream(stream);
		} catch (IOException e) {
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
		String[] rawFacesData = rawData.split("Faces<")[1].split(">Faces", 2)[0].split(",");
		String[] rawMaterialsData = rawData.split("Materials<")[1].split(">Materials", 2)[0].split(",");
		String[] rawAnimationsData = rawData.split("Animations<")[1].split(">Animations", 2)[0].split("Animation<");
		Mesh result = new Mesh(parseVertexes(rawVertexesData),
								parseFaces(rawFacesData),
								parseMaterials(rawMaterialsData),
								parseAnimations(rawAnimationsData));
		System.gc();
		return result;
	}
	
	/**
	 * Parses the {@link Vertex Vertexes} of a {@link Mesh} from the given string and returns it.
	 * The parameter should contain the content inside the "Vertexes<" and ">Vertexes" 
	 * pieces of the som file and should be by ',' splited because the method only sorts 
	 * and parses the sorted integer values to the right place in the {@link Vertex Vertexes}.
	 * 
	 * @param rawVertexesData the string array containing the splited data.
	 * @return an {@link Vertex} array.
	 * @throws ImportExeption
	 */
	static Vertex[] parseVertexes(String[] rawVertexesData) throws ImportExeption {
		int step = Mesh.VERTEX_LENGTH;
		Vertex[] vertexes = new Vertex[rawVertexesData.length/step];
		if(rawVertexesData.length > 2){
			for (int i = 0; i < rawVertexesData.length; i+= step) {
				int[] position = new int[3];
				position[vx] = toInt(rawVertexesData[i + vx]);
				position[vy] = toInt(rawVertexesData[i + vy]);
				position[vz] = toInt(rawVertexesData[i + vz]);
				int[] normal = new int[3];
				normal[vx] = toInt(rawVertexesData[i + Mesh.V_NORMAL + vx]);
				normal[vy] = toInt(rawVertexesData[i + Mesh.V_NORMAL + vy]);
				normal[vz] = toInt(rawVertexesData[i + Mesh.V_NORMAL + vz]);
				int bone = toInt(rawVertexesData[i + Mesh.V_BONE_INDEX]);
				int material = toInt(rawVertexesData[i + Mesh.V_MATERIAL_INDEX]);
				vertexes[i/step] = new Vertex(position, normal, bone, material);
			}
		}
		return vertexes;
	}
	
	/**
	 * Parses the {@link Face Faces} of a {@link Mesh} from the given string and returns it.
	 * The parameter should contain the content inside the "Faces<" and ">Faces" 
	 * pieces of the som file and should be by ',' splited because the method only sorts 
	 * and parses the sorted integer values to the right place in the {@link Face Faces}.
	 * 
	 * @param rawFacesData the string array containing the splited data.
	 * @return an {@link Face} array.
	 * @throws ImportExeption
	 */
	static Face[] parseFaces(String[] rawFacesData) throws ImportExeption {
		int step = Mesh.FACE_LENGTH-1;
		Face[] faces = new Face[rawFacesData.length/step];
		if(rawFacesData.length > 2){
			for (int i = 0; i < rawFacesData.length; i+=step) {
				int vertex1 = toInt(rawFacesData[i + Mesh.F_VERTEX_1]);
				int vertex2 = toInt(rawFacesData[i + Mesh.F_VERTEX_2]);
				int vertex3 = toInt(rawFacesData[i + Mesh.F_VERTEX_3]);
				int material = toInt(rawFacesData[i + Mesh.F_MATERIAL_INDEX]);
				int[] uv1 = new int[2];
				uv1[vx] = toInt(rawFacesData[i + Mesh.F_UV_1 + vx]);
				uv1[vy] = toInt(rawFacesData[i + Mesh.F_UV_1 + vy]);
				int[] uv2 = new int[2];
				uv2[vx] = toInt(rawFacesData[i + Mesh.F_UV_2 + vx]);
				uv2[vy] = toInt(rawFacesData[i + Mesh.F_UV_2 + vy]);
				int[] uv3 = new int[2];
				uv3[vx] = toInt(rawFacesData[i + Mesh.F_UV_3 + vx]);
				uv3[vy] = toInt(rawFacesData[i + Mesh.F_UV_3 + vy]);
				faces[i/step] = new Face(vertex1, vertex2, vertex3, material, uv1, uv2, uv3);
			}
		}
		return faces;
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
				materials[i/4] = new Material(ColorUtils.convert(r, g, b, a), new Texture(101, 101));
			}
		}else {
			materials = new Material[1];
			materials[0] = new Material(ColorUtils.convert(100, 100, 100), new Texture(101, 101));
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
//			System.out.println(rawAnimationsData.length);
//			System.out.println(animations.length);
			for (int i = 1; i < rawAnimationsData.length; i++) {
				animations[i-1] = parseAnimation(rawAnimationsData[i].split(">Animation", 2)[0], step, bonesCount);
			}
		}else {
			animations = new Animation[1];
			Transform[] bones = null;
			if(bonesCount > 0) {
				bones = new Transform[bonesCount];
				for (int i = 0; i < bones.length; i++) {
					int[] vector = new int[] {1, 1, 1};
					bones[i] = new Transform(vector, vector, vector);
				}
			}else {
				bones = new Transform[1];
				int[] vector = new int[] {1, 1, 1};
				bones[0] = new Transform(vector, vector, vector);
			}
			animations[0] = new Animation("Default", 1, bonesCount, bones);
		}
		return animations;
	}
	
	static Animation parseAnimation(String rawAnimationData, int step, int bonesCount) {
		String animName = rawAnimationData.split("Name<")[1].split(">Name", 2)[0];
		String[] rawBonesData = rawAnimationData.split("Bones<")[1].split(">Bones", 2)[0].split(",");
		int framesCount = rawBonesData.length/(step * bonesCount);
		Transform[] bones = new Transform[bonesCount*framesCount];
		for (int j = 0; j < rawBonesData.length; j+=step) {
			int px = toInt(rawBonesData[j + Mesh.POSITION + vx]);
			int py = toInt(rawBonesData[j + Mesh.POSITION + vy]);
			int pz = toInt(rawBonesData[j + Mesh.POSITION + vz]);
			int rx = toInt(rawBonesData[j + Mesh.ROTATION + vx]);
			int ry = toInt(rawBonesData[j + Mesh.ROTATION + vy]);
			int rz = toInt(rawBonesData[j + Mesh.ROTATION + vz]);
			int sx = toInt(rawBonesData[j + Mesh.SCALE + vx]);
			int sy = toInt(rawBonesData[j + Mesh.SCALE + vy]);
			int sz = toInt(rawBonesData[j + Mesh.SCALE + vz]);
			int[] pos = new int[] {px, py, pz};
			int[] rot = new int[] {rx, ry, rz};
			int[] scale = new int[] {sx, sy, sz};
			bones[j/step] =  new Transform(pos, rot, scale);
		}
		return new Animation(animName, framesCount, bonesCount, bones);
	}
	
	static int toInt(String string) {
		return Integer.parseInt(string);
	}
	
}
