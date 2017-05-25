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

public class ScheduleManagement2 {
	
	public static void main(String[] args) {
		
		updateAllSchedules();
		
	}
	
	public static void updateAllSchedules() {
		
		BufferedReader reader = null;

		String deliminator = "\\|";
		
		String currentLine;
		String[] dataValues;
		
		try {
			
			reader = new BufferedReader(new FileReader(Utility.employeeList));
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				scheduleUpdate(dataValues[0]);
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}		
		
	}
	
	public static void interfaceAddRecuringSchedule() {
		
		EmployeeManagement.listEmployees();
		String employeeID = InputRequest.requestID();
		int[] dateTime = InputRequest.requestDay();
		
		employeeRecuringScheduleAdd(employeeID, dateTime);
		
	}
	
	public static void interfaceShowSchedule() {
		
		EmployeeManagement.listEmployees();
		String employeeID = InputRequest.requestID();
		printEmployeeSchedule(employeeID, false);
		
	}
	
	public static void employeeRecuringScheduleRemove () {
		
		Scanner consoleReader = new Scanner(System.in);
		
		EmployeeManagement.listEmployees();
		String employeeID = InputRequest.requestID();
		printEmployeeSchedule(employeeID, true);
		
		System.out.println("Select item to remove:");
		int item = Integer.parseInt(consoleReader.nextLine());
		
		List<String> schedule = getEmployeeSchedule(employeeID);
		
		schedule.remove(item);
		
		saveEmployeeSchedule(employeeID, schedule);
		
	}
	
	public static void employeeRecuringScheduleRemoveGUI (int item, String employeeID) {
		
		List<String> schedule = getEmployeeSchedule(employeeID);
		
		Calendar date = Calendar.getInstance();
		
		int currentDay = date.get(Calendar.DAY_OF_WEEK);
		
		//converts currentDay so Monday is 1
		if (currentDay == 0) {
			
			currentDay = 7;
			
		} else {
			
			currentDay--;
			
		}
		
		
		int[] currentDate = {date.get(Calendar.YEAR), (date.get(Calendar.MONTH) + 1), date.get(Calendar.DAY_OF_MONTH)};

		
		System.out.println(currentDay);
		
		String[] dataValues;
		
		String deliminator = "\\|";
		
		int[] dateValue = new int[3];
		int[] outputValue = new int[5];
		int[] mod = {0,0,0};
		
		dataValues = schedule.get(item).split(deliminator);
		
		if (Integer.parseInt(dataValues[0]) != currentDay) {
			
			mod[2] = Integer.parseInt(dataValues[0]) - currentDay;
			
			System.out.println(mod[2]);
			
			if (mod[2] < 0) {
				
				mod[2] += 7;
				
			}
			
			dateValue = Utility.dateManipulator(currentDate, mod);
			
		} else {
			
			dateValue = currentDate;
			
		}

		outputValue[0] = dateValue[0];
		outputValue[1] = dateValue[1];
		outputValue[2] = dateValue[2];
		outputValue[3] = Integer.parseInt(dataValues[1]);
		outputValue[4] = Integer.parseInt(dataValues[2]);
		
		System.out.println(outputValue[0] + "//" + outputValue[1] + "//" + outputValue[2] + "//" + outputValue[3] + "//" + outputValue[4]);
		
		ScheduleManagement.employeeScheduleRemove(employeeID, outputValue);
		
		schedule.remove(item);
		
		saveEmployeeSchedule(employeeID, schedule);
		
	}
	
	public static boolean employeeRecuringScheduleAdd (String employeeID, int[] dayTime) {
		
		BufferedWriter writer = null;
		
		int i;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(employeeID + "Schedule.txt", true));

			writer.write(Integer.toString(dayTime[0]));
			writer.write("|");
			writer.write(Integer.toString(dayTime[1]));
			writer.write("|");
			writer.write(Integer.toString(dayTime[2]));

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
		
