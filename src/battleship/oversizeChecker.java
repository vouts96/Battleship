package battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class oversizeChecker {
	
	private static Scanner reader;

	public static boolean parser(String filename) throws FileNotFoundException {
		
		File parseFile = new File(filename);
		reader = new Scanner(parseFile);
		
		while(reader.hasNextLine()) {
			String data = reader.nextLine();
	    	char dataArr[] = data.toCharArray();
	    	
	    	int shipNum = dataArr[0] - 48;
	    	Ship currentShip = new Ship(shipNum);
	    	int currentShipLength = currentShip.getShipLength(currentShip);
	    	
	    	int row = dataArr[2] - 48;		
	    	int col = dataArr[4] - 48;
	    	int orientation = dataArr[6] - 48;
	    	
	    	//System.out.println(orientation);
	    	//System.out.println(currentShipLength);
	    	
	    	if(orientation == 1) {
	    		if(col + currentShipLength - 1 > 9) {
	    			reader.close();
	    			return true;
	    		}
	    	}
	    	else if(orientation == 2) {
	    		if(row + currentShipLength - 1 > 9) {
	    			reader.close();
	    			return true;
	    		}
	    	}
	    	
		}
		
		reader.close();
		return false;
	}
		
				
}

