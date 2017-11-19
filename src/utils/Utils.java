package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;

import ai.Node;
import snake.Direction;

public class Utils {
	public static Node contains(Collection<Node> collection, Node node) {
		for (Node n : collection) {
			if (n.x == node.x && n.y == node.y) {
				return n;
			}
		}
		return null;
	}

	public static Comparator<Node> comp = new Comparator<Node>() {

		@Override
		public int compare(Node o1, Node o2) {
			double d = o1.getCost() - o2.getCost();
			return (d > 0) ? 1 : (d < 0) ? -1 : 0;
		}
	};

	public static boolean compare(Node a, Node b) {
		if (a.x == b.x && a.y == b.y) {
			return true;
		}
		return false;
	}

	public static ArrayList<Direction> parsePath(Node start, ArrayList<Node> path) {
		ArrayList<Direction> p = new ArrayList<>();
		Node from = start;
		Node to = path.remove(0);

		Direction dirStart = null;
		if (to.x - from.x > 0) {
			dirStart = Direction.RIGHT;
		} else if (to.x - from.x < 0) {
			dirStart = Direction.LEFT;
		}

		if (to.y - from.y > 0) {
			dirStart = Direction.DOWN;
		} else if (to.y - from.y < 0) {
			dirStart = Direction.UP;
		}
		if (dirStart != null)
			p.add(dirStart);
		from = to;

		while (!path.isEmpty()) {
			to = path.remove(0);
			Direction dir = null;
			if (to.x - from.x > 0) {
				dir = Direction.RIGHT;
			} else if (to.x - from.x < 0) {
				dir = Direction.LEFT;
			}

			if (to.y - from.y > 0) {
				dir = Direction.DOWN;
			} else if (to.y - from.y < 0) {
				dir = Direction.UP;
			}
			p.add(dir);
			from = to;
		}
		return p;
	}
	
	
	public static int[][] loadMap(File file){
		if(!file.exists()) {
			System.out.println("File don't exists!");
			System.exit(1);
		}
		FileReader fr;
		try {
			fr = new FileReader(file);
			Scanner scanner = new Scanner(fr);
			
			int width = scanner.nextInt();
			int height = scanner.nextInt();
			int[][] matrix = new int[height][width];
			
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					matrix[i][j] = scanner.nextInt();
				}
			}
			scanner.close();
			fr.close();
			return matrix;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static void main(String[] args) {
		int[][] map = loadMap(new File("map1.txt"));
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
}
