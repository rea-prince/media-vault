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

    public void updateEntry(String title, int year, Status status)
    {
        for (MediaEntry entry : entries) {
            if (entry.getDetails().getTitle().equals(title) &&
                entry.getDetails().getYear() == year) {
                    entry.updateStatus(status);
                    return;
                }
        }
        throw new IllegalArgumentException("Entry not found.");
    }

    public ArrayList<MediaEntry> getEntries(Details details, MediaType type,
                                            Status status, ArrayList<Genre> genres)
    {
        return new ArrayList<MediaEntry>(entries.stream().filter(
            entry ->
                (details != null &&
                    (entry.getDetails().getTitle().contains(details.getTitle()) ||
                     entry.getDetails().getYear() == details.getYear())) ||
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
