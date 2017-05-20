package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.Booking;
import main.Business;
import main.BusinessManagement;

public class BusinessManagementTest {

	private BusinessManagement bManage;

	@Before
	public void setUp() throws IOException {
		bManage = new BusinessManagement("Suggar Haircut");

	}

	// Test if the business is not found in the business.txt, an IOException
	// will be thrown
	@Test
	public void testBusinessNotFound() throws IOException {
		bManage = new BusinessManagement("Domino's Pizza");
	}

	// Test if selectBusiness is able to retrieve the chosen business from the
	// business.txt
	@Test
	public void testSelectBusiness() throws IOException {
		bManage.selectBusiness("Suggar Haircut");
		assertNotNull(bManage.getSelectedBusiness());
	}

	// Test if retreiveBooking is able to obtain bookings from the text file and
	// set up an arrayList.
	@Test
	public void testRetrieveBooking() {
		List<Booking> bookingList;
		bookingList = bManage.retrieveBooking();
		assertFalse(bookingList.isEmpty());
	}

	// Test if when new bookings are being viewed, all the status of the new
	// bookings will be changed to Active
	@Test
	public void testViewNewBookings() throws IOException {
		boolean thereAreNewBookings = false;
		List<Booking> bookingList;
		bookingList = bManage.retrieveBooking();

		for (int i = 0; i < bookingList.size(); i++) {

			if (bookingList.get(i).getStatus().compareTo("NEW") == 0) {
				System.out.println(bookingList.get(i).getStatus());
				thereAreNewBookings = true;
			}
		}

		// Initially there will be new bookings with the status "New"
		assertTrue(thereAreNewBookings);

		bManage.viewNewBookings();
		bookingList = bManage.retrieveBooking();

		for (int i = 0; i < bookingList.size(); i++) {
			if (bookingList.get(i).getStatus().compareTo("NEW") != 0) {
				thereAreNewBookings = false;
			}
		}

		// After bookings are viewed, there are no longer bookings with the
		// status "New"
		assertEquals(false, thereAreNewBookings);

	}

	// To test if the input is being validated correctly, only allowing positive
	// integers as the input
	@Test
	public void testInputValidation() {
		assertEquals(false, bManage.isNumericAndPositive("!@#$%^&)"));
		assertEquals(false, bManage.isNumericAndPositive("-30"));
		assertEquals(false, bManage.isNumericAndPositive("0"));
		assertEquals(false, bManage.isNumericAndPositive("Just A Random String"));
		assertEquals(false, bManage.isNumericAndPositive("3.13"));
		assertEquals(true, bManage.isNumericAndPositive("3"));

	}

}
