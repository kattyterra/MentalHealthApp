package Controllers;

import java.util.Scanner;

public class TagebuchController {
    public TagebuchController() {
    }

    static void TagebuchMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nHier kannst du dein Tagebuch verwalten: \n");
            System.out.println("Wähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Eintrag schreiben");
            System.out.println("2 - Eintrag lesen");
            System.out.println("3 - Eintrag bearbeiten");
            System.out.println("4 - Eintrag löschen");
            System.out.println("5 - Zurück zum Start");
            System.out.print("Deine Wahl: ");

            int diaryChoice;
            try {
                diaryChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }

            switch (diaryChoice) {
                case 1:
                    System.out.println("Neuen Eintrag schreiben...");
                    break;
                case 2:
                    System.out.println("Eintrag lesen...");
                    break;
                case 3:
                    System.out.println("Eintrag bearbeiten...");
                    break;
                case 4:
                    System.out.println("Eintrag löschen...");
                    break;
                case 5:
                    System.out.println("Zurück zum Hauptmenü...");
                    return;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
