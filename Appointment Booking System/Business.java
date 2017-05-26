package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.sql.Array;
import java.time.*;

public class Business {

	private String name; // The name of the business
	private String owner; // Business's owner's name
	private String address; // Business's address
	private String phone; // Business's contact number
	private ArrayList<LocalTime> openTime = new ArrayList<LocalTime>(); // List to store the opening time
	private ArrayList<LocalTime> closeTime = new ArrayList<LocalTime>(); // List to store the closing time
	private ArrayList<DayOfWeek> openingDays = new ArrayList<DayOfWeek>(); // List to store the opening days

	// Constructor of the business
	public Business(String name, String owner, String address, String phone, String openingDays, String openingTime,
			String closingTime) {
		this.name = name;
		this.owner = owner;
		this.address = address;
		this.phone = phone;

		StringTokenizer stDays = new StringTokenizer(openingDays, ";");
		while (stDays.hasMoreTokens()) {
			this.addOpeningDays(Integer.valueOf(stDays.nextToken()));
		}

		StringTokenizer stOpenTime = new StringTokenizer(openingTime, ";");
		while (stOpenTime.hasMoreTokens()) {
			int hour = Integer.valueOf(stOpenTime.nextToken());
			int minute = Integer.valueOf(stOpenTime.nextToken());
			this.openTime.add(LocalTime.of(hour, minute));
		}

		StringTokenizer stCloseTime = new StringTokenizer(closingTime, ";");
		while (stCloseTime.hasMoreTokens()) {
			int hour = Integer.valueOf(stCloseTime.nextToken());
			int minute = Integer.valueOf(stCloseTime.nextToken());
			this.closeTime.add(LocalTime.of(hour, minute));
		}

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

	public void addOpeningDays(int dayValue) {

		// To check if there is no duplicate of opening day
		for (int i = 0; i < openingDays.size(); i++) {
			if (openingDays.get(i).getValue() == dayValue) {
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
	
	public String getFileName() {
		
		StringTokenizer st = new StringTokenizer(this.getName(), " ");
		String file_name = "";
		while (st.hasMoreTokens()) {
			file_name += st.nextToken();
		}
		
		return file_name;
		
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



	public void setOpenTime(int index, int hour, int minute) {
		this.openTime.remove(index);
		this.openTime.add(index, LocalTime.of(hour, minute));
	}

	public void setCloseTime(int index, int hour, int minute) {
		closeTime.remove(index);
		closeTime.add(index, LocalTime.of(hour, minute));
	}

	public ArrayList<LocalTime> getOpenTime() {
		return this.openTime;
	}

	public ArrayList<LocalTime> getCloseTime() {
		return this.closeTime;
	}

	public ArrayList<DayOfWeek> getOpeningDays() {
		return this.openingDays;
	}

	public int getIndexOfSelectedDay(int IntegerOfDay) {
		for (int i = 0; i < openingDays.size(); i++) {
			if (openingDays.get(i).getValue() == IntegerOfDay) {
				return i;
			}
		}
		return 0;
	}


	public void displayOpeningDay() {

		for (int i = 1; i <= this.openingDays.size(); i++) {
			System.out.println(i + ". " + this.openingDays.get(i - 1));
		}

	}

	public void displayBusinessDetails() {

		System.out.println("Business name: " + this.getName() + "\n" + "Business owner: " + this.getOwner() + "\n"
				+ "Business address: " + this.getAddress() + "\n" + "Business phone: " + this.getPhone());
	}

	public void displayOpeningDayAndTime() {

		if (this.openingDays.size() == 0) {
			System.out.println("The busines has not given any opening days.");
			return;
		}

		else {
			System.out.print(this.name + " opens on \n");
			for (int i = 0; i < this.openingDays.size(); i++) {
				System.out.println(
						this.openingDays.get(i) + " FROM " + this.openTime.get(i) + " TO " + this.closeTime.get(i));
			}
		}
	}
}
