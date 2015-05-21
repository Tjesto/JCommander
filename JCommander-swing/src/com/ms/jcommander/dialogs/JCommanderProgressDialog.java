package com.ms.jcommander.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

import com.ms.jcommander.JCommanderWindow;
import com.ms.jcommander.utils.Strings;

public class JCommanderProgressDialog extends JDialog {
	
	public interface OnDisposeListener {
		void disposed();
	}

	private final JPanel contentPanel = new JPanel();
	private JLabel message;
	private String mainMessage;
	private JProgressBar progress;
	private int current = -1;
	private int maxValue = -1;
	private OnDisposeListener onDisposeListener;

	/**
	 * Launch the application.
	 */
	public static JCommanderProgressDialog show(String title, String message, int maxValue, DialogButton positive, DialogButton negative) {
		try {
			JCommanderProgressDialog dialog = new JCommanderProgressDialog(JCommanderWindow.getWindowRef(), positive, negative);
			dialog.setTitle(title);
			dialog.setMessage(message);
			dialog.setMaxValue(maxValue);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			return dialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public double updateProgress(int current) {		
		this.current = current;
		double value = -1;
		if (maxValue > 0) {
			value = ((double) current/(double) maxValue) * 100;
			this.progress.setValue((int) value);
			updateMessage(value);
		} else {
			updateMessage(-1);
		}
		if (current >= maxValue) {
			dispose();
		}
		return value;
	}

	private void updateMessage(double value) {
		StringBuilder b = new StringBuilder();
		b.append(mainMessage != null ? mainMessage : "").append('\n');
		if (current >= 0 && maxValue > 0) {
			b.append(current).append(' ').append(Strings.of()).append(' ').append(maxValue);
		}
		b.append('(').append(!(value < 0) ? value : "?").append(')');
		this.message.setText(b.toString());
	}

	public void setMaxValue(int maxValue) {
		if (this.maxValue < 0) {
			this.maxValue = maxValue;
			updateProgress(0);
			this.progress.setMaximum(100);
		}
	}

	public void setMessage(String message) {
		this.mainMessage = message;
		this.message.setText(message);
	}

	/**
	 * Create the dialog.
	 * @param negative 
	 * @param positive 
	 */
	public JCommanderProgressDialog(JCommanderWindow referenceWindow, DialogButton positive, DialogButton negative) {
		int height = 200;
		int width = 450;
		Rectangle r = referenceWindow.getFrame().getBounds();
		setBounds((int) (r.x + ((r.getWidth() - width)/2)), (int) (r.y + ((r.getHeight() - height)/2)), width, height);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			message = new JLabel("New label");
			message.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(message, BorderLayout.NORTH);
		}
		{
			progress = new JProgressBar();
			contentPanel.add(progress, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton possitiveButton = new JButton(positive.getLabel());
				//possitiveButton.setActionCommand("OK");
				possitiveButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						positive.getOnClickAction().run();
						JCommanderProgressDialog.this.dispose();
					}
				});
				buttonPane.add(possitiveButton);
				getRootPane().setDefaultButton(possitiveButton);
			}
			{
				JButton negativeButton = new JButton(negative.getLabel());
				//negativeButton.setActionCommand("Cancel");
				negativeButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						negative.getOnClickAction().run();
						JCommanderProgressDialog.this.dispose();
					}
				});
				buttonPane.add(negativeButton);
			}
		}
	}

	public void setOnDisposeListener(OnDisposeListener listener) {
		this.onDisposeListener = listener;
	}
	
	@Override
	public void dispose() {		
		super.dispose();
		if (onDisposeListener != null) {
			onDisposeListener.disposed();			
		}
	}

	public JProgressBar getProgress() {
		return progress;
	}

}
