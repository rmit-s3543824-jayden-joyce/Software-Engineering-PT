package main;

public class Service {
	
	private String name;
	private int duration;
	private String description;
	
	public Service(String name, String duration, String description){
		this.name = name;
		this.duration = Integer.valueOf(duration);
		this.description = description;
	}
	
	// Accesors
	
	public String getName(){
		return this.name;
	}
	
	public int getDuration(){
		return this.duration;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	// Mutators
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	

}
