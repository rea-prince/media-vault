package mediavault.controllers;
import mediavault.models.*;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import java.nio.file.Files;
import java.io.File;
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

	private File currentSaveFile;

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


	/* FILE STUFF FILE STUFF */

	/**
	 * Creates a new empty vault instance and updates the displayed view.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> The controller's vault reference is replaced with a new empty MediaVault instance and the list view is updated.
	 * </p>
	 * @param e The ActionEvent triggered by selecting New.
	 * @throws IOException If rendering entry cards fails.
	 */
	@FXML
	public void handleNew(ActionEvent e) throws IOException {
		this.vault = new MediaVault();
		this.currentSaveFile = null;
		showEntries(vault.getAll());
	}

	/**
	 * Opens a FileChooser dialog to load media entries from a CSV text file into the vault.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Reads CSV lines, populates a new MediaVault via MediaVault.fromCSV(), and refreshes the list view.
	 * </p>
	 * @param e The ActionEvent triggered by selecting Open.
	 */
	@FXML
	public void handleOpen(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Vault File");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"),
			new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"),
			new FileChooser.ExtensionFilter("All Files", "*.*")
		);
		File file = fileChooser.showOpenDialog(stackView.getScene().getWindow());

		if (file != null) {
			try {
				List<String> lines = Files.readAllLines(file.toPath());
				this.vault = MediaVault.fromCSV(lines);
				this.currentSaveFile = file;
				showEntries(vault.getAll());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Saves the active vault entries in CSV format to the current save file, or prompts Save As if no path is set.
	 * <p>
	 * <b>Precondition:</b> vault must not be null.<br>
	 * <b>Postcondition:</b> The MediaVault contents are exported to CSV and written to disk.
	 * </p>
	 * @param e The ActionEvent triggered by selecting Save.
	 */
	@FXML
	public void handleSave(ActionEvent e) {
		if (currentSaveFile != null) {
			saveVaultToFile(currentSaveFile);
		} else {
			handleSaveAs(e);
		}
	}

	/**
	 * Opens a FileChooser dialog prompting the user to specify a location, then exports vault data to CSV.
	 * <p>
	 * <b>Precondition:</b> vault must not be null.<br>
	 * <b>Postcondition:</b> Exports vault contents to CSV string and writes to the selected file path.
	 * </p>
	 * @param e The ActionEvent triggered by selecting Save As.
	 */
	@FXML
	public void handleSaveAs(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Vault As");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"),
			new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt")
		);
		File file = fileChooser.showSaveDialog(stackView.getScene().getWindow());

		if (file != null) {
			saveVaultToFile(file);
			this.currentSaveFile = file;
		}
	}

	/**
	 * Helper method that converts vault data to a CSV formatted string and writes it to a file.
	 * <p>
	 * <b>Precondition:</b> file parameter and vault instance variable must not be null.<br>
	 * <b>Postcondition:</b> Writes the CSV formatted string generated by vault.toCSV() to the specified file.
	 * </p>
	 * @param file The destination file object.
	 */
	private void saveVaultToFile(File file) {
		if (vault == null) return;

		try {
			String csvData = vault.toCSV();
			Files.writeString(file.toPath(), csvData);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Clears the entry list layout without terminating the application.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Removes all child nodes from the main entry container.
	 * </p>
	 * @param e The ActionEvent triggered by selecting Close.
	 */
	@FXML
	public void handleClose(ActionEvent e) {
		entryList.getChildren().clear();
	}

	/**
	 * Exits the JavaFX application process.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Shuts down the JavaFX platform.
	 * </p>
	 * @param e The ActionEvent triggered by selecting Quit.
	 */
	@FXML
	public void handleQuit(ActionEvent e) {
		Platform.exit();
	}
}
