package grid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import grid.hexagon.HexGraph;
import grid.square.MatrixGraph;
import grid.triangle.TriangularGraph;

/**grafo rappresentare una griglia*/
public class Graph {

    /**lista dei nodi del grafo*/
    protected ArrayList<Cell> nodes;

    public Graph() {
        nodes = new ArrayList<>();
    }

    /**aggiunge cella alla lista*/
    public void addCell(Cell c) {
        nodes.add(c);
    }

    /**restituisce una cella (indicizzate da 1 a N)*/
    public Cell getCell(int cell) {
        if(cell <= 0 || cell > nodes.size()) return null;
        return nodes.get(cell-1);
    }
    
    /**stampa grafo sulla graphics passata (con eventuale colore del bordo celle, null per non avere bordo)*/
    public void drawGraph(Graphics g, Color borderColor) {
    	for(Cell c : nodes)
    		c.render(g, borderColor);
    }
    
    /**restituisce indice cella alle coordinate specificate (si assume che il grafo abbia una rappresentazione grafica definita); -1 se non esiste*/
    public int getCellAtCoordinate(int x, int y) {
    	return -1;
    }
    
    /**numero nodi nel grafo*/
    public int getNumNodes() {
    	return nodes.size();
    }
    
    public ArrayList<Cell> getNodes() {
    	return nodes;
    }
    
    /**fa una copia del grafo*/
    public Graph copy() {
    	Graph g = null;
    	int w = 0, h = 0, s = 0;
    	if(this instanceof MatrixGraph) { //trova larghezza altezza e lato
    		w = ((MatrixGraph)this).getWidth();
    		h = ((MatrixGraph)this).getHeight();
    		s = ((MatrixGraph)this).getSize();
    	}
    	
    	if(this instanceof TriangularGraph) //crea il grafo giusto
    		g = new TriangularGraph(w, h, s);
    	else
    		if(this instanceof HexGraph)
    			g = new HexGraph(w, h, s);
    		else
    			if(this instanceof MatrixGraph)
    				g = new MatrixGraph(w, h, s);
    	
    	for(int i=1; i<=this.getNumNodes(); i++) //rimetti apposto gli stati
    		g.getCell(i).setState(this.getCell(i).getState());
    	return g;
    }
    
    /**restituisce gli stati di tutte le celle nel grafo (in ordine: � una lista)*/
    public ArrayList<Integer> getArrayOfStates() {
    	ArrayList<Integer> colors = new ArrayList<>();
    	for(int i=1; i<=this.getNumNodes(); i++)
    		colors.add(this.getCell(i).getState().getRGB());
    	return colors;
    }
    
    /**crea un grafo prendendo l'array degli stati e la configurazione della griglia*/
    public static Graph fromGridConfAndStates(ArrayList<Integer> states, GridConfiguration gridConf) {
    	Graph g = buildGraph(gridConf, Color.BLACK);
    	for(int i=1; i<=g.getNumNodes(); i++)
    		if(states.size() >= i)
    			g.getCell(i).setState(new Color(states.get(i-1)));
    	return g;
    }
    
    /**costruisce grafo in base alla configurazione della griglia (e con stato di default)*/
    public static Graph buildGraph(GridConfiguration gconf, Color defaultState) {
    	Graph graph = null;
    	int cx = gconf.getNumCellsX(), cy = gconf.getNumCellsY(), len = gconf.getLen();
    	switch(gconf.getForm()) {
    		case TRIANGLE: graph = new TriangularGraph(cx, cy, len); break;
    		case SQUARE: graph = new MatrixGraph(cx, cy, len); break;
    		case HEXAGON: graph = new HexGraph(cx, cy, len); break;
    	}
    	for(int i=1; i<=graph.getNumNodes(); i++)
    		graph.getCell(i).setState(defaultState);
    	return graph;
    }
}
