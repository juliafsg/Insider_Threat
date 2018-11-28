
package br.ufrn.imd0040.insiderthreat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time_Frame extends Data {

	private Date begin;
	private Date end;
	static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public Time_Frame(String begin, String end) {
		
		try {
			
			this.begin = format.parse(begin);
			this.end = format.parse(end);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public Date getBegin() {
		
		return begin;
		
	}
	
	public Date getEnd() {
		
		return end;
		
	}
	
	public boolean equals(Time_Frame object) {
	
		if (this.getBegin() == object.getBegin() && this.getEnd() == object.getEnd()) {
			
			return true;
			
		}
		
		else {
			
			return false;
			
		}
	    
	}
	
	public String toString() {
		
		return this.getBegin().toString() + " - " + this.getEnd().toString();
		
	}
	
}
