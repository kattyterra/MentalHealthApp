package menus;

import stimmungskalender_logik.StimmungskalenderSpeicher;
import stimmungskalender_logik.StimmungskalenderVerwaltung;
import utility.AnswerParser;

import java.util.Scanner;

/**
 * Menüklasse zur Bedienung des Stimmungskalenders in der MentalHealthApp.
 * Der Benutzer kann Stimmungsdaten erfassen, Stimmungsverlauf als Textgraph anzeigen
 * oder alle bisherigen Einträge textuell ausgeben.
 */
public class StimmungskalenderMenu {

    /** Zentrale Verwaltung des Stimmungskalenders */
    private final StimmungskalenderVerwaltung verwaltung;
    private final AnswerParser parser = new AnswerParser();

    /** Konstruktor – initialisiert die Verwaltung mit einem konkreten Speicher */
    public StimmungskalenderMenu() {
        this.verwaltung = new StimmungskalenderVerwaltung(new StimmungskalenderSpeicher());
    }

    /**
     * Zeigt das Stimmungskalender-Menü und verarbeitet Benutzereingaben.
     * Das Menü läuft in einer Schleife, bis der Benutzer „Zurück zum Hauptmenü“ auswählt.
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

            int choice = parser.parsen(scanner);
            if (choice == 99) continue;

            switch (choice) {
                case 1 -> {
                    verwaltung.eintragHinzufuegen(scanner);
                    verwaltung.emotionenErfassen(scanner);
                }
                case 2 -> verwaltung.eintraegeAlsGraphAnzeigen();
                case 3 -> verwaltung.eintraegeTextuellAnzeigen();
                case 4 -> { return; }
                default -> System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
            }
        }
    }
}
