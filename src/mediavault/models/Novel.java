package mediavault.models;

import java.util.ArrayList;
import mediavault.enums.*;

public class Novel
{
    private String publisher;
    private String author;

    public Novel (int release, String title, String synopsis, ArrayList<Genre> genres,
                  String publisher, String studio, Status status)
    {

    }

    public String getPublisher ()
    {
        return publisher;
    }

    public String getAuthor ()
    {
        return author;
    }
}
