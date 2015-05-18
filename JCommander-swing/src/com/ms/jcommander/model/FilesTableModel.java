package com.ms.jcommander.model;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.ms.jcommander.utils.Strings;
import com.ms.jcommander.utils.Utils;

public class FilesTableModel extends AbstractTableModel implements RowSorterListener {

	private File[] model;
	
	private final Map<Integer, Integer> indexMapper;
	
	public FilesTableModel(File[] files) {
		model = files;
		indexMapper = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < model.length; i++) {
			indexMapper.put(i, i);
		}
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
	
	public File getFileAt(int index) {
		return model[indexMapper.get(index)];
	}

	@Override
	public synchronized void sorterChanged(RowSorterEvent e) {
		for (Integer i : indexMapper.keySet()) {
			int newIndex = e.convertPreviousRowIndexToModel(i);
			if (newIndex >= 0) {
				indexMapper.put(i, newIndex);
			}
		}		
	}

}
