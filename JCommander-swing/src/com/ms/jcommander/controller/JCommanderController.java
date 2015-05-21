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
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.FileUtils;

import com.ms.jcommander.JCommanderWindow;
import com.ms.jcommander.dialogs.JCommanderProgressDialog;
import com.ms.jcommander.dialogs.JCommanderProgressDialog.OnDisposeListener;
import com.ms.jcommander.dialogs.Toast;
import com.ms.jcommander.model.FilesTableModel;
import com.ms.jcommander.model.ModelBinder.WindowSide;
import com.ms.jcommander.model.RootDirectoriesModelBinder;
import com.ms.jcommander.utils.Strings;
import com.ms.jcommander.utils.Utils;
import com.sun.javafx.binding.StringFormatter;

public class JCommanderController implements OnDisposeListener {
	
	private final JCommanderWindow mainWindow;
	
	private final FilesController filesController;
	
	private File[] lastSelected;

	private RootDirectoriesModelBinder rootDirectiries;

	private TableRowSorter<FilesTableModel> sorter;
	
	private int sortedColumnNum = 0;
	
	private String newLeftPath;
	private String newRightPath;
	
	private class FilesRowSorter extends TableRowSorter<FilesTableModel> {
		
		public FilesRowSorter(FilesTableModel model) {
			super(model);
		}

