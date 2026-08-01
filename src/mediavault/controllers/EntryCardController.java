package mediavault.controllers;
import mediavault.enums.Status;
import mediavault.models.MediaEntry;
import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;


import java.util.stream.*;
import java.util.List;
import java.io.IOException;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.Parent;

public class EntryCardController {

	@FXML private Label entryTitle;
	@FXML private Label entryType;
	@FXML private Label entryDetails;
	@FXML private Label entrySynopsis;
	@FXML private ChoiceBox<String> entryStatus;
	@FXML private Button reviewButton;
	@FXML private Button viewEpisodesButton;
	@FXML private VBox entryBox;

	private MediaEntry entry;

	@FXML
	public void openReview() throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/Review.fxml")
		);

		Parent reviewRoot = loader.load();

		ReviewController controller = loader.getController();
		controller.setEntry(entry);

		Pane popupPane = (Pane) reviewButton.getScene().lookup("#popupPane");
		if (popupPane != null) {
			popupPane.getChildren().setAll(reviewRoot);
		}
	}

	@FXML
	public void viewEpisodes() throws IOException {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/fxml/EpisodeList.fxml")
		);

		Parent listRoot = loader.load();

		EpisodeListController controller = loader.getController();
		if (entry instanceof Anime anime) {
			controller.setAnime(anime);
		}

		Pane popupPane = (Pane) viewEpisodesButton.getScene().lookup("#popupPane");
		if (popupPane != null) {
			popupPane.getChildren().setAll(listRoot);
		}
	}


	public void setEntry(MediaEntry entry) {
		this.entry = entry;
	}

	public void setEntryView() {

		// set details

		entryTitle.setText(entry.getDetails().getTitle());
		entrySynopsis.setText(entry.getDetails().getSynopsis());

		// set status

		entryStatus.getItems().setAll(
		    Stream.of(Status.values()).map(Status::getName).toList()
		);
		entryStatus.setValue(entry.getStatus().getName());

		// attach event

		entryStatus.setOnAction(e ->
			entry.setStatus(Status.fromString(entryStatus.getValue()))
		);

		// optional stuff

		if (!(entry instanceof Anime)) {
			viewEpisodesButton.setVisible(false);
		}
		if (entry.getStatus() != Status.COMPLETED) {
			reviewButton.setVisible(false);
		}

		// type specific information

		if (entry instanceof Novel novel) {

			entryType.setText("Novel");
			entryDetails.setText(novel.getAuthor());

		} else if (entry instanceof VideoGame videoGame) {

			entryType.setText("Video Game");
			entryDetails.setText(videoGame.getStudio());

		} else if (entry instanceof Anime anime) {

			entryType.setText("Anime");
			entryDetails.setText(anime.getStudio());
		} else {

			entryType.setText(entry.getClass().getSimpleName());
			entryDetails.setText("");
		}
	}
}
