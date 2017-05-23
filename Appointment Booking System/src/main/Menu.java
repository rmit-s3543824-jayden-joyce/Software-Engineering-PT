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
	
	public static String checker(String username, String password, String confirmPassword, String street, String suburb, String postcode, String phoneNumber)
	{
		String errors = "";
		String[] address = {street, suburb, postcode};
				
		if(Registration.isUniqueUser(username) == false || Registration.usernameRequirementsTest(username) == false)
		{
			errors += "0";
		}
		if(Registration.passwordRequirementsTest(password) == false)
		{
			errors += "1";
		}
		if(Registration.doPasswordsMatch(password, confirmPassword) == false)
		{
			errors += "2";
		}
		if(Registration.streetCheck(street) == false)
		{
			errors += "3";
		}
		if(Registration.isSuburb(suburb) == false)
		{
			errors += "4";
		}
		if(Registration.isPostcode(postcode) == false)
		{
			errors += "5";
		}
		if(Registration.isPhoneNumber(phoneNumber) == false)
		{
			errors += "6";
		}
		
		if(!errors.isEmpty())
		{
			return errors;
		}
		
		Registration.saveRegistration(username, password, address, phoneNumber);
		return null;
	}
	
	public static String messageGenerator(char field)
	{
		if(field == '0')
		{
			return "*Username is invalid or already taken";
		}
		else if(field == '1')
		{
			return "*Invalid password";
		}
		else if(field == '2')
		{
			return "*Passwords don't match";
		}
		else if(field == '3')
		{
			return "*Invalid street name";
		}
		else if(field == '4')
		{
			return "*Invalid suburb";
		}
		else if(field == '5')
		{
			return "*Invalid postcode";
		}
		else if(field == '6')
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
