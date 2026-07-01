package mediavault.tui;
import mediavault.enums.Status;
import mediavault.models.MediaEntry;
import mediavault.models.MediaVault;
import java.util.Scanner;

public class Input
{
    Scanner scanner = new Scanner(System.in);

    public void promptAdd (MediaVault vault)
    {
        System.out.println("*********************** Add ***********************");
        MediaEntry media;
        System.out.println("Entry details");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        media.getDetails().setTitle(title);
        System.out.print("Year: ");
        int year = scanner.nextInt();
        media.getDetails().setYear(year);
        System.out.print("Synopsis: ");
        String synopsis = scanner.nextLine();
        media.getDetails().setSynopsis(synopsis);
        vault.addEntry(media);
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
                System.out.println("P - Planned");
                System.out.println("I - In-progress");
                System.out.println("C - Completed");
                System.out.println("Type according to letters above");
                System.out.print("Status: ");
                String changeStatus = scanner.nextLine();
                do{
                    if(changeStatus == "P")
                        vault.getEntries(null, 0, null, null, null).get(b).setStatus(Status.PLANNED);
                    else if(changeStatus == "I")
                        vault.getEntries(null, 0, null, null, null).get(b).setStatus(Status.IN_PROGRESS);
                    else if(changeStatus == "C")
                        vault.getEntries(null, 0, null, null, null).get(b).setStatus(Status.COMPLETED);
                    else
                    {
                        System.out.println("Invalid option, please try again.");
                        System.out.print("Status: ");
                        changeStatus = scanner.nextLine();
                    }
                } while(changeStatus != "P" || changeStatus != "I" || changeStatus != "C");
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
