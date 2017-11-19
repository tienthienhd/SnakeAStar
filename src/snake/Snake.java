package snake;

import java.awt.Color;
import java.awt.Graphics;

import game.GamePanel;

public class Snake {
	public static final int DOT_SIZE = 20;
	private static final int MAX_DOT = 900;
	public int[] x;
	public int[] y;
	public int nbDot;
	public Direction direction;
	Color colorTail = Color.GREEN;
	Color colorHead = Color.RED;


	public Snake(int initDot, int type) {

		x = new int[MAX_DOT];
		y = new int[MAX_DOT];

		this.nbDot = initDot;
		for (int i = 0; i < this.nbDot; i++) {
			x[i] = this.nbDot - i;
			y[i] = 0;
		}
		direction = Direction.RIGHT;
		if(type == 1) {
			this.colorHead = Color.RED;
			this.colorTail = Color.GREEN;
		} else if(type == 2) {
			this.colorHead = Color.ORANGE;
			this.colorTail = Color.CYAN;
		}
	}

	public void control(Direction dir) {
		if (dir == Direction.LEFT && this.direction != Direction.RIGHT) {
			this.direction = dir;
		}

		if (dir == Direction.RIGHT && this.direction != Direction.LEFT) {
			this.direction = dir;
		}

		if (dir == Direction.UP && this.direction != Direction.DOWN) {
			this.direction = dir;
		}

		if (dir == Direction.DOWN && this.direction != Direction.UP) {
			this.direction = dir;
		}

		// this.direction = dir;
	}

	public void move() {
		for (int i = this.nbDot - 1; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
		case DOWN:
			if (y[0] >= GamePanel.HEIGHT - 1) {
//				y[0] = 0;
			} else {
				y[0]++;
			}
			break;
		case LEFT:
			if (x[0] <= 0) {
//				x[0] = GamePanel.WIDTH - 1;
			} else {
				x[0]--;
			}
			break;
		case RIGHT:
			if (x[0] >= GamePanel.WIDTH - 1) {
//				x[0] = 0;
			} else {
				x[0]++;
			}
			break;
		case UP:
			if (y[0] <= 0) {
//				y[0] = GamePanel.HEIGHT - 1;
			} else {
				y[0]--;
			}
		}
	}

	public void draw(Graphics g) {
		g.setColor(colorTail);
		for (int i = 1; i < this.nbDot; i++) {
			// g.drawImage(ball, x[i], y[i], null);
			g.fillOval(x[i] * DOT_SIZE, y[i] * DOT_SIZE, DOT_SIZE, DOT_SIZE);
		}
		// g.drawImage(head, x[0], y[0], null);
		g.setColor(colorHead);
		g.fillOval(x[0] * DOT_SIZE, y[0] * DOT_SIZE, DOT_SIZE, DOT_SIZE);
		g.setColor(Color.BLACK);
		switch (direction) {
		case DOWN:
			g.fillOval(x[0] * DOT_SIZE + 5, y[0] * DOT_SIZE + 13, 5, 5);
			g.fillOval(x[0] * DOT_SIZE + 10, y[0] * DOT_SIZE + 13, 5, 5);
			break;
		case LEFT:
			g.fillOval(x[0] * DOT_SIZE + 5, y[0] * DOT_SIZE + 5, 5, 5);
			g.fillOval(x[0] * DOT_SIZE + 5, y[0] * DOT_SIZE + 10, 5, 5);
			break;
		case RIGHT:
			g.fillOval(x[0] * DOT_SIZE + 13, y[0] * DOT_SIZE + 5, 5, 5);
			g.fillOval(x[0] * DOT_SIZE + 13, y[0] * DOT_SIZE + 10, 5, 5);
			break;
		case UP:
			g.fillOval(x[0] * DOT_SIZE + 5, y[0] * DOT_SIZE + 5, 5, 5);
			g.fillOval(x[0] * DOT_SIZE + 10, y[0] * DOT_SIZE + 5, 5, 5);
			break;
		}
	}

	public boolean killSelf() {

		for (int i = this.nbDot - 1; i > 0; i--) {
			if ((x[0] == x[i] && y[0] == y[i])) {
				return true;
			}
		}
		return false;
	}

	public void eat() {
		this.nbDot++;
	}
	
	public boolean onSnake(int x0, int y0) {
		for(int i = 0; i < this.nbDot; i++) {
			if(x[i] == x0 && y[i] == y0) {
				return true;
			}
		}
		return false;
	}
}
