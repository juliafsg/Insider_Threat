
package br.ufrn.imd0040.insiderthreat;

import java.util.Date;

public class Logon extends Activity {
	
	private String action;

	public Logon(String id, Date date, String user, String device, String action) {
		
		super(id, date, user, device);
		
		this.action = action;
		
	}
	
	public String getAction() {
		
		return this.action;
		
	}
	
	
}
