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
     * Sets the release year in the details.
     * @param year Year to set.
     */
    public void setYear (int year)
    {
        this.year = year;
    }

    /**
     * Sets the title in the details.
     * @param title Title to be set.
     */
    public void setTitle (String title)
    {
        this.title = title;
    }

    /**
     * Sets the synopsis of the details.
     * @param synopsis Synopsis to be set.
     */
    public void setSynopsis (String synopsis)
    {
        this.synopsis = synopsis;
    }

    /**
     * Returns the year associated with the details.
     * @return int Release year associated with the details.
     */
    public int getYear ()
    {
        return year;
    }

    /**
     * Returns the title of the details.
     * @return String Title of the details.
     */
    public String getTitle ()
    {
        return title;
    }

    /**
     * Returns the synopsis of the details.
     * @return String Synopsis of the details.
     */
    public String getSynopsis ()
    {
        return synopsis;
    }
}
