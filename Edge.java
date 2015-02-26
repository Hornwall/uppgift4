package alda.graph;

/*
 * @author Hannes Hornvall haho2206 hornwall.hannes@gmail.com
 * @author Lotta Månsson loma3374 Mansson.lotta@gmail.com
 */

public class Edge<T> {
	Node<T> target;
	int weight;
	
	public Edge(Node<T> target, int weight){
		this.target = target;
		this.weight = weight;
	}
}
