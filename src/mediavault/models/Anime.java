package mediavault.models;

import java.util.ArrayList;
import mediavault.enums.*;

public class Anime
{
    Details episodes = new Details();
    private String studio;
    private String alternativeTitle;

    public Anime (int release, String title, String synopsis, ArrayList<Genre> genres,
                  String publisher, String studio, Status status)
    {

    }

    public String getStudio ()
    {
        return studio;
    }

    public String getAlternativeTitle ()
    {
        return alternativeTitle;
    }
}
