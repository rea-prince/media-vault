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

        Display.createBoard("Entry Details", List.of());
        title = Input.getStrInput("Title");

        release = Integer.parseInt(Input.getStrInput("Release Year"));
        synopsis = Input.getStrInput("Synopsis");


        /* GENRES */

        ArrayList<String> genreList = new ArrayList<String>();
        ArrayList<String> validIds = new ArrayList<String>();
        for (Genre g : Genre.values()) {
            if (g != Genre.INVALID) {
                genreList.add(String.format("[%d] - %s", g.getId(), g.name()));
                validIds.add(String.valueOf(g.getId()));
            }
        }

        // TO DO: update this

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

        String media = Input.getStrInput("Anime");
        MediaEntry chosenAnime = vault.getEntry(media, 0);

        if (chosenAnime == null) {
            System.out.println("Anime not found.");
            return;
        }

        Display.createBoard("--- Episode Details", List.of());

        String title = Input.getStrInput("Title");
        int release =Input.getIntInput("Release Year");
        String synopsis = Input.getStrInput("Synopsis");

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

        String media = Input.getStrInput("Title");
        int year = Input.getIntInput("Release Year");

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

        entry.setRating(Input.getFloatInput("Rating"));
        entry.setReview(Input.getStrInput("Review"));
    }
}
