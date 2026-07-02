package mediavault.tui;
import mediavault.enums.MediaType;
import mediavault.enums.Status;
import mediavault.models.Anime;
import mediavault.models.MediaEntry;
import mediavault.models.MediaVault;
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
            vault.addEntry(Anime(release, title, synopsis, genres, alternative, studio, status));

        else if(entryType == "N")
            vault.addEntry(Novel(release, title, synopsis, genres, publisher, author, status, chapters));

        else if(entryType == "V")
            vault.addEntry(VideoGame(release, title, synopsis, genres, publisher, studio, status));
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
