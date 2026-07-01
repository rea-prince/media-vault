package mediavault.models;

import java.util.ArrayList;
import java.time.LocalDateTime;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

abstract public class MediaEntry
{
    private LocalDateTime lastModified;

    private MediaType TYPE;
    private Details details = new Details();

    private ArrayList<Genre> genre = new ArrayList<>();

    private Status status;
    private float rating;
    private String review;

    public void rate (float rating)
    {
        this.rating = rating;
        lastModified = LocalDateTime.now();
    }

    public void makeReview (String review)
    {
        this.review = review;
        lastModified = LocalDateTime.now();
    }

    public void updateStatus (Status status)
    {
        this.status = status;
        lastModified = LocalDateTime.now();
    }

    public MediaType getMediaType() {
        return TYPE;
    }

    public Details getDetails ()
    {
        return details;
    }

    public ArrayList<Genre> getGenres ()
    {
        return genre;
    }

    public Status getStatus ()
    {
        return status;
    }

    public float getRating ()
    {
        return rating;
    }

    public String getReview ()
    {
        return review;
    }
}
