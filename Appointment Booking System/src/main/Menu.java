package main;

import java.io.IOException;
import java.util.Scanner;

import main.BusinessManagement;
import main.CustomerManagement;

public class Menu {

	public static void main(String[] args) throws IOException {
		int selection;
		Scanner sc = new Scanner(System.in);
		boolean menu = true;

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
