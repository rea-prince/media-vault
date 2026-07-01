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
     * @param title Title of the entry
     * @param year  Release year of an entry
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

    public ArrayList<MediaEntry> getAll() {
        return entries;
    }

    public MediaEntry getEntry(String title, int year) {
        return entries.stream().filter(entry ->
            (title != null) && entry.getDetails().getTitle().equals(title) &&
            (year > 0) && entry.getDetails().getYear() == year).toList().get(0);
    }

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

    public long getTotalByAttributes(MediaType type, Status status,
                                     ArrayList<Genre> genres)
    {
        return entries.stream().filter(entry ->
            (type != null && entry.getMediaType() == type) ||
            (status != null && entry.getStatus() == status) ||
            (genres != null && entry.getGenres().containsAll(genres))
        ).count();
    }

    public float getAverageRating()
    {
        if (entries.isEmpty())
            return 0;

        float sum = entries.stream().mapToDouble(entry -> entry.getRating()).count();

        return (sum / entries.size());
    }
}
