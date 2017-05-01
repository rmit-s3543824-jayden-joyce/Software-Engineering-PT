package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import utility.BookingComparator;
import utility.ReadFile;
import utility.ServiceComparator;
import utility.WriteFile;

import java.time.*;

public class CustomerManagement {

	static Business selectedBusiness;

	public CustomerManagement(String businessName) {
		try {
			System.out.println(Login.currentUser);
			selectedBusiness = selectBusiness(businessName);
		}

		// Captures the error if the business name cannot be found in the text
		// file
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void runMenu() throws IOException {

		int userInput;

		Scanner in = new Scanner(System.in);

		while (true) {
			printMenu();
			String msg = in.nextLine();
			while (!isNumericAndPositive(msg)) {
				System.out.println("Please enter a valid option\n" + "-----------------------------");
				msg = in.nextLine();
			}
			userInput = Integer.valueOf(msg);

			if (selectedBusiness == null) {
				switch (userInput) {
				case 1:
					while (selectedBusiness == null) {
						System.out.println("Please input the business name\n" + "-----------------------------");
						String businessName = in.nextLine();
						selectBusiness(businessName);
					}
					break;

				case 2:
					viewMyBookings();
					System.out.println("Press enter to return.");
					in.nextLine();
					return;

				case 3:
					System.out.println("Successfully logged out.");
					return;
				}
			}

			else {
				switch (userInput) {

				/*
				 * case 1: selectedBusiness = null; while (selectedBusiness ==
				 * null) { System.out.println("Please input the business name\n"
				 * + "-----------------------------"); String businessName =
				 * in.nextLine(); selectBusiness(businessName); } break;
				 */
				case 1:
					viewMyBookings();
					System.out.println("Press enter to return.");
					in.nextLine();
					break;

				case 2:
					// To add new booking
					List<Service> servicesList = retrieveServices();

					for (int i = 1; i <= servicesList.size(); i++) {
						System.out.println(i + ". " + servicesList.get(i - 1).getName() + " "
								+ servicesList.get(i - 1).getDuration() + " minutes");
					}

					String serviceIndex = "";

					while (!isNumericAndPositive(serviceIndex) || serviceIndex.isEmpty()) {
						System.out.println(
								"Please choose the service required\n" + "--------------------------------------");
						serviceIndex = in.nextLine();

						if (isNumericAndPositive(serviceIndex)) {
							if (Integer.parseInt(serviceIndex) > servicesList.size()) {
								serviceIndex = "INVALID";
							}
						}
					}

					String serviceType = servicesList.get(Integer.parseInt(serviceIndex) - 1).getName();

					System.out.println(servicesList.get(Integer.parseInt(serviceIndex) - 1).getName() + " selected.");

					LocalDate currentDate = LocalDate.now();
					for (int i = 1; i <= 7; i++) {
						System.out.println(i + ". " + currentDate.plusDays(i).toString());
					}

					System.out.println(
							"Please choose the date of the booking \n" + "--------------------------------------");
					String date = in.nextLine();

					if (isNumericAndPositive(date)) {
						if (Integer.parseInt(date) > 7) {
							date = "INVALID";
						}
					}

					while (!isNumericAndPositive(date) || date.isEmpty()) {
						System.out.println("Invalid input.");
						date = in.nextLine();

						if (isNumericAndPositive(date)) {
							if (Integer.parseInt(date) > 7) {
								date = "INVALID";
							}
						}

					}

					LocalDate bookingDate = currentDate.plusDays(Integer.parseInt(date));
					System.out.println(bookingDate.toString() + " selected.");

					String hour = "";
					String minute = "";
					LocalTime bookingTime = null;

					while (bookingTime == null) {

						while (!isNumericAndPositive(hour)) {
							System.out.println("Please enter the hour of the booking time \n"
									+ "---------------------------------");
							hour = in.nextLine();
						}
						while (!isNumericAndNeutral(minute) || Integer.parseInt(minute) % 30 != 0
								|| Integer.parseInt(minute) >= 60) {
							System.out.println("Please enter the minute of the booking time (00 or 30) \n"
									+ "---------------------------------");
							minute = in.nextLine();
						}

						bookingTime = LocalTime.of(Integer.valueOf(hour), Integer.valueOf(minute));

						if (bookingTime.isBefore(selectedBusiness.getOpenTime())
								|| bookingTime.isAfter(selectedBusiness.getCloseTime())) {
							System.out.println("The selected time is not within the business time.");
							bookingTime = null;
							hour = "INVALID";
							minute = "INVALID";

						}
					}

					EmployeeManagement.listEmployees();
					System.out.println("Please enter the employee's ID\n" + "--------------------------------------");
					String employeeID = ScheduleManagement.requestID();

					String customerName = "";

					customerName = Login.currentUser;

					addBooking(bookingDate.getYear(), bookingDate.getMonthValue(), bookingDate.getDayOfMonth(),
							bookingTime.getHour(), bookingTime.getMinute(), customerName, employeeID, serviceType);

					break;

				case 3:
					viewAvailability();
					System.out.println("Press enter to return.");
					in.nextLine();
					break;

				case 4:
					selectedBusiness.displayOpeningDayAndTime();
					System.out.println("Press enter to return.");
					in.nextLine();
					break;

				case 5:
					viewServices();
					System.out.println("\nPress enter to return.");
					in.nextLine();
					break;

				case 6:
					return;
				}
			}
		}
	}

	public Business selectBusiness(String businessName) throws IOException {
		String file_name = "Business.txt";

		try {
			ReadFile file = new ReadFile(file_name);
			String businessTokens[] = file.FindBusiness(businessName);
			if (businessTokens == null) {
				System.out.println("\nThe input business does not exist.");
				return null;
			} else {

				selectedBusiness = new Business(businessTokens[0], businessTokens[1], businessTokens[2],
						businessTokens[3], businessTokens[4], businessTokens[5], businessTokens[7], businessTokens[8],
						businessTokens[9], businessTokens[10]);

				StringTokenizer st = new StringTokenizer(businessTokens[6], ";");

				while (st.hasMoreTokens()) {
					int dayValue = Integer.valueOf(st.nextToken());
					selectedBusiness.addOpeningDays(dayValue);
				}
			}
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return selectedBusiness;

	}

	public void viewAvailability() {

		System.out.println("The following times and dates are currently available");
		ScheduleManagement.interfaceShowGeneralAvailability();

	}

	public void viewServices() {

		List<Service> servicesList = retrieveServices();

		System.out.println("Services for " + selectedBusiness.getName());
		System.out.println("-------------------------------------------------------");
		System.out.println("Name           |Duration (Minutes) |Description");

		for (int i = 0; i <= servicesList.size() - 1; i++) {
			servicesList.get(i).displayService();
		}

		System.out.println();

	}

	// Function to retrieve bookings related to the business from the text file
	// and present them in an array List
	public List<Booking> retrieveBooking() {

		// To read the name of the text file in the correct format
		StringTokenizer st = new StringTokenizer(selectedBusiness.getName(), " ");
		String file_name = "";
		while (st.hasMoreTokens()) {

			file_name += st.nextToken();
		}

		file_name += "Bookings.txt";

		// Retrieve the bookings and store them in a list
		List<Booking> bookingArray = new ArrayList<Booking>();
		try {
			ReadFile file = new ReadFile(file_name);
			String bookingList[][] = file.retrieveBooking();
			if (bookingList == null) {
				System.out.println("\nThere is no bookings.");
				return bookingArray;
			}

			else {
				int numberOfBookings = file.readLines();
				for (int i = 0; i < numberOfBookings; i++) {
					bookingArray.add(new Booking(bookingList[i][0], bookingList[i][1], bookingList[i][2],
							bookingList[i][3], bookingList[i][4], bookingList[i][5], bookingList[i][6],
							bookingList[i][7], bookingList[i][8]));
				}

			}
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Sorting the bookings according to date and time
		Collections.sort(bookingArray, new BookingComparator());
		return bookingArray;
	}

	// Using the function retrieveBooking to get the arrayList of bookings, then
	// present all the bookings in an orderly fashion

	public void viewMyBookings() {

		List<Booking> bookingList = retrieveBooking(); // Bookings are retrieved
														// and stored in an
														// array list.

		System.out.println("Bookings for " + Login.currentUser);
		System.out.println("-------------------------------------------------------");
		System.out.println("Date      |Time |Status  |Customer   |Employee   |Service");

		for (int i = bookingList.size() - 1; i >= 0; i--) {
			if (Login.currentUser.compareTo(bookingList.get(i).getCustomerName()) == 0)
				bookingList.get(i).displayBooking();
		}

	}

	
	// Function to retrieve list of services from config file
	public List<Service> retrieveServices() {

		// To read the name of the text file in the correct format
		StringTokenizer st = new StringTokenizer(selectedBusiness.getName(), " ");
		String file_name = "";
		while (st.hasMoreTokens()) {

			file_name += st.nextToken();
		}

		file_name += "Services.txt";

		// Retrieve the bookings and store them in a list
		List<Service> servicesArray = new ArrayList<Service>();
		try {
			ReadFile file = new ReadFile(file_name);
			String servicesList[][] = file.retrieveBooking();
			if (servicesList == null) {
				System.out.println("\nThere is no services.");
				return servicesArray;
			}

			else {
				int numberOfServices = file.readLines();
				for (int i = 0; i < numberOfServices; i++) {
					servicesArray.add(new Service(servicesList[i][0], servicesList[i][1], servicesList[i][2]));
				}

			}
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}

		Collections.sort(servicesArray, new ServiceComparator());

		return servicesArray;

	}

	// Function to add new bookings to be stored in the text file.
	public boolean addBooking(int year, int month, int date, int hour, int minute, String customerName,
			String employeeName, String serviceType) throws IOException {

		StringTokenizer st = new StringTokenizer(selectedBusiness.getName(), " ");
		String file_name = "";
		while (st.hasMoreTokens()) {
			file_name += st.nextToken();
		}

		file_name += "Bookings.txt";

		WriteFile writer = new WriteFile(file_name, true);
		if (writer.writeToFile("\n" + "NEW" + "|" + year + "|" + month + "|" + date + "|" + hour + "|" + minute + "|"
				+ customerName + "|" + employeeName + "|" + serviceType)) {
			System.out.println("Booking added successfully");
			return true;
		} else
			return false;

	}

	public void printMenu() {
		if (selectedBusiness == null) {

			// Main menu
			System.out.println("\n" + "Welcome,\n" + "Please choose your option:\n" + "------------------------------\n"
					+ "1. Select Business \n" + "2. View Bookings \n" + "3. Logout \n"

					+ "------------------------------");
		}

		// Actions menu
		if (selectedBusiness != null) {
			System.out.printf("Welcome to %s. Please choose your option\n", selectedBusiness.getName());
			System.out.println("----------------------------\n" + "1. View My Bookings \n" + "2. Add Booking \n"
					+ "3. View Availability \n" + "4. View Business Opening Days and Time \n" + "5. View Services \n"
					+ "6. Logout \n" + "----------------------------");
		}
	}

	public boolean isNumericAndPositive(String str) {
		try {
			int d = Integer.parseInt(str);
			if (d <= 0) {
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public boolean isNumericAndNeutral(String str) {
		try {
			int d = Integer.parseInt(str);
			if (d < 0) {
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
