package alda.graph;

import java.util.ArrayList;

/*
 * @author Hannes Hornvall haho2206 hornwall.hannes@gmail.com
 * @author Lotta Månsson loma3374 Mansson.lotta@gmail.com
 */

public class Node<T> {
	T data;
	ArrayList<Edge<T>> connections;

	public Node(T data) {
		this.data = data;
		this.connections = new ArrayList<Edge<T>>();
	}

	public void connect(Node<T> target, int weight) {
		connections.add(new Edge<T>(target, weight));
	}

	public void disconnect(T target) {
		for (Edge<T> e : connections) {
			if (e.target.data.equals(target)) {
				connections.remove(e);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Node)) {
			return false;
		}
		Node n = (Node) obj;
		return n.data.equals(this.data);
	}
}
