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
import javafx.scene.layout.VBox;

public class MainController {

	@FXML
	private VBox entryList;


	private MediaVault vault;

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
