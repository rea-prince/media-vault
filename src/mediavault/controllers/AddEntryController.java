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

	@FXML
	public void closeView() {
		if (addEntryRoot != null) {
			Pane parent = (Pane) addEntryRoot.getParent();
			if (parent != null) {
				parent.getChildren().remove(addEntryRoot);
			}
		}
	}

	@FXML
	public void onAdd() {
		if (onAdd != null) {
			onAdd.accept(this);
			closeView();
		}
	}

	private void bindRowVisibility(HBox row) {
		if (row != null) {
			row.managedProperty().bind(row.visibleProperty());
		}
	}

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

	public MediaEntry buildEntry() {
		String title = entryTitle.getText().trim();
		String synopsis = entrySynopsis.getText().trim();
		int year = getYear();

		if (title.isEmpty() || synopsis.isEmpty() || year == -1) {
			return null;
		}

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

	public Integer getYear() {
		String text = entryYear.getText().trim();

		if (text.isBlank())
			return -1;

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
