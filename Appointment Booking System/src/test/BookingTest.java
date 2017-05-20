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
		newBooking = new Booking("2017","6","14","15","00","Alfred","Johannah","Men's Haircut");
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
	public void testRefreshStatusFromActiveToExpired(){
		oldBooking.setDate(LocalDate.of(2017, 6, 14));
		oldBooking.refreshStatus();
		assertEquals(oldBooking.getStatus(),"Active");
		oldBooking.setDate(LocalDate.of(2017, 1, 1));
		oldBooking.refreshStatus();
		assertEquals(oldBooking.getStatus(),"Expired");
	}
	
	@Test
	public void testRefreshStatusUnchagedForNew(){
		assertEquals(newBooking.getStatus(), "NEW");
		newBooking.refreshStatus();
		assertEquals(newBooking.getStatus(),"NEW");
	}
	
	@Test
	public void testRefreshStatusFromNewToExpired(){
		assertEquals(newBooking.getStatus(),"NEW");
		newBooking.setDate(LocalDate.of(2017, 1, 1));
		newBooking.refreshStatus();
		assertEquals(newBooking.getStatus(),"Expired");
	}
	

}
