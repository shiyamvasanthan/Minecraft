package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	Vector3f position;
	float rotX, rotY, rotZ;
	float speed = 0.4f;
	float sensitivity = 0.1f;
	float moveAt = 0;
	
	public Camera(Vector3f position, float rotX, float rotY, float rotZ) {
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			moveAt = -speed;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			moveAt = speed;
		} else {
			moveAt = 0;
		}
		
		//Camera moves with the mouse
		rotX += -Mouse.getDY() * sensitivity;
		rotY += Mouse.getDX() * sensitivity;
		
		//Get x, y, z position of the camera after rotation by finding hypotenuse
		float dx = (float) -(moveAt * Math.sin(Math.toRadians(rotY)));
		float dy = (float) (moveAt * Math.sin(Math.toRadians(rotX)));
		float dz = (float) (moveAt * Math.cos(Math.toRadians(rotY)));
		
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	public Vector3f getPosition() {
		return position;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}
}
