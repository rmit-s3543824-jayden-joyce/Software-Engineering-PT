package registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EmployeeManagement {
	
	public static String employeeList = "employeeList.txt";
	
	private static void listEmployees() {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		BufferedReader reader = null;
				
		try {
			
			reader = new BufferedReader(new FileReader(employeeList));
			int i = 0;
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				i++;
				System.out.print(i + ". ");
				System.out.println(dataValues[0]);
					
				reader.close();
				
			}
			
		} catch (IOException ioe1) {
			
		}
	
	}
	
	private static void showScheduledHours(String employeeName) {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		int i = 1;
		
		BufferedReader reader = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(employeeList));
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				if (dataValues[0].equals(employeeName)) {
					
					while (dataValues[i] != null) {
						
						System.out.print("Scheduled for ");
						System.out.print(dataValues[i] + "/" + dataValues[i+1] + "/" + dataValues[i+2]);
						System.out.print(" between ");
						System.out.println(dataValues[i+3] + " and " + dataValues[i+4]);
						
						i =+ 5;
						
					}
					
					break;
					
				}
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
	
	}
	
	private static void employeeScheduleRemove (String employeeName, int[] dateTime) {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		String[] data = new String[999];
		
		int size = 0;
		int i;
		
		BufferedReader reader = null;
		
		//reads employee detail file into memory
		try {
			
			reader = new BufferedReader(new FileReader(employeeList));
			
			while ((currentLine = reader.readLine()) != null) {

				dataValues = currentLine.split(deliminator);
				
				//adds the additional work schedule for the employee
				if (dataValues[0].equals(employeeName)) {
					
					i = 1;

					while (dataValues[i] != null) {
						
						if (Integer.parseInt(dataValues[i]) == dateTime[0] && 
								Integer.parseInt(dataValues[i+1]) == dateTime[1] &&
								Integer.parseInt(dataValues[i+2]) == dateTime[2] &&
								Integer.parseInt(dataValues[i+3]) == dateTime[3] &&
								Integer.parseInt(dataValues[i+4]) == dateTime[4]) {
							
							currentLine = "";
											
							for (int j = 0; j < i; j++) {
								
								currentLine = currentLine + dataValues[j] + "|";
								
							}
							
							//moves 'cursor' position to the next time date sequence
							i =+ 5;
							
							while (dataValues[i] != null) {
								
								currentLine = currentLine + dataValues[i] + "|";
								
								i++;
								
							}
							
							break;
							
						}
						
						i =+ 5;
						
					}
					
				}
				
				data[size] = currentLine;
				size++;
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		saveEmployeeSchedule(data, size);
		
	}
	
	private static void employeeScheduleAdd(String employeeName, int[] dateTime) {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		String[] data = new String[999];
		
		int size = 0;
		
		BufferedReader reader = null;
		
		//reads employee detail file into memory
		try {
			
			reader = new BufferedReader(new FileReader(employeeList));
			
			while ((currentLine = reader.readLine()) != null) {

				dataValues = currentLine.split(deliminator);
				
				//adds the additional work schedule for the employee
				if (dataValues[0].equals(employeeName)) {

					for (int j = 0; j < 5; j++) {
						
						currentLine = currentLine + "|" + dateTime[j];
						
					}
					
				}
				
				data[size] = currentLine;
				size++;
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
		
		saveEmployeeSchedule(data, size);

	}
	
	//returns true if success, false if failure
	private static boolean saveEmployeeSchedule(String[] data, int size) {
		
		BufferedWriter writer = null;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(employeeList, false));
			
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
	
}
