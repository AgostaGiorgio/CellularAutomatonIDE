package RulesCreatorWindow;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditExpressionPanel extends JPanel {

	/* Componente che mostra la frase della condizione e i campi da compilare*/
	
	private static final long serialVersionUID = 1L;
	static private final String[] TYPE_A = {"Il numero dei vicini di colore ", " � compreso tra ", " e "};
	static private final String[] TYPE_B = {"Il ","� vicino � di colore "};
	static private final String[] TYPE_THEN = {"La cella assume il colore "};
	
	static final private int height = 10;
	private JTextField colore;
	private JTextField start;
	private JTextField end;
	private JTextField n_vicini;
	private String Type = null;
	
	
	public EditExpressionPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	public EditExpressionPanel formatA() {
		Type = "A";
		colore = new JTextField();
		start = new JTextField();
		end = new JTextField();
		add(new JLabel(TYPE_A[0]));
		add(colore);
		add(new JLabel(TYPE_A[1]));
		add(start);
		add(new JLabel(TYPE_A[2]));
		add(end);
		return this;
	}
	
	public EditExpressionPanel formatB() {
		Type = "B";
		n_vicini = new JTextField();
		colore = new JTextField();
		add(new JLabel(TYPE_B[0]));
		add(n_vicini);
		add(new JLabel(TYPE_B[1]));
		add(colore);
		return this;
	}
	
	public EditExpressionPanel formatThen() {
		Type = "THEN";
		colore = new JTextField();
		add(new JLabel(TYPE_THEN[0]));
		add(colore);
		return this;	
	}
	
	public String create_String() {
		if (Type.equals("A")) return TYPE_A[0]+colore.getText()+TYPE_A[1]+start.getText()+TYPE_A[2]+end.getText();
		else if (Type.equals("THEN")) return TYPE_THEN[0]+colore.getText();
		return TYPE_B[0]+n_vicini.getText()+TYPE_B[1]+colore.getText();
	}
	
	public String getType() {return Type;}

}
