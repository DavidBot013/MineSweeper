package buscaMinas;

import javax.swing.JFrame;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUI gui = new GUI();
				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
		});
	}

}
