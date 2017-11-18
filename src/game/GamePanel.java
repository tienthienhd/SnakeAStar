package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import ai.AStar;
import ai.Node;
import ai.graph.Graph;
import snake.Direction;
import snake.Snake;
import utils.Utils;

/**
 * Main panel of game and game loop
 * 
 * @author tienthien
 *
 */
public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	// properties of frame
	public static final String title = "Game RPG ver 1.0";
	public static final int WIDTH_PIXEL = 500;
	public static final int HEIGHT_PIXEL = WIDTH_PIXEL;// * 9 / 16; // 9/16 la ti le khung hinh chuan
	public static final int WIDTH = WIDTH_PIXEL / Snake.DOT_SIZE;
	public static final int HEIGHT = HEIGHT_PIXEL / Snake.DOT_SIZE;

	// thread implement actions of game
	private Thread thread;
	private boolean running;

	// graphics process
	private Image dbImage;
	private Graphics2D dbg;

	private Graph graph;
	private Snake snake;
	ArrayList<Direction> path;

	int xFruit;
	int yFruit;

	private boolean gameOver;

	private Snake player;

	// constructor
	public GamePanel() {
		super();
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(WIDTH_PIXEL, HEIGHT_PIXEL));
		this.setMinimumSize(new Dimension(WIDTH_PIXEL, HEIGHT_PIXEL));
		this.setMaximumSize(new Dimension(WIDTH_PIXEL, HEIGHT_PIXEL));
		this.setFocusable(true);
	}

	// initialize of game
	private void init() {

		// map = new SquareGrid(WIDTH / Snake.DOT_SIZE, HEIGHT / Snake.DOT_SIZE);
		// map.forests.add(new Location(3, 3));
		// map.forests.add(new Location(4, 3));
		// map.forests.add(new Location(5, 3));
		// map.forests.add(new Location(6, 3));
		// map.forests.add(new Location(7, 3));
		// map.forests.add(new Location(8, 3));
		// map.forests.add(new Location(9, 3));
		// map.forests.add(new Location(2, 3));
		// for (int i = 1; i < 23; i++) {
		// map.walls.add(new Location(i, 7));
		// }
		graph = new Graph(WIDTH, HEIGHT);

		snake = new Snake(3);
		// KeyInput key = new KeyInput(snake);
		player = new Snake(3);
		KeyInput key = new KeyInput(player);
		this.addKeyListener(key);

		newFruit();
		// Location start = new Location(snake.x[0] / Snake.DOT_SIZE, snake.y[0] /
		// Snake.DOT_SIZE);
		// path = AStar.parseToDirection(start,
		// AStar.findPath(map, start, new Location(fruit.x / Snake.DOT_SIZE, fruit.y /
		// Snake.DOT_SIZE)));
	}

	// update data of game
	private void update() {
		if (!path.isEmpty()) {
			Direction dir = path.remove(0);
			// System.out.println(dir);
			snake.control(dir);
		}
		snake.move();
		player.move();
		if (checkEat(snake)) {
			newFruit();
			snake.eat();
		}

		if (checkEat(player)) {
			newFruit();
			player.eat();
		}
		checkCollision();
	}

	private void checkCollision() {
		if (snake.killSelf()) {
			gameOver = true;
		}

		if (snake.x[0] < 0 || snake.y[0] < 0 || snake.x[0] > WIDTH || snake.y[0] > HEIGHT) {
			gameOver = true;
		}
	}

	private boolean checkEat(Snake snake) {
		if (snake.x[0] == xFruit && snake.y[0] == yFruit) {
			return true;
		}
		return false;
	}

	// draw graphics to buffer
	private void render() {
		if (dbImage == null) {
			dbImage = this.createImage(WIDTH_PIXEL, HEIGHT_PIXEL);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else
				dbg = (Graphics2D) dbImage.getGraphics();
		}

		dbg.setColor(Color.gray);
		dbg.fillRect(0, 0, WIDTH_PIXEL, HEIGHT_PIXEL);

		// draw here

		if (gameOver) {
			drawGameOver(dbg);
			running = false;
		} else {
			// drawMap(dbg);
			drawFruit(dbg);
			player.draw(dbg);
			snake.draw(dbg);
		}
	}

	// draw graphics to screen
	public void paintScreen() {
		Graphics2D g = (Graphics2D) this.getGraphics();
		if (dbImage != null && g != null) {
			g.drawImage(dbImage, 0, 0, WIDTH_PIXEL, HEIGHT_PIXEL, null);
			Toolkit.getDefaultToolkit().sync(); // for linux
			g.dispose();
		}
	}

	// private void drawMap(Graphics g) {
	// for (int y = 0; y < map.height; y++) {
	// for (int x = 0; x < map.width; x++) {
	// Location id = new Location(x, y);
	// // Predicate<Location> p1 = e -> e.x == id.x && e.y == id.y;
	// if (map.walls.stream().anyMatch(Location -> Location.x == id.x && Location.y
	// == id.y)) {
	// g.setColor(Color.BLACK);
	// g.fillRect(x * Snake.DOT_SIZE, y * Snake.DOT_SIZE, Snake.DOT_SIZE,
	// Snake.DOT_SIZE);
	// } else if (map.forests.stream().anyMatch(Location -> Location.x == id.x &&
	// Location.y == id.y)) {
	// g.setColor(Color.YELLOW);
	// g.fillRect(x * Snake.DOT_SIZE, y * Snake.DOT_SIZE, Snake.DOT_SIZE,
	// Snake.DOT_SIZE);
	//
	// } else {
	// g.setColor(Color.GRAY);
	// g.fillRect(x * Snake.DOT_SIZE, y * Snake.DOT_SIZE, Snake.DOT_SIZE,
	// Snake.DOT_SIZE);
	// }
	// }
	// }
	// }

	private void drawFruit(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(xFruit * Snake.DOT_SIZE, yFruit * Snake.DOT_SIZE, Snake.DOT_SIZE, Snake.DOT_SIZE);
	}

	// game loop
	@Override
	public void run() {
		init();
		while (running) {
			update();
			render();
			paintScreen();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void newFruit() {
		do {
			xFruit = (int) (Math.random() * 24);
			yFruit = (int) (Math.random() * 24);
		} while (snake.onSnake(xFruit, yFruit) || player.onSnake(xFruit, yFruit));

		Node start = new Node(snake.x[0], snake.y[0]);
		graph.setSnake(snake);

		ArrayList<Node> p = AStar.findPath(graph, start, new Node(xFruit, yFruit));
		if (p != null)
			path = Utils.parsePath(start, p);
	}

	private void drawGameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 30);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.BLACK);
		g.setFont(small);
		g.drawString(msg, (WIDTH_PIXEL - metr.stringWidth(msg)) / 2, HEIGHT_PIXEL / 2);
	}

	// start game is called when run program
	public synchronized void startGame() {
		if (thread == null && !running) {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	// stop game
	public synchronized void stopGame() {
		if (running) {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// active start game when this panel was add on container
	@Override
	public void addNotify() {
		super.addNotify();
		startGame();
	}
}

class KeyInput extends KeyAdapter {

	private Snake snake;

	public KeyInput(Snake snake) {
		this.snake = snake;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			snake.control(Direction.LEFT);
		}

		else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			snake.control(Direction.RIGHT);
		}

		else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			snake.control(Direction.UP);
		}

		else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			snake.control(Direction.DOWN);
		}

	}
}
