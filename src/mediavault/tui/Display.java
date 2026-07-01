package mediavault.tui;

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

    public void addEntry(MediaVault vault) {

    }

    public void updateEntry(MediaVault vault) {

    }

    public void rateEntry(MediaVault vault) {

    }

    public void showEntries(MediaVault vault)
    {

    }

    public void summarize(MediaVault vault)
    {

    }
}
