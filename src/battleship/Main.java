package battleship;

	
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.*;
import javafx.scene.text.Font;



public class Main extends Application {
	
	//___Program Variables__________________________________________________________________
	//private TableView<gameInfo> infoTable = new TableView<gameInfo>();
	
	public static int[][] playerDefenseGrid = new int[10][10];
	public static int[][] playerAttackGrid = new int[10][10];
	public static int[][] computerDefenseGrid = new int[10][10];
	public static int[][] computerAttackGrid = new int[10][10];
	
	public static boolean playerFirst;
	
	public static int playerMoves = 0;
	public static int computerMoves = 0;
	
	public static int playerPoints = 0;
	public static int computerPoints = 0;
	
	public static int playerActiveShips = 0;
	public static int computerActiveShips = 0;
	
	public static int playerSuccessShots = 0;
	public static int computerSuccessShots = 0;
	
	public static float playerPercentage = 0;
	public static float computerPercentage = 0;
	
	public static String currentScenario;
	public static boolean scenarioStarted = false;
	
	public static List<computerCoords> computerToShoot = new ArrayList<computerCoords>();
	
	public static List<Ship> playerShips = new ArrayList<Ship>();
	public static List<Ship> computerShips = new ArrayList<Ship>();
	
	
	//______________________________________________________________________________________
	
	
	//___MAIN_______________________________________________________________________________
	public static void main(String[] args) {
		launch(args);
	}
	//______________________________________________________________________________________
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try{
			
			//Set Title and Create Window (BorderPane, Scene)
			primaryStage.setTitle("Medialab Battleship");
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1200,880);
			scene.setFill(Color.CORAL);
			
			//___TOP AREA___________________________________________________________________________
			
			
	        //______________________________________________________________________________________
	        
	        
	        //___CENTER AREA________________________________________________________________________
	        gameInfo.displayInfoGrid(root);
			
	        //______________________________________________________________________________________
	        
	        
	        //___BOTTOM AREA________________________________________________________________________
	        VBox bottomBox = new VBox();
	        Label fireLabel = new Label("Fire at (row, col)");
	        TextField fireText = new TextField();
	        Button fireButton = new Button("FIRE!");
	        fireButton.setOnAction(actionEvent -> shooter.fire(root, fireText.getText()));
	        
	        fireText.setMaxSize(200, 50);
	        
	        bottomBox.getChildren().addAll(fireLabel, fireText, fireButton);
	        bottomBox.setAlignment(Pos.CENTER);
	        fireLabel.setFont(Font.font("Verdana", 18));
	        fireLabel.setPadding(new Insets(5, 5, 0, 5));
	        fireButton.setMinSize(200, 50);
	        fireButton.setFont(Font.font("Verdana", 18));
	        root.setBottom(bottomBox);
	        //______________________________________________________________________________________
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
