package main_frame.menu_bar.run_configuration;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Component;
import javax.swing.Box;

public class RunConfiguration extends JFrame {
	
	JRadioButton rdbtnSincrono;
	JRadioButton rdbtnAsincrono;
	
	public RunConfiguration() {
		setAutoRequestFocus(false);
		setVisible(true);
		
		JPanel pannello = new JPanel();
		getContentPane().add(pannello);		
		
		// Creazione Layout
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.01, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
		gridBagLayout.rowWeights = new double[]{0.01, 0.01, 0.1, 0.1, 0.0, 0.1, 0.1, 0.1};
		pannello.setLayout(gridBagLayout);
		
		rdbtnSincrono = new JRadioButton("Sincorno");
		rdbtnSincrono.addActionListener(onClickSincrono);
		
		rdbtnAsincrono = new JRadioButton("Asincrono");
		rdbtnAsincrono.addActionListener(onClickAsincrono);
		GridBagConstraints gbc_rdbtnAsincrono = new GridBagConstraints();
		gbc_rdbtnAsincrono.anchor = GridBagConstraints.WEST;
		gbc_rdbtnAsincrono.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAsincrono.gridx = 1;
		gbc_rdbtnAsincrono.gridy = 3;
		pannello.add(rdbtnAsincrono, gbc_rdbtnAsincrono);
		GridBagConstraints gbc_rdbtnSincrono = new GridBagConstraints();
		gbc_rdbtnSincrono.anchor = GridBagConstraints.WEST;
		gbc_rdbtnSincrono.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSincrono.gridx = 1;
		gbc_rdbtnSincrono.gridy = 5;
		pannello.add(rdbtnSincrono, gbc_rdbtnSincrono);
		
	}
	
	// Gestione RadioButton Sincrono/Asincrono
	ActionListener onClickAsincrono = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) { if (rdbtnSincrono.isSelected()) {rdbtnSincrono.setSelected(false);} }
	};
	
		ActionListener onClickSincrono = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) { if (rdbtnAsincrono.isSelected()) {rdbtnAsincrono.setSelected(false);} }
	};

	
}