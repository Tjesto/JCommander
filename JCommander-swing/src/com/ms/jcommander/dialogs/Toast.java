package com.ms.jcommander.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Window.Type;
import java.awt.Cursor;

import javax.swing.JLabel;

import com.ms.jcommander.JCommanderWindow;

public class Toast extends JDialog {

	public static final long LENGHT_LONG = 5000;
	public static final long LENGHT_SHORT = 2000;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void show(String message, long length) {
		try {
			final Toast dialog = new Toast(message);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(length);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dialog.dispose();
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param length 
	 * @param message 
	 */
	public Toast(String message) {
		setUndecorated(true);
		setOpacity(0.8f);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setType(Type.POPUP);
		setResizable(false);
		
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel label = new JLabel(message);
		contentPanel.add(label);
		Rectangle r = JCommanderWindow.getWindowRef().getFrame().getBounds();		
		Rectangle bounds = label.getBounds();
		setBounds((int) (r.x + ((r.width - 200) / 2)),
				(int) (r.y + (r.height - 50)), 200,
				50);
	}

}
