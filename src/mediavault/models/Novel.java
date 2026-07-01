package mediavault.models;

import java.util.ArrayList;
import mediavault.enums.*;

public class Novel extends MediaEntry
{
    private final int chapters;
    private final String publisher;
    private final String author;

    public Novel (int release, String title, String synopsis,
                  ArrayList<Genre> genres, String publisher,
                  String author, Status status, int chapters)
    {
        setDetails(new Details(release, title, synopsis));
        setGenres(genres);
        setStatus(status);

        this.publisher = publisher;
        this.author = author;
        this.chapters = chapters;
    }

    public int getChapters()
    {
        return chapters;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public String getAuthor()
    {
        return author;
    }
}
