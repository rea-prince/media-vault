package mediavault.models;

import java.util.ArrayList;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

public class MediaEntry
{
    private int ID;
    private int lastModified;

    private MediaType TYPE;
    private Details details = new Details();

    private ArrayList<Genre> genre = new ArrayList<>();

    private Status status;
    private float rating;
    private String review;

    public void rate (float rating)
    {

    }

    public void makeReview (String review)
    {

    }

    public void updateStatus (Status status)
    {

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
