package menus;

import stimmungskalender_logik.StimmungskalenderSpeicher;
import stimmungskalender_logik.StimmungskalenderVerwaltung;

import java.util.Scanner;

/**
 * Diese Klasse stellt ein Menü zur Verwaltung des Stimmungskalenders bereit.
 * Der Benutzer kann Stimmungsdaten eintragen, den Verlauf anzeigen oder alle Einträge textuell ausgeben.
 */
public class Stimmungskalender {

    private final StimmungskalenderVerwaltung verwaltung;

    /**
     * Konstruktor – initialisiert die Stimmungskalenderverwaltung mit einem neuen Speicher.
     */
    public Stimmungskalender() {
        this.verwaltung = new StimmungskalenderVerwaltung(new StimmungskalenderSpeicher());
    }

    /**
     * Zeigt das Stimmungskalendermenü an und verarbeitet Benutzereingaben.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nHier kannst du deinen Stimmungskalender verwalten: \n");
            System.out.println("Wähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Daten eintragen");
            System.out.println("2 - Stimmungsverlauf als Graph ausgeben");
            System.out.println("3 - Alle Einträge anzeigen");
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
                case 1 -> {
                    // Neuen Stimmungseintrag hinzufügen und Emotionen erfassen
                    verwaltung.eintragHinzufuegen(scanner);
                    verwaltung.emotionenErfassen(scanner);
                }
                case 2 -> verwaltung.eintraegeAlsGraphAnzeigen(); // Stimmungsverlauf als Graph anzeigen
                case 3 -> verwaltung.eintraegeTextuellAnzeigen(); // Alle Einträge textuell anzeigen
                case 4 -> { return; } // Zurück zum Hauptmenü
                default -> System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
