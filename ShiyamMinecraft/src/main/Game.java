package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import display.DisplayHandler;
import display.Loader;
import display.MainRenderer;
import entities.Camera;
import entities.Entity;
import models.AtlasCubeModel;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;

//This class has the main game loop
public class Game {
	public static Loader loader1 = null;
	public static StaticShader shader1 = null;
	
	//All the blocks in the world stored in this list
	static List<Chunk> chunks = Collections.synchronizedList(new ArrayList<Chunk>()); 
	
	//Stores the camera position
	static Vector3f camPos = new Vector3f(0, 0, 0);
	
	//Used positions of the terrain in the world
	static List<Vector3f> usedPos = new ArrayList<Vector3f>();
	
	static final int WORLD_SIZE = 5 * 16;
	
	public static void main(String[] args) {
		//Creates the display
		DisplayHandler.createDisplay();
		
		//Instantiate loader
		Loader loader = new Loader();
		loader1 = loader;
		
		//Shader
		StaticShader shader = new StaticShader();
		shader1 = shader;
		
		//Instantiate renderer
		MainRenderer renderer = new MainRenderer();
		
		//Create a sample shape using the vertices array above
		RawModel model = loader.loadToVAO(AtlasCubeModel.vertices, AtlasCubeModel.indices, AtlasCubeModel.uv);
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("grassTex"));
		
		TexturedModel texturedModel = new TexturedModel(model, texture);
				
		Camera camera = new Camera(new Vector3f(0, 0, 0), 0, 0, 0);
		
		//Create new thread for terrain generation
		new Thread(new Runnable() {public void run() {
			while(!Display.isCloseRequested()) {
				//Generate chunks
				for (int x = (int)(camPos.x - WORLD_SIZE)/16; x < (camPos.x + WORLD_SIZE)/16; x++) {
					for (int z = (int)(camPos.z - WORLD_SIZE)/16; z < (camPos.z + WORLD_SIZE)/16; z++) {
						//If the block doesn't already exist, add it to the world
						if (!usedPos.contains(new Vector3f(x*16, 0, z*16))) {
							List<Entity> blocks = new ArrayList<Entity>();
							
							for (int i = 0; i < 16; i++) {
								for (int j = 0; j < 16; j++) {
									blocks.add(new Entity(texturedModel, new Vector3f((x*16)+ i, 0, (z*16) + j), 0, 0, 0, 1));
								}
							}
							
							chunks.add(new Chunk(blocks, new Vector3f(x * 16, 0, z * 16)));
							
							usedPos.add(new Vector3f(x*16, 0, z*16));
						}
					}
				}
			}
		}}).start();
						
		//While the display is not closed
		while(!Display.isCloseRequested()) {
			//Check for keyboard inputs, if there are any move the camera
			camera.move();
			
			//Update camera position
			camPos = camera.getPosition();
									
			//Render the world blocks
			for (int i = 0; i < chunks.size(); i++) {
				Vector3f origin = chunks.get(i).getOrigin();
				
				int distX = (int) (camPos.x - origin.x);
				int distZ = (int) (camPos.z - origin.z);
				
				if (distX < 0) {
					distX = -distX;
				}
				
				if (distZ < 0) {
					distZ = -distZ;
				}
				
				if ((distX <= WORLD_SIZE) && (distZ <= WORLD_SIZE)) {
					for (int j = 0; j < chunks.get(i).getBlocks().size(); j++) {
						renderer.addEntity(chunks.get(i).getBlocks().get(j));
					}
				}
			}
			
			renderer.render(camera);
			
			//Update the display
			DisplayHandler.updateDisplay();
		}
		
		//Once the display is closed, terminate the game
		DisplayHandler.closeDisplay();
	}
}
