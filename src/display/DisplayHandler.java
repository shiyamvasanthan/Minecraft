package display;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import main.Game;

//This class handles setting up the display
public class DisplayHandler {
	//Capped to 120FPS 
	public static final int FPS = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay() {
		//Set context attribute to openGL version 3.2
		//Forwards compatibility enabled and using profile core
		//Syntax is weird for this but it has to be this way
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			//Create new fullscreen display 
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Shiyamcraft");
			Display.setFullscreen(true);
			
			//Tell openGL to render the display depending on it's current size
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		//Grab the mouse when you start the game
		Mouse.setGrabbed(true);
		
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		//Sets the game to be capped at 60 FPS
		Display.sync(FPS);
		Display.update();
		
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
		
		//Ensures that keyboard inputs only get registered once
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				
				//If the user presses the escape key, then exit the game
				if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					closeDisplay();
				}
				
				//If the user presses the E key for the inventory set mouse grabbed to false
				if(Keyboard.isKeyDown(Keyboard.KEY_E) && Mouse.isGrabbed()) {
					Mouse.setGrabbed(false);
				} else if(Keyboard.isKeyDown(Keyboard.KEY_E) && !Mouse.isGrabbed()) {
					Mouse.setGrabbed(true);
				}
			}
		}
	}
	
	public static void closeDisplay() {
		//Delete the vertices so they are deleted from RAM
		Game.loader1.cleanUp();
		
		//Delete the shader from ram
		Game.shader1.cleanUp();
		
		//Game.renderer.cleanUp();
		
		//Destroys the display, terminate the game
		Display.destroy();
		System.exit(0);
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	private static long getCurrentTime() {
		return Sys.getTime() * 1000/Sys.getTimerResolution();
	}
}
