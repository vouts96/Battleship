package battleship;


import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class shooter {
	
	
	public static void fire(BorderPane root, String coords) {
		char dataArr[] = coords.toCharArray();
		
		if(Main.scenarioStarted == true) {
			//___CHECK FOR WRONG INPUT__________________________________
			if(coords.length() != 3) {
				fireErrors("ERROR: YOUR INPUT MUST BE row,col");
				return;
			}
			else if(coords.length() == 3) {
				if(dataArr[0] < 48 || dataArr[0] > 57) {
					fireErrors("ERROR: YOUR INPUT MUST BE row,col");
					return;
				}
				if(dataArr[2] < 48 || dataArr[2] > 57) {
					fireErrors("ERROR: YOUR INPUT MUST BE row,col");
					return;
				}
				if(dataArr[1] != 44) {
					fireErrors("ERROR: YOUR INPUT MUST BE row,col");
					return;
				}
			}
			//__________________________________________________________
			
			int row = dataArr[0] - 48;
			int col = dataArr[2] - 48;
			
			//___PLAYER ATTACK__________________________________________
			if(Main.playerMoves < 40) {
				if(Main.computerDefenseGrid[row][col] > 0) {
					Main.playerSuccessShots++;
					
					for(int i=0; i<Main.computerShips.size(); i++) {
						Ship s = Main.computerShips.get(i);
						
						if(Main.computerDefenseGrid[row][col] == s.getShipId(s)) {
							Main.computerShips.get(i).increaseShotsTaken();
							Main.playerPoints += Main.computerShips.get(i).getShotPoints(Main.computerShips.get(i));
							if(Main.computerShips.get(i).getShipLength(Main.computerShips.get(i)) == Main.computerShips.get(i).getShotsTaken()) {
								Main.playerPoints += Main.computerShips.get(i).getSinkBonusPoints(Main.computerShips.get(i));
							}
							
						}
					}
					
					Main.playerAttackGrid[row][col] = -1;
					Main.computerDefenseGrid[row][col] = -1;
					Main.playerMoves++;
					starter.displayAttackGrid(root);
					
					
				}
				else if(Main.computerDefenseGrid[row][col] == 0) {
					Main.playerAttackGrid[row][col] = -2;
					Main.computerDefenseGrid[row][col] = -2;
					starter.displayAttackGrid(root);
					Main.playerMoves++;
				}
				else if(Main.computerDefenseGrid[row][col] < 0) {
					fireErrors("YOU HAVE ALREADY ATTACKED THIS CELL");
					return;
				}
				
				Main.playerPercentage = (float) Main.playerSuccessShots / (float) Main.playerMoves * 100;
				
			}
			
			
			//___COMPUTER ATTACK________________________________________
			computerAttack(root);
			
			
		}
		else if(Main.scenarioStarted == false) {
			fireErrors("NO ACTIVE SCENARIO");
			return;
		}
		
		if(Main.playerMoves == 40 && Main.computerMoves == 40) {
			if(Main.playerPoints > Main.computerPoints) {
				fireErrors("GAME OVER\nPLAYER WINS");
			}
			else if(Main.playerPoints < Main.computerPoints) {
				fireErrors("GAME OVER\nCOMPUTER WINS");
			}
			else if(Main.playerPoints == Main.computerPoints) {
				fireErrors("GAME OVER\nWE HAVE A TIE");
			}
		}
		
		if(Main.playerActiveShips == 0) {
			fireErrors("GAME OVER\nCOMPUTER WINS");
		}
		if(Main.computerActiveShips == 0) {
			fireErrors("GAME OVER\nPLAYER WINS");
		}
		
		gameInfo.displayInfoGrid(root);
		
		System.out.print(Main.playerMoves);
		System.out.println(Main.computerMoves);
	}
	
	
	public static void fireErrors(String text) {
		Stage errorStage = new Stage();
		BorderPane rootError = new BorderPane();
		Scene errorScene = new Scene(rootError, 220, 80);
		Label textLabel = new Label(text);
		Button okButton = new Button("OK");
		
		okButton.setOnAction(ActionEvent -> errorStage.close());
		rootError.setTop(textLabel);
		rootError.setBottom(okButton);
		BorderPane.setAlignment(okButton, Pos.BOTTOM_CENTER);
		BorderPane.setAlignment(textLabel, Pos.TOP_CENTER);
		errorStage.setScene(errorScene);
		errorStage.show();
		
		System.out.println(text);
	}
	
	
	//___COMPUTER ATTACK FUNCTION________________________________________
	public static void computerAttack(BorderPane root) {
		Random rand = new Random();
		
		if(Main.computerToShoot.isEmpty()) {
			int row = rand.nextInt(10);
			int col = rand.nextInt(10);
			
			if(Main.computerMoves < 40) {
				while(Main.playerDefenseGrid[row][col] < 0) {
					row = rand.nextInt(10);
					col = rand.nextInt(10);
				}
				if(Main.playerDefenseGrid[row][col] > 0) {
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
					
					Main.computerAttackGrid[row][col] = -1;
					Main.playerDefenseGrid[row][col] = -1;
					starter.displayDefenseGrid(root);
					Main.computerMoves++;
					Main.computerPercentage = (float) Main.computerSuccessShots/ (float) Main.computerMoves* 100;
					
					//___If successful add adjacents to list for next move___
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
				else if(Main.playerDefenseGrid[row][col] == 0) {
					Main.computerAttackGrid[row][col] = -2;
					Main.playerDefenseGrid[row][col] = -2;
					starter.displayDefenseGrid(root);
					Main.computerMoves++;
				}
				
			}
		}
		else if(!Main.computerToShoot.isEmpty()) {
			if(Main.computerMoves < 40) {
				
				
				computerCoords coords = Main.computerToShoot.get(0);
				int row = coords.getRow();
				int col = coords.getCol();
				Main.computerToShoot.remove(0);
				while(Main.playerDefenseGrid[row][col] < 0) {
					coords = Main.computerToShoot.get(0);
					row = coords.getRow();
					col = coords.getCol();
					Main.computerToShoot.remove(0);
				}
				
			
			
				if(Main.playerDefenseGrid[row][col] > 0) {
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
					
					Main.computerAttackGrid[row][col] = -1;
					Main.playerDefenseGrid[row][col] = -1;
					starter.displayDefenseGrid(root);
					Main.computerMoves++;
					Main.computerPercentage = (float) Main.computerSuccessShots / (float) Main.computerMoves * 100;
					
					//___If successful add adjacents to list for next move___
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
				else if(Main.playerDefenseGrid[row][col] == 0) {
					Main.computerAttackGrid[row][col] = -2;
					Main.playerDefenseGrid[row][col] = -2;
					starter.displayDefenseGrid(root);
					Main.computerMoves++;
				}
				
			}
		}
	}
	
}
