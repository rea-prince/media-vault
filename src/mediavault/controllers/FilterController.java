package mediavault.controllers;
import mediavault.enums.*;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class FilterController
{
    @FXML private TextField entryYear;
    @FXML private ChoiceBox<MediaType> entryType;
    @FXML private ChoiceBox<Genre> entryGenre;
    @FXML private ChoiceBox<Status> entryStatus;
    @FXML private Button resetFilters;
    @FXML private Button applyFilters;
    @FXML private Button cancelFilters;


    // behavior stuff
    private Consumer<FilterController> onApply;
    private Runnable onCancel;

    public void setOnCancel(Runnable onCancel) {
		this.onCancel = onCancel;
	}

	public void setOnApply(Consumer<FilterController> onApply) {
		this.onApply = onApply;
	}

    @FXML
	public void initialize() {
		entryType.getItems().setAll(MediaType.values());
		entryGenre.getItems().setAll(Genre.values());
		entryStatus.getItems().setAll(Status.values());
	}

	@FXML
	public void resetFilters(ActionEvent e) {
		initialize();
	}

	@FXML
	private void apply() {
		onApply.accept(this);
	}
	@FXML
	private void cancel() {
		onCancel.run();
	}

	public MediaType getSelectedMediaType() {
		return entryType.getValue();
	}

	public List<Genre> getSelectedGenre() {
		Genre genre = entryGenre.getValue();

		if (genre == null) {
			return null;
		}

		return List.of(genre);
	}

	public Status getSelectedStatus() {
		return entryStatus.getValue();
	}

	public Integer getSelectedYear() {
		String text = entryYear.getText();

		if (text.isBlank()) {
		    return Integer.valueOf(-1);
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
