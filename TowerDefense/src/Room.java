import java.awt.*;

public class Room {

	public int worldWidth = 12;
	public int worldHeight = 8;
	public int blockSize = 52;
	
	public Block[][] block;
	
	public Room(){
		define();
	}
	
	public void define(){
		block  = new Block[worldHeight][worldWidth];
		
		for(int i = 0; i < block.length; i++){
			for(int x = 0; x < block[0].length; x++){
				block[i][x] = new Block((Window.myWidth/2)-((worldWidth*blockSize)/2)+(x * blockSize), i * blockSize, blockSize, blockSize, Value.groundGrass, Value.airAir);
			}
		}
	}
	
	public void physic(){
		for(int i = 0; i< block.length; i++){
			for(int y = 0; y < block[0].length; y++){
				block[i][y].physic();
			}
		}
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < block.length; i++){
			for(int x = 0; x < block[0].length; x++){
				block[i][x].draw(g);
			}
		}
		for(int i = 0; i < block.length; i++){
			for(int x = 0; x < block[0].length; x++){
				block[i][x].fire(g);
			}
		}
	}
}
