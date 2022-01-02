package battleship;

import java.io.FileNotFoundException;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class gameInfo {
	
	public static void displayInfoGrid(BorderPane root) {
		
		GridPane infoGrid = new GridPane();
		infoGrid.setPrefSize(3, 4);
		
		//Create Menu Bar
		MenuBar menuBar = new MenuBar();
					
		//Create Application Menu
		Menu appMenu = new Menu("Application");
		MenuItem startMenuItem = new MenuItem("Start");
		MenuItem loadMenuItem = new MenuItem("Load");
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
		loadMenuItem.setOnAction(actionEvent -> loader.loadScenarios());
		startMenuItem.setOnAction(actionEvent -> {
			try {
				starter.isScenarioSet(root);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		//Add Application Menu Items to Application Menu and Application Menu to Menu Bar
		appMenu.getItems().addAll(startMenuItem, loadMenuItem, exitMenuItem);
		menuBar.getMenus().add(appMenu);
		
		//Create Details Menu
		Menu detMenu = new Menu("Details");
		MenuItem EnemyShipsMenuItem = new MenuItem("Enemy Ships");
		MenuItem PlayerShotsMenuItem = new MenuItem("Player Shots");
		MenuItem EnemyShotsMenuItem = new MenuItem("Enemy Shots");
		//Add Details Menu Items to Details Menu and Details Menu to Menu Bar
		detMenu.getItems().addAll(EnemyShipsMenuItem, PlayerShotsMenuItem, EnemyShotsMenuItem);
		menuBar.getMenus().add(detMenu);
		
		//Create Game Info Table
		
		infoGrid.setAlignment(Pos.TOP_CENTER);
		
		//Group menuBar, infoLabel and infoTable to topBox
		final VBox topBox = new VBox();
        topBox.setSpacing(5);
        topBox.setPadding(new Insets(10, 10, 10, 10));
        topBox.setMaxHeight(150);
        topBox.getChildren().addAll(menuBar, infoGrid);
        root.setTop(topBox);
		
		
		
		//___COLUMN HEADERS__________________________
		for(int i=0; i<3; i++) {
        	Rectangle rec = new Rectangle(200, 30);
        	rec.setFill(Color.LIGHTCYAN);
	        rec.setStroke(Color.BLACK);
	        
	        
	        Label text = new Label("");
	        if(i==0) {
	        	text = new Label("Game Info");
	        }
	        else if(i==1){
	        	text = new Label("Player");
	        }
	        else if(i==2){
	        	text = new Label("Computer");
	        }
	        
	        text.setTextFill(Color.BLACK);
	        text.setPadding(new Insets(5, 5, 5, 5));
	        text.setFont(Font.font("Arial", 15));
	        Group gr = new Group();
	        gr.getChildren().addAll(rec, text);
	        
	        infoGrid.add(gr, i, 0);
        }
		
		//___ROW HEADERS_____________________________
		for(int i=1; i<4; i++) {
        	Rectangle rec = new Rectangle(200, 30);
        	rec.setFill(Color.LIGHTCYAN);
	        rec.setStroke(Color.BLACK);
	        
	        
	        Label text = new Label("");
	        if(i==1) {
	        	text = new Label("Active Ships");
	        }
	        else if(i==2){
	        	text = new Label("Points");
	        }
	        else if(i==3){
	        	text = new Label("Success Percentage");
	        }
	        
	        text.setTextFill(Color.BLACK);
	        text.setPadding(new Insets(5, 5, 5, 5));
	        text.setFont(Font.font("Arial", 15));
	        Group gr = new Group();
	        gr.getChildren().addAll(rec, text);
	        
	        infoGrid.add(gr, 0, i);
        }
		
		
		//___INFO CELLS_____________________________
		for(int i=1; i<3; i++) {
			for(int j=1; j<4; j++) {
				Rectangle rec = new Rectangle(200, 30);
	        	rec.setFill(Color.WHITE);
		        rec.setStroke(Color.BLACK);
		        
		        Label text = new Label("");
		        
		        if(i==1 && j==1) {
		        	int counter = 0;
		        	for(int z=0; z<Main.playerShips.size(); z++) {
						Ship s = Main.playerShips.get(z);
						if(s.isActive() || s.isUntouched()) {
							counter++;
						}
					}
		        	Main.playerActiveShips = counter;
		        	String s = String.valueOf(Main.playerActiveShips);
		        	text = new Label(s);
		        }
		        
		        else if(i==2 && j==1) {
		        	int counter = 0;
		        	for(int z=0; z<Main.computerShips.size(); z++) {
						Ship s = Main.computerShips.get(z);
						if(s.isActive() || s.isUntouched()) {
							counter++;
						}
					}
		        	Main.computerActiveShips = counter;
		        	String s = String.valueOf(Main.computerActiveShips);
		        	text = new Label(s);
		        }
		        else if(i==1 && j==2) {
		        	String s = String.valueOf(Main.playerPoints);
		        	text = new Label(s);
		        }
		        else if(i==2 && j==2) {
		        	String s = String.valueOf(Main.computerPoints);
		        	text = new Label(s);
		        }
		        else if(i==1 && j==3) {
		        	String s = String.valueOf(Main.playerPercentage);
		        	s += "%";
		        	text = new Label(s);
		        	System.out.println(Main.playerPercentage);
		        }
		        else if(i==2 && j==3) {
		        	String s = String.valueOf(Main.computerPercentage);
		        	s += "%";
		        	text = new Label(s);
		        }
		        
		        
		        text.setTextFill(Color.BLACK);
		        text.setPadding(new Insets(5, 5, 5, 5));
		        text.setFont(Font.font("Arial", 15));
		        Group gr = new Group();
		        gr.getChildren().addAll(rec, text);
		        
		        infoGrid.add(gr, i, j);
			}
		}
		
		
	}
	
	

}
