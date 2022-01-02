package battleship;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class fileNotFound {

	public static void handler(Stage load) {
		Stage errorStage = new Stage();
		BorderPane rootError = new BorderPane();
		Scene errorScene = new Scene(rootError, 180, 50);
		Label textLabel = new Label("ERROR: FILE ID NOT FOUND");
		Button okButton = new Button("OK");
		
		okButton.setOnAction(ActionEvent -> closeErrors.okButtonFunction(errorStage, load));
		rootError.setTop(textLabel);
		rootError.setBottom(okButton);
		BorderPane.setAlignment(okButton, Pos.BOTTOM_CENTER);
		BorderPane.setAlignment(textLabel, Pos.TOP_CENTER);
		errorStage.setScene(errorScene);
		errorStage.show();
		
		System.out.println("FILE NOT FOUND");
	}
}
