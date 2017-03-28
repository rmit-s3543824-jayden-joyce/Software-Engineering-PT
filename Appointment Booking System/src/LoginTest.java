import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class LoginTest {

	String trueUsername = "Test";
	String falseUsername = "TEST";
	String truePassword = "Test";
	String falsePassword = "TEST";
	String boUsername = "bo0001";
	String boPassword = "password1";
	String customerFile = "src/customerList.txt";
	String businessOwnerFile = "src/customerList.txt";
	
	@Test
	public void username() throws IOException
	{
		assertEquals(true, Login.verifyLoginDetails(trueUsername, truePassword, customerFile));
		//Also tests case sensitivity
		assertEquals(false, Login.verifyLoginDetails(falseUsername, truePassword, customerFile));		
	}
	
	@Test
	public void password() throws IOException
	{
		assertEquals(true, Login.verifyLoginDetails(trueUsername, truePassword, customerFile));
		//Also tests case sensitivity
		assertEquals(false, Login.verifyLoginDetails(falseUsername, falsePassword, customerFile));
	}

	@Test
	public void userType() throws IOException 
	{
		//Checks to see if it reads correctly from customer and business files
		//Customer file check is done in the above tests
		assertEquals(false, Login.verifyLoginDetails(boUsername, boPassword, businessOwnerFile));
		assertEquals(false, Login.verifyLoginDetails(boUsername, falsePassword, businessOwnerFile));
	}
}