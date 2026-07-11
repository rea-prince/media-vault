package mediavault.models;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

abstract public class MediaEntry implements Serializable
{
    private LocalDateTime lastModified;

    private MediaType TYPE;
    private Details details;

    private ArrayList<Genre> genres;

    private Status status;
    private float rating;
    private String review;


    /**
     * Base constructor for initializing core fields common across all media types.
     * <p>
     * <b>Precondition:</b> type and details must not be null.<br>
     * <b>Postcondition:</b> A MediaEntry subclass instance is safely configured.
     * </p>
     * @param type    The specific classification category from the MediaType enum.
     * @param details The primary title, year, and synopsis details wrapper.
     * @param genres  The initial array list of categories describing the entry.
     * @param status  The current tracking status.
     */
    protected MediaEntry(MediaType type, Details details, ArrayList<Genre> genres, Status status)
    {
        this.TYPE = type;
        this.details = details;
        this.genres = genres;
        this.status = status;

        this.rating = -1f;
        this.review = null;
    }


    /* SETTERS */

    /**
     * Updates the last time the entry was modified.
     * <p>
     * <b>Precondition:</b> now is not null.<br>
     * <b>Postcondition:</b> The entry's lastModified timestamp is set to now.
     * </p>
     * @param now The LocalDateTime to update lastModified to.
     * @return void
     */
    public void setLastModified(LocalDateTime now)
    {
        lastModified = now;
    }

    /**
     * Updates the user rating for the media entry and clamps the values between 0.0 and 10.0.
     * <p>
     * <b>Precondition:</b> None (out-of-bounds metrics are safely adjusted).<br>
     * <b>Postcondition:</b> The entry's rating is altered and the lastModified timestamp is refreshed to the current date and time.
     * </p>
     * @param rating The numerical score evaluated by the user.
     * @return void
     */
    public void setRating(float rating)
    {
        if (status == Status.COMPLETED) {
            if (rating > 10.0f) {
                this.rating = 10.0f;
            } else if (rating < 0.0) {
                this.rating = 0.0f;
            } else {
                this.rating = rating;
            }
        }
        lastModified = LocalDateTime.now();
    }


    /**
     * Updates the user's review of the MediaEntry.
     * <p>
     * <b>Precondition:</b> review is not null.<br>
     * <b>Postcondition:</b> The entry's review is altered and the lastModified timestamp is refreshed to the current date and time.
     * </p>
     * @param review The written review of the user.
     * @return void
     */
    public void setReview(String review)
    {
        if (status == Status.COMPLETED) {
            this.review = review;
        }
        lastModified = LocalDateTime.now();
    }

    /**
     * Updates the status of the MediaEntry.
     * <p>
     * <b>Precondition:</b> status is not null.<br>
     * <b>Postcondition:</b> The entry's status is altered and the lastModified timestamp is refreshed to the current date and time.
     * </p>
     * @param status The new status of the MediaEntry.
     * @return void
     */
    public void setStatus(Status status)
    {
        this.status = status;
        lastModified = LocalDateTime.now();
    }

    /**
     * Updates the user rating for the media entry and clamps the values between 0.0 and 10.0.
     * <p>
     * <b>Precondition:</b> details is not null.<br>
     * <b>Postcondition:</b> The entry's details are altered and the lastModified timestamp is refreshed to the current date and time.
     * </p>
     * @param details The updated details of the entry.
     * @return void
     */
    public void setDetails(Details details) {
        this.details = details;
        lastModified = LocalDateTime.now();
    }

    /**
     * Sets the list of genres related to the MediaEntry.
     * <b>Precondition:</b> genres is not null.<br>
     * <b>Postcondition:</b> The entry's genres are altered and the lastModified timestamp is refreshed to the current date and time.
     * @param genres The list of genres to set the MediaEntry's genres to.
     * @return void
     */
    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
        lastModified = LocalDateTime.now();
    }

    /* GETTERS */

    /**
     * Returns the MediaType Enum of the MediaEntry.
     * <b>Precondition:</b> TYPE is not null.<br>
     * <b>Postcondition:</b> None.
     * @return MediaType The MediaType Enum assigned to the MediaEntry.
     */
    public MediaType getMediaType() {
        return TYPE;
    }

    /**
     * Returns the Details of the MediaEntry.
     * <b>Precondition:</b> details is not null.<br>
     * <b>Postcondition:</b> None.
     * @return Details The Details of the MediaEntry.
     */
    public Details getDetails()
    {
        return details;
    }

    /**
     * Returns the list of Genres associated with the MediaEntry.
     * <b>Precondition:</b> genres is not null.<br>
     * <b>Postcondition:</b> None.
     * @return ArrayList<Genre> The Genres associated with the MediaEntry.
     */
    public ArrayList<Genre> getGenres()
    {
        return genres;
    }

    /**
     * Returns the MediaEntry's current Status.
     * <b>Precondition:</b> status is not null.<br>
     * <b>Postcondition:</b> None.
     * @return Status The current Status of the MediaEntry.
     */
    public Status getStatus()
    {
        return status;
    }

    /**
     * Returns the list of Genres associated with the MediaEntry.
     * <b>Precondition:</b> rating has been assigned, and the entry has been completed.<br>
     * <b>Postcondition:</b> None.
     * @return float The current rating of the MediaEntry.
     */
    public float getRating()
    {
        if (status == Status.COMPLETED)
            return rating;
        return -1f;
    }

    /**
     * Returns the user's review of the MediaEntry.
     * <b>Precondition:</b> review is not null, and the entry has been completed.<br>
     * <b>Postcondition:</b> None.
     * @return String The user's review of the MediaEntry.
     */
    public String getReview()
    {
        if (status == Status.COMPLETED)
            return review;
        return null;
    }
}
