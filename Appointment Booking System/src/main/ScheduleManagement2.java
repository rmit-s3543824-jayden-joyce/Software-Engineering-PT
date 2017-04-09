package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.Utility;

public class ScheduleManagement2 {
	
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
		int[] dataValueInt = new int[3];
		
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
		for (i = 0; i < 7; i++) {
			
			
			
		}
		
		
		
		
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
			
		}
		
		return data;
		
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
