package main_frame.grid_initializer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.FlavorMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.VoiceStatus;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;



import grid.*;
import util.colors.ColorSelector;
import util.colors.CustomColorPicker;
import util.colors.CustomPalette;

/**
 * Inizializzatore del pannello di simulazione.
 * Implementa la griglia di simulazione e permette di cambiare i colori
 * delle celle.
 */
public class GridInitializerPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int currentCursor;
	private JButton btnHand, btnColor;
	private boolean isGridDraggable = true;
	private boolean areCellsColorable = false;

	/**
	 * Carica il pannello con la griglia e i pulsanti necessari.
	 */
	public GridInitializerPanel(Graph graph, GridConfiguration gridConfiguration, ArrayList<Color> colors) {
		
		
		// Pannello esterno
		GridRenderPanel grid = new GridRenderPanel(graph, gridConfiguration, Color.BLACK) {

			@Override
			protected void mouseDraggedCallback(MouseEvent evt) {
				if (isGridDraggable)
				super.mouseDraggedCallback(evt);
				if (areCellsColorable)
					mousePressedCallback(evt);
			}

			@Override
			protected void mousePressedCallback(MouseEvent evt) {
				super.mousePressedCallback(evt);
				if (areCellsColorable) {
					int c = getCellAtCoordinate(evt.getX(), evt.getY());
					if(c != -1) {
						graph.getCell(c).setState(new Color(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255)));
						ArrayList<Integer> al = new ArrayList<>();
						al.add(c);
						this.synchWithGraph(al);
			}
				}}
			
			
		};
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		add(grid);
		changeCursor(-1);
		currentCursor = -1;
		
		// Barra laterale
		JPanel sideBar = new JPanel();
		GridLayout gridLayout = new GridLayout(7, 1);
		sideBar.setLayout(gridLayout);
		add(sideBar, BorderLayout.WEST);
		
		// Inizializzazione pulsanti
		btnHand = new JButton(new ImageIcon(GridInitializerPanel.class.getResource("res/hand_open.png")));
		btnColor = new JButton(new ImageIcon(GridInitializerPanel.class.getResource("res/bucket.png")));

		// Mouse listener
		setMouseListener(sideBar, 0);
		setMouseListener(btnHand, 0);
		setMouseListener(btnColor, 0);
		setMouseListener(grid, -1);
		
		// Pulsante mano
		
		btnHand.setSize(40, 40);
		btnHand.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCursor(-1);
				currentCursor = -1;
				setMouseListener(grid, -1);
				isGridDraggable = true;
				areCellsColorable = false;
			}
		});
		sideBar.add(btnHand);
		
		// Pulsante colore
		
		btnColor.setSize(40, 40);
		btnColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCursor(-3);
				currentCursor = -3;
				
				setMouseListener(grid, currentCursor);
				isGridDraggable = false;
				areCellsColorable = true;
				
			}
		});	
		sideBar.add(btnColor);
					
	}
	
	/**
	 * Imposta il listener del mouse.
	 * @param object
	 * @param evt
	 */
	private void setMouseListener(JComponent jComp, int cursorInt) {
		jComp.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent evt) {
				if (cursorInt == 0)
		        changeCursor(Cursor.DEFAULT_CURSOR);
		    }

		    public void mouseExited(MouseEvent evt) {
		    	if (cursorInt == 0)
		        changeCursor(currentCursor);
		    }
		    
		    public void mousePressed(MouseEvent evt) {
		    	if (cursorInt == -1) 
		    	changeCursor(-2);
		    	else changeCursor(currentCursor);
		    	
		    }
		    
		    public void mouseReleased(MouseEvent evt) {
		    	if (cursorInt == -1)
		    	changeCursor(currentCursor);
		    	else changeCursor(currentCursor);
		    }
		
		});
	}
	
	/**
	 * Imposta l'icona del cursore.
	 * @param cursor
	 */
	private void changeCursor(int cursor) {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image;
		Cursor c;
		
		switch (cursor) {
		
		// Cursore mano aperta
		case -1: 
			image = toolkit.getImage(GridInitializerPanel.class.getResource("res/hand_open_small.png"));
			c = toolkit.createCustomCursor(image , new Point(getX(), 
			           getY()), "img");
			setCursor(c);
			break;
			
		// Cursore mano chiusa
		case -2: 
			image = toolkit.getImage(GridInitializerPanel.class.getResource("res/hand_closed_small.png"));
			c = toolkit.createCustomCursor(image , new Point(getX(), 
			           getY()), "img");
			setCursor(c);
			break;
			
		// Cursore colore
		case -3:
			image = toolkit.getImage(GridInitializerPanel.class.getResource("res/bucket_small.png"));
			c = toolkit.createCustomCursor(image , new Point(getX(), 
			           getY()), "img");
			setCursor(c);
			break;
			
		// Cursore default
		default:
			setCursor(new Cursor(cursor));
			break;
		}
	}

}
