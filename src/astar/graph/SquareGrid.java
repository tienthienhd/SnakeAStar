package astar.graph;

import java.util.ArrayList;
import java.util.HashSet;

public class SquareGrid implements WeightedGraph<Location> {

	public int width, height;
	public HashSet<Location> walls = new HashSet<>();
	public HashSet<Location> forests = new HashSet<>();
	
	public SquareGrid(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean inBounds(Location id) {
		return (0 <= id.x) && (id.x < width) && (0 <= id.y) && (id.y < height);
	}
	
	public boolean passable(Location id) {
		return !walls.stream().anyMatch(e -> e.x == id.x && e.y == id.y);
	}
	
	
	@Override
	public double getCost(Location a, Location b) {
		return forests.stream().anyMatch(e -> e.x == b.x && e.y == b.y) ? 5 : 1;
	}

	@Override
	public ArrayList<Location> getNeighbors(Location id) {
		ArrayList<Location> neighbors = new ArrayList<>();
		
		Location l;
		
		l = new Location(id.x + 1, id.y);
		if(passable(l)) {
			neighbors.add(l);
		}
		
		l = new Location(id.x - 1, id.y);
		if(passable(l)) {
			neighbors.add(l);
		}
		l = new Location(id.x, id.y + 1);
		if(passable(l)) {
			neighbors.add(l);
		}
		l = new Location(id.x, id.y - 1);
		if(passable(l)) {
			neighbors.add(l);
		}
		return neighbors;
	}

}
