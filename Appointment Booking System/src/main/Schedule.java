package main;

public class Schedule {
	String day;
	String startTime;
	String endTime;
	String index;
	
	public Schedule(String index, String day, String startTime, String endTime)
	{
		this.index = index;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;		
	}
	
	public String getDay()
	{
		return day;
	}
	
	public String getStartTime()
	{
		return startTime;
	}
	
	public String getEndTime()
	{
		return endTime;
	}
	
	public String getIndex() {
		return index;
	}
}
