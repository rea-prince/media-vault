package mediavault.models;

public class Novel
{
    private String publisher;
    private String author;

    public Novel (int release, String title, String synopsis, Genre[] genre,
                  String publisher, String studio, Status status)
    {

    }

    public String getPublisher ()
    {
        return publisher;
    }

    public String getAuthor ()
    {
        return author;
    }
}
