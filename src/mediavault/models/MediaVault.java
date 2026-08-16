package mediavault.models;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.stream.Collectors;

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

	/**
	 * Exports all media entries stored in the vault into a formatted CSV string.
	 * <p>
	 * <b>Precondition:</b> None.<br>
	 * <b>Postcondition:</b> Returns a standard CSV string containing headers and formatted rows representing all entries, escaping special characters as needed.
	 * </p>
	 *
	 * @return A CSV-formatted String representing all media vault entries.
	 */
	public String toCSV() {
		StringBuilder sb = new StringBuilder();

		// csv header

		sb.append("Type,Title,Year,Status,Rating,Synopsis,Genres,Extra1,Extra2,Extra3\n");

		for (MediaEntry entry : getAll()) {
			String type = entry.getMediaType().name();
			String title = escapeCSV(entry.getDetails().getTitle());
			int year = entry.getDetails().getYear();
			String status = entry.getStatus().name();
			float rating = entry.getRating();
			String synopsis = escapeCSV(entry.getDetails().getSynopsis());

			// genres (bar separated)

			String genres = entry.getGenres().stream()
				.map(Genre::name)
				.collect(Collectors.joining("|"));

			String extra1 = "", extra2 = "", extra3 = "";

			// add special fields

			if (entry instanceof Anime anime) {
				extra1 = escapeCSV(anime.getAlternativeTitle());
				extra2 = escapeCSV(anime.getStudio());
			} else if (entry instanceof Novel novel) {
				extra1 = escapeCSV(novel.getPublisher());
				extra2 = escapeCSV(novel.getAuthor());
				extra3 = String.valueOf(novel.getChapters());
			} else if (entry instanceof VideoGame game) {
				extra1 = escapeCSV(game.getPublisher());
				extra2 = escapeCSV(game.getStudio());
			}

			sb.append(String.format("%s,%s,%d,%s,%.1f,%s,%s,%s,%s,%s\n",
				type, title, year, status, rating, synopsis, genres, extra1, extra2, extra3));
		}
		return sb.toString();
	}

	private String escapeCSV(String value) {
		if (value == null)
			return "";

		// add esc sequences for some characters

		if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
			return "\"" + value.replace("\"", "\"\"") + "\"";
		}
		return value;
	}
	/**
	 * Parses a single CSV line into an array of field strings.
	 * Properly handles fields enclosed in double quotes containing embedded commas or escaped quotes.
	 * <p>
	 * <b>Precondition:</b> line must not be null.<br>
	 * <b>Postcondition:</b> Returns an array of parsed string fields with enclosing quotes stripped and internal escaped quotes resolved.
	 * </p>
	 *
	 * @param line The single CSV string row to parse.
	 * @return An array of parsed field values.
	 */
	public static String[] parseCSVLine(String line) {
		List<String> values = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean inQuotes = false;

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (c == '"') {
				// handle escaped double quote inside a quoted value
				if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
					sb.append('"');
					i++;
				} else {
					inQuotes = !inQuotes;
				}
			} else if (c == ',' && !inQuotes) {
				// end of field
				values.add(sb.toString().trim());
				sb.setLength(0); // Clear buffer
			} else {
				sb.append(c);
			}
		}

		// append the last field
		values.add(sb.toString().trim());

		return values.toArray(new String[0]);
	}

	/**
	 * Constructs a new MediaVault instance populated with media entries parsed from CSV line strings.
	 * <p>
	 * <b>Precondition:</b> lines list must not be null and must adhere to the expected CSV structure.<br>
	 * <b>Postcondition:</b> Returns a populated MediaVault instance with reconstructed Anime, Novel, and VideoGame objects.
	 * </p>
	 *
	 * @param lines A list of raw CSV string rows including the header.
	 * @return A MediaVault instance populated with the parsed entries.
	 */
	public static MediaVault fromCSV(List<String> lines) {
		MediaVault vault = new MediaVault();

		// skip header line if present

		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			if (line.isEmpty()) continue;

			String[] cols = parseCSVLine(line);

			MediaType type = MediaType.valueOf(cols[0]);
			String title = cols[1];
			int year = Integer.parseInt(cols[2]);
			Status status = Status.valueOf(cols[3]);
			float rating = Float.parseFloat(cols[4]);
			String synopsis = cols[5];

			ArrayList<Genre> genres = new ArrayList<>();
			if (!cols[6].isBlank()) {
				for (String g : cols[6].split("\\|")) {
					genres.add(Genre.valueOf(g));
				}
			}

			MediaEntry entry = null;

			switch (type) {
				case ANIME -> entry = new Anime(year, title, synopsis, genres, cols[7], cols[8], status);
				case NOVEL -> {
					int chapters = cols[9].isBlank() ? 0 : Integer.parseInt(cols[9]);
					entry = new Novel(year, title, synopsis, genres, cols[7], cols[8], status, chapters);
				}
				case VIDEOGAME -> entry = new VideoGame(year, title, synopsis, genres, cols[7], cols[8], status);
			}

			if (entry != null) {
				entry.setRating(rating);
				vault.addEntry(entry);
			}
		}
		return vault;
	}

}
