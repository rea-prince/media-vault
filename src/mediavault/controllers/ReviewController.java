package mediavault.controllers;

import mediavault.models.MediaEntry;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class ReviewController {

	@FXML private VBox reviewRoot;
	@FXML private TextArea reviewTextArea;
	@FXML private ChoiceBox<Float> ratingSelection;

	private MediaEntry entry;

	/**
	 * Configures the view with a media entry, initializing rating selection values and review text.
	 * <p>
	 * <b>Precondition:</b> entry parameter must not be null.<br>
	 * <b>Postcondition:</b> The rating drop-down choices (0.0 to 10.0 in increments of 0.5) and text area are populated from the entry data.
	 * </p>
	 * @param entry The MediaEntry instance to be reviewed.
	 */
	public void setEntry(MediaEntry entry) {
		this.entry = entry;

		// populate rating

		ratingSelection.getItems().clear();
		for (float r = 0.0f; r <= 10.0f; r += 0.5f) {
			ratingSelection.getItems().add(r);
		}

		if (entry.getRating() >= 0) {
			ratingSelection.setValue(entry.getRating());
		} else {
			ratingSelection.setValue(10.0f); // Default score
		}

		if (entry.getReview() != null) {
			reviewTextArea.setText(entry.getReview());
		}
	}

	/**
	 * Updates the media entry with the user-selected rating score and review text, then closes the prompt.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> The entry's rating and review fields are saved if non-empty, and the popup is removed.
	 * </p>
	 */
	@FXML
	public void submitReview() {
		if (entry != null) {
			Float selectedRating = ratingSelection.getValue();
			if (selectedRating != null) {
				entry.setRating(selectedRating);
			}

			String reviewText = reviewTextArea.getText().trim();
			if (!reviewText.isEmpty()) {
				entry.setReview(reviewText);
			}
		}
		closePrompt();
	}

	/**
     * Removes the prompt root node from its parent layout.
     * <p>
     * <b>Precondition:</b> Prompt root must have a valid parent layout.<br>
     * <b>Postcondition:</b> Prompt node is removed from the scene hierarchy.
     * </p>
     */
	@FXML
	public void closePrompt() {
		Pane parent = (Pane) reviewRoot.getParent();
		if (parent != null) {
			parent.getChildren().remove(reviewRoot);
		}
	}
}
