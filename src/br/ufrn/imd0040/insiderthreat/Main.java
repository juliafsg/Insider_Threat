
package br.ufrn.imd0040.insiderthreat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class Main {
	
	public static void main(String[] agrs) throws IOException {
			
		LinkedList<User> User_List = new LinkedList<User>();

		File file = new File("users.csv");
		CsvReader csvReader = new CsvReader();

		try (CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
		    CsvRow row;
		    
		    row = csvParser.nextRow();
		    
		    while ((row = csvParser.nextRow()) != null) {
		        
		    	//System.out.println("Read line: " + row);
		        User_List.add(new User(row.getField(0), row.getField(1), row.getField(2), row.getField(3), row.getField(4)));
		    
		    }
		    
		}
		
		System.out.println(User_List);
			
	}

}
