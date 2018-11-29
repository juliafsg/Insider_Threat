package br.ufrn.imd0040.insiderthreat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Node {
	
	private Data data;
	private String id;
	@SuppressWarnings("unused")
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
	
	public Node getParent() {
		
		return this.parent;
		
	}
	
	public void setParent(Node node) {
		
		this.parent = node;
		
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
	
	public int[] getHistogram() {
		
		return this.histogram;
		
	}
	
	public void setHistogram(int[] histogram) {
		
		this.histogram = histogram;
		
	}
	
	public void updateHistogram(Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(date));
		
		if (hour >= 0 && hour <= 23) {
			
			Node current_node = this;
			
			while (current_node != null) {
				
				current_node.histogram[hour]++;
				
				current_node = current_node.getParent();
				
			}
		
		}
	
	}

}
