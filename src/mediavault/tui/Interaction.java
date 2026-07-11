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
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Executes user menu choices until option 0 is selected to exit.
     * </p>
     * @param vault Container of media entries.
     * @return void
     */
    public static void mainEntry(MediaVault vault)
    {
        int option;
        do {
            if (vault == null) {
                System.out.println("ERROR: Could not load vault.");
            }

            if (vault.getTotal() == 0) {
                Display.createBoard("Media Vault", List.of(
                    "[1] Add a new entry",
                    "[0] Exit"
                ));
                option = Input.getIntInput(
                    "Choose what to do",
                    0, 1
                );
            } else {
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
            }

            switch(option) {
                case 1: { promptAdd(vault); } break;
                case 2: { promptAddAnimeEpisodes(vault); } break;
                case 3: { viewAnimeEpisodes(vault); } break;
                case 4: { promptDelete(vault); } break;
                case 5: { promptUpdate(vault); } break;
                case 6: { promptAssign(vault); } break;
                case 7: { showEntries(vault); } break;
                case 8: {
                    Display.summarize(vault);
                    Input.holdScreen("Press ENTER to exit this view.");
                } break;
            }
        } while (option != 0);

    }

    /**
     * Prompts the user to add a media entry and its details to the vault
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Creates a new Anime, Novel, or VideoGame entry and adds it to the vault.
     * </p>
     * @param vault Container of media entries.
     * return void
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


        if (vault.getEntry(title, release) != null) {
            System.out.println("ERROR: Entry already exists.");
            Input.holdScreen("Press ENTER to exit this view.");
            return;
        }

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
        Status entryStatus = Status.PLANNED;
        switch (status) {
           	case "P": { entryStatus = Status.PLANNED; } break;
            case "I": { entryStatus = Status.IN_PROGRESS; } break;
            case "C": { entryStatus = Status.COMPLETED; } break;
        }

        MediaEntry entry = null;

        switch (entryType) {
            case "A": {
                studio = Input.getStrInput("Studio");
                alternative = Input.getStrInput("Alternative Title");
                entry = new Anime(release, title, synopsis, genres, alternative, studio, entryStatus);
            } break;
            case "N": {
                author = Input.getStrInput("Author");
                publisher = Input.getStrInput("Publisher");
                chapters = Input.getIntInput("Chapters"); // TO DO: Add safety
                entry = new Novel(release, title, synopsis, genres, publisher, author, entryStatus, chapters);
            } break;
            case "V": {
                studio = Input.getStrInput("Studio");
                publisher = Input.getStrInput("Publisher");
                entry = new VideoGame(release, title, synopsis, genres, publisher, studio, entryStatus);
            } break;
        }

        vault.addEntry(entry);
    }

    /**
     * Prompts the user to delete a media entry of their choice
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Removes the chosen entry if found; otherwise prints an error message.
     * </p>
     * @param vault Container of media entries.
     * @return void
     */
    public static void promptDelete(MediaVault vault)
    {
        Display.createBoard("Delete", null);
        Display.showTitles(vault.getAll());

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
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Appends a new episode to the chosen anime if the anime exists.
     * </p>
     * @param vault Container of media entries.
     * @return void
     */
    public static void promptAddAnimeEpisodes(MediaVault vault)
    {
        /* print */

        Display.createBoard("Add Anime Episode", null);
        Display.showTitles(vault.getEntries(null, 0, MediaType.ANIME, null, null));

        /* option */

        String anime = Input.getStrInput("Title");
        int releaseYear = Input.getIntInput("Release Year");
        Anime chosenAnime = (Anime) vault.getEntry(anime, releaseYear);

        if (chosenAnime == null) {
            System.out.println("Anime not found.");
            return;
        }

        Display.createBoard("--- Episode Details", null);

        // TO DO: Make this continuous

        String title = Input.getStrInput("Title");
        int release = Input.getIntInput("Release Year");
        String synopsis = Input.getStrInput("Synopsis");
        chosenAnime.addEpisode(release, title, synopsis);
    }

    /**
     * Allows the user to view episodes of their anime of choice
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Prints the episode list for the chosen anime or prints an error message.
     * </p>
     * @param vault Container of media entries
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
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Changes the entry's status to Planned, In-progress, or Completed if found.
     * </p>
     * @param vault Container of media entries
     * @return void
     */
    public static void promptUpdate(MediaVault vault)
    {
        Display.createBoard("Update Available Entries", null);
        Display.showTitles(vault.getAll());

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
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Updates the entry's rating and review fields if it is found and completed.
     * </p>
     * @param vault Container of media entries
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

        entry.setRating(Input.getFloatInput("Rating", 0.0f, 10.0f));
        entry.setReview(Input.getStrInput("Review"));
    }


    /* FILTERS */


    /**
     * Filters media entries by media type (anime, novel, video game)
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the selected MediaType choice, or null if unmapped.
     * </p>
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
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns a list of selected Genre values, or null if none were chosen.
     * </p>
     * @return void
     */
    private static ArrayList<Genre> filterByGenre()
    {
        ArrayList<Genre> validGenres = new ArrayList<>(List.of(Genre.values()));

        Display.displayGenres();

        ArrayList<Genre> genre = new ArrayList<Genre>();

        for (String choice : Input.getStrInput("Genre").split("[,\\.\\s]+")) {
            Genre genreChoice = Genre.fromId(Integer.parseInt(choice));

            if (validGenres.contains(genreChoice) && genreChoice != Genre.INVALID)
                genre.add(Genre.fromId(Integer.parseInt(choice)));
        }

        if (genre.size() == 0)
            return null;

        return genre;
    }

    /**
     * Filters media entries by status
     * <p>
     * <b>Precondition:</b> None.<br>
     * <b>Postcondition:</b> Returns the selected Status choice, or null if unmapped.
     * </p>
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
     * <p>
     * <b>Precondition:</b> vault must not be null.<br>
     * <b>Postcondition:</b> Displays all matching entries to standard output based on user filters.
     * </p>
     * @param vault List of media entries
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
        ArrayList<Genre> genre = null;
        Status status = null;

        ArrayList<String> options = new ArrayList<>(List.of("1", "2", "3", "4"));

        if(yesOrNo.equalsIgnoreCase("Y")) {

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
                if (!options.contains(filter)) {
                    System.out.println("Skipping invalid option: " + filter);
                    continue;
                }
                switch (filter) {
                    case "1": { media = filterByMediaType(); } break;
                    case "2": { year = Input.getIntInput("Year"); } break;
                    case "3": { genre = filterByGenre(); } break;
                    case "4": { status = filterByStatus(); } break;
                }
            }
        }

        for (MediaEntry entry : vault.getEntries(null, year, media, status, genre))
            Display.displayEntryDetails(entry);

        Input.holdScreen("Press ENTER to exit this view.");
    }
}
