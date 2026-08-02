package mediavault.controllers;

import mediavault.models.Anime;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class AddEpisodePromptController {

	@FXML private VBox promptRoot;
	@FXML private TextField titleField;
	@FXML private TextField releaseYearField;
	@FXML private TextArea synopsisArea;

	private Anime anime;
	private Runnable onEpisodeAdded;

	public void setAnime(Anime anime) {
		this.anime = anime;
	}

	public void setOnEpisodeAdded(Runnable callback) {
		this.onEpisodeAdded = callback;
	}

	@FXML
	public void addEpisode() {
		String title = titleField.getText().trim();
		String synopsis = synopsisArea.getText().trim();
		String yearText = releaseYearField.getText().trim();

		if (title.isEmpty() || synopsis.isEmpty() || yearText.isEmpty()) {
			return;
		}

		try {
			int releaseYear = Integer.parseInt(yearText);
			anime.addEpisode(releaseYear, title, synopsis);

			if (onEpisodeAdded != null) {
				onEpisodeAdded.run();
			}

			closePrompt();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void cancel() {
		closePrompt();
	}

	private void closePrompt() {
		Pane parent = (Pane) promptRoot.getParent();
		if (parent != null) {
			parent.getChildren().remove(promptRoot);
		}
	}
}
