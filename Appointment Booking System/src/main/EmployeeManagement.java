package main;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	
	public static void main(String[] args) {
		
		try {
			employeeManagement();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
					listEmployees();
				break;
				case "3":
					ScheduleManagement.interfaceShowSchedule();
				break;
				case "4":
					//allows user to add a work schedule
					ScheduleManagement.interfaceAddSchedule();
				break;
				case "5":
					//allows user to remove a work schedule
					ScheduleManagement.interfaceRemoveSchedule();
				break;
				case "6":
					ScheduleManagement.interfaceShowAvailability();
				break;
				case "7":
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
							//creates employee file
							if(!Utility.createFile(employeeId)) {
								
								//ensures that the employee details are not saved if a file is not created
								break;
								
							}
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
	
	public static void listEmployees() {
		
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
				System.out.println(dataValues[0] + " - " + dataValues[1] + " " + dataValues[2]);
				
			}
			
			reader.close();
			
		} catch (IOException ioe1) {
			
		}
	
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
		System.out.println("2. List employees");
		System.out.println("3. Show Schedule");
		System.out.println("4. Add Schedule");
		System.out.println("5. Remove Schedule");
		System.out.println("6. Show Employee Availability");
		System.out.println("7. BACK");
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
