package com.ms.jcommander.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

public class Strings {		
	
	private static final String[] keys = new String[]{"hader.file", "header.size", "header.modified"};
	
	private static String getString(String key) {
		ResourceBundle rb = ResourceBundle.getBundle("com.ms.jcommander.res.Strings", Locale.getDefault());
		return rb.getString(key);
	}

	public static String of() {
		return getString("free.bytes.of");
	}
	
	public static String free() {
		return getString("free");
	}

	public static String diskNotAvailableMessage() {
		return getString("file.not.available.msg");
	}
	
	public static String diskNotAvailableTitle() {
		return getString("file.not.available.title");
	}

	public static String cantOpen() {
		return getString("file.cant.open");
	}

	public static String delete() {
		return getString("delete");
	}
	
	public static String newFolder() {
		return getString("new.folder");
	}

	public static Object chooseName() {
		return getString("choose.name");
	}

	public static String name(int column) {
		return getString(keys[column]);
	}

	public static String menuSettings() {
		return getString("menus.settings");
	}
	
	public static String menuFile() {
		return getString("menus.file");
	}
	
	public static String menuEdit() {
		return getString("menus.edit");
	}
	
	public static String menuLanguage() {
		return getString("menus.language");
	}
	
	public static String polishLang() {
		return getString("lang.polish");
	}
	public static String englishLang() {
		return getString("lang.english");
	}
	public static String italianLang() {
		return getString("lang.italian");
	}

	public static String copy() {
		return getString("copy");
	}

	public static String move() {
		return getString("move");
	}
	
	public static String moveMessage() {
		return getString("move.dialog.message");
	}
	
	public static String backgroundButton() {
		return getString("possitive.label");
	}
	
	public static String abortButton() {
		return getString("negative.label");
	}
	
	public static String removeMessage() {
		return getString("remove.dialog.message");
	}
	
	public static String copyMessage() {
		return getString("copy.dialog.message");
	}

	public static String dirExists(String name, String path) {
		StringBuilder b = new StringBuilder();
		b.append(getString("dir.exist.begining")).append(exists(name, path)).append(getString("dir.exist.end"));
		return b.toString();
	}
	
	public static String fileExists(String name, String path) {
		StringBuilder b = new StringBuilder();
		b.append(getString("file.exist.begining")).append(exists(name, path)).append(getString("file.exist.end"));
		return b.toString();
	}
	
	private static String exists(String name, String path) {
		StringBuilder b = new StringBuilder();
		b.append(name).append(' ').append(getString("exists.in")).append(' ').append(path).append(".\n");
		return b.toString();
	}

	public static String refresh() {
		return getString("refresh");
	}

	public static String noSelection() {
		return getString("no.selection");
	}

	public static String sure() {
		return getString("sure");
	}

}
