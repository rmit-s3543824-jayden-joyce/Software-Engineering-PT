package utility;

import java.io.File;
import java.io.IOException;

public class Utility {
	
	public static int blockTime = 30;
	public static String employeeList = "employeeList.txt";
	
	public static void main(String[] args) {
		
		int[] current = {1999,2,20};
		int[] mod = {1,4,34};
		
		int[] result = new int[3];
		
		result = dateManipulator(current, mod);
		
		System.out.println(result[0] + "/" + result[1] + "/" + result[2]);
		
	}
	
	//allows for the creation of files. Intended for appointment booking
	public static boolean createFile(String fileName) {
		
		boolean creationSuccess;
		File newFile = new File(fileName + ".txt");
		
		try {
			creationSuccess = newFile.createNewFile();
			
			if (!creationSuccess) {
				
				System.out.println("File failed to be created. Error");
				return false;
				
			} else {
				
				return true;
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	//allows for the addition of military time. Does not note if it passes over midnight
	public static int timeManipulator(int input, int modification) {
		
		int inputHours, inputMinutes, modHours, modMinutes;
		int resultHours, resultMinutes;
		
		inputMinutes = input % 100;
		inputHours = input - inputMinutes;
		
		modMinutes = modification % 100;
		modHours = modification - modMinutes;

		resultMinutes = (inputMinutes + modMinutes) % 60;
		
		if (resultMinutes != inputMinutes + modMinutes) {
			
			modHours += ((inputMinutes + modMinutes) - resultMinutes) / 60 * 100;
			
		}
		
		resultHours = (inputHours + modHours) % 2400;
		
		return resultHours + resultMinutes;
		
	}
	
	/*
	 * allows for manipulator of date. Takes input in the form of
	 * year, month, day in each array. It can deal with odd inputs
	 * such as 3 years, 20 months, 293 days, but it may produce
	 * unintended output as it calculates days first and then 
	 * months. It is recommended to only use this functionality if
	 * only one modification type is set to a non-zero value
	 */
	public static int[] dateManipulator(int[] input, int[] modification) {
		
		int[] result = new int[3];
		int currentYear, currentMonth, currentDay;

		int[] monthLength = {31,28,31,30,31,30,31,31,30,31,30,31};
		
		currentYear = input[0];
		currentMonth = input[1];
		currentDay = input[2];
		
		currentDay += modification[2];
		
		while (currentDay > monthLength[currentMonth - 1]) {
			
			//accounts for leap years
			if (currentMonth == 2 && (currentYear % 4 == 0) && ((currentYear % 100 != 0) || (currentYear % 400 == 0))) {
				
				if (currentDay > monthLength[currentMonth - 1] + 1) {
					
					currentDay -= 29;
					currentMonth++;
					
				} else {
					
					break;
					
				}
				
			} else {
				
				currentDay -= monthLength[currentMonth - 1];
				currentMonth++;
				
			}
			
			// loops around after December
			if (currentMonth > 12) {
				
				currentMonth = 1;
				currentYear++;
				
			}
			
		}
		
		//calculates current month
		currentMonth += modification[1];
		
		while (currentMonth > 12) {
			
			currentMonth -= 12;
			currentYear++;
			
		}
		
		currentYear += modification[0];
		
		result[0] = currentYear;
		result[1] = currentMonth;
		result[2] = currentDay;
		
		return result;
		
	}
	
	/* ensures that the user has actually entered a data value */
	public static boolean isBlank(String value) {
		
		if (value.isEmpty()) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}

}
