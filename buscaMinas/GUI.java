package buscaMinas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private Container mainContenedor;
	private JPanel mainPanel, panelCuadricula, topPanel;
	private JLabel etiquetaTitulo, flagLabel;
	private JButton resetButton;
	private EscuchaMouse escuchaM = new EscuchaMouse();
	private Control control;
	
	public GUI() {
		control = new Control();
		this.setTitle("MineSweeper");
		this.setResizable(false);
		initGUI();
	}
	
	private void initGUI() {
		mainContenedor=this.getContentPane();
		mainContenedor.setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		crearCuadricula();
		topMenu();
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(panelCuadricula, BorderLayout.CENTER);
		mainContenedor.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void crearCuadricula() {
		panelCuadricula = new JPanel();
		int rows = Control.getBoard();
		int cols = Control.getBoard();
		panelCuadricula.setLayout(new GridLayout(rows, cols));
		
		/**
		 * A cada celda se le añade un escucha.
		 */
		for(int fila=0; fila<rows; fila++) {
			for(int col=0; col<cols; col++) {
				control.getCeldas()[fila][col].addMouseListener(escuchaM);
				panelCuadricula.add(control.getCeldas()[fila][col]);
			}
		}
	}
	
	private void topMenu() {
		topPanel = new JPanel();
		
		resetButton = new JButton();
		resetButton.setIcon(new ImageIcon("src/resources/reset.png"));
		
		flagLabel = new JLabel();
		flagLabel.setIcon(new ImageIcon("src/resources/flag.png"));
		
		topPanel.add(resetButton);
		topPanel.add(flagLabel);
	}
	private class EscuchaMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			manejaEvento(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void manejaEvento(MouseEvent e) {
		String fuente = e.getSource().getClass().getName();
		
		if(fuente.equals("buscaMinas.Celda")) {
			Celda celdaSeleccionada = (Celda)e.getSource();
			control.checkCelda(celdaSeleccionada.getFila(), celdaSeleccionada.getCol());
			if(control.isGameOver()) {
				gameEnded();
			}
		}
	}
	
	private void gameEnded() {
		for(int fila=0; fila < Control.getBoard(); fila++) {
			for(int col=0; col < Control.getBoard(); col++) {
				if(control.getCeldas()[fila][col].isMina()) {
					control.getCeldas()[fila][col].revelar();
				}
			}
		}
	}
}
