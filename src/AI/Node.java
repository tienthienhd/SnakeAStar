package ai;

public class Node {

	public int x,y;
	Node parent;
	double costFromStart;
	double costEstimateToGoal;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double getCost() {
		return this.costEstimateToGoal + this.costFromStart;
	}
	
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
