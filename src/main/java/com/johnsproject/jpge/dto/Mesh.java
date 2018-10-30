package com.johnsproject.jpge.dto;

import java.util.Arrays;

import com.johnsproject.jpge.graphics.Renderer;
import com.johnsproject.jpge.io.SOMImporter;
import com.johnsproject.jpge.utils.VectorUtils;

/**
 * The Mesh class contains the data of {@link SceneObject} meshes imported by {@link SOMImporter}. 
 * It contains each {@link Vertex}, polygon/triangle, {@link Material} and {@link Animation} of a {@link SceneObject}.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 */
public class Mesh {
	private static final int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	
	/** Values containing location of data in a vertex. This is used by the SOM importer when parsing values. */
	public static final byte V_NORMAL = 3, V_BONE_INDEX = 6, V_MATERIAL_INDEX = 7;
	/** Default vertex length. This is used by the SOM importer when parsing values. */
	public static final byte VERTEX_LENGTH = 8;
	private Vertex[] vertexes;
	private Vertex[] vertexesBuffer;
	/** Values containing location of data in a face. This is used by the SOM importer when parsing values. */
	public static final byte F_VERTEX_1 = 0, F_VERTEX_2 = 1, F_VERTEX_3 = 2, 
							F_UV_1 = 4, F_UV_2 = 6, F_UV_3 = 8,
							F_MATERIAL_INDEX = 3, F_CULLED = 10;
	/** Default face length. This is used by the SOM importer when parsing values. */
	public static final byte FACE_LENGTH = 11;
	private Face[] faces;
	/** Default material length. This is used by the SOM importer when parsing values. */
	public static final byte MATERIAL_LENGTH = 4;
	private Material[] materials;
	/** Values containing location of data in a bone. This is used by the SOM importer when parsing values. */
	public static final byte POSITION = 0, ROTATION = 3, SCALE = 6;
	/** Default bone length. This is used by the SOM importer when parsing values. */
	public static final byte BONE_LENGTH = 9;
	private Animation[] animations;
	private Animation currentAnimation;
	
	/**
	 * Creates a new instance of the Mesh class filled with the given values.
	 * 
	 * @param vertexes an array of {@link Vertex Vertexes}.
	 * @param faces an array of faces.
	 * @param materials an array of {@link Material Materials}.
	 * @param animations an array of {@link Animation Animations}.
	 */
	public Mesh (Vertex[] vertexes, Face[] faces, Material[] materials, Animation[] animations) {
		this.vertexes = vertexes;
		this.vertexesBuffer = new Vertex[vertexes.length];
		resetBuffer();
		this.faces = faces;
		this.materials = materials;
		this.animations = animations;
		this.currentAnimation = animations[0];
	}
	
	/**
	 * Returns all vertexes of this mesh.
	 * 
	 * @return all vertexes of this mesh.
	 */
	public Vertex[] getVertexes(){
		return vertexes;
	}
	
	/**
	 * Returns the vertex at the given index.
	 * 
	 * @param index index of vertex.
	 * @return vertex at the given index.
	 */
	public Vertex getVertex(int index){
		return vertexes[index];
	}
	
	/**
	 * Returns all buffered vertexes of this mesh.
	 * The buffered vertexes are used by the {@link Renderer} at the rendering process.
	 * This vertexes are transformed, rotated and projected. 
	 * the vertex buffer is a buffer used to prevent loosing original position of vertexes.
	 * 
	 * @return all buffered vertexes of this mesh.
	 */
	public Vertex[] getBufferedVertexes(){
		return vertexesBuffer;
	}
	
	/**
	 * Returns the buffered vertex at the given index.
	 * The buffered vertexes are used by the {@link Renderer} at the rendering process.
	 * This vertexes are transformed, rotated and projected. 
	 * the vertex buffer is a buffer used to prevent loosing original position of vertexes.
	 * 
	 * 
	 * @param index index of buffered vertex.
	 * @return buffered vertex at the given index.
	 */
	public Vertex getBufferedVertex(int index){
		return vertexesBuffer[index];
	}
	
