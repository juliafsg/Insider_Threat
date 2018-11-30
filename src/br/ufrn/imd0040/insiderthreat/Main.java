
package br.ufrn.imd0040.insiderthreat;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Collections;
import java.util.Vector;

import br.ufrn.imd0040.insiderthreat.gui.Window;

public class Main {
	
	// Declara�o de variaveis
	private static final int[] meanHistogram = new int[24];
	private static int totalActiveUsers = 0;
	private static LinkedList<Profile> profiles = null;
	private static LinkedList<User> users_list = null;
	private static LinkedList<Activity> logon_list = null;
	private static LinkedList<Activity> deviceio_list = null;
	private static LinkedList<Activity> http_list = null;
	private static LinkedList<Profile> activeProfiles = null;

	public static void main(String[] agrs) {
		// Receber Comando
		
		// Receber Janela de Tempo
		
		// Receber Id do usu�rio
		
		// Receber Id de outro usu�rio
		
		@SuppressWarnings("unused")
		Window graphic_interface = new Window();
		
		Time_Frame time_frame = new Time_Frame("04/01/2010 00:00:00", "04/01/2010 23:59:59");
		String id = "DTAA/ELD1000";
		
		//Time_Frame time_frame = new Time_Frame(graphic_interface.getTimeBegin(), graphic_interface.getTimeEnd());
		//String id = graphic_interface.getUserId();


		// Leitura dos arquivos
		readFiles(time_frame);
		
		// Cria�o dos perfis
		organizeProfiles(time_frame);
		
		// Buscar os perfis da janela de tempo		
		activeProfiles = getActiveProfiles(profiles);
		
		// Atualizar o histograma final
		FinalMeanHistogram();
		
		// Imprimir histograma geral
		//PrintHistogram(activeProfiles);
		
		// Imprimir perfil espec�fico
		PrintProfile(id, activeProfiles);
		
		// Procurar Outliers
		SearchOutliers(activeProfiles);
		
		// Exportar histograma geral para arquivo
		exportHistogram(activeProfiles, "files/histogram.txt");
		
		System.out.println("\n Total de usu�rios: " + totalActiveUsers);
		
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
	
	// Criar �rvores de perfil
	public static void organizeProfiles(Time_Frame time_frame) {
			
		profiles = createProfiles(users_list);		
		System.out.println("Created Profiles.");
		
		assignActivities(time_frame, logon_list, profiles);
		System.out.println("Assigned Logons.");
		
		assignActivities(time_frame, deviceio_list, profiles);
		System.out.println("Assigned Devices I/O.");
		
		assignActivities(time_frame, http_list, profiles);
		System.out.println("Assigned HTTP.");
		
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
	
	// Criar os perfis dos usu�rios
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
	    		
	    		//System.out.println("Usu�rio: " + profile.getRoot().getHistogram()[0] );
	    		
	    	}
	    	
	    }
	    
		return activeProfiles;
		
	}
	
	// Imprimir histograma geral
	public static void PrintHistogram(LinkedList activeProfiles) {
		
		System.out.print("\n          Horas do dia : ");
		
		for (int i = 0; i < 24; i++) {
			
			String numberAsString = String.valueOf(i);
    	    
    		String paddedNumberAsString = "000".substring(numberAsString.length()) + numberAsString;
    		
    		System.out.print(" " + paddedNumberAsString);
    		
		}
		
		System.out.println("");
	
		ListIterator<Profile> profile_iterator = activeProfiles.listIterator();
	      
	    while (profile_iterator.hasNext()) {	    	
	    	
	    	Profile profile = profile_iterator.next();
	    	
	    	System.out.print("\n Usu�rio: " + profile.getRoot().getId() + " : ");
	    	
	    	for (int j = 0; j < 24 ; j++) {
	    		
	    		String numberAsString = String.valueOf(profile.getRoot().getHistogram()[j]);
	    	    
	    		String paddedNumberAsString = "000".substring(numberAsString.length()) + numberAsString;
	    		
	    		System.out.print(" " + paddedNumberAsString);
	    		
	    	}
	    	
	    }
		
	    System.out.print("\n\n      Histograma m�dio : ");
	    
	    for (int k = 0; k < 24; k++) {
			
	    	String numberAsString = String.valueOf(meanHistogram[k]);
    	    
    		String paddedNumberAsString = "000".substring(numberAsString.length()) + numberAsString;
    		
    		System.out.print(" " + paddedNumberAsString);
    		
		}
	    
	    System.out.println("\n");
	    
	}
	
