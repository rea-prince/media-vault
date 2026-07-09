package mediavault.models;

import java.util.ArrayList;
import mediavault.enums.*;

public class VideoGame extends MediaEntry
{
    private final String publisher;
    private final String studio;

    public VideoGame (int release, String title, String synopsis,
                      ArrayList<Genre> genres, String publisher,
                      String studio, Status status)
    {
        super(MediaType.VIDEOGAME, new Details(release, title, synopsis), genres);

        this.publisher = publisher;
        this.studio = studio;
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
