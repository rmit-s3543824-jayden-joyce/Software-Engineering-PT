package utility;

import java.time.LocalDateTime;
import java.util.Comparator;

import main.Booking;

public class BookingComparator implements Comparator<Booking> {
	
	@Override
	public int compare(Booking ldt1, Booking ldt2){
		
		if(ldt1.getDate() == ldt2.getDate())
			return ldt1.getTime().compareTo(ldt2.getTime());
		else
		return ldt1.getDate().compareTo(ldt2.getDate());
	}
	
	
	

}
