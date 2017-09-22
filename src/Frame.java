import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	public static String title = "BLOCK WARS - Beta 1.2"; //Title of window
	public static Dimension size = new Dimension(700, 550);//Dimensions of window

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		//Main method
		Frame frame = new Frame();

	}
	
	public Frame(){
		//Creation of Jframe, misc settings
		setTitle(title); //Title of window, SEE ABOVE
		setSize(size); //Dimensions of window, SEE ABOVE
		setResizable(false); //Cannot be resized
		setLocationRelativeTo(null); //Where the window pops up null==center
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Clicking the Close, stops the program
		
		
		
		init();
	}
	
	
	public void init(){
		//To make it a Java applet
		setVisible(true);
		setLayout(new GridLayout(1, 1, 0, 0)); //Adds the grid
		Window window = new Window(this);
		add(window); //Adds window to JFrame
		PlaySound.play();
		
	}
}
