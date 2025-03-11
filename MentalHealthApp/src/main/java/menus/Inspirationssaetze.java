package menus;

import inspirations_logik.InspirationsSpeicher;
import inspirations_logik.InspirationsVerwaltung;

import java.util.Scanner;

/**
 * Diese Klasse stellt das Menü zur Anzeige von Inspirationssätzen dar.
 * Der Benutzer kann über die Konsole einen zufälligen Satz anzeigen lassen
 * oder zum Hauptmenü zurückkehren.
 */
public class Inspirationssaetze {
    private final InspirationsVerwaltung verwaltung;

    /**
     * Konstruktor – initialisiert die Inspirationsverwaltung mit dem Dateispeicher.
     */
    public Inspirationssaetze() {
        this.verwaltung = new InspirationsVerwaltung(new InspirationsSpeicher());
    }

    /**
     * Zeigt das Menü zur Auswahl von Aktionen im Bereich „Inspirationssätze“.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWähle, was du tun möchtest:");
            System.out.println("1 - Zufälligen Satz anzeigen");
            System.out.println("2 - Zurück zum Hauptmenü");
            System.out.print("Deine Wahl: ");

            int auswahl = Integer.parseInt(scanner.nextLine());
            switch (auswahl) {
                case 1 -> zeigeSaetze(scanner);
                case 2 -> { return; }
                default -> System.out.println("Ungültige Eingabe.");
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
