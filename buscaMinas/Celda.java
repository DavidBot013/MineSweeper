package buscaMinas;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Celda extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 15;
	private int minasCercanas, fila, col, estado;
	private boolean esMina;
	private String color;
	
	public Celda(int fila, int col, boolean esMina) {
		this.fila=fila;
		this.col=col;
		this.esMina=esMina;
		estado=0;
		this.setPreferredSize(new Dimension(SIZE, SIZE));
		this.setOpaque(true);
		this.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * Cambia el estado de la celda. Los estados son:
	 * 0 -> Estado inicial (tapada);
	 * 1 -> Destapada
	 * 2 -> Marcada
	 * @param action
	 */
	public void setState(int estado) {
		this.estado=estado;
	}
	
	/**
	 * "Destapa" la celda seleccionada. En realidad lo que sucede es que se le asigna
	 * un icono al botón dependiendo del número de minas cercanas.
	 */
	public void revelar() {
		if(estado==0) {
			this.setIcon(new ImageIcon("src/resources/"+minasCercanas+".png"));
		}
	}
	public int getMinasCercanas() {
		return minasCercanas;
	}

	public int getFila() {
		return fila;
	}

	public int getCol() {
		return col;
	}

	public int getEstado() {
		return estado;
	}

	public boolean isMina() {
		return esMina;
	}

	public String getColor() {
		return color;
	}	
}
