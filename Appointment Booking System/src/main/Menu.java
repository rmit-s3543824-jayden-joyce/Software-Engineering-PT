package main;

import java.io.IOException;
import java.util.Scanner;

import main.BusinessManagement;
import main.CustomerManagement;

public class Menu {

	public static void main(String[] args) throws IOException {
		int selection;
		Scanner sc = new Scanner(System.in);
		boolean menu = true, gui = true;
		
		if(gui)
        {
            MenuGUI.launch(MenuGUI.class);
        }
		else
		{
			do {
				menuScreen();
				String input = sc.next();

				switch (input) {
				case "1":
					selection = Login.login();
					if (selection == 1) {
						UserManagement userManagement = new UserManagement("Suggar Haircut");
						userManagement.runCustomerMenu();
					} else if (selection == 2) {
						UserManagement userManagement = new UserManagement("Suggar Haircut");
						userManagement.runBusinessMenu();
					}
					break;
				case "2":
					Registration.register();
					break;
				case "3":
					System.out.println("Exiting...");
					sc.close();
					menu = false;
					break;
				default:
					System.out.println("Invalid input.\n");
					break;
				}
			} while (menu);
		}
	}
	
	public static String checker(String name, String username, String password, String confirmPassword, String street, String suburb, String postcode, String phoneNumber)
	{
		String errors = "";
		String[] address = {street, suburb, postcode};
				
		if(Registration.isUniqueUser(username) == false)
		{
			System.out.println("TEST1");
			errors += "0";
		}
		if(Registration.usernameRequirementsTest(username) == false)
		{
			System.out.println("TEST2");
			errors += "1";
		}
		if(Registration.passwordRequirementsTest(password) == false)
		{
			System.out.println("TEST3");
			errors += "2";
		}
		if(Registration.doPasswordsMatch(password, confirmPassword) == false)
		{
			System.out.println("TEST4");
			errors += "3";
		}
		if(Registration.streetCheck(street) == false)
		{
			System.out.println("TEST5");
			errors += "4";
		}
		if(Registration.isPostcode(postcode) == false)
		{
			System.out.println("TEST6");
			errors += "6";
		}
		if(Registration.isPhoneNumber(phoneNumber) == false)
		{
			System.out.println("TEST7");
			errors += "7";
		}
		
		if(!errors.isEmpty())
		{
			System.out.println(errors);
			return errors;
		}
		
		Registration.saveRegistration(username, password, address, phoneNumber);
		return null;
	}
	
	public static String messageGenerator(char field)
	{
		if(field == '0')
		{
			return "*Invalid name";
		}
		else if(field == '1')
		{
			return "*Username already taken";
		}
		else if(field == '2')
		{
			return "*Invalid username";
		}
		else if(field == '3')
		{
			return "*Passwords don't match";
		}
		else if(field == '4')
		{
			return "*Invalid street name";
		}
		else if(field == '5')
		{
			return "*Invalid suburb";
		}
		else if(field == '6')
		{
			return "*Invalid postcode";
		}
		else if(field == '7')
		{
			return "*Invalid phone number";
		}
		return null;
	}

	public static void menuScreen() {
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
