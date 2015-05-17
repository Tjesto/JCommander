package com.ms.jcommander.controller;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.FileUtils;

import com.ms.jcommander.JCommanderWindow;
import com.ms.jcommander.model.FilesTableModel;
import com.ms.jcommander.model.ModelBinder.WindowSide;
import com.ms.jcommander.model.RootDirectoriesModelBinder;
import com.ms.jcommander.utils.Strings;
import com.ms.jcommander.utils.Utils;
import com.sun.javafx.binding.StringFormatter;

public class JCommanderController {
	
	private final JCommanderWindow mainWindow;
	
	private final FilesController filesController;
	
	private File[] lastSelected;

	private RootDirectoriesModelBinder rootDirectiries;

	private TableRowSorter<FilesTableModel> sorter;

	private final Comparator<File> nameComparator = new Comparator<File>() {
		
		@Override
		public int compare(File o1, File o2) {			
			
			int dirs = compareDirectories(o1, o2);			
			
			return dirs != 0 ? dirs : o1.getName().compareToIgnoreCase(o2.getName());
		}
	};

	private final Comparator<Object> sizeComparator = new Comparator<Object>() {
		
		@Override
		public int compare(Object o1, Object o2) {			
			if (o1 instanceof String && o2 instanceof Long) {
				return -1;
			}
			if (o2 instanceof String && o1 instanceof Long) {
				return 1;
			}
						
			if ((o1 instanceof String && o2 instanceof String) || (o1 instanceof Long && o2 instanceof Long && (Long) o1 == (Long) o2)) {
				return 0;//o1.getName().compareToIgnoreCase(o2.getName());				
			}			
			return (Long) o1 > (Long) o2 ? -1 : (Long) o1 < (Long) o2 ? 1 : 0;
		}
	};

	private final Comparator<Object> modificationComparator = new Comparator<Object>() {
		
		@Override
		public int compare(Object o1, Object o2) {
			if ((Boolean) ((Object[]) o1)[0] && !((Boolean) ((Object[]) o2)[0])) {
				return -1;
			}
			if ((Boolean) ((Object[]) o2)[0] && !((Boolean) ((Object[]) o1)[0])) {
				return 1;
			}
			if ((Boolean) ((Object[]) o1)[0] && ((Boolean) ((Object[]) o2)[0])) {
				return 0;
			}
			Date d1 = (Date) ((Object[]) o1)[1];
			Date d2 = (Date) ((Object[]) o2)[1];
			int dates = d1.before(d2) ? -1 : d1.after(d2) ? 1 : 0;
			return dates;
		}
	};

	public JCommanderController(JCommanderWindow windowRef) {
		mainWindow = windowRef;
		filesController = new FilesController();
		lastSelected = new File[] {filesController.getDefaultRoot(),
				filesController.getDefaultRoot() };
		notifySelectionChanged(WindowSide.LEFT, lastSelected[0]);
		notifySelectionChanged(WindowSide.RIGHT, lastSelected[1]);
	}
	
	private int compareDirectories(File o1, File o2) {
		if (o1.isDirectory() && !o2.isDirectory()) {
			return -1;
		}
		
		if (!o1.isDirectory() && o2.isDirectory()) {
			return 1;
		}
		
		return 0;
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
		FilesTableModel model = new FilesTableModel(files);
		table.setModel(model);
		sorter = new TableRowSorter<FilesTableModel>(model);
		table.setRowSorter(sorter);
		sorter.setComparator(0, nameComparator);
		sorter.setComparator(1, sizeComparator);
		sorter.setComparator(2, modificationComparator);
		sorter.toggleSortOrder(0);
		StringBuilder builder = new StringBuilder();
		builder.append(selected.getFreeSpace() / 1024).append("k ")
				.append(Strings.of()).append(' ')
				.append(selected.getTotalSpace() / 1024).append("k ")
				.append(Strings.free());
		label.setText(builder.toString());
		path.setText(selected.getAbsolutePath());		
		table.getColumn(Strings.name(0)).setCellRenderer(new FilesNamesTableRenderer());
		table.getColumn(Strings.name(1)).setCellRenderer(new FilesSizesTableRenderer());
		table.getColumn(Strings.name(2)).setCellRenderer(new FilesModificationTableRenderer());
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

	public void invalidate() {
		notifySelectionChanged(WindowSide.LEFT, new File (mainWindow.getLeftPath().getText()));
		notifySelectionChanged(WindowSide.RIGHT, new File (mainWindow.getRightPath().getText()));
	}

}

class FilesNamesTableRenderer extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		File f = (File) value;
		return super.getTableCellRendererComponent(table, f.getName(), isSelected, hasFocus,
				row, column);
	}
	
}

class FilesSizesTableRenderer extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Object o; 
		if (value instanceof String) {
			o = value;
		} else {
			double v = (long) value;			
			String[] values = new String[] {"B", "kB", "MB", "GB", "TB"};
			int i = 0;
			for (i = 0; i < values.length && v > 1024; i++, v /= 1024);
			o = String.format(Locale.getDefault(), "%,.2f%s", v, values[i]);
		}
		return super.getTableCellRendererComponent(table, o, isSelected, hasFocus,
				row, column);
	}
	
}

class FilesModificationTableRenderer extends DefaultTableCellRenderer {	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {	
		Object[] values = (Object[]) value;
		String s = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.DEFAULT, Locale.getDefault()).format((Date) values[1]);
		return super.getTableCellRendererComponent(table, s, isSelected, hasFocus,
				row, column);
	}
	
}
