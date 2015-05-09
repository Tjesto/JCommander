package com.ms.jcommander.model;

import com.ms.jcommander.controller.JCommanderController;

public abstract class ModelBinder {
	
	public enum WindowSide {LEFT(0), RIGHT(1);
	
	private int index;
	
	private WindowSide(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}}
	
	protected JCommanderController controller;
	public ModelBinder(JCommanderController jCommanderController) {
		controller = jCommanderController;
	}

	public abstract void updateViews();		
}
