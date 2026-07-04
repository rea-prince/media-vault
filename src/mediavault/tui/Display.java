package mediavault.tui;

import java.util.ArrayList;
import java.util.Scanner;

import mediavault.enums.Genre;
import mediavault.enums.MediaType;
import mediavault.enums.Status;

import mediavault.models.Anime;
import mediavault.models.MediaEntry;
import mediavault.models.Novel;
import mediavault.models.VideoGame;
import mediavault.models.MediaVault;

public class Display
{
    MediaVault vault = new MediaVault();

    public static void mainMenu()
    {
        System.out.println("Media Vault");
        System.out.println("[A] Add a new entry");
        System.out.println("[D] Delete an entry");
        System.out.println("[U] Update an entry");
        System.out.println("[R] Rate and review an entry");
        System.out.println("[E] Display the entire library");
        System.out.println("[S] Summarize the library");
    }

    public static void addEntry(MediaVault vault) 
    {
        Input.promptAdd(vault);
    }

    public static void deleteEntry(MediaVault vault) 
    {
        Input.promptDelete(vault);
    }

    public static void updateEntry(MediaVault vault) 
    {
        Input.promptUpdate(vault);
    }

    public static void rateEntry(MediaVault vault) 
    {
        Input.promptAssign(vault);
    }

    public static void showEntries(MediaVault vault)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************** Full library display **************");

        System.out.println("Do you want to filter entries? Y/N");
        String yesOrNo = scanner.nextLine();
        while (!yesOrNo.equals("Y") && !yesOrNo.equals("N"))
        {
            System.out.print("Invalid input, please try again. ");
            yesOrNo = scanner.nextLine();
        }

        MediaType media = null;
        int year = 0;
        ArrayList<Genre> genre = new ArrayList<>();
        Status status = null;

