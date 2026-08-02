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

	/**
	 * Populates the selection choices for media type, genre, and status drop-down controls.
	 * <p>
	 * <b>Precondition:</b> Control instances must be injected via FXML.<br>
	 * <b>Postcondition:</b> Selection lists for MediaType, Genre, and Status controls are initialized with enum values.
	 * </p>
	 */
    @FXML
	public void initialize() {
		entryType.getItems().setAll(MediaType.values());
		entryGenre.getItems().setAll(Genre.values());
		entryStatus.getItems().setAll(Status.values());
	}

	/**
	 * Resets all filter dropdown choices to their initial state.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> All dropdown filter menus are re-initialized.
	 * </p>
	 * @param e The ActionEvent triggered by clicking the reset button.
	 */
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

	/**
	 * Retrieves the user-selected genre as a single-element list.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Returns a List containing the chosen Genre, or null if no selection was made.
	 * </p>
	 * @return A immutable List containing the selected Genre, or null if none selected.
	 */
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


	/**
	 * Parses and retrieves the year integer entered in the text field.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Returns the parsed year integer, or -1 if the field is empty or blank.
	 * </p>
	 * @return Integer representing the input year, or -1 if blank.
	 * @throws NumberFormatException If the input string cannot be parsed into an integer.
	 */
	public Integer getSelectedYear() {
		String text = entryYear.getText();

		if (text.isBlank()) {
		    return Integer.valueOf(-1);
		}

		return Integer.parseInt(text);
	}

}