		employeeScheduleDuplicateClean(employeeID);
		
		return true;
		
	}
	
	public static void employeeScheduleDuplicateClean(String employeeID) {
		
		List<String> schedule = getEmployeeSchedule(employeeID);

		String deliminator = "\\|";
		
		int i, j, k;
		
		String comparisonValuesA[];
		int comparisonValuesAInt[] = new int[3];
		String comparisonValuesB[];
		int comparisonValuesBInt[] = new int[3];
		
		
		for (i = 1; i < schedule.size(); i++) {
			
			comparisonValuesA = schedule.get(i).split(deliminator);
			
			for (k = 0; k < 3; k++) {
				
				comparisonValuesAInt[k] = Integer.parseInt(comparisonValuesA[k]);
			
			}
			
			for (j = (i+1); j < schedule.size(); j++) {
				
				comparisonValuesB = schedule.get(j).split(deliminator);
				
				for (k = 0; k < 3; k++) {
					
					comparisonValuesBInt[k] = Integer.parseInt(comparisonValuesB[k]);
				
				}
				
				//checks if the day is the same
				if (comparisonValuesAInt[0] == comparisonValuesBInt[0]) {
					
					//checks if there is an overlap
					if (comparisonValuesAInt[1] <= comparisonValuesAInt[2] && comparisonValuesAInt[1] >= comparisonValuesBInt[1] || 
							comparisonValuesAInt[2] >= comparisonValuesBInt[1] && comparisonValuesAInt[2] <= comparisonValuesBInt[2]) {
						
						//merges them if so
						if (comparisonValuesAInt[1] > comparisonValuesBInt[1]) {
							
							comparisonValuesAInt[1] = comparisonValuesBInt[1];
							
						}
						
						if (comparisonValuesAInt[2] < comparisonValuesBInt[2]) {
							
							comparisonValuesAInt[2] = comparisonValuesBInt[2];
							
						}
						
						schedule.set(i, (comparisonValuesAInt[0] + "|" + comparisonValuesAInt[1] + "|" + comparisonValuesAInt[2]));
						
						//removes the current element so that dayTime can then be checked against other elements without producing duplicates
						schedule.remove(j);
						
						//steps i back one to ensure that we don't skip elements
						j--;
					
					}
					
				}
				
			}
			
		}
		
		saveEmployeeSchedule(employeeID, schedule);
		
	}
	
	public static void scheduleUpdate(String employeeID) {
		
		List<String> schedule = getEmployeeSchedule(employeeID);
		
		String deliminator = "\\|";
		
		String[] dataValue;
		int[] dateMod = {0,0,0};
		
		int[] dateManipOut = new int[3];
		
		int[] scheduleAdd = new int[5];

		Calendar date = Calendar.getInstance();
		
		int currentDay = date.get(Calendar.DAY_OF_WEEK);
		
		//converts currentDay so Monday is 1
		if (currentDay == 0) {
			
			currentDay = 7;
			
		} else {
			
			currentDay--;
			
		}
		
		System.out.println("Current Day = " + currentDay);
		int[] currentDate = {date.get(Calendar.YEAR), (date.get(Calendar.MONTH) + 1), date.get(Calendar.DAY_OF_MONTH)};
		
		int i, j, k;
		
		/*
		 * determine the number of days it has been since last update
		 * and update the difference between that number + 7 and the
		 * current date + 7
		*/
		int[] currentUpdateDate = new int[3];
		
		dataValue = schedule.get(0).split(deliminator);
		
		for (i = 0; i < 3; i++) {
			
			currentUpdateDate[i] = Integer.valueOf(dataValue[i]);
			
		}
		
		// I apologize for this crime
		// Determines what days it needs to add to eXYZBookings.txt
		// If i is 0, none, if I is one, one needs to be added on the end etc
		for (i = 0; i <= 7; i++) {
			
			dateMod[2] = i;
			
			dateManipOut = Utility.dateManipulator(currentUpdateDate, dateMod);
			
			if (dateManipOut[0] == currentDate[0] && dateManipOut[1] == currentDate[1] && dateManipOut[2] == currentDate[2]) {
				
				break;
				
			}
			
			//counts what day we are looking at
			if (i != 0 && i <= 7) {
				
				currentDay++;
				
			}
			
		}
		
		System.out.println("Current Day = " + currentDay);
		
		// sets currentDate to where we want to start adding
		dateMod[2] = 7 - dateMod[2];
		currentDate = Utility.dateManipulator(currentDate, dateMod);
		
		
		
		for (j = 1; j <= i; j++) {
			
			// loops around the week
			while (currentDay > 7) {
				
				currentDay -= 7;
				
			}
			
			for (k = 1; k < schedule.size(); k++) {
				
				dataValue = schedule.get(k).split(deliminator);
				
				System.out.println("Schedule date = " + dataValue[0] + " Current Day = " + currentDay);
				
				if (Integer.parseInt(dataValue[0]) == currentDay) {
					
					scheduleAdd[3] = Integer.parseInt(dataValue[1]);
					scheduleAdd[4] = Integer.parseInt(dataValue[2]);
					
					scheduleAdd[0] = currentDate[0];
					scheduleAdd[1] = currentDate[1];
					scheduleAdd[2] = currentDate[2];
					
					ScheduleManagement.employeeScheduleAdd(employeeID, scheduleAdd);
					
				}
				
				
			}
			
			//keeps track of the date we are looking at
			dateMod[2] = 1;
			
			currentDate = Utility.dateManipulator(currentDate, dateMod);
			
			//keeps track of the day of the week we are working on
			currentDay++;
			
			
		}
		
		saveUpdateDate(employeeID);
		
	}
	
	public static List<String> getEmployeeSchedule(String employeeID) {
		
		List<String> data = new ArrayList<String>();
		
		String currentLine;
		
		BufferedReader reader = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(employeeID + "Schedule.txt"));
			
			while ((currentLine = reader.readLine()) != null) {
				
				data.add(currentLine);
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {

			ioe1.printStackTrace();
			
		}
		
		return data;
		
	}
	
	public static void printEmployeeSchedule(String employeeID, boolean numbered) {
		
		List<String> schedule = getEmployeeSchedule(employeeID);
		
		String[] dataValues;
		
		String deliminator = "\\|";
		
		String[] weekDays = {"Ix", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		
		for (int i = 1; i < schedule.size(); i++) {
			
			dataValues = schedule.get(i).split(deliminator);
			
			if (numbered) {
				
				System.out.print(i + ". ");
				
			}
			
			System.out.println("Scheduled on " + weekDays[Integer.parseInt(dataValues[0])] + " between " + dataValues[1] + " and " + dataValues[2]);
			
		}
		
	}
	
	public static boolean saveUpdateDate(String employeeID) {
		
		List<String> schedule = getEmployeeSchedule(employeeID);

		Calendar date = Calendar.getInstance();
		String currentDate = date.get(Calendar.YEAR) + "|" + (date.get(Calendar.MONTH) + 1) + "|" + date.get(Calendar.DAY_OF_MONTH);
		
		schedule.set(0, currentDate);
		
		return saveEmployeeSchedule(employeeID, schedule);
		
	}
	
	public static boolean saveEmployeeSchedule(String employeeID, List<String> data) {
		
		BufferedWriter writer = null;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(employeeID + "Schedule.txt", false));
			
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
	
	//returns true if success, false if failure
	public static boolean saveBookingSchedule(String employeeID, List<String> data) {
			
			BufferedWriter writer = null;
			
			try {
				
				//wraps FileWriter in BufferedWrite, in order to use newLine()
				writer = new BufferedWriter(new FileWriter(employeeID + "Bookings.txt", false));
				
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

}