		@Override
		public void toggleSortOrder(int column) {
			sortedColumnNum = column;
			super.toggleSortOrder(column);
		}
				
	}

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
			Date d1 = (Date) ((Object[]) o1)[1];
			Date d2 = (Date) ((Object[]) o2)[1];
			int dates = d1.before(d2) ? -1 : d1.after(d2) ? 1 : 0;
			return dates;
		}
	};

	private volatile boolean isAborted;

	private JCommanderProgressDialog dialog;

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
		sorter = new FilesRowSorter(model);
		sorter.addRowSorterListener(model);
		table.setRowSorter(sorter);
		sorter.setComparator(0, nameComparator);
		sorter.setComparator(1, sizeComparator);
		sorter.setComparator(2, modificationComparator);
		sorter.toggleSortOrder(sortedColumnNum);
		sorter.sort();
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
		isAborted = false;
		
		if (table.getSelectedRows().length == 0 ) {
			Toast.show(Strings.noSelection(), Toast.LENGHT_LONG);
			return;
		}
		if (JOptionPane.showConfirmDialog(mainWindow.getFrame(), Strings.sure(), "", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
			return;
		}
		dialog = JCommanderProgressDialog.show(Strings.copy(),
				Strings.removeMessage(), table.getSelectedRowCount(),
				mainWindow.generateMovePositiveAction(),
				mainWindow.getStandardAbortAction());
		dialog.setOnDisposeListener(this);
		new Thread(new Runnable() {
			public void run() {
				newLeftPath = mainWindow.getLeftPath().getText();
				newRightPath = mainWindow.getRightPath().getText();
				int i = 1;
				for (int index : table.getSelectedRows()) {
					File f = (File) ((FilesTableModel) table.getModel())
							.getFileAt(index);
					if (isAborted) {
						break;
					}
					try {
						System.out.println(f.getName());
						if (newLeftPath.contains(f.getCanonicalPath())) {
							newLeftPath = Utils.removeFileName(newLeftPath);
						}
						if (newRightPath.contains(f.getCanonicalPath())) {
							newRightPath = Utils.removeFileName(newRightPath);
						}
						if (f.canWrite()) {
							if (f.isDirectory()) {
								checkDirs(f);
								FileUtils.deleteDirectory(f);
							} else if (f.isFile()) {
								FileUtils.forceDelete(f);
							}
							updateProgress(i);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						i++;
					}

				}
			}
		}).start();
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
		if (name == null) {
			return;
		}
		File f = new File(path, name);
		if (f.exists()) {
			System.out.println("exists " + name);
			if (JOptionPane.showConfirmDialog(mainWindow.getFrame(), Strings.dirExists(name, path), "", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}			
		}
		boolean s = f.mkdirs();
		notifySelectionChanged(WindowSide.LEFT, new File (mainWindow.getLeftPath().getText()));
		notifySelectionChanged(WindowSide.RIGHT, new File (mainWindow.getRightPath().getText()));
	}

	public void invalidate() {
		notifySelectionChanged(WindowSide.LEFT, new File (mainWindow.getLeftPath().getText()));
		notifySelectionChanged(WindowSide.RIGHT, new File (mainWindow.getRightPath().getText()));
	}

	public void copyFiles(JTable files, String from, String to) throws IOException {
		isAborted = false;
		System.out.println(files + "\n" + files.getSelectedRowCount() + "\n" + files.getSelectedRows().length);
		if (files.getSelectedRows().length == 0) {
			Toast.show(Strings.noSelection(), Toast.LENGHT_LONG);
			return;
		}
		dialog = JCommanderProgressDialog.show(Strings.copy(), Strings.copyMessage(), files.getSelectedRowCount(), mainWindow.generateMovePositiveAction(), mainWindow.getStandardAbortAction());
		dialog.setOnDisposeListener(this);		
		new Thread(new Runnable() {
			public void run() {
				newLeftPath = mainWindow.getLeftPath().getText();
				newRightPath = mainWindow.getRightPath().getText();
				int i = 1;
				for (int index : files.getSelectedRows()) {
					File f = (File) ((FilesTableModel) files.getModel())
							.getFileAt(index);
					if (isAborted) {
						break;
					}
					try {
						System.out.println("Copy " + f.getName());
						if (newLeftPath.contains(f.getCanonicalPath())) {
							newLeftPath = Utils.removeFileName(newLeftPath);
						}
						if (newRightPath.contains(f.getCanonicalPath())) {
							newRightPath = Utils.removeFileName(newRightPath);
						}
						if (f.canWrite()) {
							if (f.isDirectory()) {
								if (new File(to, f.getName()).exists() && doNotCopyThis(Strings.dirExists(f.getName(), to))) {
									updateProgress(i++);
									continue;
								}
								checkDirs(f);
								FileUtils.copyDirectoryToDirectory(f, new File(
										to));
							} else if (f.isFile()) {
								if (new File(to, f.getName()).exists() && doNotCopyThis(Strings.fileExists(f.getName(), to))) {
									updateProgress(i++);
									continue;
								}
								FileUtils.copyFileToDirectory(f, new File(to));
							}
							updateProgress(i);
						}						
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						i++;
					}

				}
			}
		}).start();		
		
	}
	
	protected synchronized boolean doNotCopyThis(String message) {		
		return JOptionPane.showConfirmDialog(mainWindow.getFrame(), message, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}
	
	public void moveFiles(JTable files, String from, String to) throws IOException {		
		isAborted = false;
		if (files.getSelectedRows().length == 0) {
			Toast.show(Strings.noSelection(), Toast.LENGHT_LONG);
			return;
		}
		dialog = JCommanderProgressDialog.show(Strings.move(), Strings.moveMessage(), files.getSelectedRowCount(), mainWindow.generateMovePositiveAction(), mainWindow.getStandardAbortAction());
		dialog.setOnDisposeListener(this);
		new Thread(new Runnable() {					

		public void run() {
			newLeftPath = mainWindow.getLeftPath().getText();
			newRightPath = mainWindow.getRightPath().getText();
				int i = 1;
				for (int index : files.getSelectedRows()) {
					if (isAborted) {
						break;
					}
					try {
						File f = (File) ((FilesTableModel) files.getModel())
								.getFileAt(index);
						System.out.println("Move " + f.getName());
						if (newLeftPath.contains(f.getCanonicalPath())) {
							newLeftPath = Utils.removeFileName(newLeftPath);
						}

						if (newRightPath.contains(f.getCanonicalPath())) {
							newRightPath = Utils.removeFileName(newRightPath);
						}
						if (f.canWrite()) {
							if (f.isDirectory()) {
								if (new File(to, f.getName()).exists() && doNotCopyThis(Strings.dirExists(f.getName(), to))) {
									updateProgress(i++);
									continue;
								}
								checkDirs(f);
								FileUtils.copyDirectoryToDirectory(f, new File(
										to));
								FileUtils.deleteDirectory(f);
							} else if (f.isFile()) {
								if (new File(to, f.getName()).exists() && doNotCopyThis(Strings.fileExists(f.getName(), to))) {
									updateProgress(i++);
									continue;
								}
								FileUtils.copyFileToDirectory(f, new File(to));
								FileUtils.forceDelete(f);
							}
							updateProgress(i);
						}						
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						i++;
					}
				}
			}
		}).start();
		isAborted = false;
				
	}

	protected void updateProgress(int i) {
		double val = dialog.updateProgress(i);
		mainWindow.getProgress().setValue((int) val);
		if ((int) val == 100) {
			mainWindow.getProgress().setValue(0);
			mainWindow.getProgress().setVisible(false);
			disposed();			
		}
		
	}

	public void abort() {
		isAborted = true;
	}

	@Override
	public void disposed() {
		notifySelectionChanged(WindowSide.LEFT, new File (newLeftPath));
		notifySelectionChanged(WindowSide.RIGHT, new File (newRightPath));
	}

	public void moveToBackground() {
		dialog.dispose();
		mainWindow.getProgress().setVisible(true);		
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
