package br.ufrn.imd0040.insiderthreat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import br.ufrn.imd0040.insiderthreat.User;
import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class Reader {
	
	public Reader() {
		
	}
	
	public LinkedList<User> read_users(String filename) throws IOException {
		
		LinkedList<User> Users_List = new LinkedList<User>();
		
		File file = new File(filename);
		CsvReader csvReader = new CsvReader();

		try (CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
		    
			CsvRow row;
		    row = csvParser.nextRow();
		    
		    while ((row = csvParser.nextRow()) != null) {
		        
		        Users_List.add(new User(row.getField(0), row.getField(1), row.getField(2), row.getField(3), row.getField(4)));
		    
		    }
		    
		}
		
		catch (IOException e) {
			
			throw new IOException("Não foi possível ler o arquivo " + filename);
			
		}
		
		return Users_List;
		
	}
	
public LinkedList<Activity> read_activities(String filename) throws IOException {
		
		LinkedList<Activity> Activities_List = new LinkedList<Activity>();
		
		File file = new File(filename);
		CsvReader csvReader = new CsvReader();

		try (CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
		    
			CsvRow row;
		    row = csvParser.nextRow();
		    
		    while ((row = csvParser.nextRow()) != null) {
		    	
		    	if (filename == "files/logon.csv") { 
		    		
		    		Activities_List.add(new Logon(row.getField(0), row.getField(1), row.getField(2), row.getField(3), row.getField(4)));
			    	
		    	}
		    	
		    	else if (filename == "files/device.csv") {
		        
		    		Activities_List.add(new DeviceIO(row.getField(0), row.getField(1), row.getField(2), row.getField(3), row.getField(4)));
		    	
		    	}
		    	
		    	else if (filename == "files/http.csv") {
		    		
		    		Activities_List.add(new HTTP(row.getField(0), row.getField(1), row.getField(2), row.getField(3), row.getField(4)));
			    	
		    	}
		    	
		    }
		    
		}
		
		catch (IOException e) {
			
			throw new IOException("Não foi possível ler o arquivo " + filename);
			
		}
		
		return Activities_List;
		
	}
	

}
