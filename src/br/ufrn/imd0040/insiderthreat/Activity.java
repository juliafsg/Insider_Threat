
package br.ufrn.imd0040.insiderthreat;

import java.util.Date;

public abstract class Activity extends Data {

	private String id;
	private String date;
	private String user;
	private String device;
	
	public Activity(String id, String date, String user, String device) {
		
		this.id = id;
		this.date = date;
		this.user = user;
		this.device = device;
		
	}
	
	public String toString() { 
		
        return "\n" + this.id + "(" + this.getClass() + ") on " + this.date + " by " + this.user + " using " + this.device;
        
     } 
	
}
