package mediavault.controllers;
import mediavault.models.MediaEntry;
import mediavault.models.MediaVault;

import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

public class MainController {

	@FXML
	private VBox entryList;

	@FXML
	private Menu filterButton;

	@FXML
	private StackPane stack;


	private MediaVault vault;


	public void openFilterMenu() {
		FXMLLoader loader = new FXMLLoader(
			getClass().getRrsource("/fxml/FilterPrompt.fxml")
		);

		Parent popup = loader.load();

	}

	public void setVault(MediaVault vault) throws IOException {
		this.vault = vault;
		showEntries();
	}

	public void showEntries() throws IOException {
		entryList.getChildren().clear();

		for (MediaEntry entry : vault.getAll()) {
			FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fxml/EntryCard.fxml")
			);

			Parent card = loader.load();

			EntryCardController entryController = loader.getController();
			entryController.setEntry(entry);

			entryList.getChildren().add(card);
		}
	}

}
