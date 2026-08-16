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

	/**
	 * Clears and populates the vertical container with card views for each episode in the active anime.
	 * <p>
	 * <b>Precondition:</b> anime instance variable and its episode collection must not be null.<br>
	 * <b>Postcondition:</b> Episode card UI elements are instantiated, populated, and appended to the episode container.
	 * </p>
	 */
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

	/**
	 * Loads and displays the overlay prompt for adding a new episode to the current anime.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> The AddEpisodePrompt view is rendered, bound to the active anime, configured with a refresh callback, and added to the view hierarchy.
	 * </p>
	 */
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

	/**
     * Removes the prompt root node from its parent layout.
     * <p>
     * <b>Precondition:</b> Prompt root must have a valid parent layout.<br>
     * <b>Postcondition:</b> Prompt node is removed from the scene hierarchy.
     * </p>
     */
	@FXML
	public void closeView() {
		Pane parent = (Pane) episodeList.getParent();
		if (parent != null) {
			parent.getChildren().remove(episodeList);
		}
	}
}
