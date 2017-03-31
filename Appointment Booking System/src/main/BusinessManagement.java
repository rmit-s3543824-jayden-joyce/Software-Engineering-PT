package main;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BusinessManagement {

	private Business selectedBusiness;

	public BusinessManagement(String businessName) throws IOException {
		selectedBusiness = selectBusiness(businessName);

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

			switch (userInput) {
			case 1:
				System.out.println("Employee Management interface");
				break;

			

			case 2:
				viewSummariesOfBookings();
				System.out.println("\nPress any key to return.");
				in.nextLine();
				break;
				
			case 3:
				viewNewBookings();
				break;

			case 4:
				addService(in);
				break;

			case 5:
				return;
				
			case 6:
				setOperatingTime(in);
				break;

			}
		}

	}

	public void setOperatingTime(Scanner in) {

		String msg = in.nextLine();
		while(!isNumericAndPositive(msg)){
			System.out.println("Please enter a valid number\n"
								+"-----------------------------");
			msg = in.nextLine();
		}
		do {
			System.out.println("Opening Time");

			System.out.println("Please specify the opening hour (24HR format)");
			System.out.println("-----------------------------------------------");
			msg = in.nextLine();

			while(!isNumericAndPositive(msg)){
				System.out.println("Please enter a valid number\n"
									+"-----------------------------");
				msg = in.nextLine();
			}


			int hour = Integer.valueOf(msg);

			System.out.println("Please specify the opening minute");
			System.out.println("------------------------------------");
			msg = in.nextLine();

			while(!isNumericAndPositive(msg)){
				System.out.println("Please enter a valid number\n"
									+"-----------------------------");
				msg = in.nextLine();
			}

			int minute = Integer.valueOf(msg);
			selectedBusiness.setOpenTime(hour, minute);
		} while (selectedBusiness.getOpenTime() == null);

		do {
			System.out.println("Closing Time");
			System.out.println("Please specify the closing hour (24HR format)");
			System.out.println("-----------------------------------------------");
			msg = in.nextLine();

			while(!isNumericAndPositive(msg)){
				System.out.println("Please enter a valid number\n"
									+"-----------------------------");
				msg = in.nextLine();
			}

			int hour = Integer.valueOf(msg);

			System.out.println("Please specify the closing minute");
			System.out.println("------------------------------------");
			msg = in.nextLine();
			while(!isNumericAndPositive(msg)){
				System.out.println("Please enter a valid number\n"
									+"-----------------------------");
				msg = in.nextLine();
			}

			int minute = Integer.valueOf(msg);
			selectedBusiness.setCloseTime(hour, minute);
		} while (selectedBusiness.getCloseTime() == null);

	}
	
	public void addService(Scanner in) throws IOException{
		
		System.out.println("Please enter the name of the service\n"
							+"--------------------------------------");
		String serviceName = in.nextLine();
		
		System.out.println("Please enter the duration of the service (minutes)\n"
							+"--------------------------------------------------");
		
		
		String serviceDuration = in.nextLine();
		
		while(!isNumericAndPositive(serviceDuration)){
			System.out.println("Please enter a valid duration\n"
								+"------------------------------");
			serviceDuration = in.nextLine();
		}
		
		
		
		System.out.println("Please enther the description of the service\n"
							+"----------------------------------------------");
		
		String serviceDescription = in.nextLine();
		
		if(serviceName != null && serviceDuration != null && serviceDescription != null){
		
				StringTokenizer st = new StringTokenizer(selectedBusiness.getName()," ");
				String file_name = "";
				while(st.hasMoreTokens()){
					
					file_name += st.nextToken();
				}
				
				file_name += "Services.txt";
		
				WriteFile writer = new WriteFile(file_name,true);
				writer.writeToFile("\n"+serviceName +"|"+ serviceDuration +"|"+ serviceDescription);
		}
				
	}

	public Booking[] retrieveBooking() {
		

		StringTokenizer st = new StringTokenizer(selectedBusiness.getName()," ");
		String file_name = "";
		while(st.hasMoreTokens()){
			
			file_name += st.nextToken();
		}
		
		file_name += "Bookings.txt";
		Booking[] bookingArray = null;
		try {
			ReadFile file = new ReadFile(file_name);
			String bookingList[][] = file.retrieveBooking();
			if (bookingList == null) {
				System.out.println("\nThere is no bookings.");
				return bookingArray;
			}

			else {
				int numberOfBookings = file.readLines();
				bookingArray = new Booking[numberOfBookings];
				for (int i = 0; i < numberOfBookings; i++) {
					bookingArray[i] = new Booking(bookingList[i][0], bookingList[i][1], bookingList[i][2],
							bookingList[i][3], bookingList[i][4], bookingList[i][5], bookingList[i][6]);
				}

			}
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return bookingArray;
	}

	public void viewSummariesOfBookings() {

		Booking[] bookingList = retrieveBooking();

		System.out.println("Summaries of Bookings for " + selectedBusiness.getName());
		System.out.println("-------------------------------------------------------");
		System.out.println("Date      |Time |Status |Customer|Employee");

		for (int i = 0; i < bookingList.length; i++) {
			bookingList[i].displayBooking();
		}

	}

	public void viewNewBookings() {

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

	public void printMenu() {

		System.out.printf("\nWelcome %s. \nPlease choose your option\n", selectedBusiness.getName());
		System.out.println(
				"----------------------------\n" 
						+ "1. Employee Management \n" 
						+ "2. View Booking Summaries \n"
						+ "3. View New Bookings \n" 
						+ "4. Add service \n"
						+ "5. Logout \n" + "----------------------------");
	}
	
	public Business getSelectedBusiness(){
		return this.selectedBusiness;
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
