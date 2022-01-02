package battleship;

import javafx.stage.Stage;

public class closeErrors {

	public static void okButtonFunction(Stage error, Stage load) {
		error.close();
		load.close();
		loader.loadScenarios();
	}
}
