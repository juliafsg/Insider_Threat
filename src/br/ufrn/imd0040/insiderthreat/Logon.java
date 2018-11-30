/**
 * Classe Logon
 * 
 * Representa a tividade do logon do usuário.
 * 
 * @author Abmael Dantas
 * @author Julia Ferreira
 * @version 2018.11.29
 */
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
