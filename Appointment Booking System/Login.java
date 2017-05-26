package main;

import java.io.*;
import java.util.Scanner;

public class Login
{	
	static String delim = "\\|";
	static String customerList = "customerList.txt";
	static String businessOwnerList = "businessOwnerList.txt";
	public static String currentUser;
	
	public static int login() throws IOException
	{
		String Username, Password;
		//char passwordArray[];
		boolean loginOption = true;
		int userType;
		Console console = System.console();	
		Scanner consoleReader = new Scanner(System.in);
		
		do
		{
			heading();
			
			System.out.println("Username:");
			System.out.print(">>> ");
			Username = consoleReader.next();
			
			//sets username for global user
			Menu.username = Username;
					
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
		
		userType = verifyLoginDetails(Username, Password);
		if(userType == 1)
		{
			System.out.println("Login successful!\n");
			Menu.username = Username;
			return 1;
		}
		else if(userType == 2)
		{
			System.out.println("Login successful!\n");
			Menu.username = Username;
			return 2;
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
	
	public static void heading()
	{
		System.out.println("\nLogin");
		System.out.println("------------------------");
	}

	public static int verifyLoginDetails(String username, String password) throws IOException
	{

		//defines global variable username to be used in customization
		Menu.username = username;
				
		if(password.isEmpty() || username.isEmpty())
		{
			System.out.println("Username or password are invalid.\n");
			return 0;
		}
		
		if(username.matches("(bo[0-9]{4})"))
		{
			String[] words;
			String line = "";
			FileReader fr = new FileReader(BusinessManagement.selectedBusiness.getFileName() + businessOwnerList);
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
						return 2;
					}
				}
			}
			reader.close();
		}
		else
		{
			String[] words;
			String line = "";
			FileReader fr = new FileReader(BusinessManagement.selectedBusiness.getFileName() + customerList);
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
						return 1;
					}
				}
			}
			reader.close();
		}
		
		System.out.println("Username or password not found\n");
		return 0;
	}
}