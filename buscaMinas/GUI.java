package buscaMinas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private Container mainContenedor;
	private JPanel mainPanel, panelCuadricula, bottomPanel;
	private JLabel flagLabel, numFlags;
	private JButton resetButton;
	private EscuchaMouse escuchaM = new EscuchaMouse();
	private Control control;
	private Timer timer;
	
	public GUI() {
		control = new Control();
		timer = new Timer();
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
		bottomMenu();
		
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.add(panelCuadricula, BorderLayout.CENTER);
		mainPanel.add(timer, BorderLayout.NORTH);
		mainContenedor.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		timer.start();
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
	
	private void bottomMenu() {
		bottomPanel = new JPanel();
		
		resetButton = new JButton();
		resetButton.setIcon(new ImageIcon("src/resources/reset.png"));
		resetButton.setBackground(Color.GRAY);
		resetButton.addMouseListener(escuchaM);
		flagLabel = new JLabel();
		flagLabel.setIcon(new ImageIcon("src/resources/flag.png"));
		numFlags=new JLabel(Integer.toString(control.getFlags()));
		
		bottomPanel.add(resetButton);
		bottomPanel.add(flagLabel);
		bottomPanel.add(numFlags);
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
			if(e.getButton()==3) {
				if(celdaSeleccionada.isFlagged()) {
					control.unlockCell(celdaSeleccionada.getFila(), celdaSeleccionada.getCol());
				}
				else {
					control.lockCell(celdaSeleccionada.getFila(), celdaSeleccionada.getCol());
				}
				numFlags.setText(Integer.toString(control.getFlags()));
				System.out.println(control.getFlags());
			}
			else {
				if(!celdaSeleccionada.isFlagged()) {
					control.checkCelda(celdaSeleccionada.getFila(), celdaSeleccionada.getCol());
					if(control.isGameOver()) {
						timer.stop();
						revelarMinas();
						endMessage(fuente);
					}
					else {
						if(control.isGanador()) {
							timer.stop();
							JOptionPane.showMessageDialog(mainContenedor, "¡Felicidades, has ganado!");
						}
					}
				}
			}
		}
		else if(e.getSource()==resetButton){
			timer.setTime(0);
			taparCeldas();
			control.reset();
			numFlags.setText(Integer.toString(control.getFlags()));
		}
	}
	
	private void revelarMinas() {
		for(int fila=0; fila < Control.getBoard(); fila++) {
			for(int col=0; col < Control.getBoard(); col++) {
				if(control.getCeldas()[fila][col].isMina()) {
					control.getCeldas()[fila][col].revelar();
				}
			}
		}
	}
	
	private void endMessage(String s) {
		int option = JOptionPane.showConfirmDialog(mainContenedor, 
				"Has perdido. ¿Deseas jugar otra vez?", s, JOptionPane.YES_NO_OPTION);
	
		
		System.out.println(option);
		if(option==0) {
			taparCeldas();
			control.reset();
			numFlags.setText(Integer.toString(control.getFlags()));
			timer.setTime(0);
			timer.start();
		}
		else {
			System.exit(0);
		}
	}
	
	private void taparCeldas() {
		for(int fila=0; fila < Control.getBoard(); fila++) {
			for(int col=0; col < Control.getBoard(); col++) {
				control.getCeldas()[fila][col].setIcon(new ImageIcon("src/resources/10.png"));
			}
		}
	}
}

