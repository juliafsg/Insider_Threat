
package br.ufrn.imd0040.insiderthreat;

import java.util.Date;

public class Activity {

	private String id;
	private Date date;
	private String domain;
	private String user;
	private String device;
	
	public Activity(String id, Date date, String domain, String user, String device) {
		
		this.id = id;
		this.date = date;
		this.domain = domain;
		this.user = user;
		this.device = device;
		
	}
	
}
