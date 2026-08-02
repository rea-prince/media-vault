package mediavault.controllers;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;
import mediavault.models.MediaEntry;
import mediavault.models.MediaVault;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SummaryController {

	@FXML private HBox vaultSummary;
	@FXML private Label totalEntriesLabel;
	@FXML private Label averageRatingLabel;
	@FXML private VBox mediaTypeContainer;
	@FXML private VBox statusContainer;
	@FXML private VBox genreContainer;

	public void setVault(MediaVault vault) {
		if (vault == null) {
			return;
		}

		totalEntriesLabel.setText(String.format("Total entries: %d", vault.getTotal()));

		mediaTypeContainer.getChildren().clear();
		for (MediaType type : MediaType.values()) {
			long count = vault.getTotalByAttributes(type, null, null);
			mediaTypeContainer.getChildren().add(createSummaryRow(type.getName(), String.valueOf(count)));
		}

		statusContainer.getChildren().clear();
		for (Status status : Status.values()) {
			long count = vault.getTotalByAttributes(null, status, null);
			statusContainer.getChildren().add(createSummaryRow(status.getName(), String.valueOf(count)));
		}

		genreContainer.getChildren().clear();
		for (Genre genre : Genre.values()) {
			if (genre == Genre.INVALID) {
				continue;
			}

			ArrayList<Genre> genreFilter = new ArrayList<>(List.of(genre));
			long count = vault.getTotalByAttributes(null, null, genreFilter);
			if (count > 0) {
				genreContainer.getChildren().add(createSummaryRow(genre.getName(), String.valueOf(count)));
			}
		}

		float totalRating = 0.0f;
		int withRatingCount = 0;
		List<MediaEntry> completedEntries = vault.getEntries(null, 0, null, Status.COMPLETED, null);

		if (completedEntries != null) {
			for (MediaEntry entry : completedEntries) {
				if (entry.getRating() >= 0.0f) {
					totalRating += entry.getRating();
					withRatingCount++;
				}
			}
		}

		float averageRating = withRatingCount > 0 ? totalRating / withRatingCount : 0.0f;
		averageRatingLabel.setText(String.format("Average rating: %.2f", averageRating));
	}

	// TO DO: Make this a component to avoid hard coding

	private HBox createSummaryRow(String key, String value) {
		HBox row = new HBox();
		row.setPrefWidth(200);

		Label keyLabel = new Label(key);
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		Label valueLabel = new Label(value);

		row.getChildren().addAll(keyLabel, spacer, valueLabel);
		return row;
	}

	@FXML
	public void closeView() {
		Pane parent = (Pane) vaultSummary.getParent();
		if (parent != null) {
			parent.getChildren().remove(vaultSummary);
		}
	}
}
