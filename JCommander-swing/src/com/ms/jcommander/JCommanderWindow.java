package com.ms.jcommander;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

import java.awt.ComponentOrientation;

import javax.swing.JPanel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.GridLayout;

import javax.swing.SpringLayout;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import com.ms.jcommander.controller.JCommanderController;
import com.ms.jcommander.model.ModelBinder.WindowSide;
import com.ms.jcommander.utils.Strings;
import com.ms.jcommander.utils.Utils;

public class JCommanderWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtTest;
	private JTable leftFilesTable;
	private JTable rightFilesTable;
	private JTextField leftPath;
	private JTextField rightPath;
	private static JCommanderWindow windowRef;
	private JComboBox<File> rootDirLeft;
	private JComboBox<File> rootDirRight;
	private JLabel rightMainDirInfo;
	private JLabel leftMainDirInfo;
	protected boolean[] isFirstClick = new boolean[]{true, true};
	private static JCommanderController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCommanderWindow window = new JCommanderWindow();
					window.frame.setVisible(true);
					windowRef = window;
					controller = new JCommanderController(windowRef);
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});										
		
	}

	/**
	 * Create the application.
	 */
	public JCommanderWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem fileSubmenuItem = new JMenuItem("File");
		menuBar.add(fileSubmenuItem);
		
		JMenuItem editSubmenuItem = new JMenuItem("Edit");
		menuBar.add(editSubmenuItem);
		
		JMenuItem settingsSubmenuItem = new JMenuItem("Settings");
		menuBar.add(settingsSubmenuItem);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel leftPanel = new JPanel();
		mainPanel.add(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		leftPanel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_4.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		rootDirLeft = new JComboBox<>();
		panel.add(rootDirLeft);
		
		leftMainDirInfo = new JLabel("New label");
		panel.add(leftMainDirInfo);
		
		JButton button = new JButton("\\");
		panel.add(button);
		
		JButton leftBackButton = new JButton("..");
		panel.add(leftBackButton);
		leftBackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = leftPath.getText();
				File selected = new File(Utils.removeFileName(path));
				controller.notifySelectionChanged(WindowSide.LEFT, selected);
			}
		});
		
		leftPath = new JTextField();
		panel_4.add(leftPath, BorderLayout.SOUTH);
		leftPath.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		leftPanel.add(scrollPane, BorderLayout.CENTER);
		
		leftFilesTable = new JTable();
		scrollPane.setViewportView(leftFilesTable);
		leftFilesTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				//empty
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//empty
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				//empty
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				//
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isFirstClick[WindowSide.LEFT.getIndex()]) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {							
							try {
								Thread.sleep(Utils.DOUBLE_CLICK_PERIOD);
								isFirstClick[WindowSide.LEFT.getIndex()] = true;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start(); 
					isFirstClick[WindowSide.LEFT.getIndex()] = false;
					return;
				}
				File selected = (File) leftFilesTable.getModel().getValueAt(leftFilesTable.getSelectedRow(),0);
				if (selected.isDirectory()) {
					controller.notifySelectionChanged(WindowSide.LEFT, selected);
					isFirstClick[WindowSide.LEFT.getIndex()] = true;
				} else if (selected.isFile()) {
					JOptionPane.showMessageDialog(frame, Strings.cantOpen());
				}
			}
		});
		
		JPanel rightPanel = new JPanel();
		mainPanel.add(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		rightPanel.add(panel_5, BorderLayout.NORTH);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_5.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		rootDirRight = new JComboBox<>();
		panel_1.add(rootDirRight);
		
		rightMainDirInfo = new JLabel("New label");
		panel_1.add(rightMainDirInfo);
		
		JButton button_1 = new JButton("\\");
		panel_1.add(button_1);
		
		JButton rightBackButton = new JButton("..");
		panel_1.add(rightBackButton);
		rightBackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = rightPath.getText();
				File selected = new File(Utils.removeFileName(path));
				controller.notifySelectionChanged(WindowSide.RIGHT, selected);
			}
		});
		
		rightPath = new JTextField();
		panel_5.add(rightPath, BorderLayout.SOUTH);
		rightPath.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		rightPanel.add(scrollPane_1, BorderLayout.CENTER);
		
		rightFilesTable = new JTable();
		scrollPane_1.setViewportView(rightFilesTable);
		rightFilesTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				//empty
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//empty
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				//empty
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				//
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isFirstClick[WindowSide.RIGHT.getIndex()]) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {							
							try {
								Thread.sleep(Utils.DOUBLE_CLICK_PERIOD);
								isFirstClick[WindowSide.RIGHT.getIndex()] = true;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start(); 
					isFirstClick[WindowSide.RIGHT.getIndex()] = false;
					return;
				}
				File selected = (File) rightFilesTable.getModel().getValueAt(rightFilesTable.getSelectedRow(),0);
				if (selected.isDirectory()) {
					controller.notifySelectionChanged(WindowSide.RIGHT, selected);
					isFirstClick[WindowSide.RIGHT.getIndex()] = true;
				} else if (selected.isFile()) {
					JOptionPane.showMessageDialog(frame, Strings.cantOpen());
				}
			}
		});
		
	}

	public JComboBox<File> getRootDirLeft() {
		return rootDirLeft;
	}

	public JComboBox<File> getRootDirRight() {
		return rootDirRight;
	}

	public JTable getLeftFilesTable() {
		return leftFilesTable;
	}

	public JTable getRightFilesTable() {
		return rightFilesTable;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JTextField getTxtTest() {
		return txtTest;
	}

	public JTextField getLeftPath() {
		return leftPath;
	}

	public JTextField getRightPath() {
		return rightPath;
	}

	public static JCommanderWindow getWindowRef() {
		return windowRef;
	}

	public JLabel getRightMainDirInfo() {
		return rightMainDirInfo;
	}

	public JLabel getLeftMainDirInfo() {
		return leftMainDirInfo;
	}

}
