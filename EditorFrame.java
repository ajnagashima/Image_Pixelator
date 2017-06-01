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
import java.awt.Dimension;
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
import javax.swing.JLabel;

public class EditorFrame extends JFrame{
	Color[][] pixels;
	Color current = new Color(0,0,0);
	
	public EditorFrame(Color[][] p){
		pixels = new Color[p.length][p[0].length];
		for(int r = 0; r < pixels.length; r++)
			for(int c = 0; c < pixels[0].length; c++)
				pixels[r][c] = p[r][c];
		
		setSize(20*pixels.length + 200, 20*pixels[0].length+ 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new GridLayout());
		JPanel grid = new JPanel(new GridLayout(pixels.length, pixels[0].length));
		JPanel colorSldr = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel();
		
		for(int r = 0; r < pixels.length; r++)
			for(int c = 0; c < pixels[0].length; c++)
			{
				JButton temp = new JButton();
				temp.setBackground(pixels[r][c]);
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						temp.setBackground(current);
					}
				});
				temp.setPreferredSize(new Dimension(20,20));
				grid.add(temp, r, c);
			}
		
		add(grid, BorderLayout.CENTER);
		
		JTextField savefield = new JTextField(20);
		bottom.add(new JLabel("Save As: "));
		bottom.add(savefield);
		
		JButton savebtn = new JButton("Save");
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				save(savefield.getText());
			}
		});
		
		pack();
		getRootPane().setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.BLACK));
		getContentPane().setBackground(Color.WHITE);
		setVisible(true);
		
	}
	
	private void save(String path){
		//TODO
	}
}
