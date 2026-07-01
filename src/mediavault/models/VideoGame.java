package mediavault.models;

import java.util.ArrayList;
import mediavault.enums.*;

public class VideoGame
{
    private String publisher;
    private String studio;

    public VideoGame (int release, String title, String synopsis, ArrayList<Genre> genres,
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
