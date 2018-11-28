
package br.ufrn.imd0040.insiderthreat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Activity extends Data {

	private String id;
	private Date date;
	private String user;
	private String device;
	
	public Activity(String id, String date, String user, String device) {
		
		this.id = id;
		
		try {
			
			this.date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(date);
		
		} catch (ParseException e) {
		
			e.printStackTrace();
		
		}
		
		this.user = user;
		this.device = device;
		
	}
	
	public String toString() { 
		
        return "\n" + this.id + "(" + this.getClass() + ") on " + this.date + " by " + this.user + " using " + this.device;
        
     }
	
	public String getId() {
		
		return this.id;
		
	}
	
	public void setUser(String user) {
		
		this.user = user;
		
	}

	public String getUser() {
		
		return this.user;
		
	}

	public Date getDate() {
		
		return this.date;
		
	}
		
	public String getDevice() {
		
		return this.device;
		
	}
	
}
