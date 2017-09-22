import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

@SuppressWarnings("serial")
public class Window extends JPanel implements Runnable {
	public Thread thread = new Thread(this); //Creates new thread of application
	
	public static Image[] grass = new Image[100]; //Image should be 26*2600
	public static Image[] air = new Image[100];
	public static Image[] tileRes = new Image[100];
	public static Image[] tileMob = new Image[100];
	public static Image[] coffeeCup = new Image[2];
	public static Image[] gameOver = new Image[2];
	public static Image[] win = new Image[2];
	public static Image[] startImg = new Image[2];
	
	public static int myWidth, myHeight;
	public static int blockBucks = 20;
	public static int blockHealth = 100;
	public static int killed = 0, killsToWin = 0, level = 1, maxLevel = 3;
	public static int winTime = 4000, winFrame = 0;
	
	private int fps = 0; // FPS starts at number 0
	
	public static boolean isFirst = true;
	public static boolean isDebug = false;
	public static boolean isCoffeeCup = false;
	public static boolean isWin = false;
	public static boolean isGameOver = false;
	public static boolean isIntro = true;
	public static boolean winScreen = false;
	
	public static Point mse = new Point(0,0); //Used to track mouse movements
	
	public static Room room = new Room();
	public static Save save;
	public static Store store;
	public static Mob[] mobs = new Mob[100];
	
	public Window(Frame frame){
		frame.addMouseListener(new KeyHandler());
		frame.addMouseMotionListener(new KeyHandler());
		frame.addKeyListener(new KeyHandler());
		
		thread.start();
	}
	
	public static void checkHasWon(){
		if(killed >= killsToWin){
			isWin = true;
			killed = 0;
		}
	}
	
