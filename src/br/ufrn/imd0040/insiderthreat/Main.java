
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
		
		ListIterator<User> it = Users_List.listIterator();
	      
	    while (it.hasNext()) {
	    	  
	    	User user = it.next();
	         
	    	Profiles.add(new Profile(new Node(user.getId(), user)));
	         
	    }
		
		//System.out.println(Users_List);
		
		System.out.println("GOT IT");
			
	}

}
