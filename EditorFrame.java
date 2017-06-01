/*
 * file: EditorFrame.java
 * desc: the JFrame in which pixel editing is done
 * Author: AJ Nagashima
 * email: ajn3687@g.rit.edu
 */

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class EditorFrame extends JFrame{
	Color[][] pixels;
	public EditorFrame(int r, int c){
		pixels = new Color[r][c];
	}
	
	public EditorFrame(Color[][] p){
		pixels = new Color[p.length][p[0].length];
		for(int r = 0; r < pixels.length; r++)
			for(int c = 0; c < pixels[0].length; c++)
				pixels[r][c] = p[r][c];
		
	}

}
