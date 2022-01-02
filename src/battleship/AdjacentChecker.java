package battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdjacentChecker {
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
	    				System.out.println(row);
	    				System.out.println(i);
	    				reader.close();
	    				return true;
	    			}
	    			else if(visited[row][i] == false) {
	    				visited[row][i] = true;
	    				if(row+1 < 10) visited[row+1][i] = true;
	    				if(row-1 >= 0) visited[row-1][i] = true;
	    			}
	    		}
	    		if(col-1 >= 0) visited[row][col-1] = true;
	    		if(col+currentShipLength < 10) visited[row][col+currentShipLength] = true;
	    	}
	    	else if(orientation == 2) {
	    		for(int i=row; i<row + currentShipLength; i++) {
	    			if(visited[i][col] == true) {
	    				System.out.println(row);
	    				System.out.println(i);
	    				reader.close();
	    				return true;
	    			}
	    			else if(visited[i][col] == false) {
	    				visited[i][col] = true;
	    				if(col+1 < 10) visited[i][col+1] = true;
	    				if(col-1 >= 0) visited[i][col-1] = true;
	    			}
	    			if(row-1 >= 0) visited[row-1][col] = true;
		    		if(row+currentShipLength < 10) visited[row+currentShipLength][col] = true;
	    		}
	    	}
	    	
		}
		
		reader.close();
		return false;
	}
}
