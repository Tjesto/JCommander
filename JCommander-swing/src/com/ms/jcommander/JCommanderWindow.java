package com.ms.jcommander;

import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import com.ms.jcommander.controller.JCommanderController;
import com.ms.jcommander.listeners.AbstractOnClickListener;
import com.ms.jcommander.menus.ContextMenuItem;
import com.ms.jcommander.model.ModelBinder.WindowSide;
import com.ms.jcommander.utils.Strings;
import com.ms.jcommander.utils.Utils;

import javax.swing.JPopupMenu;
import java.awt.Dimension;

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
	protected boolean[] isFirstClick = new boolean[] { true, true };
	protected int[] lastSelected = new int[2];
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
		rootDirLeft.setMaximumSize(new Dimension(64, 32767));
		panel.add(rootDirLeft);

		leftMainDirInfo = new JLabel("New label");
		panel.add(leftMainDirInfo);

		JButton leftRootButton = new JButton("\\");
		panel.add(leftRootButton);
		leftRootButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = leftPath.getText();
				File selected = new File(Utils.getRoot(path));
				controller.notifySelectionChanged(WindowSide.LEFT, selected);
			}
		});		

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
		
		JPopupMenu leftTableContextMenu = new JPopupMenu();
		leftPanel.add(leftTableContextMenu, BorderLayout.SOUTH);
		leftFilesTable.addMouseListener(new AbstractOnClickListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3) {
					return;
				}	
				if (e.getButton() == MouseEvent.BUTTON3) {
					leftTableContextMenu.show(leftFilesTable, e.getX(), e.getY());
					return;
				}
				if (isFirstClick[WindowSide.LEFT.getIndex()]
						|| leftFilesTable.getSelectedRow() != lastSelected[WindowSide.LEFT
								.getIndex()]) {
					lastSelected[WindowSide.LEFT.getIndex()] = leftFilesTable.getSelectedRow();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(Utils.DOUBLE_CLICK_PERIOD);
								isFirstClick[WindowSide.LEFT.getIndex()] = true;
								lastSelected[WindowSide.LEFT.getIndex()] = -1;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
					isFirstClick[WindowSide.LEFT.getIndex()] = false;
					return;
				}
				File selected = (File) leftFilesTable.getModel().getValueAt(
						leftFilesTable.getSelectedRow(), 0);
				if (selected.isDirectory()) {
					controller
							.notifySelectionChanged(WindowSide.LEFT, selected);
					isFirstClick[WindowSide.LEFT.getIndex()] = true;
					lastSelected[WindowSide.LEFT.getIndex()] = -1;
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
		rootDirRight.setMaximumSize(new Dimension(64, 32767));
		panel_1.add(rootDirRight);

		rightMainDirInfo = new JLabel("New label");
		panel_1.add(rightMainDirInfo);

		JButton rightRootButton = new JButton("\\");
		panel_1.add(rightRootButton);
		
		rightRootButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = rightPath.getText();
				File selected = new File(Utils.getRoot(path));
				controller.notifySelectionChanged(WindowSide.RIGHT, selected);
			}
		});

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
		
		final JPopupMenu rightTableContextMenu = new JPopupMenu();
		initializePopupMenus(leftTableContextMenu,rightTableContextMenu);
		rightPanel.add(rightTableContextMenu, BorderLayout.SOUTH);
		rightFilesTable.addMouseListener(new AbstractOnClickListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3) {
					return;
				}	
				if (e.getButton() == MouseEvent.BUTTON3) {
					rightTableContextMenu.show(rightFilesTable, e.getX(), e.getY());
					return;
				}
				if (isFirstClick[WindowSide.RIGHT.getIndex()]
						|| rightFilesTable.getSelectedRow() != lastSelected[WindowSide.RIGHT
								.getIndex()]) {
					lastSelected[WindowSide.RIGHT.getIndex()] = rightFilesTable.getSelectedRow();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(Utils.DOUBLE_CLICK_PERIOD);
								isFirstClick[WindowSide.RIGHT.getIndex()] = true;
								lastSelected[WindowSide.RIGHT.getIndex()] = -1;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
					isFirstClick[WindowSide.RIGHT.getIndex()] = false;
					return;
				}
				File selected = (File) rightFilesTable.getModel().getValueAt(
						rightFilesTable.getSelectedRow(), 0);
				if (selected.isDirectory()) {
					controller.notifySelectionChanged(WindowSide.RIGHT,
							selected);
					isFirstClick[WindowSide.RIGHT.getIndex()] = true;
					lastSelected[WindowSide.RIGHT.getIndex()] = -1;
				} else if (selected.isFile()) {
					JOptionPane.showMessageDialog(frame, Strings.cantOpen());
				}
			}
		});

	}

	private void initializePopupMenus(JPopupMenu leftTableContextMenu,
			JPopupMenu rightTableContextMenu) {	
		
		ContextMenuItem.createAndAdd(Strings.newFolder(), leftTableContextMenu, rightTableContextMenu,
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String name = getName();
							controller.addNewDirectory(leftPath.getText(), name);
						} catch (IOException e1) {
							e1.printStackTrace();
						}						
					}
				},
		
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = getName();
					controller.addNewDirectory(rightPath.getText(), name);
				} catch (IOException e1) {
					e1.printStackTrace();
				}						
			}
		});
		
		ContextMenuItem.createAndAdd(Strings.delete(), leftTableContextMenu, rightTableContextMenu,
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							controller.removeFiles(leftFilesTable);
						} catch (IOException e1) {
							e1.printStackTrace();
						}						
					}
				},
		
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.removeFiles(rightFilesTable);
				} catch (IOException e1) {
					e1.printStackTrace();
				}						
			}
		});	
		
		
		
	}

	protected String getName() {		
		return JOptionPane.showInputDialog(frame, Strings.chooseName());
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