        if(yesOrNo.equals("N"))
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
                    while(!type.equals("A") && !type.equals("N") && !type.equals("V"))
                    {
                        System.out.println("Invalid option, please try again.");
                        System.out.print("Media type: ");
                        type = scanner.nextLine();
                    }
                    if(type.equals("A"))
                        media = MediaType.ANIME;
                    else if(type.equals("N"))
                        media = MediaType.NOVEL;
                    else if(type.equals("V"))
                        media = MediaType.VIDEOGAME;
                }
                else if (filter == 2)
                {
                    System.out.print("Year: ");
                    year = scanner.nextInt();
                }
                else if (filter == 3)
                {
                    for (int i = 1; i <= 16; i++)
                        System.out.println("[i] " + Genre.values());
                    System.out.print("Genre: ");
                    int genreNumber = scanner.nextInt();
                    while(!(genreNumber >= 1 && genreNumber <= 16))
                    {
                        System.out.println("Invalid option, please try again.");
                        System.out.print("Genre: ");
                        genreNumber = scanner.nextInt();
                    }
                    if (genreNumber == 1)
                        genre.add(Genre.ACTION);
                    else if (genreNumber == 2)
                        genre.add(Genre.ADVENTURE);
                    else if (genreNumber == 3)
                        genre.add(Genre.COMEDY);
                    else if (genreNumber == 4)
                        genre.add(Genre.CRIME);
                    else if (genreNumber == 5)
                        genre.add(Genre.DOCUMENTARY);
                    else if (genreNumber == 6)
                        genre.add(Genre.DRAMA);
                    else if (genreNumber == 7)
                        genre.add(Genre.FANTASY);
                    else if (genreNumber == 8)
                        genre.add(Genre.HISTORICAL_FICTION);
                    else if (genreNumber == 9)
                        genre.add(Genre.HORROR);
                    else if (genreNumber == 10)
                        genre.add(Genre.MUSIC);
                    else if (genreNumber == 11)
                        genre.add(Genre.MYSTERY);
                    else if (genreNumber == 12)
                        genre.add(Genre.PSYCHOLOGICAL);
                    else if (genreNumber == 13)
                        genre.add(Genre.ROMANCE);
                    else if (genreNumber == 14)
                        genre.add(Genre.SCIENCE_FICTION);
                    else if (genreNumber == 15)
                        genre.add(Genre.SPORTS);
                    else if (genreNumber == 16)
                        genre.add(Genre.THRILLER);
                }
                else if (filter == 4)
                {
                    System.out.println("[P] - Planned");
                    System.out.println("[I] - In-progress");
                    System.out.println("[C] - Completed");
                    System.out.print("Status: ");
                    String stat = scanner.nextLine();
                    while (!stat.equals("P") && !stat.equals("I") && !stat.equals("C"))
                    {
                        System.out.println("Invalid option, please try again.");
                        System.out.print("Status: ");
                        stat = scanner.nextLine();
                    }
                    if (stat.equals("P"))
                        status = Status.PLANNED;
                    else if (stat.equals("I"))
                        status = Status.IN_PROGRESS;
                    else if (stat.equals("C"))
                        status = Status.COMPLETED;
                }
            } while (scanner.hasNextInt());
        }
        
        ArrayList<MediaEntry> mediaList = vault.getEntries(null, year, media, status, genre);
        for (MediaEntry entry : mediaList)
        {
            System.out.println(entry.getDetails().getTitle());
            System.out.println("Release year: " + entry.getDetails().getYear());
            System.out.println("Synopsis: " + entry.getDetails().getSynopsis());
            System.out.print("Genres: ");
            ArrayList<Genre> genreList = entry.getGenres();
            for (Genre entryGenre : genreList)
                System.out.print(entryGenre + ", ");
            System.out.println("Status: " + entry.getStatus());
            
            if(entry instanceof Anime)
            {
                Anime anime = new Anime(entry.getDetails().getYear(), entry.getDetails().getTitle(), entry.getDetails().getSynopsis(), 
                                        entry.getGenres(), null, null, entry.getStatus());
                System.out.println("Alternate title: " + anime.getAlternativeTitle());
                System.out.println("Studio: " + anime.getStudio());
            }
            else if(entry instanceof Novel)
            {
                Novel novel = new Novel(entry.getDetails().getYear(), entry.getDetails().getTitle(), entry.getDetails().getSynopsis(), 
                                        entry.getGenres(), null, null, entry.getStatus(), 0);
                System.out.println("Author: " + novel.getAuthor());
                System.out.println("Publisher: " + novel.getPublisher());
                System.out.println("Number of chapters: " + novel.getChapters());
            }
            else if(entry instanceof VideoGame)
            {
                VideoGame videoGame = new VideoGame(entry.getDetails().getYear(), entry.getDetails().getTitle(), 
                                                    entry.getDetails().getSynopsis(), entry.getGenres(), 
                                                    null, null, entry.getStatus());
                System.out.println("Studio: " + videoGame.getStudio());
                System.out.println("Publisher: " + videoGame.getPublisher());
            }
            System.out.print("\nView next entry? Press 'B' to go back, 'N' to proceed, or 'X' to exit. ");
        }
        
        genre.remove(0);
        scanner.close();
    }

    public void summarize(MediaVault vault)
    {
        System.out.println("Total number of entries: " + vault.getTotalByAttributes(null, null, null));
        
        System.out.println("\nNumber of entries by media type: ");
        MediaType[] types = MediaType.values();
        for (MediaType type : types)
            System.out.println(type + ": " + vault.getTotalByAttributes(type, null, null));
        
        System.out.println("\nNumber of entries by genre: ");
        Genre[] genreList = Genre.values();
        for (Genre genre : genreList)
        {
            ArrayList<Genre> genreDisp = new ArrayList<>();
            genreDisp.add(genre);
            if (vault.getTotalByAttributes(null, null, genreDisp) > 0)
                System.out.println(genre + ": " + vault.getTotalByAttributes(null, null, genreDisp));
        }
        
        System.out.println("\nNumber of entries by status: ");
        Status[] statusList = Status.values();
        for (Status status : statusList)
            System.out.println(status + ": " + vault.getTotalByAttributes(null, status, null));

        float totalRating = 0;
        for (MediaEntry entry : vault.getEntries(null, 0, null, Status.COMPLETED, null))
            totalRating += vault.getEntry(entry.getDetails().getTitle(), 0).getRating();
        float averageRating = totalRating / vault.getTotalByAttributes(null, Status.COMPLETED, null);
        System.out.println("\nAverage rating: " + averageRating);
    }
}
