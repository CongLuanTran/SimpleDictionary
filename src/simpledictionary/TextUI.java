/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tranc
 */
public class TextUI {

    private static boolean validInput(String word) {
        return word != null && !word.isEmpty() && word.chars().allMatch(Character::isLetter);
    }

    private final Dictionary dictionary;
    private final String fileName;
    private final Scanner scanner;

    public TextUI(Dictionary dictionary, Scanner scanner, String fileName) {
        this.dictionary = dictionary;
        this.scanner = scanner;
        this.fileName = fileName;
    }

    private void addTranslation() {
        String engWord, vietWord;

        do {
            System.out.print("Enter an English word: ");
            engWord = scanner.nextLine();

            if (dictionary.engList().contains(engWord)) {
                System.out.println("The word already exists! Enter a different word!");
                engWord = "";
            }
        }
        while (!validInput(engWord));

        do {
            System.out.print("Enter a Vietnamese word: ");
            vietWord = scanner.nextLine();
        }
        while (!validInput(vietWord));

        dictionary.addTranslation(engWord, vietWord);
        System.out.println("Word added successfully!");

        System.out.println("===CURRENT WORDS IN DICTIONARY===");
        System.out.println(dictionary);
        System.out.println("=================================\n");

        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    private void engSearch() {

        if (isEmpty()) {
            System.out.println("The dictionary is empty, please add some words!");
            return;
        }

        boolean next = true;

        do {
            String query;
            System.out.println("===SEARCH BAR====");
            System.out.print("Input: ");
            query = scanner.nextLine();
            System.out.println("=================\n");

            if (query.isEmpty()) {
                continue;
            }

            String translated = dictionary.engToViet(query);
            if (translated == null) {
                List<String> matched = dictionary.engSearch(query);
                if (matched == null) {
                    System.out.println("Your search did not match any word!");
                }
                else {
                    System.out.println("===MATCHING WORDS===");
                    matched.forEach(System.out::println);
                    System.out.println("====================\n");
                }
            }
            else {
                System.out.println("===TRANSLATION===");
                System.out.println(translated);
                System.out.println("=================\n");
            }

            String choice;
            do {
                System.out.print("Do you want to search another word? (y/n) ");
                choice = scanner.nextLine();
                switch (choice) {
                    case "y":
                        break;
                    case "n":
                        next = false;
                        break;
                    default:
                        choice = "";
                }
            }
            while (choice.isEmpty());

        }
        while (next);
    }

    private void exam() {

        if (isEmpty()) {
            System.out.println("The dictionary is empty, please add some words!");
            return;
        }

        List<String> examList = dictionary.engList();
        List<String> mistakes = new ArrayList<>();

        int score = 0;

        if (examList.size() < 20) {
            System.out.println("Not enough word in dictionary! Please add more words!");
            System.out.println("Press enter to return.");
            scanner.nextLine();
            return;
        }

        Collections.shuffle(examList);

        System.out.println("Translate these English words to Vietnamese: ");

        for (int i = 0; i < 10; i++) {
            String engWord = examList.get(i);
            String answer;
            String correctAnswer = dictionary.engToViet(engWord);

            System.out.println((i + 1) + ". '" + engWord + "'");
            System.out.print("Your translation: ");
            answer = scanner.nextLine();

            if (answer.equalsIgnoreCase(correctAnswer)) {
                System.out.println("Your answer is correct!");
                score++;
            }
            else {
                System.out.println("Your answer is incorrect!");
                mistakes.add(engWord);
            }
            System.out.println("Your score: " + score);
        }

        System.out.println("Final score: " + score + "/10");

        if (!mistakes.isEmpty()) {
            System.out.println("===WRONG WORDS===");
            mistakes.forEach((t) -> {
                System.out.println(t + ": " + dictionary.engToViet(t));
            });
            System.out.println("=================\n");
        }

        System.out.println("Press enter to return.");
        scanner.nextLine();
    }

    private boolean isEmpty() {
        return dictionary.size() <= 0;
    }

    private void load() {
        System.out.println("Loading dictionary...");
        try (Scanner sc = new Scanner(new File(fileName))) {
            int count = Integer.parseInt(sc.nextLine());

            if (count == 0) {
                System.out.println("The dictionary is empty, please add some words!");
            }
            else {

                String engWord;
                String vietWord;
                for (int i = 0; i < count; i++) {
                    engWord = sc.nextLine();
                    vietWord = sc.nextLine();
                    dictionary.addTranslation(engWord, vietWord);
                }

                System.out.println("Finished loading " + count + " words!");
            }

        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found! An empty dictionary will be used!");
        }
    }

    private void menu() {
        System.out.println("====ENGLISH-VIETNAMESE DICTIONARY====");
        System.out.println("1. English to Vietnamese");
        System.out.println("2. Vietnamese to English");
        System.out.println("3. Add a word");
        System.out.println("4. Test your knowledge");
        System.out.println("5. Quit");
        System.out.println("=============END OF MENU=============");
    }

    private void save() {
        System.out.println("Saving dictionary ...");
        try (FileWriter fw = new FileWriter(fileName)) {
            int count = dictionary.size();
            fw.write(count + "\n");

            String toWrite = "";
            for (String engWord : dictionary.engList()) {
                toWrite += engWord + "\n";
                toWrite += dictionary.engToViet(engWord) + "\n";
            }

            fw.write(toWrite);

            System.out.println("Finished saving " + count + " words!");
        }
        catch (IOException ex) {
            System.out.println("Error when saving: " + ex);
        }
    }

    public void start() {
        load();
        boolean next = true;

        do {
            menu();

            String choice;
            do {
                System.out.print("Enter your choice: ");
                choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        engSearch();
                        break;
                    case "2":
                        vietSearch();
                        break;
                    case "3":
                        addTranslation();
                        break;
                    case "4":
                        exam();
                        break;
                    case "5":
                        next = false;
                        save();
                        break;
                    default:
                        choice = "";
                        break;
                }
            }
            while (choice.isEmpty());
        }
        while (next);
    }

    private void vietSearch() {

        if (isEmpty()) {
            System.out.println("The dictionary is empty, please add some words!");
            return;
        }

        boolean next = true;

        do {
            String query;
            System.out.println("===SEARCH BAR====");
            System.out.print("Input: ");
            query = scanner.nextLine();
            System.out.println("=================\n");

            if (query.isEmpty()) {
                continue;
            }

            List<String> translated = dictionary.vietToEng(query);
            if (translated == null) {
                List<String> matched = dictionary.vietSearch(query);
                if (matched == null) {
                    System.out.println("Your search did not match any word!");
                }
                else {
                    System.out.println("===MATCHING WORDS===");
                    matched.forEach(System.out::println);
                    System.out.println("====================\n");
                }
            }
            else {
                System.out.println("===TRANSLATION===");
                translated.forEach(System.out::println);
                System.out.println("=================\n");
            }

            String choice;
            do {
                System.out.print("Do you want to search another word? (y/n) ");
                choice = scanner.nextLine();
                switch (choice) {
                    case "y":
                        break;
                    case "n":
                        next = false;
                        break;
                    default:
                        choice = "";
                }
            }
            while (choice.isEmpty());
        }
        while (next);
    }
}