public static void exportHistogram(LinkedList activeProfiles, String file_name) {
		
		String file_content = "";
		
		file_content += "\n          Horas do dia : ";
		
		for (int i = 0; i < 24; i++) {
			
			String numberAsString = String.valueOf(i);
    	    
    		String paddedNumberAsString = "000".substring(numberAsString.length()) + numberAsString;
    		
    		file_content += " " + paddedNumberAsString;
    		
		}
		
		file_content += "\n";
	
		ListIterator<Profile> profile_iterator = activeProfiles.listIterator();
	      
	    while (profile_iterator.hasNext()) {	    	
	    	
	    	Profile profile = profile_iterator.next();
	    	
	    	file_content += "\n Usu�rio: " + profile.getRoot().getId() + " : ";
	    	
	    	for (int j = 0; j < 24 ; j++) {
	    		
	    		String numberAsString = String.valueOf(profile.getRoot().getHistogram()[j]);
	    	    
	    		String paddedNumberAsString = "000".substring(numberAsString.length()) + numberAsString;
	    		
	    		file_content += " " + paddedNumberAsString;
	    		
	    	}
	    	
	    }
		
	    file_content += "\n\n      Histograma m�dio : ";
	    
	    for (int k = 0; k < 24; k++) {
			
	    	String numberAsString = String.valueOf(meanHistogram[k]);
    	    
    		String paddedNumberAsString = "000".substring(numberAsString.length()) + numberAsString;
    		
    		file_content += " " + paddedNumberAsString;
    		
		}
	    
	    
	    try (PrintWriter out = new PrintWriter(file_name)) {
	    
	    	out.println(file_content);
	    
	    } catch (FileNotFoundException e) {
		
	    	e.printStackTrace();
		
	    }
	    
	}


	// Imprimir um perfil
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
		 
		 else{
			 
			 System.out.println("N�o existem atividades realizadas por esse usu�rio no intervalo de tempo fornecido.");
		 }
		 
	}
	
	// Procurar por um perfil de um usu�rio espec�fico
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
	
	// Calcular a m�dia no histograma m�dio
	public static void FinalMeanHistogram() {
		
		for(int i = 0 ; i < 24; i++) {
			
			meanHistogram[i] = meanHistogram[i]/totalActiveUsers;
		
		}
		
	}
	
	// Atualizar contagem do histograma m�dio
	public static void updateMeanHistogram(Activity activity) {
		
		Date date = activity.getDate();
		DateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(date));
        
        if (hour >= 0 && hour <= 23) {

        	//System.out.println("Updating meanHistogram at pos " + hour + " that was " + meanHistogram[hour] + " and now is " + ++meanHistogram[hour]);
        	meanHistogram[hour]++;
        }
		
	}
		
	// Procurar por outliers
	public static void SearchOutliers(LinkedList<Profile> activeProfiles) {
		
		int distance;
		int[] limits = new int[2];
		boolean found = false;
		
		Vector allDistances = new Vector();
		
		ListIterator<Profile> profile_iterator = activeProfiles.listIterator();
	      
	    while (profile_iterator.hasNext()) {
	    	
	    	Profile profile = profile_iterator.next();
	    		
	    	distance = EuclidianDistance(profile.getRoot().getHistogram(), meanHistogram);
	    	
	    	profile.setEuclidian_distance(distance);
	    	
	    	allDistances.add(distance);
	    }
	    
	    // Ordenar vetor de distancias
	    Collections.sort(allDistances);
	    
	    System.out.println("Distancias" + allDistances);
	    
	    // Calcular os limites dos Quartis
	    if(allDistances.size() >= 5) {
	    	
	    	limits = QuartisLimits(allDistances);
	    	
	    	// Imprimir outliers
		    ListIterator<Profile> profile_iterator2 = activeProfiles.listIterator();
		      
		    while (profile_iterator2.hasNext()) {
		    	
		    	Profile profile2 = profile_iterator2.next();
		    		
		    	if(profile2.getEuclidian_distance() < limits[0] || profile2.getEuclidian_distance() > limits[1]) {
		    		
		    		System.out.println(" Encontramos um Outlier e � o usu�rio: " + profile2.getRoot().getId() + " com distancia euclidiana igual a: " + profile2.getEuclidian_distance());
		    		
		    		found = true;
		    		
		    		//PrintProfile(profile2.getRoot().getId(), activeProfiles);
		    	}
		    	
		    }
	    
	    }
	    
	    else {
	    	
	    	System.out.println(" N�o foi poss�vel detectar os outliers, pois o conjunto de dados � muito pequeno.");
	    }
	    
	    if (!found) {
	    	    		
	    		System.out.println(" N�o existe nenhum outlier, ou seja, nenhum usu�rio aparentou ser uma amea�a!");
	    	
	    }
				
	}
	
	// Retorna os limites para os outliers
	public static int [] QuartisLimits(Vector vector) {
		
		int median, first_median, second_median, mean, smaller, bigger, smaller_limit, bigger_limit;
		double iqr;
		
		int[] limits = new int[2];
		
		Vector first_quartil = new Vector();
		Vector second_quartil = new Vector();
		
		smaller = (int) vector.firstElement();
		bigger = (int) vector.lastElement();
		mean = (smaller+bigger)/2;
		median = FindMedian(vector);
		
		
		ListIterator vector_iterator = vector.listIterator();
		
		// Criar quartis 
		if(vector.size()%2 == 0) {
			
			for(int i = 0; i < vector.size(); i++) {
				
				if( i+1 <=  vector.size()/2 ){
					
					first_quartil.add(vector.get(i));
					
				}
				
				else if(i+1 > vector.size()/2) {
					
					second_quartil.add(vector.get(i));
				}
			}
			
		}
		else {
			
			for(int i = 0; i < vector.size(); i++) {
				
				if( i+1 <= (int) vector.size()/2 ){
					
					first_quartil.add(vector.get(i));
					
				}
				
				else if(i+1 > (int) (vector.size()/2) + 1) {
					
					second_quartil.add(vector.get(i));
				}
			}
			
		}
		
		System.out.println("Este � o primeiro Quartil" + first_quartil);
		System.out.println("Este � o segundo Quartil" + second_quartil);
		
		first_median = FindMedian(first_quartil);
		second_median = FindMedian(second_quartil);
		
		iqr = second_median - first_median;
		
		smaller_limit = (int) (first_median - (iqr*1.5));
		bigger_limit = (int) (second_median + (iqr*1.5));
		
		limits[0] = smaller_limit;
		limits[1] = bigger_limit;
		
		System.out.println("IQR: " + iqr);
		
		System.out.println("Limite inferior:" + limits[0]);
		System.out.println("Limite superior: " + limits[1]);
		
		return limits;
		
	}
	
	public static int FindMedian(Vector vector) {
		int median;
		
		if(vector.size()%2 !=0 ) {
			median = (int) vector.elementAt((vector.size()/2) + 1);
		}
		
		else {
			median = ((int) vector.elementAt(vector.size()/2) + (int) vector.elementAt((vector.size()/2)+1))/2;
			
		}
		
		return median;
	}
	
	// Calcular distancia euclidiana
	public static int EuclidianDistance(int [] a, int [] b) {
		
		int distance = 0;
		int aux = 0;
		
		for(int r = 0; r < 24; r++) {
			
			aux += (a[r] - b[r])^2;
		}
		
		distance = (int) Math.sqrt(aux);
		
		return distance;
	}	 
}
