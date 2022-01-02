package battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OverlapChecker {

	private static Scanner reader;

	public static boolean parser(String filename) throws FileNotFoundException {
		boolean[][] visited = new boolean[10][10];
		
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
	    		for(int i=col; i<col + currentShipLength; i++) {
	    			if(visited[row][i] == true) {
	    				reader.close();
	    				return true;
	    			}
	    			else if(visited[row][i] == false) {
	    				visited[row][i] = true;
	    			}
	    		}
	    	}
	    	else if(orientation == 2) {
	    		for(int i=row; i<row + currentShipLength; i++) {
	    			if(visited[i][col] == true) {
	    				reader.close();
	    				return true;
	    			}
	    			else if(visited[i][col] == false) {
	    				visited[i][col] = true;
	    			}
	    		}
	    	}
	    	
		}
		
		reader.close();
		return false;
	}
}
