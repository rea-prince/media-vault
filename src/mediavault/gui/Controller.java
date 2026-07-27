
package mediavault.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Controller extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);

		stage.setTitle("Media Vault");

		stage.setScene(scene);

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
