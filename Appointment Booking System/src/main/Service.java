package main;

public class Service {
	
	private String name;			// Name of the service
	private int duration;			// Duration of the service in minutes
	private String description;		// Description of the service
	
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
	
	public void displayService(){
		System.out.print(this.name);

		int nameLength = this.name.length();

		for (int i = nameLength; i < 16; i++) {
			System.out.print(" ");
		}
		
		System.out.print(this.duration);
		
		int durationLength = Integer.toString(duration).length();
		
		for(int i = durationLength; i < 10; i++){
			System.out.print(" ");
		}

		System.out.print(this.description);

		System.out.println();
	}
	
	

}
