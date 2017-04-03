package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import utility.ReadFile;

import java.time.*;

public class CustomerManagement {

	static Business selectedBusiness;

	public CustomerManagement() throws IOException {

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
					System.out.println("Please choose the respective day of the Month (-1 to exit)\n");
					int day = 0;
					while (day != -1) {
						day = Integer.valueOf(in.nextLine());
						selectedBusiness.addAvailableDay(day);
					}
					break;

				case 5:

					do {
						System.out.println("Opening Time");

						System.out.println("Please specify the opening hour (24HR format)");
						System.out.println("-----------------------------------------------");
						msg = in.nextLine();

						while (Integer.valueOf(msg) < 0 || Integer.valueOf(msg) > 24) {
							System.out.println("Input Invalid.");
							System.out.println("Please specify the opening hour (24HR format)");
							System.out.println("-----------------------------------------------");
							msg = in.nextLine();

						}

						int hour = Integer.valueOf(msg);

						System.out.println("Please specify the opening minute");
						System.out.println("------------------------------------");
						msg = in.nextLine();

						while (Integer.valueOf(msg) < 0 || Integer.valueOf(msg) > 60) {
							System.out.println("Input Invalid.");
							System.out.println("Please specify the opening minute");
							System.out.println("------------------------------------");
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

						while (Integer.valueOf(msg) < 0 || Integer.valueOf(msg) > 24) {
							System.out.println("Input Invalid.");
							System.out.println("Please specify the closing hour (24HR format)");
							System.out.println("-----------------------------------------------");
							msg = in.nextLine();

						}
						int hour = Integer.valueOf(msg);

						System.out.println("Please specify the closing minute");
						System.out.println("------------------------------------");
						msg = in.nextLine();
						while (Integer.valueOf(msg) < 0 || Integer.valueOf(msg) > 60) {
							System.out.println("Input Invalid.");
							System.out.println("Please specify the closing minute");
							System.out.println("------------------------------------");
							msg = in.nextLine();

						}
						int minute = Integer.valueOf(msg);
						selectedBusiness.setCloseTime(hour, minute);
					} while (selectedBusiness.getCloseTime() == null);
					break;

				case 6:
					selectedBusiness = null;
					break;

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

	/*public void viewAvailability() {

		if (selectedBusiness.getAvailableDays().isEmpty()) {
			System.out.println("The business has either no available days or has not given its availability yet");
			return;
		}

		LocalDate localDate = LocalDate.now();
		LocalDate nextMonth = localDate.plusMonths(1);
		int dayOfNextMonth = nextMonth.getDayOfMonth();
		LocalDate startOfNextMonth = nextMonth.minusDays(dayOfNextMonth - 1);
		int valueOfStartDay = startOfNextMonth.getDayOfWeek().getValue();
		int dayCount = 0;
		int availableDayIndex = 0;

		System.out.println();
		System.out.println(nextMonth.getMonth());
		System.out.println(" Su| Mo| Tu| We| Th| Fr| Sa|");

		if (valueOfStartDay == 7) {
			
			dayCount = 1;
			
		} else {
			
			dayCount = -(valueOfStartDay) + 1;
			
		}

		for (; dayCount <= nextMonth.lengthOfMonth(); dayCount++) {

			if (dayCount <= 0) {
				System.out.print("   |");
			}

			else if (dayCount < 10) {
				if (selectedBusiness.getAvailableDays().get(availableDayIndex) == dayCount) {
					System.out.printf(" *%d|", dayCount);
					if (selectedBusiness.getAvailableDays().size() > availableDayIndex + 1)
						availableDayIndex++;
				} else
					System.out.printf("  %d|", dayCount);
			}

			else if (dayCount >= 10) {
				if (selectedBusiness.getAvailableDays().get(availableDayIndex) == dayCount) {
					System.out.printf("*%d|", dayCount);
					if (selectedBusiness.getAvailableDays().size() > availableDayIndex + 1)
						availableDayIndex++;
				} else
					System.out.printf(" %d|", dayCount);
			}

			if (valueOfStartDay == 7)
				if (dayCount == 7 || dayCount == 14 || dayCount == 21 || dayCount == 28) {
					System.out.println();
				}

			if (valueOfStartDay == 1)
				if (dayCount == 6 || dayCount == 13 || dayCount == 20 || dayCount == 27) {
					System.out.println();
				}

			if (valueOfStartDay == 2)
				if (dayCount == 5 || dayCount == 12 || dayCount == 19 || dayCount == 26) {
					System.out.println();
				}

			if (valueOfStartDay == 3)
				if (dayCount == 4 || dayCount == 11 || dayCount == 18 || dayCount == 25) {
					System.out.println();
				}

			if (valueOfStartDay == 4)
				if (dayCount == 3 || dayCount == 10 || dayCount == 17 || dayCount == 24 || dayCount == 31) {
					System.out.println();
				}

			if (valueOfStartDay == 5)
				if (dayCount == 2 || dayCount == 9 || dayCount == 16 || dayCount == 23 || dayCount == 30) {
					System.out.println();
				}

			if (valueOfStartDay == 6)
				if (dayCount == 1 || dayCount == 8 || dayCount == 15 || dayCount == 22 || dayCount == 29) {
					System.out.println();
				}

		}

		System.out.println();
		System.out.println("Note: Date with * is available");

		if (selectedBusiness.getOpenTime() == null || selectedBusiness.getCloseTime() == null) {
			System.out.println("The selected business has not given its opening or closing time");
		}

		else
			System.out.print("The business opens at " + selectedBusiness.getOpenTime() + " to "
					+ selectedBusiness.getCloseTime() + "\n");
	}*/

	public void printMenu() {
		if (selectedBusiness == null) {

			// Main menu
			System.out.println("\n" + "Welcome,\n" + "Please choose your option:\n" + "------------------------------\n"
					+ "1. Select Business \n" + "2. View Bookings \n" + "3. Logout \n"

					+ "------------------------------");
		}

		// Actions menu
		if (selectedBusiness != null) {
			System.out.printf("%s has been selected. Please choose your option\n", selectedBusiness.getName());
			System.out.println("----------------------------\n" + "1. Change Business \n" + "2. View Availability \n"
					+ "3. View Business Opening Days and Time \n" + "4. Return to Main Menu \n"
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
