package registration;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class EmployeeManagement {
	
	public static String employeeList = "employeeList.txt";
	static String delimWrite = "|";
	static String delimRead = "\\|";
	
	public static void employeeManagement() throws IOException
	{
		Scanner sc = new Scanner(System.in);
		boolean employeeMenu = true;
		
		do
		{
			printMenu();		
			String input = sc.next();
			
			switch(input)
			{
				case "1":
					//Generates unique employee ID
					String employeeId = generateId();
					addEmployee(employeeId);
				break;
				case "2":
					//listEmployees();
				break;
				case "3":
					//showScheduledHours();
				break;
				case "4":
					//employeeScheduleRemove();
				break;
				case "5":
					//employeeScheduleAdd();
				break;
				case "6":
					employeeMenu = false;
				break;
				default:
					System.out.println("Invalid input.\n");
				break;
			}
		}while(employeeMenu);

	}
	
	public static void addEmployee(String employeeId) throws IOException
	{
		String employeeDetails = employeeId + delimWrite, firstName = null, lastName = null, selection;
		Boolean menu = true, firstNameCheck = true, lastNameCheck = true, confirmation = true;
		Scanner sc = new Scanner(System.in);
		BufferedWriter bw = new BufferedWriter(new FileWriter(employeeList, true));
		
		do
		{
			System.out.println("\nNew Employee");
			System.out.println("------------");
			
			System.out.println("First name:");
			System.out.print(">>> ");
			do
			{
				firstName = sc.next();
				
				//CHecks for blank input
				if(isNotBlank(firstName))
				{
					//Checks if the input contains only letters
					if(isString(firstName))
					{
						//Concats name to variable for when it is to be written to file
						employeeDetails = employeeDetails.concat(firstName + delimWrite);
						firstNameCheck = false;
					}
				}
			}while(firstNameCheck);
			
			System.out.println("Last name:");
			System.out.print(">>> ");
			do
			{
				lastName = sc.next();
								
				if(isNotBlank(lastName))
				{
					if(isString(lastName))
					{
						employeeDetails = employeeDetails.concat(lastName);
						lastNameCheck = false;
					}
				}
			}while(lastNameCheck);
			
			detailSummary(employeeDetails);
				
			do
			{
				selection = sc.next();
				
				if(isNotBlank(selection))
				{
					//Checks if input contains only numbers
					if(isInteger(selection))
					{
						if(selection.equals("1"))
						{
							//Writes employee details to file
							bw.write(employeeDetails);
							//Prints new line for when the next employee is to be added
							bw.newLine();
							confirmation = false;
							menu = false;
						}
						else if(selection.equals("2"))
						{
							//Loop over the process again
							confirmation = false;
						}
						else
						{
							System.out.println("Invalid input.");
						}
					}
				}	
			}while(confirmation);
		}while(menu);
		
		bw.close();
		return;
	}
	
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
	
	public static String generateId() throws IOException
	{
		int lastEmployeeId = 0, newEmployeeId = 1; 
		String employeeId;
		InputStream is = new BufferedInputStream(new FileInputStream(employeeList));
		
		//Loop counts number of lines in file
		try
		{
	        byte[] c = new byte[1024];
	        int readChars = 0;
	        boolean empty = true;
	        
	        while((readChars = is.read(c)) != -1)
	        {
	            empty = false;
	            for (int i = 0; i < readChars; ++i)
	            {
	                if (c[i] == '\n')
	                {
	                    //Increments each time a line is counted
	                	//Therefore its final value will be the total number of lines/Total number of employee entries
	                	++lastEmployeeId;
	                }
	            }
	        }
	        
	        //Employee ID is generated, adding 1 to the last employee added's ID
	        employeeId = String.format("e%03d", lastEmployeeId + newEmployeeId);
	        return employeeId;
	    }
		finally
		{
	        is.close();
	    }
		
		/*The above file-line counter was found online and slightly modified for our use,
		 *credit is given to the owner below.
		 *Reference: http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
		*/
	}
	
	public static void printMenu()
	{
		System.out.println("\nEmployee Management");
		System.out.println("------------------------");
		System.out.println("1. Add employee");
		System.out.println("2. Add working time/date");
		System.out.println("3. BACK");
		System.out.print(">>> ");
		return;
	}
	
	public static void detailSummary(String employeeDetails)
	{
		//Splits the details up to be individually printed out for summary
		String[] words = employeeDetails.split(delimRead);
		
		System.out.println("\nEmployee ID:   " + words[0]);
		System.out.println("First name:    " + words[1]);
		System.out.println("Last name:     " + words[2]);
		
		System.out.println("\nConfirm details?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		System.out.print(">>> ");
		return;
	}
	
	private static boolean isNotBlank(String input)
	{
		//Checks if input is empty/blank
		if(input.isEmpty())
		{
			System.out.println("This field is required and must not be left empty.");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean isString(String input)
	{
		//Checks if input only contains letters
		if(input.matches("[a-zA-Z]+"))
		{
			return true;
		}
		else
		{
			System.out.println("Invalid input.");
			return false;
		}
	}
	
	public static boolean isInteger(String input)
	{
		//Checks if input contains only integers
		if(input.matches("[0-9]+"))
		{
			return true;
		}
		else
		{
			System.out.println("Invalid input.");
			return false;
		}
	}
	
}
