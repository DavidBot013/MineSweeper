package buscaMinas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JPanel;

/**
 * Clase Timer implementa el cronometro que se usa para calcular el tiempo de un
 * jugador. Hereda de JPanel e implementa la interfaz Runnable.
 * 
 *
 */
public class Timer extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private int WIDTH = 30;
	private int HEIGHT = 12;
	private String timeString = "00:00:00";
	private long time;
	private Thread hilo1;
	
	/**
	 * El tiempo inicia en cero.
	 */
	public Timer() {
		time=0;
	}
	
	/**
	 * Hilo que se encarga de ir aumentando el atributo time de uno en uno
	 * cada segundo y decirle a setTime() que lo organice en un formato más fácil
	 * de leer (hh:mm:ss).
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(time>=0) {
			time+=1;
			setTime(time);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return;
			}
		}
	}
	
	/**
	 * Determina el tamaño del panel.
	 */
	public Dimension getPreferredSize() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		return size;
	}
	/**
	 * Función que se encarga de pintar el cronometro.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawString(timeString, 0, HEIGHT);
	}
	
	/**
	 * Convierte el parametro time en un formato más entendible y fuente agradable.
	 * Luego llama al metodo repaint() para que lo pinte.
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
		long h = time/3600;
		long m = (time-h*3600)/60;
		long s = time - h*3600 - m*60;
		
		timeString = String.format("%02d:%02d:%02d", h, m, s);
		Font fuente = new Font("Dialog", Font.BOLD, 12); 
		setFont(fuente);
		HEIGHT=fuente.getSize();
		FontMetrics fm = getFontMetrics(fuente);
		WIDTH = fm.stringWidth(timeString);
		
		repaint();
	}
	
	/**
	 * Devuelve el tiempo en formato más entendible. Hace lo mismo que la primera parte
	 * de setTime().
	 * @param time
	 * @return
	 */
	public String timeFormat(long time) {
		long h = time/3600;
		long m = (time-h*3600)/60;
		long s = time - h*3600 - m*60;
		
		timeString = String.format("%02d:%02d:%02d", h, m, s);
		return timeString;
	}
	
	/**
	 * Inicia el hilo 
	 */
	public void start() {
		stop();
		hilo1 = new Thread(this);
		hilo1.start();
	}
	
	/**
	 * Detiene el hilo luego de cerciorarse de que exista.
	 */
	public void stop() {
		if(hilo1!=null) {
			hilo1.interrupt();
			hilo1 = null;
		}
	}
	
	/**
	 * Retorna el tiempo en segundos.
	 * @return time
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * Retorna la fecha actual del sistema en formato yyyy-MM-dd.
	 * @return
	 */
	public String getDate() {
		LocalDateTime ldt = LocalDateTime.now().plusDays(1);
		DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		
		String fecha = formato1.format(ldt);
		return fecha;
	}

}
