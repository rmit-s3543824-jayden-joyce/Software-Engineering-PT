package test;
import static org.junit.Assert.*;

import java.io.*;
import org.junit.*;

import main.Login;

public class LoginTest {

	public String Username = "Mack1";
	public String Password = "pWord123";
	public String emptyUsername = "";
	public String emptyPassword = "";
	public String caseSensitiveUsername = "mack1";
	public String caseSensitivePassword = "pword123";
	public static String mockCustomerFile = "src/customerListTest.txt";
	public static String mockBusinessOwnerFile = "src/businessOwnerListTest.txt";
	
	@BeforeClass
	public static void setUp() throws Exception
	{
		//Creating files to contain mock data for testing purposes 
		File f1 = new File(mockCustomerFile);
		File f2 = new File(mockBusinessOwnerFile);
		f1.createNewFile();
		f2.createNewFile();
		
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(f1));
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(f2));
		//File format: Username|Password
		bw1.write("Mack1|pWord123");
		bw2.write("Mack1|pWord123");
		
		bw1.close();
		bw2.close();
	}
	
	@Test
	public void testVerifyLoginDetails() throws IOException
	{
		//Testing valid login details of an existing customer
		assertTrue(Login.verifyLoginDetails(Username, Password, mockCustomerFile));
		//Testing with invalid username/non-existing customer
		assertFalse(Login.verifyLoginDetails(emptyUsername, Password, mockCustomerFile));
		//Testing with valid username but invalid password of existing customer
		assertFalse(Login.verifyLoginDetails(Username, emptyPassword, mockCustomerFile));
		//Testing for case-sensitivity in username and password
		assertFalse(Login.verifyLoginDetails(caseSensitiveUsername, Password, mockCustomerFile));
		assertFalse(Login.verifyLoginDetails(Username, caseSensitivePassword, mockCustomerFile));
		//Testing reading from business owner/different file
		assertTrue(Login.verifyLoginDetails(Username, Password, mockBusinessOwnerFile));
	}
	
	@AfterClass
	public static void cleanUp() throws Exception
	{
		File f1 = new File(mockCustomerFile);
		File f2 = new File(mockBusinessOwnerFile);
		//Deleting the mock files since they're not needed anymore
		f1.delete();
		f2.delete();
	}
}