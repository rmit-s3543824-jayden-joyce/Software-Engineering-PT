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
	private ArrayList<DayOfWeek> openingDays = new ArrayList<DayOfWeek>();
	private ArrayList<Integer> availableDays = new ArrayList<Integer>();

	public Business(String name, String owner, String address, String phone, String username, String password,
			String openingHour, String openingMinute, String closingHour, String closingMinute) {
		this.name = name;
		this.owner = owner;
		this.address = address;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.openTime = LocalTime.of(Integer.valueOf(openingHour), Integer.valueOf(openingMinute));
		this.closeTime = LocalTime.of(Integer.valueOf(closingHour), Integer.valueOf(closingMinute));
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
		this.openTime = LocalTime.of(hour, minute);
	}

	public void setCloseTime(int hour, int minute) {
		this.closeTime = LocalTime.of(hour, minute);
	}

	public void addOpeningDays(int dayValue) {
		
		// To check if there is no duplicate of opening day
		for(int i = 0; i < openingDays.size(); i++){
			if(openingDays.get(i).getValue() == dayValue){
				return;
			}
		}

		openingDays.add(DayOfWeek.of(dayValue));
		Collections.sort(openingDays);
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

	public ArrayList<DayOfWeek> getOpeningDays() {
		return this.openingDays;
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

	public void displayOpeningDayAndTime() {

		if (this.openingDays.size() == 0) {
			System.out.println("The busines has not given any opening days.");
			return;
		}

		else {
			System.out.print(this.name + " opens on ");
			for (int i = 0; i < openingDays.size(); i++) {
				System.out.print(openingDays.get(i) + ", ");
			}
			System.out.println("\nFrom " + this.openTime + " to " + this.closeTime);
		}
	}
}
