import java.io.IOException;
import java.util.Scanner;

public class Menu {
	
	static String customerList = "C:\\Users\\Joyce\\workspace\\LoginMenu.zip_expanded\\SEPT - Assignment 1\\customerList.txt";
	static String businessOwnerList = "C:\\Users\\Joyce\\workspace\\LoginMenu.zip_expanded\\SEPT - Assignment 1\\businessOwnerList.txt";
	
	public static void main(String[] args) throws IOException
	{
		Login login = new Login();
		Scanner sc = new Scanner(System.in);
		boolean menu = true;
		String Username;
		String Password;
		
		do
		{
			menuScreen();
			boolean loginOption = true, signUp = true;
			String input = sc.next();
			
			switch(input)
			{
				case "1":
					int userType;
					
					System.out.println("Select user-type:");
					System.out.println("1. Customer");
					System.out.println("2. Business Owner");
					System.out.print(">>> ");
					
					userType = sc.nextInt();
					
					//Throws error when input is of String type
					if(userType != 1 && userType != 2)
					{
						System.out.println("Invalid input.");
						break;
					}
					
					do
					{
						System.out.println("\n(Case-sensitive)");
						System.out.println("Username:");
						System.out.print(">>> ");
						Username = sc.next();
						System.out.println("Password:");
						System.out.print(">>> ");
						Password = sc.next();
						if(Password.isEmpty() || Username.isEmpty())
						{
							System.out.println("Username or password in incorrect.\n");
						}
						else
						{
							loginOption = false;
						}
						
					}while(loginOption);
					
					if(userType == 1)
					{
						if(login.verifyLoginDetails(Username, Password, customerList) == false)
						{
							break;
						}
					}
					else
					{
						if(login.verifyLoginDetails(Username, Password, businessOwnerList) == false)
						{
							break;
						}
					}
					
					System.out.println("Login successful!\n");
				break;
				case "2":
					//register new customer/business owner
				break;
				case "3":
					System.out.println("Exiting...");
					menu = false;
				break;
				default:
					System.out.println("Invalid input.\n");
				break;
			}
		}while(menu);
		
		sc.close();
	}

	public static void menuScreen()
	{
		System.out.println("-------------------");
		System.out.println("     MAIN MENU     ");
		System.out.println("-------------------");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. EXIT");
		System.out.println("-------------------");
		System.out.print(">>> ");
	}
}
