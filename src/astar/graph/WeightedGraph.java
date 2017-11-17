package astar.graph;

import java.util.ArrayList;

public interface WeightedGraph<L> {
	double getCost(Location a, Location b);
	ArrayList<Location> getNeighbors(Location id);
}
