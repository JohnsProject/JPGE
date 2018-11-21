package com.johnsproject.jpge.io;

import java.io.IOException;
import java.io.InputStream;

import com.johnsproject.jpge.dto.Animation;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Material;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.Texture;
import com.johnsproject.jpge.dto.Transform;
import com.johnsproject.jpge.dto.Vertex;
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
	 * @param data data of the som file.
	 * @return a mesh containing the parsed som file data.
	 * @throws ImportExeption
	 */
	public static Mesh loadFromRaw(String data) throws ImportExeption {
		String rawData = data.replace(" ", "").replace("\n", "");
		Vertex[] vertexes = parseVertexes(rawData);
		Face[] faces = parseFaces(rawData);
		Material[] materials = parseMaterials(rawData);
		Animation[] animations = parseAnimations(rawData);
		Mesh result = new Mesh(vertexes, faces, materials, animations);
		System.gc();
		return result;
	}
	
	static Vertex[] parseVertexes(String rawData) throws ImportExeption {
		String vCountData = rawData.split("vCount<")[1].split(">vCount", 2)[0];
		Vertex[] vertexes = new Vertex[toInt(vCountData)];
		String[] vPositionData = rawData.split("vPosition<")[1].split(">vPosition", 2)[0].split(",");
		String[] vNormalData = rawData.split("vNormal<")[1].split(">vNormal", 2)[0].split(",");
		String[] vBoneData = rawData.split("vBone<")[1].split(">vBone", 2)[0].split(",");
		String[] vMaterialData = rawData.split("vMaterial<")[1].split(">vMaterial", 2)[0].split(",");
		for (int i = 0; i < vertexes.length * 3; i += 3) {
			int[] position = new int[3];
			position[vx] = toInt(vPositionData[i + vx]);
			position[vy] = toInt(vPositionData[i + vy]);
			position[vz] = toInt(vPositionData[i + vz]);
			int[] normal = new int[3];
			normal[vx] = toInt(vNormalData[i + vx]);
			normal[vy] = toInt(vNormalData[i + vy]);
			normal[vz] = toInt(vNormalData[i + vz]);
			int bone = toInt(vBoneData[i / 3]);
			int material = toInt(vMaterialData[i / 3]);
			vertexes[i / 3] = new Vertex(position, normal, bone, material);
		}
		return vertexes;
	}
	
	static Face[] parseFaces(String rawData) throws ImportExeption {
		String fCountData = rawData.split("fCount<")[1].split(">fCount", 2)[0];
		Face[] faces = new Face[toInt(fCountData)];
		String[] fVertex1Data = rawData.split("fVertex1<")[1].split(">fVertex1", 2)[0].split(",");
		String[] fVertex2Data = rawData.split("fVertex2<")[1].split(">fVertex2", 2)[0].split(",");
		String[] fVertex3Data = rawData.split("fVertex3<")[1].split(">fVertex3", 2)[0].split(",");
		String[] fMaterialData = rawData.split("fMaterial<")[1].split(">fMaterial", 2)[0].split(",");
		String[] fUV1Data = rawData.split("fUV1<")[1].split(">fUV1", 2)[0].split(",");
		String[] fUV2Data = rawData.split("fUV2<")[1].split(">fUV2", 2)[0].split(",");
		String[] fUV3Data = rawData.split("fUV3<")[1].split(">fUV3", 2)[0].split(",");
		for (int i = 0; i < faces.length * 2; i += 2) {
			int vertex1 = toInt(fVertex1Data[i / 2]);
			int vertex2 = toInt(fVertex2Data[i / 2]);
			int vertex3 = toInt(fVertex3Data[i / 2]);
			int material = toInt(fMaterialData[i / 2]);
			int[] uv1 = new int[2];
			uv1[vx] = toInt(fUV1Data[i + vx]);
			uv1[vy] = toInt(fUV1Data[i + vy]);
			int[] uv2 = new int[2];
			uv2[vx] = toInt(fUV2Data[i + vx]);
			uv2[vy] = toInt(fUV2Data[i + vy]);
			int[] uv3 = new int[2];
			uv3[vx] = toInt(fUV3Data[i + vx]);
			uv3[vy] = toInt(fUV3Data[i + vy]);
			faces[i / 2] = new Face(vertex1, vertex2, vertex3, material, uv1, uv2, uv3);
		}
		return faces;
	}
	
	static Material[] parseMaterials(String rawData){
		String mCountData = rawData.split("mCount<")[1].split(">mCount", 2)[0];
		Material[] materials = new Material[toInt(mCountData)];
		String[] mColorData = rawData.split("mColor<")[1].split(">mColor", 2)[0].split(",");
		for (int i = 0; i < mColorData.length; i+=4) {
			int r = toInt(mColorData[i]);
			int	g = toInt(mColorData[i+1]);
			int	b = toInt(mColorData[i+2]);
			int	a = toInt(mColorData[i+3]);
			materials[i/4] = new Material(ColorUtils.convert(r, g, b, a), new Texture(10, 10));
		}
		return materials;
	}
	
	static Animation[] parseAnimations(String rawData) throws ImportExeption {
		String[] rawAnimationsData = rawData.split("Animations<")[1].split(">Animations", 2)[0].split("Animation<");
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
