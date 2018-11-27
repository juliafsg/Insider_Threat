
package br.ufrn.imd0040.insiderthreat;

import java.util.Date;

public class Logon extends Activity {
	
	private String action;

	public Logon(String id, Date date, String domain, String user, String device, String action) {
		
		super(id, date, domain, user, device);
		
		this.action = action;
		
	}
	
	
}
