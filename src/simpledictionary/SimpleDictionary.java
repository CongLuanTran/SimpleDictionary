/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledictionary;

import java.util.Scanner;

/**
 *
 * @author tranc
 */
public class SimpleDictionary {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String fileName = "EnVi.txt";
        Dictionary dictionary = new Dictionary();
        try (Scanner scanner = new Scanner(System.in)) {
            TextUI ui = new TextUI(dictionary, scanner, fileName);
            ui.start();
        }
    }
    
}
