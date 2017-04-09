package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import utility.BookingComparator;
import utility.ReadFile;
import utility.WriteFile;

public class BusinessManagement {

	private Business selectedBusiness;

	public BusinessManagement(String businessName) throws IOException {
		try {
			selectedBusiness = selectBusiness(businessName);
		} 
		
		// Captures the error if the business name cannot be found in the text file
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
				// To display summaries of bookings
				viewSummariesOfBookings();
				System.out.println("\nPress any key to return.");
				in.nextLine();
				break;

			case 3:
				// To display only the new bookings
				viewNewBookings();
				System.out.println("\nPress any key to return.");
				in.nextLine();
				break;

			case 4:
				// To add a new service and store in text file
				System.out.println("Please enter the name of the service\n" + "--------------------------------------");
				String serviceName = in.nextLine();

				System.out.println("Please enter the duration of the service (minutes)\n"
						+ "--------------------------------------------------");

				String serviceDuration = in.nextLine();

				while (!isNumericAndPositive(serviceDuration)) {
					System.out.println("Please enter a valid duration\n" + "------------------------------");
					serviceDuration = in.nextLine();
				}

				System.out.println("Please enther the description of the service\n"
						+ "----------------------------------------------");

				String serviceDescription = in.nextLine();
				if (serviceName != null && serviceDuration != null && serviceDescription != null) {
					if (addService(serviceName, serviceDuration, serviceDescription))
						System.out.println("The service has been added succesfully.");
				}

				break;

			case 5:
				// Logout
				return;

			default:
				System.out.println("Option is not valid");
				break;

			}
		}

	}

	/*
	 * Function to set operating time (not a requirement for now)
	 * 
	 * public void setOperatingTime(Scanner in) {
	 * 
	 * String msg = in.nextLine(); while (!isNumericAndPositive(msg)) {
	 * System.out.println("Please enter a valid number\n" +
	 * "-----------------------------"); msg = in.nextLine(); } do {
	 * System.out.println("Opening Time");
	 * 
	 * System.out.println("Please specify the opening hour (24HR format)");
	 * System.out.println("-----------------------------------------------");
	 * msg = in.nextLine();
	 * 
	 * while (!isNumericAndPositive(msg)) {
	 * System.out.println("Please enter a valid number\n" +
	 * "-----------------------------"); msg = in.nextLine(); }
	 * 
	 * int hour = Integer.valueOf(msg);
	 * 
	 * System.out.println("Please specify the opening minute");
	 * System.out.println("------------------------------------"); msg =
	 * in.nextLine();
	 * 
	 * while (!isNumericAndPositive(msg)) {
	 * System.out.println("Please enter a valid number\n" +
	 * "-----------------------------"); msg = in.nextLine(); }
	 * 
	 * int minute = Integer.valueOf(msg); selectedBusiness.setOpenTime(hour,
	 * minute); } while (selectedBusiness.getOpenTime() == null);
	 * 
	 * do { System.out.println("Closing Time");
	 * System.out.println("Please specify the closing hour (24HR format)");
	 * System.out.println("-----------------------------------------------");
	 * msg = in.nextLine();
	 * 
	 * while (!isNumericAndPositive(msg)) {
	 * System.out.println("Please enter a valid number\n" +
	 * "-----------------------------"); msg = in.nextLine(); }
	 * 
	 * int hour = Integer.valueOf(msg);
	 * 
	 * System.out.println("Please specify the closing minute");
	 * System.out.println("------------------------------------"); msg =
	 * in.nextLine(); while (!isNumericAndPositive(msg)) {
	 * System.out.println("Please enter a valid number\n" +
	 * "-----------------------------"); msg = in.nextLine(); }
	 * 
	 * int minute = Integer.valueOf(msg); selectedBusiness.setCloseTime(hour,
	 * minute); } while (selectedBusiness.getCloseTime() == null);
	 * 
	 * }
	 */

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
					bookingArray
							.add(new Booking(bookingList[i][0], bookingList[i][1], bookingList[i][2], bookingList[i][3],
									bookingList[i][4], bookingList[i][5], bookingList[i][6], bookingList[i][7]));
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

		List<Booking> bookingList = retrieveBooking();  //Bookings are retrieved and stored in an array list.

		System.out.println("Summaries of Bookings for " + selectedBusiness.getName());
		System.out.println("-------------------------------------------------------");
		System.out.println("Date      |Time |Status  |Customer   |Employee  ");

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

		System.out.println("Date      |Time |Status  |Customer   |Employee  ");

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
						+ "|" + bookingList.get(i).getEmployeeName() + "|\n");
			} else {
				writer.writeToFile(bookingList.get(i).getStatus() + "|" + bookingList.get(i).getDate().getYear() + "|"
						+ bookingList.get(i).getDate().getMonthValue() + "|"
						+ bookingList.get(i).getDate().getDayOfMonth() + "|" + bookingList.get(i).getTime().getHour()
						+ "|" + bookingList.get(i).getTime().getMinute() + "|" + bookingList.get(i).getCustomerName()
						+ "|" + bookingList.get(i).getEmployeeName() + "|\n");
			}
		}
	}

	// Function to select a business, useful if there is more than one business
	public Business selectBusiness(String businessName) throws IOException {
		String file_name = "Business.txt";

		try {
			ReadFile file = new ReadFile(file_name);
			String businessTokens[] = file.FindBusiness(businessName);
			if (businessTokens == null) {
				System.out.println("\nThe input business does not exist.");
				return null;
			} else {

				setSelectedBusiness(new Business(businessTokens[0], businessTokens[1], businessTokens[2],
						businessTokens[3], businessTokens[4], businessTokens[5], businessTokens[7], businessTokens[8],
						businessTokens[9], businessTokens[10]));

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

	// To display the main menu
	public void printMenu() {

		if (selectedBusiness != null) {

			System.out.printf("\nWelcome %s. \nPlease choose your option\n", selectedBusiness.getName());
			System.out.println("----------------------------\n" + "1. Employee Management \n"
					+ "2. View Booking Summaries \n" + "3. View New Bookings \n" + "4. Add service \n" + "5. Logout \n"
					+ "----------------------------");
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

}
