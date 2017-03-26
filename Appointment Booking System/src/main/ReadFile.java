package main;

import java.io.IOException;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile {

	private String path;
	
	public ReadFile(String file_path){
		path = file_path;
	}
	
	
	
	public String[] OpenFile() throws IOException{
		
		FileReader fr = new FileReader(this.path);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();
		String[] textData = new String[numberOfLines];
		
		int i;
		
		for(i=0;i<numberOfLines;i++){
			textData[i] = textReader.readLine();
			System.out.println(textData[i]);
		}
		
		textReader.close();
		return textData;
		
	}
	
	
	
	public String[] FindBusiness(String businessName) throws IOException{
		
		FileReader fr = new FileReader(this.path);
		BufferedReader textReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();

		
		int i;
		for(i=0;i<numberOfLines;i++){
			String currentLine = textReader.readLine();
			
			StringTokenizer st = new StringTokenizer(currentLine,";");
			String[] tokens = new String[st.countTokens()];
		
			int j = 0;
				while(st.hasMoreTokens()){
					tokens[j] = st.nextToken();
					j++;
						}
				
				
				if(tokens[0].equals(businessName)){
 					return tokens;
					}
		
			
			}
		
		return null;


		
		
		
	}
	
	public int readLines() throws IOException{
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader(file_to_read);
		
		String aLine;
		int numberOfLines = 0;
		
		while((aLine = bf.readLine())!= null){
			numberOfLines++;
		}
		
		bf.close();
		
		return numberOfLines;
	}
}
