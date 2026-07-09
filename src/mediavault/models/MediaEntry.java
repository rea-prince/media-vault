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


    /* SETTERS */

    public void setLastModified(LocalDateTime now) {
        lastModified = now;
    }

    public void setRating(float rating)
    {
        if (rating > 10.0f) {
            this.rating = 10.0f;
        } else if (rating < 0.0) {
            this.rating = 0.0f;
        } else {
            this.rating = rating;
        }
        lastModified = LocalDateTime.now();
    }

    public void setReview(String review)
    {
        this.review = review;
        lastModified = LocalDateTime.now();
    }

    public void setStatus(Status status)
    {
        this.status = status;
        lastModified = LocalDateTime.now();
    }

    public void setDetails(Details details) {
        this.details = details;
        lastModified = LocalDateTime.now();
    }
    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
        lastModified = LocalDateTime.now();
    }

    /* GETTERS */

    public MediaType getMediaType() {
        return TYPE;
    }

    public Details getDetails()
    {
        return details;
    }

    public ArrayList<Genre> getGenres()
    {
        return genres;
    }

    public Status getStatus()
    {
        return status;
    }

    public float getRating()
    {
        return rating;
    }

    public String getReview()
    {
        return review;
    }
}
