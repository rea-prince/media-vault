package mediavault.models;
import mediavault.enums.Status;

public class VideoGame
{
    private String publisher;
    private String studio;

    public VideoGame (int release, String title, String synopsis, Genre[] genre,
                      String publisher, String studio, Status status)
    {

    }

    public String getPublisher ()
    {
        return publisher;
    }

    public String getStudio ()
    {
        return studio;
    }
}
