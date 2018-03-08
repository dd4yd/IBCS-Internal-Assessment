import java.awt.*;

public class Store {
	
	public static int shopWidth = 4;
	public static int buttonSize = 52;
	public static int cellSpace = 2;
	public static int awayFromRoom = 29;
	public static int iconSize = 20;
	public static int iconSpace = 6;
	public static int iconType = 15;
	public static int itemIN = 4;
	public static int heldID = -1;
	public static int trueID = -1;
	public static int[] buttonID = {Value.airTower, Value.airTower2, Value.airTower3, Value.airTrash }; // 6 store blocks in game
	public static int[] itemPrice = {10, 10, 10, 0};
	
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	
	public boolean holdsItem = false;
	
	public Store(){
		define();
	}
	
	public void click(int mouseButton){
		if(mouseButton == 1){ // checks left
			for (int i = 0; i < button.length; i++){
				if(button[i].contains(Window.mse)){
					if(buttonID[i] != Value.airAir){
						if(buttonID[i] == Value.airTrash){
							holdsItem = false;
					//CHECK		heldID = Value.airAir;
						}else{
							heldID = buttonID[i];
							trueID = i;
							holdsItem = true;
						}
					}
				}
			}
			
			if(holdsItem){
				if(Window.coins>=itemPrice[trueID]){
					for(int i = 0; i < Window.room.block.length; i++){
						for(int y = 0; y < Window.room.block[0].length; y++){
							if(Window.room.block[i][y].contains(Window.mse)){
								if(Window.room.block[i][y].groundID!= Value.groundRoad && Window.room.block[i][y].airID== Value.airAir){
									Window.room.block[i][y].airID = heldID;
									Window.coins -= itemPrice[trueID];
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void define(){
		for(int i = 0; i < button.length; i++){
			button[i] = new Rectangle((Window.myWidth/2) - ((shopWidth*(buttonSize+cellSpace))/2) + ((buttonSize+cellSpace)*i), (Window.room.block[Window.room.worldHeight-1][0].y)+ Window.room.blockSize + awayFromRoom, buttonSize, buttonSize);
		}
		
		buttonHealth = new Rectangle(Window.room.block[0][0].x -1, button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Window.room.block[0][0].x -1, button[0].y + button[0].height - iconSize, iconSize, iconSize);
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < button.length; i++){
			if(button[i].contains(Window.mse)){
				g.setColor(new Color(255,255,255,100));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			
			g.drawImage(Window.cell[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
			if(buttonID[i] != Value.airAir)
				g.drawImage(Window.tileset_air[buttonID[i]], button[i].x + itemIN, button[i].y + itemIN, button[i].width - (itemIN*2), button[i].height - (itemIN*2), null);
			if(itemPrice[i] > 0){
				g.setColor(new Color(255,255,255));
				g.setFont(new Font("Century Gothic", Font.BOLD, 14));
				g.drawString("$"+itemPrice[i], button[i].x + itemIN + 10, button[i].y + itemIN -8);
			
			}
			if(i == button.length-1){
				g.setColor(new Color(255,255,255));
				g.setFont(new Font("Century Gothic", Font.BOLD, 14));
				g.drawString("TRASH", button[i].x + itemIN , button[i].y + itemIN - 5);
			}
		}
		g.drawImage(Window.cell[1], buttonHealth.x,buttonHealth.y,buttonHealth.width,buttonHealth.height, null);
		g.drawImage(Window.cell[2], buttonCoins.x,buttonCoins.y,buttonCoins.width,buttonCoins.height, null);
		g.setFont(new Font("Century Gothic", Font.BOLD, 14));
		g.setColor(new Color(255,255,255));
		g.drawString("" + Window.health, buttonHealth.x + buttonHealth.width + 6, buttonHealth.y + 15);
		g.drawString("" + Window.coins, buttonCoins.x + buttonCoins.width + 6, buttonCoins.y + 15);
		
		if(holdsItem){
			g.drawImage(Window.tileset_air[heldID], Window.mse.x - ((button[0].width - (itemIN*2))/2) + itemIN, Window.mse.y - ((button[0].width - (itemIN*2))/2) + itemIN, button[0].width - (itemIN*2), button[0].height - (itemIN*2), null);
		}
	}
}
