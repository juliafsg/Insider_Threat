package br.ufrn.imd0040.insiderthreat;

import java.util.LinkedList;

public class Node {
	
	private Data data;
	private String id;
	private int histogram[];
	private LinkedList<Node> children;
	
	public Node(String id, Data data) {
	
		this.id = id;
		this.data = data;
		histogram = new int[24];
		children = new LinkedList<Node>();
		
	}
	
	public void addChild(Node node) {
		
		children.add(node);
		
	}
	
	public LinkedList<Node> getChildren() {
		
		return children;
		
	}
	
	public Data getData() {
		
		return data;
		
	}

}
