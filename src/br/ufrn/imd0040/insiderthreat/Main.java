
package br.ufrn.imd0040.insiderthreat;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ListIterator;

import br.ufrn.imd0040.insiderthreat.gui.Window;

public class Main {
	
	private static final long[] meanHistogram = new long[24];
	private static int totalActiveUsers = 0;

	public static void main(String[] agrs) {
				
		@SuppressWarnings("unused")
		Window graphic_interface = new Window();
		
		Time_Frame time_frame = new Time_Frame("04/01/2010 03:00:00", "04/01/2010 04:00:00");
		Reader reader = new Reader(time_frame);
		
		LinkedList<Profile> profiles = null;
		LinkedList<User> users_list = null;
		LinkedList<Activity> logon_list = null;
		LinkedList<Activity> deviceio_list = null;
		LinkedList<Activity> http_list = null;
		LinkedList<Profile> activeProfiles = null;
				
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
		
		profiles = createProfiles(users_list);		
		System.out.println("Created Profiles.");
		
		assignActivities(time_frame, logon_list, profiles);
		System.out.println("Assigned Logons.");
		
		assignActivities(time_frame, deviceio_list, profiles);
		System.out.println("Assigned Devices I/O.");
		
		assignActivities(time_frame, http_list, profiles);
		System.out.println("Assigned HTTP.");
		
		System.out.println("I always belived in you, you did it. Come on, give me a hug...");
		
		activeProfiles = getActiveProfiles(profiles);
		FinalMeanHistogram();
		PrintHistogram(activeProfiles);
		
		System.out.println("Total de usuários: " + totalActiveUsers);
		
		Profile teste = SearchProfile("DTAA/GML0105", profiles);
		
		System.out.println("Sumido: " + teste.getRoot().getHistogram()[0] + teste.getRoot().getHistogram()[1] + teste.getRoot().getHistogram()[2] + teste.getRoot().getHistogram()[3] );
	}
	
	public static void assignActivities(Time_Frame time_frame, LinkedList<Activity> activities_list, LinkedList<Profile> profiles) {
		  
		ListIterator<Activity> activities_iterator = activities_list.listIterator();
	    
	    while (activities_iterator.hasNext()) {
	    	  
	    	Activity activity = activities_iterator.next();
	    	ListIterator<Profile> profiles_iterator = profiles.listIterator();
	    	boolean found = false;
	  	      
	    	while (profiles_iterator.hasNext() && !found) {
	    		
	    	   	Profile profile = profiles_iterator.next();
	    	    	
	    	   	if (profile.getRoot().getId().equals(activity.getUser())) {
	    	    		
	    	   		profile.addActivity(time_frame, activity);	
	    	   		updateMeanHistogram(activity);
	    	   		
	    	   		found = true;

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
	    	profiles.add(profile);
	    	
	    }
	    
		return profiles;
		
	}
	public static LinkedList<Profile> getActiveProfiles(LinkedList<Profile> profiles) {
		
		LinkedList<Profile> activeProfiles = new LinkedList<Profile>();
				
		ListIterator<Profile> profile_iterator = profiles.listIterator();
	      
	    while (profile_iterator.hasNext()) {
	    	
	    	Profile profile = profile_iterator.next();
	    	
	    	if (profile.getRoot().notEmpty()) {
	    		
	    		totalActiveUsers++;
	    		activeProfiles.add(profile);
	    		
	    		//System.out.println("Usuário: " + profile.getRoot().getHistogram()[0] );
	    		
	    	}
	    	
	    }
	    
		return activeProfiles;
		
	}
	
	public static void PrintHistogram(LinkedList activeProfiles) {
		
		System.out.println("");
		
		for(int i = 0; i < 24; i++) {
			
			System.out.print(" " + i + " ");
		}
		
		System.out.println("");
	
		ListIterator<Profile> profile_iterator = activeProfiles.listIterator();
	      
	    while (profile_iterator.hasNext()) {
	    	
	    	System.out.println("");
	    	
	    	Profile profile = profile_iterator.next();
	    	
	    	for (int j = 0; j < 24 ; j++) {
	    		
	    		System.out.print(" " + profile.getRoot().getHistogram()[j] + " ");
	    		
	    	}
	    	
	    	System.out.print("Usuário: " + profile.getRoot().getId());
	    	
	    }
		
	    System.out.println("");
	    System.out.println("");
	    
	    for(int k = 0; k < 24; k++) {
			
			System.out.print(" " + meanHistogram[k] + " ");
		}
	}
	
	
	public static void ExportProfiles(LinkedList<Profile> activeProfiles){
		
		 //Gerar Arquivo
	}
	
	public static void PrintProfile(LinkedList<Profile> profiles, String id){
		
		 Profile profile = SearchProfile(id, profiles);
		 
		 
		 
	}
	
	public static void SearchOutliers(LinkedList<Profile> activeProfiles) {
	
		//euclidianDistande(profile.histogram[i], meanHistogram[i]);
	}
	
	public static int EuclidianDistance(int a, int b) {
		
		return 0;
	}
	
	public static Profile SearchProfile(String id,LinkedList<Profile> profiles) {
		
		ListIterator<Profile> profile_iterator = profiles.listIterator();
	      
	    while (profile_iterator.hasNext()) {
	    	
	    	Profile profile = profile_iterator.next();
	    	
	    	if (profile.getRoot().getId().equals(id)) {
	    		
	    		 return profile;
	    		
	    	}
	    	
	    }
	    
	    return null;
		
	}
	public static void FinalMeanHistogram() {
		
		for(int i = 0 ; i < 24; i++) {
			
			meanHistogram[i] = meanHistogram[i]/totalActiveUsers;
		
		}
		
	}
	
	public static void updateMeanHistogram(Activity activity) {
		
		Date date = activity.getDate();
		DateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(date));
        
        if (hour >= 0 && hour <= 23) {

        	//System.out.println("Updating meanHistogram at pos " + hour + " that was " + meanHistogram[hour] + " and now is " + ++meanHistogram[hour]);
        	meanHistogram[hour]++;
        }
		
	}

}
