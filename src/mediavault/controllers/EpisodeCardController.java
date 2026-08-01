package mediavault.controllers;

import mediavault.models.Details;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EpisodeCardController {

	@FXML private Label episodeTitle;
	@FXML private Label episodeYear;
	@FXML private Label episodeSynopsis;

	public void setDetails(Details details, int epCount) {
		if (details == null) return;

		episodeTitle.setText(String.format("%d - %s", epCount, details.getTitle()));
		episodeYear.setText(String.format("(%d)", details.getYear()));
		episodeSynopsis.setText(details.getSynopsis());
	}
}
