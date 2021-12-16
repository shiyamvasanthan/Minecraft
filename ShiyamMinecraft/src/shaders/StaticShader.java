package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{

	private static final String vertexFile = "/shaders/vertexShader.txt";
	private static final String fragmentFile = "/shaders/fragmentShader.txt";
	
	int location_transformationMatrix;
	int location_projectionMatrix;
	int location_viewMatrix;
	
	public StaticShader() {
		super(vertexFile, fragmentFile);
	}

	protected void bindAttributes() {
		//Input the position variable from the vertexShader text file, VAO 0
		super.bindAttribute("position", 0);
		
		//Input the textureCoords variable from the vertexShader text file, VAO 1
		super.bindAttribute("textureCoords", 1);
	}

	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
	}
}
