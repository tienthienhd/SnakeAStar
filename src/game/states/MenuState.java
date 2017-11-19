package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import game.GamePanel;
import game.Handler;

public class MenuState extends State {

	private Button btnSingle;
	private Button btnWithComputer;
	private Button btnAuto;

	public MenuState(Handler handler) {
		super(handler);
		this.btnSingle = new Button(handler, "Single Play", 1);
		this.btnWithComputer = new Button(handler, "Play with Computer", 2);
		this.btnAuto = new Button(handler, "Auto Play", 3);

	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		btnSingle.render(g);
		btnWithComputer.render(g);
		btnAuto.render(g);
	}

	public void mouseListen(int x, int y, int action) {
		btnAuto.onButton(x, y, action);
		btnSingle.onButton(x, y, action);
		btnWithComputer.onButton(x, y, action);
	}

}

class Button {
	public static final int BTN_WIDTH = 300;
	public static final int BTN_HEIGHT = 70;
	private int x, y;
	Handler handler;
	String name;
	int position;
	Color background = Color.WHITE;
	Color forceground = Color.BLACK;

	public Button(Handler handler, String name, int position) {
		this.handler = handler;
		this.name = name;
		this.position = position;
		this.x = GamePanel.WIDTH_PIXEL / 2 - BTN_WIDTH / 2;
		this.y = position * BTN_HEIGHT + 30 * position;
	}

	public void render(Graphics g) {
		g.setColor(background);
		g.fillRoundRect(x, y, BTN_WIDTH, BTN_HEIGHT, 15, 15);

		Font big = new Font("Helvetica", Font.BOLD, 30);
		FontMetrics metr = this.handler.getFontMetrics(big);
		g.setColor(forceground);
		g.setFont(big);
		g.drawString(name, (GamePanel.WIDTH_PIXEL - metr.stringWidth(name)) / 2,
				position * BTN_HEIGHT + metr.getHeight() + 30 * position);
	}

	public void onButton(int x, int y, int action) {
		if (this.x < x && x < this.x + BTN_WIDTH && this.y < y && y < this.y + BTN_HEIGHT) {
			if (action == 1) {
				if (name.equals("Single Play"))
					handler.playGame(1);
				else if(name.equals("Play with Computer")) {
					handler.playGame(2);
				} else if(name.equals("Auto Play")) {
					handler.playGame(3);
				}
			} else if (action == 2) {
				this.background = Color.BLUE;
				this.forceground = Color.RED;
			}
		} else {
			this.background = Color.WHITE;
			this.forceground = Color.BLACK;
		}
	}
}
