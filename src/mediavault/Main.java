package mediavault;
import java.util.Scanner;

import mediavault.tui.*;


public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        Display.mainMenu();

        scanner.close();
    }
}
