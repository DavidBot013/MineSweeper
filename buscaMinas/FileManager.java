package buscaMinas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase FileManager se encargará de manejar los archivos de texto,
 * leer y escribir sobre ellos. La mayoría de sus metodos son públicos ya que
 * es una clase que es utilizada por GUI.
 *
 */
public class FileManager {
	private List<String> listaTiempos, listaJugadores;
	
	/**
	 * Instancia dos Listas de tipo String y las pasa como ArrayLists.
	 * ListaTiempos almacena los 5 mejores tiempos (solo el tiempo).
	 * ListaJugadores almacena los records como tal (nombre, tiempo logrado y fecha).
	 */
	public FileManager() {
		listaTiempos = new ArrayList<String>();
		listaJugadores = new ArrayList<String>();
	}
	
	/**
	 * Modifica listaJugadores y luego reescribe todo el contenido
	 * de top5.txt con el contenido de listaJugadores.
	 * @param posicion, posicón donde añadir s dentro de listaJugadores.
	 * @param s, String nuevo que se le añade a listaJugadores.
	 */
	public void gestionarTextFile(int posicion, String s) {		
		try {
			Path path = Paths.get("src/resources/top5.txt");
			listaJugadores = Files.readAllLines(path);
			
			if(listaJugadores.isEmpty()) {
				listaJugadores.add(s);
			}
			else if(listaJugadores.size()<5) {
				listaJugadores.add(posicion, s);
			}
			else {
				listaJugadores.add(posicion, s);
				listaJugadores.remove(5);
			}
			
			Files.write(path, listaJugadores);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Llena listaTiempos con el contenido que se encuentre en ese momento dentro
	 * de tiempos.txt.
	 */
	private void setTiempos() {
		
		try {
			Path path = Paths.get("src/resources/tiempos.txt");
			listaTiempos = Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Reescribe el archivo tiempos.txt, con el cambio en la linea especificada
	 * Esta función se llama cada que haya un tiempo merecedor de estar en el top 5.
	 * @param linea, número de linea a ser modificada (se entiende como un indice dentro
	 * de listaTiempos.
	 * @param newTime, tiempo nuevo que hizo el jugador.
	 */
	public void modTiempos(int linea, long newTime) {
		Path path = Paths.get("src/resources/tiempos.txt");
		
		if(listaTiempos.isEmpty()) {
			listaTiempos.add(String.valueOf(newTime));
		}
		else if (listaTiempos.size()<5) {
			listaTiempos.add(linea, String.valueOf(newTime));
		}
		else {
			listaTiempos.add(linea, String.valueOf(newTime));
			listaTiempos.remove(5);
		}
		
		try {
			Files.write(path, listaTiempos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Devuelve listaTiempos
	 * @return
	 */
	public List<String> getTiempos() {
		setTiempos();
		return listaTiempos;
	}
}


