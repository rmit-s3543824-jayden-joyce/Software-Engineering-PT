package main;

import java.io.*;
import java.util.Scanner;

public class Login
{	
	static String delim = "\\|";
	static String customerList = "customerList.txt";
	static String businessOwnerList = "businessOwnerList.txt";
	
	public static int login() throws IOException
	{
		String userType, Username, Password;
		//char passwordArray[];
		boolean loginOption = true;
		Console console = System.console();	
		Scanner consoleReader = new Scanner(System.in);
		
		//Establishes whether user is a Customer or Business Owner
		userTypeSelection();
		userType = consoleReader.next();
		
		//Checks for invalid entries
		if(!userType.equals("1") && !userType.equals("2"))
		{
			System.out.println("Invalid input.");
			return 0;
		}
		
		do
		{
			heading(userType);
			
			System.out.println("Username:");
			System.out.print(">>> ");
			Username = consoleReader.next();
					
			System.out.println("Password:");
			System.out.print(">>> ");
			//console.readPassword has built-in password masking
			//passwordArray = console.readPassword();
			//Convert to string for simplicity
			//Password = new String(passwordArray);
			
			Password = consoleReader.next();
			
			//Checks for invalid entries
			if(Password.isEmpty() || Username.isEmpty())
			{
				System.out.println("Username or password are invalid.\n");
			}
			else
			{
				loginOption = false;
			}
		}while(loginOption);
		
		//Depending on user type, decides which file to read from
		if(userType.equals("1"))
		{
			if(verifyLoginDetails(Username, Password, customerList) == true)
			{
				System.out.println("Login successful!\n");
				return 1;
			}
		}
		else
		{
			if(verifyLoginDetails(Username, Password, businessOwnerList) == true)
			{
				System.out.println("Login successful!\n");
				return 2;
			}
		}
		return 0;
	}
	
	public static void userTypeSelection()
	{
		System.out.println("\nSelect user-type:");
		System.out.println("1. Customer");
		System.out.println("2. Business Owner");
		System.out.print(">>> ");
	}
	
	public static void heading(String x)
	{
		if(x.equals("1"))
		{
			System.out.println("\nLogin (Customer)");
		}
		else
		{
			System.out.println("\nLogin (Business Owner)");
		}
		System.out.println("------------------------");
	}

	public static boolean verifyLoginDetails(String username, String password, String userType) throws IOException
	{
		String[] words;
		String line = "";
		FileReader fr = new FileReader(userType);
		BufferedReader reader = new BufferedReader(fr);
		
		//Reads each line from respective text file
		while((line = reader.readLine()) != null)
		{
			//Splits up words in the line using delim
			words = line.split(delim);
			
			if((words[0]).equals(username))
			{
				if((words[1]).equals(password))
				{
					reader.close();
					return true;
				}
			}
		}
		
		System.out.println("Username or password not found\n");
		reader.close();
		return false;
	}
}