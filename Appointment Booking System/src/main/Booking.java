package main;


import java.time.*;
import java.util.Date;

public class Booking {

	private LocalDate date;
	private LocalTime time;
	private String customerName;
	private String employeeName;
	
	
	// Maybe can use localTime or localDate depending which is more suitable
	
	private String status;
	// private Employee employee;
	
	public Booking(String year, String month, String date, String hour, String minute, String customerName, String employeeName){
		this.date = LocalDate.of(Integer.valueOf(year),Integer.valueOf(month),Integer.valueOf(date));
		this.time = LocalTime.of(Integer.valueOf(hour),Integer.valueOf(minute));
		this.customerName = customerName;
		this.employeeName = employeeName;
		this.status = "New";
		//this.employee = searchEmployee(employeeName);
		refreshStatus();
	}
	
	// Accessors
	public LocalDate getDate(){
		return this.date;
	}
	
	public LocalTime getTime(){
		return this.time;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public String getCustomerName(){
		return this.customerName;
	}
	
	public String getEmployeeName(){
		return this.employeeName;
	}
	
	/*
	public Employee getEmployee(){
		return this.employee;
	}
	*/
	
	
	
	
	// Mutators
	public void setDate(LocalDate date){
		this.date = date;
	}
	
	public void setTime(LocalTime time){
		this.time = time;
	}
	
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	
	public void refreshStatus(){
	
		
		// If the booking is established but there is no assigned employee
		/*
		if(this.getEmployee() == null)
		this.status = "vacant";
		
		else
			this.status = "active";
		*/
		
		
		// If the date has already passed, set the status to passed.
		if(date.isBefore(LocalDate.now())){
		this.status = "Expired";
		}
		
	}
	
	/*
	public void assignEmployee(Employee employeeName){
		this.employee = employeeName;
	}
	*/
	public void displayBooking(){
		System.out.println(this.date + " " + this.time + " " + this.status + " " + this.customerName + "    " + this.employeeName);

	}
	
		
	
	
	
	
	
}
