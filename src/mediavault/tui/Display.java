package mediavault.tui;

import java.util.ArrayList;
import java.util.Scanner;
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
            System.out.printf("%s %s %s\n", padding, title, padding);
        }
        for (String option : options) {
            if (option == null || option.trim().isEmpty())
                continue;

            System.out.println(option);
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
        System.out.println(entry.getDetails().getTitle());
        System.out.println("Release year: " + entry.getDetails().getYear());
        System.out.println("Synopsis: " + entry.getDetails().getSynopsis());
        System.out.print("Genres: ");
        for (Genre entryGenre : entry.getGenres())
            System.out.print(entryGenre.getName() + ", ");
        System.out.println("Status: " + entry.getStatus());
    }

    public static void mainMenu()
    {
        createBoard("Media Vault", List.of(
            "[A] Add a new entry",
            "[B] Add anime episodes",
            "[D] Delete an entry",
            "[U] Update an entry",
            "[R] Rate and review an entry",
            "[E] Display the entire library",
            "[S] Summarize the library",
            "[X] Exit"
        ));
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

        tempEntries = List.of(Float.toString(averageRating));

        createBoard("--- Average Rating", tempEntries);

        tempEntries.clear();
    }
}
