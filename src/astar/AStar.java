package astar;

import java.util.HashMap;
import java.util.LinkedList;

import astar.graph.Location;
import astar.graph.SquareGrid;
import snake.Direction;

public class AStar {
	public static HashMap<Location, Location> cameFrom = new HashMap<>();
	public static HashMap<Location, Double> costSoFar = new HashMap<>();
	
	private static double heuristic(Location a, Location b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	public static LinkedList<Location> findPath(SquareGrid graph, Location start, Location goal) {
		PriorityQueueMyshelf<Location> priorityQueue = new PriorityQueueMyshelf<>();
		priorityQueue.add(start, 0d);
		
		cameFrom = new HashMap<>();
		costSoFar = new HashMap<>();
		
		cameFrom.put(start, start);
		costSoFar.put(start, 0d);



		while (!priorityQueue.isEmpty()) {
			Location current = priorityQueue.remove();

			if (current.x == goal.x && current.y == goal.y) {
				return buildPath(start, current);
			}

			for (Location next : graph.getNeighbors(current)) {
				
				double newCost = getCostFromCostSoFar(current) + graph.getCost(current, next);
				
				if (!costSoFar.keySet().stream().anyMatch(e -> e.x == next.x && e.y == next.y) 
						|| newCost < getCostFromCostSoFar(next)) {
					
					if (getKeyFromCostSoFar(next) == null) {
						costSoFar.put(next, newCost);
					} else {
						costSoFar.replace(getKeyFromCostSoFar(next), newCost);
					}

					double priority = newCost + heuristic(next, goal);
					priorityQueue.add(next, priority);
					cameFrom.put(next, current);
					
//					if(cameFrom.size() > 10000) {
//						cameFrom = null;
//						System.gc();
//						return null;
//					}
				}
			}
		}
		return null;
		
	}
	private static Location getKeyFromCostSoFar(Location id) {
		for(Location l : costSoFar.keySet()) {
			if(l.x == id.x && l.y == id.y) {
				return l;
			}
		}
		return null;
	}
	
	private static double getCostFromCostSoFar(Location id) {
		if(costSoFar.keySet().stream().anyMatch(e -> e.x == id.x && e.y == id.y)) {
			 for(Location l: costSoFar.keySet()) {
				 if(l.x == id.x && l.y == id.y) {
					 return costSoFar.get(l);
				 }
			 }
		}
		return 0;
	}
	
	private static LinkedList<Location> buildPath(Location start, Location goal) {
		LinkedList<Location> path = new LinkedList<>();
		Location current = goal;
		while(!current.equals(start)) {
			path.addFirst(current);
			current = cameFrom.get(current);
		}
		return path;
	}
	
	public static LinkedList<Direction> parseToDirection(Location start, LinkedList<Location> path){
		LinkedList<Direction> p = new LinkedList<>();
		Location from = start;
		Location to = path.removeFirst();
		
		Direction dirStart = null;
		if(to.x - from.x > 0) {
			dirStart = Direction.RIGHT;
		}
		else if(to.x - from.x < 0) {
			dirStart = Direction.LEFT;
		}
		
		if(to.y - from.y > 0) {
			dirStart = Direction.DOWN;
		}
		else if(to.y - from.y < 0) {
			dirStart = Direction.UP;
		}
		p.add(dirStart);
		from = to;
		
		while(!path.isEmpty()) {
			to = path.removeFirst();
			Direction dir = null;
			if(to.x - from.x > 0) {
				dir = Direction.RIGHT;
			}
			else if(to.x - from.x < 0) {
				dir = Direction.LEFT;
			}
			
			if(to.y - from.y > 0) {
				dir = Direction.DOWN;
			}
			else if(to.y - from.y < 0) {
				dir = Direction.UP;
			}
			p.add(dir);
			from = to;
		}
//		p.removeLast();
		return p;
	}

	
	public static void main(String[] args) {
		SquareGrid map = new SquareGrid(10, 10);
		Location start = new Location(4, 4);
		LinkedList<Location> path = findPath(map, start, new Location(1, 4));
		System.out.println(parseToDirection(start, path));
		while(!path.isEmpty()) {
			Location l = path.removeFirst();
			System.out.println(l.x + ", " + l.y);
		}
		
		
//		HashMap<Location, Location> m = new HashMap<>();
//		m.put(new Location(1, 1), new Location(3,3));
//		System.out.println(m.containsKey(new Location(1,1)));
//		System.out.println(m.keySet().stream().anyMatch(e -> e.x == 1 && e.y == 1));
//		
//		ArrayList<Location> l = new ArrayList<>();;
//		l.add(new Location(3, 3));
//		System.out.println(l.contains(new Location(3,3)));
	}
}
