package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import org.w3c.dom.events.MouseEvent;

import game.states.GameState;
import game.states.MenuState;
import game.states.State;
import snake.Direction;
import snake.Snake;

/**
 * Main panel of game and game loop
 * 
 * @author tienthien
 *
 */
public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	// properties of frame
	public static final String title = "Snake AI";
	public static final int WIDTH_PIXEL = 500;
	public static final int HEIGHT_PIXEL = WIDTH_PIXEL;// * 9 / 16; // 9/16 la ti le khung hinh chuan
	public static final int WIDTH = WIDTH_PIXEL / Snake.DOT_SIZE;
	public static final int HEIGHT = HEIGHT_PIXEL / Snake.DOT_SIZE;

	// thread implement actions of game
	private Thread thread;
	boolean running;

	// graphics process
	private Image dbImage;
	private Graphics2D dbg;
	boolean gameOver;
	
	private KeyInput key;

	// state
    private State gameState;
    private State menuState;

    private Handler handler;
	// constructor
	public GamePanel() {
		super();
		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(WIDTH_PIXEL, HEIGHT_PIXEL));
		this.setMinimumSize(new Dimension(WIDTH_PIXEL, HEIGHT_PIXEL));
		this.setMaximumSize(new Dimension(WIDTH_PIXEL, HEIGHT_PIXEL));
		this.setFocusable(true);
	}

	// initialize of game
	private void init() {

		
		this.handler = new Handler(this);
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {}
			
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {}
			
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {}
			
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(State.getState() instanceof MenuState) {
					((MenuState) menuState).mouseListen(e.getX(), e.getY(), 1);
				}
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(java.awt.event.MouseEvent e) {
				if(State.getState() instanceof MenuState) {
					((MenuState) menuState).mouseListen(e.getX(), e.getY(), 2);
				}
			}
			
			@Override
			public void mouseDragged(java.awt.event.MouseEvent e) {}
		});

		gameState = new GameState(this.handler);
    	menuState = new MenuState(this.handler);
    	State.setState(menuState, 0);
//    	State.setState(gameState);
    	
		
		// Location start = new Location(snake.x[0] / Snake.DOT_SIZE, snake.y[0] /
		// Snake.DOT_SIZE);
		// path = AStar.parseToDirection(start,
		// AStar.findPath(map, start, new Location(fruit.x / Snake.DOT_SIZE, fruit.y /
		// Snake.DOT_SIZE)));
	}

	// update data of game
	private void update() {
		if(State.getState() != null){
    		State.getState().update();
    	}
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

		dbg.setColor(Color.GRAY);
		dbg.fillRect(0, 0, WIDTH_PIXEL, HEIGHT_PIXEL);

		// draw here
		if(State.getState() != null){
    		State.getState().render(dbg);
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

	public void playGame(int mode) {
		State.setState(gameState, mode);
	}
	
	public void addKeyListener(Snake snake) {
		this.key = new KeyInput(snake);
		this.addKeyListener(key);
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


