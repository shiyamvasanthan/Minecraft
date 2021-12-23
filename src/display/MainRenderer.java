package display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import shaders.StaticShader;

//This class handles all the primary rendering
public class MainRenderer {
	Matrix4f projectionMatrix;
	
	//FOV 70 degrees
	private static final float FOV = 70f;
	
	//Closest object view distance
	private static final float NEAR_PLANE = 0.1f;
	
	//Max view distance
	private static final float FAR_PLANE = 10000f;
	
	StaticShader shader = new StaticShader();
	EntityRenderer renderer = new EntityRenderer();
	
	Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	//Projection matrix doesn't slow down the game because it is loaded only once
	public MainRenderer() {
		//GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glCullFace(GL11.GL_BACK);
		
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	//Prepare the frame to render the next frame
	public void prepare() {
		//Render vertices in the correct order
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		//Clear the color buffer and depth buffer to render the next frame
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		//Set the background to the color of the sky
		GL11.glClearColor(0.4f, 0.7f, 1.0f, 1);
		
	}
	
	//Renders the models
	public void render(Camera camera) {
		prepare();
		shader.start();
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		
		//Clear the entites each frame
		entities.clear();
	}
	
	public void addEntity(Entity entity) {
		TexturedModel model = entity.getModel();
		
		List<Entity> batch = entities.get(model);
		
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model, newBatch);
		}
	}
	
	public void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		
		float aspect = (float)Display.getWidth()/(float)Display.getHeight();
		float yScale = (float)(1f/Math.tan(Math.toRadians(FOV/2f)));
		float xScale = yScale/aspect;
		float zp = FAR_PLANE + NEAR_PLANE;
		float zm = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix.m00 = xScale;
		projectionMatrix.m11 = yScale;
		projectionMatrix.m22 = -zp/zm;
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -(2 * FAR_PLANE * NEAR_PLANE)/zm;
		projectionMatrix.m33 = 0;
	}
}
