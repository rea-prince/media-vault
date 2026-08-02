package mediavault;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import mediavault.controllers.MainController;
import mediavault.controllers.FilterController;
import mediavault.models.MediaVault;

public class MainGUI extends Application {


	@Override
	public void start(Stage stage) throws Exception {

		try {
			// set scene

			FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fxml/Main.fxml")
			);

			Parent root = loader.load();

			// setup controller

			MainController controller = loader.getController();
			controller.setVault(new MediaVault());
			controller.initComponent();

			// render

			Scene scene = new Scene(root);
			// scene.getStylesheets().add("/css/browny.css");

			stage.setMinWidth(800);
			stage.setMinHeight(600);
			stage.setTitle("Media Vault");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
