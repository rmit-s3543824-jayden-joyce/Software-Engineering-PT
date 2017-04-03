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
				
				schedule = scheduleDateBoundaries(scheduleGet(dataValues[0]),minDate,maxDate);
				schedulePrint(schedule);
				
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
	
	//contains console code to request ID. TODO: Add return to main menu on blank input
	private static String requestID() {
		
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
	
	
	//adds to the employees schedule; returns true if successful
	private static boolean employeeScheduleAdd(String employeeID, int[] dateTime) {
		
		BufferedWriter writer = null;
		
		int i;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(employeeID + ".txt", true));

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
			
			reader = new BufferedReader(new FileReader(employeeID + ".txt"));
			
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
			
			reader = new BufferedReader(new FileReader(employeeID + ".txt"));
			
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
		
		int i;
		String deliminator = "\\|";
		String currentLine;
		String[] currentValues;
		
		for (i = 0; i < schedule.size() - 1; i++) {
			
			currentLine = schedule.get(i);
			currentValues = currentLine.split(deliminator);
			
			if (dateMin[0] <= Integer.parseInt(currentValues[0]) && dateMin[1] <= Integer.parseInt(currentValues[1]) && dateMin[2] <= Integer.parseInt(currentValues[2]) &&
					dateMax[0] >= Integer.parseInt(currentValues[0]) && dateMax[1] >= Integer.parseInt(currentValues[1]) && dateMax[2] >= Integer.parseInt(currentValues[2])) {

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
	
	
	
	/*public static void employeeScheduleShow(String employeeID, boolean availableOnly, int[] date) {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		int isFree;
		
		BufferedReader reader = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(employeeID + ".txt"));
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				isFree = dataValues[4].compareTo("Free");
				
				if (availableOnly && isFree != 0) {
					
					//do not display
					
				} else {
					
					if (date == null || date[0] == Integer.parseInt(dataValues[0]) && date[1] == Integer.parseInt(dataValues[1]) && date[2] == Integer.parseInt(dataValues[2])) {

						System.out.print("Scheduled for ");
						System.out.print(dataValues[0] + "/" + dataValues[1] + "/" + dataValues[2]);
						System.out.print(" from ");
						System.out.println(dataValues[3]);
						//TODO: Display client if currently booked
						
					}
					
				}
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
	}*/
	
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
			
			reader = new BufferedReader(new FileReader(employeeID + ".txt"));
			
			while ((currentLine = reader.readLine()) != null) {

				dataValues = currentLine.split(deliminator);
				
				if (Integer.parseInt(dataValues[0]) == dateTime[0] && 
						Integer.parseInt(dataValues[1]) == dateTime[1] &&
						Integer.parseInt(dataValues[2]) == dateTime[2] &&
						Integer.parseInt(dataValues[3]) >= dateTime[3] &&
						Integer.parseInt(dataValues[3]) < dateTime[4]) {

					currentLine = currentLine + customerID;
					
					//saves slot to customers scheduling information
					saveCustomerSchedule(customerID, dataValues[0] + "|" + dataValues[1] + "|" + dataValues[2] + "|" + dataValues[3] + "|" + employeeID);
						
				}
					
				data[size] = currentLine;
				size++;
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		saveEmployeeSchedule(employeeID, data, size);
		
	}
	
	/*//shows times that there is at least one worker available
	public static void showGeneralAvailability() {
		
		int[][] data = new int[999][4];
		int availableCount = 0;
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		int isFree;
		int i;
		
		BufferedReader employeeListReader = null;
		BufferedReader employeeScheduleReader = null;
		
		try {
			
			employeeListReader = new BufferedReader(new FileReader(Utility.employeeList));
			
			while ((currentLine = employeeListReader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				try {
					
					employeeScheduleReader = new BufferedReader(new FileReader(dataValues[0] + ".txt"));
					
					while ((currentLine = employeeScheduleReader.readLine()) != null) {
						
						dataValues = currentLine.split(deliminator);
						
						isFree = dataValues[4].compareTo("Free");
						
						if (isFree == 0) {
							
							for (i = 0; i < dataValues.length; i++) {
								
								data[availableCount][i] = Integer.parseInt(dataValues[i]);
								
							}
							
							availableCount++;
							
						}
						
					}
					
					employeeScheduleReader.close();
					
				} catch (IOException ioe1) {
					
				}
				
			}
			
			employeeListReader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		data = sortAvailability(data, availableCount);
		data = removeDuplicates(data, availableCount);
		
	}
	
	private static int[][] sortAvailability(int[][] availabilityData, int dataPoints) {
		
		int i, j, k;
		int[] holding = new int[4];
		
		for (i = 0; i < dataPoints - 1; i++) {
			
			for (j = i + 1; j < dataPoints; j++) {
				
				//checks if the positionally higher value is earlier
				if (availabilityData[i][0] > availabilityData[j][0] ||
						(availabilityData[i][0] == availabilityData[j][0] && availabilityData[i][1] > availabilityData[j][1]) ||
						(availabilityData[i][0] == availabilityData[j][0] && availabilityData[i][1] == availabilityData[j][1] &&
						availabilityData[i][2] > availabilityData[j][2]) || (availabilityData[i][0] == availabilityData[j][0] && 
						availabilityData[i][1] == availabilityData[j][1] && availabilityData[i][2] == availabilityData[j][2] && 
						availabilityData[i][3] > availabilityData[j][3])) {
					
					for (k = 0; k < holding.length; k++) {
						
						holding[k] = availabilityData[i][k];
						availabilityData[i][k] = availabilityData[j][k];
						availabilityData[j][k] = holding[k];
						
					}
					
				}
				
			}
			
		}
		
		return availabilityData;
		
	}
	
	// can only remove duplicates from sorted data
	private static int[][] removeDuplicates(int[][] availabilityData, int dataPoints) {
		
		int i, j, k;
		
		i = 0;
		
		while (i < dataPoints - 1) {
			
			if ((availabilityData[i][0] == availabilityData[i + 1][0] && availabilityData[i][1] == availabilityData[i + 1][1] 
						&& availabilityData[i][2] == availabilityData[i + 1][2] && availabilityData[i][3] == availabilityData[i + 1][3])) {
					
				for (j = i + 1; j < dataPoints - 1; j++) {
					
					for (k = 0; k < 4; k++) {
						
						availabilityData[j][k] = availabilityData[j + 1][k];
						
					}	
					
				}
				
				dataPoints--;
					
			} else {
				
				i++;
				
			}
			
		}

		return availabilityData;
		
	}
	
	//shows workers and their available times
	public static void showAvailability() {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		int[] viewDate = new int[3];
		int[] modDate = {0,0,0};
		int i, j;
		
		Calendar date = Calendar.getInstance();
		viewDate[0] = date.get(Calendar.YEAR);
		viewDate[1] = date.get(Calendar.MONTH);
		viewDate[2] = date.get(Calendar.DAY_OF_MONTH);
		
		BufferedReader reader = null;
				
		try {
			
			reader = new BufferedReader(new FileReader(Utility.employeeList));
			i = 0;
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				i++;
				System.out.print(i + ". ");
				System.out.println(dataValues[0] + " - " + dataValues[1] + " " + dataValues[2]);
				
				for (j = 0; j < 7; j++) {
					
					modDate[2] = j;
					employeeScheduleShow(dataValues[0],true,Utility.dateManipulator(viewDate, modDate));
					
				}
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}		
		
	}*/
	
	//returns true if success, false if failure
	private static boolean saveEmployeeSchedule(String employeeID, String[] data, int size) {
		
		BufferedWriter writer = null;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(employeeID + ".txt", false));
			
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
