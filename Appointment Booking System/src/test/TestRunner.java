package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
		
	   public static void main(String[] args) {
		   
		  System.out.println("Testing Registration");
	      Result resultRegistration = JUnitCore.runClasses(RegistrationTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureRegistration : resultRegistration.getFailures()) {
	    	  
	         System.out.println(failureRegistration.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultRegistration.wasSuccessful());

		   
		  System.out.println("Testing Login");
	      Result resultLogin = JUnitCore.runClasses(LoginTest.class);
			
	      System.out.println("Failures:");
	      
	      for (Failure failureLogin : resultLogin.getFailures()) {
	    	  
	         System.out.println(failureLogin.toString());
	         
	      }

	      System.out.println("Successful Test:");
	      System.out.println(resultLogin.wasSuccessful());
	   }
	
}
