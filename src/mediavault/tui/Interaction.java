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
import java.util.List;

abstract public class Interaction {

    public static void mainEntry(MediaVault vault) {
        String option;
        do {
            if (vault == null) {
                System.out.println("ERROR: Could not load vault.");
            }

            // TO DO: Add anime episodes view

            Display.createBoard("Media Vault", List.of(
                "[A] Add a new entry",
                "[B] Add anime episodes",
                "[D] Delete an entry",
                "[U] Update an entry",
                "[R] Rate and review an entry",
                "[E] Display the entire library",
                "[S] Summarize the library",
                "[X] Exit"
            ));

            option = Input.getStrInput(
                "Choose what to do",
                "A", "B", "D", "U", "R", "E", "S", "X"
            );

            switch(option) {
                case "A": {
                    promptAdd(vault);
                } break;
                case "B": {
                    promptAddAnimeEpisodes(vault);
                } break;
                case "D": {
                    promptDelete(vault);
                } break;
                case "U": {
                    promptUpdate(vault);
                } break;
                case "R": {
                    promptAssign(vault);
                } break;
                case "E": {
                    showEntries(vault);
                } break;
                case "S": {
                    Display.summarize(vault);
                } break;
            }
        } while (!option.equals("X"));

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
        entryType = Input.getStrInput("Media type", "A", "N", "V");

        Display.createBoard("Entry Details", null);
        title = Input.getStrInput("Title");

        release = Input.getIntInput("Release Year");
        synopsis = Input.getStrInput("Synopsis");


        /* GENRES */

        ArrayList<String> genreList = new ArrayList<String>();
        ArrayList<String> validIds = new ArrayList<String>();
        for (Genre g : Genre.values()) {
            if (g != Genre.INVALID) {
                genreList.add(String.format("[%d] - %s", g.getId(), g.getName()));
                validIds.add(String.valueOf(g.getId()));
            }
        }


        Display.createBoard("--- Genre options", genreList);
        String rawIn = Input.getStrInput("Genre");
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
        String status = Input.getStrInput("Status", "P", "I", "C");

        MediaEntry entry = null;

        switch (entryType) {
            case "A": {
                studio = Input.getStrInput("Studio");
                alternative = Input.getStrInput("Alternative Title");
                entry = new Anime(release, title, synopsis, genres, alternative, studio, null);
            } break;
            case "N": {
                author = Input.getStrInput("Author");
                publisher = Input.getStrInput("Publisher");
                chapters = Input.getIntInput("Chapters"); // TO DO: Add safety
                entry = new Novel(release, title, synopsis, genres, publisher, author, null, chapters);
            } break;
            case "V": {
                studio = Input.getStrInput("Studio");
                publisher = Input.getStrInput("Publisher");
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

        String media = Input.getStrInput("Title");
        int year = Input.getIntInput("Release Year");

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

        String anime = Input.getStrInput("Title");
        int releaseYear = Input.getIntInput("Release Year");
        Anime chosenAnime = (Anime) vault.getEntry(anime, releaseYear);

        if (chosenAnime == null) {
            System.out.println("Anime not found.");
            return;
        }

        Display.createBoard("--- Episode Details", null);

        String title = Input.getStrInput("Title");
        int release = Input.getIntInput("Release Year");
        String synopsis = Input.getStrInput("Synopsis");
        chosenAnime.addEpisode(release, title, synopsis);
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

        Display.createBoard("Update Available Entries", entries);

        String media = Input.getStrInput("Title");
        int year = Input.getIntInput("Release Year");

        MediaEntry entry = vault.getEntry(media, year);

        if (entry == null) {
            System.out.println("Entry not found.");
            Input.holdScreen("Press ENTER to exit this view.");
            return;
        }

        Display.createBoard("Status", List.of(
            "[P] - Planned",
            "[I] - In-progress",
            "[C] - Completed"
        ));

        switch (Input.getStrInput("Status", "P", "I", "C")) {
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

        String media = Input.getStrInput("Title");
        int year = Input.getIntInput("Release Year");

        MediaEntry entry = vault.getEntry(media, year);

        if (entry == null) {
            System.out.println("Entry not found or status not complete.");
            return;
        }

        entry.setRating(Input.getFloatInput("Rating", 0.0f, 5.0f));
        entry.setReview(Input.getStrInput("Review"));
    }


    /* FILTERS */

    private static MediaType filterByMediaType()
    {
        Display.createBoard("Media Type", List.of(
            "[A] - Anime",
            "[N] - Novel",
            "[V] - Video Game"
        ));

        MediaType media = null;

        switch (Input.getStrInput("Media Type", "A", "N", "V")) {
            case "A":
                media = MediaType.ANIME;
                break;

            case "N":
                media = MediaType.NOVEL;
                break;

            case "V":
                media = MediaType.VIDEOGAME;
                break;
        }

        return media;
    }

    private static ArrayList<Genre> filterByGenre()
    {
        ArrayList<String> validIds = new ArrayList<>();

        Display.displayGenres();

        ArrayList<Genre> genre = new ArrayList<Genre>();

        for (String choice : Input.getStrInput("Genre").split("[,\\.\\s]+")) {
            if (validIds.contains(choice))
                genre.add(Genre.fromId(Integer.parseInt(choice)));
        }

        if (genre.size() == 0)
            return null;

        return genre;
    }

    private static Status filterByStatus()
    {
        Display.createBoard("Status", List.of(
            "[P] - Planned",
            "[I] - In-progress",
            "[C] - Completed"
        ));

        Status status = null;

        switch (Input.getStrInput("Status", "P", "I", "C")) {
            case "P":
                status = Status.PLANNED;
                break;

            case "I":
                status = Status.IN_PROGRESS;
                break;

            case "C":
                status = Status.COMPLETED;
                break;
        }

        return status;
    }

    public static void showEntries(MediaVault vault)
    {

        Display.createBoard("Full Library Display", null);
        String yesOrNo = Input.getStrInput(
            "Do you want to filter entries? (Y/N)",
            "Y", "N"
        );

        MediaType media = null;
        int year = 0;
        ArrayList<Genre> genre = new ArrayList<>();
        Status status = null;

        if(yesOrNo.equals("Y")) {

            Display.createBoard("Filter entries by ...", List.of(
                "[1] Media type",
                "[2] Year",
                "[3] Genre",
                "[4] Status"
            ));

            String[] filters = Input.getStrInput(
                "Filters (space/coma separated)"
            ).split("[,\\.\\s]+");

            for (String filter : filters) {
                switch (filter) {
                    case "1": { filterByMediaType(); } break;
                    case "2": { year = Input.getIntInput("Year"); } break;
                    case "3": { genre = filterByGenre(); } break;
                    case "4": { status = filterByStatus(); } break;
                }
            }
        }

        Display.showEntries(vault.getEntries(null, year, media, status, genre));

        Input.holdScreen("Press ENTER to exit this view.");
    }
}
