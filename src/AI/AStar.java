package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import ai.graph.Graph;
import snake.Direction;
import utils.Utils;

public class AStar {
	
//	public static void main(String[] args) {
//		Graph graph = new Graph(10, 10);
//		ArrayList<Node> path = findPath(graph, graph.getNode(0, 0), graph.getNode(3, 3));
//		
//		for(Node node: path) {
//			System.out.println(node);
//		}
//		
//		ArrayList<Direction> pathDir = Utils.parsePath(graph.getNode(0, 0), path);
//		for(Direction dir: pathDir) {
//			System.out.println(dir);
//		}
//	}

	public static ArrayList<Node> findPath(Graph graph, Node start, Node goal) {
		PriorityQueue<Node> open = new PriorityQueue<>(Utils.comp);
		open.add(start);
		
		ArrayList<Node> closed = new ArrayList<>();
		
		start.parent = null;
		start.costFromStart = 0;
		start.costEstimateToGoal = heuristic(start, goal);
		

		while (!open.isEmpty()) {
			Node current = open.remove();
			closed.add(current);
			
			if (Utils.compare(current, goal)) {
				goal.parent = current.parent;
				return buildPath(goal);
			}
			
			for(Node neighbor : graph.getNeighbor(current)) {
				double costFromStartOfNeighbor = current.costFromStart + graph.getCost(current, neighbor);
				if(Utils.contains(open, neighbor) == null && Utils.contains(closed, neighbor) == null
						|| costFromStartOfNeighbor < neighbor.costFromStart) {
					neighbor.parent = current;
					neighbor.costFromStart = costFromStartOfNeighbor;
					neighbor.costEstimateToGoal = heuristic(neighbor, goal);
					
					Node tmp = Utils.contains(closed, neighbor);
					if (tmp != null) {
						closed.remove(tmp);
					}
					
					if(Utils.contains(open, neighbor) == null) {
						open.add(neighbor);
					}
				}
				
//				if(Utils.contains(closed, neighbor) != null) {
//					
//					continue;
//				}
//				
//				if(Utils.contains(open, neighbor) == null) {
//					open.add(neighbor);
//				}
//				
//				double costFromStartOfNeighbor = current.costFromStart + graph.getCost(current, neighbor);
//				if(costFromStartOfNeighbor >= neighbor.costFromStart) {
//					continue;
//				}
//				
//				neighbor.parent = current;
//				neighbor.costFromStart = costFromStartOfNeighbor;
//				neighbor.costEstimateToGoal = heuristic(neighbor, goal);
			}
		}

		System.out.println("can't find path");
		return null;
	}

	public static ArrayList<Node> buildPath(Node goal) {
		ArrayList<Node> path = new ArrayList<>();
		Node current = goal;
		while(current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);
		return path;
	}
	
//	public static ArrayList<Node> reversePath(ArrayList<Node> path){
//		Collections.reverse(path);
//		return path;
//	}
	
	public static double heuristic(Node start, Node goal) {
		return Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y);
	}
}
