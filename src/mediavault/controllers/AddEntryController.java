package mediavault.controllers;

import mediavault.models.MediaEntry;
import javafx.fxml.FXML;
import mediavault.enums.MediaType;
import mediavault.enums.Genre;
import mediavault.enums.Status;
import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddEntryController 
{
    @FXML private TextField titleField;
	@FXML private TextField releaseYearField;
	@FXML private TextArea synopsisArea;
    @FXML private ChoiceBox<MediaType> entryType;
    @FXML private ChoiceBox<Status> entryStatus;
    @FXML private ChoiceBox<Genre> entryGenre;
    @FXML private TextField altTitleField;
	@FXML private TextField studioField;
    @FXML private TextField authorField;
	@FXML private TextField publisherField;
    @FXML private TextField chaptersField;

    @FXML
	public void initialize() 
    {
		entryType.getItems().setAll(MediaType.values());
		entryStatus.getItems().setAll(Status.values());
		entryGenre.getItems().setAll(Genre.values());
	}

    public void addEntryDetails (MediaEntry entry)
    {
        String title = titleField.getText().trim();
		String yearText = releaseYearField.getText().trim();
		String synopsis = synopsisArea.getText().trim();

		if (title.isEmpty() || synopsis.isEmpty() || yearText.isEmpty())
			return;
    }

    public void typeSpecDisplay (MediaEntry entry)
    {
        if (entry instanceof Anime anime) 
        {
            
        }
        else if (entry instanceof Novel novel) 
        {
            
        }
        else if (entry instanceof VideoGame videoGame)
        {
            
        }
    }
}
