package mediavault.models;
public class Details
{
    private int release;
    private String title;
    private String synopsis;

    public void setRelease (int release)
    {
        this.release = release;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public void setSynopsis (String synopsis)
    {
        this.synopsis = synopsis;
    }

    public int getRelease ()
    {
        return release;
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
