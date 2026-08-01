package mediavault.controllers;

import mediavault.models.MediaEntry;
import javafx.fxml.FXML;
import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class AddEntryController 
{
    @FXML private Label entryType;
    @FXML private Label entryDetails;

    public void typeSpecDisplay (MediaEntry entry)
    {
        if (entry instanceof Anime anime) 
        {
            entryType.setText("Anime");
            entryDetails.setText(anime.getStudio());
        }
        else if (entry instanceof Novel novel) 
        {
            entryType.setText("Novel");
            entryDetails.setText(novel.getAuthor());

        }
        else if (entry instanceof VideoGame videoGame)
        {
            entryType.setText("Video Game");
            entryDetails.setText(videoGame.getStudio());

        }
    }
}
