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
import java.util.Scanner;

public class Input
{
    public static void promptAdd (MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*********************** Add ***********************");
        System.out.println("[A] - Anime");
        System.out.println("[N] - Novel");
        System.out.println("[V] - Video Game");
        System.out.print("Media type: ");
        String entryType = scanner.nextLine();
        while(!entryType.equals("A") && !entryType.equals("N") && !entryType.equals("V"))
        {
            System.out.println("Invalid option, please try again.");
            System.out.print("Media type: ");
            entryType = scanner.nextLine();
        }

        System.out.println("\nEntry details");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        String alternative = null, publisher = null, author = null, studio = null;
        int chapters = 0;

        if (entryType.equals("A"))
        {
            System.out.print("Alternative title: ");
            alternative = scanner.nextLine();
        }
        else    // if(entryType.equals("N") || entryType.equals("V"))
        {
            System.out.print("Publisher: ");
            publisher = scanner.nextLine();
        }

        if (entryType.equals("N"))
        {
            System.out.print("Author: ");
            author = scanner.nextLine();
            System.out.print("Chapter count: ");
            chapters = scanner.nextInt();
        }
        else    // if (entryType.equals("A") || entryType.equals("V"))
        {
            System.out.print("Studio: ");
            studio = scanner.nextLine();
        }

        System.out.print("Release year: ");
        int release = scanner.nextInt();

        System.out.print("Synopsis: ");
        String synopsis = scanner.nextLine();

        ArrayList<Genre> genreList = new ArrayList<>();
        System.out.println("Genres:");
        for (int i = 1; i <= 16; i++)
            System.out.println("[i] " + Genre.values());
        System.out.println("Choose genres based on their numbers: ");
        System.out.println("Put spaces between each number (e.g. 1 2 3 4)");
        do {
            int genre = scanner.nextInt();
            if (genre == 1)
                genreList.add(Genre.ACTION);
            else if (genre == 2)
                genreList.add(Genre.ADVENTURE);
            else if (genre == 3)
                genreList.add(Genre.COMEDY);
            else if (genre == 4)
                genreList.add(Genre.CRIME);
            else if (genre == 5)
                genreList.add(Genre.DOCUMENTARY);
            else if (genre == 6)
                genreList.add(Genre.DRAMA);
            else if (genre == 7)
                genreList.add(Genre.FANTASY);
            else if (genre == 8)
                genreList.add(Genre.HISTORICAL_FICTION);
            else if (genre == 9)
                genreList.add(Genre.HORROR);
            else if (genre == 10)
                genreList.add(Genre.MUSIC);
            else if (genre == 11)
                genreList.add(Genre.MYSTERY);
            else if (genre == 12)
                genreList.add(Genre.PSYCHOLOGICAL);
            else if (genre == 13)
                genreList.add(Genre.ROMANCE);
            else if (genre == 14)
                genreList.add(Genre.SCIENCE_FICTION);
            else if (genre == 15)
                genreList.add(Genre.SPORTS);
            else if (genre == 16)
                genreList.add(Genre.THRILLER);
            else
                System.out.println(genre + " is invalid.");
        } while (scanner.hasNextInt());

        System.out.println("Status:");
        System.out.println("[P] - Planned");
        System.out.println("[I] - In-progress");
        System.out.println("[C] - Completed");
        System.out.print("Type according to letters above: ");
        String status = scanner.nextLine();
        while (!status.equals("P") && !status.equals("I") && !status.equals("C"))
        {
            System.out.print("Invalid option, please try again: ");
            status = scanner.nextLine();
        }

        if (entryType.equals("A"))
        {
            Anime anime = new Anime(release, title, synopsis, genreList, alternative, studio, null);
            if (status.equals("P"))
                anime.setStatus(Status.PLANNED);
            else if (status.equals("I"))
                anime.setStatus(Status.IN_PROGRESS);
            else if (status.equals("C"))
                anime.setStatus(Status.COMPLETED);
            vault.addEntry(anime);
        }

        else if (entryType.equals("N"))
        {
            Novel novel = new Novel(release, title, synopsis, genreList, publisher, author, null, chapters);
            if (status.equals("P"))
                novel.setStatus(Status.PLANNED);
            else if (status.equals("I"))
                novel.setStatus(Status.IN_PROGRESS);
            else if (status.equals("C"))
                novel.setStatus(Status.COMPLETED);
            vault.addEntry(novel);
        }

        else if (entryType.equals("V"))
        {
            VideoGame videoGame = new VideoGame(release, title, synopsis, genreList, publisher, studio, null);
            if (status.equals("P"))
                videoGame.setStatus(Status.PLANNED);
            else if (status.equals("I"))
                videoGame.setStatus(Status.IN_PROGRESS);
            else if (status.equals("C"))
                videoGame.setStatus(Status.COMPLETED);
            vault.addEntry(videoGame);
        }

        scanner.close();
    }

    public static void promptDelete (MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("********************* Delete *********************");
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

        System.out.println("*************** Add Anime Episodes ***************");
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

        System.out.println("********************* Update *********************");
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
            String changeStatus = scanner.nextLine();
            while(!(changeStatus.equals("P") || changeStatus.equals("I") || changeStatus.equals("C")))
            {
                System.out.print("Invalid option, please try again: ");
                changeStatus = scanner.nextLine();
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

        System.out.println("***************** Rate and Review *****************");
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
