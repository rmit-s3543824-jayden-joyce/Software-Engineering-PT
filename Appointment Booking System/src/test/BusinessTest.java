package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Business;

public class BusinessTest {
	
	private Business suggar;

	@Before
	public void setUp() throws Exception {
		
		suggar = new Business("Suggar","Jeremy","123 ABC","0412345678","suggarlogin","suggarpass");
	}

	@Test
	public void testGetName() {
		assertEquals(suggar.getName(),"Suggar");
	}
	
	@Test
	public void testAddAvailableDay(){
		suggar.addAvailableDay(20);
		suggar.addAvailableDay(11);
	}
	
	@After
	public void tearDown() throws Exception {
	}



}
