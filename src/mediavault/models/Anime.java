package mediavault.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import mediavault.enums.*;

public class Anime extends MediaEntry
{
    ArrayList<Details> episodes;
    private String studio;
    private final String alternativeTitle;

    public Anime (int release, String title, String synopsis,
                  ArrayList<Genre> genres, String alternativeTitle,
                  String studio, Status status)
    {
        setDetails(new Details(release, title, synopsis));
        setGenres(genres);
        updateStatus(status);

        this.alternativeTitle = alternativeTitle;
        this.studio = studio;

        episodes = new ArrayList<Details>();
    }

    public void addEpisode(int release, String title, String synopsis)
    {
        episodes.add(new Details(release, title, synopsis));
        setLastModified(LocalDateTime.now());
    }

    public void setStudio(String newStudio) {
        studio = newStudio;
        setLastModified(LocalDateTime.now());
    }

    public String getStudio()
    {
        return studio;
    }

    public String getAlternativeTitle()
    {
        return alternativeTitle;
    }
}
