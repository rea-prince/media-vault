package mediavault;
import java.util.Scanner;

import mediavault.models.MediaVault;
import mediavault.tui.*;

public class Main
{
    public static void main(String[] args)
    {
        MediaVault vault = new MediaVault();
        Scanner scanner = new Scanner(System.in);

        Display.mainMenu();
        System.out.print("Choose what to do: ");
        String option = scanner.nextLine().toUpperCase();
        while (!option.equals("X"))
        {
            while (!(option.equals("A") || option.equals("B") || option.equals("D") || option.equals("U") ||
                     option.equals("R") || option.equals("E") || option.equals("S")))
            {
                System.out.print("Invalid option, please try again: ");
                option = scanner.nextLine();
            }

            if(option.equals("A"))
                Display.addEntry(vault);

            else
            {
                if(vault != null)
                {
                    if(option.equals("B"))
                        Display.addAnimeEpisodes(vault);

                    else if(option.equals("D"))
                        Display.deleteEntry(vault);

                    else if(option.equals("U"))
                        Display.updateEntry(vault);

                    else if(option.equals("R"))
                        Display.rateEntry(vault);

                    else if(option.equals("E"))
                        Display.showEntries(vault);

                    else if(option.equals("S"))
                        Display.summarize(vault);
                }
                else
                    System.out.println("No existing items to edit.");
            }

            Display.mainMenu();
            System.out.print("Choose what to do: ");
            option = scanner.nextLine();
        }

        scanner.close();
    }
}
