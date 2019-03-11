package buscaMinas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	private FileReader fileRead, fileTiempos;
	private BufferedReader input, inputTiempos;
	private FileWriter fileWrite, fileWriteTiempos;
	private BufferedWriter output, outputTiempos;
	private List<String> listaTiempos, listaJugadores;
	
	
	public FileManager() {
		try {
			fileRead = new FileReader("src/resources/top5.txt");
			input = new BufferedReader(fileRead);
			fileWrite = new FileWriter("src/resources/top5.txt",true);
			output = new BufferedWriter(fileWrite);
			
			fileTiempos = new FileReader("src/resources/tiempos.txt");
			inputTiempos = new BufferedReader(fileTiempos);
			fileWriteTiempos = new FileWriter("src/resources/tiempos.txt",true);
			outputTiempos = new BufferedWriter(fileWriteTiempos);
			
			listaTiempos = new ArrayList<String>();
			listaJugadores = new ArrayList<String>();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Escribe al archivo tiempos.txt el recurso timer.getTime(), sin formato 
	 * para poder comparar más facilmente en el caso que un jugador haga un tiempo
	 * que merezca estar en el top 5.
	 * @param time
	 */
	public void onlyTime(String time) {
		try {			
			String linea = inputTiempos.readLine();
			String texto = time;
			
			while(linea!=null){
				outputTiempos.write(texto);
			    outputTiempos.newLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			   try {
				inputTiempos.close();
				outputTiempos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	}
	
	/**
	 * Guarda en el archivo top5.txt el nombre, tiempo logrado y fecha de cada jugador.
	 * @param s
	 */
	public void gestionarTextFile(int posicion, String s) {		
		try {
			Path path = Paths.get("src/resources/top5.txt");
			listaJugadores = Files.readAllLines(path);
			
			if(listaJugadores.isEmpty() || listaJugadores.size()<5) {
				listaJugadores.add(s);
			}
			else {
				listaJugadores.set(posicion, s);
				Files.write(path, listaJugadores);
			}
			//String texto = s;
			//String linea = input.readLine();
			
			
			
//			while(linea!=null){
//			     output.write(texto);
//			     output.newLine();
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   finally{
		   try {
			input.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	}
	/**
	 * 
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
	 * Reescribe el archivo tiempos.txt, convel cambio en la linea especificada
	 * Esta función se llama cada que haya un tiempo merecedor de estar en el top 5.
	 * @param linea
	 * @param newTime
	 */
	public void modTiempos(int linea, long newTime) {
		Path path = Paths.get("src/resources/tiempos.txt");
		
		if(listaTiempos.isEmpty() || listaTiempos.size()<5) {
			listaTiempos.add(String.valueOf(newTime));
		}
		else {
			listaTiempos.set(linea, String.valueOf(newTime));
		}
		
		try {
			Files.write(path, listaTiempos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getTiempos() {
		setTiempos();
		return listaTiempos;
	}
}


