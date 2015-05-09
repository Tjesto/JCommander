package com.ms.jcommander.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Strings {		

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

}
