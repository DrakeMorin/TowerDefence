import java.awt.*;

public class Room {
	//Dimensions for grid
	public int worldWidth = 12;
	public int worldHeight = 8;
	public int blockSize = 52; //26*26 pixels

	public Block[][] block;

	public Room() {
		define();
	}

	public void define() {
		block = new Block[worldHeight][worldWidth];

		for (int y = 0; y < block.length; y++) {
			for(int x=0;x<block[0].length;x++){
				//Draws centered grid
				block[y][x] = new Block((Window.myWidth/2) - ((worldWidth*blockSize) / 2) + (x * blockSize), y * blockSize, blockSize, blockSize, Values.grass, Values.air);
			}
		}
	}

	public void physic() {
		for(int y=0; y<block.length; y++){
			for(int x=0; x<block[0].length; x++){
				block[y][x].physic();
			}
		}
	}

	public void draw(Graphics g) {
		for (int y = 0; y < block.length; y++) {
			for(int x=0;x<block[0].length;x++){
				block[y][x].draw(g); //Draws block grid of textures
			}
		}
		for (int y=0; y < block.length; y++) {
			for(int x=0;x<block[0].length;x++){
				block[y][x].fight(g); //Draws range of towers when in debug mode
			}
		}
	}
}