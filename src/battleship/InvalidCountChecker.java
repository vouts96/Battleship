package battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InvalidCountChecker {

	private static Scanner reader;

	public static boolean parser(String filename) throws FileNotFoundException{
		boolean[] visited = new boolean[5];
		
		File parseFile = new File(filename);
		reader = new Scanner(parseFile);
		
		while(reader.hasNextLine()) {
			String data = reader.nextLine();
	    	char dataArr[] = data.toCharArray();
	    	
	    	int currentNum = dataArr[0] - 48;
	    	if(visited[currentNum - 1] == false) {
	    		visited[currentNum - 1] = true;
	    	}
	    	else if(visited[currentNum - 1] == true) {
	    		reader.close();
	    		return true;
	    	}
		}
		
		reader.close();
		return false;
	}
}
