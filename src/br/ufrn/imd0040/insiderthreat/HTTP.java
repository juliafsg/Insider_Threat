
package br.ufrn.imd0040.insiderthreat;

import java.util.Date;

public class HTTP extends Activity {
	
	private String url;

	public HTTP(String id, Date date, String domain, String user, String device, String url) {
		
		super(id, date, domain, user, device);
		
		this.url = url;
		
	}
	
	
}
