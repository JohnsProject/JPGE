package com.johnsproject.jpge.graphics;

import java.util.Arrays;

import com.johnsproject.jpge.utils.VectorMathUtils;
import com.johnsproject.jpge.utils.VectorUtils;

public class Mesh {
	
	public static final byte BONE_INDEX = 3, SHADE_FACTOR = 4;
	public static final byte VERTEX_LENGTH = 5;
	private int[][] vertexes;
	private int[][] vertexesBuffer;
	public static final byte VERTEX_1 = 0, VERTEX_2 = 1, VERTEX_3 = 2;
	public static final byte UV_1 = 4, UV_2 = 5, UV_3 = 6;
	public static final byte MATERIAL_INDEX = 3;
	public static final byte POLYGON_LENGTH = 7;
	private int[][] polygons;
	public static final byte UV_LENGTH = 2;
	private int[][] uvs;
	public static final byte MATERIAL_LENGTH = 4;
	private Material[] materials;
	public static final byte POSITION = 0, ROTATION = 3, SCALE = 6;
	public static final byte BONE_LENGTH = 9;
	private Animation[] animations;
	private Animation currentAnimation;
	private int radius;
	
	public Mesh (int[][] vertexes, int[][] polygons, int[][] uvs, Material[] materials, Animation[] animations) {
		this.vertexes = vertexes;
		this.vertexesBuffer = new int[vertexes.length][VERTEX_LENGTH];
		this.polygons = polygons;
		this.uvs = uvs;
		this.materials = materials;
		this.animations = animations;
		this.currentAnimation = animations[0];
		this.radius = VectorMathUtils.getRadius(vertexes);
	}
	
	public int [][] getVertexes(){
		return vertexes;
	}
	
	public int [] getVertex(int index){
		return vertexes[index];
	}
	
	public int getRadius(){
		return radius;
	}
	
	public int [][] getUVs(){
		return uvs;
	}
	
	public int [] getUV(int index){
		if (uvs.length < 2) return uvs[0];
		return uvs[index];
	}
	
	public int [][] getBufferedVertexes(){
		return vertexesBuffer;
	}
	
	public int [] getBufferedVertex(int index){
		return vertexesBuffer[index];
	}
	
	public void resetBuffer() {
		for (int i = 0; i < vertexes.length; i++) {
			VectorUtils.match(vertexesBuffer[i], vertexes[i]);
		}
	}
	
	public int[][] getPolygons() {
		return polygons;
	}
	
	public int[] getPolygon(int index) {
		return polygons[index];
	}
	
	public Material[] getMaterials() {
		return materials;
	}
	
	public Material getMaterial(int index) {
		return materials[index];
	}
	
	public void playAnimation(String name) {
		currentAnimation = getAnimation(name);
	}
	
	public void playAnimation(int id) {
		currentAnimation = animations[id];
	}
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public Animation getAnimation(int index) {
		return animations[index];
	}
	
	public Animation getAnimation(String name) {
		Animation anim = null;
		for (Animation animation : animations) {
			if (animation.getName() == name) anim = animation;
		}
		return anim;
	}
	
	public Animation[] getAnimations() {
		return animations;
	}

	@Override
	public String toString() {
		return "Mesh [vertexes=" + Arrays.toString(vertexes) + ", vertexesBuffer=" + Arrays.toString(vertexesBuffer)
				+ ", polygons=" + Arrays.toString(polygons) + ", materials=" + Arrays.toString(materials)
				+ ", animations=" + Arrays.toString(animations) + "]";
	}
	
	
}
