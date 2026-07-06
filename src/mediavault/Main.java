package mediavault;

import mediavault.models.MediaVault;
import mediavault.tui.*;

public class Main
{
    public static void main(String[] args)
    {
        MediaVault vault = new MediaVault();

        Interaction.mainEntry(vault);
    }
}
