import java.util.ArrayList;

public class MediaEntry 
{
    private int ID;
    private int lastModified;
    MediaType TYPE;
    Details details = new Details();
    ArrayList<Genre> genre = new ArrayList<>();
    Status status;
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
