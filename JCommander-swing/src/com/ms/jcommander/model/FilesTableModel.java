package com.ms.jcommander.model;

import java.io.File;
import java.util.Date;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.ms.jcommander.utils.Strings;
import com.ms.jcommander.utils.Utils;

public class FilesTableModel extends AbstractTableModel {

	private File[] model;
	
	public FilesTableModel(File[] files) {
		model = files;
	}

	@Override
	public int getColumnCount() {		
		return 3;
	}

	@Override
	public int getRowCount() {
		return model.length;
	}	
	
	@Override
	public String getColumnName(int column) {		
		return Strings.name(column);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {	
		switch (columnIndex){
		case 0:
			return model[rowIndex];
		case 1:
			File f = model[rowIndex];
			return f.isDirectory()? "<DIR>" : f.length();
		case 2:
			return new Object[] {model[rowIndex].isDirectory(), new Date(model[rowIndex].lastModified()) };
		}
		return null;
	}

}
