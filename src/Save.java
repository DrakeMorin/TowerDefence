import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Save {
	public void loadWorld(File loadPath) {
		try {
			Scanner loadScanner = new Scanner(loadPath);
			
			while (loadScanner.hasNext()) {
				for (int y = 0; y < Window.room.block.length; y++) {
					for (int x = 0; x < Window.room.block[0].length; x++) {
						Window.room.block[y][x].groundID = loadScanner.nextInt();
					}

				}

				for (int y = 0; y < Window.room.block.length; y++) {
					for (int x = 0; x < Window.room.block[0].length; x++) {
						Window.room.block[y][x].airID = loadScanner.nextInt();
					}
				}
			}

			loadScanner.close();
		} catch (Exception e) {
			System.err.println("An error has occurred while saving!");
			e.printStackTrace();
		}

	}
}
