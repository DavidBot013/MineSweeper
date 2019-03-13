package buscaMinas;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.net.URL;

/**
 * Maneja todos los elementos gráficos de buscaminas.
 *
 */
public class GUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Container mainContenedor;
	private JMenuBar BarraMenu;
	private JMenu menu;
	private JMenuItem Newgame, Score;
	private JPanel mainPanel, panelCuadricula, bottomPanel, MenuInf;
	private JLabel flagLabel, numFlags;
	private JButton resetButton;
	private EscuchaMouse escuchaM = new EscuchaMouse();
	private Control control;
	private Timer timer;
	private FileManager files;
	
	/**
	 * Constructor de la clase GUI
	 */
	public GUI() {
		control = new Control();
		timer = new Timer();
		files = new FileManager();
		BarraMenu = new JMenuBar();
		setJMenuBar(BarraMenu);
		menu = new JMenu("Opciones");
		BarraMenu.add(menu);
		Newgame = new JMenuItem("Nuevo Juego");
		Newgame.addActionListener(this);
		menu.add(Newgame);
		Score = new JMenuItem("Puntajes");
		Score.addActionListener(this);
		menu.add(Score);
		this.setTitle("MineSweeper");
		this.setResizable(false);
		initGUI();
	}
	
	/**
	 * Inicializa la ventana construyendo los elementos básicos (paneles, bottones, etc).
	 */
	private void initGUI() {
		mainContenedor=this.getContentPane();
		mainContenedor.setLayout(new BorderLayout());

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		crearCuadricula();
		bottomMenu();
		
		mainPanel.add(MenuInf, BorderLayout.SOUTH);
		mainPanel.add(panelCuadricula, BorderLayout.CENTER);
		
		mainContenedor.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		timer.start();
	}
	
	/**
	 * Visualmente crea la cuadricula de celdas dentro del JPanel panelCuadricula.
	 */
	private void crearCuadricula() {
		panelCuadricula = new JPanel();
		int rows = Control.getBoard();
		int cols = Control.getBoard();
		panelCuadricula.setLayout(new GridLayout(rows, cols));
		
		/**
		 * A cada celda se le añade un escucha.
		 */
		for(int fila=0; fila<rows; fila++) {
			for(int col=0; col<cols; col++) {
				control.getCeldas()[fila][col].addMouseListener(escuchaM);
				panelCuadricula.add(control.getCeldas()[fila][col]);
			}
		}
	}
	
	/**
	 * Crea el menú inferior que contiene el botón de reset, el número de banderas
	 * restantes y el tiempo que lleva jugando.
	 */
	private void bottomMenu() {
		MenuInf = new JPanel();
		MenuInf.setLayout(new BorderLayout());
		
		
		bottomPanel = new JPanel();
		
		resetButton = new JButton();
		resetButton.setIcon(new ImageIcon("src/resources/reset.png"));
		resetButton.setBackground(Color.GRAY);
		resetButton.addMouseListener(escuchaM);
		flagLabel = new JLabel();
		flagLabel.setIcon(new ImageIcon("src/resources/flag.png"));
		numFlags=new JLabel(Integer.toString(control.getFlags()));
		
		bottomPanel.add(resetButton);
		bottomPanel.add(flagLabel);
		bottomPanel.add(numFlags);
		
		MenuInf.add(bottomPanel, BorderLayout.WEST);
		MenuInf.add(timer, BorderLayout.EAST);
	}
	
	private class EscuchaMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			manejaEvento(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * Clase que maneja los eventos provenientes el mouse.
	 * @param MouseEvent e
	 */
	private void manejaEvento(MouseEvent e) {
		String fuente = e.getSource().getClass().getName();
		
		if(fuente.equals("buscaMinas.Celda")) {
			Celda celdaSeleccionada = (Celda)e.getSource();
			if(e.getButton()==3) {
				if(celdaSeleccionada.isFlagged()) {
					control.unlockCell(celdaSeleccionada.getFila(), celdaSeleccionada.getCol());
				}
				else {
					control.lockCell(celdaSeleccionada.getFila(), celdaSeleccionada.getCol());
				}
				numFlags.setText(Integer.toString(control.getFlags()));
				System.out.println(control.getFlags());
			}
			else {
				if(!celdaSeleccionada.isFlagged()) {
					control.checkCelda(celdaSeleccionada.getFila(), celdaSeleccionada.getCol());
					if(control.isGameOver()) {
						timer.stop();
						//guardarPuntaje();
						revelarMinas();
						try {
							//DataLine.Info daInfo = new DataLine.Info(Clip.class, null);
							
							//URL url = getClass().getResource("/resources/boom.wav");
							AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/boom.wav"));
							//DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
							//Clip clip = (Clip) AudioSystem.getLine(info);
							Clip clip = AudioSystem.getClip();
							clip.open(audioInputStream);
							clip.start();
							clip.drain();
						} catch (IOException e1) {
							System.out.println("Caught IO Exception: " + e1.getMessage());
						} catch (UnsupportedAudioFileException e1) {
							System.out.println("Caught Unsupported Audio File Exception: " + e1.getMessage()); 
					    } catch (LineUnavailableException e1) {
							System.out.println("Caught Line Unavailable Exception: " + e1.getMessage());
						}
						 endMessage();
					}
					else {
						if(control.isGanador()) {
							timer.stop();
							guardarPuntaje();
						}
					}
				}
			}
		}
		else if(e.getSource()==resetButton){
			timer.setTime(0);
			taparCeldas();
			control.reset();
			numFlags.setText(Integer.toString(control.getFlags()));
		}
	}
	/**
	 * Cuando se pierde la partida se le revelan al jugador las minas que estaban tapadas
	 * y cuales banderas eran minas.
	 */
	private void revelarMinas() {
		for(int fila=0; fila < Control.getBoard(); fila++) {
			for(int col=0; col < Control.getBoard(); col++) {
				if(control.getCeldas()[fila][col].isMina()) {
					control.getCeldas()[fila][col].revelar();
				}
			}
		}
	}
	
	/**
	 * Guarda el tiempo logrado por el jugador, junto con su nombre y fecha.
	 */
	private void guardarPuntaje() {
		int posicion = control.compararTiempos(timer.getTime(), files.getTiempos());
		/**
		 * Este bloque condicional evalua si el tiempo fue bueno.
		 * La función de Control compararTiempos, devuelve -1 si NO fue bueno.
		 * Si en cambio, el tiempo logrado merece estar dentro del top 5, devuelve
		 * la posición (lo que se entiende como la linea donde debe ir.)
		 */
		if(posicion!=-1) {
			String name = JOptionPane.showInputDialog(mainContenedor, "¡Has Ganado!, por favor ingresa tu nombre");
			String time = timer.timeFormat(timer.getTime());
			String date = timer.getDate();
			
			/** Le paso a FileManager el nuevo tiempo (sin formato). **/
			files.modTiempos(posicion, timer.getTime());
			System.out.println("has hecho: "+timer.getTime());
			/** Ahora si le paso a FileManager el nombre, tiempo logrado (hh:mm:ss) y fecha. **/
			files.gestionarTextFile(posicion, name+" "+time+" "+date);
		}
		else {
			endMessage();
		}
	}
	
	/**
	 * Mensaje final una vez acaba el juego, ya sea que pierda o que gane.
	 * Si ganó pero cae en esta función quiere decir que no hizo un tiempo
	 * suficientemente bueno.
	 */
	private void endMessage() {
		int option;
		if(control.isGanador()) {
			option = JOptionPane.showConfirmDialog(mainContenedor, 
					"¡Has Ganado! ¿Deseas jugar otra vez?", "GameOver", JOptionPane.YES_NO_OPTION);
		}
		else {
			option = JOptionPane.showConfirmDialog(mainContenedor, 
					"Perdiste. ¿Deseas jugar otra vez?", "GameOver", JOptionPane.YES_NO_OPTION);
		}
			
		if(option==0) {
			taparCeldas();
			control.reset();
			numFlags.setText(Integer.toString(control.getFlags()));
			timer.setTime(0);
			timer.start();
		}
		else {
			System.exit(0);
		}
	}
	
	/**
	 * Tapa todas las celdas. Esta función se llama cuando el jugador quiera volver a jugar otra partida
	 * o reiniciar su partida actual.
	 */
	private void taparCeldas() {
		for(int fila=0; fila < Control.getBoard(); fila++) {
			for(int col=0; col < Control.getBoard(); col++) {
				control.getCeldas()[fila][col].setIcon(new ImageIcon("src/resources/10.png"));
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==Newgame) {
			timer.setTime(0);
			taparCeldas();
			control.reset();
			numFlags.setText(Integer.toString(control.getFlags()));
        }
		
		//Lee el .txt y muestra los puntajes del top 5 en una nueva ventana
        if (e.getSource()==Score) {
        	String input = "";
        	//Lector
        	BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader("src/resources/top5.txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	String line = null;
        	//Un bucle que recorre cada linea del .txt
        	try {
				while ((line = reader.readLine()) != null) {
				    //A\F1ade la linea y  "\n" que indica una nueva linea
				    input += line + "\n";
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	try {
				reader.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	//Imprime la variable input del lector la cual contiene todas las lineas del .txt en un JTextArea
        	JTextArea textArea = new JTextArea(input);
        	JScrollPane scrollPane = new JScrollPane(textArea);  // El JScrollPane solo agrega un marco, asi se ve mejor.
        	textArea.setLineWrap(true);  
        	textArea.setWrapStyleWord(true); 
        	scrollPane.setPreferredSize( new Dimension( 200, 100 ) ); //A E S T H E T I C  S I Z E
        	JOptionPane.showMessageDialog(null, 
        	    scrollPane, 
        	    "Lista de Puntajes:", 
        	    JOptionPane.PLAIN_MESSAGE);
     
        }
    }
}