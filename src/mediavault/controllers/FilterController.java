package mediavault.controllers;
import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;
import mediavault.models.MediaVault;
import mediavault.models.MediaEntry;
import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;
import mediavault.tui.Display;
import mediavault.tui.Input;

import java.util.ArrayList;
import java.util.List;
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

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FilterController
{
    @FXML
    private TextField entryYear;

    @FXML
    private ChoiceBox<MediaType> entryType;

    @FXML
    private ChoiceBox<Genre> entryGenre;

    @FXML
    private ChoiceBox<Status> entryStatus;

    private MediaVault vault;

    /**
     * Filters media entries by media type (anime, novel, video game)
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the selected MediaType choice, or null if unmapped.
     * </p>
     * @return void
     */
    public void filterByMediaType(MediaEntry entry)
    {
        entryType.getItems().setAll(MediaType.values());
        entryType.setValue(entry.getMediaType());
    }

    public void filterByYear(MediaEntry entry)
    {
        entryYear.setValue(entry.getDetails().getYear());
    }

    /**
     * Filters media entries by genres
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns a list of selected Genre values, or null if none were chosen.
     * </p>
     * @return void
     */
    public void filterByGenre(MediaEntry entry)
    {
        entryGenre.getItems().setAll(Genre.values());
        entryGenre.setValue(entry.getGenres());
    }

    /**
     * Filters media entries by status
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the selected Status choice, or null if unmapped.
     * </p>
     * @return void
     */
    public void filterByStatus(MediaEntry entry)
    {
        entryStatus.getItems().setAll(Status.values());
        entryStatus.setValue(entry.getStatus());
    }
}
