/*
 * file: Main.java
 * desc: Takes in a file and uses classes to turn it into a compatible LED grid
 * Author: AJ Nagashima
 * email: ajn3687@g.rit.edu
 */

//ImageIO imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//Swing GUI imports
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

//Misc imports
import java.awt.Color;


public class Main extends JFrame{
	BufferedImage base;
	OpeningFrame oFrame;
	EditorFrame eFrame;
	Color[][] pixels;
	
	public Main() {
		oFrame = new OpeningFrame();
		while( true )
		{
			if(oFrame.confirmPressed()){
				oFrame.confirmChecked();
				if(oFrame.getPath().equals(""))
					break;
				if(open(oFrame.getPath()))
					break;

			}
			try{Thread.sleep(10);}
			catch (Exception e){}
		}
		oFrame.dispose();
		
		makeGrid();
		eFrame = new EditorFrame(pixels);
	}
	
	void makeGrid(){
		pixels = new Color[oFrame.getDims()[0]][oFrame.getDims()[1]];
		if(base == null){
			for(int r = 0; r < pixels.length; r++)
				for(int c = 0; c < pixels[0].length; c++)
					pixels[r][c] = new Color(255,255,255);
		}
		
	}

	boolean open(String path) {
		try {
			base = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String args[]) {
		new Main();
	}
}
