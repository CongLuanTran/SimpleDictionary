/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledictionaray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tranc
 */
public class TextUI {

    private static boolean hasNumeric(String word) {
        for (char c : word.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private final Dictionary dictionary;
    private final Scanner scanner;

    public TextUI(Dictionary dictionary, Scanner scanner) {
        this.dictionary = dictionary;
        this.scanner = scanner;
    }

    private void addTranslation() {
        String engWord, vietWord;

        do {
            System.out.print("Enter an English word: ");
            engWord = scanner.nextLine();

            if (dictionary.engList().contains(engWord)) {
                System.out.println("The word already exists! Enter a different word!");
                continue;
            }
        }
        while (engWord.isEmpty() || hasNumeric(engWord));

        do {
            System.out.print("Enter a Vietnamese word: ");
            vietWord = scanner.nextLine();
        }
        while (vietWord.isEmpty() || hasNumeric(vietWord));

        dictionary.addTranslation(engWord, vietWord);
        System.out.println("Word added successfully!");
        System.out.println(dictionary);

        System.out.print("Press enter to continue.");
        scanner.nextLine();
    }

    private void engSearch() {

        boolean next = true;

        do {
            String query;
            System.out.print("Search bar: ");
            query = scanner.nextLine();

            if (query.isEmpty()) {
                continue;
            }

            String translated = dictionary.engToViet(query);
            if (translated == null) {
                List<String> matched = dictionary.engSearch(query);
                if (matched.isEmpty()) {
                    System.out.println("Your search did not match any word!");
                }
                else {
                    matched.forEach(System.out::println);
                }
            }
            else {
                System.out.println(translated);
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
                        continue;
                }
            }
            while (choice.isEmpty());

        }
        while (next);
    }

    private void exam() {
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

        for (int i = 0; i < 10; i++) {
            String engWord = examList.get(i);
            String answer;
            String correctAnswer = dictionary.engToViet(engWord);

            System.out.println("Translate '" + engWord + "' to Vietnamese.");
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
            System.out.println("Wrong words: ");
            mistakes.forEach((t) -> {
                System.out.println(t + ": " + dictionary.engToViet(t));
            });
        }
    }

    private void vietSearch() {

        boolean next = true;

        do {
            String query;
            System.out.print("Search bar: ");
            query = scanner.nextLine();

            if (query.isEmpty()) {
                continue;
            }

            List<String> translated = dictionary.vietToEng(query);
            if (translated == null) {
                List<String> matched = dictionary.vietSearch(query);
                if (matched.isEmpty()) {
                    System.out.println("Your search did not match any word!");
                }
                else {
                    matched.forEach(System.out::println);
                }
            }
            else {
                translated.forEach(System.out::println);
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
                        continue;
                }
            }
            while (choice.isEmpty());
        }
        while (next);
    }
}
