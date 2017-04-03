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

	// If no status is given, usually is the constructor for new booking
	public Booking(String year, String month, String date, String hour, String minute, String customerName,
			String employeeName) {
		this.date = LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(date));
		this.time = LocalTime.of(Integer.valueOf(hour), Integer.valueOf(minute));
		this.customerName = customerName;
		this.employeeName = employeeName;
		this.status = "New";
		// this.employee = searchEmployee(employeeName);
		refreshStatus();
	}

	// If status is given, usually is used when reading booking from text file
	public Booking(String status, String year, String month, String date, String hour, String minute,
			String customerName, String employeeName) {
		this.date = LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(date));
		this.time = LocalTime.of(Integer.valueOf(hour), Integer.valueOf(minute));
		this.customerName = customerName;
		this.employeeName = employeeName;
		this.status = status;
		// this.employee = searchEmployee(employeeName);
		refreshStatus();
	}

	// Accessors
	public LocalDate getDate() {
		return this.date;
	}

	public LocalTime getTime() {
		return this.time;
	}

	public String getStatus() {
		return this.status;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	/*
	 * public Employee getEmployee(){ return this.employee; }
	 */

	// Mutators
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void refreshStatus() {

		// If the booking is established but there is no assigned employee
		/*
		 * if(this.getEmployee() == null) this.status = "vacant";
		 * 
		 * else this.status = "active";
		 */

		// If the date has already passed, set the status to passed.
		if (date.isBefore(LocalDate.now())) {
			this.status = "Expired";
		}

	}

	/*
	 * public void assignEmployee(Employee employeeName){ this.employee =
	 * employeeName; }
	 */
	public void displayBooking() {

		System.out.print(this.date + " " + this.time + " " + this.status);

		int statusLength = this.status.length();

		for (int i = statusLength; i < 9; i++) {
			System.out.print(" ");
		}
		
		System.out.print(this.customerName);
		
		int customerNameLength = this.customerName.length();
		
		for(int i = customerNameLength; i < 12; i++){
			System.out.print(" ");
		}
		
		int employeeNameLength = this.employeeName.length();
		System.out.print(this.employeeName);

		System.out.println();
		
		

	}

}
