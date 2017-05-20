package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.*;

public class CustomerManagementTest {

	private CustomerManagement cManage;

	@Before
	public void setUp() throws Exception {
		cManage = new CustomerManagement("Suggar Haircut");
	}

	@Test
	public void testSelectBusiness() throws IOException {
		cManage.selectBusiness("Suggar Haircut");
		assertNotNull(cManage.getSelectedBusiness());
	}

	@Test
	public void testRetrieveBusiness() {
		List<Business> businessArray = new ArrayList<Business>();
		businessArray = cManage.retrieveBusiness();
		assertNotNull(businessArray);
	}

	@Test
	public void testRetrieveBookings() {
		List<Booking> bookingArray = new ArrayList<Booking>();
		bookingArray = cManage.retrieveBooking();
		assertNotNull(bookingArray);
	}

	@Test
	public void testRetrieveServices() {
		List<Service> serviceArray = new ArrayList<Service>();
		serviceArray = cManage.retrieveServices();
		assertNotNull(serviceArray);
	}

}
