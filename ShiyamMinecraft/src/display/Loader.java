package display;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

//This class is responsible for loading all the VAOs and VBOs into memory
public class Loader {
	//List VAOs and VBOs
	static List<Integer> vaos = new ArrayList<Integer>();
	static List<Integer> vbos = new ArrayList<Integer>();
	static List<Integer> textures = new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] vertices, int[] indices, float[] uv) {
		//Create and bind a VAO
		int vaoID = createVAO();
		
		//Store the vertices data in VAO number 0, and set to 3 dimensions
		storeDataInAttributeList(vertices, 0, 3);
		
		//Store the texture coordinates data in VAO number 1, and set to 2 dimensions
		storeDataInAttributeList(uv, 1, 2);
				
		bindIndicesBuffer(indices);
		
		//Unbind the VAO
		GL30.glBindVertexArray(0);
		
		//Returns a new raw model object with the vao number and the number of vertices
		return new RawModel(vaoID, indices.length);
	}
	
	//This method creates and binds a VAO
	private int createVAO() {
		//Create a VAO
		int vaoID = GL30.glGenVertexArrays();
		
		//Add the VAO to the list of VAOs
		vaos.add(vaoID);
		
		//Bind VAO so it can be used
		GL30.glBindVertexArray(vaoID);
		
		//Return the VAO
		return vaoID;
	}
	
	public int loadTexture(String fileName) {
		Texture texture = null;
		
		//Get texture from file name
		try {
			texture = TextureLoader.getTexture("PNG", Loader.class.getResourceAsStream("/resource/" + fileName + ".PNG"));
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		
		//Add the textures to a list
		textures.add(textureID);
		
		return textureID;
		
	}
	private void storeDataInAttributeList(float[] data, int attributeNumber, int dimensions) {
		//Create a VBO
		int vboID = GL15.glGenBuffers();
		
		//Add the VBO to the list of VBOs
		vbos.add(vboID);
		
		//Bind the VBO so it can be used
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		//Takes the float array of vertices and converts it to a float buffer
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		
		//Store the float buffer in the VBO
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		//Stores the VBO in VAO
		GL20.glVertexAttribPointer(attributeNumber, dimensions, GL11.GL_FLOAT, false, 0, 0);
		
		//Unbind the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void bindIndicesBuffer(int[] indices) {
		//Create a VBO
		int vboID = GL15.glGenBuffers();
		
		//Add the VBO to the list of VBOs
		vbos.add(vboID);
		
		//Bind the VBO so it can be used
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		
		//Takes the integer array of indices and converts it to a integer buffer
		IntBuffer buffer = storeDataInIntBuffer(indices);
		
		//Store the integer buffer in the VBO
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		
		//Puts the data array into the float buffer
		buffer.put(data);
		
		//Make the buffer readable
		buffer.flip();
		
		//Return the float buffer
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		//Creates a float buffer with the same length as the data array
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		
		//Puts the data array into the float buffer
		buffer.put(data);
		
		//Make the buffer readable
		buffer.flip();
		
		//Return the float buffer
		return buffer;
	}
	
	//Deletes all the vaos and vbos when needed
	public void cleanUp() {
		//For each vao in the vaos list, delete it to free up memory
		for(int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		//For each vbo in the vbos list, delete it to free up memory
		for(int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		
		//For each texture in the textures list, delete it to free up memory
		for(int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
}
