package test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import mediavault.enums.*;
import mediavault.models.*;
import mediavault.tui.*;

public class TestCase
{
    public static void main(String[] args) {

        /* MEDIA VAULT */


        System.out.println("\n\n=== TESTING MEDIA VAULT ===");
        MediaVault vault = new MediaVault();

        Anime testAnime = new Anime(2002, "Naruto", "Ninja stuff", new ArrayList<>(List.of(Genre.ACTION)), "N/A", "Studio Pierrot", Status.COMPLETED);
        Novel testNovel = new Novel(1997, "Harry Potter", "Wizard stuff", new ArrayList<>(List.of(Genre.FANTASY)), "Bloomsbury", "J.K. Rowling", Status.COMPLETED, 10);

        System.out.println("\n--- Testing addEntry ---");
        System.out.println(">>> TEST 1: Adding a completely new valid entry");
        vault.addEntry(testAnime);
        System.out.println("Vault total after addition: " + vault.getTotal());

        System.out.println("\n>>> TEST 2: Attempting to add an entry that already exists");
        try {
            vault.addEntry(testAnime);
            System.out.println("Result: Failed (Exception was not thrown)");
        } catch (IllegalArgumentException e) {
            System.out.println("Result: Success (Caught expected exception: " + e.getMessage() + ")");
        }

        System.out.println("\n>>> TEST 3: Adding a valid entry of a different MediaType");
        vault.addEntry(testNovel);
        System.out.println("Vault total after second addition: " + vault.getTotal());






        System.out.println("\n--- Testing getEntry ---");
        System.out.println(">>> TEST 1: Title and year of the same entry both exist");
        MediaEntry found1 = vault.getEntry("Naruto", 2002);
        System.out.println("Result: " + (found1 != null ? found1.getDetails().getTitle() : "null"));

        System.out.println("\n>>> TEST 2: The title exists, but the year does not match");
        MediaEntry found2 = vault.getEntry("Naruto", 2026);
        System.out.println("Result: " + (found2 == null ? "Success (returned null)" : "Failed"));

        System.out.println("\n>>> TEST 3: Neither the title nor the year exists");
        MediaEntry found3 = vault.getEntry("Bleach", 2004);
        System.out.println("Result: " + (found3 == null ? "Success (returned null)" : "Failed"));




        System.out.println("\n--- Testing getEntries ---");
        System.out.println(">>> TEST 1: All parameters are provided and match an existing entry");
        ArrayList<Genre> searchGenres = new ArrayList<>(List.of(Genre.ACTION));
        List<MediaEntry> filtered1 = vault.getEntries("Naruto", 2002, MediaType.ANIME, Status.COMPLETED, searchGenres);
        System.out.println("Result Size (Expected 1): " + filtered1.size());

        System.out.println("\n>>> TEST 2: All parameters are passed as null or 0");
        List<MediaEntry> filtered2 = vault.getEntries(null, 0, null, null, null);
        System.out.println("Result Size (Expected 2): " + filtered2.size());

        System.out.println("\n>>> TEST 3: Only one parameter is provided (others null/0)");
        List<MediaEntry> filtered3 = vault.getEntries(null, 0, MediaType.NOVEL, null, null);
        System.out.println("Result Size (Expected 1): " + filtered3.size());






        System.out.println("\n--- Testing getTotalByAttributes ---");
        System.out.println(">>> TEST 1: No parameters are null");
        long total1 = vault.getTotalByAttributes(MediaType.ANIME, Status.COMPLETED, searchGenres);
        System.out.println("Result (Expected 1): " + total1);

        System.out.println("\n>>> TEST 2: 1 parameter is null (Status is null)");
        long total2 = vault.getTotalByAttributes(MediaType.ANIME, null, searchGenres);
        System.out.println("Result (Expected 1): " + total2);

        System.out.println("\n>>> TEST 3: 2 parameters are null (Type and Status are null)");
        long total3 = vault.getTotalByAttributes(null, null, searchGenres);
        System.out.println("Result (Expected 1): " + total3);







        System.out.println("\n--- Testing removeEntry ---");
        System.out.println(">>> TEST 1: Title and year of the same entry both exist");
        vault.removeEntry("Naruto", 2002);
        System.out.println("Vault total after removal: " + vault.getTotal());

        System.out.println("\n>>> TEST 2: Title and year mismatch");
        try {
            vault.removeEntry("Harry Potter", 2026);
            System.out.println("Result: Failed (Exception was not thrown)");
        } catch (IllegalArgumentException e) {
            System.out.println("Result: Success (Caught expected exception: " + e.getMessage() + ")");
        }

        System.out.println("\n>>> TEST 3: Attempting to remove an entry that does not exist");
        try {
            vault.removeEntry("One Piece", 1999);
            System.out.println("Result: Failed (Exception was not thrown)");
        } catch (IllegalArgumentException e) {
            System.out.println("Result: Success (Caught expected exception: " + e.getMessage() + ")");
        }







        /* ANIME */

        System.out.println("=== TESTING ANIME ===");

        Anime naruto = new Anime(
            2002,
            "Naruto",
            "Demon fox possesses boy",
            new ArrayList<Genre>(List.of(Genre.ACTION, Genre.ADVENTURE)),
            "N/A",
            "Studio Perriot",
            Status.IN_PROGRESS
        );
        naruto.setStatus(Status.IN_PROGRESS);

        Display.displayEntryDetails(naruto);

        System.out.println("\n\n>>> TEST 1: Addition of a single episode");
        naruto.addEpisode(2002,  "Enter: Naruto Uzumaki",  "Demon fox possesses boy");
        int i = 1;
        for (Details episode : naruto.getAnimeEpisodes()) {
            System.out.print("Episode " + i++ + " - ");
            Display.displayAnimeEpisode(episode);
        }

        System.out.println("\n\n>>> TEST 2: Addition of multiple episodes");
        naruto.addEpisode(2002, "My Name Is Konohamaru!", "Naruto meets Konohamaru");
        naruto.addEpisode(2002, "Sasuke and Sakura", "Naruto becomes teammates with Sasuke and Sakura");
        i = 1;
        for (Details episode : naruto.getAnimeEpisodes()) {
            System.out.print("Episode " + i++ + " - ");
            Display.displayAnimeEpisode(episode);
        }


        /* MEDIA ENTRY */

        System.out.println("\n\n=== TESTING MEDIA ENTRY ===");

        System.out.println("\n>>> TEST 1: Normal Rating (Status COMPLETED)");
        naruto.setStatus(Status.COMPLETED);
        naruto.setRating(7.5f);
        System.out.println("Expected Rating: 7.5 | Actual Rating: " + naruto.getRating());

        System.out.println("\n>>> TEST 2: Upper Boundary Clamp");
        naruto.setRating(15.0f);
        System.out.println("Expected Rating: 10.0 | Actual Rating: " + naruto.getRating());

        System.out.println("\n>>> TEST 3: Lower Boundary Clamp");
        naruto.setRating(-5.0f);
        System.out.println("Expected Rating: 0.0 | Actual Rating: " + naruto.getRating());

        System.out.println("\n>>> TEST 4: Status Gatekeeping (IN_PROGRESS)");
        naruto.setStatus(Status.IN_PROGRESS);
        naruto.setRating(8.0f);
        naruto.setReview("This should not save.");
        System.out.println("Expected Rating: -1.0 | Actual Rating: " + naruto.getRating());
        System.out.println("Expected Review: null | Actual Review: " + naruto.getReview());


        /* INPUT */

        // need to manually input here so i commented it out

        // System.out.println("\n\n=== TESTING INPUT ===");

        // System.out.println("\n>>> TEST 1: getStrInput");
        // System.out.println("INSTRUCTIONS: \n1. Press Enter without input \n2. Type '123ABC' \n3. Type 'A' (Valid string)");
        // String str1 = Input.getStrInput("Enter 'A' or 'B'", "A", "B");
        // System.out.println("Result: " + str1);

        // System.out.println("\n>>> TEST 2: getMultilineInput");
        // System.out.println("INSTRUCTIONS: \n1. Press Enter without an input \n2. Type 'Line 1 with numbers 123' \n3. Type '>END' to finish");
        // String multi = Input.getMultilineInput("Enter a multi-line string");
        // System.out.println("Result:\n" + multi);

        // System.out.println("\n>>> TEST 3: getIntInput");
        // System.out.println("INSTRUCTIONS: \n1. Type 'Hello' \n2. Type '3.14' \n3. Type '42' (Valid int)");
        // int int1 = Input.getIntInput("Enter an integer");
        // System.out.println("Result: " + int1);

        // System.out.println("\n>>> TEST 4: getIntInput(min, max)");
        // System.out.println("INSTRUCTIONS: \n1. Type '20' (Out of bounds) \n2. Type '5A' (Mixed) \n3. Type '5' (Valid int)");
        // int int2 = Input.getIntInput("Enter an integer between 1 and 10", 1, 10);
        // System.out.println("Result: " + int2);

        // System.out.println("\n>>> TEST 5: getFloatInput");
        // System.out.println("INSTRUCTIONS: \n1. Type '3.14abc' (Mixed) \n2. Type 'abc' (String) \n3. Type '3.14' (Valid float)");
        // float float1 = Input.getFloatInput("Enter a float");
        // System.out.println("Result: " + float1);

        // System.out.println("\n>>> TEST 6: getFloatInput(min, max)");
        // System.out.println("INSTRUCTIONS: \n1. Type '15.5' (Out of bounds) \n2. Type 'abc' (String) \n3. Type '5.5' (Valid float)");
        // float float2 = Input.getFloatInput("Enter a float between 1.0 and 10.0", 1.0f, 10.0f);
        // System.out.println("Result: " + float2);







        /* DISPLAY */

        System.out.println("\n\n=== TESTING DISPLAY ===");

        System.out.println("\n--- Testing createBoard ---");
        System.out.println(">>> TEST 1: Title starts with '---', null options");

        Display.createBoard("--- Quick Menu", null);

        System.out.println("\n>>> TEST 2: Standard title, valid options");

        Display.createBoard("Main Menu", List.of("Option 1", "Option 2"));

        System.out.println("\n>>> TEST 3: Standard title, options with null and empty strings");
        Display.createBoard("Settings", Arrays.asList("Volume", null, "", "   ", "Brightness"));



        System.out.println("\n--- Testing displayEntryDetails ---");
        Anime testAnime2 = new Anime(2023, "Frieren", "Elf mage travels", new ArrayList<>(List.of(Genre.FANTASY, Genre.ADVENTURE)), "N/A", "Madhouse", Status.COMPLETED);
        Novel testNovel2 = new Novel(1965, "Dune", "Spice planet", new ArrayList<>(List.of(Genre.SCIENCE_FICTION)), "Chilton", "Frank Herbert", Status.COMPLETED, 20);
        VideoGame testGame2 = new VideoGame(2015, "Bloodborne", "Hunter in Yharnam", new ArrayList<>(List.of(Genre.ACTION, Genre.FANTASY)), "Sony", "FromSoftware", Status.PLANNED);

        System.out.println(">>> TEST 1: Entry is an anime");
        Display.displayEntryDetails(testAnime2);

        System.out.println("\n>>> TEST 2: Entry is a novel");
        Display.displayEntryDetails(testNovel2);

        System.out.println("\n>>> TEST 3: Entry is a video game");
        Display.displayEntryDetails(testGame2);



        System.out.println("\n--- Testing summarize ---");
        System.out.println(">>> TEST 1: Empty vault");
        MediaVault emptyVault = new MediaVault();
        Display.summarize(emptyVault);

        System.out.println("\n>>> TEST 2: Mixed entries, some COMPLETED");
        MediaVault mixedVault = new MediaVault();
        testAnime2.setRating(9.5f);
        testNovel2.setRating(10.0f);
        mixedVault.addEntry(testAnime2);
        mixedVault.addEntry(testNovel2);
        mixedVault.addEntry(testGame2);
        Display.summarize(mixedVault);

        System.out.println("\n>>> TEST 3: Mixed entries, none COMPLETED");
        MediaVault incompleteVault = new MediaVault();
        testAnime2.setStatus(Status.IN_PROGRESS);
        testNovel2.setStatus(Status.PLANNED);
        incompleteVault.addEntry(testAnime2);
        incompleteVault.addEntry(testNovel2);
        incompleteVault.addEntry(testGame2);
        Display.summarize(incompleteVault);


        System.out.println("\n=== TESTS CONCLUDED ===");
    }
}
