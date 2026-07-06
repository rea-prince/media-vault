package mediavault.tui;

import java.util.ArrayList;
import java.util.List;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

import mediavault.models.Anime;
import mediavault.models.MediaEntry;
import mediavault.models.Novel;
import mediavault.models.VideoGame;
import mediavault.models.MediaVault;

abstract public class Display
{
    /* Displays a pre-formatted board with a header and a list of strings
     *
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
        }
        if (options != null) {
            for (String option : options) {
                if (option == null || option.trim().isEmpty())
                    continue;
                System.out.println(option);
            }
        }
    }

    public static void displayGenres() {
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

    public static void displayEntryDetails(MediaEntry entry) {

        // TO DO: Add lastModified

        System.out.println(entry.getDetails().getTitle());
        System.out.println(" > Release: " + entry.getDetails().getYear());
        System.out.println(" > Synopsis: " + entry.getDetails().getSynopsis());
        System.out.print(" > Genres: ");
        for (Genre entryGenre : entry.getGenres())
            System.out.print(entryGenre.getName() + ", ");
        System.out.println();
        System.out.println(" > Status: " + entry.getStatus().getName());
    }

    public static void showEntries(ArrayList<MediaEntry> entries)
    {
        for (MediaEntry entry : entries)
        {
            Display.displayEntryDetails(entry);

            if(entry instanceof Anime) {
                Anime anime = (Anime) entry;
                System.out.println(" > Alternate title: " + anime.getAlternativeTitle());
                System.out.println(" > Studio: " + anime.getStudio());
            }
            else if(entry instanceof Novel) {
                Novel novel = (Novel) entry;
                System.out.println(" > Author: " + novel.getAuthor());
                System.out.println(" > Publisher: " + novel.getPublisher());
                System.out.println(" > Number of chapters: " + novel.getChapters());
            }
            else if(entry instanceof VideoGame) {
                VideoGame videoGame = (VideoGame) entry;

                System.out.println(" > Studio: " + videoGame.getStudio());
                System.out.println(" > Publisher: " + videoGame.getPublisher());
            }
        }
    }

    public static void summarize(MediaVault vault)
    {
        List<String> tempEntries = new ArrayList<String>();

        createBoard("Library Summary", List.of());

        tempEntries = List.of(
            Long.toString(vault.getTotalByAttributes(null, null, null))
        );

        createBoard("--- Total Number of Entries", tempEntries);

        tempEntries.clear();

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
        for (MediaEntry entry : vault.getEntries(null, 0, null, Status.COMPLETED, null))
            totalRating += vault.getEntry(entry.getDetails().getTitle(), 0).getRating();
        float averageRating = totalRating / vault.getTotalByAttributes(null, Status.COMPLETED, null);

        tempEntries = List.of(String.format("%.2f", averageRating));

        createBoard("--- Average Rating", tempEntries);

        tempEntries.clear();
    }
}
