package main;

public class Customer {
	String name;
	String street;
	String suburb;
	String postcode;
	String phone;
	
	public Customer(String name, String street, String suburb, String postcode, String phone)
	{
		this.name = name;
		this.street = street;
		this.suburb = suburb;
		this.postcode = postcode;
		this.phone = phone;
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public String getSteet() {
		
		return street;
		
	}
	
	public String getSuburb() {
		
		return suburb;
		
	}
	
	public String getPostcode()
	{
		return postcode;
	}
	
	public String getPhone()
	{
		return phone;
	}

}
