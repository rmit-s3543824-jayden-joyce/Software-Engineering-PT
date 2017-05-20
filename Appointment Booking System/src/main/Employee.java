package main;

public class Employee {
	String employeeId;
	String firstName;
	String lastName;
	
	public Employee(String empId, String empFirstName, String empLastName)
	{
		this.employeeId = empId;
		this.firstName = empFirstName;
		this.lastName = empLastName;		
	}
	
	public String getEmployeeId()
	{
		return employeeId;
	}
	
	public void setFirstName(String newFirstName)
	{
		 firstName = newFirstName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setLastName(String newLastName)
	{
		lastName = newLastName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
}
