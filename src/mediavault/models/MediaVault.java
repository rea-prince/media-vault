package mediavault.models;
import java.util.ArrayList;

import mediavault.enums.Status;
import mediavault.enums.MediaType;
import mediavault.enums.Genre;

public class MediaVault
{
    ArrayList<MediaEntry> entries;

    public MediaVault()
    {
        entries = new ArrayList<MediaEntry>();
    }

    /**
     * Adds an entry to the list of entries, and maps it to a unique
     * @param entry Container of media item details
     *
     * @return void
     */
    public void addEntry(MediaEntry entry)
    {
        if (!entries.contains(entry)) {
            entries.add(entry);
            return;
        }
        throw new IllegalArgumentException("Entry is already in vault.");
    }

    /**
     * Removes all instances of an entry with a given title released on a
     * specific year
     * @param title Title of the entry
     * @param year  Release year of an entry
     *
     * @return void
     */
    public void removeEntry(String title, int year)
    {
        if (!entries.removeIf(entry ->
            entry.getDetails().getTitle().equals(title) &&
            entry.getDetails().getYear() == year)
        )
        {
            throw new IllegalArgumentException("Entry not found.");
        }
    }

    /**
     * Returns entire ArrayList of entries in the vault
     *
     * @return ArrayList<MediaEntry>
     */

    public ArrayList<MediaEntry> getAll() {
        return entries;
    }

    /**
     * Returns the first entry with an exact match to a certain name and year
     * @param title Title of the entry
     * @param year  Release year of an entry
     *
     * @return ArrayList<MediaEntry>
     */
    public MediaEntry getEntry(String title, int year) {
        ArrayList<MediaEntry> matchingEntries = new ArrayList<MediaEntry>(
            entries.stream().filter(entry ->
            (title != null) && entry.getDetails().getTitle().equals(title) &&
            (year > 0) && entry.getDetails().getYear() == year).toList()
        );

        if (matchingEntries.isEmpty()) {
            return null;
        }

        return matchingEntries.get(0);
    }

    /**
     * Returns an ArrayList with all entries that match the parameters;
     * parameters can be null if only some are needed
     * @param title  Title of the media to be searched for
     * @param year   Release year of the media to be searched for
     * @param type   Type of media to be searched for
     * @param status Progress status to be searched for
     * @param genres List of genres to be searched for
     *
     * @return ArrayList<MediaEntry> All entries that match the parameter
     */
    public ArrayList<MediaEntry> getEntries(String title, int year, MediaType type,
                                            Status status, ArrayList<Genre> genres)
    {
        return new ArrayList<MediaEntry>(entries.stream().filter(
            entry ->
                (title != null && entry.getDetails().getTitle().contains(title)) ||
                (year > 0 && entry.getDetails().getYear() == year) ||
                (type != null && entry.getMediaType() == type) ||
                (status != null && entry.getStatus() == status) ||
                (genres != null && entry.getGenres().containsAll(genres))
        ).toList());
    }

    /**
     * Tallies the amount of entries that match the Parameters
     *
     * @param type   Type of media to be searched for
     * @param status Progress status to be searched for
     * @param genres List of genres to be searched for
     *
     * @return long Total number of entries that match the parameters
     */

    public long getTotalByAttributes(MediaType type, Status status,
                                     ArrayList<Genre> genres)
    {
        return entries.stream().filter(entry ->
            (type != null && entry.getMediaType() == type) ||
            (status != null && entry.getStatus() == status) ||
            (genres != null && entry.getGenres().containsAll(genres))
        ).count();
    }

    // PROBABLY NOT THE JOB OF THIS CLASS
    // public float getAverageRating()
    // {
    //     if (entries.isEmpty())
    //         return 0;

    //     float sum = entries.stream().mapToDouble(entry -> entry.getRating()).count();

    //     return (sum / entries.size());
    // }
}
