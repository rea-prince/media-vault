package mediavault.models;

import java.util.ArrayList;
import mediavault.enums.*;

public class Novel extends MediaEntry
{
    private final int chapters;
    private final String publisher;
    private final String author;

    /**
     * Constructs a new Novel media entry with its specific attributes.
     * <p>
     * <b>Precondition:</b> genres list must not be null. chapters must be a non-negative integer.<br>
     * <b>Postcondition:</b> A Novel instance is initialized, storing publishing details and its layout structure.
     * </p>
     * @param release   The publication year of the novel.
     * @param title     The main title of the novel.
     * @param synopsis  A short summary or plot description of the novel.
     * @param genres    A list of genres associated with this novel.
     * @param publisher The company responsible for publishing the book.
     * @param author    The author who wrote the novel.
     * @param status    The current tracking status.
     * @param chapters  The total number of chapters contained in the novel.
     */
    public Novel (int release, String title, String synopsis,
                  ArrayList<Genre> genres, String publisher,
                  String author, Status status, int chapters)
    {
        super(MediaType.NOVEL, new Details(release, title, synopsis), genres);

        this.publisher = publisher;
        this.author = author;
        this.chapters = chapters;
    }

    /**
     * Returns the number of chapters the Novel has.
     * <b>Precondition:</b> The Novel is not null.<br>
     * <b>Postcondition:</b> None.
     * @return int The number of chapters in the Novel.
     */
    public int getChapters()
    {
        return chapters;
    }

    /**
     * Returns the publisher of the Novel.
     * <b>Precondition:</b> publisher is not null.<br>
     * <b>Postcondition:</b> None.
     * @return String The name of the publisher of the Novel.
     */
    public String getPublisher()
    {
        return publisher;
    }

    /**
     * Returns the author who wrote the Novel.
     * <b>Precondition:</b> author is not null.<br>
     * <b>Postcondition:</b> None.
     * @return String The name of the author who wrote the novel.
     */
    public String getAuthor()
    {
        return author;
    }
}
