package battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileFormatChecker {
	
	private static Scanner reader;

	public static boolean parser(String filename) throws FileNotFoundException {
		int countLines;
		File parseFile = new File(filename);
		reader = new Scanner(parseFile);
		
		countLines = 0;
		while (reader.hasNextLine()) {
	    	countLines++;
	    	String data = reader.nextLine();
	    	char dataArr[] = data.toCharArray();
	    	
	    	if(data.length() != 7) {
	    		reader.close();
	    		return true;
	    	}
	    	for(int i=0; i<data.length(); i++) {
	    		if(dataArr[i] != 44 && (dataArr[i] < 48 || dataArr[i] > 57)) {
	    			reader.close();
	    			return true;
	    		}
	    		if(i%2 == 0) {
	    			if(dataArr[i] < 48 || dataArr[i] > 57) {
	    				reader.close();
	    				return true;
	    			}
	    		}
	    		else if(i%2 == 1) {
	    			if(dataArr[i] != 44) {
	    				reader.close();
	    				return true;
	    			}
	    		}
	    		
	    	}
	    	if(dataArr[0] < 49 || dataArr[0] > 53) {
	    		reader.close();
	    		return true;
	    	}
	    	
	        System.out.println(data);
	     }
	     if(countLines != 5) {
	    	 reader.close();
	    	 return true;
	     }
	     
	     reader.close();
	     return false;
	}
	
	
}
