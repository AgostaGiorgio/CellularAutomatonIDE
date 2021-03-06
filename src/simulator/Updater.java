package simulator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import grid.Graph;
import rules.Rule;

/**classe che e' in grado di eseguire step nella simulazione*/
public class Updater {
	
	public Graph graph;
	protected ArrayList<Rule> rules;
	protected HashMap<Color,Integer> frequencyMap; //map contenente le frequenze dei colori
	
	/**set delle celle che ha senso aggiornare (evitiamo di scorrere tutto il grafo) */
	protected HashSet<Integer> toUpdate;
	
	/**map usata per operazioni temporanee durante l'update*/
	protected HashMap<Integer, Color> tmpMapUpd;
	
	protected int actualIteration;
	
	/**serve griglia della simulazione (gia' inizializzata) e regole*/
	public Updater(Graph graph, ArrayList<Rule> rules) {
		this.graph = graph;		
		this.rules = rules;
		

		toUpdate = new HashSet<Integer>();
		for(int i=1; i<=this.graph.getNumNodes(); i++)
			toUpdate.add(i);
		tmpMapUpd = new HashMap<Integer, Color>();
		
		actualIteration = 0;
	}
	
	public Updater(Graph graph, ArrayList<Rule> rules, List<Color> States) {
		this(graph,rules);
		
		//inizializzo la mappa delle frequenze
		this.frequencyMap = new HashMap<>();
		for (Color state : States) 
			frequencyMap.put(state, 0);
		for(int i=1; i<=graph.getNumNodes(); i++) {
			Color c = graph.getCell(i).getState();
			frequencyMap.put(c,  frequencyMap.get(c)+1);
		}
	}
	
	/**restituisce iterazione attuale (o quante ne sono state effettuate)*/
	public int getActualIteration() {
		return actualIteration;
	}
	
	/**esegue uno step della simulazione (restituisce lista delle celle modificate)*/
	public Set<Integer> execStep() {
		actualIteration++;
		tmpMapUpd.clear();

		for(int cell : toUpdate) { //scorro le celle candidate all'update
			for(Rule r : rules) //scorro regole 
				if(r.getRoot().evaluate(graph, cell)) { //regola verificata per questa cella
					if(!graph.getCell(cell).getState().equals(r.getNewColor())) //controllo che la cella non sia gia' nello stato futuro (e' inutile aggiungerla)
						tmpMapUpd.put(cell, r.getNewColor()); //mi salvo nella map che la cella verifica la regola e che devo cambiargli colore
			 		break; //NB: appena verifico una regola esco
				}
		}
		
		toUpdate.clear();
		for(Entry<Integer, Color> e : tmpMapUpd.entrySet()) { //scorro le celle a cui devo cambiare colore
			updColorSwapped(graph.getCell(e.getKey()).getState(), e.getValue()); //aggiorno le frequenza nella frequency map
			
			graph.getCell(e.getKey()).setState(e.getValue());
			toUpdate.add(e.getKey()); //aggiungo le celle aggiornate e i loro vicini alle possibili celle da aggiornare
			
			for(int i=1; i<=graph.getCell(e.getKey()).getNumNeighbors(); i++) { //scorro vicini della cella 
				int ng = graph.getCell(e.getKey()).getKthNeighbor(i);
				if(ng != -1) //controllo che il vicino esista
					toUpdate.add(ng); //lo aggiungo alle celle da aggiornare
			}
		}
		
		return tmpMapUpd.keySet();
	}
	
	public void addCellToUpdate(int cell) {
		toUpdate.add(cell);
		for(int i=1; i<=graph.getCell(cell).getNumNeighbors(); i++)
			toUpdate.add(graph.getCell(cell).getKthNeighbor(i));
	}
	
	/**da chiamare quando una cella passa da colore old a colore nw (per ricalibrare la map)... va chiamata solo se si modificano le celle dall'esterno (es: editor)*/
	public void updColorSwapped(Color old, Color nw) {
		if(frequencyMap != null) {
			frequencyMap.put(old, frequencyMap.get(old)-1); //diminuisco di 1 la frequenza del colore vecchio
			frequencyMap.put(nw, frequencyMap.get(nw)+1); //aumento di 1 la frequenza del colore nuovo
		}
	}
	
	public void setCellsToUpdate(HashSet<Integer> cells) {
		toUpdate.clear();
		for(int v : cells)
			toUpdate.add(v);
	}
	
	public HashMap<Color, Integer> getFrequencyMap() {
		return frequencyMap;
	}
	
}
