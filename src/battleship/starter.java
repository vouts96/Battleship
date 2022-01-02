package battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class starter {
	
	public static void isScenarioSet(BorderPane root) throws FileNotFoundException {
		if(Main.currentScenario == null) {
			Stage errorStage = new Stage();
			BorderPane rootError = new BorderPane();
			Scene errorScene = new Scene(rootError, 180, 50);
			Label textLabel = new Label("NO SCENARIO LOADED");
			Button okButton = new Button("OK");
			
			okButton.setOnAction(ActionEvent -> errorStage.close());
			rootError.setTop(textLabel);
			rootError.setBottom(okButton);
			BorderPane.setAlignment(okButton, Pos.BOTTOM_CENTER);
			BorderPane.setAlignment(textLabel, Pos.TOP_CENTER);
			errorStage.setScene(errorScene);
			errorStage.show();
			
			
			System.out.println(Main.currentScenario);
			
		}
		else {
			Stage errorStage = new Stage();
			BorderPane rootError = new BorderPane();
			Scene errorScene = new Scene(rootError, 220, 80);
			Label textLabel = new Label("WANT TO START A NEW SCENARIO\nWITH ID " + Main.currentScenario + "?");
			Button noButton = new Button("NO");
			Button yesButton = new Button("YES");
			
			
			noButton.setOnAction(ActionEvent -> errorStage.close());
			yesButton.setOnAction(ActionEvent -> {
				try {
					errorStage.close();
					startNewScenario(root);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
			
			
			rootError.setTop(textLabel);
			rootError.setRight(noButton);
			rootError.setLeft(yesButton);
			
			BorderPane.setAlignment(yesButton, Pos.BOTTOM_LEFT);
			BorderPane.setAlignment(noButton, Pos.BOTTOM_RIGHT);
			BorderPane.setAlignment(textLabel, Pos.TOP_CENTER);
			
			errorStage.setScene(errorScene);
			errorStage.show();
			
			
			System.out.println(Main.currentScenario);
			
		}
	}
	
	//___FUNCTION THAT STARTS A NEW SCENARIO__________________________________________
	public static void startNewScenario(BorderPane root) throws FileNotFoundException{
			int shipId;
			
			//___CLEAR PROGRAM VARIABLES____________________
			Main.playerMoves = 0;
			Main.computerMoves = 0;
			Main.playerPoints = 0;
			Main.computerPoints = 0;
			Main.playerActiveShips = 0;
			Main.computerActiveShips = 0;
			Main.playerSuccessShots = 0;
			Main.computerSuccessShots = 0;
			Main.playerPercentage = 0;
			Main.computerPercentage = 0;
			Main.computerToShoot.clear();
			Main.scenarioStarted = true;
			
			//___INITIALIZE / CLEAR GRIDS____________________
			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					Main.playerDefenseGrid[i][j] = 0;
					Main.playerAttackGrid[i][j] = 0;
					Main.computerDefenseGrid[i][j] = 0;
					Main.computerAttackGrid[i][j] = 0;
				}
			}
			
			//___INITIALIZE PLAYER DEFENSE GRID__________________________________
			String Id = Main.currentScenario;
			String filename = "./scenarios/player_" + Id + ".txt";
			
			File parseFile = new File(filename);
			Scanner scanner = new Scanner(parseFile);
			
			while(scanner.hasNextLine()) {
				String data = scanner.nextLine();
		    	char dataArr[] = data.toCharArray();
		    	
		    	shipId = dataArr[0] - 48;
		    	Ship currentShip = new Ship(shipId);
		    	int currentShipLength = currentShip.getShipLength(currentShip);
		    	
		    	int row = dataArr[2] - 48;		
		    	int col = dataArr[4] - 48;
		    	int orientation = dataArr[6] - 48;
		    	
		    	if(orientation == 1) {
		    		for(int i=col; i<col+currentShipLength; i++) {
		    			Main.playerDefenseGrid[row][i] = shipId;
		    		}
		    	}
		    	else if(orientation == 2) {
		    		for(int i=row; i<row+currentShipLength; i++) {
		    			Main.playerDefenseGrid[i][col] = shipId;
		    		}
		    	}
		    	
		    	Main.playerShips.add(currentShip);
			}
			scanner.close();
			
			//___INITIALIZE COMPUTER DEFENSE GRID________________________________
			filename = "./scenarios/enemy_" + Id + ".txt";
			
			parseFile = new File(filename);
			scanner = new Scanner(parseFile);
			
			while(scanner.hasNextLine()) {
				String data = scanner.nextLine();
		    	char dataArr[] = data.toCharArray();
		    	
		    	shipId = dataArr[0] - 48;
		    	Ship currentShip = new Ship(shipId);
		    	int currentShipLength = currentShip.getShipLength(currentShip);
		    	
		    	int row = dataArr[2] - 48;		
		    	int col = dataArr[4] - 48;
		    	int orientation = dataArr[6] - 48;
		    	
		    	if(orientation == 1) {
		    		for(int i=col; i<col+currentShipLength; i++) {
		    			Main.computerDefenseGrid[row][i] = shipId;
		    		}
		    	}
		    	else if(orientation == 2) {
		    		for(int i=row; i<row+currentShipLength; i++) {
		    			Main.computerDefenseGrid[i][col] = shipId;
		    		}
		    	}
		    	
		    	Main.computerShips.add(currentShip);
			}
			scanner.close();
			
			System.out.println("SUCCESS");
			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					System.out.print(Main.playerDefenseGrid[i][j]);
				}
				System.out.println("");
			}
			
			System.out.println("");
			System.out.println("");
			
			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					System.out.print(Main.computerDefenseGrid[i][j]);
				}
				System.out.println("");
			}
				
			//System.out.print(Main.playerDefenseGrid);	
			displayDefenseGrid(root);
			displayAttackGrid(root);
			playerOrder();
			computerFirstMove(root);
			gameInfo.displayInfoGrid(root);
		}
	
		//___FUNCTION TO GRAPHICALLY DISPLAY PLAYER DEFENSE GRID____________________________
		public static void displayDefenseGrid(BorderPane root) {
			GridPane centerGrid = new GridPane();
	        centerGrid.setPrefSize(11, 11);
	        
	        
	        for(int i=0; i<11; i++) {
	        	Rectangle rec = new Rectangle(50, 50);
	        	rec.setFill(Color.LIGHTCYAN);
		        rec.setStroke(Color.BLACK);
		        
		        String s = String.valueOf(i-1);
		        Label text;
		        if(i==0) {
		        	text = new Label("");
		        }
		        else {
		        	text = new Label(s);
		        }
		        
		        text.setTextFill(Color.BLACK);
		        text.setPadding(new Insets(15, 15, 15, 15));
		        text.setFont(Font.font("Arial", 15));
		        Group gr = new Group();
		        gr.getChildren().addAll(rec, text);
		        
		        centerGrid.add(gr, i, 0);
		        
	        	
	        }
	        for(int i=1; i<11; i++) {
	        	Rectangle rec = new Rectangle(50, 50);
	        	rec.setFill(Color.LIGHTCYAN);
		        rec.setStroke(Color.BLACK);
		        
		        String s = String.valueOf(i-1);
		        
		        Label text = new Label(s);
		        text.setTextFill(Color.BLACK);
		        text.setPadding(new Insets(15, 15, 15, 15));
		        text.setFont(Font.font("Arial", 15));
		        Group gr = new Group();
		        gr.getChildren().addAll(rec, text);
		        
		        centerGrid.add(gr, 0, i);
		        
	        	
	        }
	        for(int i=0; i<10; i++) {
	        	for(int j=0; j<10; j++) {
	        		if(Main.playerDefenseGrid[i][j] == 0) {
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(Color.WHITE);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        		else if(Main.playerDefenseGrid[i][j] == -1){
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(Color.RED);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        		else if(Main.playerDefenseGrid[i][j] == -2){
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(Color.BLACK);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        		else {
	        			int shipId = Main.playerDefenseGrid[i][j];
	        			Ship currentShip = new Ship(shipId);
	        			
	        			Color cellColor = currentShip.getShipColor(currentShip);
	        			
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(cellColor);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        	}
	        }
	        
	        
	        centerGrid.setPadding(new Insets(10,10,10,10));
	        root.setLeft(centerGrid);
	        
		}
		
		
		
		//___FUNCTION TO GRAPHICALLY DISPLAY PLAYER ATTACK GRID____________________________
		public static void displayAttackGrid(BorderPane root) {
			GridPane centerGrid = new GridPane();
	        centerGrid.setPrefSize(11, 11);
	        
	        
	        for(int i=0; i<11; i++) {
	        	Rectangle rec = new Rectangle(50, 50);
	        	rec.setFill(Color.LIGHTCYAN);
		        rec.setStroke(Color.BLACK);
		        
		        String s = String.valueOf(i-1);
		        Label text;
		        if(i==0) {
		        	text = new Label("");
		        }
		        else {
		        	text = new Label(s);
		        }
		        
		        text.setTextFill(Color.BLACK);
		        text.setPadding(new Insets(15, 15, 15, 15));
		        text.setFont(Font.font("Arial", 15));
		        Group gr = new Group();
		        gr.getChildren().addAll(rec, text);
		        
		        centerGrid.add(gr, i, 0);
		        
	        	
	        }
	        for(int i=1; i<11; i++) {
	        	Rectangle rec = new Rectangle(50, 50);
	        	rec.setFill(Color.LIGHTCYAN);
		        rec.setStroke(Color.BLACK);
		        
		        String s = String.valueOf(i-1);
		        
		        Label text = new Label(s);
		        text.setTextFill(Color.BLACK);
		        text.setPadding(new Insets(15, 15, 15, 15));
		        text.setFont(Font.font("Arial", 15));
		        Group gr = new Group();
		        gr.getChildren().addAll(rec, text);
		        
		        centerGrid.add(gr, 0, i);
		        
	        	
	        }
	        for(int i=0; i<10; i++) {
	        	for(int j=0; j<10; j++) {
	        		if(Main.playerAttackGrid[i][j] == 0) {
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(Color.WHITE);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        		else if(Main.playerAttackGrid[i][j] == -1) {
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(Color.RED);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        		else if(Main.playerAttackGrid[i][j] == -2) {
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(Color.BLACK);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        		else {
	        			int shipId = Main.playerAttackGrid[i][j];
	        			Ship currentShip = new Ship(shipId);
	        			
	        			Color cellColor = currentShip.getShipColor(currentShip);
	        			
	        			Rectangle rec = new Rectangle(50, 50);
	    	        	rec.setFill(cellColor);
	    		        rec.setStroke(Color.BLACK);
	    		        
	    		        Label text = new Label("");
	    		        text.setTextFill(Color.BLACK);
	    		        text.setPadding(new Insets(15, 15, 15, 15));
	    		        text.setFont(Font.font("Arial", 15));
	    		        Group gr = new Group();
	    		        gr.getChildren().addAll(rec, text);
	    		        
	    		        centerGrid.add(gr, j+1, i+1);
	        		}
	        	}
	        }
	        
	        
	        centerGrid.setPadding(new Insets(10,10,10,10));
	        root.setRight(centerGrid);
	        
		}
	

		public static void playerOrder() {
			Random rand = new Random();
			String text = "";
			
			int order = rand.nextInt(2);
			
			
			//___0 stands for player first__________
			if(order == 0) {
				Main.playerFirst = true;
				text = "PLAYER";
			}
			//___1 stands for computer first________
			else if(order == 1) {
				Main.playerFirst = false;
				text = "COMPUTER";
			}
			
			Stage orderStage = new Stage();
			BorderPane rootOrder = new BorderPane();
			Scene orderScene = new Scene(rootOrder, 180, 50);
			Label textLabel = new Label(text + " PLAYS FIRST");
			Button okButton = new Button("OK");
			
			okButton.setOnAction(ActionEvent -> orderStage.close());
			rootOrder.setTop(textLabel);
			rootOrder.setBottom(okButton);
			BorderPane.setAlignment(okButton, Pos.BOTTOM_CENTER);
			BorderPane.setAlignment(textLabel, Pos.TOP_CENTER);
			orderStage.setScene(orderScene);
			orderStage.show();
		}

		public static void computerFirstMove(BorderPane root) {
			if(Main.playerFirst == false) {
				Random rand = new Random();
				
				int row = rand.nextInt(10);
				int col = rand.nextInt(10);
				
				if(Main.playerDefenseGrid[row][col] == 0) {
					Main.playerDefenseGrid[row][col] = -2;
					Main.computerAttackGrid[row][col] = -2;
				}
				else if(Main.playerDefenseGrid[row][col] > 0) {
					Main.computerSuccessShots++;
					
					
					for(int i=0; i<Main.playerShips.size(); i++) {
						Ship s = Main.playerShips.get(i);
						if(Main.playerDefenseGrid[row][col] == s.getShipId(s)) {
							Main.playerShips.get(i).increaseShotsTaken();
							Main.computerPoints += Main.playerShips.get(i).getShotPoints(Main.playerShips.get(i));
							if(Main.playerShips.get(i).getShipLength(Main.playerShips.get(i)) == Main.playerShips.get(i).getShotsTaken()) {
								Main.computerPoints += Main.playerShips.get(i).getSinkBonusPoints(Main.playerShips.get(i));
							}
						}
					}
					
					Main.playerDefenseGrid[row][col] = -1;
					Main.computerAttackGrid[row][col] = -1;
					
					if(row+1 < 10) {
						if(Main.computerAttackGrid[row+1][col] == 0) {
							computerCoords cell = new computerCoords(row+1, col);
							Main.computerToShoot.add(cell);
						}
					}
					if(row-1 >= 0 ) {
						if(Main.computerAttackGrid[row-1][col] == 0) {
							computerCoords cell = new computerCoords(row-1, col);
							Main.computerToShoot.add(cell);
						}
						
					}
					if(col+1 < 10) {
						if(Main.computerAttackGrid[row][col+1] == 0) {
							computerCoords cell = new computerCoords(row, col+1);
							Main.computerToShoot.add(cell);
						}
					}
					if(col-1 >= 0) {
						if(Main.computerAttackGrid[row][col-1] == 0) {
							computerCoords cell = new computerCoords(row, col-1);
							Main.computerToShoot.add(cell);
						}
					}
				}
				
				Main.computerMoves++;
				Main.computerPercentage = (float) Main.computerSuccessShots / (float) Main.computerMoves * 100;
				displayDefenseGrid(root);
				starter.displayDefenseGrid(root);
				gameInfo.displayInfoGrid(root);
			}
		}
}

	

