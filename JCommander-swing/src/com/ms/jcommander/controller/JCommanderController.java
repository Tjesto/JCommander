package com.ms.jcommander.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import com.ms.jcommander.JCommanderWindow;
import com.ms.jcommander.model.FilesTableModel;
import com.ms.jcommander.model.ModelBinder.WindowSide;
import com.ms.jcommander.model.RootDirectoriesModelBinder;
import com.ms.jcommander.utils.Strings;
import com.ms.jcommander.utils.Utils;

public class JCommanderController {
	
	private final JCommanderWindow mainWindow;
	
	private final FilesController filesController;
	
	private File[] lastSelected;

	private RootDirectoriesModelBinder rootDirectiries;

	public JCommanderController(JCommanderWindow windowRef) {
		mainWindow = windowRef;
		filesController = new FilesController();
		lastSelected = new File[] {filesController.getDefaultRoot(),
				filesController.getDefaultRoot() };
		notifySelectionChanged(WindowSide.LEFT, lastSelected[0]);
		notifySelectionChanged(WindowSide.RIGHT, lastSelected[1]);
	}
	
	public void start() {
		rootDirectiries = new RootDirectoriesModelBinder(this, mainWindow
				.getRootDirLeft(), mainWindow.getRootDirRight(),
				filesController);
	}

	public void notifySelectionChanged(WindowSide side, File selected) {
		if (selected == null || selected.listFiles() == null) {							
			JOptionPane.showMessageDialog(mainWindow.getFrame(),
					Strings.diskNotAvailableMessage(),
					Strings.diskNotAvailableTitle(), JOptionPane.ERROR_MESSAGE);
			rootDirectiries.updateViews(side, lastSelected[side.getIndex()]);			
			return;
		}
		switch (side) {
		case LEFT:
			updateRootSelection(selected, mainWindow.getLeftFilesTable(),
					mainWindow.getLeftMainDirInfo(), mainWindow.getLeftPath());
			break;
		case RIGHT:
			updateRootSelection(selected, mainWindow.getRightFilesTable(),
					mainWindow.getRightMainDirInfo(), mainWindow.getRightPath());
			break;
		}
		lastSelected[side.getIndex()] = selected;
	}

	private void updateRootSelection(File selected, JTable table, JLabel label, JTextField path) {
		File[] files = selected.listFiles();
		table.setModel(new FilesTableModel(files));
		StringBuilder builder = new StringBuilder();
		builder.append(selected.getFreeSpace() / 1024).append("k ")
				.append(Strings.of()).append(' ')
				.append(selected.getTotalSpace() / 1024).append("k ")
				.append(Strings.free());
		label.setText(builder.toString());
		path.setText(selected.getAbsolutePath());
	}

	public void removeFiles(JTable table) throws IOException {
		for (int index : table.getSelectedRows()) {
			File f = (File) table.getModel().getValueAt(index, 0);
			if (f.canWrite()) {				
				if (f.isDirectory()) {
					checkDirs(f);
					FileUtils.deleteDirectory(f);
				} else if (f.isFile()) {
					f.delete();
				}
			}
		}
		notifySelectionChanged(WindowSide.LEFT, new File (mainWindow.getLeftPath().getText()));
		notifySelectionChanged(WindowSide.RIGHT, new File (mainWindow.getRightPath().getText()));
	}
	
	private void checkDirs(File f) {
		if (mainWindow.getLeftPath().getText().equals(f.getAbsolutePath())) {
			notifySelectionChanged(WindowSide.LEFT, new File(Utils.removeFileName(mainWindow.getLeftPath().getText())));
		}
		
		if (mainWindow.getRightPath().getText().equals(f.getAbsolutePath())) {
			notifySelectionChanged(WindowSide.LEFT, new File(Utils.removeFileName(mainWindow.getRightPath().getText())));
		}
		
	}

	public void addNewDirectory(String path, String name) throws IOException {
		File f = new File(path, name);
		if (f.exists()) {
			return;
		}
		boolean s = f.mkdirs();
		notifySelectionChanged(WindowSide.LEFT, new File (mainWindow.getLeftPath().getText()));
		notifySelectionChanged(WindowSide.RIGHT, new File (mainWindow.getRightPath().getText()));
	}

}