	private void define(){
		//Defines all images and rooms
		room = new Room();
		save = new Save();
		store = new Store();
		
		blockBucks = 20;
		
		if(level == 1){
			killsToWin = 50;
		}else if(level ==2){
			killsToWin = 100;
		}else{
			killsToWin = 150;
			winScreen = true;
		}
		
		for(int i=0;i<grass.length; i++){
			//Cropping the images in grass
			grass[i] = new ImageIcon("res/grass.png").getImage();
			grass[i] = createImage(new FilteredImageSource(grass[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
		for(int i=0;i<air.length; i++){
			//Cropping the images in air
			air[i] = new ImageIcon("res/air.png").getImage();
			air[i] = createImage(new FilteredImageSource(air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		tileRes[0] = new ImageIcon("res/storeIcon.png").getImage();
		tileRes[1] = new ImageIcon("res/Heart.png").getImage();
		tileRes[2] = new ImageIcon("res/Coin.png").getImage();
		
		tileMob[0] = new ImageIcon("res/mob1.png").getImage(); //Importing mob textures
		tileMob[1] = new ImageIcon("res/mob2.png").getImage();
		tileMob[2] = new ImageIcon("res/mob3.png").getImage();
		tileMob[3] = new ImageIcon("res/mob4.png").getImage();
		
		coffeeCup[0] = new ImageIcon("res/CoffeeCup.png").getImage();
		gameOver[0] = new ImageIcon("res/gameover.gif").getImage();
		win[0] = new ImageIcon("res/winner.gif").getImage();
		startImg[1] = new ImageIcon("res/intro.gif").getImage();
		
		save.loadWorld(new File("Save/world" + level +".abc")); //Loads the map design
		
		for(int i=0; i<mobs.length; i++){
			mobs[i] = new Mob(); //Instantiates mob
		}
	}
	
	public void paintComponent(Graphics g){
		//Handles the graphics of the program
		if(isFirst){
			
			//Runs first time repaint(); runs and then no more
			myWidth = getWidth(); //Stores the width for later
			myHeight = getHeight(); //Stores the height for later
			define();
			
			isFirst = false;
		}
		g.setColor(new Color(70, 70, 70)); //Creates a custom RGB colour
		g.fillRect(0, 0, getWidth(), getHeight()); //Covers the window completely
		g.setColor(Color.BLACK);
		g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y + room.blockSize);//Drawing the left line
		g.drawLine(room.block[0][room.worldWidth-1].x + room.blockSize, 0, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize);//Drawing the right line
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight-1][0].y + room.blockSize, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize);//Drawing bottom line
		
		room.draw(g); //Draws the room using room's method
		for(int i=0; i<mobs.length; i++){
			//Drawing mobs
			if(mobs[i].inGame){
				mobs[i].draw(g);
				
			}
		}
		store.draw(g); //Draws the store using store's method
		
		g.setColor(Color.WHITE);
		g.drawString(killed + "/" + killsToWin, 5, 10);
		
		//FPS counter MUST be the last part of this method, Java paints in layers!
		if(isDebug){
			g.setColor(Color.WHITE); //Sets the color it will paint with
			g.drawString(fps + "", 5, 25); //FPS counter
		}
		
		if(blockHealth<1){
			isGameOver = true;
			//Game over screen
			//g.setColor(new Color(255, 20, 20)); //Red colour
			//g.fillRect(0, 0, myWidth, myHeight); //Fills screen
			
	        //Icon icon = new ImageIcon("http://imgur.com/CSxQxpP");
	        //JLabel label = new JLabel(icon);

	        //JFrame f = new JFrame("Animation");
	        //f.getContentPane().add(label);
	       //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //f.pack();
	        //f.setLocationRelativeTo(null);
	        //f.setVisible(true);
			
			//g.setColor(Color.WHITE);
			//g.setFont(new Font("Courier New", Font.BOLD, 14));
			//g.drawString("Game Over!", ((myWidth/2)-47), myHeight/2);
		}
		
		if(isWin){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, myWidth, myHeight);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			
			if(winScreen){
				g.drawString("YOU'VE WON THE GAME", ((myWidth/2)-70), myHeight/2);
				g.drawImage(win[0], 0, 0, myWidth, myHeight, null);
			} else {
				g.drawString("Next Level Is Loading", ((myWidth/2)-77), myHeight/2);
			}
		}
		
		if(isCoffeeCup){
			g.drawImage(coffeeCup[0], 0, 0, myWidth, myHeight, null);
			
		}
		
		if(isGameOver){
			g.drawImage(gameOver[0], 0, 0, myWidth, myHeight, null);
			
		}
		if(isIntro){
			g.drawImage(startImg[1], 0, 0, myWidth, myHeight, null);
		}
		
	}
	
	//public static void startImageGif(Graphics g){
		//g.drawImage(startImg[1], 0, 0, myWidth, myHeight, null);
		//try {
		  // Thread.sleep(3000);
		//} catch(InterruptedException ex) {
		    //Thread.currentThread().interrupt();
		//}
//	}
	
	public static int spawnTime = 2000; //How often mobs spawn
	public int spawnFrame = 0; //Keeps track of how long it has been since last spawn
	public int counter = 0;
	
	//Spawn mob difficulty progression
	public void mobSpawner(){
		if(level == 1){
			//Spawns 20 Stripeys, 20 Poxes, 10 BlueEyes
			if(spawnFrame >= spawnTime){
				for(int i=0; i<mobs.length; i++){
					if(!mobs[i].inGame){
						if(counter<20){
							mobs[i].spawnMob(Values.mobStripey); //Spawns the Striped type of mob
						}else if(counter<40){
							mobs[i].spawnMob(Values.mobPox);//Spawns the Pox type of mob
							mobs[i].mobHealth = 100;
						}else{
							mobs[i].spawnMob(Values.mobBlueEyes); //Spawns the BlueEyes type of mob
							mobs[i].mobHealth = 150;
						}
						counter++;
						break;
					}
				}
				spawnFrame = 0;
			}else{
				spawnFrame++;
			}
		}else if(level ==2){
			//Spawns 19 Stripeys, 50 Poxes, 35 BlueEyes, 6 Sunshines
			spawnTime = 1700;
			if(spawnFrame >= spawnTime){
				for(int i=0; i<mobs.length; i++){
					if(!mobs[i].inGame){
						if(counter<70){
							mobs[i].spawnMob(Values.mobStripey); //Spawns the Striped type of mob
						}else if(counter<110){
							mobs[i].spawnMob(Values.mobPox);//Spawns the Pox type of mob
							mobs[i].mobHealth = 100;
						}else if(counter<145){
							mobs[i].spawnMob(Values.mobBlueEyes); //Spawns the BlueEyes type of mob
							mobs[i].mobHealth = 150;
						}else{
							mobs[i].spawnMob(Values.mobSunshine); //Spawns the BlueEyes type of mob
							mobs[i].mobHealth = 200;
						}
						counter++;
						break;
					}
				}
				spawnFrame = 0;
			}else{
				spawnFrame++;
			}
		}else if(level==3){
			//Spawns 24 stripeys, 40 pox, 55 BlueEyes, 31 Sunshines
			spawnTime = 1400;
			if(spawnFrame >= spawnTime){
				for(int i=0; i<mobs.length; i++){
					if(!mobs[i].inGame){
						if(counter<175){
							mobs[i].spawnMob(Values.mobStripey); //Spawns the Striped type of mob
							
						}else if(counter<215){
							mobs[i].spawnMob(Values.mobPox);//Spawns the Pox type of mob
							mobs[i].mobHealth = 100;
						}else if(counter<270){
							mobs[i].spawnMob(Values.mobBlueEyes); //Spawns the BlueEyes type of mob
							mobs[i].mobHealth = 150;
						}else{
							mobs[i].spawnMob(Values.mobSunshine); //Spawns the BlueEyes type of mob
							mobs[i].mobHealth = 200;
						}
						counter++;
						break;
					}
				}
				spawnFrame = 0;
			}else{
				spawnFrame++;
			}
		}
	}
	
	public void run(){
		long lastFrame = System.currentTimeMillis(); //Variable for FPS counter
		int frames = 0; //Variable for FPS counter
		
		while(true){
			//This loop handles entire game (GAME LOOP)
			if(!isFirst && blockHealth>0 && !isWin){
				room.physic();
				mobSpawner();
				for(int i=0; i<mobs.length; i++){
					if(mobs[i].inGame){
						mobs[i].physics();
					}
				}
			} else {
				if(isWin){
					if(winFrame >= winTime){
						if(level == maxLevel){
							System.exit(0); //Closes application
						}else{
							level++;
							define();
							isWin = false;
						}
						winFrame = 0;
					}else{
						winFrame++;
					}
				}
			}
			repaint(); //Re does the background image
			
			frames++; //Increments frames
			if (System.currentTimeMillis() - 1000 >= lastFrame) {
				// If the current time is 1000ms (1 second) or greater, set fps == frames
				fps = frames;
				frames = 0; // Reset frames
				lastFrame = System.currentTimeMillis(); // Reset lastFrame
			}
			
			try {
				Thread.sleep(1);
			} catch(Exception e) { }
			
		}
	}
}