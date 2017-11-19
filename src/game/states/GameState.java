package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import ai.Node;
import ai.graph.AStar;
import ai.graph.Graph;
import game.GamePanel;
import game.Handler;
import snake.Direction;
import snake.Snake;
import utils.Utils;

public class GameState extends State {
	public static Font normalFont = new Font("Arial", Font.BOLD, 15);
	private Graph graph;
	private Snake snake;
	ArrayList<Direction> path;

	int xFruit;
	int yFruit;

	private Snake player;

	public GameState(Handler handler) {
		super(handler);
		graph = new Graph(GamePanel.WIDTH, GamePanel.HEIGHT);
		path = new ArrayList<>();
	}

	public void newGame(int mode) {
		if (mode == 1) {
			player = new Snake(3, 1);
			handler.addKeyListener(player);
		} else if (mode == 2) {
			player = new Snake(3, 1);
			snake = new Snake(3, 2);
			handler.addKeyListener(player);
		} else if (mode == 3) {
			snake = new Snake(3, 2);
		}
		newFruit();
	}

	@Override
	public void update() {
		if (snake != null) {
			if (!path.isEmpty()) {
				Direction dir = path.remove(0);
				// System.out.println(dir);
				snake.control(dir);
			}
			snake.move();
			if (checkEat(snake)) {
				newFruit();
				snake.eat();
			}
			checkCollision(snake);
		}

		if (player != null) {
			player.move();
			if (checkEat(player)) {
				newFruit();
				player.eat();
			}
			checkCollision(player);
		}
	}

	@Override
	public void render(Graphics g) {
		if (this.handler.getGameOver()) {
			drawGameOver(g);
			this.handler.setGameRunning(false);
		} else {
			// drawMap(dbg);
			drawFruit(g);
			if (player != null)
				player.draw(g);
			if (snake != null) {
				snake.draw(g);
			}
			g.setColor(Color.ORANGE);
			g.setFont(normalFont);
			if (snake == null)
				g.drawString("Score: " + player.nbDot, GamePanel.WIDTH_PIXEL - 70, 13);

			if (player == null)
				g.drawString("Score: " + snake.nbDot, GamePanel.WIDTH_PIXEL - 70, 13);

			if (snake != null && player != null) {
				g.drawString("Player's score: " + player.nbDot, GamePanel.WIDTH_PIXEL - 150, 13);
				g.drawString("Computer's score: " + snake.nbDot, GamePanel.WIDTH_PIXEL - 150, 33);
			}
		}
	}

	private void checkCollision(Snake snake) {
		if (snake.killSelf()) {
			this.handler.setGameOver(true);
		}

		if (snake.x[0] < 0 || snake.y[0] < 0 || snake.x[0] > GamePanel.WIDTH || snake.y[0] > GamePanel.HEIGHT) {
			this.handler.setGameOver(true);
		}
	}

	private boolean checkEat(Snake snake) {
		if (snake.x[0] == xFruit && snake.y[0] == yFruit) {
			return true;
		}
		return false;
	}

	private void drawFruit(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(xFruit * Snake.DOT_SIZE, yFruit * Snake.DOT_SIZE, Snake.DOT_SIZE, Snake.DOT_SIZE);
	}

	private void newFruit() {
		do {
			xFruit = (int) (Math.random() * 24);
			yFruit = (int) (Math.random() * 24);
			if (snake != null && snake.onSnake(xFruit, yFruit)) {
				continue;
			}
			if (player != null && player.onSnake(xFruit, yFruit)) {
				continue;
			}
			break;
		} while (true);

		if (snake != null) {
			Node start = graph.getNode(snake.x[0], snake.y[0]);
			graph.setSnake(snake);

			ArrayList<Node> p = AStar.findPath(graph, start, graph.getNode(xFruit, yFruit));
			if (p != null)
				path = Utils.parsePath(start, p);
		}
	}

	private void drawGameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 30);
		FontMetrics metr = this.handler.getFontMetrics(small);

		g.setColor(Color.GREEN);
		g.setFont(small);
		g.drawString(msg, (GamePanel.WIDTH_PIXEL - metr.stringWidth(msg)) / 2, GamePanel.HEIGHT_PIXEL / 2);
		
		if (player != null)
			g.drawString("Your score: " + player.nbDot, 
					(GamePanel.WIDTH_PIXEL - metr.stringWidth("Your score: ")) / 2,
					GamePanel.HEIGHT_PIXEL / 2 + metr.getHeight() + 10);
		if (snake != null)
			g.drawString("Computer's score: " + snake.nbDot, 
					(GamePanel.WIDTH_PIXEL - metr.stringWidth("Computer's score: ")) / 2,
					GamePanel.HEIGHT_PIXEL / 2 + metr.getHeight() + 10 + 30);
	}
}
