package main_frame.menu_bar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import grid.Graph;
import grid.GridConfCreator;
import grid.GridConfiguration;
import grid.square.MatrixGraph;
import main_frame.errors_panel.ErrorsPanel;
import main_frame.menu_bar.run_configuration.RunConfiguration;
import main_frame.rules_creator.RuleChoser;
import main_frame.states.StateChoser;
import rules.Rule;

// import RunConfiguration;

public class MenuBar extends JMenuBar {
	
	// Istanza che permette di accedere a info come screenSize etc
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// Informazioni per test Errori
	StateChoser state;
	GridConfCreator grid;
	RuleChoser rules;
	MatrixGraph graph;
	ErrorsPanel errorPanel;
	
	public MenuBar(StateChoser state, GridConfCreator grid, RuleChoser rules,  MatrixGraph graph, ErrorsPanel err) {
		
		//Inizializzo Informazioni per testing di Errori
		this.state = state;
		this.grid = grid;
		this.rules = rules;
		this.graph = graph;
		this.errorPanel = err;
		
		// File
		JMenu menuFile = new JMenu("File");
			// Voce Import che permette di caricare una configurazione completa 
			JMenuItem importRule = new JMenuItem(); 
			importRule.addActionListener(importConfiguration);
			importRule.setText("Import...");
			// Voce Export che permette di salvare l'intera configurazione in fomrato JSON
			JMenuItem exportRule = new JMenuItem();
			exportRule.addActionListener(exportConfiguration);
			exportRule.setText("Export...");
		menuFile.add(importRule);
		menuFile.add(exportRule);
		
		// Run
		JMenu menuRun = new JMenu("Run");
			// Voce Run che manda in esecuzione la configurazione creata dall'utente
			JMenuItem run = new JMenuItem("Run"); 
			run.addActionListener(runConfiguration);
			run.setText("Run");
			// Voce Run Configurations che permette di definire caratteristiche avanzate
			JMenuItem runConfiguration = new JMenuItem();
			runConfiguration.addActionListener(setConfiguration);
			runConfiguration.setText("Run Configuration...");
		menuRun.add(run);
		menuRun.add(runConfiguration);
		
		
		add(menuFile);
		add(menuRun);
	}
	
	// Metodo richiamato dal click di Run Configurations che apre il frame di definzione di caratteristiche avanzate
	ActionListener setConfiguration = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFrame runConf = new RunConfiguration();
			runConf.setBounds( (int)(screenSize.getWidth()/4),  (int)(screenSize.getHeight()/4), (int)(screenSize.getWidth()*0.35), (int)(screenSize.getHeight()*0.45));
			runConf.setVisible(true);
		}
	};
	
	// Metodo richiamato dal click di Run che avvia la simulazione usando la configurazione creata dall'utente
	ActionListener runConfiguration = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			errorPanel.update(state.getStates(), grid.getGridConfiguration(), rules.getRuleTrees(), graph, true);
		}
	};
	
	// Metodo richiamato dal click di Import che permette di importare configurazioni gia' esistenti
	ActionListener importConfiguration = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("importConfiguration");
		}
	};
		
	// Metodo richiamato dal click di Export che esporta la configurazione creata dall'utente
	ActionListener exportConfiguration = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("exportConfiguration");
		}
	};

}
