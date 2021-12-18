package cube;

public class Block {
	public int x, y, z;
	
	//Block ID
	public static int GRASS = 0;
	
	public int type;
	
	public Block(int x, int y, int z, int type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}
}
