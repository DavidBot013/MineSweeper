package buscaMinas;

import java.util.Random;

public class Control {
	private Celda[][] celdas;
	private boolean ganador;
	private static final int MINAS = 40;
	private static final int BOARD = 16;
	private Random aleatorio;
	
	public Control() {
		celdas = new Celda[BOARD][BOARD];
		ganador=false;
		aleatorio = new Random();
		instanciarCeldas();
		setMinas();
	}
	/**
	 * Crea cada celda dentro del arreglo celdas[][] y los inicializa con unas
	 * coordenadas contenidas dentro de un cuadrado 16x16px
	 */
	private void instanciarCeldas() {
		for(int fila=0; fila<BOARD; fila++) {
			for(int col=0; col<BOARD; col++) {
				celdas[fila][col] = new Celda(fila, col);
			}
		}
	}
	
	/**
	 * Crea 40 minas en posiciones aleatorias. Para asegurarse que dichas posiciones
	 * no se repitan se utiliza la funcion isMina() de la clase Celda.
	 */
	private void setMinas() {
		int contador=0;
		
		while(contador <= MINAS) {
			int randomRow = aleatorio.nextInt(BOARD);
			int randomCol = aleatorio.nextInt(BOARD);
			
			if(celdas[randomRow][randomCol].isMina()==false) {
				celdas[randomRow][randomCol].beMine();
				contador++;
			}
		}
	}
	
	
	
	public Celda[][] getCeldas() {
		return celdas;
	}

	public boolean isGanador() {
		return ganador;
	}

	public static int getMinas() {
		return MINAS;
	}

	public static int getBoard() {
		return BOARD;
	}
}
