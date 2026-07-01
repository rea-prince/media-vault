package mediavault.models;
public class Details
{
    private int year;
    private String title;
    private String synopsis;

    public void setYear (int year)
    {
        this.year = year;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public void setSynopsis (String synopsis)
    {
        this.synopsis = synopsis;
    }

    public int getYear ()
    {
        return year;
    }

    public String getTitle ()
    {
        return title;
    }

    public String getSynopsis ()
    {
        return synopsis;
    }
}
