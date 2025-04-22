package utility;

import java.util.Scanner;

public class AnswerParser {
    public int parsen(Scanner scanner){
        try {
            if (scanner.hasNextLine()) {
                return Integer.parseInt(scanner.nextLine());
            } else {
                System.out.println("❗️Ups! Keine Eingabe erkannt.");
                return 99;
            }
        } catch (NumberFormatException e) {
            System.out.println("❗️Ups! Bitte gib eine Zahl ein, damit ich weiß, was du meinst. 😊");
            return 99;
        }
    }
}
