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

	@FXML
	public void closePrompt() {
		Pane parent = (Pane) reviewRoot.getParent();
		if (parent != null) {
			parent.getChildren().remove(reviewRoot);
		}
	}
}
