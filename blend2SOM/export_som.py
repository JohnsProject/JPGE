import bpy
import math
import bmesh

bonesCount = 0
materialsCount = 0

def write(filepath,
			applyMods=False
			):
#   bpy.ops.object.mode_set(mode = 'EDIT')
	scene = bpy.context.scene
	meshData = MeshData()
	animsData = []
	bones = []
	for obj in bpy.context.visible_objects:
		if obj.pose is not None:
			for bone in obj.pose.bones:
				bones.append(bone)
	global bonesCount
	bonesCount = len(bones)
	global materialsCount
	materialsCount = 0
	for obj in bpy.context.visible_objects:
		if applyMods or obj.type != "MESH":
			try:
				me = obj.to_mesh(scene, True, "PREVIEW")
			except:
				me = None
			is_tmp_mesh = True
		else:
			try:
				me = obj.to_mesh(scene, False, "PREVIEW")
			except:
				me = None
			is_tmp_mesh = True
		if obj.animation_data is not None:
			for track in obj.animation_data.nla_tracks:
				for strip in track.strips:
					action = strip.action
					obj.animation_data.action = action
					animData = AnimData(action.name)
					for i in range(int(action.frame_range[0]), int(action.frame_range[1])):
						bpy.context.scene.frame_set(i)
						bpy.context.scene.update()
						for bone in bones:
						   animData.addBone(bone)
					animsData.append(animData)  
		if me is not None:
			bm = bmesh.new()
			bm.from_mesh(me)
			#bmesh.ops.subdivide_edges(bm, edges=bm.edges, use_grid_fill=True, cuts=1)
			bmesh.ops.triangulate(bm, faces=bm.faces)
			bm.to_mesh(me)
			bm.free()
			del bm
			for vertex in me.vertices:
				meshData.addVertex(vertex.co[0]*1000)
				meshData.addVertex(vertex.co[1]*1000)
				meshData.addVertex(vertex.co[2]*1000)
				meshData.addVertex(vertex.normal[0]*1000)
				meshData.addVertex(vertex.normal[1]*1000)
				meshData.addVertex(vertex.normal[2]*1000)
				found = 0
				for group in vertex.groups:
					i = 0
					for bone in bones:
						if obj.vertex_groups[group.group].name == bone.name:
							found = i
						i+=1
				meshData.addVertex(found)
				# empty value as place for material index later
				meshData.addVertex(0)
			for polygon in me.polygons:
				meshData.addPolygon(polygon, me)
			for mat_slot in obj.material_slots:
				meshData.addMaterial(mat_slot)  	  
			if is_tmp_mesh:
				bpy.data.meshes.remove(me)
			materialsCount += len(obj.material_slots)
	writeToFile(filepath, meshData, animsData)


