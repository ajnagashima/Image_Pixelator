
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

public class OpeningFrame extends JFrame {
	JTextField textfield = new JTextField();
	private boolean cbtnPressed = false;
	
	
	public OpeningFrame() {
		setSize(1000, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		
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
		top.add(textfield);
		
		//NEED BUTTON LISTENER
		JButton confirmbtn = new JButton("confirm");
		confirmbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				cbtnPressed = true;
			}
		});
		top.add(confirmbtn);
		
		add(top, BorderLayout.CENTER);
		
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
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
}
