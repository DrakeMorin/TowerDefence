import java.awt.*;

@SuppressWarnings("serial")
public class Mob extends Rectangle {
	public int xCoord, yCoord;
	public int mobHealth = 60;
	public int healthSpace = 3, healthHeight = 6;
	public int mobWalk = 0; //How far mob has walked
	public final int UPWARD = 0, DOWNWARD = 1, RIGHT = 2, LEFT = 3; //Directions mob can go
	public int direction = RIGHT; //Default travel direction is right
	public int mobSize = 52;
	public int mobID = Values.mobAir;
	public boolean inGame = false;
	
	public boolean hasUp = false; //For path finding
	public boolean hasDown = false; //For path finding
	public boolean hasLeft = false; //For path finding
	public boolean hasRight = false; //For path finding
	
	public Mob(){
		
	}
	
	public void spawnMob(int mobID){
		for(int y=0; y<Window.room.block.length;y++){
			if(Window.room.block[y][0].groundID == Values.road){
				setBounds(Window.room.block[y][0].x, Window.room.block[y][0].y, mobSize, mobSize);
				xCoord = 0;
				yCoord = y;
			}
			inGame = true;
			this.mobID = mobID;
		}
	}
	
	public void deleteMob(){   //Mob hits castle and deletes
		inGame = false;
		direction = RIGHT;
		mobWalk = 0;
		mobHealth = 50;
		Window.killed++;
	}

	public void loseHealth(){
		Window.blockHealth = Window.blockHealth - 2 ;
	}
	
	public int walkFrame = 0;
	public static int walkSpeed = 40; //REMOVE SATIC IF NECESSARY
	public void physics(){
		if(walkFrame >= walkSpeed){
			if(direction == RIGHT){
				x++;
				walkFrame = 0;
			} else if(direction == UPWARD){
				y--;
				walkFrame = 0;
			}else if(direction == DOWNWARD){
				y++;
				walkFrame = 0;
			}else if(direction == LEFT){
				x--;
				walkFrame = 0;
			}
			
			mobWalk++;
			
			if(mobWalk == Window.room.blockSize){
				if(direction == RIGHT){
					xCoord++;
					hasRight = true;
				} else if(direction == UPWARD){
					yCoord--;
					hasUp = true;
				}else if(direction == DOWNWARD){
					yCoord++;
					hasDown = true;
				}else if(direction == LEFT){
					xCoord--;
					hasLeft = true;
				}
				
				if(!hasUp){
					try{
						if (Window.room.block[yCoord+1][xCoord].groundID == Values.road) {
							direction = DOWNWARD;
						}
					}catch (Exception e){}
				}
				
				if(!hasDown){
					try{
						if (Window.room.block[yCoord-1][xCoord].groundID == Values.road) {
							direction = UPWARD;
						}
					}catch (Exception e){}
				}
				
				if(!hasLeft){
					try{
						if (Window.room.block[yCoord][xCoord+1].groundID == Values.road) {
							direction = RIGHT;
						}
					}catch (Exception e){}
				}
				
				if(!hasRight){
					try{
						if (Window.room.block[yCoord][xCoord-1].groundID == Values.road) {
							direction = LEFT;
						}
					}catch (Exception e){}
				}
				
				if(Window.room.block[yCoord][xCoord].airID == Values.airCave){ //Monsters reach the end
					deleteMob();
					loseHealth();
				}
				
				hasUp = false;
				hasDown = false;
				hasRight = false;
				hasLeft = false;
				
				mobWalk = 0;
			}
		} else {
			walkFrame++;
		}
	}
	
	public void loseHealth(int amount){
		mobHealth -= amount;
		checkDeath();
	}
	
	public void checkDeath(){
		if(mobHealth<=0){
			mobHealth = 0;
			deleteMob();
		}
	}
	
	public boolean isDead(){
		if(inGame){
			return false;
		}else{
			return true;
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(Window.tileMob[mobID], x, y, width, height, null); //Draws texture based on mobID value
		
		//Red health bar background
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - (healthSpace + healthHeight), width/4 - 2, healthHeight);
		
		//Green bar of current health
		g.setColor(new Color(50, 180, 50));
		g.fillRect(x, y - (healthSpace + healthHeight), mobHealth/4, healthHeight);
		
		//Health bar outline
		g.setColor(Color.BLACK);
		g.drawRect(x, y - (healthSpace + healthHeight), mobHealth/4 - 1, healthHeight - 1);
	}
}
