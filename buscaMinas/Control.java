package buscaMinas;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Maneja toda la lógica del buscaminas.
 *
 */
public class Control {
	private Celda[][] celdas;
	private boolean ganador, gameOver;
	private static final int MINAS = 40;
	private int banderas, celdasTapadas;
	private static final int BOARD = 16;
	private Random aleatorio;
	
	public Control() {
		celdas = new Celda[BOARD][BOARD];
		ganador=false;
		gameOver=false;
		celdasTapadas=BOARD*BOARD;
		banderas=MINAS;
		aleatorio = new Random();
		instanciarCeldas();
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
		setMinas();
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
	/**
	 * Esta función solo es llamada por setMinas(). Lo que hace es decirle a todas las casillas circundantes
	 * que están cerca de una mina.
	 * @param fila
	 * @param col
	 */
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
	
	/**
	 * Función auxiliar de aumentarMinas()
	 * @param fila
	 * @param col
	 */
	private void aumentarContadorMinas(int fila, int col) {
		if(fila>=0 && fila<=BOARD-1 && col>=0 && col<=BOARD-1) {
			celdas[fila][col].increaseMineCount();
		}
	}
	
	/**
	 * Chequea si una celda dada es mina o no.
	 * Si no es una mina se necesita destaparla y saber si podemos propagarnos y destapar más
	 * celdas en caso de que hayan 0 minas cercanas.
	 * @param fila
	 * @param col
	 */
	public void checkCelda(int fila, int col) {
		if(celdas[fila][col].isMina()) {
			celdas[fila][col].revelar();
			gameOver=true;
			celdasTapadas--;
		}
		else {
			continuar(fila, col);
		}
	}
	
	/**
	 * Destapa la celda seleccionada y en caso de que hayan cero minas cercanas, se propaga hacia sus vecinos.
	 * @param fila
	 * @param col
	 */
	private void continuar(int fila, int col) {		
		if(fila>=0 && fila<=BOARD-1 && col>=0 && col<=BOARD-1
				&& !celdas[fila][col].isMina() && celdas[fila][col].getEstado()==0) {
				celdas[fila][col].revelar();
				celdasTapadas--;
				if(celdas[fila][col].getMinasCercanas()==0) {
					checkVecinos(fila,col);
				}
			}
	}
	
	/**
	 * Cheque los ocho vecinos de la celda seleccionada, apoyandose en la función continuar();
	 * @param fila
	 * @param col
	 */
	private void checkVecinos(int fila, int col) {
		continuar(fila-1,col);
		continuar(fila+1,col);
		continuar(fila,col-1);
		continuar(fila,col+1);
		continuar(fila-1,col+1);
		continuar(fila-1,col-1);
		continuar(fila+1,col-1);
		continuar(fila+1,col+1);
	}
	/**
	 * Lo que sucede a nivel lógico cuando el jugador ponga una banderita sobre la celda.
	 * Dicha celda queda en un estado bloqueado o 'flagged'
	 * @param fila
	 * @param col
	 */
	public void lockCell(int fila, int col) {
		if(celdas[fila][col].getEstado()==0) {
			celdas[fila][col].flagCell(true);
			banderas--;
		}
	}
	/**
	 * Cuando el jugador quiera quitarle la bandera, Control le dice a la celda que cambie su estado
	 * flagged a false.
	 * @param fila
	 * @param col
	 */
	public void unlockCell(int fila, int col) {
		celdas[fila][col].flagCell(false);
		banderas++;
	}
	
	/**
	 * Reinicia la partida.
	 */
	public void reset() {
		gameOver=false;
		celdasTapadas=BOARD*BOARD;
		banderas=MINAS;
		for(int fila=0; fila<BOARD; fila++) {
			for(int col=0; col<BOARD; col++) {
				celdas[fila][col].reset();
			}
		}
		setMinas();
	}
	
	/**
	 * Determina si se ha logrado un buen tiempo. Se utiliza un objeto Iterator<E>
	 * para hacer más comodo el recorrer la lista tiempos.
	 * 
	 * Si en efecto se logró un buen tiempo la función retornará a que posicion pertenece
	 * en caso contrario retorna -1.
	 * @param newTime
	 * @param tiempos
	 * @return
	 */
	public int compararTiempos(long newTime, List<String> tiempos) {
		int contador=0;
		Iterator<String> it = tiempos.iterator();
		
		/** Si tiempos está vacía, quiere decir que aún no hay ningún record
		 * por lo tanto simplemente se añade el tiempo logrado.
		 */
		if(!tiempos.isEmpty()) {
			while(it.hasNext()) {
				long oldTime = Long.parseLong(it.next());
				System.out.println("old "+oldTime);
				if(newTime<oldTime) {
					return contador;
				}
				contador++;
			}// fin ciclo while
			
			/** Si la lista aún no está llena...**/
			if(tiempos.size()<5) {
				return tiempos.size();
			}
			else {
				return -1;
			}
		}
		else {
			return 0;
		}
  }
		
	public Celda[][] getCeldas() {
		return celdas;
	}

	/**
	 * Determina si se ha ganado el juego. Esto sucede cuando el número de
	 * celdas tapadas es igual al número de minas.
	 * 
	 * Por la mera naturaleza del juego es imposible que se haya llegado a 40 celdas
	 * tapadas y que alguna de ellas no sea una mina. 
	 * 
	 * (Celdas tapadas incluye las celdas marcadas con bandera).
	 * @return
	 */
	public boolean isGanador() {
		if(celdasTapadas==MINAS) {
			ganador=true;
		}
		else {
			ganador=false;
		}
		return ganador;
	}

	public static int getMinas() {
		return MINAS;
	}

	public static int getBoard() {
		return BOARD;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public int getFlags() {
		return banderas;
	}
}
