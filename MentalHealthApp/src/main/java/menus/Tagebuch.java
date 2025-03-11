package menus;

import tagebuch_logik.TagebuchVerwaltung;
import tagebuch_logik.DateiSpeicher;

import java.util.Scanner;

/**
 * Diese Klasse stellt ein Menü für die Verwaltung von Tagebucheinträgen bereit.
 * Benutzer können Einträge schreiben, lesen, bearbeiten und löschen.
 */
public class Tagebuch {
    private final TagebuchVerwaltung tagebuchVerwaltung;

    /**
     * Konstruktor – initialisiert die Tagebuchverwaltung mit einem Dateispeicher.
     */
    public Tagebuch() {
        this.tagebuchVerwaltung = new TagebuchVerwaltung(new DateiSpeicher());
    }

    /**
     * Zeigt das Tagebuchmenü und verarbeitet Benutzereingaben.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
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
                case 1 -> tagebuchVerwaltung.eintragSchreiben(scanner); // Neuer Tagebucheintrag schreiben
                case 2 -> tagebuchVerwaltung.eintragLesen(scanner); // Eintrag lesen
                case 3 -> tagebuchVerwaltung.eintragBearbeiten(scanner); // Eintrag bearbeiten
                case 4 -> tagebuchVerwaltung.eintragLoeschen(scanner); // Eintrag löschen
                case 5 -> { return; } // Zurück zum Hauptmenü
                default -> System.out.println("Ungültige Auswahl.");
            }
        }
    }
}
