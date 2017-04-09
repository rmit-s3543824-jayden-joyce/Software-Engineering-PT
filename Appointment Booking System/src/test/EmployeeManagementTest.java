package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import main.EmployeeManagement;

public class EmployeeManagementTest {
	
	String number = "123";
	String word = "TEST";
	String characters = "!@#$%";
	String blank = "";
	
	@Test
	public void testGenerateId() throws IOException
	{
		String[] words;
		String newId = "", lastId = "", currentLine = "", lastLine = "";
		BufferedReader br = new BufferedReader(new FileReader("employeeList.txt"));
		
		//Loops through file to get the last line
		while((currentLine = br.readLine()) != null)
		{
			lastLine = currentLine;
		}
		
		//Splits the line up
		words = lastLine.split("\\|");
		//Gets the employee ID of the last added employee
		lastId = words[0];
		//Generate new ID as if there was a new employee to be added
		newId = EmployeeManagement.generateId();
		
		//The new ID should be one more than the last ID
		assertNotEquals(lastId, newId);
		
		System.out.println("Last ID: " + lastId);
		System.out.println("New ID: " + newId);
	}
	
	@Test
	public void testIsInteger()
	{
		//Testing if input is of numeric format
		assertTrue(EmployeeManagement.isInteger(number));
		//Testing input of character format
		assertFalse(EmployeeManagement.isInteger(word));
		//Testing input of special character format
		assertFalse(EmployeeManagement.isInteger(characters));
		//Testing for blank input
		assertFalse(EmployeeManagement.isInteger(blank));
	}
	
	@Test
	public void testIsString()
	{		
		//Testing input of character format
		assertTrue(EmployeeManagement.isString(word));
		//Testing if input is of numeric format
		assertFalse(EmployeeManagement.isString(number));
		//Testing input of special character format
		assertFalse(EmployeeManagement.isString(characters));
		//Testing for blank input
		assertFalse(EmployeeManagement.isString(blank));
	}
}
