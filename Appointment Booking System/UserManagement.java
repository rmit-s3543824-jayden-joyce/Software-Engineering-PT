package main;

import java.io.IOException;

public class UserManagement {

	private ManagementInterface businessManagement;
	private ManagementInterface customerManagement;

	public UserManagement(String businessName) throws IOException {

		businessManagement = new BusinessManagement(businessName);
		customerManagement = new CustomerManagement(businessName);

	}

	public void runCustomerMenu() throws IOException {
		customerManagement.runMenu();
	}

	public void runBusinessMenu() throws IOException {
		businessManagement.runMenu();
	}

}
