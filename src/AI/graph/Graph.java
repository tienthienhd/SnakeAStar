package ai.graph;

import java.util.ArrayList;

import ai.Node;
import snake.Snake;

public class Graph {

	public Node[][] graph;
	public Snake snake;

	public Graph(int width, int height) {
		this.graph = new Node[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				graph[i][j] = new Node(j, i, true);
			}
		}
	}
	
	public Graph(int[][] map) {
		if(map == null || map[0] == null) {
			System.out.println("Load map failed");
			System.exit(1);
		}
		this.graph = new Node[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				graph[i][j] = new Node(j, i, map[i][j] == 0 ? true : false);
			}
		}
	}

	public ArrayList<Node> getNeighbor(Node node) {
		ArrayList<Node> neighbors = new ArrayList<>();
		
		if(onBoard(node.x, node.y - 1)) {
			Node up = graph[node.y - 1][node.x];
			if(!onSnake(up) && up.passable) {
				neighbors.add(up);
			}
		}
		if(onBoard(node.x, node.y + 1)) {
			Node down = graph[node.y + 1][node.x];
			if(!onSnake(down) && down.passable) {
				neighbors.add(down);
			}
		}
		if(onBoard(node.x + 1, node.y)) {
			Node right = graph[node.y][node.x + 1];
			if(!onSnake(right) && right.passable) {
				neighbors.add(right);
			}
		}
		if(onBoard(node.x - 1, node.y)) {
			Node left = graph[node.y][node.x - 1];
			if(!onSnake(left) && left.passable) {
				neighbors.add(left);
			}
		}
		
		
		return neighbors;
	}
	
	public boolean onBoard(int x, int y) {
		if(0 <= x && x < getWidth() && 0 <= y && y < getHeight()) {
			return true;
		}
		return false;
	}
	
	public boolean onSnake(Node node) {
		for(int i = 0; i < snake.nbDot; i++) {
			if(node.x == snake.x[i] && node.y == snake.y[i]) {
				return true;
			}
		}
		return false;
	}

	public double getCost(Node a, Node b) {
		return 1;
	}

	public int getWidth() {
		return this.graph[0].length;
	}

	public int getHeight() {
		return this.graph.length;
	}

	public Node getNode(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight())
			return null;
		return graph[y][x];
	}
	
	public void setSnake(Snake snake) {
		this.snake = snake;
	}
	
	public boolean passable(int x, int y) {
		return this.getNode(x, y).passable;
	}
}
