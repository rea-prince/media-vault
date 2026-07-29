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

public class FilterController 
{
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

    public FilterController ()
    {

    }

    /**
     * Filters media entries by media type (anime, novel, video game)
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the selected MediaType choice, or null if unmapped.
     * </p>
     * @return void
     */
    private static MediaType filterByMediaType()
    {
        MediaType media = null;

        switch (Input.getStrInput("Media Type", "A", "N", "V")) {
            case "A": { media = MediaType.ANIME; } break;
            case "N": { media = MediaType.NOVEL; } break;
            case "V": { media = MediaType.VIDEOGAME; } break;
        }

        return media;
    }

    /**
     * Filters media entries by genres
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns a list of selected Genre values, or null if none were chosen.
     * </p>
     * @return void
     */
    private static ArrayList<Genre> filterByGenre()
    {
        ArrayList<Genre> validGenres = new ArrayList<>(List.of(Genre.values()));

        Display.displayGenres();

        ArrayList<Genre> genre = new ArrayList<Genre>();

        for (String choice : Input.getStrInput("Genre").split("[,\\.\\s]+")) {
            Genre genreChoice = Genre.fromId(Integer.parseInt(choice));

            if (validGenres.contains(genreChoice) && genreChoice != Genre.INVALID)
                genre.add(Genre.fromId(Integer.parseInt(choice)));
        }

        if (genre.size() == 0)
            return null;

        return genre;
    }

    /**
     * Filters media entries by status
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the selected Status choice, or null if unmapped.
     * </p>
     * @return void
     */
    private static Status filterByStatus()
    {
        Display.createBoard("Status", List.of(
            "[P] - Planned",
            "[I] - In-progress",
            "[C] - Completed"
        ));

        Status status = null;

        switch (Input.getStrInput("Status", "P", "I", "C")) {
            case "P": { status = Status.PLANNED; } break;
            case "I": { status = Status.IN_PROGRESS; } break;
            case "C": { status = Status.COMPLETED; } break;
        }

        return status;
    }
}
