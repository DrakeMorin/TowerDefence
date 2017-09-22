import java.awt.*;

public class Store {
	public static int shopWidth = 8; // Amount of shop cells
	public static int buttonSize = 52; // Size of cells in shop
	public static int cellSpace = 2; // Distance between cells in shop
	public static int awayFromRoom = 29;
	public static int iconSize = 20; // Size of health/coins icon
	public static int iconSpace = 5; // Size of space between icon and text
	public static int iconTextY = 15; // Additional distance to center text on icon on Y axis
	public static int itemIn = 4;
	public static int realID = -1;
	public static int heldID = -1;
	public static int[] buttonID = {Values.airTower1, Values.airTower2, Values.airTower3, Values.airTower4, Values.airTower5, Values.airTower6, Values.airTower7, Values.airTrash};
	public static int[] buttonPrice = {10, 20, 30, 60, 80, 100, 1000, 0};

	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;

	public boolean holdsItem = false;

	public Store() {
		define();
	}

	public void click(int mouseButton) {
		if (mouseButton == 1) { // RCLICK is 3
			for (int i = 0; i < button.length; i++) {
				if(buttonID[i] != Values.air){
					if (button[i].contains(Window.mse)) {
						if (buttonID[i] == Values.airTrash) { //Deletes held item 
							holdsItem = false;
						} else {
							heldID = buttonID[i];
							realID = i;
							holdsItem = true;
						}
					}
				}
			}
			if(holdsItem){
				if(Window.blockBucks >= buttonPrice[realID]){ //Ensuring the user has enough money
					for(int y=0;y<Window.room.block.length;y++){
						for(int x=0;x<Window.room.block[0].length;x++){
							if(Window.room.block[y][x].contains(Window.mse)){
								if(Window.room.block[y][x].groundID != Values.road && Window.room.block[y][x].airID == Values.air){ //Towers only allowed on grass
									Window.room.block[y][x].airID = heldID; //Places tower
									Window.blockBucks -= buttonPrice[realID]; //Subtracts tower cost from coins
								}
							}
						}
					}
					
				}
			}
		
		} else if (mouseButton == 3) {
			// Deletes held item on right click
			holdsItem = false;
		}
		
	}

	public void define() {
		for (int i = 0; i < button.length; i++) {
			// Creates button locations/dimensions
			button[i] = new Rectangle((Window.myWidth / 2)
					- ((shopWidth * buttonSize) / 2)
					+ ((buttonSize + cellSpace) * i),
					Window.room.block[Window.room.worldHeight - 1][0].y
							+ Window.room.blockSize + awayFromRoom, buttonSize,
					buttonSize);
		}
		buttonHealth = new Rectangle(Window.room.block[0][0].x - 1,
				button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Window.room.block[0][0].x - 1, button[0].y
				+ button[0].height - iconSize, iconSize, iconSize);
	}

	public void draw(Graphics g) {
		for (int i = 0; i < button.length; i++) {
			// Draws buttons
			g.drawImage(Window.tileRes[0], button[i].x, button[i].y, button[i].width, button[i].height, null);

			g.drawImage(Window.tileRes[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
			if(buttonID[i] != Values.air) g.drawImage(Window.air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - (itemIn * 2), button[i].height - (itemIn * 2), null);
			if(buttonPrice[i] > 0) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Courier, New", Font.BOLD, 14));
				g.drawString("$"+buttonPrice[i], button[i].x + itemIn, button[i].y + itemIn + 10); //Draws tower costs
			}
			
			g.drawImage(Window.tileRes[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null); // Draws health image
			g.drawImage(Window.tileRes[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null); // Draws coins image
			g.setFont(new Font("Courier new", Font.BOLD, 14));// Sets font
			g.setColor(Color.BLACK); // Sets text colour
			g.drawString(Window.blockHealth + "", buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + iconTextY); // Draws health amount
			g.drawString(Window.blockBucks + "", buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY); // Draws blockBucks amount

			if (holdsItem) {
				//Draws tower where mouse is when a tower is selected
				g.drawImage(Window.air[heldID], Window.mse.x - ((button[0].width - (itemIn * 2)) / 2) + itemIn, Window.mse.y - ((button[0].width - (itemIn * 2)) / 2) + itemIn, button[0].width - (itemIn * 2), button[0].height - (itemIn * 2), null);
			}

			if (button[i].contains(Window.mse)) {
				// Highlights store buttons if mouse is hovered over one
				g.setColor(new Color(255, 255, 255, 75));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}

		}
	}
}