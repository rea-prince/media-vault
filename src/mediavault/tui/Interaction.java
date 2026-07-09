package mediavault.tui;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

import mediavault.models.Anime;
import mediavault.models.Details;
import mediavault.models.Novel;
import mediavault.models.VideoGame;
import mediavault.models.MediaEntry;
import mediavault.models.MediaVault;

import java.util.ArrayList;
import java.util.List;

abstract public class Interaction
{
    /**
     * Displays the main menu and triggers the user's choice of feature
     * @param vault Container of media entries
     *
     * @return void
     */
    public static void mainEntry(MediaVault vault) {
        int option;
        do {
            if (vault == null) {
                System.out.println("ERROR: Could not load vault.");
            }

            Display.createBoard("Media Vault", List.of(
                "[1] Add a new entry",
                "[2] Add anime episodes",
                "[3] View anime episodes",
                "[4] Delete an entry",
                "[5] Update an entry",
                "[6] Rate and review an entry",
                "[7] Display the entire library",
                "[8] Summarize the library",
                "[0] Exit"
            ));

            option = Input.getIntInput(
                "Choose what to do",
                0, 8
            );

            switch(option) {
                case 1: { promptAdd(vault); } break;
                case 2: { promptAddAnimeEpisodes(vault); } break;
                case 3: { viewAnimeEpisodes(vault); } break;
                case 4: { promptDelete(vault); } break;
                case 5: { promptUpdate(vault); } break;
                case 6: { promptAssign(vault); } break;
                case 7: { showEntries(vault); } break;
                case 8: { Display.summarize(vault); } break;
            }
        } while (option != 0);

    }

    /**
     * Prompts the user to add a media entry and its details to the vault
     * @param vault Container of media entries
     *
     * @return void
     */
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
               	case "P": { entry.setStatus(Status.PLANNED); } break;
                case "I": { entry.setStatus(Status.IN_PROGRESS); } break;
                case "C": { entry.setStatus(Status.COMPLETED); } break;
            }
        }

        vault.addEntry(entry);
    }

    /**
     * Prompts the user to delete a media entry of their choice
     * @param vault Container of media entries
     *
     * @return void
     */
    public static void promptDelete(MediaVault vault)
    {
        Display.createBoard("Delete", null);
        Display.showTitles(vault);

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

    /**
     * Prompts the user to add episodes and its details to their anime of choice
     * @param vault Container of media entries
     *
     * @return void
     */
    public static void promptAddAnimeEpisodes(MediaVault vault)
    {

        /* print */

        Display.createBoard("Add Anime Episode", null);
        Display.showTitles(vault);

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

    /**
     * Allows the user to view episodes of their anime of choice
     * @param vault Container of media entries
     *
     * @return void
     */
    public static void viewAnimeEpisodes(MediaVault vault)
    {
        ArrayList<String> entries = new ArrayList<>();

        for (MediaEntry anime : vault.getEntries(null, 0, MediaType.ANIME, null, null)) {
            entries.add(String.format(
                "%s (%d)",
                anime.getDetails().getTitle(), anime.getDetails().getYear()
            ));
        }

        Display.createBoard("View Anime Episodes", entries);

        String media = Input.getStrInput("Title");
        int year = Input.getIntInput("Release Year");

        MediaEntry entry = vault.getEntry(media, year);
        Anime anime;

        if (entry instanceof Anime) {
            anime = (Anime) vault.getEntry(media, year);
        } else if (entry == null) {
            System.out.println("Entry not found.");
            Input.holdScreen("Press ENTER to exit this view.");
            return;
        } else {
            System.out.println("Entry is not an anime.");
            Input.holdScreen("Press ENTER to exit this view.");
            return;
        }

        int i = 0;
        for (Details episode : anime.getAnimeEpisodes())
        {
            i++;
            System.out.print("Episode " + i + " - ");
            Display.displayAnimeEpisode(episode);
        }

        Input.holdScreen("Press ENTER to exit this view.");
    }

    /**
     * Allows the user to change an entry's status
     * @param vault Container of media entries
     *
     * @return void
     */
    public static void promptUpdate(MediaVault vault)
    {
        Display.createBoard("Update Available Entries", null);
        Display.showTitles(vault);

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
            case "P": { entry.setStatus(Status.PLANNED);  }break;
            case "I": { entry.setStatus(Status.IN_PROGRESS); } break;
            case "C": { entry.setStatus(Status.COMPLETED); } break;
        }
    }

    /**
     * Allows the user to rate and review a completed media entry
     * @param vault Container of media entries
     *
     * @return void
     */
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
            Input.holdScreen("Press ENTER to exit this view.");
            return;
        }

        if (entry.getStatus() != Status.COMPLETED) {
            System.out.println("Entry cannot be rated unless it is completed.");
            Input.holdScreen("Press ENTER to exit this view.");
            return;
        }

        entry.setRating(Input.getFloatInput("Rating", 0.0f, 5.0f));
        entry.setReview(Input.getStrInput("Review"));
    }


    /* FILTERS */


    /**
     * Filters media entries by media type (anime, novel, video game)
     *
     * @return void
     */
    private static MediaType filterByMediaType()
    {
        Display.createBoard("Media Type", List.of(
            "[A] - Anime",
            "[N] - Novel",
            "[V] - Video Game"
        ));

        MediaType media = null;

        switch (Input.getStrInput("Media Type", "A", "N", "V")) {
            case "A": { media = MediaType.ANIME; } break;
            case "N": { media = MediaType.NOVEL; } break;
            case "V": { media = MediaType.VIDEOGAME; } break;
        }

        return media;
    }

    /**
     * Filters media entries by genres
     *
     * @return void
     */
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

    /**
     * Filters media entries by status
     *
     * @return void
     */
    private static Status filterByStatus()
    {
        Display.createBoard("Status", List.of(
            "[P] - Planned",
            "[I] - In-progress",
            "[C] - Completed"
        ));

        Status status = null;

        switch (Input.getStrInput("Status", "P", "I", "C")) {
            case "P": { status = Status.PLANNED; } break;
            case "I": { status = Status.IN_PROGRESS; } break;
            case "C": { status = Status.COMPLETED; } break;
        }

        return status;
    }

    /**
     * Displays filtered media entries
     * @param vault List of media entries
     *
     * @return void
     */
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
