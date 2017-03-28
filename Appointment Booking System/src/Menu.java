import java.io.IOException;
import java.io.Console;
import java.util.Scanner;

public class Menu {
	
	static String customerList = "customerList.txt";
	static String businessOwnerList = "businessOwnerList.txt";
	
	public static void main(String[] args) throws IOException
	{
		Login login = new Login();
		Scanner sc = new Scanner(System.in);
		boolean menu = true;
		String Username;
		char passwordArray[];
		
		do
		{
			menuScreen();
			boolean loginOption = true;
			Console console = System.console();			
			String input = sc.next();
			
			switch(input)
			{
				case "1":
					String userType;
					
					System.out.println("\nSelect user-type:");
					System.out.println("1. Customer");
					System.out.println("2. Business Owner");
					System.out.print(">>> ");
					
					userType = sc.next();
					
					if(!userType.equals("1") && !userType.equals("2"))
					{
						System.out.println("Invalid input.");
						break;
					}
					
					do
					{
						if(userType.equals("1"))
						{
							System.out.println("\nLogin (Customer)");
							System.out.println("------------------------");
						}
						else
						{
							System.out.println("\nLogin (Business Owner)");
							System.out.println("------------------------");
						}
						
						System.out.println("Username:");
						System.out.print(">>> ");
						Username = sc.next();
						System.out.println("Password:");
						System.out.print(">>> ");
						passwordArray = console.readPassword();
						
						if(passwordArray.length == 0 || Username.isEmpty())
						{
							System.out.println("Username or password in incorrect.\n");
						}
						else
						{
							loginOption = false;
						}
						
					}while(loginOption);
					
					if(userType.equals("1"))
					{
						if(login.verifyLoginDetails(Username, new String(passwordArray), customerList) == false)
						{
							break;
						}
					}
					else
					{
						if(login.verifyLoginDetails(Username, new String(passwordArray), businessOwnerList) == false)
						{
							break;
						}
					}
					
					System.out.println("Login successful!\n");
				break;
				case "2":
					Registration.register();
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
