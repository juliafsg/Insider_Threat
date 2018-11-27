
package br.ufrn.imd0040.insiderthreat;

import java.io.*;
import java.util.*;


public class Main {
	
	public static void main(String[] agrs) {
		
		Reader reader = new Reader();
		
		LinkedList<Profile> Profiles = new LinkedList<Profile>();
		LinkedList<User> Users_List = null;
		LinkedList<Activity> Logon_List = null;
		//LinkedList<Activity> DeviceIO_List = null;
		//LinkedList<Activity> HTTP_List = null;
		
		try {
			
			Users_List = reader.read_users("files/users.csv");
			Logon_List = reader.read_activities("files/logon.csv");
			//DeviceIO_List = reader.read_activities("files/device.csv");
			//HTTP_List = reader.read_activities("files/http.csv");
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		ListIterator<User> it1 = Users_List.listIterator();
	      
	    while (it1.hasNext()) {
	    	  
	    	User user = it1.next();
	         
	    	Profiles.add(new Profile(new Node(user.getId(), user)));
	         
	    }
	    
	    Profiles.get(1).getRoot().addChild(new Node("LOGON", null));
	    Profiles.get(1).getRoot().addChild(new Node("DEVICEIO", null));
	    Profiles.get(1).getRoot().addChild(new Node("HTTP", null));
	    
	    ListIterator<Activity> it2 = Logon_List.listIterator();
	      
	    while (it2.hasNext()) {
	    	  
	    	Activity activity = it2.next();
	    	
	    	ListIterator<Profile> it3 = Profiles.listIterator();
		      
		    while (it3.hasNext()) {
		    	  
		    	Profile profile = it3.next();

		    	User user2 = (User) profile.getRoot().getData();
		    			
		    	if ("DTAA/"+user2.getId() == activity.getUser()) {
		    		
		    		profile.getRoot().addChild(new Node(activity.getClass().toString(), activity));
		    		
		    	}
		         
		    }
	         
	         
	    }
		
		//System.out.println(Users_List);
		
		System.out.println("GOT IT");
			
	}

}
