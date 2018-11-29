
package br.ufrn.imd0040.insiderthreat;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import br.ufrn.imd0040.insiderthreat.gui.Window;

public class Main {
	
	private static final long[] meanHistogram = new long[24];
	private static int totalActiveUsers = 0;
	// Declaração de variaveis
	private static LinkedList<Profile> profiles = null;
	private static LinkedList<User> users_list = null;
	private static LinkedList<Activity> logon_list = null;
	private static LinkedList<Activity> deviceio_list = null;
	private static LinkedList<Activity> http_list = null;
	private static LinkedList<Profile> activeProfiles = null;

	public static void main(String[] agrs) {
		// Receber Comando
		
		// Receber Janela de Tempo
		
		// Receber Id do usuário
		
		// Receber Id de outro usuário
		
		@SuppressWarnings("unused")
		Window graphic_interface = new Window();
		
		Time_Frame time_frame = new Time_Frame("04/01/2010 00:00:00", "04/01/2010 04:00:00");

		// Leitura dos arquivos
		readFiles(time_frame);
		
		// Criação dos perfis
		organizeProfiles(time_frame);
		
		// Buscar os perfis da janela de tempo		
		activeProfiles = getActiveProfiles(profiles);
		
		// Atualizar o histograma final
		FinalMeanHistogram();
		
		// Imprimir histograma geral
		PrintHistogram(activeProfiles);
		
		// Imprimir perfil específico
		
		
		//
		
		//System.out.println("Total de usuários: " + totalActiveUsers);
		//Profile teste = SearchProfile("DTAA/GML0105", profiles);
		//System.out.println("Sumido: " + teste.getRoot().getHistogram()[0] + teste.getRoot().getHistogram()[1] + teste.getRoot().getHistogram()[2] + teste.getRoot().getHistogram()[3] );
	
	}
	
	// Leitura do Arquivo
	public static void readFiles(Time_Frame time_frame) {
		
		Reader reader = new Reader(time_frame);
		
		// Leitura dos arquivos
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

	}
	
	// Criar árvores de perfil
	public static void organizeProfiles(Time_Frame time_frame) {
			
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
	
	// Adicionar atividades nos perfis 
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
	
	// Criar os perfis dos usuários
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
	
	// Procurar perfis no intervalo de tempo
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
	
	// Imprimir histograma geral
public static void PrintHistogram(LinkedList activeProfiles) {
		
		System.out.print("\n          Horas do dia : ");
		
		for (int i = 0; i < 24; i++) {
			
			String numberAsString = String.valueOf(i);
    	    
    		String paddedNumberAsString = "00".substring(numberAsString.length()) + numberAsString;
    		
    		System.out.print(" " + paddedNumberAsString);
    		
		}
		
		System.out.println("");
	
		ListIterator<Profile> profile_iterator = activeProfiles.listIterator();
	      
	    while (profile_iterator.hasNext()) {	    	
	    	
	    	Profile profile = profile_iterator.next();
	    	
	    	System.out.print("\n Usu�rio: " + profile.getRoot().getId() + " : ");
	    	
	    	for (int j = 0; j < 24 ; j++) {
	    		
	    		String numberAsString = String.valueOf(profile.getRoot().getHistogram()[j]);
	    	    
	    		String paddedNumberAsString = "00".substring(numberAsString.length()) + numberAsString;
	    		
	    		System.out.print(" " + paddedNumberAsString);
	    		
	    	}
	    	
	    }
		
	    System.out.print("\n\n      Histograma m�dio : ");
	    
	    for (int k = 0; k < 24; k++) {
			
	    	String numberAsString = String.valueOf(meanHistogram[k]);
    	    
    		String paddedNumberAsString = "00".substring(numberAsString.length()) + numberAsString;
    		
    		System.out.print(" " + paddedNumberAsString);
    		
		}
	    
	    System.out.println("\n");
	    
	}
	
	public static void PrintProfile(String id, LinkedList<Profile> profiles){
		
		 Profile profile = SearchProfile(id, profiles);
		 
		 if (profile != null) {
			 
			 Queue<Node> current_level = new ArrayDeque<Node>();
			 Queue<Node> next_level = new ArrayDeque<Node>();
			 String current_level_str = "";
			 
			 current_level.add(profile.getRoot());
			 
			 while (!current_level.isEmpty()) {
				 
				 Node node = current_level.poll();
				 
				 if (node != null) {
				 
					 current_level_str += node.getId() + "   ";
					 
					 next_level.addAll(node.getChildren());
				 
				 }
				 
				 if (current_level.isEmpty()) {
					 
					 System.out.println(current_level_str);
					 current_level_str = "";
					 
					 current_level = next_level;
					 next_level = new ArrayDeque<Node>(); 
					 
				 }
				 
			 }
		 
		 }		 
		 
	}
	
	// Procurar por um perfil de um usuário específico
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
	
	// Calcular a média no histograma médio
	public static void FinalMeanHistogram() {
		
		for(int i = 0 ; i < 24; i++) {
			
			meanHistogram[i] = meanHistogram[i]/totalActiveUsers;
		
		}
		
	}
	
	// Atualizar contagem do histograma médio
	public static void updateMeanHistogram(Activity activity) {
		
		Date date = activity.getDate();
		DateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(date));
        
        if (hour >= 0 && hour <= 23) {

        	//System.out.println("Updating meanHistogram at pos " + hour + " that was " + meanHistogram[hour] + " and now is " + ++meanHistogram[hour]);
        	meanHistogram[hour]++;
        }
		
	}
	
	// Exportar arquivo com os perfis gerados
	public static void ExportProfiles(LinkedList<Profile> activeProfiles){
		
		 //Gerar Arquivo
	}
	
	// Procurar por outliers
	public static void SearchOutliers(LinkedList<Profile> activeProfiles) {
		
		//euclidianDistande(profile.histogram[i], meanHistogram[i]);
	}
	
	// Calcular distancia euclidiana
	public static int EuclidianDistance(int a, int b) {
		
		return 0;
	}

}
