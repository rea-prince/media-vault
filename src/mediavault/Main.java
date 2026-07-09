package mediavault;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mediavault.models.MediaVault;
import mediavault.tui.*;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        MediaVault vault = null;

        /* TEMPORARY FROM HERE */

        System.out.println(">> Loading vault.");

        if (args.length > 0) {
            FileInputStream fileIn = new FileInputStream(args[0]);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            vault = (MediaVault) in.readObject();
            in.close();
            fileIn.close();
        } else {
            vault = new MediaVault();
        }

        /* TEMPORARY TO HERE */

        Interaction.mainEntry(vault);

        /* TEMPORARY FROM HERE */

        // this is only so we can save data for the demo
        // the actual one has to be in txt file
        FileOutputStream fileOut = new FileOutputStream("data/Vault.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(vault);
        out.close();
        fileOut.close();

        /* TEMPORARY TO HERE */

        System.out.println(">> Vault successfully saved.");
    }
}
