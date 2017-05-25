package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import utility.Utility;

import java.time.LocalDateTime;

public class ScheduleManagement {
	
	public static void interfaceShowSchedule() {
		
		List<String> schedule = new ArrayList<String>();
		
		EmployeeManagement.listEmployees();
		String employeeID = requestID();
		
		schedule = scheduleGet(employeeID);
		schedulePrint(schedule);
		
	}
	
	public static void interfaceShowAvailability() {

		List<String> schedule = new ArrayList<String>();
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		int i;
		
		int[] minDate = new int[3];
		int[] dateChange = {0,0,7};
		
		Calendar date = Calendar.getInstance();
		minDate[0] = date.get(Calendar.YEAR);
		minDate[1] = date.get(Calendar.MONTH);
		minDate[2] = date.get(Calendar.DAY_OF_MONTH);
		
		int[] maxDate = Utility.dateManipulator(minDate, dateChange);
		
		BufferedReader reader = null;
				
		try {
			
			reader = new BufferedReader(new FileReader(Utility.employeeList));
			i = 0;
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				i++;
				System.out.print(i + ". ");
				System.out.println(dataValues[0] + " - " + dataValues[1] + " " + dataValues[2]);
				
				schedulePrint(scheduleDateBoundaries(scheduleGet(dataValues[0]),minDate,maxDate));
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}		
		
	}
	
