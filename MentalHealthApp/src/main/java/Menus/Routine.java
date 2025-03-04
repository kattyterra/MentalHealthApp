package Controllers;

import java.util.Scanner;

public class Routine {
    void showMenu(Scanner scanner){
        while (true) {
            System.out.println("\nHier kannst du deine Routinen verwalten: \n");
            System.out.println("Wähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Meine Routinen ansehen");
            System.out.println("2 - Routine hinzufügen");
            System.out.println("2 - Routine bearbeiten");
            System.out.println("4 - Routine löschen");;
            System.out.println("5 - Zurück zum Hauptmenü");
            System.out.print("Deine Wahl: ");

            int benutzerAuswahl;
            try {
                benutzerAuswahl = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }

            switch (benutzerAuswahl) {
                case 1:
                    System.out.println("Meine Routinen ansehen");
                    break;
                case 2:
                    System.out.println("Routine hinzufügen");
                    break;
                case 3:
                    System.out.println("Routine bearbeiten");
                    break;
                case 4:
                    System.out.println("Routine löschen");
                    break;
                case 5:
                    System.out.println("Zurück zum Hauptmenü");
                    return;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
