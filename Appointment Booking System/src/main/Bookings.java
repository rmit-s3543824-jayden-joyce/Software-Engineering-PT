package main;

public class Bookings {
	String index;
	String year;
	String month;
	String day;
	String startTime;
	String value;
	
	public Bookings(String index, String year, String month, String day, String startTime, String value)
	{
		this.index = index;
		this.year = year;
		this.month = month;
		this.day = day;
		this.startTime = startTime;
		this.value = value;		
	}
	
	public String getIndex() {
		
		return index;
		
	}
	
	public String getYear() {
		
		return year;
		
	}
	
	public String getMonth() {
		
		return month;
		
	}
	
	public String getDay()
	{
		return day;
	}
	
	public String getStartTime()
	{
		return startTime;
	}
	
	public String getValue() {
		
		return value;
		
	}

}
