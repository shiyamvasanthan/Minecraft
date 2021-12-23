package models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class CubeModel {
	public static Vector3f[] positiveX = {
		new Vector3f(0.5f,0.5f,-0.5f),
		new Vector3f(0.5f,-0.5f,-0.5f),
		new Vector3f(0.5f,-0.5f,0.5f),
		new Vector3f(0.5f,-0.5f,0.5f),
		new Vector3f(0.5f,0.5f,0.5f),
		new Vector3f(0.5f,0.5f,-0.5f)
	};
	
	public static Vector3f[] negativeX = {
		new Vector3f(-0.5f,0.5f,-0.5f),
		new Vector3f(-0.5f,-0.5f,-0.5f),
		new Vector3f(-0.5f,-0.5f,0.5f),
		new Vector3f(-0.5f,-0.5f,0.5f),
		new Vector3f(-0.5f,0.5f,0.5f),
		new Vector3f(-0.5f,0.5f,-0.5f)
	};
	
	public static Vector3f[] positiveY = {
		new Vector3f(-0.5f,0.5f,0.5f),
		new Vector3f(-0.5f,0.5f,-0.5f),
		new Vector3f(0.5f,0.5f,-0.5f),
		new Vector3f(0.5f,0.5f,-0.5f),
		new Vector3f(0.5f,0.5f,0.5f),
		new Vector3f(-0.5f,0.5f,0.5f)
	};
	
	public static Vector3f[] negativeY = {
		new Vector3f(-0.5f,-0.5f,0.5f),
		new Vector3f(-0.5f,-0.5f,-0.5f),
		new Vector3f(0.5f,-0.5f,-0.5f),
		new Vector3f(0.5f,-0.5f,-0.5f),
		new Vector3f(0.5f,-0.5f,0.5f),
		new Vector3f(-0.5f,-0.5f,0.5f)
	};
	
	public static Vector3f[] positiveZ = {
		new Vector3f(-0.5f,0.5f,0.5f),
		new Vector3f(-0.5f,-0.5f,0.5f),
		new Vector3f(0.5f,-0.5f,0.5f),
		new Vector3f(0.5f,-0.5f,0.5f),
		new Vector3f(0.5f,0.5f,0.5f),
		new Vector3f(-0.5f,0.5f,0.5f)
	};
	
	public static Vector3f[] negativeZ = {
		new Vector3f(-0.5f,0.5f,-0.5f),
		new Vector3f(-0.5f,-0.5f,-0.5f),
		new Vector3f(0.5f,-0.5f,-0.5f),
		new Vector3f(0.5f,-0.5f,-0.5f),
		new Vector3f(0.5f,0.5f,-0.5f),
		new Vector3f(-0.5f,0.5f,-0.5f)
	};
	
	public static Vector2f[] UV = {
		new Vector2f(0.f, 0.f),
		new Vector2f(0.f, 1.f),
		new Vector2f(1.f, 1.f),
		new Vector2f(1.f, 1.f),
		new Vector2f(1.f, 0.f),
		new Vector2f(0.f, 0.f)
	};
	
	//Front face
	public static Vector2f[] UV_positiveX = {
		//Grass
		new Vector2f(3f/16f, 0.0f),
		new Vector2f(3f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 0.0f),
		new Vector2f(3f/16f, 0.0f)
	};
	
	//Back face
	public static Vector2f[] UV_negativeX = {
		//Grass
		new Vector2f(3f/16f, 0.0f),
		new Vector2f(3f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 0.0f),
		new Vector2f(3f/16f, 0.0f)
	};
	
	//Top face
	public static Vector2f[] UV_positiveY = {
		//Grass
		new Vector2f(0f/16f, 0f/16f),
		new Vector2f(0f/16f, 1f/16f),
		new Vector2f(1f/16f, 1f/16f),
		new Vector2f(1f/16f, 1f/16f),
		new Vector2f(1f/16f, 0f/16f),
		new Vector2f(0f/16f, 0f/16f)
	};
	
	//Bottom face
	public static Vector2f[] UV_negativeY = {
		//Grass
		new Vector2f(2f/16f, 0.0f),
		new Vector2f(2f/16f, 1f/16f),
		new Vector2f(3f/16f, 1f/16f),
		new Vector2f(3f/16f, 1f/16f),
		new Vector2f(3f/16f, 0.0f),
		new Vector2f(2f/16f, 0.0f)
	};
	
	//Left face
	public static Vector2f[] UV_positiveZ = {
		//Grass
		new Vector2f(3f/16f, 0.0f),
		new Vector2f(3f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 0.0f),
		new Vector2f(3f/16f, 0.0f)
	};
	
	//Right face
	public static Vector2f[] UV_negativeZ = {
		//Grass
		new Vector2f(3f/16f, 0.0f),
		new Vector2f(3f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 1f/16f),
		new Vector2f(4f/16f, 0.0f),
		new Vector2f(3f/16f, 0.0f)
	};
	
	public static Vector3f[] NORMALS = {
		new Vector3f(0.f, 0.f, 0.f),
		new Vector3f(0.f, 0.f, 0.f),
		new Vector3f(0.f, 0.f, 0.f),
		new Vector3f(0.f, 0.f, 0.f),
		new Vector3f(0.f, 0.f, 0.f),
		new Vector3f(0.f, 0.f, 0.f)
	};
	
	//Float array of vertices (x, y, z)
	public static float[] vertices = {
		-0.5f,0.5f,-0.5f,	
		-0.5f,-0.5f,-0.5f,	
		0.5f,-0.5f,-0.5f,	
		0.5f,0.5f,-0.5f,		
		
		-0.5f,0.5f,0.5f,	
		-0.5f,-0.5f,0.5f,	
		0.5f,-0.5f,0.5f,	
		0.5f,0.5f,0.5f,
		
		0.5f,0.5f,-0.5f,	
		0.5f,-0.5f,-0.5f,	
		0.5f,-0.5f,0.5f,	
		0.5f,0.5f,0.5f,
		
		-0.5f,0.5f,-0.5f,	
		-0.5f,-0.5f,-0.5f,	
		-0.5f,-0.5f,0.5f,	
		-0.5f,0.5f,0.5f,
		
		-0.5f,0.5f,0.5f,
		-0.5f,0.5f,-0.5f,
		0.5f,0.5f,-0.5f,
		0.5f,0.5f,0.5f,
		
		-0.5f,-0.5f,0.5f,
		-0.5f,-0.5f,-0.5f,
		0.5f,-0.5f,-0.5f,
		0.5f,-0.5f,0.5f			
	};
	
	public static int[] indices = {
		0,1,3,	
		3,1,2,	
		4,5,7,
		7,5,6,
		8,9,11,
		11,9,10,
		12,13,15,
		15,13,14,	
		16,17,19,
		19,17,18,
		20,21,23,
		23,21,22
	};
	
	public static float[] uv = {
		0, 0,
		0, 1,
		1, 1,
		1, 0,
		
		0, 0,
		0, 1,
		1, 1,
		1, 0,
		
		0, 0,
		0, 1,
		1, 1,
		1, 0,
		
		0, 0,
		0, 1,
		1, 1,
		1, 0,
		
		0, 0,
		0, 1,
		1, 1,
		1, 0,
		
		0, 0,
		0, 1,
		1, 1,
		1, 0
	};
}
