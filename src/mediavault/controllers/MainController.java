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

	/**
	 * Opens a confirmation popup to delete the selected media entry.
	 * <p>
	 * <b>Precondition:</b> selectedEntry must not be null.<br>
	 * <b>Postcondition:</b> Loads the deletion prompt UI, sets up the removal callback on vault, and displays the popup.
	 * </p>
	 * @param selectedEntry The media entry selected for deletion.
	 * @throws IOException If the FXML resource cannot be loaded.
	 */
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

	/**
	 * Loads and displays the review modal screen.
	 * <p>
	 * <b>Precondition:</b> stackView container must be present in layout.<br>
	 * <b>Postcondition:</b> The review overlay scene is added to stackView.
	 * </p>
	 * @param e The ActionEvent triggering the review prompt.
	 * @throws IOException If the FXML resource cannot be loaded.
	 */
	@FXML
	public void openReview(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/Review.fxml")
		);

		Parent popup = loader.load();
		stackView.getChildren().add(popup);
	}


	/**
	 * Opens the filtering configuration popup and registers filter application handlers.
	 * <p>
	 * <b>Precondition:</b> popupPane container must be available.<br>
	 * <b>Postcondition:</b> Loads filter view, binds apply/cancel handlers to filter the displayed vault list, and renders popupPane.
	 * </p>
	 * @param e The ActionEvent triggering the filter dialog.
	 * @throws IOException If the FXML resource cannot be loaded.
	 */
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


	/**
	 * Loads and displays the vault statistics summary screen.
	 * <p>
	 * <b>Precondition:</b> vault must be initialized.<br>
	 * <b>Postcondition:</b> Summary controller is initialized with current vault instance and added to popupPane.
	 * </p>
	 * @param e The ActionEvent triggering summary view.
	 * @throws IOException If the FXML resource cannot be loaded.
	 */
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

	/**
	 * Displays the form overlay to add a new media entry into the vault.
	 * <p>
	 * <b>Precondition:</b> popupPane must be non-null.<br>
	 * <b>Postcondition:</b> Displays entry creation popup and handles insertion into vault on submit.
	 * </p>
	 * @param e The ActionEvent triggering the entry prompt.
	 * @throws IOException If the FXML resource cannot be loaded.
	 */
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


	/**
	 * Renders cards for each provided media entry in the primary entry container.
	 * <p>
	 * <b>Precondition:</b> entries list must not be null.<br>
	 * <b>Postcondition:</b> Clears entryList children and injects a generated card for each item in entries.
	 * </p>
	 * @param entries List of MediaEntry objects to render.
	 * @throws IOException If FXML loading fails for card view.
	 */
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

	/**
	 * Initializes component views and populates active entries from vault.
	 * <p>
	 * <b>Precondition:</b> vault instance variable must be populated.<br>
	 * <b>Postcondition:</b> Container elements are cleared and reloaded with current vault items.
	 * </p>
	 * @throws IOException If UI components fail to load.
	 */
	public void initComponent() throws IOException {
		entryList.getChildren().clear();

		showEntries(vault.getAll());
	}

	public void setVault(MediaVault vault) {
		this.vault = vault;
	}
}
