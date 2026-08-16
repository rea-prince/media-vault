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

    /**
     * Executes the deletion callback for the target media entry and closes the prompt.
     * <p>
     * <b>Precondition:</b> targetEntry and onDeleteConfirmed consumer must be set.<br>
     * <b>Postcondition:</b> The entry deletion callback is triggered and the view is detached from its parent layout.
     * </p>
     */
    @FXML
    public void deleteEntry() {
        if (onDeleteConfirmed != null && entryToDelete != null) {
            onDeleteConfirmed.accept(entryToDelete);
        }
        closeView();
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
        if (deleteRoot != null) {
            Pane parent = (Pane) deleteRoot.getParent();
            if (parent != null) {
                parent.getChildren().remove(deleteRoot);
            }
        }
    }
}
