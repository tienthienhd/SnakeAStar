package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

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
		// p.removeLast();
		return p;
	}
}
