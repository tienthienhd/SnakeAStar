package game;


import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		GamePanel game = new GamePanel();
		
		JFrame frame = new JFrame();
		frame.setTitle(GamePanel.title);
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
