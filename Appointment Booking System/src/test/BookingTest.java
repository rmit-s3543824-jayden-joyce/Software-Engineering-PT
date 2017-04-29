package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import main.Booking;

public class BookingTest {

	Booking oldBooking;
	Booking newBooking;

	
	@Before
	public void setUp() throws Exception {
		oldBooking = new Booking("Active","2017","3","20","11","30","Jenny","Lawrence","Men's Haircut");
		newBooking = new Booking("2017","4","11","15","00","Alfred","Johannah","Men's Haircut");
	}

	
	// To test the constructors if they are constructing the bookings correctly, and also if the accessors are working as intended
	@Test
	public void testConstructorsAndAccessors() {
		assertEquals(oldBooking.getCustomerName(),"Jenny");
		assertEquals(oldBooking.getEmployeeName(),"Lawrence");
		assertEquals(oldBooking.getDate(),LocalDate.of(2017, 3, 20));
		assertEquals(oldBooking.getTime(),LocalTime.of(11,30));
	}
	
	// To test if the system recognize expired bookings even if their last status was active
	@Test
	public void testOldBookingStatus(){
		assertEquals(oldBooking.getStatus(),"Expired");
	}
	
	// To test if the system recognize new bookings when they are initialized with a new booking constructor
	@Test
	public void testNewBookingStatus(){
		assertEquals(newBooking.getStatus(),"NEW");
	}
	
	// Manipulate the date of the booking to see if the function is udpating the status of the booking according to the time
	@Test
	public void testRefreshStatus(){
		oldBooking.setDate(LocalDate.of(2017, 5, 4));
		oldBooking.refreshStatus();
		assertEquals(oldBooking.getStatus(),"Active");
		oldBooking.setDate(LocalDate.of(2017, 1, 1));
		oldBooking.refreshStatus();
		assertEquals(oldBooking.getStatus(),"Expired");
	}
	

}
