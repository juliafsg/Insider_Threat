package br.ufrn.imd0040.insiderthreat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ListIterator;

public class Profile {
	
	private Node root;
	//private int acess;
	private int euclidian_distance;

	public Profile(Node root) {
		
		this.root = root;
		
	}
	
	public Node getRoot() {
		
		return this.root;
		
	}
	
	public void addActivity(Time_Frame time_frame, Activity activity) {
		
		DateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(activity.getDate()));
		
        root.increaseHistogram(hour);
        
       /* int [] histogram = root.getHistogram();
        histogram[hour]++;
        root.setHistogram(histogram);
        */
		//acess++;	
		//System.out.println("Houve uma nova atividade, total de acessos: " + acess + " usuário :" + root.getId());
		
		Node time_frame_node = this.find_time_frame(time_frame);
		
		if (time_frame_node == null) {
			
			time_frame_node = new Node(time_frame.toString(), time_frame);
						
			this.getRoot().addChild(time_frame_node);
			
		}
		
		Node device_node = this.find_device(activity.getDevice(), time_frame_node);
		
		if (device_node == null) {
			
			device_node = new Node(activity.getDevice(), null);
						
			time_frame_node.addChild(device_node);
			
		}
		
		if (device_node.getChildren().isEmpty()) {
			
			device_node.addChild(new Node("Logon", null));
			device_node.addChild(new Node("DeviceIO", null));
			device_node.addChild(new Node("HTTP", null));
			
		}
		
	
		if (activity instanceof Logon) {
			
			if (device_node.getChildren().get(0).getChildren().isEmpty())  {
				
				device_node.getChildren().get(0).addChild(new Node("Logon", null));
				device_node.getChildren().get(0).addChild(new Node("Logoff", null));
				
			}
			
			if (((Logon) activity).getAction() == "Logon") {
				
				device_node.getChildren().get(0).getChildren().get(0).updateHistogram(hour);
				
			}
			
			else if (((Logon) activity).getAction() == "Logoff") {
				
				device_node.getChildren().get(0).getChildren().get(1).updateHistogram(hour);
				
			}
			
		}
		
		else if (activity instanceof DeviceIO) {
			
			if (device_node.getChildren().get(1).getChildren().isEmpty())  {
				
				device_node.getChildren().get(1).addChild(new Node("Connect", null));
				device_node.getChildren().get(1).addChild(new Node("Disconnect", null));
				
			}
			
			if (((DeviceIO) activity).getAction() == "Connect") {
				
				device_node.getChildren().get(1).getChildren().get(0).updateHistogram(hour);
				
			}
			
			else if (((DeviceIO) activity).getAction() == "Disconnect") {
				
				device_node.getChildren().get(1).getChildren().get(1).updateHistogram(hour);
				
			}
			
		}
		
		else if (activity instanceof HTTP) {
			
			Node url_node = this.find_url(((HTTP) activity).getUrl(), device_node.getChildren().get(2));
			
			if (url_node == null) {
				
				Node node = new Node(((HTTP) activity).getUrl(), null);
				device_node.getChildren().get(2).addChild(node);
				node.updateHistogram(hour);
				
			}
			
			else {
								
				url_node.updateHistogram(hour);
				
			}
			
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
	    	
	    	if (node.getId().equals(device_id)) {
	    		
	    		return node;
	    		
	    	}
	    	    		    	
	    }
	    
	    return null;
	
	}
	
	public Node find_url(String url, Node http_node) {
		
		ListIterator<Node> nodes_iterator = http_node.getChildren().listIterator();
	      
	    while (nodes_iterator.hasNext()) {
	    	  
	    	Node node = nodes_iterator.next();
	    	
	    	if (node.getId().equals(url)) {
	    		
	    		return node;
	    		
	    	}
	    	    		    	
	    }
	    
	    return null;
	
	}
	
	public int getEuclidian_distance() {
		return euclidian_distance;
	}

	public void setEuclidian_distance(int euclidian_distance) {
		this.euclidian_distance = euclidian_distance;
	}
}
