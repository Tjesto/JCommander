package com.ms.jcommander.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Strings {		
	
	private static final String[] keys = new String[]{"hader.file", "header.size", "header.modified"};

	public static String of() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("free.bytes.of");
	}
	
	public static String free() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("free");
	}

	public static String diskNotAvailableMessage() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("file.not.available.msg");
	}
	
	public static String diskNotAvailableTitle() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("file.not.available.title");
	}

	public static Object cantOpen() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("file.cant.open");
	}

	public static String delete() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("delete");
	}
	
	public static String newFolder() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("new.folder");
	}

	public static Object chooseName() {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString("choose.name");
	}

	public static String name(int column) {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());		
		return rb.getString(keys[column]);
	}

}
