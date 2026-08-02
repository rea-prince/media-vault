package mediavault.controllers;
import mediavault.models.*;

import java.util.ArrayList;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;


public class MainController {

	@FXML private VBox entryList;
	@FXML private MenuItem filterButton;
	@FXML private MenuItem summarizeButton;
	@FXML private StackPane stackView;
	@FXML private VBox popupPane;

	private MediaVault vault;

	public MediaVault getVault() {
		return vault;
	}

	@FXML
	public void openDeleteEntry(MediaEntry selectedEntry) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/DeletePrompt.fxml")
		);

		Parent popup = loader.load();
		DeleteEntryController controller = loader.getController();

		controller.setTargetEntry(selectedEntry, entry -> {
			vault.removeEntry(entry.getDetails().getTitle(), entry.getDetails().getYear());
			popupPane.getChildren().remove(popup);
			try {
				showEntries(vault.getAll());
			} catch (IOException except) {
				except.printStackTrace();
			}
		});

		popupPane.getChildren().add(popup);
	}

	@FXML
	public void openReview(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/Review.fxml")
		);

		Parent popup = loader.load();
		stackView.getChildren().add(popup);
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

	@FXML
	public void openSummary(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/Summary.fxml")
		);

		Parent popup = loader.load();

		SummaryController controller = loader.getController();
		controller.setVault(vault);

		popupPane.getChildren().add(popup);
	}

	@FXML
	public void openAddEntry(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/AddEntryPrompt.fxml")
		);

		Parent popup = loader.load();
		AddEntryController controller = loader.getController();
		controller.setOnAdd(cont -> {
			try {
				vault.addEntry(cont.buildEntry());
				showEntries(vault.getAll());
			} catch (IOException except) {
				except.printStackTrace();
			}
		});

		popupPane.getChildren().add(popup);
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
			entryController.setOnDelete(controller -> {
				try {
					openDeleteEntry(entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			entryController.setEntryView();

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
}
