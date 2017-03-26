package main;

import java.util.ArrayList;
import java.util.Collections;
import java.time.*;

public class Business {

	private String name;
	private String owner;
	private String address;
	private String phone;
	private String username;
	private String password;
	private LocalTime openTime;
	private LocalTime closeTime;
	private ArrayList<Integer> availableDays = new ArrayList<Integer>();

	public Business(String name, String owner, String address, String phone, String username, String password) {
		this.name = name;
		this.owner = owner;
		this.address = address;
		this.phone = phone;
		this.username = username;
		this.password = password;
	}

	// Mutators
	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOpenTime(int hour, int minute) {
		this.openTime = LocalTime.of(hour,minute);
	}

	public void setCloseTime(int hour, int minute) {
		this.closeTime = LocalTime.of(hour, minute);                       
	}
	

	// Accessors
	public String getName() {
		return this.name;
	}

	public String getOwner() {
		return this.owner;
	}

	public String getAddress() {
		return this.address;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public ArrayList<Integer> getAvailableDays() {
		return this.availableDays;
	}

	public LocalTime getOpenTime() {
		return this.openTime;
	}

	public LocalTime getCloseTime() {
		return this.closeTime;
	}

	public void addAvailableDay(int day) {

		LocalDate localDate = LocalDate.now();
		LocalDate nextMonth = localDate.plusMonths(1);

		int lastDayOfNextMonth = nextMonth.lengthOfMonth();

		if (day > 0 && day <= lastDayOfNextMonth)
			availableDays.add(day);

		else
			System.out.println("The selected day is not valid");

		Collections.sort(availableDays);

		System.out.println(availableDays);

	}
}
