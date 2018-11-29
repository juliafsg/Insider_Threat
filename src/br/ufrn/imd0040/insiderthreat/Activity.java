
package br.ufrn.imd0040.insiderthreat;

import java.util.Date;

public abstract class Activity extends Data {

	private String id;
	private Date date;
	private String user;
	private String device;
	
	public Activity(String id, Date date, String user, String device) {
		
		this.id = id;
		this.date = date;		
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
