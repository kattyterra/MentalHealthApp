package menus;

import tagebuch_logik.TagebuchVerwaltung;
import tagebuch_logik.DateiSpeicher;
import utility.AnswerParser;

import java.util.Scanner;

/**
 * Diese Klasse stellt ein Menü für die Verwaltung von Tagebucheinträgen bereit.
 * Benutzer können Einträge schreiben, lesen, bearbeiten und löschen.
 */
public class TagebuchMenu {

    /** Verwaltungsklasse für alle Tagebuchfunktionen */
    private final TagebuchVerwaltung tagebuchVerwaltung;
    private final AnswerParser answerParser = new AnswerParser();

    /** Konstruktor – initialisiert die Tagebuchverwaltung mit einem Dateispeicher */
    public TagebuchMenu() {
        this.tagebuchVerwaltung = new TagebuchVerwaltung(new DateiSpeicher());
    }

    /**
     * Zeigt das Tagebuchmenü und verarbeitet Benutzereingaben.
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

            int choice = answerParser.parsen(scanner);
            if (choice == 99){
                continue;
            }

            switch (choice) {
                case 1 -> tagebuchVerwaltung.eintragSchreiben(scanner);
                case 2 -> tagebuchVerwaltung.eintragLesen(scanner);
                case 3 -> tagebuchVerwaltung.eintragBearbeiten(scanner);
                case 4 -> tagebuchVerwaltung.eintragLoeschen(scanner);
                case 5 -> { return; }
                default -> System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
            }
        }
    }
}