	public static void interfaceShowGeneralAvailability() {

		List<String> schedule = new ArrayList<String>();
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		int[] minDate = new int[3];
		int[] dateChange = {0,0,7};
		
		Calendar date = Calendar.getInstance();
		minDate[0] = date.get(Calendar.YEAR);
		minDate[1] = date.get(Calendar.MONTH);
		minDate[2] = date.get(Calendar.DAY_OF_MONTH);
		
		int[] maxDate = Utility.dateManipulator(minDate, dateChange);
		
		BufferedReader reader = null;
				
		try {
			
			reader = new BufferedReader(new FileReader(Utility.employeeList));
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				schedule.addAll(scheduleGet(dataValues[0]));
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		schedule = scheduleRemoveNonFree(schedule);
		schedule = scheduleDateBoundaries(schedule, minDate, maxDate);
		schedule = scheduleSort(schedule);
		schedule = scheduleRemoveDuplicates(schedule);
		
		schedulePrint(schedule);
		
	}
	
	public static List<String> returnGeneralAvailability() {

		List<String> schedule = new ArrayList<String>();
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		int[] minDate = new int[3];
		int[] dateChange = {0,0,7};
		
		Calendar date = Calendar.getInstance();
		minDate[0] = date.get(Calendar.YEAR);
		minDate[1] = date.get(Calendar.MONTH) + 1;
		minDate[2] = date.get(Calendar.DAY_OF_MONTH);
		
		int[] maxDate = Utility.dateManipulator(minDate, dateChange);
		
		BufferedReader reader = null;
				
		try {
			
			reader = new BufferedReader(new FileReader(Utility.employeeList));
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				schedule.addAll(scheduleGet(dataValues[0]));
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		System.out.println(schedule.size());
		
		schedule = scheduleRemoveNonFree(schedule);
		System.out.println(schedule.size());
		schedule = scheduleDateBoundaries(schedule, minDate, maxDate);
		System.out.println(schedule.size());
		schedule = scheduleSort(schedule);
		System.out.println(schedule.size());
		schedule = scheduleRemoveDuplicates(schedule);
		System.out.println(schedule.size());
		
		return schedule;
		
	}
	
	
	public static void interfaceAddSchedule() {

		EmployeeManagement.listEmployees();
		String employeeID = requestID();
		schedulePrint(scheduleGet(employeeID));
		int[] dateTime = requestDate();
		
		employeeScheduleAdd(employeeID, dateTime);
		
	}
	
	public static void interfaceRemoveSchedule() {

		EmployeeManagement.listEmployees();
		String employeeID = requestID();
		schedulePrint(scheduleGet(employeeID));
		int[] dateTime = requestDate();
		
		employeeScheduleRemove(employeeID, dateTime);
		
	}
	
	private static int[] requestDate() {
		
		Scanner consoleReader = new Scanner(System.in);
		
		int[] dateTime = new int[5];
		
		System.out.println("Year: ");
		dateTime[0] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Month:");
		dateTime[1] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Day:");
		dateTime[2] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Time From:");
		dateTime[3] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Time To:");
		dateTime[4] = Integer.parseInt(consoleReader.nextLine());
		
		return dateTime;
		
	}
	
	//allows for the booking of a recurring day work schedule
	public static int[] requestDay() {
		
		Scanner consoleReader = new Scanner(System.in);
		
		String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		
		int[] dayTime = new int[3];
		
		System.out.println("Day:");
		
		//prints out the days of the week, numbered
		for (int i = 0; i < 7; i++) {
			
			System.out.println((i+1) + ". " + weekDays[i]);
			
		}
		
		dayTime[0] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Time From:");
		dayTime[1] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Time To:");
		dayTime[2] = Integer.parseInt(consoleReader.nextLine());
		
		consoleReader.close();
		
		return dayTime;
		
	}
	
	//contains console code to request ID. TODO: Add return to main menu on blank input
	public static String requestID() {
		
		String employeeID = null;

		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		BufferedReader reader = null;
		Scanner consoleReader = new Scanner(System.in);
		
		while (true) {
			
			try {
				
				reader = new BufferedReader(new FileReader(Utility.employeeList));
					
					System.out.println("ID: ");
					employeeID = consoleReader.nextLine();
					
					while ((currentLine = reader.readLine()) != null) {
						
						dataValues = currentLine.split(deliminator);
						
						if (dataValues[0].equals(employeeID)) {
							
							//prints out name of employee
							System.out.println(dataValues[1] + " " + dataValues[2]);
							
							reader.close();
							
							return employeeID;
							
						}
						
					}
					
					System.out.println("Please enter a valid Employee ID");
				
			} catch (IOException ioe1) {
				
			}
			
		}
			
	}
	
	private static boolean employeeRecuringScheduleAdd(String employeeID, int[] dayTime) {
		
		List<String> employees = getEmployees();
		List<String> workSchedule = new ArrayList<String>();
		
		String employeeString;

		String deliminator = "\\|";
		String[] dataValues = null;
		int[] dataValuesInt =  new int[3];
		String currentLine;
		
		int i, j, k;
		
		for (i = 0; i < employees.size(); i++) {
			
			currentLine = employees.get(i);
			
			dataValues = currentLine.split(deliminator);
			
			if (dataValues[0].equals(employeeID)) {
				
				employeeString = employees.get(i);
				break;
				
			}
			
		}
		
		//splits the schedule into its component parts
		for (j = 1; j < dataValues.length / 3; j++) {

			workSchedule.add(dataValues[j*3] + "|" + dataValues[j*3 + 1] + "|" + dataValues[j*3 + 2]);
			
		}
		
		//determines if there is an overlap between the new element and existing ones
		for (j = 0; j < workSchedule.size(); j++) {
			
			currentLine = workSchedule.get(j);

			dataValues = currentLine.split(deliminator);
			
			for (k = 0; k < 3; k++) {
				
				dataValuesInt[k] = Integer.parseInt(dataValues[k]);
			
			}
			
			//checks if the day is the same
			if (dayTime[0] == dataValuesInt[0]) {
				
				//checks if there is an overlap
				if (dayTime[1] <= dataValuesInt[2] && dayTime[1] >= dataValuesInt[1] || 
						dayTime[2] >= dataValuesInt[1] && dayTime[2] <= dataValuesInt[2]) {
					
					//merges them if so
					if (dayTime[1] > dataValuesInt[1]) {
						
						dayTime[1] = dataValuesInt[1];
						
					}
					
					if (dayTime[2] < dataValuesInt[2]) {
						
						dayTime[2] = dataValuesInt[2];
						
					}
					
					//removes the current element so that dayTime can then be checked against other elements without producing duplicates
					workSchedule.remove(j);
					
					//steps i back one to ensure that we don't skip elements
					j--;
				
				}
				
			}
			
		}
		
		employeeString = dataValues[0] + "|" +  dataValues[1] + "|" +  dataValues[2];
		
		for (j = 0; j < dataValuesInt.length / 3; j++) {
			
			employeeString = employeeString + "|" + dataValuesInt[j*3] + "|" + dataValuesInt[j*3 + 1] + "|" + dataValuesInt[j*3 + 2];
			
		}
		
		employees.set(i, employeeString);
		
		return saveEmployees(employees);
		
	}
	
	//
	public static boolean scheduleUpdate(String employeeID) {
		
		List<String> schedule = new ArrayList<String>();
		List<String> employees = getEmployees();
		List<String> workSchedule = new ArrayList<String>();
		String employeeString;
		
		String deliminator = "\\|";
		String[] dataValues;
		int[] dataValuesInt = new int[3];
		String currentLine;
		
		int dayDifference;
		
		int[] minDate = new int[3];
		int[] dateChange = {0,0,0};
		
		int[] targetDate;
		int[] outputDate = new int[5];
		
		Calendar date = Calendar.getInstance();
		minDate[0] = date.get(Calendar.YEAR);
		minDate[1] = date.get(Calendar.MONTH);
		minDate[2] = date.get(Calendar.DAY_OF_MONTH);
		
		int weekday = date.get(Calendar.DAY_OF_WEEK);
		
		int i, j, k;
		
		for (i = 0; i < employees.size(); i++) {
			
			currentLine = employees.get(i);
			
			dataValues = currentLine.split(deliminator);
			
			if (dataValues[0].equals(employeeID)) {
				
				employeeString = employees.get(i);
				break;
				
			}
			
		}
		
	
		for (j = 0; j < workSchedule.size(); j++) {
					
			currentLine = workSchedule.get(j);

			dataValues = currentLine.split(deliminator);
					
			for (k = 0; k < 3; k++) {
						
				dataValuesInt[k] = Integer.parseInt(dataValues[k]);
					
			}
			
			dayDifference = dataValuesInt[0] - weekday;
			
			if (dayDifference <= 0) {
				
				dayDifference += 7;
				
			}
			
			dateChange[2] = dayDifference;
			
			targetDate = Utility.dateManipulator(minDate, dateChange);
			
			outputDate[0] = targetDate[0];
			outputDate[1] = targetDate[1];
			outputDate[2] = targetDate[2];
			outputDate[3] = dataValuesInt[1];
			outputDate[4] = dataValuesInt[2];
			
			employeeScheduleAdd(employeeID, outputDate);
			
		}
		
		return false;
		
	}
	
	public static List<String> getEmployees() {
		
		List<String> data = new ArrayList<String>();
		
		String currentLine;
		
		BufferedReader reader = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(EmployeeManagement.employeeList));
			
			while ((currentLine = reader.readLine()) != null) {
				
				data.add(currentLine);
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		return data;
		
	}
	
	//splits an employees file into either specific block schedules or the recurring schedule
	public static List<String> splitSchedule(String employeeID, Boolean recurringSchedule) {
		
		List<String> combinedSchedule = new ArrayList<String>();
		List<String> schedule = new ArrayList<String>();
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		//states the number of items expected on each line; three if recurring, five if block
		int elementCount;
		
		if (recurringSchedule) {
			
			elementCount = 3;
			
		} else {
			
			elementCount = 5;
			
		}
		
		combinedSchedule = scheduleGet(employeeID);
		
		//searches through full schedule for the relevent lines
		for (int i = 0; i < combinedSchedule.size(); i++) {
			
			currentLine = combinedSchedule.get(i);
			
			dataValues = currentLine.split(deliminator);
			
			if (dataValues.length == elementCount) {
				
				schedule.add(currentLine);
				
			}
			
		}
		
		return schedule;
		
	}
	
	
	//adds to the employees schedule; returns true if successful
	public static boolean employeeScheduleAdd(String employeeID, int[] dateTime) {
		
		BufferedWriter writer = null;
		
		int i;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(employeeID + "Bookings.txt", true));

			//saves a separate item into the employee specific folder for each booking block
			for (dateTime[3] = dateTime[3]; dateTime[3] < dateTime[4]; dateTime[3] = Utility.timeManipulator(dateTime[3], Utility.blockTime)) {
				
				for (i = 0; i < 4; i++) {
					
					writer.write(Integer.toString(dateTime[i]));
					writer.write("|");
					
				}
				
				writer.write("Free");

				writer.newLine();
				
			}
			
			
		} catch (IOException ioe2) {
			
		} finally {
			
			if ( writer != null) {
				try {
					writer.close();
				} catch (IOException ioe3) {
					
				}
				
			} else {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	public static void employeeScheduleRemove(String employeeID, int[] dateTime) {

		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		String[] data = new String[999];
		
		int size = 0;
		
		BufferedReader reader = null;
		
		//reads employee detail file into memory
		try {
			
			reader = new BufferedReader(new FileReader(employeeID + "Bookings.txt"));
			
			while ((currentLine = reader.readLine()) != null) {

				dataValues = currentLine.split(deliminator);
				
				if (Integer.parseInt(dataValues[0]) == dateTime[0] && 
						Integer.parseInt(dataValues[1]) == dateTime[1] &&
						Integer.parseInt(dataValues[2]) == dateTime[2] &&
						Integer.parseInt(dataValues[3]) >= dateTime[3] &&
						Integer.parseInt(dataValues[3]) < dateTime[4]) {
						
					//date is to be ignored
						
				} else {
					
					data[size] = currentLine;
					size++;
					
				}
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		saveEmployeeSchedule(employeeID, data, size);
		
	}
	
	public static List<String> scheduleGet(String employeeID) {

		List<String> data = new ArrayList<String>();
		
		String currentLine;
		
		BufferedReader reader = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(employeeID + "Bookings.txt"));
			
			while ((currentLine = reader.readLine()) != null) {
				
				data.add(currentLine);
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		return data;
		
	}
	
	
	public static List<String> scheduleRemoveNonFree(List<String> schedule) {

		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		int isFree;
		int i;
		
		for(i = 0; i < schedule.size(); i++) {
			
			currentLine = schedule.get(i);
			
			dataValues = currentLine.split(deliminator);
			
			isFree = dataValues[4].compareTo("Free");
			
			if (isFree != 0) {
				
				schedule.remove(i);
				i--;
				
			}
			
		}
		
		return schedule;
		
	}
	
	public static List<String> scheduleSort(List<String> schedule) {

		String deliminator = "\\|";
		String[] currentValues, futureValues;
		int[] currentIntValues = new int[4];
		int[] futureIntValues = new int[4];
		String currentLine, futureLine;
		
		String holder;
		
		int i, j, k;
		
		for (i = 0; i < schedule.size() - 1; i++) {
			
			currentLine = schedule.get(i);
			currentValues = currentLine.split(deliminator);
			
			for (k = 0; k < 4; k++) {
				
				currentIntValues[k] = Integer.parseInt(currentValues[k]);
				
			}
			
			for (j = i + 1; j < schedule.size(); j++) {
				
				futureLine = schedule.get(i);
				futureValues = futureLine.split(deliminator);
				
				for (k = 0; k < 4; k++) {
					
					futureIntValues[k] = Integer.parseInt(futureValues[k]);
					
				}
				
				//checks if the positionally higher value is earlier
				if (currentIntValues[0] > futureIntValues[0] ||
						(currentIntValues[0] == futureIntValues[0] && currentIntValues[1] > futureIntValues[1]) ||
						(currentIntValues[0] == futureIntValues[0] && currentIntValues[1] == futureIntValues[1] &&
						currentIntValues[2] > futureIntValues[2]) || (currentIntValues[0] == futureIntValues[0] && 
						currentIntValues[1] == futureIntValues[1] && currentIntValues[2] == futureIntValues[2] && 
						currentIntValues[3] > futureIntValues[3])) {
					
					holder = schedule.get(i);
					schedule.set(i, schedule.get(j));
					schedule.set(j, holder);
					
				}
				
			}
			
		}
		
		return schedule;
		
	}
	
	public static List<String> scheduleRemoveDuplicates(List<String> schedule) {
		
		int i;
		
		for (i = 0; i < schedule.size() - 1; i++) {
			
			if ((schedule.get(i)).compareTo(schedule.get(i+1)) == 0) {
				
				schedule.remove(i);
				
				//counters the for loops attempt to move forward
				i--;
				
			}
			
		}
		
		return schedule;
		
	}
	
	public static List<String> scheduleDateBoundaries(List<String> schedule, int[] dateMin, int[] dateMax) {
		
		int i, j;
		String deliminator = "\\|";
		String currentLine;
		String[] currentValues;
		
		int[] mod = {0,0,0};
		int[] output = new int[3];
		
		boolean dateInBounds;
		
		for (i = 0; i < schedule.size() - 1; i++) {
			
			currentLine = schedule.get(i);
			currentValues = currentLine.split(deliminator);
			
			j = 0;
			mod[2] = 0;
			dateInBounds = false;
			
			while (j < 8) {
				
				mod[2] = j;
				
				j++;
				
				output = Utility.dateManipulator(dateMin, mod);
				
				if (output[0] == Integer.parseInt(currentValues[0]) &&
						output[1] == Integer.parseInt(currentValues[1]) &&
						output[2] == Integer.parseInt(currentValues[2])) {
					
					dateInBounds = true;
					break;
					
				}
				
			}
			
			if (dateInBounds) {

				//date can stay
				
			} else {
				
				schedule.remove(i);
				
				//counters the for loops attempt to move forward
				i--;
				
			}
			
		}
		
		return schedule;
		
		
		
	}
	
	public static void schedulePrint(List<String> schedule) {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		int i;
		
		for (i = 0; i < schedule.size(); i++) {
			
			currentLine = schedule.get(i);
			dataValues = currentLine.split(deliminator);
			
			System.out.print("Scheduled for ");
			System.out.print(dataValues[0] + "/" + dataValues[1] + "/" + dataValues[2]);
			System.out.print(" from ");
			System.out.println(dataValues[3]);
			
		}
		
	}
	
	//allows bookings to be made
	public static void scheduleBooking(String employeeID, String customerID, int[] dateTime) {

		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		String[] data = new String[999];
		
		int size = 0;
		
		BufferedReader reader = null;
		
		//reads employee detail file into memory
		try {
			
			reader = new BufferedReader(new FileReader(employeeID + "Bookings.txt"));
			
			while ((currentLine = reader.readLine()) != null) {

				dataValues = currentLine.split(deliminator);
				
				if (Integer.parseInt(dataValues[0]) == dateTime[0] && 
						Integer.parseInt(dataValues[1]) == dateTime[1] &&
						Integer.parseInt(dataValues[2]) == dateTime[2] &&
						Integer.parseInt(dataValues[3]) >= dateTime[3] &&
						Integer.parseInt(dataValues[3]) < dateTime[4]) {

					currentLine = dataValues[0] + "|" + dataValues[1] + "|" + dataValues[2] + "|" + dataValues[3] + "|" + customerID;
					
					//saves slot to customers scheduling information
					//saveCustomerSchedule(customerID, dataValues[0] + "|" + dataValues[1] + "|" + dataValues[2] + "|" + dataValues[3] + "|" + employeeID);
						
				}
					
				data[size] = currentLine;
				size++;
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		saveEmployeeSchedule(employeeID, data, size);
		
	}
	
	//returns true if success, false if failure
	private static boolean saveEmployeeSchedule(String employeeID, String[] data, int size) {
		
		BufferedWriter writer = null;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(employeeID + "Bookings.txt", false));
			
			for (int j = 0; j < size; j++) {

				writer.write(data[j]);
				writer.newLine();
				
			}
			
		} catch (IOException ioe2) {
			
		} finally {
			
			if ( writer != null) {
				try {
					writer.close();
				} catch (IOException ioe3) {
					
				}
				
			} else {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	private static boolean saveEmployees(List<String> data) {
		
		BufferedWriter writer = null;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(EmployeeManagement.employeeList, false));
			
			for (int j = 0; j < data.size(); j++) {

				writer.write(data.get(j));
				writer.newLine();
				
			}
			
		} catch (IOException ioe2) {
			
		} finally {
			
			if ( writer != null) {
				try {
					writer.close();
				} catch (IOException ioe3) {
					
				}
				
			} else {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	private static boolean saveCustomerSchedule(String customerID, String data) {
		
		BufferedWriter writer = null;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(customerID + ".txt", true));
			
			writer.write(data);
			writer.newLine();
			
		} catch (IOException ioe2) {
			
		} finally {
			
			if ( writer != null) {
				try {
					writer.close();
				} catch (IOException ioe3) {
					
				}
				
			} else {
				
				return false;
				
			}
			
		}
		
		return true;
	}

}
