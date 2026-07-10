package mediavault.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import mediavault.enums.*;

public class Anime extends MediaEntry
{
    private final ArrayList<Details> EPISODES;   // List of episodes in the anime
    private String studio;                 // Animation studio responsible for the anime
    private String alternativeTitle; // Alternative/untranslated title

    /**
     * Constructs a new Anime media entry with its specific attributes and initializes an empty episode list.
     * <p>
     * <b>Precondition:</b> genres list must not be null. release year should be a positive integer.<br>
     * <b>Postcondition:</b> An Anime instance is initialized, linking its base details to the underlying MediaEntry.
     * </p>
     * @param release          The release year of the anime series.
     * @param title            The main title of the anime.
     * @param synopsis         A short summary or plot description of the anime.
     * @param genres           A list of genres associated with this anime.
     * @param alternativeTitle An alternate or localized title for the anime.
     * @param studio           The animation studio that produced the series.
     * @param status           The current tracking status.
     */
    public Anime (int release, String title, String synopsis,
                  ArrayList<Genre> genres, String alternativeTitle,
                  String studio, Status status)
    {
        super(MediaType.ANIME, new Details(release, title, synopsis), genres);

        this.alternativeTitle = alternativeTitle;
        this.studio = studio;

        EPISODES = new ArrayList<Details>();
    }

    /**
     * Adds an individual episode details object to the anime's record history.
     * <p>
     * <b>Precondition:</b> Title and synopsis should be valid string instances.<br>
     * <b>Postcondition:</b> A new Details instance is appended to the internal episodes array, and lastModified timestamp is updated.
     * </p>
     * @param release The release year of the specific episode.
     * @param title   The sub-title of the episode.
     * @param synopsis Brief thematic overview of the episode.
     * @return void
     */
    public void addEpisode(int release, String title, String synopsis)
    {
        EPISODES.add(new Details(release, title, synopsis));
        setLastModified(LocalDateTime.now());
    }

    /**
     * Changes the studio responsible for the Anime.
     * <b>Precondition:</b> newStudio is not null.<br>
     * <b>Postcondition:</b> The studio assigned to the Anime is updated.
     * @param newStudio The new studio assigned to the Anime.
     * @return void
     */
    public void setStudio(String newStudio)
    {
        studio = newStudio;
        setLastModified(LocalDateTime.now());
    }

    /**
     * Returns the studio currently assigned to the Anime.
     * <b>Precondition:</b> studio is not null.<br>
     * <b>Postcondition:</b> None.
     * @return String The studio currently assigend to the Anime.
     */
    public String getStudio()
    {
        return studio;
    }

    /**
     * Returns the alternative title of the Anime.
     * <b>Precondition:</b> alternativeTitle is not null.<br>
     * <b>Postcondition:</b> None.
     * @return String The alternative or translated title of the Anime.
     */
    public String getAlternativeTitle()
    {
        return alternativeTitle;
    }

    /**
     * Returns the Anime's list of episodes.
     * <b>Precondition:</b> episodes is not null.<br>
     * <b>Postcondition:</b> None.
     * @return ArrayList<Details> List of details pertaining to the episodes.
     */
    public ArrayList<Details> getAnimeEpisodes()
    {
        return EPISODES;
    }
}
