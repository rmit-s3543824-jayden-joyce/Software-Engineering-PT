package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import utility.ReadFile;
import utility.ServiceComparator;

import java.time.*;

public class CustomerManagement {

	static Business selectedBusiness;
	
	public CustomerManagement (String businessName){
		try {
			selectedBusiness = selectBusiness(businessName);
		} 
		
		// Captures the error if the business name cannot be found in the text file
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
			while(!isNumericAndPositive(msg)){
				System.out.println("Please enter a valid option\n"
									+"-----------------------------");
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
					System.out.println("Function not implemented yet");
					return;

				case 3:
					System.out.println("Successfully logged out.");
					return;
				}
			}

			else {
				switch (userInput) {
				case 1:
					selectedBusiness = null;
					while (selectedBusiness == null) {
						System.out.println("Please input the business name\n" + "-----------------------------");
						String businessName = in.nextLine();
						selectBusiness(businessName);
					}
					break;

				case 2:
					viewAvailability();
					System.out.println("Press any key to return.");
					in.nextLine();
					break;

				case 3:
					selectedBusiness.displayOpeningDayAndTime();
					System.out.println("Press any key to return.");
					in.nextLine();
					break;

				case 4:
					viewServices();
					System.out.println("\nPress any key to return.");
					in.nextLine();
					break;
					
				case 5:
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
	
	public void viewServices(){
		
		List<Service> servicesList = retrieveServices();
		
		System.out.println("Services for " + selectedBusiness.getName());
		System.out.println("-------------------------------------------------------");
		System.out.println("Name           |Duration |Description");
		
		for (int i = servicesList.size() - 1; i >= 0; i--) {
			servicesList.get(i).displayService();
		}
		
		System.out.println();
		
	}

	public List<Service> retrieveServices(){
		
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
				System.out.println("\nThere is no bookings.");
				return servicesArray;
			}

			else {
				int numberOfServices = file.readLines();
				System.out.println(numberOfServices);
				for (int i = 0; i < numberOfServices; i++) {
					System.out.println(servicesList[i][0]);
					System.out.println(servicesList[i][1]);
					servicesArray
							.add(new Service(servicesList[i][0], servicesList[i][1], servicesList[i][2]));
				}

			}
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		Collections.sort(servicesArray, new ServiceComparator());
		
		return servicesArray;
		
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
			System.out.println("----------------------------\n" + "1. Change Business \n" + "2. View Availability \n"
					+ "3. View Business Opening Days and Time \n" + "4. View Services \n" + "5. Logout \n"
					+ "----------------------------");
		}
	}
	
	public boolean isNumericAndPositive(String str){
		try{
			int d = Integer.parseInt(str);
			if(d<0){
				return false;
			}
		}
		catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}

}
