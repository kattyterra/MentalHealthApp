package Menus;

import TagebuchLogik.TagebuchVerwaltung;

import java.util.Scanner;

public class Tagebuch {

    private final TagebuchVerwaltung tgverw = new TagebuchVerwaltung();

     void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nHier kannst du dein Tagebuch verwalten: \n");
            System.out.println("Wähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Eintrag schreiben");
            System.out.println("2 - Eintrag lesen");
            System.out.println("3 - Eintrag bearbeiten");
            System.out.println("4 - Eintrag löschen");
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
                    tgverw.eintragSchreiben(scanner);
                    break;
                case 2:
                    System.out.println("Eintrag lesen...");
                    break;
                case 3:
                    System.out.println("Eintrag bearbeiten...");
                    break;
                case 4:
                    tgverw.eintragLoeschen(scanner);
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
