package mediavault.controllers;

import mediavault.models.MediaEntry;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DeleteEntryController {

    @FXML private VBox deleteRoot;

    private MediaEntry entryToDelete;
    private Consumer<MediaEntry> onDeleteConfirmed;

    public void setTargetEntry(MediaEntry entry, Consumer<MediaEntry> onDeleteConfirmed) {
        this.entryToDelete = entry;
        this.onDeleteConfirmed = onDeleteConfirmed;
    }

    @FXML
    public void deleteEntry() {
        if (onDeleteConfirmed != null && entryToDelete != null) {
            onDeleteConfirmed.accept(entryToDelete);
        }
        closeView();
    }

    @FXML
    public void closeView() {
        if (deleteRoot != null) {
            Pane parent = (Pane) deleteRoot.getParent();
            if (parent != null) {
                parent.getChildren().remove(deleteRoot);
            }
        }
    }
}
