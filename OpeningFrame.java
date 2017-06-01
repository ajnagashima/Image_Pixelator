/*
 * file: OpeningFrame.java
 * desc: provides an initial JFrame responsible for retrieveing an image
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
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class OpeningFrame extends JFrame {
	JTextField textfield = new JTextField("");
	JComboBox<String> cols, rows, colorselect;
	private boolean cbtnPressed = false;
	
	
	public OpeningFrame() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		JPanel top = new JPanel();
		JPanel mid = new JPanel();
		JPanel bot = new JPanel();
		
		mid.add(new JLabel("File: "));
		
		textfield.setColumns(20);
		//setDropTarget for textfield
		textfield.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent d){
				try{
					d.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> dropped = (List<File>)d.getTransferable().
							getTransferData(DataFlavor.javaFileListFlavor);
					
					File file = dropped.get(0);
					
					textfield.setText(file.getAbsolutePath());
				} catch(Exception e) {e.printStackTrace();}
			}
		});
		mid.add(textfield);
		
		add(mid, BorderLayout.CENTER);
		
		JButton confirmbtn = new JButton("confirm");
		confirmbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				cbtnPressed = true;
			}
		});
		bot.add(confirmbtn);
		
		add(bot, BorderLayout.SOUTH);
		
		String[] rgb = {"RGB", "Grey Scale"};
		colorselect = new JComboBox<>(rgb);
		top.add(colorselect);
		
		String[] sizes = {"4", "8", "16", "32", "64", "128"};
		rows = new JComboBox<>(sizes);
		cols = new JComboBox<>(sizes);
		top.add(new JLabel("# of Pixels:")); top.add(rows); 
		top.add(new JLabel(" x ")); top.add(cols);
		
		add(top, BorderLayout.NORTH);
		
		pack();
		getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		getContentPane().setBackground(Color.WHITE);
		setVisible(true);
	}

	boolean confirmPressed(){
		return cbtnPressed;
	}
	
	void confirmChecked(){
		cbtnPressed = false;
	}
	
	String getPath() {
		return textfield.getText();
	}
	
	int[] getDims(){
		int[] dims = {Integer.parseInt((String)(rows.getSelectedItem())), 
				Integer.parseInt((String)(cols.getSelectedItem()))};
		return dims;
	}
	
	boolean isRGB(){
		return colorselect.getSelectedIndex() == 0 ? true : false;
	}
}
