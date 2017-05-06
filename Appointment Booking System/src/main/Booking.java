package main;

import java.time.*;
import java.util.Date;

public class Booking {

	private LocalDate date; // The date of the booking
	private LocalTime time; // The time of the booking
	private String customerName; // The customer's name of the booking
	private String employeeName; // The employee assigned to the booking
	private String status;
	private String serviceType;

	// Constructor when there is no status given, to be used for new bookings
	// which automatically sets the status of new bookings to "NEW"
	public Booking(String year, String month, String date, String hour, String minute, String customerName,
			String employeeName, String serviceType) {
		this.date = LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(date));
		this.time = LocalTime.of(Integer.valueOf(hour), Integer.valueOf(minute));
		this.customerName = customerName;
		this.employeeName = employeeName;
		this.status = "NEW"; // New booking
		this.serviceType = serviceType;
		refreshStatus();
	}

	// If status is given, usually is used when reading booking from text file
	public Booking(String status, String year, String month, String date, String hour, String minute,
			String customerName, String employeeName, String serviceType) {
		this.date = LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(date));
		this.time = LocalTime.of(Integer.valueOf(hour), Integer.valueOf(minute));
		this.customerName = customerName;
		this.employeeName = employeeName;
		this.status = status;
		this.serviceType = serviceType;
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

	public String getType() {
		return this.serviceType;
	}

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

	public void setType(String type) {
		this.serviceType = type;
	}

	// Checking the status of the booking with the date
	public void refreshStatus() {

		// If the date has already passed, set the status to passed.
		if (date.isBefore(LocalDate.now())) {
			this.status = "Expired";
		}

		if (date.isAfter(LocalDate.now()) && this.status.compareTo("NEW") != 0) {
			this.status = "Active";
		}

	}

	// To display the booking
	public void displayBooking() {

		System.out.print(this.date + " " + this.time + " " + this.status);

		int statusLength = this.status.length();

		for (int i = statusLength; i < 9; i++) {
			System.out.print(" ");
		}

		System.out.print(this.customerName);

		int customerNameLength = this.customerName.length();

		for (int i = customerNameLength; i < 12; i++) {
			System.out.print(" ");
		}

		int employeeNameLength = this.employeeName.length();
		System.out.print(this.employeeName);

		for (int i = employeeNameLength; i < 12; i++) {
			System.out.print(" ");
		}

		System.out.print(this.serviceType);

		System.out.println();

	}

	// To display the booking
	public void displayBookingCustomer() {

		System.out.print(this.date + " " + this.time + " ");

		System.out.print(this.customerName);

		int customerNameLength = this.customerName.length();

		for (int i = customerNameLength; i < 12; i++) {
			System.out.print(" ");
		}

		int employeeNameLength = this.employeeName.length();
		System.out.print(this.employeeName);

		for (int i = employeeNameLength; i < 12; i++) {
			System.out.print(" ");
		}

		System.out.print(this.serviceType);

		System.out.println();

	}

}
