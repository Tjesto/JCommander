package com.ms.jcommander.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ms.jcommander.listeners.OnLocalesChangeListener;

public final class LanguageUtils {
	
	public static final Locale PL = new Locale("pl");
	public static final Locale EN = Locale.ENGLISH;
	public static final Locale IT = Locale.ITALIAN;
	private static LanguageUtils instance;
	private final List<OnLocalesChangeListener> listeners = new ArrayList<>();
	
	private LanguageUtils() {
		//empty
	}
	
	public static LanguageUtils languageUtils() {
		if (instance == null) {
			instance = new LanguageUtils();
		}
		
		return instance;
	}
	
	public void addListener(OnLocalesChangeListener listener) {
		listeners.add(listener);		
	}
	
	public synchronized void setLocale(Locale l) {
		Locale.setDefault(l);
		for (OnLocalesChangeListener listener : listeners) {
			listener.onLocalesChanged();
		}
	}

}
