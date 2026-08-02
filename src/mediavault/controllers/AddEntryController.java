package mediavault.controllers;

import mediavault.models.*;
import mediavault.enums.*;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AddEntryController
{
	@FXML private Pane addEntryRoot;

	@FXML private TextField entryTitle;
	@FXML private TextField entryYear;
	@FXML private TextArea entrySynopsis;
	@FXML private ChoiceBox<String> entryType;
	@FXML private ChoiceBox<String> entryStatus;
	@FXML private ChoiceBox<Genre> entryGenre;

	// type specific
	@FXML private TextField entryAltTitle;
	@FXML private TextField entryStudio;
	@FXML private TextField entryAuthor;
	@FXML private TextField entryPublisher;
	@FXML private TextField entryChapters;

	// containers
	@FXML private HBox altTitleRow;
	@FXML private HBox studioRow;
	@FXML private HBox authorRow;
	@FXML private HBox publisherRow;
	@FXML private HBox chaptersRow;

	private Consumer<AddEntryController> onAdd;

	/**
	 * Initializes the controller by binding layout row visibilities and populating selection dropdowns.
	 * <p>
	 * <b>Precondition:</b> FXML control fields must be properly injected.<br>
	 * <b>Postcondition:</b> Row visibility bindings are established, dropdown options for type, status, and genre are populated, and default selection handlers are attached.
	 * </p>
	 */
	@FXML
	public void initialize()
	{
		bindRowVisibility(altTitleRow);
		bindRowVisibility(studioRow);
		bindRowVisibility(authorRow);
		bindRowVisibility(publisherRow);
		bindRowVisibility(chaptersRow);

		entryType.getItems().setAll(
			Stream.of(MediaType.values()).map(MediaType::getName).toList()
		);
		entryStatus.getItems().setAll(
			Stream.of(Status.values()).map(Status::getName).toList()
		);

		for (Genre genre : Genre.values()) {
			if (genre != Genre.INVALID) {
				entryGenre.getItems().add(genre);
			}
		}

		entryType.setOnAction(e -> updateFieldVisibility(getEntryMediaType()));

		// default selection
		if (!entryType.getItems().isEmpty()) {
			entryType.setValue(entryType.getItems().get(0));
		}
		if (!entryStatus.getItems().isEmpty()) {
			entryStatus.setValue(entryStatus.getItems().get(0));
		}
	}

	/**
	 * Removes the entry creation prompt layout from its parent container.
	 * <p>
	 * <b>Precondition:</b> addEntryRoot must be attached to a valid parent layout.<br>
	 * <b>Postcondition:</b> The entry creation UI prompt is detached from the scene hierarchy.
	 * </p>
	 */
	@FXML
	public void closeView() {
		if (addEntryRoot != null) {
			Pane parent = (Pane) addEntryRoot.getParent();
			if (parent != null) {
				parent.getChildren().remove(addEntryRoot);
			}
		}
	}

	/**
	 * Invokes the entry addition callback and closes the creation prompt.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> If registered, onAdd consumer is executed with this controller instance, and the overlay view is closed.
	 * </p>
	 */
	@FXML
	public void onAdd() {
		if (onAdd != null) {
			onAdd.accept(this);
			closeView();
		}
	}

	/**
	 * Binds the managed property of an HBox container to its visible property.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> If row is non-null, its managed property is bound to reflect its visibility, toggling layout recalculations automatically.
	 * </p>
	 * @param row The HBox layout row to configure.
	 */
	private void bindRowVisibility(HBox row) {
		if (row != null) {
			row.managedProperty().bind(row.visibleProperty());
		}
	}

	/**
	 * Toggles the visibility of type-specific form fields according to the selected media type.
	 * <p>
	 * <b>Precondition:</b> Form row containers must be injected.<br>
	 * <b>Postcondition:</b> Input rows for alternative titles, studio, author, publisher, and chapter count are shown or hidden based on media type requirements.
	 * </p>
	 * @param type The selected MediaType determining field visibility.
	 */
	private void updateFieldVisibility(MediaType type) {
		if (type == null)
			return;

		boolean isAnime = type == MediaType.ANIME;
		boolean isNovel = type == MediaType.NOVEL;
		boolean isGame = type == MediaType.VIDEOGAME;

		if (altTitleRow != null)
			altTitleRow.setVisible(isAnime);
		if (studioRow != null)
			studioRow.setVisible(isAnime || isGame);
		if (authorRow != null)
			authorRow.setVisible(isNovel);
		if (publisherRow != null)
			publisherRow.setVisible(isNovel || isGame);
		if (chaptersRow != null)
			chaptersRow.setVisible(isNovel);
	}

	public void setOnAdd(Consumer<AddEntryController> onAdd) {
		this.onAdd = onAdd;
	}

	/**
	 * Instantiates a specific MediaEntry subclass based on current form field inputs.
	 * <p>
	 * <b>Precondition:</b> Input fields should contain valid user selections.<br>
	 * <b>Postcondition:</b> Returns a concrete Anime, Novel, or VideoGame object with populated metadata, or null if the media type is unrecognized.
	 * </p>
	 * @return A constructed MediaEntry instance corresponding to form values, or null if unmapped.
	 */
	public MediaEntry buildEntry() {
		String title = entryTitle.getText().trim();
		if (title.isEmpty())
			title = "Untitled";

		String synopsis = entrySynopsis.getText().trim();
		if (synopsis.isEmpty())
			synopsis = "Synopsis unavailable.";

		int year = getYear();

		List<Genre> genreList = getEntryGenre();
		ArrayList<Genre> genres = genreList != null ? new ArrayList<>(genreList) : new ArrayList<>();
		Status status = getEntryStatus();
		MediaType type = getEntryMediaType();

		if (type == MediaType.ANIME) {
			String altTitle = entryAltTitle.getText().trim();
			String studio = entryStudio.getText().trim();
			return new Anime(year, title, synopsis, genres, altTitle, studio, status);
		}
		else if (type == MediaType.NOVEL) {
			String author = entryAuthor.getText().trim();
			String publisher = entryPublisher.getText().trim();
			int chapters = getChapters();
			return new Novel(year, title, synopsis, genres, publisher, author, status, chapters);
		}
		else if (type == MediaType.VIDEOGAME) {
			String publisher = entryPublisher.getText().trim();
			String studio = entryStudio.getText().trim();
			return new VideoGame(year, title, synopsis, genres, publisher, studio, status);
		}

		return null;
	}

	/**
	 * Parses and returns the release year entered in the form field.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Returns the parsed release year, default year 2026 if blank, or -1 if input format is invalid.
	 * </p>
	 * @return Integer representing release year, 2026 if blank, or -1 on parse failure.
	 */
	public Integer getYear() {
		String text = entryYear.getText().trim();

		if (text.isBlank())
			return 2026;

		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public MediaType getEntryMediaType() {
		return MediaType.fromString(entryType.getValue());
	}

	public List<Genre> getEntryGenre() {
		Genre genre = entryGenre.getValue();

		if (genre == null)
			return null;

		return List.of(genre);
	}

	public Status getEntryStatus() {
		return Status.fromString(entryStatus.getValue());
	}

	/**
	 * Parses and returns the chapter count entered in the form field.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Returns the parsed chapter integer, or default value 1 if input is blank or invalid.
	 * </p>
	 * @return Integer representing total chapters, defaulting to 1 on blank or parse error.
	 */
	public Integer getChapters() {
		String text = entryChapters.getText().trim();

		if (text.isBlank())
			return 1;

		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return 1;
		}
	}
}
