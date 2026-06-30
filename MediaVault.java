import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MediaVault 
{
    Map<int, MediaType> entries = new Map<int, MediaType>();

    public MediaVault ()
    {

    }

    public void addEntry (MediaEntry entry, Status status)
    {

    }

    public void removeEntry (MediaEntry entry)
    {

    }

    public void updateEntry (MediaEntry entry, Status status)
    {

    }

    public MediaEntry getEntryByID (int ID)
    {
        return entry;
    }

    public ArrayList<MediaEntry> getEntriesByID (int ID)
    {
        return entries;
    }

    public ArrayList<MediaEntry> getEntriesByAttributes (MediaType type, Status status, Genre genre)
    {
        return entries;
    }

    public int getTotalByAttributes (MediaType type, Status status, Genre genre)
    {
        return ;
    }

    public float getAverageRating ()
    {
        return ;
    }
}
