package test;
import static org.junit.Assert.*;

import java.io.*;
import org.junit.*;

import main.Registration;

public class RegistrationTest {
	
	public String emptyString = "";
	public String fullString = "string";
	
	public String failPasswordShort = "absd";
	public String failPasswordShortBar = "abs|";
	public String failPasswordBar = "abasdasdcs|";
	public String passPassword = "abasdasdcs";
	
	public String failUsernameBarEnd = "john|";
	public String failUsernameBarStart = "|john";
	public String failUsernameBarMiddle = "jo|hn";
	public String usernameJohn = "John";
	public String usernameRichard = "Richard";
	public String usernameTest = "Test";
	public String usernameBob = "Bob";
	public String usernameKim = "Kim";
	
	public String pass1 = "John1234";
	public String pass2 = "Richard123";
	
	public String matchall = ".*";
	
	public String phonePlusStart = "+61412345678";
	public String phone04Start = "0412345678";
	public String phoneOtherStart = "0312345678";
	public String phone04StartTooShort = "04123";
	public String phone04StartTooLong = "04123456789";
	public String phonePlusStartTooShort = "+614123";
	public String phonePlusStartTooLong = "+614123456789";
	
	
	@BeforeClass
	public static void setUp() {
		
		Registration.customerList = "testCustomerList.txt";
		//Creating files to contain mock data for testing purposes 
		File f1 = new File(Registration.customerList);
		
		try {
			f1.createNewFile();
			
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(f1));
			bw1.write("Test|Test|Test|Test|Test|Test|Test|Test");
			bw1.newLine();
			bw1.write("John|Test|Test|Test|Test|Test|Test|Test");
			bw1.newLine();
			bw1.write("Richard|Test|Test|Test|Test|Test|Test|Test");
			bw1.newLine();
			
			bw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testVerifyIsBlank() {
		
		assertFalse(Registration.isNotBlank(emptyString));
		assertTrue(Registration.isNotBlank(fullString));
		
		
	}

	@Test
	public void testPasswordRequirements() {
		
		assertFalse(Registration.passwordRequirementsTest(emptyString));
		assertFalse(Registration.passwordRequirementsTest(failPasswordShort));
		assertFalse(Registration.passwordRequirementsTest(failPasswordShortBar));
		assertFalse(Registration.passwordRequirementsTest(failPasswordBar));
		assertTrue(Registration.passwordRequirementsTest(passPassword));
		
	}

	@Test
	public void testPasswordMatch() {
		
		assertFalse(Registration.doPasswordsMatch(pass1, pass2));
		assertFalse(Registration.doPasswordsMatch(pass2, pass1));
		assertTrue(Registration.doPasswordsMatch(pass1, pass1));
		assertTrue(Registration.doPasswordsMatch(pass2, pass2));
		assertTrue(Registration.doPasswordsMatch(emptyString, emptyString));
		assertFalse(Registration.doPasswordsMatch(pass1, matchall));
		
	}

	@Test
	public void testIsPhonenNumber() {

		assertTrue(Registration.isPhoneNumber(phone04Start));
		assertTrue(Registration.isPhoneNumber(phonePlusStart));
		assertFalse(Registration.isPhoneNumber(phoneOtherStart));
		assertFalse(Registration.isPhoneNumber(phone04StartTooShort));
		assertFalse(Registration.isPhoneNumber(phone04StartTooLong));
		assertFalse(Registration.isPhoneNumber(phonePlusStartTooShort));
		assertFalse(Registration.isPhoneNumber(phonePlusStartTooLong));
		
	}
	
	@Test
	public void testUsernameRequirements() {
		
		assertTrue(Registration.usernameRequirementsTest(usernameBob));
		assertFalse(Registration.usernameRequirementsTest(failUsernameBarEnd));
		assertFalse(Registration.usernameRequirementsTest(failUsernameBarStart));
		assertFalse(Registration.usernameRequirementsTest(failUsernameBarMiddle));
		
	}
	
	@Test
	public void testUsernameUniqueness() {
		
		assertFalse(Registration.isUniqueUser(usernameTest));
		assertFalse(Registration.isUniqueUser(usernameJohn));
		assertFalse(Registration.isUniqueUser(usernameRichard));
		assertTrue(Registration.isUniqueUser(usernameBob));
		assertTrue(Registration.isUniqueUser(usernameKim));
		assertTrue(Registration.isUniqueUser(matchall));
		
	}
	
	@AfterClass
	public static void cleanUp() {
		
		File f1 = new File(Registration.customerList);
		f1.delete();
		
		//returns customerlist to its proper address
		Registration.customerList = "customerList.txt";
		
	}

}
