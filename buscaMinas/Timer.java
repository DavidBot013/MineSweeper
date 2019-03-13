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

public class Timer extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private int WIDTH = 30;
	private int HEIGHT = 12;
	private String timeString = "00:00:00";
	private long time;
	private Thread hilo1;
	
	public Timer() {
		time=0;
	}
	
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
	
	public Dimension getPreferredSize() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		return size;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawString(timeString, 0, HEIGHT);
	}
	
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
	
	public String timeFormat(long time) {
		long h = time/3600;
		long m = (time-h*3600)/60;
		long s = time - h*3600 - m*60;
		
		timeString = String.format("%02d:%02d:%02d", h, m, s);
		return timeString;
	}
	
	public void start() {
		stop();
		hilo1 = new Thread(this);
		hilo1.start();
	}
	
	public void stop() {
		if(hilo1!=null) {
			hilo1.interrupt();
			hilo1 = null;
		}
	}
	
	public long getTime() {
		return time;
	}
	
	public String getDate() {
		LocalDateTime ldt = LocalDateTime.now().plusDays(1);
		DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		
		String fecha = formato1.format(ldt);
		return fecha;
	}

}
