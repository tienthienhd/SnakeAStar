package astar;

import java.util.Comparator;
import java.util.PriorityQueue;


public class PriorityQueueMyshelf<T> {
	
	private PriorityQueue<Node> queue;
	
	public PriorityQueueMyshelf() {
		Comparator<Node> comp = new Comparator<Node>() {
			
			@Override
			public int compare(Node o1, Node o2) {
				double d = o1.priority - o2.priority;
				return (d > 0) ? 1 : (d < 0) ? -1 : 0;
			}
		};
		
		queue = new PriorityQueue<>(comp);
	}
	
	public int size() {
		return queue.size();
	}
	
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
	
	public void add(T item, double priority) {
		Node<T> node = new Node<T>(item, priority);
		queue.add(node);
	}
	
	public T remove() {
		Node node = queue.remove();
		return (T) node.item;
	}
	

	private static class Node<E> {
		private E item;
		private Double priority;
		
		public Node(E item, Double priority){
			this.item = item;
			this.priority = priority;
		}
	}
}
