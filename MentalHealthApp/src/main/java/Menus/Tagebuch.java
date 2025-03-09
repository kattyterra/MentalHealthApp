package Menus;

import TagebuchLogik.TagebuchVerwaltung;
import TagebuchLogik.DateiSpeicher;
import java.util.Scanner;

public class Tagebuch {
    private final TagebuchVerwaltung tagebuchVerwaltung;

    public Tagebuch() {
        this.tagebuchVerwaltung = new TagebuchVerwaltung(new DateiSpeicher());
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nHier kannst du dein Tagebuch verwalten:");
            System.out.println("1 - Eintrag schreiben");
            System.out.println("2 - Eintrag lesen");
            System.out.println("3 - Eintrag bearbeiten");
            System.out.println("4 - Eintrag löschen");
            System.out.println("5 - Zurück zum Hauptmenü");
            System.out.print("Deine Wahl: ");

            int auswahl = scanner.nextInt();
            scanner.nextLine();

            switch (auswahl) {
                case 1:
                    tagebuchVerwaltung.eintragSchreiben(scanner);
                    break;
                case 2:
                    tagebuchVerwaltung.eintragLesen(scanner);
                    break;
                case 3:
                    tagebuchVerwaltung.eintragBearbeiten(scanner);
                    break;
                case 4:
                    tagebuchVerwaltung.eintragLoeschen(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Ungültige Auswahl.");
            }
        }
    }
}