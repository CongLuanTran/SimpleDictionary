/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledictionary;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author tranc
 */
public class Dictionary {

    private final Hashtable<String, String> dictionary;

    public Dictionary() {
        dictionary = new Hashtable<>();
    }

    public void addTranslation(String engWord, String vietWord) {
        dictionary.putIfAbsent(engWord.toLowerCase(), vietWord.toLowerCase());
    }

    public List<String> engList() {
        return new ArrayList<>(dictionary.keySet());
    }

    public List<String> engSearch(String query) {
        List<String> result = new ArrayList<>();

        for (String engWord : dictionary.keySet()) {
            if (engWord.toLowerCase().contains(query.toLowerCase())) {
                result.add(engWord);
            }
        }

        return result.isEmpty() ? null : result;
    }

    public String engToViet(String query) {
        String result = dictionary.get(query.toLowerCase());
        return result;
    }

    public int size() {
        return dictionary.size();
    }

    @Override
    public String toString() {
        String output = "";
        for (String w : dictionary.keySet()) {
            output += w + ": " + dictionary.get(w) + "\n";
        }

        return output;
    }

    public List<String> vietSearch(String query) {
        List<String> result = new ArrayList<>();

        for (String vietWord : dictionary.values()) {
            if (vietWord.toLowerCase().contains(query.toLowerCase())) {
                result.add(vietWord);
            }
        }

        return result.isEmpty() ? null : result;
    }

    public List<String> vietToEng(String query) {
        List<String> result = new ArrayList<>();
        for (String engWord : dictionary.keySet()) {
            if (dictionary.get(engWord).equalsIgnoreCase(query)) {
                result.add(engWord);
            }
        }

        return result.isEmpty() ? null : result;
    }

}
