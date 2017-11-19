package game;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import snake.Snake;

public class Handler {
	private GamePanel gamePanel;
	
	public Handler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public FontMetrics getFontMetrics(Font font) {
		return this.gamePanel.getFontMetrics(font);
	}

	public void setGameOver(boolean b) {
		gamePanel.gameOver = b;
	}
	
	public boolean getGameOver() {
		return this.gamePanel.gameOver;
	}
	
	public void setGameRunning(boolean b) {
		this.gamePanel.running = b;
	}
	
	public void addMouseListener(MouseListener l) {
		this.gamePanel.addMouseListener(l);
	}
	
	public void addMouseMotionListener(MouseMotionListener l) {
		this.gamePanel.addMouseMotionListener(l);
	}

	public void playGame(int mode) {
		this.gamePanel.playGame(mode);
	}
	
	public void addKeyListener(Snake snake) {
		this.gamePanel.addKeyListener(snake);
	}
}
