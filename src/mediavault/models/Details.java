package mediavault.models;

import java.io.Serializable;

public class Details implements Serializable
{
    private int year;
    private String title;
    private String synopsis;

    /**
     * Constructs a generic Details wrapper containing essential tracking data.
     * <p>
     * <b>Precondition:</b> Title and synopsis must be non-null strings.<br>
     * <b>Postcondition:</b> A Details instance is initialized to hold the descriptive metadata of an entry or episode.
     * </p>
     * @param year     The calendar year related to this specific item.
     * @param title    The text name or title designation.
     * @param synopsis The accompanying descriptive text summary.
     */
    public Details(int year, String title, String synopsis) {
        this.year = year;
        this.title = title;
        this.synopsis = synopsis;
    }

    /**
     *
     * @param year
     */
    public void setYear (int year)
    {
        this.year = year;
    }

    /**
     *
     * @param title
     */
    public void setTitle (String title)
    {
        this.title = title;
    }

    /**
     *
     * @param synopsis
     */
    public void setSynopsis (String synopsis)
    {
        this.synopsis = synopsis;
    }

    /**
     *
     * @return int
     */
    public int getYear ()
    {
        return year;
    }

    /**
     *
     * @return String
     */
    public String getTitle ()
    {
        return title;
    }

    /**
     *
     * @return String
     */
    public String getSynopsis ()
    {
        return synopsis;
    }
}
