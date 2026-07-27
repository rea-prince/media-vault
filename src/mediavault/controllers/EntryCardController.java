package mediavault.controllers;
import mediavault.enums.Status;
import mediavault.models.MediaVault;
import mediavault.models.MediaEntry;
import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;

import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class EntryCardController {

    @FXML
    private Label entryTitle;

    @FXML
    private Label entryType;

    @FXML
    private Label entryDetails;

    @FXML
    private Label entrySynopsis;

    @FXML
    private ChoiceBox<Status> entryStatus;

    public void setEntry(MediaEntry entry) {

        entryTitle.setText(entry.getDetails().getTitle());
        entrySynopsis.setText(entry.getDetails().getSynopsis());

        entryStatus.getItems().setAll(Status.values());
        entryStatus.setValue(entry.getStatus());

        entryStatus.setOnAction(e ->
            entry.setStatus(entryStatus.getValue())
        );

        // Display type-specific information
        if (entry instanceof Novel novel) {

            entryType.setText("Novel");
            entryDetails.setText(novel.getAuthor());

        } else if (entry instanceof VideoGame videoGame) {

            entryType.setText("Video Game");
            entryDetails.setText(videoGame.getStudio());

        } else if (entry instanceof Anime anime) {

            entryType.setText("Anime");
            entryDetails.setText( anime.getStudio()
                // show.getCurrentEpisode() + "/" + show.getEpisodeCount()
            );

        } else {

            entryType.setText(entry.getClass().getSimpleName());
            entryDetails.setText("");
        }
    }
}
