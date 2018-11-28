package br.ufrn.imd0040.insiderthreat;

import java.util.ListIterator;

public class Profile {
	
	private Node root;
	
	public Profile(Node root) {
		
		this.root = root;
		
	}
	
	public Node getRoot() {
		
		return this.root;
		
	}
	
	public void addActivity(Time_Frame time_frame, Activity activity) {
		
		Node time_frame_node = this.find_time_frame(time_frame);
		
		if (time_frame_node == null) {
				
			this.getRoot().addChild(new Node(time_frame.toString(), time_frame));
			
		}
		
		
	    
	}
	
	public Node find_time_frame(Time_Frame time_frame) {
		
		ListIterator<Node> nodes_iterator = this.getRoot().getChildren().listIterator();
	      
	    while (nodes_iterator.hasNext()) {
	    	  
	    	Node node = nodes_iterator.next();
	    	
	    	if (node.getData() instanceof Time_Frame && node.getData().equals(time_frame)) {
	    		
	    		return node;
	    		
	    	}
	    	    		    	
	    }
	    
	    return null;
	
	}
	
	public Node find_device(String device_id, Node time_frame_node) {
		
		ListIterator<Node> nodes_iterator = time_frame_node.getChildren().listIterator();
	      
	    while (nodes_iterator.hasNext()) {
	    	  
	    	Node node = nodes_iterator.next();
	    	
	    	if (node.getId() == device_id) {
	    		
	    		return node;
	    		
	    	}
	    	    		    	
	    }
	    
	    return null;
	
	}

}
