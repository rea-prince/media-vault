package mediavault.controllers;
import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;
import mediavault.models.MediaEntry;


import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
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

    @FXML
    private Button applyFilters;


    @FXML
	public void initialize() {
		entryType.getItems().setAll(MediaType.values());
		entryGenre.getItems().setAll(Genre.values());
		entryStatus.getItems().setAll(Status.values());
	}

	public MediaType getSelectedMediaType() {
		return entryType.getValue();
	}

	public Genre getSelectedGenre() {
		return entryGenre.getValue();
	}

	public Status getSelectedStatus() {
		return entryStatus.getValue();
	}

	public Integer getSelectedYear() {
		String text = entryYear.getText();

		if (text.isBlank()) {
		    return null;
		}

		return Integer.parseInt(text);
	}


    // /**
    //  * Filters media entries by media type (anime, novel, video game)
    //  * <p>
    //  * <b>Precondition:</b> None.<br>
    //  * <b>Postcondition:</b> Returns the selected MediaType choice, or null if unmapped.
    //  * </p>
    //  * @return void
    //  */
    // public void filterByMediaType(MediaEntry entry)
    // {
    //     entryType.getItems().setAll(MediaType.values());
    //     entryType.setValue(entry.getMediaType());
    // }

    // public void filterByYear(MediaEntry entry)
    // {
    // 	entryYear.setText(String.valueOf(entry.getDetails().getYear()));
    // }

    // /**
    //  * Filters media entries by genres
    //  * <p>
    //  * <b>Precondition:</b> None.<br>
    //  * <b>Postcondition:</b> Returns a list of selected Genre values, or null if none were chosen.
    //  * </p>
    //  * @return void
    //  */
    // public void filterByGenre(MediaEntry entry)
    // {
    //     entryGenre.getItems().setAll(Genre.values());
    //     entryGenre.setValue(entry.getGenres().get(0));
    // }

    // /**
    //  * Filters media entries by status
    //  * <p>
    //  * <b>Precondition:</b> None.<br>
    //  * <b>Postcondition:</b> Returns the selected Status choice, or null if unmapped.
    //  * </p>
    //  * @return void
    //  */
    // public void filterByStatus(MediaEntry entry)
    // {
    //     entryStatus.getItems().setAll(Status.values());
    //     entryStatus.setValue(entry.getStatus());
    // }
}
