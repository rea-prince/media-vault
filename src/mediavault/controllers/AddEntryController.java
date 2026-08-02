package mediavault.controllers;

import mediavault.models.MediaEntry;

import java.util.List;

import mediavault.enums.MediaType;
import mediavault.enums.Genre;
import mediavault.enums.Status;
import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;

import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
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

	public Integer getYear() 
    {
		String text = releaseYearField.getText();

		if (text.isBlank())
		    return Integer.valueOf(-1);

		return Integer.parseInt(text);
	}

	public MediaType getEntryMediaType() 
    {
		return entryType.getValue();
	}

	public List<Genre> getEntryGenre() 
    {
		Genre genre = entryGenre.getValue();

		if (genre == null)
			return null;

		return List.of(genre);
	}

	public Status getEntryStatus() 
    {
		return entryStatus.getValue();
	}

    String altTitle = null, studio = null, author = null, publisher = null;

	public Integer getChapters() 
    {
		String text = chaptersField.getText();

		if (text.isBlank())
		    return Integer.valueOf(-1);

		return Integer.parseInt(text);
	}
    
    public void typeSpecDisplay (MediaEntry entry)
    {
        if (entry instanceof Anime anime) 
        {
            altTitle = altTitleField.getText().trim();
            studio = studioField.getText().trim();
        }
        else if (entry instanceof Novel novel) 
        {
            author = authorField.getText().trim();
            publisher = authorField.getText().trim();
            int chapters = getChapters();
        }
        else if (entry instanceof VideoGame videoGame)
        {
            studio = studioField.getText().trim();
            publisher = publisherField.getText().trim();
        }
    }
}
