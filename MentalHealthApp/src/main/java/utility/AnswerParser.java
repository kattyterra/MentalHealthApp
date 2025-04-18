package utility;

import java.util.Scanner;

public class AnswerParser {
    public int parsen(Scanner scanner){
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❗️Ups! Bitte gib eine Zahl ein, damit ich weiß, was du meinst. 😊");
            return 99;
        }
    }
}
