package mediavault.models;

import java.util.ArrayList;
import mediavault.enums.*;

public class VideoGame extends MediaEntry
{
    private final String publisher;
    private final String studio;

    /**
     * Constructs a new VideoGame media entry with its specific production attributes.
     * <p>
     * <b>Precondition:</b> genres list must not be null. release year should be valid.<br>
     * <b>Postcondition:</b> A VideoGame instance is initialized and cataloged under the VIDEOGAME media type.
     * </p>
     * @param release   The release year of the video game.
     * @param title     The main title of the video game.
     * @param synopsis  A short description or plot summary of the video game.
     * @param genres    A list of genres associated with this game.
     * @param publisher The company that published the video game.
     * @param studio    The development studio that created the video game.
     * @param status    The current tracking status.
     */
    public VideoGame (int release, String title, String synopsis,
                      ArrayList<Genre> genres, String publisher,
                      String studio, Status status)
    {
        super(MediaType.VIDEOGAME, new Details(release, title, synopsis), genres);

        this.publisher = publisher;
        this.studio = studio;
    }

    /**
     * Returns the publisher of the VideoGame.
     * <b>Precondition:</b> publisher is not null.<br>
     * <b>Postcondition:</b> None.
     * @return String The name of the publisher of the VideoGame.
     */
    public String getPublisher ()
    {
        return publisher;
    }

    /**
     * Returns the studio currently assigned to the VideoGame.
     * <b>Precondition:</b> studio is not null.<br>
     * <b>Postcondition:</b> None.
     * @return String The studio currently assigend to the VideoGame.
     */
    public String getStudio ()
    {
        return studio;
    }
}