def writeToFile(filepath, meshData, animsData):
	# open target file
	file = open(filepath, "w")
	i = 0
	# write the commons to the file
	commons = "".join("SOM (SceneObjectMesh) file created by Blender SOM exporter version 1.2"
						+ "\n" + "project page: https://github.com/JohnsProject/JPGE" + "\n" 
						+ "\n" + "Wiki : "
						+ "\n" + " Vertexes contains the vertex data (x, y, z, normal_x, normal_y, normal_z, boneIndex, material) of all visible objects in the scene"
						+ "\n" + " Faces contains the face data (vertex1, vertex2, vertex3, material, uv1_x, uv1_y, uv2_x, uv2_y, uv3_x, uv3_y) of all visible objects in the scene."
						+ "\n" + " Materials contains the material (red, green, blue, alpha) data of all visible objects in the scene."
						+ "\n" + " Animations contains the animation data of all visible objects in the scene,"
						+ "\n" + " a animation contains the data of bones at each keyframe and a bone is composed of (px, py, pz, rx, ry, rz, sx, sy, sz),"
						+ "\n" + " where p = position, r = rotation and s = scale." + "\n" + "\n")
	file.write(commons)
	# write the vertexes to the file
	i = 0
	file.write("Vertexes < ")
	for value in meshData.vertexes:
		i += 1
		if (i < len(meshData.vertexes)):
			file.write("%i," % value)
		else:
			file.write(("%i" % value))
	file.write(" > Vertexes" + "\n\n")
	# write the faces to the file
	i = 0
	file.write("Faces < ")
	for value in meshData.polygons:
		i += 1
		if (i < len(meshData.polygons)):
			file.write("%i," % value)
		else:
			file.write(("%i" % value))
	file.write(" > Faces" + "\n\n")
	# write the materials to the file
	i = 0
	file.write("Materials < ")
	for value in meshData.materials:
		i += 1
		if (i < len(meshData.materials)):
			file.write("%i," % value)
		else:
			file.write(("%i" % value))
	
	file.write(" > Materials" + "\n\n")
	# write the animations to the file
	file.write("Animations < " + "\n")
	global bonesCount
	file.write((" BonesCount <%i" % bonesCount) + "> BonesCount \n")
	for animData in animsData:
		file.write(" Animation < " + "\n")
		file.write("  Name < " + animData.name + "> Name \n")
		file.write("  Bones < ")
		i = 0
		for value in animData.bones:
			i += 1
			if (i < len(animData.bones)):
				file.write("%i," % value)
			else:
				file.write(("%i" % value))
		
		file.write(" > Bones" + "\n")
		file.write(" > Animation " + "\n")
	file.write("> Animations" + "\n")
	
	# close file
	file.close()

class MeshData:

	def __init__(self):
		self.vertexes = []
		self.polygons = []
		self.materials = []
		
	def addVertex(self, value):
		self.vertexes.append(value)
		
	def addPolygon(self, value, me):
		self.polygons.append(value.vertices[0])
		self.polygons.append(value.vertices[1])
		self.polygons.append(value.vertices[2])
		global materialsCount
		self.polygons.append(materialsCount + value.material_index)
		self.vertexes[(value.vertices[0] * 8) + 7] = materialsCount + value.material_index
		self.vertexes[(value.vertices[1] * 8) + 7] = materialsCount + value.material_index
		self.vertexes[(value.vertices[2] * 8) + 7] = materialsCount + value.material_index
		if me.uv_layers.active is not None:
			self.polygons.append(me.uv_layers.active.data[value.loop_indices[0]].uv[0]*128)
			self.polygons.append(me.uv_layers.active.data[value.loop_indices[0]].uv[1]*128)
			self.polygons.append(me.uv_layers.active.data[value.loop_indices[1]].uv[0]*128)
			self.polygons.append(me.uv_layers.active.data[value.loop_indices[1]].uv[1]*128)
			self.polygons.append(me.uv_layers.active.data[value.loop_indices[2]].uv[0]*128)
			self.polygons.append(me.uv_layers.active.data[value.loop_indices[2]].uv[1]*128)
		else:
			self.polygons.append(0)
			self.polygons.append(0)
			self.polygons.append(0)
			self.polygons.append(0)
			self.polygons.append(0)
			self.polygons.append(0)
	
	def addMaterial(self, value):
		if value.material is not None:
			self.materials.append(value.material.diffuse_color[0] * 255)
			self.materials.append(value.material.diffuse_color[1] * 255)
			self.materials.append(value.material.diffuse_color[2] * 255)
			self.materials.append(value.material.alpha * 255)

class AnimData:

	def __init__(self, name):
		self.name = name
		self.bones = []

	def addBone(self, bone):
		self.bones.append(bone.head[0]*100)
		self.bones.append(bone.head[1]*100)
		self.bones.append(bone.head[2]*100)
		bone.rotation_mode = 'XYZ'
		self.bones.append(math.degrees(bone.rotation_euler[0]))
		self.bones.append(math.degrees(bone.rotation_euler[1]))
		self.bones.append(math.degrees(bone.rotation_euler[2]))
		self.bones.append(bone.scale[0])
		self.bones.append(bone.scale[1])
		self.bones.append(bone.scale[2])
