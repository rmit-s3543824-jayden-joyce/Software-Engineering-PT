package main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import utility.BookingComparator;
import utility.ReadFile;
import utility.ServiceComparator;
import utility.WriteFile;

public class BusinessManagement {

	private Business selectedBusiness;
	private List<Business> businessList;

	public BusinessManagement(String businessName) throws IOException {
		try {
			businessList = retrieveBusiness();
			selectedBusiness = selectBusiness(businessName);

		}

		// Captures the error if the business name cannot be found in the text
		// file
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// To run the main menu of business management
	public void runMenu() throws IOException {
		int userInput; // User's input

		Scanner in = new Scanner(System.in);

		while (true) {
			printMenu();
			String msg = in.nextLine();
			while (!isNumericAndPositive(msg)) {
				System.out.println("Please enter a valid option\n" + "-----------------------------");
				msg = in.nextLine();
			}
			userInput = Integer.valueOf(msg);

			switch (userInput) {
			case 1:
				// To proceed to employee management menu
				EmployeeManagement.employeeManagement();
				break;

			case 2:
				// To add new booking
				System.out.println("Adding new booking\n" + "-----------------------------");
				List<Service> servicesList = retrieveServices();

				for (int i = 1; i <= servicesList.size(); i++) {
					System.out.println(i + ". " + servicesList.get(i - 1).getName() + " "
							+ servicesList.get(i - 1).getDuration() + " minutes");
				}

				String serviceIndex = "";

				while (!isNumericAndPositive(serviceIndex) || serviceIndex.isEmpty()) {
					System.out
							.println("Please choose the service required\n" + "--------------------------------------");
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

				System.out
						.println("Please choose the date of the booking \n" + "--------------------------------------");
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

				int dayOfWeek = bookingDate.getDayOfWeek().getValue();
				int dayIndex = selectedBusiness.getIndexOfSelectedDay(dayOfWeek);

				if (dayIndex == 0) {
					System.out.println("The business is not operating on the selected day.");
					break;
				}

				String hour = "";
				String minute = "";
				LocalTime bookingTime = null;

				while (bookingTime == null) {

					while (!isNumericAndPositive(hour)) {
						System.out.println(
								"Please enter the hour of the booking time \n" + "---------------------------------");
						hour = in.nextLine();
					}
					while (!isNumericAndNeutral(minute) || Integer.parseInt(minute) % 30 != 0
							|| Integer.parseInt(minute) >= 60) {
						System.out.println("Please enter the minute of the booking time (00 or 30) \n"
								+ "---------------------------------");
						minute = in.nextLine();
					}

					bookingTime = LocalTime.of(Integer.valueOf(hour), Integer.valueOf(minute));

					if (bookingTime.isBefore(selectedBusiness.getOpenTime().get(dayIndex))
							|| bookingTime.isAfter(selectedBusiness.getCloseTime().get(dayIndex))) {
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

				while (customerName.isEmpty()) {
					System.out.println("Please enter the customer name\n" + "--------------------------------------");
					customerName = in.nextLine();
				}

				addBooking(bookingDate.getYear(), bookingDate.getMonthValue(), bookingDate.getDayOfMonth(),
						bookingTime.getHour(), bookingTime.getMinute(), customerName, employeeID, serviceType);

				break;

			case 3:
				// To display summaries of bookings
				viewSummariesOfBookings();
				System.out.println("\nPress enter to return.");
				in.nextLine();
				break;

			case 4:
				// To display only the new bookings
				viewNewBookings();
				System.out.println("\nPress enter to return.");
				in.nextLine();
				break;

			case 5:
				// To add a new service and store in text file
				System.out.println("Please enter the name of the service\n" + "--------------------------------------");
				String serviceName = in.nextLine();

				while (serviceName.isEmpty()) {
					System.out.println(
							"Please enter the name of the service\n" + "--------------------------------------");
					serviceName = in.nextLine();
				}

				System.out.println(
						"Please enter the duration (minutes) of the service (only multiples of 30 mins are allowed)\n"
								+ "--------------------------------------------------");

				String serviceDuration = in.nextLine();

				while (!isNumericAndPositive(serviceDuration) || !isMultiplesOf30(serviceDuration)) {
					System.out.println("Please enter a valid duration\n" + "------------------------------");
					serviceDuration = in.nextLine();
				}

				System.out.println("Please enther the description of the service\n"
						+ "----------------------------------------------");

				String serviceDescription = in.nextLine();

				while (serviceDescription.isEmpty()) {
					System.out.println(
							"Please enter the description of the service\n" + "--------------------------------------");
					serviceDescription = in.nextLine();
				}

				if (serviceName != null && serviceDuration != null && serviceDescription != null) {
					if (addService(serviceName, serviceDuration, serviceDescription))
						System.out.println("The service has been added succesfully.");
				}

				break;

			case 6:
				viewServices();
				System.out.println("\nPress enter to return.");
				in.nextLine();
				break;

			case 7:
				setOperatingTime(in);
				break;

			case 8:
				editBusinessDetails(in);
				break;

			case 9:
				selectedBusiness.displayBusinessDetails();
				selectedBusiness.displayOpeningDayAndTime();
				System.out.println("\nPress enter to return.");
				in.nextLine();
				break;

			case 10:
				// Logout
				return;

			default:
				System.out.println("Option is not valid");
				break;

			}
		}

	}

	// Function to add service that the business provide, which will be stored
	// in a text file so it can be retrieved for the customer to view
	public boolean addService(String serviceName, String serviceDuration, String serviceDescription)
			throws IOException {

		// To read the name of the text file in the correct format
		StringTokenizer st = new StringTokenizer(selectedBusiness.getName(), " ");
		String file_name = "";
		while (st.hasMoreTokens()) {

			file_name += st.nextToken();
		}

		file_name += "Services.txt";

		// Appending the selected text file
		WriteFile writer = new WriteFile(file_name, true);
		if (writer.writeToFile("\n" + serviceName + "|" + serviceDuration + "|" + serviceDescription)) {
			return true;
		} else
			return false;
	}

	public void viewServices() {

		List<Service> servicesList = retrieveServices();

		System.out.println("Services for " + selectedBusiness.getName());
		System.out.println("-------------------------------------------------------");
		System.out.println("Name           |Duration (Minutes) |Description");

		for (int i = 0; i <= servicesList.size() - 1; i++) {
			servicesList.get(i).displayService();
		}

	}

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

	public void viewSummariesOfBookings() {

		List<Booking> bookingList = retrieveBooking(); // Bookings are retrieved
														// and stored in an
														// array list.

		System.out.println("Summaries of Bookings for " + selectedBusiness.getName());
		System.out.println("-------------------------------------------------------");
		System.out.println("Date      |Time |Status  |Customer   |Employee   |Service");

		for (int i = bookingList.size() - 1; i >= 0; i--) {
			bookingList.get(i).displayBooking();
		}

	}

	// Using the function retrieveBooking to get the arrayList of bookings, then
	// only present the bookings with "NEW" as their status, after bookings are
	// displayed, the status are changed from "NEW" to "Active" and all status
	// are updated in the text file.

	public void viewNewBookings() throws IOException {

		// To display the new bookings
		List<Booking> bookingList = retrieveBooking();

		System.out.println("New Bookings for " + selectedBusiness.getName());
		System.out.println("-------------------------------------------------------");

		int newBookingsCount = 0;
		for (int i = bookingList.size() - 1; i >= 0; i--) {
			if (bookingList.get(i).getStatus().compareTo("NEW") == 0) {
				newBookingsCount++;
			}
		}

		if (newBookingsCount == 0) {
			System.out.println("There is no new bookings at the moment");
			return;
		}

		System.out.println("Date      |Time |Status  |Customer   |Employee   |Service");

		for (int i = bookingList.size() - 1; i >= 0; i--) {
			if (bookingList.get(i).getStatus().compareTo("NEW") == 0) {
				bookingList.get(i).displayBooking();
			}
		}

		// To read the bookings text file in the correct format
		StringTokenizer st = new StringTokenizer(selectedBusiness.getName(), " ");
		String file_name = "";
		while (st.hasMoreTokens()) {

			file_name += st.nextToken();
		}

		file_name += "Bookings.txt";

		// To change the status of the new bookings from "NEW" to "Active"
		WriteFile writer = new WriteFile(file_name, false);
		writer.writeToFile("");

		writer = new WriteFile(file_name, true);
		for (int i = bookingList.size() - 1; i >= 0; i--) {
			if (bookingList.get(i).getStatus().compareTo("NEW") == 0) {
				writer.writeToFile("Active" + "|" + bookingList.get(i).getDate().getYear() + "|"
						+ bookingList.get(i).getDate().getMonthValue() + "|"
						+ bookingList.get(i).getDate().getDayOfMonth() + "|" + bookingList.get(i).getTime().getHour()
						+ "|" + bookingList.get(i).getTime().getMinute() + "|" + bookingList.get(i).getCustomerName()
						+ "|" + bookingList.get(i).getEmployeeName() + "|" + bookingList.get(i).getType() + "\n");
			} else {
				writer.writeToFile(bookingList.get(i).getStatus() + "|" + bookingList.get(i).getDate().getYear() + "|"
						+ bookingList.get(i).getDate().getMonthValue() + "|"
						+ bookingList.get(i).getDate().getDayOfMonth() + "|" + bookingList.get(i).getTime().getHour()
						+ "|" + bookingList.get(i).getTime().getMinute() + "|" + bookingList.get(i).getCustomerName()
						+ "|" + bookingList.get(i).getEmployeeName() + "|" + bookingList.get(i).getType() + "\n");
			}
		}
	}

	// Function to retrieve businesses from the text file
	// and present them in an array List
	public List<Business> retrieveBusiness() {

		// To read the name of the text file in the correct format
		String file_name = "Business.txt";

		// Retrieve the bookings and store them in a list
		List<Business> businessArray = new ArrayList<Business>();
		try {
			ReadFile file = new ReadFile(file_name);
			String businessList[][] = file.retrieveBusiness();
			if (businessList == null) {
				System.out.println("\nThere is no bookings.");
				return businessArray;
			}

			else {
				int numberOfBookings = file.readLines();
				for (int i = 0; i < numberOfBookings; i++) {
					businessArray.add(new Business(businessList[i][0], businessList[i][1], businessList[i][2],
							businessList[i][3], businessList[i][4], businessList[i][5], businessList[i][6]));
				}
			}

		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Sorting the bookings according to date and time
		return businessArray;
	}

	public void editBusinessDetails(Scanner in) throws IOException {
		String msg = "";

		while (!isStringAcceptable(msg)) {
			System.out.println(
					"Please enter the new name for the business\n" + "---------------------------------------");
			msg = in.nextLine();
		}
		selectedBusiness.setName(msg);
		msg = "";

		while (!isStringAcceptable(msg)) {
			System.out.println(
					"Please enter the new owner's name for the business\n" + "---------------------------------------");
			msg = in.nextLine();
		}
		selectedBusiness.setOwner(msg);
		msg = "";

		while (!isStringAcceptable(msg)) {
			System.out.println(
					"Please enter the new address for the business\n" + "---------------------------------------");
			msg = in.nextLine();
		}
		selectedBusiness.setAddress(msg);
		msg = "";

		while (!isPhoneNumber(msg)) {
			System.out.println(
					"Please enter the new phone number for the business\n" + "---------------------------------------");
			msg = in.nextLine();
		}
		selectedBusiness.setPhone(msg);

		saveBusiness();

	}

	// Function to set operating time for a specific operating day
	public void setOperatingTime(Scanner in) throws IOException {

		String msg = null;

		selectedBusiness.displayOpeningDay();
		int dayIndex = 0;
		while (!isNumericAndPositive(msg)) {

			System.out.println("Which day would you like to change the operating time of ?");
			msg = in.nextLine();
		}

		dayIndex = Integer.valueOf(msg) - 1;

		do {
			System.out.println("Opening Time");

			System.out.println("Please specify the opening hour (24HR format)");
			System.out.println("-----------------------------------------------");
			msg = in.nextLine();

			while (!isNumericAndPositive(msg)) {
				System.out.println("Please enter a valid number\n" + "-----------------------------");
				msg = in.nextLine();
			}

			int hour = Integer.valueOf(msg);

			System.out.println("Please specify the opening minute");
			System.out.println("------------------------------------");
			msg = in.nextLine();

			while (!isNumericAndNeutral(msg)) {
				System.out.println("Please enter a valid number\n" + "-----------------------------");
				msg = in.nextLine();
			}

			int minute = Integer.valueOf(msg);
			selectedBusiness.setOpenTime(dayIndex, hour, minute);
		} while (selectedBusiness.getOpenTime() == null);

		do {
			System.out.println("Closing Time");
			System.out.println("Please specify the closing hour (24HR format)");
			System.out.println("-----------------------------------------------");
			msg = in.nextLine();

			while (!isNumericAndPositive(msg)) {
				System.out.println("Please enter a valid number\n" + "-----------------------------");
				msg = in.nextLine();
			}

			int hour = Integer.valueOf(msg);

			System.out.println("Please specify the closing minute");
			System.out.println("------------------------------------");
			msg = in.nextLine();
			while (!isNumericAndNeutral(msg)) {
				System.out.println("Please enter a valid number\n" + "-----------------------------");
				msg = in.nextLine();
			}

			int minute = Integer.valueOf(msg);
			selectedBusiness.setCloseTime(dayIndex, hour, minute);
		} while (selectedBusiness.getCloseTime() == null);

		saveBusiness();

	}

	public void saveBusiness() throws IOException {

		String file_name = "Business.txt";

		WriteFile writer = new WriteFile(file_name, false);
		writer.writeToFile("");

		writer = new WriteFile(file_name, true);
		for (int i = 0; i < businessList.size(); i++) {
			writer.writeToFile(businessList.get(i).getName() + "|" + businessList.get(i).getOwner() + "|"
					+ businessList.get(i).getAddress() + "|" + businessList.get(i).getPhone() + "|");

			Collections.sort(businessList.get(i).getOpeningDays());

			for (int j = 0; j < businessList.get(i).getOpeningDays().size() - 1; j++) {
				writer.writeToFile(businessList.get(i).getOpeningDays().get(j).getValue() + ";");
			}
			writer.writeToFile(
					businessList.get(i).getOpeningDays().get(businessList.get(i).getOpeningDays().size() - 1).getValue()
							+ "|");

			for (int k = 0; k < businessList.get(i).getOpenTime().size() - 1; k++) {
				writer.writeToFile(Integer.toString(businessList.get(i).getOpenTime().get(k).getHour()) + ";"
						+ Integer.toString(businessList.get(i).getOpenTime().get(k).getMinute()) + ";");
			}
			writer.writeToFile(Integer.toString(
					businessList.get(i).getOpenTime().get(businessList.get(i).getOpenTime().size() - 1).getHour()) + ";"
					+ Integer.toString(businessList.get(i).getOpenTime()
							.get(businessList.get(i).getOpenTime().size() - 1).getMinute())
					+ "|");

			for (int l = 0; l < businessList.get(i).getCloseTime().size() - 1; l++) {
				writer.writeToFile(Integer.toString(businessList.get(i).getCloseTime().get(l).getHour()) + ";"
						+ Integer.toString(businessList.get(i).getCloseTime().get(l).getMinute()) + ";");
			}
			writer.writeToFile(Integer.toString(
					businessList.get(i).getCloseTime().get(businessList.get(i).getCloseTime().size() - 1).getHour())
					+ ";" + Integer.toString(businessList.get(i).getCloseTime()
							.get(businessList.get(i).getCloseTime().size() - 1).getMinute())
					+ "\n");

		}

	}

	// Function to select a business, useful if there is more than one business
	public Business selectBusiness(String businessName) throws IOException {

		Business selectedBusiness = null;

		for (int i = 0; i < businessList.size(); i++) {
			if (businessList.get(i).getName().compareTo(businessName) == 0) {
				selectedBusiness = businessList.get(i);
			}
		}

		if (selectedBusiness == null) {
			System.out.println("The business does not exist.");
		}
		return selectedBusiness;

	}

	// To display the main menu
	public void printMenu() {

		if (selectedBusiness != null) {

			System.out.printf("\nWelcome %s. \nPlease choose your option\n", selectedBusiness.getName());
			System.out.println("----------------------------\n" + "1. Employee Management \n" + "2. Add New Booking \n"
					+ "3. View Booking Summaries \n" + "4. View New Bookings \n" + "5. Add service \n"
					+ "6. View Service \n" + "7. Set Operating Time \n" + "8. Edit Business Details \n"
					+ "9. View Business Details \n" + "10. Logout \n" + "----------------------------");
		}
	}

	public Business getSelectedBusiness() {
		return this.selectedBusiness;
	}

	public void setSelectedBusiness(Business business) {
		this.selectedBusiness = business;
	}

	// To check if there is any business being selected
	public boolean isEmpty() {
		if (selectedBusiness == null) {
			return true;
		}
		return false;
	}

	// To check if UserInput is valid for the menu
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

	public boolean isMultiplesOf30(String str) {
		try {
			int d = Integer.parseInt(str);
			if (d % 30 == 0) {
				return true;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return false;
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

	public boolean isStringAcceptable(String str) {
		if (str.isEmpty())
			return false;

		return true;
	}

	public boolean isPhoneNumber(String str) {

		if (str.isEmpty())
			return false;

		if (!Registration.isPhoneNumber(str))
			return false;

		return true;
	}

}
