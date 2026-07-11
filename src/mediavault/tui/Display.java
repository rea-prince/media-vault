package mediavault.tui;

import java.util.ArrayList;
import java.util.List;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

import mediavault.models.Anime;
import mediavault.models.Details;
import mediavault.models.MediaEntry;
import mediavault.models.Novel;
import mediavault.models.VideoGame;
import mediavault.models.MediaVault;

abstract public class Display
{
    /**
     * Renders a standardized terminal information board with a title header and content options.
     * <p>
     * <b>Precondition:</b> title should not be null; options list may be null if only a header wrapper is needed.<br>
     * <b>Postcondition:</b> Clears the console and prints the formatted UI board to standard output.
     * </p>
     * @param title   The textual header or option tag for the display block.
     * @param options A collection of menu options or summary item lines to list.
     * @return void
     */
    public static void createBoard(String title, List<String> options)
    {
        if (title.startsWith("---")) {
            /* for options */
            System.out.println(title);
        } else {
            /* for headers(?) */
            int totalWidth = 50;
            int titleLength = title.length();

            int paddingSize = (totalWidth - titleLength - 2) / 2;
            paddingSize = Math.max(0, paddingSize);

            String padding = "=".repeat(paddingSize);

            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.printf("%s %s %s\n", padding, title, padding);
            System.out.flush();
        }
        if (options != null) {
            for (String option : options) {
                if (option == null || option.trim().isEmpty())
                    continue;
                System.out.println(option);
            }
        }
    }

    /**
     * Displays the list of valid genres the user can select for a media entry.
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Iterates through all available genres and lists them in a formatted selection table via createBoard.
     * </p>
     * @return void
     */
    public static void displayGenres()
    {
        ArrayList<String> genreOptions = new ArrayList<>();
        ArrayList<String> validIds = new ArrayList<>();

        for (Genre g : Genre.values()) {
            if (g != Genre.INVALID) {
                genreOptions.add(String.format("[%d] - %s", g.getId(), g.getName()));
                validIds.add(String.valueOf(g.getId()));
            }
        }

        Display.createBoard("Genre", genreOptions);
    }

    /**
     * Displays the title, release year, and summary layout of an individual anime episode.
     * <p>
     * <b>Precondition:</b> episode is not null.<br>
     * <b>Postcondition:</b> Outputs the episode descriptive text elements directly to the console.
     * </p>
     * @param episode The Details record tracking episode name and tracking context.
     * @return void
     */
    public static void displayEntryDetails(MediaEntry entry)
    {

        // TO DO: Add lastModified

        System.out.printf("%s (%d) | %s\n",
            entry.getDetails().getTitle(),
            entry.getDetails().getYear(),
            entry.getMediaType().getName()
        );
        System.out.flush();
        System.out.println(" > Synopsis : " + entry.getDetails().getSynopsis());
        System.out.print(" > Genres   : ");
        for (Genre entryGenre : entry.getGenres())
        {
            System.out.print(entryGenre.getName());
            if(entryGenre != entry.getGenres().getLast())
                System.out.print(", ");
        }
        System.out.println();
        if (entry instanceof Anime) {
            Anime anime = (Anime) entry;
            System.out.println(" > Alt title: " + anime.getAlternativeTitle());
            System.out.println(" > Studio   : " + anime.getStudio());
            System.out.println(" > Episodes : " + anime.getAnimeEpisodes().size());
            // int i = 0;
            // for (Details episode : anime.getAnimeEpisodes())
            // {
            //     i++;
            //     System.out.print("Episode " + i + " - ");
            //     displayAnimeEpisode(episode);
            // }
        }
        else if (entry instanceof Novel) {
            Novel novel = (Novel) entry;
            System.out.println(" > Author   : " + novel.getAuthor());
            System.out.println(" > Publisher: " + novel.getPublisher());
            System.out.println(" > Chapters : " + novel.getChapters());
        }
        else if (entry instanceof VideoGame) {
            VideoGame videoGame = (VideoGame) entry;

            System.out.println(" > Studio   : " + videoGame.getStudio());
            System.out.println(" > Publisher: " + videoGame.getPublisher());
        }
        System.out.println(" > Status   : " + entry.getStatus().getName());

        if (entry.getRating() >= 0.0) {
            System.out.println(" > Rating   : " + entry.getRating());
            System.out.println(" > Review   : " + entry.getReview());
        }
    }

    /**
     * Displays the title, release year, and summary layout of an individual anime episode.
     * <p>
     * <b>Precondition:</b> episode is not null.<br>
     * <b>Postcondition:</b> Outputs the details of the episode into the console.
     * </p>
     * @param episode The Details record tracking episode name and tracking context.
     * @return void
     */
    public static void displayAnimeEpisode(Details episode)
    {
        System.out.printf("%s (%d)\n", episode.getTitle(), episode.getYear());
        System.out.flush();
        System.out.println(" > Synopsis: " + episode.getSynopsis());
    }

    /**
     * Displays a lightweight index listing names and release years of items in the vault database.
     * <p>
     * <b>Precondition:</b> vault is not null.<br>
     * <b>Postcondition:</b> Prints a baseline title map of the library collection to the console.
     * </p>
     * @param entries The list of stored media items to showcase.
     * @return void
     */
    public static void showTitles(ArrayList<MediaEntry> entries) {
        for (MediaEntry entry : entries)
        {
            System.out.printf("%s (%d)\n",
                entry.getDetails().getTitle(),
                entry.getDetails().getYear()
            );
        }
        System.out.flush();
    }

    /**
     * Displays a summary dashboard detailing existing item types, genres, status tallies, and average score metrics.
     * <p>
     * <b>Precondition:</b> vault is not null.<br>
     * <b>Postcondition:</b> prints details of the user's vault onto the console.
     * </p>
     * @param vault The user's MediaVault.
     * @return void
     */
    public static void summarize(MediaVault vault)
    {
        ArrayList<String> tempEntries = new ArrayList<String>();

        createBoard("Library Summary", List.of());

        createBoard("--- Total Number of Entries", List.of(
            Long.toString(vault.getTotal())
        ));

        for (MediaType type : MediaType.values()) {
            tempEntries.add(String.format("%s: %d",
                type.getName(),
                vault.getTotalByAttributes(type, null, null)
            ));
        }

        createBoard("--- Number of entries by media type", tempEntries);

        tempEntries.clear();

        for (Genre genre : Genre.values()) {
            if (genre == Genre.INVALID)
                continue;

            ArrayList<Genre> genreDisp = new ArrayList<>();
            genreDisp.add(genre);
            long total = vault.getTotalByAttributes(null, null, genreDisp);
            if (total > 0)
                tempEntries.add(String.format("%s: %d",
                    genre.getName(), total
                ));
        }
        createBoard("--- Number of entries by genre", tempEntries);

        tempEntries.clear();

        for (Status status : Status.values()) {
            tempEntries.add(String.format("%s: %d",
                status.getName(), vault.getTotalByAttributes(null, status, null)
            ));
        }
        createBoard("--- Number of entries by status", tempEntries);

        tempEntries.clear();

        float totalRating = 0;
        int withRating = 0;
        for (MediaEntry entry : vault.getEntries(null, 0, null, Status.COMPLETED, null)) {
            if (entry.getRating() != -1f) {
                totalRating += entry.getRating();
                withRating++;
            }
        }
        float averageRating = withRating > 0 ? totalRating / withRating : 0;

        createBoard("--- Average Rating", List.of(String.format("%.2f", averageRating)));

    }
}
