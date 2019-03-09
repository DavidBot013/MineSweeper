package buscaMinas;

import java.awt.Dimension;

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
	 * 
	 */
	public void revelar() {
		
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