	/**
	 * Resets the vertex buffer of this mesh.
	 * the vertex buffer is a buffer used to prevent loosing original position of vertexes.
	 */
	public void resetBuffer() {
		for (int i = 0; i < vertexes.length; i++) {
			if (vertexesBuffer[i] == null) {
				vertexesBuffer[i] = new Vertex(
						vertexes[i].getPosition().clone(),
						vertexes[i].getNormal().clone(),
						vertexes[i].getBone(),
						vertexes[i].getMaterial()
				);
			}else {
				vertexesBuffer[i].getPosition()[vx] = vertexes[i].getPosition()[vx];
				vertexesBuffer[i].getPosition()[vy] = vertexes[i].getPosition()[vy];
				vertexesBuffer[i].getPosition()[vz] = vertexes[i].getPosition()[vz];
				vertexesBuffer[i].getNormal()[vx] = vertexes[i].getNormal()[vx];
				vertexesBuffer[i].getNormal()[vy] = vertexes[i].getNormal()[vy];
				vertexesBuffer[i].getNormal()[vz] = vertexes[i].getNormal()[vz];
			}
		}
	}
	
	/**
	 * Returns all faces of this mesh.
	 * 
	 * @return all faces of this mesh.
	 */
	public Face[] getFaces() {
		return faces;
	}
	
	/**
	 * Returns the faces at the given index.
	 * 
	 * @param index index of polygon.
	 * @return the faces at the given index.
	 */
	public Face getFace(int index) {
		return faces[index];
	}
	
	/**
	 * Returns all materials of this mesh.
	 * 
	 * @return all materials of this mesh.
	 */
	public Material[] getMaterials() {
		return materials;
	}
	
	/**
	 * Returns the materials at the given index.
	 * 
	 * @param index index of material.
	 * @return the material at the given index.
	 */
	public Material getMaterial(int index) {
		return materials[index];
	}
	
	/**
	 * Sets the animation with the given name as current animation of this mesh.
	 * 
	 * @param name name of animation.
	 */
	public void playAnimation(String name) {
		currentAnimation = getAnimation(name);
	}
	
	/**
	 * Sets the animation with the given id as current animation of this mesh.
	 * 
	 * @param id id of animation.
	 */
	public void playAnimation(int id) {
		currentAnimation = animations[id];
	}
	
	/**
	 * Sets the given animation as current animation of this mesh.
	 * 
	 * @param animation animation to play.
	 */
	public void playAnimation(Animation animation) {
		currentAnimation = animation;
	}
	
	/**
	 * Returns the current animation of this mesh.
	 * 
	 * @return current animation of this mesh.
	 */
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	/**
	 * Returns the animation at the given index.
	 * 
	 * @param index index of animation.
	 * @return animation at the given index.
	 */
	public Animation getAnimation(int index) {
		return animations[index];
	}
	
	/**
	 * Returns the animation with the given name.
	 * 
	 * @param index index of animation.
	 * @return animation at the given index.
	 */
	public Animation getAnimation(String name) {
		Animation anim = null;
		for (Animation animation : animations) {
			if (animation.getName() == name) anim = animation;
		}
		return anim;
	}
	
	/**
	 * Returns all animations of this mesh.
	 * 
	 * @return all animations of this mesh.
	 */
	public Animation[] getAnimations() {
		return animations;
	}

	@Override
	public String toString() {
		return "Mesh [vertexes=" + Arrays.toString(vertexes) + ", vertexesBuffer=" + Arrays.toString(vertexesBuffer)
				+ ", polygons=" + Arrays.toString(faces) + ", materials=" + Arrays.toString(materials)
				+ ", animations=" + Arrays.toString(animations) + ", currentAnimation=" + currentAnimation + "]";
	}	
}
