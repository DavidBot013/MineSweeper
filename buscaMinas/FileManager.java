package buscaMinas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private FileReader fileRead;
	private BufferedReader input;
	private FileWriter fileWrite;
	private BufferedWriter output;
	
	/*
	 * private String pasarMayusculas(String texto){ return texto.toUpperCase();
	 * 
	 * }
	 */
	
	public void gestionarTextFile(String s) {
		
		try {
			//fileRead = new FileReader("src/files/original.txt");
			//input = new BufferedReader(fileRead);
			
			fileWrite = new FileWriter("src/files/top5.txt",true);
			output = new BufferedWriter(fileWrite);
			
			String texto = s;
			
			while(texto!=null){
			     //String nuevoTexto = pasarMayusculas(texto);
			     output.write(texto);
			     output.newLine();
			     texto = input.readLine();
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
}
