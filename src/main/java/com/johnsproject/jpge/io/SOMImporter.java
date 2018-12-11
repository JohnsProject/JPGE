/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge.io;

import java.io.IOException;
import java.io.InputStream;

import com.johnsproject.jpge.dto.Animation;
import com.johnsproject.jpge.dto.Face;
import com.johnsproject.jpge.dto.Material;
import com.johnsproject.jpge.dto.Mesh;
import com.johnsproject.jpge.dto.Texture;
import com.johnsproject.jpge.dto.Vertex;
import com.johnsproject.jpge.utils.ColorUtils;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The SOMImporter class provides import methods for the som (Scene Object Mesh) file format
 * that is exported with the blend2SOM som exporter, the som exporter currently only supports Blender.
 *
 * @author John´s Project - John Salomon
 *
 */
public class SOMImporter {
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	
	/**
	 * Loads the som file from the given path, parses it and returns it as a {@link Mesh}.
	 * 
	 * @param path path of the som file.
	 * @return a mesh containing the parsed som file data.
	 * @throws IOException
	 */
	public static Mesh load(String path) throws IOException {
		String content = FileIO.readFile(path);
		return loadFromRaw(content);
	}
	
	/**
	 * Loads the som file from the given path at the resources folder,
	 *  parses it and returns it as a {@link Mesh}.
	 * 
	 * @param stream {@link InputStream} to read from.
	 * @return a mesh containing the parsed som file data.
	 * @throws IOException
	 */
	public static Mesh load(InputStream stream) throws IOException {
		String content = FileIO.readStream(stream);
		return loadFromRaw(content);
	}
	
	/**
	 * Parses the som file from the given string and returns it as a {@link Mesh}.
	 * 
	 * @param data data of the som file.
	 * @return a mesh containing the parsed som file data.
	 * @throws IOException
	 */
	public static Mesh loadFromRaw(String data) throws IOException {
		String rawData = data.replace(" ", "").replace("\n", "");
		Vertex[] vertexes = parseVertexes(rawData);
		Face[] faces = parseFaces(rawData);
		Material[] materials = parseMaterials(rawData);
		Animation[] animations = parseAnimations(rawData);
		Mesh result = new Mesh(vertexes, faces, materials, animations);
		System.gc();
		return result;
	}
	
	static Vertex[] parseVertexes(String rawData) throws IOException {
		String vCountData = rawData.split("vCount<")[1].split(">vCount", 2)[0];
		Vertex[] vertexes = new Vertex[toInt(vCountData)];
		String[] vLocationData = rawData.split("vPosition<")[1].split(">vPosition", 2)[0].split(",");
		String[] vNormalData = rawData.split("vNormal<")[1].split(">vNormal", 2)[0].split(",");
		String[] vBoneData = rawData.split("vBone<")[1].split(">vBone", 2)[0].split(",");
		String[] vMaterialData = rawData.split("vMaterial<")[1].split(">vMaterial", 2)[0].split(",");
		for (int i = 0; i < vertexes.length * 3; i += 3) {
			int[] location = new int[3];
			location[vx] = toInt(vLocationData[i + vx]);
			location[vy] = toInt(vLocationData[i + vy]);
			location[vz] = toInt(vLocationData[i + vz]);
			int[] normal = new int[3];
			normal[vx] = toInt(vNormalData[i + vx]);
			normal[vy] = toInt(vNormalData[i + vy]);
			normal[vz] = toInt(vNormalData[i + vz]);
			int bone = toInt(vBoneData[i / 3]);
			int material = toInt(vMaterialData[i / 3]);
			vertexes[i / 3] = new Vertex(location, normal, bone, material);
		}
		return vertexes;
	}
	
	static Face[] parseFaces(String rawData) throws IOException {
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
		if (materials.length > 0) {
			for (int i = 0; i < materials.length * 4; i+=4) {
				int r = toInt(mColorData[i]);
				int	g = toInt(mColorData[i+1]);
				int	b = toInt(mColorData[i+2]);
				int	a = toInt(mColorData[i+3]);
				materials[i/4] = new Material(ColorUtils.convert(r, g, b, a), new Texture(10, 10));
			}
		}else {
			materials = new Material[] {new Material(ColorUtils.convert(0, 0, 0, 255), new Texture(10, 10))};
		}
		return materials;
	}
	
	static Animation[] parseAnimations(String rawData) throws IOException {
		String[] rawAnimationsData = rawData.split("Animations<")[1].split(">Animations", 2)[0].split("Animation<");
//		int bonesCount = toInt(rawAnimationsData[0].split("BonesCount<")[1].split(">BonesCount")[0]);
		Animation[] animations = new Animation[rawAnimationsData.length-1];
		animations = new Animation[] {new Animation("Default", 0, 0, null)};
//		for (int i = 0; i < rawAnimationsData.length-1; i++) {
//			String animName = rawAnimationData.split("Name<")[1].split(">Name", 2)[0];
//			String[] rawBonesData = rawAnimationData.split("Bones<")[1].split(">Bones", 2)[0].split(",");
//			int framesCount = rawBonesData.length/(step * bonesCount);
//			Transform[] bones = new Transform[bonesCount*framesCount];
//			for (int j = 0; j < rawBonesData.length; j+=3) {
//				int px = toInt(rawBonesData[j + vx]);
//				int py = toInt(rawBonesData[j + vy]);
//				int pz = toInt(rawBonesData[j + vz]);
//				int rx = toInt(rawBonesData[j + vx]);
//				int ry = toInt(rawBonesData[j + vy]);
//				int rz = toInt(rawBonesData[j + vz]);
//				int sx = toInt(rawBonesData[j + vx]);
//				int sy = toInt(rawBonesData[j + vy]);
//				int sz = toInt(rawBonesData[j + vz]);
//				int[] pos = new int[] {px, py, pz};
//				int[] rot = new int[] {rx, ry, rz};
//				int[] scale = new int[] {sx, sy, sz};
//				bones[j/step] =  new Transform(pos, rot, scale);
//			}
//			animations[i] = new Animation(animName, framesCount, bonesCount, bones);
//		}
		return animations;
	}
	
	static int toInt(String string) {
		return Integer.parseInt(string);
	}
	
}
