import java.awt.*;

@SuppressWarnings("serial")
public class Block extends Rectangle {
	public Rectangle towerSquare;
	public int towerSquareSize = 120; //Range of tower
	public int groundID;
	public int airID;
	public int shotMob = -1;
	public boolean shooting = false;
	public static int loseTime=100, loseFrame=0;
	public static int damage = 0;
	
	
	public Block(int x, int y, int width, int height, int groundID, int airID){
		setBounds(x, y, width, height);
		towerSquare = new Rectangle(x - (towerSquareSize /2), y - (towerSquareSize /2), width + towerSquareSize, height + towerSquareSize);
		this.groundID = groundID;//what type of block it is
		this.airID = airID;
	}
	
	public void draw(Graphics g){
		//Draws textures on screen for blocks
		g.drawImage(Window.grass[groundID], x, y, width, height, null);
		
		if(airID != Values.air){
			g.drawImage(Window.air[airID], x, y, width, height, null);
		}
	}
	
	public void physic(){
		if(shotMob != -1 && towerSquare.intersects(Window.mobs[shotMob])){
			shooting = true;
		} else {
			shooting = false;
		}
		
		if(!shooting){
			if(airID == Values.airTower1 || airID == Values.airTower2 || airID == Values.airTower3 || airID == Values.airTower4 || airID == Values.airTower5 || airID == Values.airTower6 || airID == Values.airTower7){
				for(int i=0; i<Window.mobs.length; i++){
					if(Window.mobs[i].inGame){
						if(towerSquare.intersects(Window.mobs[i])){
							shooting = true;
							shotMob = i;
						}
					}
				}
			}
		}
		if(shooting){
			if(loseFrame>=loseTime){
				//Setting damage value based on tower
				if(airID == Values.airTower1) damage = 1;
				if(airID == Values.airTower2) damage = 2;
				if(airID == Values.airTower3) damage = 3;
				if(airID == Values.airTower4) damage = 4;
				if(airID == Values.airTower5) damage = 5;
				if(airID == Values.airTower6) damage = 6;
				if(airID == Values.airTower7) damage = 15;
				Window.mobs[shotMob].loseHealth(damage);
				loseFrame = 0;
				shooting = false;
			}else{
				loseFrame++;
			}
			
			if(Window.mobs[shotMob].isDead()){
				getMoney(Window.mobs[shotMob].mobID);
				
				shooting = false;
				shotMob = -1;
				Window.checkHasWon();
			}
		}
	}
	
	public static void getMoney(int mobID){
		Window.blockBucks += Values.killMoney[mobID];
	}
	
	public void fight(Graphics g){
		if(Window.isDebug){ //Draws the range of the towers if in debugging mode
			if(airID == Values.airTower1 || airID == Values.airTower2 || airID == Values.airTower3 || airID == Values.airTower4 || airID == Values.airTower5 || airID == Values.airTower6 || airID == Values.airTower7){
				g.setColor(Color.BLACK);
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}
		}
		if(shooting){
			//Draws shooting particle that differs with the tower
			if(airID ==Values.airTower1){
				g.setColor(new Color(255, 255, 0));
				g.drawLine(x + (width/2), y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
			
			}else if(airID == Values.airTower2){
				//Red Laser 3 wide
				g.setColor(new Color(253,9,9));
				g.drawLine(x + (width/2)-1, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2), y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2)+1, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
			
			}else if(airID == Values.airTower3){
				//Magenta laser of dome (top centre)
				g.setColor(new Color(210,30,99));
				g.drawLine(x + (width/2)-1, y + 2, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2), y + 2, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2)+1, y + 2, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
			
			}else if(airID == Values.airTower4){
				//Laser from centre of cross
				g.setColor(new Color(255,94,0));//ORANGE
				g.drawLine(x + (width/2)-5, y+14, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2)-4, y+14, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(255,0,0));//RED
				g.drawLine(x + (width/2)-3, y+14, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2)-2, y+14, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2)-1, y+14, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(255,94,0));//ORANGE
				g.drawLine(x + (width/2), y+14, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2)+1, y+14, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
			
			}else if(airID == Values.airTower5){
				//Blue beam center
				g.setColor(new Color(64,32,169));//Lighter blue
				g.drawLine(x + (width/2)-2, y+(height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(65,43,135));//Darker blue
				g.drawLine(x + (width/2)-1, y+(height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2), y+(height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.drawLine(x + (width/2)+1, y+(height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(64,32,169));//Lighter blue
				g.drawLine(x + (width/2)+2, y+(height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				
			}else if(airID == Values.airTower6){
				//4 LASERS == 4 * FUN
				g.setColor(Color.RED);
				g.drawLine(x+3-1, y+3, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Top left (left)
				g.drawLine(x+3, y+3, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Top left (mid)
				g.drawLine(x+3+1, y+3, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Top left (right)
				
				g.drawLine(x+49-1, y+3, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Top right (left)
				g.drawLine(x+49, y+3, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Top right (mid)
				g.drawLine(x+49+1, y+3, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Top right (right)
				
				g.drawLine(x+3-1, y+49, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Bottom left (left)
				g.drawLine(x+3, y+49, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Bottom left (mid)
				g.drawLine(x+3+1, y+49, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Bottom left (right)
				
				g.drawLine(x+49-1, y+49, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Bottom right (left)
				g.drawLine(x+49, y+49, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Bottom right (mid)
				g.drawLine(x+49-1, y+49, Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));//Bottom right (right)
				
			
			}else if(airID == Values.airTower7){
				//RAINBOW PARTICLE EFFECTS
				g.setColor(new Color(251,3,3));//RED
				g.drawLine(x + (width/2)-3, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(251,102,3));//ORANGE
				g.drawLine(x + (width/2)-2, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(251,231,3));//YELLOW
				g.drawLine(x + (width/2)-1, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(15,251,3));//GREEN
				g.drawLine(x + (width/2), y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(3,137,251));//LIGHT BLUE
				g.drawLine(x + (width/2)+1, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(3,50,251));//DARK BLUE
				g.drawLine(x + (width/2)+2, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
				g.setColor(new Color(190,3,251));//VIOLET
				g.drawLine(x + (width/2)+3, y + (height/2), Window.mobs[shotMob].x + (width/2), Window.mobs[shotMob].y + (Window.mobs[shotMob].height/2));
			}
		}
	}
}
