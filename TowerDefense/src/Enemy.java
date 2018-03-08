import java.awt.*;

public class Enemy extends Rectangle {
	public int xC, yC;
	public int health = 100;
	public int healthBarSpace = 3;
	public int healthHeight = 3;
	public int mob = 52;
	public int enemyWalk = 0;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	public int enemyID = Value.enemyAir;
	public boolean inGame= false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;
	
	public Enemy(){

	}
	public void spawnEnemies(int enemyID){
		for(int i = 0; i < Window.room.block.length; i++){
			if(Window.room.block[i][0].groundID == Value.groundRoad){
				setBounds(Window.room.block[i][0].x - mob, Window.room.block[i][0].y, mob, mob);
				xC = 0;
				yC = i;
			}
		}
		this.enemyID = enemyID;
		this.health = mob;
		inGame = true;
	}
	
	public void deleteEnemy(){
		inGame = false;
		direction = right;
		if(health==0){
			Window.coins+=5;
		}
	}
	public void loseHP(){
		Window.health -=1;
	}
	
	public int walkFrame = 0,walkSpeed = 17;
	public void physic(){
		if(walkFrame>= walkSpeed){
			if(direction == right){
				x+=1;
			}	
			else if(direction == upward){
				y-=1;
			}
			else if(direction == downward){
				y+=1;
			}
			else if(direction == left){
				x-=1;
			}
			enemyWalk += 1;	
			
			if(enemyWalk == Window.room.blockSize){
				if(direction == right){
					xC+=1;
					hasRight = true;
				}	
				else if(direction == upward){
					yC-=1;
					hasUpward = true;
				}
				else if(direction == downward){
					yC+=1;
					hasDownward = true;
				}
				else if(direction == left){
					xC-=1;
					hasLeft = true;
				}
				if(!hasUpward){
					try{
						if(Window.room.block[yC+1][xC].groundID == Value.groundRoad){
							direction = downward;
						}
					} catch(Exception e){}
				}
				if(!hasDownward){
					try{
						if(Window.room.block[yC-1][xC].groundID == Value.groundRoad){
							direction = upward;
						}
					} catch(Exception e){}
				}
				if(!hasLeft){
					try{
						if(Window.room.block[yC][xC+1].groundID == Value.groundRoad){
							direction = right;
						}
					} catch(Exception e){}
				}
				if(!hasRight){
					try{
						if(Window.room.block[yC][xC-1].groundID == Value.groundRoad){
							direction = left;
						}
					} catch(Exception e){}
				}
				
				if(Window.room.block[yC][xC].airID == Value.airCave){
					deleteEnemy();
					loseHP();
				}
				
				if(Window.room.block[yC][xC].airID == Value.airCave){
					
				}
				
				hasDownward = false;
				hasUpward = false;
				hasLeft = false;
				hasRight = false;
				enemyWalk = 0;
			}
			
			walkFrame = 0;
		}
		else{
			walkFrame+= 1;
		}
	}
	
	public void loseHealth(int num){
		health -= num;
		checkDeath();
	}
	public void checkDeath(){
		if(health == 0)
			deleteEnemy();
	}
	public boolean isDead(){
		if(inGame){
			return false;
		}
		else
			return true;
	}
	
	public void draw(Graphics g){
		g.drawImage(Window.enemy[enemyID],x+52, y, width, height, null);
		g.setColor(new Color(0,0,255));
		
		g.fillRect(x+52, y - (healthBarSpace + healthHeight), width, healthHeight);
		g.setColor(new Color(180,50,50));
		
		g.fillRect(x+52, y - (healthBarSpace + healthHeight), health, healthHeight);
		g.setColor(new Color(0,0,0));
		g.drawRect(x+52, y - (healthBarSpace + healthHeight), health-1, healthHeight-1);
	}
}
