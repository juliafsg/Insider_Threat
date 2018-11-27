package br.ufrn.imd0040.insiderthreat;

import java.util.LinkedList;

public class Node {
	
	private Data data;
	private String id;
	private int histogram[];
	private LinkedList<Node> below;
	
	public Node(String id, Data data) {
	
		this.id = id;
		this.data = data;
		histogram = new int[24];
		below = new LinkedList<Node>();
		
	}

}
