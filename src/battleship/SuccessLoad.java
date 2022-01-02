package battleship;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SuccessLoad {
	
	public static void handler(String Id, Stage load) {
		Stage errorStage = new Stage();
		BorderPane rootError = new BorderPane();
		Scene errorScene = new Scene(rootError, 180, 50);
		Label textLabel = new Label("LOAD SUCCESS");
		Button okButton = new Button("OK");
		
		okButton.setOnAction(ActionEvent -> okButtonFunction(errorStage, load));
		rootError.setTop(textLabel);
		rootError.setBottom(okButton);
		BorderPane.setAlignment(okButton, Pos.BOTTOM_CENTER);
		BorderPane.setAlignment(textLabel, Pos.TOP_CENTER);
		errorStage.setScene(errorScene);
		errorStage.show();
		
		Main.currentScenario = Id;
		System.out.println("LOAD SUCCESS");
	}
	
	public static void okButtonFunction(Stage error, Stage load) {
		error.close();
		load.close();
		
	}
}
