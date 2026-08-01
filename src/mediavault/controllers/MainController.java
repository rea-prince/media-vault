package mediavault.controllers;
import mediavault.models.*;
import mediavault.enums.*;

import java.util.Arrays;
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
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;


public class MainController {

	@FXML private VBox entryList;
	@FXML private MenuItem filterButton;
	@FXML private StackPane stackView;
	@FXML private VBox popupPane;

	private MediaVault vault;

	public MediaVault getVault() {
		return vault;
	}

	@FXML
	public void openAddEntryTab(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/DeletePrompt.fxml")
		);

		Parent popup = loader.load();
		stackView.getChildren().add(popup);
	}

	@FXML
	public void openDeleteTab(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/DeletePrompt.fxml")
		);

		Parent popup = loader.load();
		stackView.getChildren().add(popup);
	}

	@FXML
	public void openReviewTab(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/Review.fxml")
		);

		Parent popup = loader.load();
		stackView.getChildren().add(popup);
	}


	private void showEntries(ArrayList<MediaEntry> entries) throws IOException {
		entryList.getChildren().clear();

		if (vault == null) {
			return;
		}

		for (MediaEntry entry : entries) {
			FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fxml/EntryCard.fxml")
			);

			Parent card = loader.load();

			EntryCardController entryController = loader.getController();
			entryController.setEntry(entry);

			entryList.getChildren().add(card);
		}
	}

	public void initComponent() throws IOException {
		entryList.getChildren().clear();

		showEntries(vault.getAll());
	}

	public void setVault(MediaVault vault) {
		this.vault = vault;
	}

	@FXML
	public void openFilterMenu(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/FilterPrompt.fxml")
		);

		Parent popup = loader.load();
		FilterController controller = loader.getController();
		controller.setOnApply(cont -> {
			try {
				showEntries(vault.getEntries(
					null,
					cont.getSelectedYear().intValue(),
			        cont.getSelectedMediaType(),
					cont.getSelectedStatus(),
			        cont.getSelectedGenre()
    			));
			} catch (IOException except) {
				except.printStackTrace();
			}
			popupPane.getChildren().remove(popup);
		});
		controller.setOnCancel(() -> {
			popupPane.getChildren().remove(popup);
		});

		popupPane.getChildren().add(popup);
	}

}
