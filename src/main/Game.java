package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import chunk.Chunk;
import chunk.ChunkMesh;
import cube.Block;
import display.DisplayHandler;
import display.Loader;
import display.MainRenderer;
import entities.Camera;
import entities.Entity;
import models.CubeModel;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.PerlinNoiseGenerator;

//This class has the main game loop
public class Game {
	public static Loader loader1 = null;
	public static StaticShader shader1 = null;
	
	//All the blocks in the world stored in this list
	static List<ChunkMesh> chunks = Collections.synchronizedList(new ArrayList<ChunkMesh>()); 
	
	//Stores the camera position
	static Vector3f camPos = new Vector3f(0, 0, 0);
	
	//Used positions of the terrain in the world
	static List<Vector3f> usedPos = new ArrayList<Vector3f>();
	
	static List<Entity> entities = new ArrayList<Entity>();
	
	//Render distance
	static final int WORLD_SIZE = 5 * 32;
	
	//The native library for lwjgl
	static File nativeLibrary;
	
	//Method that will set the native path for lwjgl depending on the current operating system
	public static void platformCheck() {
		switch(LWJGLUtil.getPlatform())
		{
		    case LWJGLUtil.PLATFORM_WINDOWS:
		    {
		    	nativeLibrary = new File("./native/windows/");
		    }
		    break;

		    case LWJGLUtil.PLATFORM_LINUX:
		    {
		    	nativeLibrary = new File("./native/linux/");
		    }
		    break;

		    case LWJGLUtil.PLATFORM_MACOSX:
		    {
		    	nativeLibrary = new File("./native/macosx/");
		    }
		    break;
		}

		System.setProperty("org.lwjgl.librarypath", nativeLibrary.getAbsolutePath());
	}
	
	public static void main(String[] args) {
		//Checks what operating system the game is running on
		platformCheck();
		
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
		RawModel model = loader.loadToVAO(CubeModel.vertices, CubeModel.indices, CubeModel.uv);
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("minecraft_sheet"));
		
		TexturedModel texturedModel = new TexturedModel(model, texture);
				
		Camera camera = new Camera(new Vector3f(0, 0, 0), 0, 0, 0);
		
		PerlinNoiseGenerator generator = new PerlinNoiseGenerator();
		
		//Create new thread for terrain generation
		new Thread(new Runnable() {public void run() {
			while(!Display.isCloseRequested()) {
				//Generate chunks
				for (int x = (int)(camPos.x - WORLD_SIZE)/32; x < (camPos.x + WORLD_SIZE)/32; x++) {
					for (int z = (int)(camPos.z - WORLD_SIZE)/32; z < (camPos.z + WORLD_SIZE)/32; z++) {
						//If the block doesn't already exist, add it to the world
						if (!usedPos.contains(new Vector3f(x * 32, 0 * 32, z * 32))) {
							List<Block> blocks = new ArrayList<Block>();
							
							for (int i = 0; i < 32; i++) {
								for (int j = 0; j < 32; j++) {
									blocks.add(new Block(i, (int)generator.generateHeight(i + (x * 32), j + (z * 32)), j, Block.GRASS));
								}
							}
							
							Chunk chunk = new Chunk(blocks, new Vector3f(x * 32, 0 * 32, z * 32));
							ChunkMesh mesh = new ChunkMesh(chunk);
							
							chunks.add(mesh);
							
							usedPos.add(new Vector3f(x * 32, 0 * 32, z * 32));
						}
					}
				}
			}
		}}).start(); 
				
		//Main Game Loop
		int index = 0;
		while(!Display.isCloseRequested()) {
			//Check for keyboard inputs, if there are any move the camera
			camera.move();
			
			//Update camera position
			camPos = camera.getPosition();
				
			if (index < chunks.size()) {				
				RawModel model123 = loader.loadToVAO(chunks.get(index).positions, chunks.get(index).uvs);
				TexturedModel texturedModel123 = new TexturedModel(model123, texture);
				Entity entity = new Entity(texturedModel123, chunks.get(index).chunk.origin, 0, 0, 0, 1); 
				entities.add(entity);
				
				//Once data is loaded into GPU, it doesn't need to be in RAM anymore
				chunks.get(index).positions = null;
				chunks.get(index).normals = null;
				chunks.get(index).uvs = null;
				
				index++;
			}
			
			//Render the world blocks
			for (int i = 0; i < entities.size(); i++) {
				Vector3f origin = entities.get(i).getPosition();
				
				int distX = (int) (camPos.x - origin.x);
				int distZ = (int) (camPos.z - origin.z);
				
				if (distX < 0) {
					distX = -distX;
				}
				
				if (distZ < 0) {
					distZ = -distZ;
				}
				
				if ((distX <= WORLD_SIZE) && (distZ <= WORLD_SIZE)) {
					renderer.addEntity(entities.get(i));
				}
			}
			
			//renderer.addEntity(entity);
			renderer.render(camera);
			
			//Update the display
			DisplayHandler.updateDisplay();
		}
		
		//Once the display is closed, terminate the game
		DisplayHandler.closeDisplay();
	}
}
