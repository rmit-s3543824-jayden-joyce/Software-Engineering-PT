package main;



import java.io.*;
import java.util.Scanner;

public class Registration {
	
	public static String customerList = "customerList.txt";
	
	public static void main(String[] args) {
		
		register();
		
	}
	
	protected static void register() {
		
		String username = null;
		String password = null;
		String passwordVerification = null;
		String[] address = new String[5];
		String phone = null;
		
		String[] addressDetails = {"Street","Suburb","Postcode","State","Country"};
		
		Boolean userFlag = false;
		Boolean passwordFlag = false;
		Boolean addressFlag = false;
		Boolean phoneFlag = false;
		
		Scanner consoleReader = new Scanner(System.in);
		
		int i;
		
		
		System.out.println("Registration:");
		System.out.println("----------------");
		
		//obtains username
		do {
			
			//resets username to null
			username = null;
			
			System.out.println("Please enter username:");
			username = consoleReader.nextLine();
			
			if (isNotBlank(username)) {
				
				if (isUniqueUser(username)) {
					
					if (usernameRequirementsTest(username)) {
						
						userFlag = true;
						
					}
					
				}
				
			}
			
			
		} while (userFlag == false);
		
		//obtains password
		do {
			
			//resets password to null to prevent errors
			password = null;
			
			System.out.println("Please enter password:");
			password = consoleReader.nextLine();
			
			if (isNotBlank(password)) {
				
				if (passwordRequirementsTest(password)) {

					//resets passwordVerification to null to prevent errors
					passwordVerification = null;
					
					System.out.println("Please enter password verification:");
					passwordVerification = consoleReader.nextLine();
					
					if (doPasswordsMatch(password, passwordVerification)) {
						
						passwordFlag = true;
						
					}
					
				}
				
			}
			
			
		} while (passwordFlag == false);
		
		//obtains address
		for (i = 0; i < 5; i++) {

			do {
				
				addressFlag = false;
				System.out.println("Please enter address (" + addressDetails[i] + "):");
				address[i] = consoleReader.nextLine();
				
				if (isNotBlank(address[i])) {
					
					addressFlag = true;
					
				}
				
			} while (addressFlag == false);		
			
		}
		
		//obtains phone number
		do {
			
			//resets number to null to prevent errors
			phone = null;
			
			System.out.println("Please enter phone number:");
			phone = consoleReader.nextLine();
			
			if (isNotBlank(phone)) {
				
				if (isPhoneNumber(phone)) {
					
					phoneFlag = true;
					
				}
				
			}
			
			
		} while (phoneFlag == false);		
		
		if(saveRegistration(username,password,address,phone)) {
			
			System.out.println("Registration Successful.");
			
		} else {
			
			System.out.println("Registration Failed. Please try again later.");
			
		}
		
	}
	
	/* ensures that the user has actually entered a data value */
	public static boolean isNotBlank(String value) {
		
		if (value.isEmpty()) {

			System.out.println("This field is required and must not be left empty.");
			
			return false;
			
		} else {
			
			return true;
			
		}
		
	}
	
	/* checks that the password matches the requirements */
	public static boolean passwordRequirementsTest(String password) {
		
		if (password.matches("(.*)\\|(.*)")) {
			
			System.out.println("Password may not contain '|'");
			
			return false;
			
		} else if (!password.matches(".{8,}")) {
			
			System.out.println("Password must be at least eight characters");
			
			return false;
			
		}
		
		return true;
		
	}
	
	/* checks that the two passwords enter match each other and thus that 
	 * the user has not accidentally entered an undesired password */
	public static boolean doPasswordsMatch(String password, String passwordVerification) {
		
		int strcmp = password.compareTo(passwordVerification);
		
		if (strcmp == 0) {
			
			return true;
			
		} else {
			
			System.out.println("Passwords do not match. Please re-enter");
			
			return false;
			
		}
		
	}

	/* checks that the phone number is a phone number */
	public static boolean isPhoneNumber(String number) {
		
		if (number.matches("(\\+?614)\\d{8}|(04)\\d{8}")) {

			return true;
			
		} else {
			
			System.out.println("Please enter an Australian Mobile Number");
			return false;
			
		}
		
		
	}
	
	/* checks that the username is unique */
	public static boolean isUniqueUser(String username) {
		
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		BufferedReader reader = null;
				
		try {
			
			reader = new BufferedReader(new FileReader(customerList));
			
			while ((currentLine = reader.readLine()) != null) {
				
				dataValues = currentLine.split(deliminator);
				
				if (dataValues[0].equals(username)) {
					
					System.out.println("Username taken.");
					
					reader.close();
					return false;
					
				}
				
			}
			
		} catch (IOException ioe1) {
			
		}
		
		return true;
	}
	
	/* checks that the username meets requirements */
	public static boolean usernameRequirementsTest(String username) {
		
		if (username.matches("(.*)\\|(.*)")) {
			
			System.out.println("The character '|' may not be used");
			
			return false;
			
		} 
		
		return true;
	}
	
	/* saves the new user to file. Returns true if successful*/
	private static boolean saveRegistration(String username, String password, String[] address, String number) {
		
		BufferedWriter writer = null;
		
		int i;
		
		try {
			
			//wraps FileWriter in BufferedWrite, in order to use newLine()
			writer = new BufferedWriter(new FileWriter(customerList, true));
			
			writer.write(username);
			writer.write("|");
			
			writer.write(password);
			writer.write("|");
			
			for (i = 0; i < 5; i++) {
				
				writer.write(address[i]);
				writer.write("|");
				
			}
			
			writer.write(number);
			writer.newLine();
			
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