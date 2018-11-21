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
	
	/**
	 * Path to default models under the resources folder.
	 * Load them with:
	 * <br>
	 * <br>
	 * <code>
	 * SOMImporter.load(getClass().getResourceAsStream(Mesh.RESOURCES_));
	 * </code>
	 */
	public static final String RESOURCES_CUBE = "/cube.som",
								RESOURCES_CONE = "/cone.som",
								RESOURCES_CYLINDER = "/cylinder.som",
								RESOURCES_SPHERE = "/sphere.som",
								RESOURCES_PLANE = "/plane.som",
								RESOURCES_MONKEY = "/monkey.som",
								RESOURCES_TORUS = "/torus.som",
								RESOURCES_ALL = "/meshes.som";
	
	private Vertex[] vertexes;
	private Vertex[] vertexesBuffer;
	private Face[] faces;
	private Material[] materials;
	private Animation[] animations;
	private int currentAnimation;
	
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
		this.currentAnimation = 0;
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
		for (int i = 0; i < animations.length; i++) {
			if (animations[i].getName().equals(name))
				currentAnimation = i;
		}
	}
	
	/**
	 * Sets the animation with the given id as current animation of this mesh.
	 * 
	 * @param id id of animation.
	 */
	public void playAnimation(int id) {
		currentAnimation = id;
	}
	
	/**
	 * Returns the current animation of this mesh.
	 * 
	 * @return current animation of this mesh.
	 */
	public Animation getCurrentAnimation() {
		return animations[currentAnimation];
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
	 * @param name name of animation.
	 * @return animation at the given index.
	 */
	public Animation getAnimation(String name) {
		for (int i = 0; i < animations.length; i++) {
			if (animations[i].getName().equals(name))
				return animations[i];
		}
		return null;
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
