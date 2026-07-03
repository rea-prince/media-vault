package mediavault.tui;

import java.util.ArrayList;
import java.util.Scanner;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;
import mediavault.models.MediaVault;

public class Display
{
    MediaVault vault = new MediaVault();

    public void mainMenu()
    {
        System.out.println("Media Vault");
        System.out.println("[A] Add a new entry");
        System.out.println("[U] Update an entry");
        System.out.println("[R] Rate and review an entry");
        System.out.println("[E] Display the entire library");
        System.out.println("[S] Summarize the library");
    }

    public void addEntry(MediaVault vault) 
    {
        Input.promptAdd(vault);
    }

    public void updateEntry(MediaVault vault) 
    {
        Input.promptUpdate(vault);
    }

    public void rateEntry(MediaVault vault) 
    {
        Input.promptAssign(vault);
    }

    public void showEntries(MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************** Full library display **************");

        System.out.println("Do you want to filter entries? Y/N");
        String yesOrNo = scanner.nextLine();
        while (yesOrNo != "Y" && yesOrNo != "N")
        {
            System.out.print("Invalid input, please try again. ");
            yesOrNo = scanner.nextLine();
        }

        MediaType media = null;
        int year = 0;
        ArrayList<Genre> genres = null;
        Status status = null;

        if(yesOrNo == "N")
        {
            System.out.println("Filter entries by ...");
            System.out.println("[1] Media type");
            System.out.println("[2] Year");
            System.out.println("[3] Genre");
            System.out.println("[4] Status");
            System.out.println("Put spaces between each number (e.g. 1 2 3 4)");
            do {
                int filter = scanner.nextInt();
                if (filter == 1)
                {
                    System.out.println("[A] - Anime");
                    System.out.println("[N] - Novel");
                    System.out.println("[V] - Video Game");
                    System.out.print("Media type: ");
                    String type = scanner.nextLine();
                    while(type != "A" && type != "N" && type != "V")
                    {
                        System.out.println("Invalid option, please try again.");
                        System.out.print("Media type: ");
                        type = scanner.nextLine();
                    }
                    if(type == "A")
                        media = MediaType.ANIME;
                    else if(type == "N")
                        media = MediaType.NOVEL;
                    else if(type == "V")
                        media = MediaType.VIDEOGAME;
                }
                else if (filter == 2)
                {

                }
            } while (scanner.hasNextInt());
        }
        
        for (int a = 0; a < vault.getEntries(null, year, media, status, genres).size(); a++)
        {
            System.out.println(vault.getEntries(null, year, media, status, genres).get(a).getDetails().getTitle());
            System.out.print("\nView next entry? Press 'B' to go back, 'N' to proceed, or 'X' to exit. ");
        }
        
        scanner.close();
    }

    public void summarize(MediaVault vault)
    {

    }
}
