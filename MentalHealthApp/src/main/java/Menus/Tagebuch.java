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
            System.out.println("2 - Eintrag löschen");
            System.out.println("3 - Zurück zum Hauptmenü");
            System.out.print("Deine Wahl: ");

            int auswahl = scanner.nextInt();
            scanner.nextLine(); // Vermeidet Scanner-Probleme

            switch (auswahl) {
                case 1:
                    tagebuchVerwaltung.eintragSchreiben(scanner);
                    break;
                case 2:
                    tagebuchVerwaltung.eintragLoeschen(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Ungültige Auswahl.");
            }
        }
    }
}