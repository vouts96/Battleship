package battleship;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileNotFoundException;



public class loader {

	public static void loadScenarios() {
		//___DECLARE SCREEN OBJECTS________________________________________
		Stage loadStage = new Stage();
		BorderPane rootLoad = new BorderPane();
		Scene loadScene = new Scene(rootLoad, 300, 120);
		Label textLabel = new Label("Give scenario ID: ");
		TextField scenarioText = new TextField();
		Button setIdButton = new Button("Set ID");
		VBox loadBox = new VBox();
		//_________________________________________________________________
		
		
		//___DETERMINE OBJECTS' PROPERTIES_________________________________
		loadBox.setSpacing(10);
		loadBox.setPadding(new Insets(10, 10, 10, 10));
		loadBox.getChildren().addAll(textLabel, scenarioText, setIdButton);
		loadStage.setTitle("Scenario ID");
		rootLoad.setCenter(loadBox);
		setIdButton.setOnAction(ActionEvent -> checkIdAndFile(scenarioText.getText(), loadStage));
		
		loadStage.setScene(loadScene);
		loadStage.show();
		//_________________________________________________________________
	}
	
	public static void checkIdAndFile(String Id, Stage stage) {
		//System.out.println(Main.currentScenario);
		
		try {
			String playerFilename = "./scenarios/player_" + Id + ".txt";
			String computerFilename = "./scenarios/enemy_" + Id + ".txt";
			
			//___READ PLAYER SCENARIO FILE_____________________________________
			if(fileFormatChecker.parser(playerFilename)) {
				throw new fileFormatException();
			}
			if(oversizeChecker.parser(playerFilename)) {
				throw new OversizeException();
			}
			if(InvalidCountChecker.parser(playerFilename)) {
				throw new InvalidCountException();
			}
			if(OverlapChecker.parser(playerFilename)) {
				throw new OverlapTilesException();
			}
			if(AdjacentChecker.parser(playerFilename)) {
				throw new AdjacentTilesException();
			}
			
			//_________________________________________________________________
			 
			 
			//___READ COMPUTER SCENARIO FILE___________________________________
			if(fileFormatChecker.parser(computerFilename)) {
				throw new fileFormatException();
			}
			
			if(oversizeChecker.parser(computerFilename)) {
				throw new OversizeException();
			}
			if(InvalidCountChecker.parser(computerFilename)) {
				throw new InvalidCountException();
			}
			if(OverlapChecker.parser(playerFilename)) {
				throw new OverlapTilesException();
			}
			if(AdjacentChecker.parser(playerFilename)) {
				throw new AdjacentTilesException();
			}
			//_________________________________________________________________
			
			//___IF ALL IS OK LOAD IS SUCCESS__________________________________
			SuccessLoad.handler(Id, stage);
		}
		catch(FileNotFoundException e){
			//___FILE NOT FOUND EXCEPTION____________________
			fileNotFound.handler(stage);
			
		}
		catch (fileFormatException e){
			//___FILE FORMAT EXCEPTION_______________________
			e.handler(stage);
		} 
		catch (OversizeException e) {
			//___OVERSIZE EXCEPTION__________________________
			e.handler(stage);
		} 
		catch (InvalidCountException e) {
			//___INVALID COUNT EXCEPTION_____________________
			e.handler(stage);
		} 
		catch (OverlapTilesException e) {
			//___OVERLAP TILES EXCEPTION_____________________
			e.handler(stage);
		} 
		catch (AdjacentTilesException e) {
			//___ADJACENT TILES EXCEPTION____________________
			e.handler(stage);
		}		
	}
	
}
