package com.johnsproject.jpge.dto;

import org.junit.Test;

import com.johnsproject.jpge.io.FileIO;

public class SerializeDtoTest {

	private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/java/test.txt";
	
	@Test
	public void serializeAnimationTest() throws Exception {
		Animation object = new Animation("Hey", 10, 50, new Transform[10]);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Animation rObject = (Animation) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeCameraTest() throws Exception {
		Camera object = new Camera("Ha", new Transform(), 10, 50, 100, 100);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Camera rObject = (Camera) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeFaceTest() throws Exception {
		int[] value = new int[] {5, 2};
		Face object = new Face(1, 2, 3, 10, value, value, value);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Face rObject = (Face) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeLightTest() throws Exception {
		Light object = new Light("Hey", new Transform());
		FileIO.writeObjectToFile(FILE_PATH, object);
		Light rObject = (Light) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeMaterialTest() throws Exception {
		Material object = new Material(10, new Texture());
		FileIO.writeObjectToFile(FILE_PATH, object);
		Material rObject = (Material) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeMeshTest() throws Exception {
		Mesh object = new Mesh(new Vertex[5], new Face[4], new Material[5], new Animation[2]);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Mesh rObject = (Mesh) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializePhysicssettingsTest() throws Exception {
		int[] value = new int[] {5, 4, 7};
		PhysicsSettings object = new PhysicsSettings();
		object.setGravity(value);
		FileIO.writeObjectToFile(FILE_PATH, object);
		PhysicsSettings rObject = (PhysicsSettings) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeRenderbufferTest() throws Exception {
		RenderBuffer object = new RenderBuffer(500, 100);
		FileIO.writeObjectToFile(FILE_PATH, object);
		RenderBuffer rObject = (RenderBuffer) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeRigidbodyTest() throws Exception {
		Rigidbody object = new Rigidbody();
		object.setColliderRadius(50);
		object.setCollisionType(Rigidbody.COLLISION_MESH);
		object.setKinematic(true);
		object.setMass(1000);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Rigidbody rObject = (Rigidbody) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeSceneTest() throws Exception {
		Scene object = new Scene();
		object.addSceneObject(new SceneObject());
		object.addCamera(new Camera());
		object.addLight(new Light());
		FileIO.writeObjectToFile(FILE_PATH, object);
		Scene rObject = (Scene) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeSceneObjectTest() throws Exception {
		int[] value = new int[] {5, 4, 7};
		SceneObject object = new SceneObject("test", new Transform(value, value, value), new Mesh());
		FileIO.writeObjectToFile(FILE_PATH, object);
		SceneObject rObject = (SceneObject) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeTextureTest() throws Exception {
		Texture object = new Texture(100, 100);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Texture rObject = (Texture) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeTransformTest() throws Exception {
		int[] value = new int[] {5, 4, 7};
		Transform object = new Transform(value, value, value);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Transform rObject = (Transform) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
	@Test
	public void serializeVertexTest() throws Exception {
		int[] value = new int[] {5, 4, 7};
		Vertex object = new Vertex(value, value, 0, 10);
		FileIO.writeObjectToFile(FILE_PATH, object);
		Vertex rObject = (Vertex) FileIO.readObjectFromFile(FILE_PATH);
		assert(rObject.equals(object));
	}
	
}
