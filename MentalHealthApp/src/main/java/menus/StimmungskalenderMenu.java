package menus;

import stimmungskalender_logik.StimmungskalenderSpeicher;
import stimmungskalender_logik.StimmungskalenderVerwaltung;

import java.util.Scanner;

/**
 * Menüklasse zur Bedienung des Stimmungskalenders in der MentalHealthApp.
 * <p>
 * Der Benutzer kann:
 * <ul>
 *     <li>Stimmungsdaten erfassen</li>
 *     <li>Stimmungsverlauf als Textgraph anzeigen</li>
 *     <li>Alle bisherigen Einträge textuell ausgeben</li>
 * </ul>
 */
public class StimmungskalenderMenu {

    /** Zentrale Verwaltung des Stimmungskalenders */
    private final StimmungskalenderVerwaltung verwaltung;

    /**
     * Konstruktor – initialisiert die Verwaltung mit einem konkreten Speicher.
     */
    public StimmungskalenderMenu() {
        this.verwaltung = new StimmungskalenderVerwaltung(new StimmungskalenderSpeicher());
    }

    /**
     * Zeigt das Stimmungskalender-Menü und verarbeitet Benutzereingaben.
     * Das Menü läuft in einer Schleife, bis der Benutzer „Zurück zum Hauptmenü“ auswählt.
     *
     * @param scanner Scanner zur Eingabe
     */
    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n😊 Stimmungskalender – Deine emotionale Reise im Blick");
            System.out.println("────────────────────────────────────────────────────");
            System.out.println(" 1 - 📝 Daten eintragen – dokumentiere deine Tagesstimmung & Emotionen");
            System.out.println(" 2 - 📈 Stimmungsverlauf als Graph anzeigen – erkenne deine Entwicklungen");
            System.out.println(" 3 - 📖 Alle Einträge anzeigen – dein emotionales Protokoll");
            System.out.println(" 4 - 🔙 Zurück zum Hauptmenü");
            System.out.println("────────────────────────────────────────────────────");
            System.out.print("👉 Deine Wahl: ");

            int benutzerAuswahl;
            try {
                benutzerAuswahl = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❗️Ups! Bitte gib eine Zahl ein, damit ich weiß, was du meinst. 😊");
                continue;
            }

            switch (benutzerAuswahl) {
                case 1: {
                    // Neue Stimmung und Emotionen erfassen
                    verwaltung.eintragHinzufuegen(scanner);
                    verwaltung.emotionenErfassen(scanner);
                    break;
                }
                case 2: {
                    // Stimmungskurve als Textgraph anzeigen
                    verwaltung.eintraegeAlsGraphAnzeigen();
                    break;
                }
                case 3: {
                    // Alle Einträge in Textform ausgeben
                    verwaltung.eintraegeTextuellAnzeigen();
                    break;
                }
                case 4: {
                    // Zurück zum Hauptmenü
                    return;
                }
                default: {
                    // Fehlerbehandlung bei ungültiger Eingabe
                    System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
                    break;
                }
            }
        }
    }
}
