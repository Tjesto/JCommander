package com.ms.jcommander.dialogs;

public class DialogButton {
	
	private final String label;
	
	private final Runnable onClickAction;
	
	public DialogButton(String label, Runnable action) {
		this.label = label;
		onClickAction = action;
	}

	public String getLabel() {
		return label;
	}

	public Runnable getOnClickAction() {
		return onClickAction;
	}
	
}
