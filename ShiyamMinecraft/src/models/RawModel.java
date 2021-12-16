package models;

//This class handles all the raw models before they are drawn to the screen
public class RawModel {
	//Each vao is stored as an integer ID 0, 1, 2, etc...
	int vaoID;
	
	//Number of vertices
	int vertexCount;
	
	//Constructor takes in vaoID, and number of vertices
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	//Getter for vaoID
	public int getVaoID() {
		return vaoID;
	}

	//Getter for vertexCount
	public int getVertexCount() {
		return vertexCount;
	}
}
