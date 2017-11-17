package snake;

import java.awt.Color;
import java.awt.Graphics;

public class Snake {
	public static final int DOT_SIZE = 20;
	private static final int MAX_DOT = 900;
	public int[] x;
	public int[] y;
	int nbDot;
	Direction direction;

	// private Image head;
	// private Image ball;

	public Snake(int initDot) {
		// ImageIcon iid = new ImageIcon(this.getClass().getResource("dot.png"));
		// ball = iid.getImage();
		//
		// ImageIcon iih = new ImageIcon(this.getClass().getResource("head.png"));
		// head = iih.getImage();

		x = new int[MAX_DOT];
		y = new int[MAX_DOT];

		this.nbDot = initDot;
		for (int i = 0; i < this.nbDot; i++) {
			x[i] = (this.nbDot - i) * DOT_SIZE;
			y[i] = 0;
		}
		direction = Direction.RIGHT;
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
			if (y[0] + DOT_SIZE >= 500) {
				y[0] = 0;
			} else {
				y[0] += DOT_SIZE;
			}
			break;
		case LEFT:
			if (x[0] - DOT_SIZE < 0) {
				x[0] = 500;
			} else {
				x[0] -= DOT_SIZE;
			}
			break;
		case RIGHT:
			if (x[0] + DOT_SIZE >= 500) {
				x[0] = 0;
			} else {
				x[0] += DOT_SIZE;
			}
			break;
		case UP:
			if (y[0] - DOT_SIZE < 0) {
				y[0] = 500;
			} else {
				y[0] -= DOT_SIZE;
			}
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		for (int i = 1; i < this.nbDot; i++) {
			// g.drawImage(ball, x[i], y[i], null);
			g.fillOval(x[i], y[i], DOT_SIZE, DOT_SIZE);
		}
		// g.drawImage(head, x[0], y[0], null);
		g.setColor(Color.RED);
		g.fillOval(x[0], y[0], DOT_SIZE, DOT_SIZE);
		g.setColor(Color.BLACK);
		switch (direction) {
		case DOWN:
			g.fillOval(x[0] + 5, y[0] + 13, 5, 5);
			g.fillOval(x[0] + 10, y[0] + 13, 5, 5);
			break;
		case LEFT:
			g.fillOval(x[0] + 5, y[0] + 5, 5, 5);
			g.fillOval(x[0] + 5, y[0] + 10, 5, 5);
			break;
		case RIGHT:
			g.fillOval(x[0] + 13, y[0] + 5, 5, 5);
			g.fillOval(x[0] + 13, y[0] + 10, 5, 5);
			break;
		case UP:
			g.fillOval(x[0] + 5, y[0] + 5, 5, 5);
			g.fillOval(x[0] + 10, y[0] + 5, 5, 5);
			break;
		}
	}

	public boolean killSelf() {

		for (int i = this.nbDot - 1; i > 0; i--) {
			if (/* (i > 4) && */(x[0] == x[i] && y[0] == y[i])) {
				return true;
			}
		}
		return false;
	}

	public void eat() {
		this.nbDot++;
	}
}
