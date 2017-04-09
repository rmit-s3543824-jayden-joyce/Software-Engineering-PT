package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
		
	   public static void main(String[] args) {
		   
		  //test Registration file
		   
		  System.out.println("Testing Registration");
	      Result resultRegistration = JUnitCore.runClasses(RegistrationTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureRegistration : resultRegistration.getFailures()) {
	    	  
	         System.out.println(failureRegistration.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultRegistration.wasSuccessful());

	      //test login file
		   
		  System.out.println("Testing Login");
	      Result resultLogin = JUnitCore.runClasses(LoginTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureLogin : resultLogin.getFailures()) {
	    	  
	         System.out.println(failureLogin.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultLogin.wasSuccessful());
	      
	      //tests Utility File

		  System.out.println("Testing Utility");
	      Result resultUtility = JUnitCore.runClasses(UtilityTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureUtility : resultUtility.getFailures()) {
	    	  
	         System.out.println(failureUtility.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultUtility.wasSuccessful());
	      
	      //tests Booking

		  System.out.println("Testing Booking");
	      Result resultBooking = JUnitCore.runClasses(UtilityTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureBooking : resultBooking.getFailures()) {
	    	  
	         System.out.println(failureBooking.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultBooking.wasSuccessful());
	      
	      //tests Business Management

		  System.out.println("Testing Business Management");
	      Result resultBusinessManagement = JUnitCore.runClasses(UtilityTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureBusinessManagement : resultBusinessManagement.getFailures()) {
	    	  
	         System.out.println(failureBusinessManagement.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultBusinessManagement.wasSuccessful());
	      
	      //tests Business

		  System.out.println("Testing Business");
	      Result resultBusiness = JUnitCore.runClasses(UtilityTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureBusiness : resultBusiness.getFailures()) {
	    	  
	         System.out.println(failureBusiness.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultBusiness.wasSuccessful());
	      
	      //tests Customer Management

		  System.out.println("Testing Customer Management");
	      Result resultCustomerManagement = JUnitCore.runClasses(UtilityTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureCustomerManagement : resultCustomerManagement.getFailures()) {
	    	  
	         System.out.println(failureCustomerManagement.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultCustomerManagement.wasSuccessful());
	      
	   }
	
}
