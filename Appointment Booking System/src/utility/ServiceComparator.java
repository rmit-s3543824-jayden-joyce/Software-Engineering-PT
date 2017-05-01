package utility;

import java.util.Comparator;

import main.Service;

public class ServiceComparator implements Comparator<Service>{
	
	@Override
	public int compare(Service s1, Service s2){
		
		return s1.getName().compareTo(s2.getName());
	}

}
