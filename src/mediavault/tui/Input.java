package mediavault.tui;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

import mediavault.models.Anime;
import mediavault.models.Novel;
import mediavault.models.VideoGame;
import mediavault.models.MediaEntry;
import mediavault.models.MediaVault;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

abstract public class Input
{
    private static String getStrInput(String prompt, String... valid) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        List<String> validIn = Arrays.asList(valid);
        do {
            if (!input.equals(""))
                System.out.println("Invalid option, please try again.");

            System.out.print(prompt + ": ");
            input = scanner.nextLine().trim().toUpperCase();
        } while(!input.equals("") && ((valid.length > 0) && !validIn.contains(input)));

        return input;
    }

    public static void promptAdd(MediaVault vault)
    {
        String entryType;
        String title;
        String synopsis;
        ArrayList<Genre> genres;
        String alternative = null, publisher = null, author = null, studio = null;
        int chapters = 0;
        int release;

        Display.createBoard("Add", List.of(
            "[A] - Anime",
            "[N] - Novel",
            "[V] - Video Game"
        ));
        entryType = getStrInput("Media type", "A", "N", "V");

        Display.createBoard("Entry Details", List.of());
        title = getStrInput("Title");

        release = Integer.parseInt(getStrInput("Release Year"));
        synopsis = getStrInput("Synopsis");


        /* GENRES */

        ArrayList<String> genreList = new ArrayList<String>();
        ArrayList<String> validIds = new ArrayList<String>();
        for (Genre g : Genre.values()) {
            if (g != Genre.INVALID) {
                genreList.add(String.format("[%d] - %s", g.getId(), g.name()));
                validIds.add(String.valueOf(g.getId()));
            }
        }

        Display.createBoard("--- Genre options", genreList);
        String rawIn = getStrInput("Genre");
        String[] genreChoices = rawIn.split("[,\\.\\s]+");

        genres = new ArrayList<Genre>();

        for (String choice : genreChoices) {
            if (validIds.contains(choice)) {
                genres.add(Genre.fromId(Integer.parseInt(choice)));
            } else {
                System.out.println("Skipping invalid option: " + choice);
            }
        }

        /* STATUS */

        Display.createBoard("--- Status Options", List.of(
            "[P] - Planned",
            "[I] - In-progress",
            "[C] - Completed"
        ));
        String status = getStrInput("Status", "P", "I", "C");

        MediaEntry entry = null;

        switch (entryType) {
            case "A": {
                studio = getStrInput("Studio");
                alternative = getStrInput("Alternative Title");
                entry = new Anime(release, title, synopsis, genres, alternative, studio, null);
            } break;
            case "N": {
                author = getStrInput("Author");
                publisher = getStrInput("Publisher");
                chapters = Integer.parseInt(getStrInput("Chapters")); // TO DO: Add safety
                entry = new Novel(release, title, synopsis, genres, publisher, author, null, chapters);
            } break;
            case "V": {
                studio = getStrInput("Studio");
                publisher = getStrInput("Publisher");
                entry = new VideoGame(release, title, synopsis, genres, publisher, studio, null);
            } break;
        }

        if (entry != null) {
            switch (status) {
               	case "P": {
                    entry.setStatus(Status.PLANNED);
                } break;
                case "I": {
                    entry.setStatus(Status.IN_PROGRESS);
                } break;
                case "C": {
                    entry.setStatus(Status.COMPLETED);
                } break;

            }
        }

        vault.addEntry(entry);
    }

    public static void promptDelete(MediaVault vault)
    {
        ArrayList<String> entries = new ArrayList<>();

        /* PRINT */

        for (MediaEntry entry : vault.getAll()) {
            entries.add(String.format(
                "%s (%d)",
                entry.getDetails().getTitle(),
                entry.getDetails().getYear()
            ));
        }
        Display.createBoard("Delete", entries);

        /* INPUT */

        String media = getStrInput("Title");
        int year = Integer.parseInt(getStrInput("Release Year"));

        MediaEntry entry = vault.getEntry(media, year);

        /* REMOVE */

        if (entry != null)
            vault.removeEntry(media, year);
        else
            System.out.println("Entry not found.");
    }

    public static void promptAddAnimeEpisodes(MediaVault vault)
    {

        /* print */

        ArrayList<String> animeTitles = new ArrayList<>();

        for (MediaEntry entry : vault.getEntries(null, 0, MediaType.ANIME, null, null)) {
            animeTitles.add(entry.getDetails().getTitle());
        }

        Display.createBoard("Add Anime Episode", animeTitles);

        /* option */

        String media = getStrInput("Anime");
        MediaEntry chosenAnime = vault.getEntry(media, 0);

        if (chosenAnime == null) {
            System.out.println("Anime not found.");
            return;
        }

        Display.createBoard("--- Episode Details", List.of());

        String title = getStrInput("Title");
        int release = Integer.parseInt(getStrInput("Release Year"));
        String synopsis = getStrInput("Synopsis");

        Anime anime = new Anime(
            chosenAnime.getDetails().getYear(),
            chosenAnime.getDetails().getTitle(),
            chosenAnime.getDetails().getSynopsis(),
            chosenAnime.getGenres(),
            null, null,
            chosenAnime.getStatus()
        );

        anime.addEpisode(release, title, synopsis);
    }

    public static void promptUpdate(MediaVault vault)
    {
        ArrayList<String> entries = new ArrayList<>();

        for (MediaEntry entry : vault.getAll()) {
            entries.add(String.format(
                "%s (%d)",
                entry.getDetails().getTitle(), entry.getDetails().getYear()
            ));
        }

        Display.createBoard("Update Status", entries);

        String media = getStrInput("Title");
        int year = Integer.parseInt(getStrInput("Release Year"));

        MediaEntry entry = vault.getEntry(media, year);

        if (entry == null) {
            System.out.println("Entry not found.");
            return;
        }

        Display.createBoard("Status", List.of(
            "[P] - Planned",
            "[I] - In-progress",
            "[C] - Completed"
        ));

        switch (getStrInput("Status", "P", "I", "C")) {
            case "P":
                entry.setStatus(Status.PLANNED);
                break;

            case "I":
                entry.setStatus(Status.IN_PROGRESS);
                break;

            case "C":
                entry.setStatus(Status.COMPLETED);
                break;
        }
    }

    public static void promptAssign(MediaVault vault)
    {
        ArrayList<String> entries = new ArrayList<>();

        for (MediaEntry entry : vault.getEntries(null, 0, null, Status.COMPLETED, null)) {
            entries.add(String.format(
                "%s (%d)", entry.getDetails().getTitle(), entry.getDetails().getYear()
            ));
        }

        Display.createBoard("Rate and Review", entries);

        String media = getStrInput("Title");
        int year = Integer.parseInt(getStrInput("Release Year"));

        MediaEntry entry = vault.getEntry(media, year);

        if (entry == null) {
            System.out.println("Entry not found or status not complete.");
            return;
        }

        entry.setRating(Float.parseFloat(getStrInput("Rating")));
        entry.setReview(getStrInput("Review"));
    }
}
