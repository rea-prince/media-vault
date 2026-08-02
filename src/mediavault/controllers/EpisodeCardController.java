package mediavault.controllers;

import mediavault.models.Details;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EpisodeCardController {

	@FXML private Label episodeTitle;
	@FXML private Label episodeYear;
	@FXML private Label episodeSynopsis;

	/**
	 * Binds episode details and its 1-based sequence number to the card UI labels.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> If details is non-null, UI labels are populated with formatted title, release year, and synopsis text.
	 * </p>
	 * @param details  The metadata model containing title, release year, and synopsis for the episode.
	 * @param epCount  The sequential episode number.
	 */
	public void setDetails(Details details, int epCount) {
		if (details == null) return;

		episodeTitle.setText(String.format("%d - %s", epCount, details.getTitle()));
		episodeYear.setText(String.format("(%d)", details.getYear()));
		episodeSynopsis.setText(details.getSynopsis());
	}
}
