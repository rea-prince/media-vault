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
	private static String getInput(String prompt, String... valid) {
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

    public static void promptAdd (MediaVault vault)
    {
        System.out.println("======================= Add =======================");
	    System.out.println("[A] - Anime");
	    System.out.println("[N] - Novel");
	    System.out.println("[V] - Video Game");
        String entryType = getInput("Media type", "A", "N", "V");

        System.out.println("Entry details");

        String title = getInput("Title");

        String alternative = null, publisher = null, author = null, studio = null;
        int chapters = 0;

        int release = Integer.parseInt(getInput("Release Year"));

        String synopsis = getInput("Synopsis");

        ArrayList<Genre> genreList = new ArrayList<>();


        /* GENRES */

        System.out.println("--- Genres Options");

        String[] validIds = new String[Genre.values().length - 1];
        int index = 0;
        for (Genre g : Genre.values()) {
            if (g != Genre.INVALID) {
                System.out.println("[" + g.getId() + "] " + g.name());
                validIds[index++] = String.valueOf(g.getId());
            }
        }
        String choice = getInput("Genre", validIds);

        genreList.add(Genre.fromId(Integer.parseInt(choice)));


        /* STATUS */

        System.out.println("--- Status Options");
        System.out.println("[P] - Planned");
        System.out.println("[I] - In-progress");
        System.out.println("[C] - Completed");
        System.out.print("Type according to letters above: ");
        String status = getInput("Type according to letters above", "P", "I", "C");

        MediaEntry entry = null;

        switch (entryType) {
	       	case "A":
	      		studio = getInput("Studio");
				publisher = getInput("Publisher");
				entry = new Anime(release, title, synopsis, genreList, alternative, studio, null);
				break;
			case "N":
				author = getInput("Author");
				publisher = getInput("Publisher");
				entry = new Novel(release, title, synopsis, genreList, publisher, author, null, chapters);
				break;
			case "V":
				studio = getInput("Studio");
				publisher = getInput("Publisher");
				entry = new VideoGame(release, title, synopsis, genreList, publisher, studio, null);
				break;
        }

        if (entry != null) {
            switch (status) {
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

        vault.addEntry(entry);
    }

    public static void promptDelete (MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===================== Delete =====================");
        ArrayList<MediaEntry> mediaList = vault.getEntries(null, 0, null, null, null);
        for (MediaEntry entry : mediaList)
            System.out.println(entry.getDetails().getTitle());
        System.out.print("Enter entry to delete: ");
        String media = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        if (vault.getEntry(media, year) != null)
            vault.removeEntry(media, year);
        else
            System.out.println("Entry not found.");

        scanner.close();
    }

    public static void promptAddAnimeEpisodes (MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=============== Add Anime Episodes ***************");
        ArrayList<MediaEntry> animeList = vault.getEntries(null, 0, MediaType.ANIME, null, null);
        for (MediaEntry anime : animeList)
            System.out.println(anime);
        System.out.print("Choose anime: ");
        String media = scanner.nextLine();
        MediaEntry chosenAnime = vault.getEntry(media, 0);

        System.out.println("\nEpisode details");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Release year: ");
        int release = scanner.nextInt();

        System.out.print("Synopsis: ");
        String synopsis = scanner.nextLine();

        Anime anime = new Anime(chosenAnime.getDetails().getYear(), chosenAnime.getDetails().getTitle(),
                                chosenAnime.getDetails().getSynopsis(), chosenAnime.getGenres(),
                                null, null, chosenAnime.getStatus());
        anime.addEpisode(release, title, synopsis);

        scanner.close();
    }

    public static void promptUpdate (MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===================== Update =====================");
        ArrayList<MediaEntry> mediaList = vault.getEntries(null, 0, null, null, null);
        for (MediaEntry entry : mediaList)
            System.out.println(entry.getDetails().getTitle());
        System.out.print("Choose which entry to change the status of: ");
        String media = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        if (vault.getEntry(media, year) != null)
        {
            System.out.println("Status:");
            System.out.println("[P] - Planned");
            System.out.println("[I] - In-progress");
            System.out.println("[C] - Completed");
            System.out.print("Type according to letters above: ");
            String changeStatus = scanner.nextLine().toUpperCase();
            while(!(changeStatus.equals("P") || changeStatus.equals("I") || changeStatus.equals("C")))
            {
                System.out.print("Invalid option, please try again: ");
                changeStatus = scanner.nextLine().toUpperCase();
            }
            if (changeStatus.equals("P"))
                vault.getEntry(media, year).setStatus(Status.PLANNED);
            else if (changeStatus.equals("I"))
                vault.getEntry(media, year).setStatus(Status.IN_PROGRESS);
            else if (changeStatus.equals("C"))
                vault.getEntry(media, year).setStatus(Status.COMPLETED);
        }
        else
            System.out.println("Entry not found.");

        scanner.close();
    }

    public static void promptAssign (MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("================= Rate and Review =================");
        ArrayList<MediaEntry> mediaList = vault.getEntries(null, 0, null, Status.COMPLETED, null);
        for (MediaEntry entry : mediaList)
            System.out.println(entry.getDetails().getTitle());
        System.out.print("Choose completed entry: ");
        String media = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        if (vault.getEntry(media, year) != null)
        {
            System.out.println("Rating: ");
            float changeRating = scanner.nextFloat();
            vault.getEntry(media, year).setRating(changeRating);
            System.out.println("Review: ");
            String changeReview = scanner.nextLine();
            vault.getEntry(media, year).setReview(changeReview);
        }
        else
            System.out.println("Entry not found or status not complete.");

        scanner.close();
    }
}
