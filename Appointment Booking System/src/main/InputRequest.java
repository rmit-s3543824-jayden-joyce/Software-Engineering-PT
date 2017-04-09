package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import utility.Utility;

public class InputRequest {
	
	public static int[] requestDate() {
		
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
		
		//prints out the days of the week, numbered
		for (int i = 0; i < 7; i++) {
			
			System.out.println((i+1) + ". " + weekDays[i]);
			
		}

		System.out.println("Day (Enter number adjacent):");
		dayTime[0] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Time From:");
		dayTime[1] = Integer.parseInt(consoleReader.nextLine());
		System.out.println("Time To:");
		dayTime[2] = Integer.parseInt(consoleReader.nextLine());
		
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
	
}
