package com.ms.jcommander.model;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.List;

import javax.swing.JComboBox;

import com.ms.jcommander.controller.FilesController;
import com.ms.jcommander.controller.JCommanderController;
import com.ms.jcommander.model.ModelBinder.WindowSide;

public class RootDirectoriesModelBinder extends ModelBinder {
	
	private final JComboBox<File> left;
	private final JComboBox<File> right;	
	
	public RootDirectoriesModelBinder(
			JCommanderController jCommanderController,
			JComboBox<File> rootDirLeft, JComboBox<File> rootDirRight,
			FilesController filesController) {
		super(jCommanderController);
		left = rootDirLeft;
		right = rootDirRight;
		initWith(filesController.listDrives());
	}

	private void initWith(List<File> listDrives) {
		for (File f : listDrives) {
			left.addItem(f);
			right.addItem(f);
		}
		
		left.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					File selected = (File) event.getItem();
					controller.notifySelectionChanged(WindowSide.LEFT, selected);
				}
			}
		});
		
		right.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					File selected = (File) event.getItem();
					controller.notifySelectionChanged(WindowSide.RIGHT, selected);
				}
			}
		});
	}

	@Override
	public void updateViews() {
		// TODO Auto-generated method stub

	}

	public void updateViews(WindowSide side, File file) {		
		switch (side) {
		case LEFT:
			left.setSelectedItem(file);
			break;
		case RIGHT:
			right.setSelectedItem(file);
			break;
		default:
			break;
		}
	}

}
