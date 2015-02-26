package alda.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;



/*
 * @author Hannes Hornvall haho2206 hornwall.hannes@gmail.com
 * @author Lotta Månsson loma3374 Mansson.lotta@gmail.com
 */

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	private ArrayList<Node<T>> nodes = new ArrayList<Node<T>>(); 
	@Override
	public int getNumberOfNodes() {
		return nodes.size();
	}

	@Override
	public int getNumberOfEdges() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean add(T newNode) {
		Node<T> n = new Node<T>(newNode);
		if(!nodes.contains(n)){
			nodes.add(new Node<T>(newNode));
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if(cost <= 0){
			return false;
		}
		
		Node<T> from = findNode(node1);
		Node<T> to = findNode(node2);
		
		if(!isConnected(node1, node2)){
			if(from != null && to != null){
				from.connect(to, cost);
				to.connect(from, cost);
				return true;
			}
		}else{
			findConnection(node1, node2).weight = cost;
			findConnection(node2, node1).weight = cost;
			return true;
		}
		return false;
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		Node<T> from = findNode(node1);
		Node<T> to = findNode(node2);
		
		if(from == null || to == null)
			return false;
		
		for (Edge<T> e: from.connections) {
			if(e.target.equals(to))
				return true;
		}
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		if(!isConnected(node1, node2))
			return -1;
		else{
			Node<T> from = findNode(node1);
			Node<T> to = findNode(node2);
			
			for(Edge<T> e: from.connections){
				if(e.target.equals(to)){
					return e.weight;
				}
			}
		}
		return -1;
	}
	
	private Node<T> findNode(T item){
		for (Node<T> n: nodes){
			if(n.data.equals(item)){
				return n;
			}
		}
		return null;
	}
	
	private Edge<T> findConnection(T node1, T node2){
		Node<T> from = findNode(node1);
	
		for(Edge<T> e: from.connections){
			if(e.target.data.equals(node2)){
				return e;
			}
		}
		return null;
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		Stack<SimpleEntry<Node<T>, List<T>>> stack = new Stack<SimpleEntry<Node<T>, List<T>>>();
		Node<T> s = findNode(start);
		Node<T> e = findNode(end);
		
		SimpleEntry<Node<T>, List<T>> en = new SimpleEntry<Node<T>, List<T>>(s, new ArrayList<T>());
		
		stack.add(en);
		
		ArrayList<Node<T>> searchedNodes = new ArrayList<Node<T>>();
		SimpleEntry<Node<T>, List<T>> current = null;
		
		while(!stack.empty()){
			current = stack.pop();
			if(!searchedNodes.contains(current.getKey())){
				if(current.getKey().equals(e)){
					current.getValue().add(current.getKey().data);
					return current.getValue();
				}else{
					searchedNodes.add(current.getKey());
					
					List<T> clone = new ArrayList<T>(current.getValue());

					clone.add(current.getKey().data);
					for(Edge<T> edge: current.getKey().connections){
						if(!searchedNodes.contains(edge.target)){
							stack.add(new SimpleEntry<Node<T>, List<T>>(edge.target, clone));
						}
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		LinkedBlockingQueue<SimpleEntry<Node<T>, List<T>>> searchQueue 
		= new LinkedBlockingQueue<SimpleEntry<Node<T>, List<T>>>();
		Node<T> s = findNode(start);
		Node<T> e = findNode(end);
		
		SimpleEntry<Node<T>, List<T>> en = new SimpleEntry<Node<T>, List<T>>(s, new ArrayList<T>());
		
		searchQueue.add(en);
		
		ArrayList<Node<T>> searchedNodes = new ArrayList<Node<T>>();
		SimpleEntry<Node<T>, List<T>> current = null;
		
		while (!searchQueue.isEmpty()) {
			current = searchQueue.remove();
			if(!searchedNodes.contains(current.getKey())){
				if(current.getKey().equals(e)){
					current.getValue().add(current.getKey().data);
					return current.getValue();
				}else{
					searchedNodes.add(current.getKey());
					
					List<T> clone = new ArrayList<T>(current.getValue());

					clone.add(current.getKey().data);
					for(Edge<T> edge: current.getKey().connections){
						if(!searchedNodes.contains(edge.target)){
							searchQueue.add(new SimpleEntry<Node<T>, List<T>>(edge.target, clone));
						}
					}
				}
			}
		}	
		
		return null;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		MyUndirectedGraph<T> minimumSpanning = new MyUndirectedGraph<T>();
		ArrayList<Node<T>> visited = new ArrayList<Node<T>>();
		
		Node<T> current = nodes.get(0);
		minimumSpanning.add(current.data);
		visited.add(current);
		
		while(nodes.size() != visited.size()){
			
			Edge<T> leastEdge = null;
			Node<T> selectedNode = null;
			for(Node<T> n: visited){
				for(Edge<T> e: n.connections){
					if((leastEdge == null || leastEdge.weight > e.weight) && !visited.contains(e.target)){
						leastEdge = e;
						selectedNode = n;
					}
				}
			}
			if(selectedNode != null){
				minimumSpanning.add(leastEdge.target.data);
				minimumSpanning.connect(leastEdge.target.data, selectedNode.data, leastEdge.weight);
				visited.add(leastEdge.target);
			} else
				current = null;
			
		}
		
		return minimumSpanning;
	}

}
