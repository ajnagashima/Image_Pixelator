
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class EditorFrame extends JFrame {
	Color[][] pixels;
	Color current = new Color(255, 255, 255);
	int red = 255, blue = 255, green = 255;
	boolean paint = false;

	public EditorFrame(Color[][] p, boolean rgb) {
		pixels = new Color[p.length][p[0].length];
		for (int r = 0; r < pixels.length; r++)
			for (int c = 0; c < pixels[0].length; c++)
				pixels[r][c] = p[r][c];

		setSize(20 * pixels.length + 200, 20 * pixels[0].length + 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		JPanel grid = new JPanel(new GridLayout(pixels.length, pixels[0].length));
		JPanel colorSldr = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel();

		for (int r = 0; r < pixels.length; r++)
			for (int c = 0; c < pixels[0].length; c++) {
				JButton temp = new JButton();
				temp.setBackground(pixels[r][c]);
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						temp.setBackground(current);
					}
				});

				temp.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						temp.setBackground(current);
						paint = true;
					}

					public void mouseEntered(MouseEvent e) {
						if (paint == true)
							temp.setBackground(current);
					}

					public void mouseReleased(MouseEvent e) {
						paint = false;
					}
				});

				temp.setPreferredSize(new Dimension(20, 20));
				grid.add(temp, r, c);
			}

		add(grid, BorderLayout.CENTER);

		JTextField savefield = new JTextField(20);
		bottom.add(new JLabel("Save As: "));
		bottom.add(savefield);

		JButton savebtn = new JButton("Save");
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save(savefield.getText());
			}
		});
		bottom.add(savebtn);
		add(bottom, BorderLayout.SOUTH);

		if (rgb)
			addColorCmps(colorSldr);
		else
			addGreyCmps(colorSldr);

		add(colorSldr, BorderLayout.EAST);

		getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));
		pack();
		// getRootPane().setBorder(BorderFactory.createMatteBorder(2,2,2,2,
		// Color.BLACK));
		getContentPane().setBackground(Color.WHITE);
		setVisible(true);

	}

	private void addGreyCmps(JPanel colorSldr) {
		JSlider greyshader = new JSlider(SwingConstants.VERTICAL, 0, 255, 255);
		JTextField greyfield = new JTextField(3);
		greyfield.setText("255");

		greyshader.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				red = greyshader.getValue();
				current = new Color(red, red, red);
				greyfield.setText(red + "");
			}
		});

		greyfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int temp = Integer.parseInt(greyfield.getText());
					if (temp <= 255 && temp >= 0) {
						red = temp;
						greyshader.setValue(temp);
					}

				} catch (Exception x) {
				}
			}
		});

		colorSldr.add(greyshader, BorderLayout.CENTER);
		colorSldr.add(greyfield, BorderLayout.NORTH);
	}

	private void addColorCmps(JPanel colorSldr) {
		JTextField hexfield = new JTextField(6);
		hexfield.setText("ffffff");

		JSlider rshader = new JSlider(SwingConstants.VERTICAL, 0, 255, 255);
		JTextField rfield = new JTextField(3);
		rfield.setText("255");

		rshader.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				red = rshader.getValue();
				current = new Color(red, green, blue);
				rfield.setText(red + "");
				hexfield.setText( Integer.toHexString(red));
				hexfield.setText((hexfield.getText().length() == 1 ? "0": "")+
						hexfield.getText()+
						(Integer.toHexString(green).length() == 1 ? "0": "")+
						Integer.toHexString(green));
				hexfield.setText(hexfield.getText()+
						(Integer.toHexString(blue).length() == 1 ? "0":"")+
						Integer.toHexString(blue));
			}
		});

		rfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int temp = Integer.parseInt(rfield.getText());
					if (temp <= 255 && temp >= 0) {
						red = temp;
						rshader.setValue(temp);
					}

				} catch (Exception x) {
				}
			}
		});

		JSlider gshader = new JSlider(SwingConstants.VERTICAL, 0, 255, 255);
		JTextField gfield = new JTextField(3);
		gfield.setText("255");

		gshader.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				green = gshader.getValue();
				current = new Color(red, green, blue);
				gfield.setText(green+"");
				hexfield.setText( Integer.toHexString(red));
				hexfield.setText((hexfield.getText().length() == 1 ? "0": "")+
						hexfield.getText()+
						(Integer.toHexString(green).length() == 1 ? "0": "")+
						Integer.toHexString(green));
				hexfield.setText(hexfield.getText()+
						(Integer.toHexString(blue).length() == 1 ? "0":"")+
						Integer.toHexString(blue));
			}
		});

		gfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int temp = Integer.parseInt(gfield.getText());
					if (temp <= 255 && temp >= 0) {
						green = temp;
						gshader.setValue(temp);
					}

				} catch (Exception x) {
				}
			}
		});

		JSlider bshader = new JSlider(SwingConstants.VERTICAL, 0, 255, 255);
		JTextField bfield = new JTextField(3);
		bfield.setText("255");

		bshader.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				blue = bshader.getValue();
				current = new Color(red, green, blue);
				bfield.setText(blue+"");
				hexfield.setText( Integer.toHexString(red));
				hexfield.setText((hexfield.getText().length() == 1 ? "0": "")+
						hexfield.getText()+
						(Integer.toHexString(green).length() == 1 ? "0": "")+
						Integer.toHexString(green));
				hexfield.setText(hexfield.getText()+
						(Integer.toHexString(blue).length() == 1? "0":"")+
						Integer.toHexString(blue));
				
			}
		});

		bfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int temp = Integer.parseInt(bfield.getText());
					if (temp <= 255 && temp >= 0) {
						blue = temp;
						bshader.setValue(temp);
					}

				} catch (Exception x) {
				}
			}
		});

		hexfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (hexfield.getText().length() == 6) {
						int rtemp = Integer.parseInt(hexfield.getText().substring(0, 2), 16);
						int gtemp = Integer.parseInt(hexfield.getText().substring(2, 4), 16);
						int btemp = Integer.parseInt(hexfield.getText().substring(4), 16);
						if (rtemp >= 0 && gtemp >= 0 && btemp >= 0 && 
								rtemp <= 255 && gtemp <= 255 && btemp <= 255) {
							red = rtemp;
							green = gtemp;
							blue = btemp;
							rshader.setValue(red);
							gshader.setValue(green);
							bshader.setValue(blue);
						}
					}

				} catch (Exception x) {
				}
			}
		});

		JPanel top = new JPanel();

		top.add(new JLabel("R: "));
		top.add(rfield);
		top.add(new JLabel("G: "));
		top.add(gfield);
		top.add(new JLabel("B: "));
		top.add(bfield);

		JPanel bottom = new JPanel();

		bottom.add(new JLabel("Hex: 0x"));
		bottom.add(hexfield);

		colorSldr.add(rshader, BorderLayout.WEST);
		colorSldr.add(gshader, BorderLayout.CENTER);
		colorSldr.add(bshader, BorderLayout.EAST);
		colorSldr.add(top, BorderLayout.NORTH);
		colorSldr.add(bottom, BorderLayout.SOUTH);
	}

	private void save(String path) {
		// TODO
	}
}
