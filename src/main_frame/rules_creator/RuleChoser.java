package main_frame.rules_creator;


import rules.Rule;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.List;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class RuleChoser extends JPanel{
	
		private static final long serialVersionUID = 1L;
		
		ArrayList<Color> listColor;

		//lista degli alberi delle regole
		ArrayList<Rule> forest;
		
		// Contenitore di tutte le regole di transizione gia' create
		List list;
			
		public RuleChoser(ArrayList<Color> listcolor){
			
			listColor = listcolor;
			
			//lista degli alberi delle regole
			forest = new ArrayList<Rule>();
			
			// Creazione Layout
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{0, 0, 0};	//nColumns
			gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};	//nRows
			gridBagLayout.columnWeights = new double[]{0.5, 0.01, 0.2};	//dimColumns
			gridBagLayout.rowWeights = new double[]{1.0, 0.125, 0.125, 0.125};	//dimRows
			setLayout(gridBagLayout);
			
			// Contenitore di tutte le regole di transizione gia' create
			list= new List();
			list.setMultipleMode(true);
			
			// Pulsante che richiama la procedura che crea le regole di transizione
			JButton btnNew = new JButton("   New   ");
			btnNew.addActionListener(actionbtnNew);
			btnNew.setText("   New   ");

			// Pulsante che richiama la procedura che elimina le regole di transizione selezionate
			JButton btnRemove = new JButton("Remove");
			btnRemove.addActionListener(actionbtnRemove);		
			btnRemove.setText("Remove");
			
			// Aggiunta contenitore regole
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 0;
			gbc_list.gridy = 0;
			gbc_list.gridheight = 4;
			add(list, gbc_list);
			
			// Aggiunta pulsante New
			GridBagConstraints gbc_btnNew = new GridBagConstraints();
			gbc_btnNew.anchor = GridBagConstraints.WEST;
			gbc_btnNew.gridx = 2;
			gbc_btnNew.gridy = 2;
			add(btnNew, gbc_btnNew);
			
			// Aggiunta pulsante New
			GridBagConstraints gbc_btnRemove = new GridBagConstraints();
			gbc_btnRemove.anchor = GridBagConstraints.WEST;
			gbc_btnRemove.gridx = 2;
			gbc_btnRemove.gridy = 3;
			add(btnRemove, gbc_btnRemove);
			
		}
	
	// Apertura modulo per la creazione delle regole
	ActionListener actionbtnNew = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			java.util.List<Color> cl = new ArrayList<>();
			cl.add(Color.GREEN);
			cl.add(Color.BLACK);
			cl.add(Color.ORANGE);
			JFrame ruleCreator = new CreateExpressionWindow(100, 100, 450, 300, list, forest, cl);
			ruleCreator.setBounds(100, 100, 450, 300);
			ruleCreator.setVisible(true);
		}
	};
		
	// Rimozione delle regole selezionate
	ActionListener actionbtnRemove = new ActionListener() {	
		@Override
		public void actionPerformed(ActionEvent arg0) {	
			int [] inds = list.getSelectedIndexes();
			for(int i=inds.length-1; i>=0; i--) {
				list.remove(inds[i]);
				forest.remove(inds[i]);
			}
			// Dopo l'eliminaizione, seleziona tutte le righe restanti. In questo modo si deselezionano
			for (int i = 0; i < list.getItemCount(); i++) {
				list.deselect(i);
			}
		}
	};
	
	// Semplice funzione che restituisce l'insieme delle regole create sotto forma di albero
	public ArrayList<Rule> getRuleTrees() {
		return this.forest;
	}
}