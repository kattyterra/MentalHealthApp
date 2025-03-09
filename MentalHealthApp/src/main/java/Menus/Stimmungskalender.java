package Menus;

import java.util.Scanner;

public class Stimmungskalender {

    void showMenu(Scanner scanner){
        while (true) {
            System.out.println("\nHier kannst du dein Stimmungskalender verwalten: \n");
            System.out.println("Wähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Daten eintragen");
            System.out.println("2 - Graph ausgeben");
            System.out.println("3 - Eintrag bearbeiten");;
            System.out.println("4 - Zurück zum Hauptmenü");
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
                    System.out.println("Daten eintragen");
                    break;
                case 2:
                    System.out.println("Graph ausgeben");
                    break;
                case 3:
                    System.out.println("Eintrag bearbeiten...");
                    break;
                case 4:
                    System.out.println("Zurück zum Hauptmenü");
                    return;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
