package buscaMinas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	private FileReader fileRead, fileT;
	private BufferedReader input, inputT;
	private FileWriter fileWrite, fileWT;
	private BufferedWriter output, outputT;
	private List<String> listaTiempos, listaJugadores;
	
	
	public FileManager() {
		try {
			fileRead = new FileReader("src/resources/top5.txt");
			input = new BufferedReader(fileRead);
			fileWrite = new FileWriter("src/resources/top5.txt",true);
			output = new BufferedWriter(fileWrite);
			
			fileT = new FileReader("src/resources/tiempos.txt");
			inputT = new BufferedReader(fileT);
			fileWT = new FileWriter("src/resources/tiempos.txt",true);
			outputT = new BufferedWriter(fileWT);
			
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
			String linea = inputT.readLine();
			String texto = time;
			
			while(linea!=null){
				outputT.write(texto);
			    outputT.newLine();
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
				inputT.close();
				outputT.close();
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
	public void gestionarTextFile(String s) {		
		try {
			listaJugadores = Files.readAllLines((Path) input);
			String texto = s;
			String linea = input.readLine();
			
			while(linea!=null){
			     //String nuevoTexto = pasarMayusculas(texto);
			     output.write(texto);
			     output.newLine();
			     //texto = input.readLine();
			     //System.out.println(input.readLine());
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
			input.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	}

	private void setTiempos() {
		
		try {
			listaTiempos = Files.readAllLines((Path) inputT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modTiempos(int linea, long newTime) {
		listaTiempos.set(linea, String.valueOf(newTime));
		try {
			Files.write((Path) inputT, listaTiempos);
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


