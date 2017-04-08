package test;

import static org.junit.Assert.*;
import java.io.*;
import org.junit.*;

import main.Registration;
import utility.Utility;

public class UtilityTest {
	
	//tests whether the time manipulator properly adds military time
	
	@Test
	public void testTimeManipulator() {
		
		int timeOneResult = 1800;
		int timeOneInput = 1800;
		int timeOneMod = 0;
		
		int timeTwoResult = 1200;
		int timeTwoInput = 600;
		int timeTwoMod = 600;
		
		int timeThreeResult = 1800;
		int timeThreeInput = 1630;
		int timeThreeMod = 130;
		
		int timeFourResult = 0;
		int timeFourInput = 1800;
		int timeFourMod = 600;

		int timeFiveResult = 200;
		int timeFiveInput = 0;
		int timeFiveMod = 200;
		
		int timeSixResult = 1234;
		int timeSixInput = 603;
		int timeSixMod = 631;

		assertEquals(timeOneResult,Utility.timeManipulator(timeOneInput, timeOneMod));
		assertEquals(timeTwoResult,Utility.timeManipulator(timeTwoInput, timeTwoMod));
		assertEquals(timeThreeResult,Utility.timeManipulator(timeThreeInput, timeThreeMod));
		assertEquals(timeFourResult,Utility.timeManipulator(timeFourInput, timeFourMod));
		assertEquals(timeFiveResult,Utility.timeManipulator(timeFiveInput, timeFiveMod));
		assertEquals(timeSixResult,Utility.timeManipulator(timeSixInput, timeSixMod));
		
	}
	
	//tests whether dates are properly added, accounting for leap years and the like
	
	@Test
	public void testDateManipulator() {
		
		int[] dateOneResult = {2000,1,1};
		int[] dateOneInput = {2000,1,1};
		int[] dateOneMod = {0,0,0};
		
		int[] dateTwoResult = {2004,3,1};
		int[] dateTwoInput = {2004,2,28};
		int[] dateTwoMod = {0,0,2};
		
		int[] dateThreeResult = {1700,3,1};
		int[] dateThreeInput = {1700,2,28};
		int[] dateThreeMod = {0,0,1};
		
		int[] dateFourResult = {1999,12,3};
		int[] dateFourInput = {1989,12,3};
		int[] dateFourMod = {10,0,0};
		
		int[] dateFiveResult = {2015,6,28};
		int[] dateFiveInput = {0,1,0};
		int[] dateFiveMod = {2015,5,28};
		
		int[] dateSixResult = {2013,9,25};
		int[] dateSixInput = {2013,3,3};
		int[] dateSixMod = {0,0,206};
		
		assertArrayEquals(dateOneResult,Utility.dateManipulator(dateOneInput, dateOneMod));
		assertArrayEquals(dateTwoResult,Utility.dateManipulator(dateTwoInput, dateTwoMod));
		assertArrayEquals(dateThreeResult,Utility.dateManipulator(dateThreeInput, dateThreeMod));
		assertArrayEquals(dateFourResult,Utility.dateManipulator(dateFourInput, dateFourMod));
		assertArrayEquals(dateFiveResult,Utility.dateManipulator(dateFiveInput, dateFiveMod));
		assertArrayEquals(dateSixResult,Utility.dateManipulator(dateSixInput, dateSixMod));
		
	}
	
	//tests whether the file creator successful creates unique files and fails on non-unique ones
	
	@Test
	public void testFileCreator() {
		
		assertTrue(Utility.createFile("test01"));
		assertTrue(Utility.createFile("test02"));
		assertTrue(Utility.createFile("test03"));
		assertTrue(Utility.createFile("test04"));
		assertFalse(Utility.createFile("test01"));
		assertFalse(Utility.createFile("test02"));
		
	}
	
	//cleans up the created files after the test is concluded
	
	@AfterClass
	public static void cleanUp() {
		
		File f1 = new File("test01.txt");
		f1.delete();
		File f2 = new File("test02.txt");
		f2.delete();
		File f3 = new File("test03.txt");
		f3.delete();
		File f4 = new File("test04.txt");
		f4.delete();
		
		
	}


}
