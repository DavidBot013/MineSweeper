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
				aumentarMinas(randomRow, randomCol);
				contador++;
			}
		}
	}
	
	private void aumentarMinas(int fila, int col) {
		aumentarContadorMinas(fila-1,col);
		aumentarContadorMinas(fila+1,col);
		aumentarContadorMinas(fila,col-1);
		aumentarContadorMinas(fila,col+1);
		aumentarContadorMinas(fila-1,col+1);
		aumentarContadorMinas(fila-1,col-1);
		aumentarContadorMinas(fila+1,col-1);
		aumentarContadorMinas(fila+1,col+1);
	}
	
	private void aumentarContadorMinas(int fila, int col) {
		if(fila>=0 && fila<=BOARD-1 && col>=0 && col<=BOARD-1) {
			celdas[fila][col].increaseMineCount();
		}
	}
	public void nearMines(int fila, int col) {
		boolean inBounds = (fila>=0 && fila<=BOARD-1) && (col>=0 && col<=BOARD-1);
		boolean unknownMine = celdas[fila][col].getEstado()==0 && !celdas[fila][col].isMina();
		if (inBounds && unknownMine) {
			celdas[fila][col].revelar();
		}
	}
	/**
	 * Chequea si una celda dada es mina o no.
	 * @param celda
	 */
	public void checkCelda(Celda celda) {
		nearMines(celda);
		if(celda.isMina()) {
			
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
