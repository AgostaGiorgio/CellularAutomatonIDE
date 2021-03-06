package rules;

import grid.Graph;
import main_frame.rules_creator.EditExpressionPanel;
import util.StaticUtil;

import java.awt.Color;
import java.util.HashSet;

/**
 * nodo di un'espressione di base di tipo2, cioe':
 * se il 'k'-esimo vicino e' di colore 'c', allora...
 * NB: assumiamo che il vicino 0 sia la cella stessa, quindi gli altri vicini sono indicizzati da 1 a N
 * */
public class BaseExpressionNode2 extends ExpressionNode {

    protected int indChild;
    protected Color colr;

    /**
     * parametri: 'k'-esimo vicino e colore 'c'
     */
    public BaseExpressionNode2(int k, Color c) {
    	type = TypeNode.BE2;
        indChild = k;
        colr = c;
    }

    @Override
    public boolean evaluate(Graph graph, int cell) {
        if(graph.getCell(cell) == null) return false; //fai check sugli indici
        if(indChild < 0 || indChild > graph.getCell(cell).getNumNeighbors()) return false; //non ha abbastanza vicini

        int indn = graph.getCell(cell).getKthNeighbor(indChild);//controlla condizione dell'espressione
        if(indn < 1) return false;
        return graph.getCell(indn).getState().equals(colr);
    }
    
    @Override
    protected String toHtmlStringRec() {
    	String html = StaticUtil.getHtmlColorSpan(colr);
    	return EditExpressionPanel.TYPE_B[0] + indChild + EditExpressionPanel.TYPE_B[1] + html;
    	//return "Il " + indChild + "� vicino � di colore " + html;
    }
    
    @Override
    public void getColors(HashSet<Color> colors) {
    	super.getColors(colors);
    	colors.add(colr);
    }
    
    @Override
    public ExpressionNode copy() {
    	return new BaseExpressionNode2(indChild, colr);
    }
    
    @Override
    public String getAttribute() { 
    	return this.type + " " + this.indChild + " " + this.colr.getRGB() + " "; 	
    }
}