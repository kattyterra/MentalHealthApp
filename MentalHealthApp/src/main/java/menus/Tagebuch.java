package menus;

import tagebuch_logik.TagebuchVerwaltung;
import tagebuch_logik.DateiSpeicher;

import java.util.Scanner;

/**
 * Diese Klasse stellt ein Menü für die Verwaltung von Tagebucheinträgen bereit.
 * Benutzer können Einträge schreiben, lesen, bearbeiten und löschen.
 */
public class Tagebuch {

    /** Verwaltungsklasse für alle Tagebuchfunktionen */
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
            System.out.println("\n📔 Tagebuch – Gedanken festhalten & reflektieren");
            System.out.println("───────────────────────────────────────────────");
            System.out.println(" 1 - ✍️  Eintrag schreiben – was möchtest du loswerden?");
            System.out.println(" 2 - 📖 Eintrag lesen – blättere durch deine Gedankenwelt");
            System.out.println(" 3 - ✏️  Eintrag bearbeiten – passe deine Worte nachträglich an");
            System.out.println(" 4 - 🗑️  Eintrag löschen – was du loslassen möchtest");
            System.out.println(" 5 - 🔙 Zurück zum Hauptmenü");
            System.out.println("───────────────────────────────────────────────");
            System.out.print("👉 Deine Wahl: ");


            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❗️Ups! Bitte gib eine Zahl ein, damit ich weiß, was du meinst. 😊");
                continue;
            }

            switch (choice) {
                case 1:
                {
                    // Neuer Tagebucheintrag wird geschrieben
                    tagebuchVerwaltung.eintragSchreiben(scanner);
                    break;
                }
                case 2:
                {
                    // Bestehenden Eintrag anzeigen
                    tagebuchVerwaltung.eintragLesen(scanner);
                    break;
                }
                case 3:
                {
                    // Eintrag bearbeiten
                    tagebuchVerwaltung.eintragBearbeiten(scanner);
                    break;
                }
                case 4:
                {
                    // Eintrag löschen
                    tagebuchVerwaltung.eintragLoeschen(scanner);
                    break;
                }
                case 5:
                {
                    // Zurück zum Hauptmenü
                    return;
                }
                default:
                {
                    // Ungültige Auswahlbehandlung
                    System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
                    break;
                }
            }
        }
    }
}
