package util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import grid.CellForm;
import grid.Graph;
import grid.GridConfiguration;
import grid.hexagon.HexCell;
import grid.square.MatrixGraph;
import grid.square.SquareCell;
import grid.triangle.TriangularCell;
import rules.Rule;

/**classe che trova i possibili conflitti*/
public class ConflictFinder {
	
	public static final String STATE_RULE_CNF = "Stato inesistente usato nelle regole";
	public static final String STATE_INITCONF_CNF = "Stato inesistente usato nella configurazione iniziale";
	public static final String INITCONF_GRIDCONF_CNF = "La configurazione iniziale non rispetta le propriet� della griglia";
	public static final String INITCONF_EMPTY = "Configurazione iniziale vuota"; 
	
	private ArrayList<Color> stateColors;
	private GridConfiguration gconf;
	private ArrayList<Rule> rules;
	private Graph graph;
	
	public ConflictFinder(ArrayList<Color> stateColors, GridConfiguration gconf, ArrayList<Rule> rules, Graph graph) {
		this.stateColors = stateColors;
		this.gconf = gconf;
		this.rules = rules;
		this.graph = graph;
	}
	
	/**ci sono conflitti?*/
	public boolean containsConflicts() {
		return getConflicts().size() != 0;
	}
	
	/**controlla se ci sono conflitti col grafo e la configurazione*/
	public boolean graphConfConflicts() {
		return getGraphConfConflicts() != null;
	}
	
	/**controlla se ci sono conflitti col grafo e la configurazione*/
	public String getGraphConfConflicts() {
		if(graph.getNumNodes() == 0) //nessun nodo nella configurazione iniziale
			return INITCONF_EMPTY;
		else { //c'e' almeno una cella
			//controlla coerenza forma->tipo grafo
			if(!(graph.getCell(1) instanceof SquareCell) && gconf.getForm() == CellForm.SQUARE)
				return INITCONF_GRIDCONF_CNF;
			else
				if(!(graph.getCell(1) instanceof TriangularCell) && gconf.getForm() == CellForm.TRIANGLE)
					return INITCONF_GRIDCONF_CNF;
				else
					if(!(graph.getCell(1) instanceof HexCell) && gconf.getForm() == CellForm.HEXAGON)
						return INITCONF_GRIDCONF_CNF;
					else {
						MatrixGraph mtx = (MatrixGraph)graph;
						if(mtx.getWidth() != gconf.getNumCellsX() || mtx.getHeight() != gconf.getNumCellsY() || mtx.getSize() != gconf.getLen())
							return INITCONF_GRIDCONF_CNF;
					}
		}
		return null;
	}
	
	/**restituisce set con la descrizione dei conflitti trovati (se il set e' vuoto non ci sono conflitti)*/
	public HashSet<String> getConflicts() {
		HashSet<String> errors = new HashSet<String>();
		HashSet<Integer> rgbColors = new HashSet<Integer>();
		for(Color c : stateColors)
			rgbColors.add(c.getRGB());
		
		boolean goOn = true;
		for(Rule r : rules) { //cerchiamo colori delle regole negli stati
			if(!rgbColors.contains(r.getNewColor().getRGB())) //nel then
				errors.add(STATE_RULE_CNF);
			for(Color c : r.getRoot().getColors())
				if(!rgbColors.contains(c.getRGB())) { //nelle espressioni
					errors.add(STATE_RULE_CNF);
					goOn = false;
					break;
				}
			if(!goOn) break;
		}
		
		for(int i=1; i<=graph.getNumNodes(); i++) //controlla stati del grafo con stati disponibili
			if(!rgbColors.contains(graph.getCell(i).getState().getRGB())) {
				errors.add(STATE_INITCONF_CNF);
				break;
			}
		
		String gcnf = getGraphConfConflicts(); //conflitti grafo configurazione
		if(gcnf != null)
			errors.add(gcnf);
		return errors;
	}
}
