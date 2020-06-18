package lect.omok.client;

import javax.swing.JFrame;

public class Client {
	public static void main(String [] args) {
		JFrame gameWindow = new JFrame();
		gameWindow.getContentPane().add(new GamePanel());
		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);
	}
}
