package mediavault.controllers;

import mediavault.models.Anime;
import mediavault.models.Details;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class EpisodeListController {

	@FXML private HBox episodeList;
	@FXML private VBox episodeContainer;

	private Anime anime;

	public void setAnime(Anime anime) {
		this.anime = anime;
		populateEpisodes();
	}

	private void populateEpisodes() {
		if (anime == null || anime.getAnimeEpisodes() == null) {
			return;
		}

		episodeContainer.getChildren().clear();

		int epCount = 1;

		for (Details epDetails : anime.getAnimeEpisodes()) {
			try {
				FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/fxml/EpisodeCard.fxml")
				);

				Parent episodeCard = loader.load();

				EpisodeCardController controller = loader.getController();
				controller.setDetails(epDetails, epCount++);

				episodeContainer.getChildren().add(episodeCard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	public void openAddEpisodePrompt() {
		try {
			FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fxml/AddEpisodePrompt.fxml")
			);

			Parent promptRoot = loader.load();

			AddEpisodePromptController controller = loader.getController();
			controller.setAnime(anime);
			controller.setOnEpisodeAdded(this::populateEpisodes);

			episodeList.getChildren().add(promptRoot);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void closeView() {
		Pane parent = (Pane) episodeList.getParent();
		if (parent != null) {
			parent.getChildren().remove(episodeList);
		}
	}
}
