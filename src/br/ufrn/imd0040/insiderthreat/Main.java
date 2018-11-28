
package br.ufrn.imd0040.insiderthreat;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
	
	public static void main(String[] agrs) {
		
		Reader reader = new Reader();
		
		LinkedList<Profile> profiles = null;
		LinkedList<User> users_list = null;
		LinkedList<Activity> logon_list = null;
		LinkedList<Activity> deviceio_list = null;
		LinkedList<Activity> http_list = null;
		
		try {
			
			users_list = reader.read_users("files/users.csv");
			System.out.println("Read Users.");
			logon_list = reader.read_activities("files/logon.csv");
			System.out.println("Read Logons.");
			deviceio_list = reader.read_activities("files/device.csv");
			System.out.println("Read Devices I/O.");
			http_list = reader.read_activities("files/http.csv");
			System.out.println("Read HTTP.");
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
    
		Time_Frame time_frame = new Time_Frame("01/04/2010 00:00:00", "07/29/2010 23:59:00");
		
		profiles = createProfiles(users_list);
		
		System.out.println("Created Profiles.");
		assignActivities(time_frame, logon_list, profiles);
		System.out.println("Assigned Logons.");
		assignActivities(time_frame, deviceio_list, profiles);
		System.out.println("Assigned Devices I/O.");
		assignActivities(time_frame, http_list, profiles);
		System.out.println("Assigned HTTP.");
		
		System.out.println("I always belived in you, you did it. Come on, give me a hug...");
			
	}
	
	public static void assignActivities(Time_Frame time_frame, LinkedList<Activity> activities_list, LinkedList<Profile> profiles) {
		  
		ListIterator<Activity> activities_iterator = activities_list.listIterator();
	      
	    while (activities_iterator.hasNext()) {
	    	  
	    	Activity activity = activities_iterator.next();
	    	
	    	if (activity.getDate().compareTo(time_frame.getEnd()) <= 0 && activity.getDate().compareTo(time_frame.getBegin()) >= 0) {
	    	
	    		ListIterator<Profile> profiles_iterator = profiles.listIterator();
	  	      
	    	    while (profiles_iterator.hasNext()) {
	    		
	    	    	Profile profile = profiles_iterator.next();
	    	    	    	    	
	    	    	if (profile.getRoot().getId() == activity.getUser()) {
	    	    		
	    	    		System.out.println(profile.getRoot().getId() + " = " + activity.getUser());
	    	    		profile.addActivity(time_frame, activity);
	    	    		
	    	    	}
	    	    	
	    	    }
	    	    
	    	}
	    	
	    }
		
	}
	
	public static LinkedList<Profile> createProfiles(LinkedList<User> users_list) {
		
		LinkedList<Profile> profiles = new LinkedList<Profile>();
		
		ListIterator<User> users_iterator = users_list.listIterator();
	      
	    while (users_iterator.hasNext()) {
	    	  
	    	User user = users_iterator.next();
	 
	    	Profile profile = new Profile(new Node("DTAA/" + user.getId(), user));
	    	//profile.getRoot().addChild(new Node(time_frame.toString(), time_frame));
	    	profiles.add(profile);
	    	
	    }
	    
		return profiles;
		
	}

}
