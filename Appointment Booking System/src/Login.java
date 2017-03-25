import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Array;

public class Login
{	
	static String delim = " ";
	
	public boolean verifyLoginDetails(String username, String password, String userType) throws IOException {
		
		String[] words;
		String line = "";
		FileReader fr = new FileReader(userType);
		BufferedReader reader = new BufferedReader(fr);
		
		while((line = reader.readLine()) != null)
		{
			words = line.split(delim);
			
			if((words[0]).equals(username))
			{
				System.out.println("Username found!");
				if((words[1]).equals(password))
				{
					System.out.println("Password valid!");
					reader.close();
					return true;
				}
			}
		}
		
		System.out.println("Username or password not found\n");
		reader.close();
		return false;
	}
}
