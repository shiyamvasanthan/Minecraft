package models;

import org.lwjgl.util.vector.Vector3f;

public class Model2D {
	public static float[] vertices = {
		-0.5f, 0.5f, 0f,
		-0.5f, -0.5f, 0f,
		0.5f, -0.5f, 0f,
		0.5f, 0.5f, 0f,
	};
	
	public static int[] indices = {
		0,1,3,	
		3,1,2
	};
		
	public static float[] uv = {
		0, 0,
		0, 1,
		1, 1,
		1, 0
	};
}
