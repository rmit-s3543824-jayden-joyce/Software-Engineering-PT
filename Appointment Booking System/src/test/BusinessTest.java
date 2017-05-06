package test;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Business;

public class BusinessTest {

	private Business business;

	@Before
	public void setUp() throws Exception {
		business = new Business("Suggar Haircut", "Suggar", "15 Lonsdale Street", "0412345678", "1;2;3;4;5",
				"10;0;9;0;9;0;9;0;10;30", "18;0;18;30;18;30;18;30;17;30");
	}

	// To test the constructors if they are constructing the bookings correctly,
	// and also if the accessors are working as intended
	@Test
	public void testConstructorsAndAccessors() {
		assertEquals(business.getName(), "Sui Haircut");
		assertEquals(business.getOwner(), "Sui");
		assertEquals(business.getAddress(), "15 Lonsdale Street");
		assertEquals(business.getPhone(), "0412345678");
		assertEquals(business.getOpenTime(), LocalTime.of(9, 00));
		assertEquals(business.getCloseTime(), LocalTime.of(18, 00));
	}

	// To test if the working day is being added to the arrayList
	@Test
	public void testAddingOpeningDay() {
		business.addOpeningDays(1);
		assertEquals(business.getOpeningDays().get(0).getValue(), 1);
	}

	@Test
	public void testdisplayOpeningDayAndTime() {
		business.addOpeningDays(1);
		business.addOpeningDays(2);
		business.addOpeningDays(3);
		business.addOpeningDays(4);
		business.addOpeningDays(5);
		business.displayOpeningDayAndTime();
	}

	// To test if adding repeated working day will result in duplicate
	@Test
	public void testFilterDuplicateOfOpeningDays() {
		business.addOpeningDays(1);
		assertEquals(business.getOpeningDays().size(), 1);
		business.addOpeningDays(1);
		business.addOpeningDays(1);
		business.addOpeningDays(1);
		assertEquals(business.getOpeningDays().size(), 1);
	}
}
