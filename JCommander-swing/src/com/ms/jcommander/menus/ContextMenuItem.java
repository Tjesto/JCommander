package com.ms.jcommander.menus;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ms.jcommander.model.ModelBinder.WindowSide;

public class ContextMenuItem extends JMenuItem {

	private final WindowSide side;
	
	public ContextMenuItem(String text, Icon icon, WindowSide side) {
		super(text, icon);
		this.side = side;
	}

	public ContextMenuItem(String text, int mnemonic, WindowSide side) {
		super(text, mnemonic);
		this.side = side;
	}

	public ContextMenuItem(String text, WindowSide side) {
		super(text);
		this.side = side;
	}

	public WindowSide getSide() {
		return side;
	}
	
	public ContextMenuItem addActionListenerTo(ActionListener l) {		
		addActionListener(l);
		return this;
	}

	public static void createAndAdd(String label,
			JPopupMenu leftTableContextMenu, JPopupMenu rightTableContextMenu,
			ActionListener leftListener, ActionListener rightListener) {
		leftTableContextMenu.add(new ContextMenuItem(label, WindowSide.LEFT).addActionListenerTo(leftListener));
		rightTableContextMenu.add(new ContextMenuItem(label, WindowSide.RIGHT).addActionListenerTo(rightListener));
		
	}	

}
