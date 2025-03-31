package menus;

import inspirations_logik.InspirationsSpeicher;
import inspirations_logik.InspirationsVerwaltung;

import java.util.Scanner;

/**
 * Diese Klasse stellt das Menü zur Anzeige von Inspirationssätzen dar.
 * Der Benutzer kann über die Konsole einen zufälligen Satz anzeigen lassen
 * oder zum Hauptmenü zurückkehren.
 */
public class InspirationssaetzeMenu {
    private final InspirationsVerwaltung verwaltung;

    /**
     * Konstruktor – initialisiert die Inspirationsverwaltung mit dem Dateispeicher.
     */
    public InspirationssaetzeMenu() {
        this.verwaltung = new InspirationsVerwaltung(new InspirationsSpeicher());
    }

    /**
     * Zeigt das Menü zur Auswahl von Aktionen im Bereich „Inspirationssätze“.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n💡 Inspirationsecke – kleine Impulse für deinen Tag");
            System.out.println("────────────────────────────────────────────");
            System.out.println(" 1 - ✨ Zufälligen Satz anzeigen – ein Gedanke, der dich stärkt");
            System.out.println(" 2 - 🔙 Zurück zum Hauptmenü");
            System.out.println("────────────────────────────────────────────");
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
                    zeigeSaetze(scanner);
                    break;
                }
                case 2:
                {
                    return;
                }
                default:
                {
                    System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
                    break;
                }
            }
        }
    }

    /**
     * Gibt einen zufälligen Inspirationssatz aus der Datenquelle aus.
     *
     * @param scanner Scanner-Objekt (hier nicht direkt genutzt, aber für spätere Erweiterungen vorbereitet)
     */
    private void zeigeSaetze(Scanner scanner) {
        String satz = verwaltung.gibZufaelligenSatz();
        System.out.println("\n✨ " + satz);
    }
}
