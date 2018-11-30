/**
 * Classe HTTP
 * 
 * Representa a atividade HTTP do usuário.
 * 
 * @author Abmael Dantas
 * @author Julia Ferreira
 * @version 2018.11.29
 */
package br.ufrn.imd0040.insiderthreat;

import java.util.Date;

public class HTTP extends Activity {
	
	private String url;

	public HTTP(String id, Date date, String user, String device, String url) {
		
		super(id, date, user, device);
		
		this.url = url;
		
	}
	
	public String getUrl() {
		
		return this.url;
		
	}
	
	
}
