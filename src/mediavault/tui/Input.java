package mediavault.tui;
import mediavault.models.MediaVault;
import java.util.Scanner;
import java.util.ArrayList;

public class Input
{
    Scanner scanner = new Scanner(System.in);

    public void promptAdd (MediaVault vault)
    {
        System.out.println("Add media");
    }

    public void promptUpdate (MediaVault vault)
    {
        System.out.println("******************** Update ********************");
        System.out.print("Choose which entry to mark as complete: ");
        String media = scanner.nextLine();
        int i = 0;
        boolean isFound = false;
        while (isFound == false)
        {
            if(media == vault.get(i))
                isFound = true;
            i++;
        }
        if (isFound == true)
        {

        }
        else
            System.out.println("Entry already marked complete.");
    }

    public void promptAssign (MediaVault vault)
    {
        System.out.println("************ Rate and Review ************");
    }
}
