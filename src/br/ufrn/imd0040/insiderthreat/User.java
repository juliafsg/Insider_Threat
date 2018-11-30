/**
 * Classe User
 * 
 * Define as características do usuário.
 * 
 * @author Abmael Dantas
 * @author Julia Ferreira
 * @version 2018.11.29
 */
package br.ufrn.imd0040.insiderthreat;

public class User extends Data {

	private String name;
	private String id;
	private String domain;
	private String email;
	private String role;
	
	public User(String name, String id, String domain, String email, String role) {
		
		this.name = name;
		this.id = id;
		this.domain = domain;
		this.email = email;
		this.role = role;
		
	}
	
	public String toString() { 
		
        return "\n" + this.name + " (" + this.role + " from "+ this.domain + "), Id: " + this.id + "; Contact by " + this.email;
        
     }
	
	public String getId() {
		
		return this.id;
		
	}
	
	public String getDomain() {
		return domain;
	}
	
}
