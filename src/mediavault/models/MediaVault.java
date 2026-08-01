package mediavault.models;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import mediavault.enums.Status;
import mediavault.enums.MediaType;
import mediavault.enums.Genre;

public class MediaVault implements Serializable
{
    ArrayList<MediaEntry> entries;

    /**
     * Constructs an empty MediaVault database, initializing the internal collection
     * used to store media library entries.
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> A new MediaVault instance is initialized with an empty, non-null internal storage list.
     * </p>
     */
    public MediaVault()
    {
        entries = new ArrayList<MediaEntry>();
    }

    /**
     * Adds an entry to the list of entries if it does not already exist.
     * <p>
     * <b>Precondition:</b> The entry parameter must not be null, and must not already exist in the vault.<br>
     * <b>Postcondition:</b> The entry is successfully appended to the internal storage list.
     * </p>
     * @param entry Container of media item details
     * @return void
     * @throws IllegalArgumentException if the entry is already present in the vault.
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
     * Removes all instances of an entry with a given title released on a specific year.
     * <p>
     * <b>Precondition:</b> An entry matching the specified title and release year must exist within the vault.<br>
     * <b>Postcondition:</b> All matching media entries are permanently removed from the internal storage list.
     * </p>
     * @param title Title of the entry
     * @param year  Release year of an entry
     * @return void
     * @throws IllegalArgumentException if no entry matches the given criteria.
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

    /* GETTERS */

    /**
     * Returns the list of entries that the user has in their vault.
     * <b>Precondition:</b> entries is not null.<br>
     * <b>Postcondition:</b> None.
     * @return ArrayList<MediaEntry> List of entries in the user's vault.
     */
    public ArrayList<MediaEntry> getAll()
    {
        return entries;
    }

    /**
     * Returns the first entry with an exact match to a certain name and year.
     * <p>
     * <b>Precondition:</b> title must not be null and year must be greater than 0.<br>
     * <b>Postcondition:</b> Returns the matching MediaEntry object if found; otherwise returns null.
     * </p>
     * @param title Title of the entry
     * @param year  Release year of an entry
     * @return MediaEntry The first matching media entry, or null if no match exists.
     */
    public MediaEntry getEntry(String title, int year)
    {
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
     * Returns an ArrayList with all entries that match any of the provided parameters.
     * <p>
     * <b>Precondition:</b> Parameters can be null or empty if they are not intended to be filtered against.<br>
     * <b>Postcondition:</b> Returns a filtered collection of entries where at least one matching criteria is met.
     * </p>
     * @param title  Title of the media to be searched for
     * @param year   Release year of the media to be searched for
     * @param type   Type of media to be searched for
     * @param status Progress status to be searched for
     * @param genres List of genres to be searched for
     * @return ArrayList<MediaEntry> All entries that match the given parameters.
     */
    public ArrayList<MediaEntry> getEntries(String title, int year, MediaType type,
                                            Status status, List<Genre> genres)
    {
        return new ArrayList<MediaEntry>(entries.stream().filter(
            entry ->
                (title == null || entry.getDetails().getTitle().contains(title)) &&
                (year <= 0 || entry.getDetails().getYear() == year) &&
                (type == null || entry.getMediaType() == type) &&
                (status == null || entry.getStatus() == status) &&
                (genres == null || genres.isEmpty() || entry.getGenres().containsAll(genres))
        ).toList());
    }

    /**
     * Tallies the amount of entries that match the parameters.
     * <p>
     * <b>Precondition:</b> Parameters can be null if they are excluded from the tally constraint.<br>
     * <b>Postcondition:</b> Returns the total count of matching entries as a non-negative long value.
     * </p>
     * @param type   Type of media to be searched for
     * @param status Progress status to be searched for
     * @param genres List of genres to be searched for
     * @return long Total number of entries that match the parameters.
     */
    public long getTotalByAttributes(MediaType type, Status status,
                                     ArrayList<Genre> genres)
    {
        return entries.stream().filter(entry ->
            (type == null || entry.getMediaType() == type) &&
            (status == null || entry.getStatus() == status) &&
            (genres == null || entry.getGenres().containsAll(genres))
        ).count();
    }

    /**
     * Returns the total number of entries the user has in their vault.
     * <b>Precondition:</b> entries is not null.<br>
     * <b>Postcondition:</b> None.
     * @return long The total number of entries in the user's vault
     */
    public long getTotal()
    {
        return entries.size();
    }

}
