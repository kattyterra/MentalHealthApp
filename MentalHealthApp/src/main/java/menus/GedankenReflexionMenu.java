package menus;

import gedanken_reflexion_logik.*;

import java.util.Scanner;

/**
 * Menüklasse zur Bedienung des Moduls „Gedankenkarussell stoppen“.
 *
 * Bietet dem Benutzer ein einfaches Textmenü zur Auswahl:
 * - Neue Reflexion durchführen
 * - Vorherige Einträge anzeigen
 * - Zurück zum Hauptmenü
 */
public class GedankenReflexionMenu {

    /** Verwaltungsklasse für die Gedankenreflexion (steuert Logik & Speicherung) */
    private final GedankenReflexionVerwaltung verwaltung;

    /**
     * Konstruktor – initialisiert die Verwaltungslogik mit einer konkreten Speicherstrategie.
     */
    public GedankenReflexionMenu() {
        this.verwaltung = new GedankenReflexionVerwaltung(new GedankenReflexionSpeicher());
    }

    /**
     * Zeigt das Menü zur Gedankenreflexion in einer Schleife,
     * bis der Benutzer den Menüpunkt „Zurück“ wählt.
     *
     * @param scanner Scanner zur Benutzereingabe
     */
    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n🧠 Gedankenkarussell stoppen – Finde Klarheit durch Selbstreflexion");
            System.out.println("──────────────────────────────────────────────────────────────");
            System.out.println(" 1 - ✍️  Neue Gedankenreflexion starten – was beschäftigt dich gerade?");
            System.out.println(" 2 - 📖 Vergangene Einträge anzeigen – deine bisherigen Gedankenreisen");
            System.out.println(" 0 - 🔙 Zurück zum Hauptmenü");
            System.out.println("──────────────────────────────────────────────────────────────");
            System.out.print("👉 Deine Auswahl: ");


            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    verwaltung.neuerEintrag(scanner);
                    break;

                case "2":
                    verwaltung.alleEintraegeAnzeigen();
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Ungültige Eingabe.");
                    break;
            }
        }
    }
}
