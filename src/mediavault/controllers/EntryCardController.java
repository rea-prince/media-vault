package mediavault.controllers;
import mediavault.enums.Status;
import mediavault.models.MediaEntry;
import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class EntryCardController {

    @FXML private Label entryTitle;
    @FXML private Label entryType;
    @FXML private Label entryDetails;
    @FXML private Label entrySynopsis;
    @FXML private ChoiceBox<Status> entryStatus;
    @FXML private Button reviewButton;
    @FXML private Button viewEpisodesButton;

    public void setEntry(MediaEntry entry) {

    	// set details

        entryTitle.setText(entry.getDetails().getTitle());
        entrySynopsis.setText(entry.getDetails().getSynopsis());

        // set status

        entryStatus.getItems().setAll(Status.values());
        entryStatus.setValue(entry.getStatus());

        // attach event

        entryStatus.setOnAction(e ->
            entry.setStatus(entryStatus.getValue())
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
