import java.awt.*;

public class Block extends Rectangle {

	public Rectangle towerBlock;
	public Rectangle towerBlock2;
	public Rectangle towerBlock3;
	public int towerSize = 104;
	public int towerSize2 = 208;
	public int towerSize3 = 52;
	public int groundID;
	public int airID;
	public int shot = -1;
	public int time = 105, frame = 0, time2 = 155, time3 = 75;
	
	public boolean firing = false;
	
	
	public Block(int x, int y, int width, int height, int groundID, int airID){
		setBounds(x, y, width, height);
		towerBlock = new Rectangle(x- (towerSize / 2)-52,y- (towerSize / 2),width+ (towerSize),height+ (towerSize));
		towerBlock2 = new Rectangle(x- (towerSize2 / 2)-52,y- (towerSize2 / 2),width+ (towerSize2),height+ (towerSize2));
		towerBlock3 = new Rectangle(x- (towerSize3 / 2)-52,y- (towerSize3 / 2),width+ (towerSize3),height+ (towerSize3));
		this.groundID = groundID;
		this.airID = airID;
	}
	
	public void physic(){
		if(shot!= -1 && towerBlock.intersects(Window.enemies[shot])){
			firing = true;
		}
		else if(shot!= -1 && towerBlock2.intersects(Window.enemies[shot])){
			firing = true;
		}
		else if(shot!= -1 && towerBlock3.intersects(Window.enemies[shot])){
			firing = true;
		}
		else
			firing = false;
		if(!firing){
			if(airID == Value.airTower){
				for(int i = 0; i < Window.enemies.length; i++){
					if(Window.enemies[i].inGame){
						if(towerBlock.intersects(Window.enemies[i])){
							firing = true;
							shot = i;
						}
					}
				}
			}
			else if(airID == Value.airTower2){
				for(int i = 0; i < Window.enemies.length; i++){
					if(Window.enemies[i].inGame){
						if(towerBlock2.intersects(Window.enemies[i])){
							firing = true;
						 	shot = i;
						}
					}
				}
			}
			else if(airID == Value.airTower3){
				for(int i = 0; i < Window.enemies.length; i++){
					if(Window.enemies[i].inGame){
						if(towerBlock3.intersects(Window.enemies[i])){
							firing = true;
							shot = i;
						}
					}
				}
			}
		}
		if(firing && airID == Value.airTower){
			if(frame>=time){
				Window.enemies[shot].loseHealth(1);
				frame = 0;
			}
			else
				frame+=1;
			if(Window.enemies[shot].isDead()){
				getPaid(Window.enemies[shot].enemyID);
				firing = false;
				shot -=1;
				Window.killed++;
				
	//			Window.hasWon();
					
			}
		}
		else if(firing && airID == Value.airTower2){
			if(frame>=time2){
				Window.enemies[shot].loseHealth(1);
				frame = 0;
			}
			else
				frame+=1;
			if(Window.enemies[shot].isDead()){
				getPaid(Window.enemies[shot].enemyID);
				firing = false;
				shot -=1;
				Window.killed++;
				
	//			Window.hasWon();
			}
		}
		else if(firing && airID == Value.airTower3){
			if(frame>=time3){
				Window.enemies[shot].loseHealth(1);
				frame = 0;
			}
			else
				frame+=1;
			if(Window.enemies[shot].isDead()){
				getPaid(Window.enemies[shot].enemyID);
				firing = false;
				shot -=1;
				Window.killed++;
				
	//			Window.hasWon();
			}
		}
	}
	
	public void getPaid(int enemyID){
		//for(int i = 0; i < Value.money.length; i++){
			//Value.money[i]=5-350;
			//Window.coins+=5;
		//}
	}
	
	public void draw(Graphics g){
		g.drawImage(Window.tileset_ground[groundID], x, y, width, height, null);
		if(airID != Value.airAir){
			g.drawImage(Window.tileset_air[airID], x, y, width, height, null);
			
		}
	}
	public void fire(Graphics g){
		if(Window.debug){
			if(airID == Value.airTower){
				g.drawRect(towerBlock.x, towerBlock.y, towerBlock.width, towerBlock.height);
			}
			else if(airID == Value.airTower2){
				g.drawRect(towerBlock2.x, towerBlock2.y, towerBlock2.width, towerBlock2.height);
			}
			else if(airID == Value.airTower3){
				g.drawRect(towerBlock3.x, towerBlock3.y, towerBlock3.width, towerBlock3.height);
			}
		}
		if(firing){
				//drawing the firing animation
				g.setColor(new Color(255,0,0));
				g.drawLine(x + (width/2), y + (height/2), Window.enemies[shot].x + (Window.enemies[shot].width/2)+52,  Window.enemies[shot].y + (Window.enemies[shot].height/2) );
		}
	}
}
