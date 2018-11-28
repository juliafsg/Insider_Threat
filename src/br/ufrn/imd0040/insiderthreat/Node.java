package br.ufrn.imd0040.insiderthreat;

import java.util.LinkedList;

public class Node {
	
	private Data data;
	private String id;
	private int histogram[];
	private LinkedList<Node> children;
	private Node parent;
	
	public Node(String id, Data data) {
	
		this.id = id;
		this.data = data;
		this.histogram = new int[24];
		this.children = new LinkedList<Node>();
		this.parent = null;
		
	}
	
	public void setParent(Node node) {
		
		this.parent = parent;
		
	}
	
	public void addChild(Node node) {
		
		node.setParent(this);
		this.children.add(node);
		
	}
	
	public LinkedList<Node> getChildren() {
		
		return this.children;
		
	}
	
	public Data getData() {
		
		return this.data;
		
	}
	
	public String getId() {
		
		return this.id;
		
	}

}
