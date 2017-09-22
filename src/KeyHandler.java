import java.awt.event.*;
import java.awt.*;


public class KeyHandler implements MouseMotionListener, MouseListener, KeyListener{
	private static final int SPACE = 32, ENTER = 10, CTRL = 17, ESC = 27;//, LEFT = 37, UP = 38, DOWN = 40, RIGHT = 39;
	int keyCode; //Entered code
	StringBuilder cheatCode = new StringBuilder();
	
	//Tracking mouse movements
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) {	}

	public void mousePressed(MouseEvent e) { 
		Window.store.click(e.getButton());
	}

	public void mouseReleased(MouseEvent e) { }

	public void mouseDragged(MouseEvent e) { }

	public void mouseMoved(MouseEvent e) {
		Window.mse = new Point(e.getX() - 3, e.getY() - 25);
		//Keep following commented code just in case (Logic he used)
		//Window.mse = new Point(e.getX() - ((Frame.size.width) - Window.myWidth/2), e.getY() - ((Frame.size.height) - Window.myHeight/2));
	}

	public void keyPressed(KeyEvent e) {
		keyCode = e.getKeyCode();
		
		//System.err.println(e.getKeyCode());
		
		if (keyCode == SPACE && Mob.walkSpeed == 40){
			Mob.walkSpeed = 10; //Increase mob speed on SPACE button press
			Block.loseTime /= 2;
			Window.spawnTime /= 2;
		}else if(keyCode == SPACE && Mob.walkSpeed != 40){
			Mob.walkSpeed = 40; //Returns mob speed to normal
			Block.loseTime *= 2;
			Window.spawnTime *= 2;
			//Window.blockHealth = 1;
		}else if(keyCode == CTRL && !Window.isDebug){
			Window.isDebug = true; //Enable debug mode
		}else if(keyCode == CTRL && Window.isDebug){
			Window.isDebug = false; //Disable debug mode
		}else if (keyCode == ESC){
			Window.isCoffeeCup = false; //ESC
			Window.isIntro = false;
		}else if(keyCode == ENTER){
			if(cheatCode.toString().equals("38384040373937396665")) Window.blockBucks += 10000; //Up, up, down, down, left, right, left right, B, A
			if(cheatCode.toString().equals("37373939384038406566")) Window.blockHealth += 100; //Left, left, right, right, up, down, up, down, A, B
			if(cheatCode.toString().equals("677970706969678580")) Window.isCoffeeCup = true; //COFFEECUP
			if(cheatCode.toString().equals("6772696584")) Window.blockBucks = 10; //CHEAT
			if(cheatCode.toString().equals("72656775")) Window.blockHealth = 1; //HACKER
			
			//System.err.println(cheatCode.toString());
			
			cheatCode.delete(0,cheatCode.length()); //Clears cheatcode
		}else if(keyCode == 107){//ADD
			Window.winScreen = true;
		}else if(keyCode == 109){//MINUE
			Window.isWin = true;
		}else{
			cheatCode.append(keyCode+"");
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

}
