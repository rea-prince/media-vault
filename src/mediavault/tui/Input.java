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
    Scanner scanner = new Scanner(System.in);

    public void promptAdd (MediaVault vault)
    {
        System.out.println("*********************** Add ***********************");
        System.out.println("[A] - Anime");
        System.out.println("[N] - Novel");
        System.out.println("[V] - Video Game");
        System.out.println("Media type: ");
        String entryType = scanner.nextLine();
        while(entryType != "A" || entryType != "N" || entryType != "V")
        {
            System.out.println("Invalid option, please try again.");
            System.out.print("Media type: ");
            entryType = scanner.nextLine();
        }

        System.out.println("\nEntry details");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        String alternative, publisher, author, studio;
        int chapters;

        if(entryType == "A")
        {
            System.out.print("Alternative title: ");
            alternative = scanner.nextLine();
        }
        else    // if(entryType == "N" || entryType == "V")
        {
            System.out.print("Publisher: ");
            publisher = scanner.nextLine();
        }

        if(entryType == "N")
        {
            System.out.print("Author: ");
            author = scanner.nextLine();
            System.out.print("Chapter count: ");
            chapters = scanner.nextInt();
        }
        else    // if(entryType == "A" || entryType == "V")
        {
            System.out.print("Studio: ");
            studio = scanner.nextLine();
        }

        System.out.print("Release year: ");
        int release = scanner.nextInt();

        System.out.print("Synopsis: ");
        String synopsis = scanner.nextLine();

        ArrayList<Genre> genreList;
        System.out.println("Genres:");
        System.out.println("[1] ACTION");
        System.out.println("[2] ADVENTURE");
        System.out.println("[3] COMEDY");
        System.out.println("[4] CRIME");
        System.out.println("[5] DOCUMENTARY");
        System.out.println("[6] DRAMA");
        System.out.println("[7] FANTASY");
        System.out.println("[8] HISTORICAL_FICTION");
        System.out.println("[9] HORROR");
        System.out.println("[10] MUSIC");
        System.out.println("[11] MYSTERY");
        System.out.println("[12] PSYCHOLOGICAL");
        System.out.println("[13] ROMANCE");
        System.out.println("[14] SCIENCE_FICTION");
        System.out.println("[15] SPORTS");
        System.out.println("[16] THRILLER");
        System.out.println("Choose genres based on their numbers: ");
        System.out.println("Put spaces between each number (e.g. 1 2 3 4)");
        do {
            int genre = scanner.nextInt();
            if(genre == 1)
                genreList.add(Genre.ACTION);
            else if(genre == 2)
                genreList.add(Genre.ADVENTURE);
            else if(genre == 3)
                genreList.add(Genre.COMEDY);
            else if(genre == 4)
                genreList.add(Genre.CRIME);
            else if(genre == 5)
                genreList.add(Genre.DOCUMENTARY);
            else if(genre == 6)
                genreList.add(Genre.DRAMA);
            else if(genre == 7)
                genreList.add(Genre.FANTASY);
            else if(genre == 8)
                genreList.add(Genre.HISTORICAL_FICTION);
            else if(genre == 9)
                genreList.add(Genre.HORROR);
            else if(genre == 10)
                genreList.add(Genre.MUSIC);
            else if(genre == 11)
                genreList.add(Genre.MYSTERY);
            else if(genre == 12)
                genreList.add(Genre.PSYCHOLOGICAL);
            else if(genre == 13)
                genreList.add(Genre.ROMANCE);
            else if(genre == 14)
                genreList.add(Genre.SCIENCE_FICTION);
            else if(genre == 15)
                genreList.add(Genre.SPORTS);
            else if(genre == 16)
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
        while(status != "P" || status != "I" || status != "C")
        {
            System.out.print("Invalid option, please try again: ");
            status = scanner.nextLine();
        }

        if(entryType == "A")
        {
            Anime anime = new Anime(release, title, synopsis, genreList, alternative, studio, null);
            if(status == "P")
                anime.setStatus(Status.PLANNED);
            else if(status == "I")
                anime.setStatus(Status.IN_PROGRESS);
            else if(status == "C")
                anime.setStatus(Status.COMPLETED);
            vault.addEntry(anime);
        }

        else if(entryType == "N")
        {
            Novel novel = new Novel(release, title, synopsis, genreList, publisher, author, null, chapters);
            if(status == "P")
                novel.setStatus(Status.PLANNED);
            else if(status == "I")
                novel.setStatus(Status.IN_PROGRESS);
            else if(status == "C")
                novel.setStatus(Status.COMPLETED);
            vault.addEntry(novel);
        }

        else if(entryType == "V")
        {
            VideoGame videoGame = new VideoGame(release, title, synopsis, genreList, publisher, studio, null);
            if(status == "P")
                videoGame.setStatus(Status.PLANNED);
            else if(status == "I")
                videoGame.setStatus(Status.IN_PROGRESS);
            else if(status == "C")
                videoGame.setStatus(Status.COMPLETED);
            vault.addEntry(videoGame);
        }
    }

    public void promptUpdate (MediaVault vault)
    {
        System.out.println("********************* Update *********************");
        for(int a = 0; a < vault.getEntries(null, 0, null, null, null).size(); a++)
            System.out.println(vault.getEntries(null, 0, null, null, null).get(a));
        System.out.print("Choose which entry to change the status of: ");
        String media = scanner.nextLine();
        int b = 0;
        boolean isFound = false;
        while (isFound == false)
        {
            if(media == vault.getEntries(null, 0, null, null, null).get(b).getDetails().getTitle())
            {
                isFound = true;
                System.out.println("Status:");
                System.out.println("[P] - Planned");
                System.out.println("[I] - In-progress");
                System.out.println("[C] - Completed");
                System.out.print("Type according to letters above: ");
                String changeStatus = scanner.nextLine();
                while(changeStatus != "P" || changeStatus != "I" || changeStatus != "C")
                {
                    System.out.print("Invalid option, please try again: ");
                    changeStatus = scanner.nextLine();
                }
                if(changeStatus == "P")
                    vault.getEntries(null, 0, null, null, null).get(b).setStatus(Status.PLANNED);
                else if(changeStatus == "I")
                    vault.getEntries(null, 0, null, null, null).get(b).setStatus(Status.IN_PROGRESS);
                else if(changeStatus == "C")
                    vault.getEntries(null, 0, null, null, null).get(b).setStatus(Status.COMPLETED);
            }
            b++;
        }
        if (isFound == false)
            System.out.println("Entry not found.");
    }

    public void promptAssign (MediaVault vault)
    {
        System.out.println("***************** Rate and Review *****************");
        for(int a = 0; a < vault.getEntries(null, 0, null, Status.COMPLETED, null).size(); a++)
            System.out.println(vault.getEntries(null, 0, null, Status.COMPLETED, null).get(a));
        System.out.print("Choose completed entry: ");
        String media = scanner.nextLine();
        int b = 0;
        boolean isFound = false;
        while (isFound == false && b < vault.getEntries(null, 0, null, Status.COMPLETED, null).size())
        {
            if(media == vault.getEntries(null, 0, null, null, null).get(b).getDetails().getTitle())
            {
                isFound = true;
                System.out.println("Rating: ");
                float changeRating = scanner.nextFloat();
                vault.getEntries(null, 0, null, null, null).get(b).setRating(changeRating);
                System.out.println("Review: ");
                String changeReview = scanner.nextLine();
                vault.getEntries(null, 0, null, null, null).get(b).setReview(changeReview);
            }
            b++;
        }
        if (isFound == false)
            System.out.println("Entry not found or status not COMPLETED.");
    }
}
