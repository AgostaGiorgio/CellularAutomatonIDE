package main_frame.menu_bar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.google.gson.Gson;

import grid.Graph;
import grid.GridConfCreator;
import grid.GridConfiguration;
import grid.square.MatrixGraph;
import main_frame.errors_panel.ErrorsPanel;
import main_frame.menu_bar.run_configuration.RunConfigurationFrame;
import main_frame.rules_creator.RuleChoser;
import main_frame.states.StateChoser;
import util.ConfigContainer;
import util.ConflictFinder;
import util.StaticUtil;

// import RunConfiguration;

public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	private static final String NO_STATE_INSERTED_MESSAGE_ERROR = "Inserisci almeno uno stato!";

	// Istanza che permette di accedere a info come screenSize etc
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	Graph graph; //grafo memorizzato qui: ci serve per run e run configurations
	
	// Informazioni per test Errori
	StateChoser state;
	GridConfCreator grid;
	RuleChoser rules;
	ErrorsPanel errorPanel;
	
	public MenuBar(StateChoser state, GridConfCreator grid, RuleChoser rules, ErrorsPanel err) {
		
		//Inizializzo Informazioni per testing di Errori
		this.state = state;
		this.grid = grid;
		this.rules = rules;
		this.graph = new MatrixGraph();
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
			// Voce Import Image che
			JMenuItem importImage = new JMenuItem();
			importImage.addActionListener(importImg);
			importImage.setText("Import Image...");
		menuFile.add(importRule);
		menuFile.add(exportRule);
		menuFile.add(importImage);

		
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
			if(state.getStates().size() > 0) {
				repairGraph(grid.getGridConfiguration(), state.getStates().get(0)); //ripara grafo prima di passarlo
				
				JFrame runConf = new RunConfigurationFrame(graph, grid.getGridConfiguration(), state.getStates());
				runConf.setBounds( (int)(screenSize.getWidth()/4),  (int)(screenSize.getHeight()/4), (int)(screenSize.getWidth()*0.35), (int)(screenSize.getHeight()*0.45));
				runConf.setVisible(true);
			}
			else
				StaticUtil.errorDialog(NO_STATE_INSERTED_MESSAGE_ERROR);
		}
	};
	
	// Metodo richiamato dal click di Run che avvia la simulazione usando la configurazione creata dall'utente
	ActionListener runConfiguration = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(errorPanel.update(state.getStates(), grid.getGridConfiguration(), rules.getRuleTrees(), graph, true)) {
				System.out.println("RUN!");
			}
		}
	};
	
	// Metodo richiamato dal click di Import che permette di importare configurazioni gia' esistenti
	ActionListener importConfiguration = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("importConfiguration");
			
			// Frame che permette di scegliere la destinazione e di dare un nome al file
			JFileChooser importPath = new JFileChooser();
			importPath.showOpenDialog(new JFrame());
			
			
			
		}
	};
		
	// Metodo richiamato dal click di Export che esporta la configurazione creata dall'utente
	ActionListener exportConfiguration = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			// Frame che permette di scegliere la destinazione e di dare un nome al file
			JFileChooser exportPath = new JFileChooser();
			exportPath.showSaveDialog(new JFrame());
			
			// Genero la path dove salvare il file
			String path = exportPath.getSelectedFile() + ".txt";
			if (path != "null.agm") {
				try { 
					// Creo il file su cui salvare il tutto
					File exportFile = new File(path); 
					exportFile.createNewFile();
					
					// Inizializzo il contenitore di tutte le informazioni da salvare
					ConfigContainer confContainer = new ConfigContainer(graph, state, grid, rules);
					
					// Inizializzo l'istanza JSON e converto l'oggetto
					Gson gson = new Gson();
					String dati = gson.toJson(confContainer);
					
					// salvo l'oggetto su file e chiudo il flusso
					FileWriter fw = new FileWriter(exportFile);
					fw.write(dati);
					fw.flush();
					fw.close();
				}
				catch (IOException e) {}
			}
		}
	};
	
	// Metodo richiamato dal click di import Image che importa una qualsiasi immagine selezionata dall'utente
	ActionListener importImg = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("importImage");
		}
	};
	
	/**ripara il grafo da eventuali incongruenze*/
	private void repairGraph(GridConfiguration gconf, Color defaultState) {
		ConflictFinder cf = new ConflictFinder(null, gconf, null, graph);
		if(cf.graphConfConflicts()) { //conflitto...
			if(graph instanceof MatrixGraph) //proviamo a sistemare la dimensione delle celle... e' il massimo che possiamo aggiustare
				((MatrixGraph)graph).setSize(gconf.getLen());
			if(cf.graphConfConflicts()) { //ci sono ancora conflitti, allora dobbiamo per forza ricreare il grafo da zero
				graph = Graph.buildGraph(gconf, defaultState);
			}
		}
	}

}
