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
                genreOptions.add(String.format("[%d] - %s", g.getId(), g.name()));
                validIds.add(String.valueOf(g.getId()));
            }
        }

        Display.createBoard("Genre", genreOptions);
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

    public static void addEntry(MediaVault vault)
    {
        Input.promptAdd(vault);
    }

    public static void addAnimeEpisodes(MediaVault vault)
    {
        Input.promptAddAnimeEpisodes(vault);
    }

    public static void deleteEntry(MediaVault vault)
    {
        Input.promptDelete(vault);
    }

    public static void updateEntry(MediaVault vault)
    {
        Input.promptUpdate(vault);
    }

    public static void rateEntry(MediaVault vault)
    {
        Input.promptAssign(vault);
    }

    public static void showEntries(MediaVault vault)
    {

    }

    public static void summarize(MediaVault vault)
    {
        System.out.println("Total number of entries: " + vault.getTotalByAttributes(null, null, null));

        System.out.println("\nNumber of entries by media type: ");
        MediaType[] types = MediaType.values();
        for (MediaType type : types)
            System.out.println(type + ": " + vault.getTotalByAttributes(type, null, null));

        System.out.println("\nNumber of entries by genre: ");
        Genre[] genreList = Genre.values();
        for (Genre genre : genreList)
        {
            ArrayList<Genre> genreDisp = new ArrayList<>();
            genreDisp.add(genre);
            if (vault.getTotalByAttributes(null, null, genreDisp) > 0)
                System.out.println(genre + ": " + vault.getTotalByAttributes(null, null, genreDisp));
        }

        System.out.println("\nNumber of entries by status: ");
        Status[] statusList = Status.values();
        for (Status status : statusList)
            System.out.println(status + ": " + vault.getTotalByAttributes(null, status, null));

        float totalRating = 0;
        for (MediaEntry entry : vault.getEntries(null, 0, null, Status.COMPLETED, null))
            totalRating += vault.getEntry(entry.getDetails().getTitle(), 0).getRating();
        float averageRating = totalRating / vault.getTotalByAttributes(null, Status.COMPLETED, null);
        System.out.println("\nAverage rating: " + averageRating);
    }
}
