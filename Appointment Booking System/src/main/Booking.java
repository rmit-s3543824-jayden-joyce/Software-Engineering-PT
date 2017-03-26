package main;

import java.sql.Time;
import java.util.Date;

public class Booking {

	private Date date;
	private Time time;
	
	// Maybe can use localTime or localDate depending which is more suitable
	
	private String status;
	private Employee employee;
	
	// Accessors
	public Date getDate(){
		return this.date;
	}
	
	public Time getTime(){
		return this.time;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public Employee getEmployee(){
		return this.employee;
	}
	
	
	
	// Mutators
	public void setDate(Date date){
		this.date = date;
	}
	
	public void setTime(Time time){
		this.time = time;
	}
	
	public void refreshStatus(){
	
		
		// If the booking is established but there is no assigned employee
		if(this.getEmployee() == null)
		this.status = "vacant";
		
		else
			this.status = "active";
		
		
		
		// If the date has already passed, set the status to passed.
		if(){
		this.status = "passed";
		}
		
	}
	
	
	public void assignEmployee(Employee employeeName){
		this.employee = employeeName;
	}
	
	
	
	
	
}
