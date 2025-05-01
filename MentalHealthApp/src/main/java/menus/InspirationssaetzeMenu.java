package menus;

import inspirations_logik.InspirationsVerwaltung;
import utility.AnswerParser;

import java.util.Scanner;

/**
 * Diese Klasse stellt das Menü zur Anzeige von Inspirationssätzen dar.
 * Der Benutzer kann über die Konsole einen zufälligen Satz anzeigen lassen
 * oder zum Hauptmenü zurückkehren.
 */
public class InspirationssaetzeMenu {
    private final InspirationsVerwaltung verwaltung = new InspirationsVerwaltung();
    private final AnswerParser answerParser = new AnswerParser();


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

            int choice = answerParser.parsen(scanner);
            if (choice == 99) continue;

            switch (choice) {
                case 1 -> zeigeSaetze();
                case 2 -> { return; }
                default -> System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
            }
        }
    }

    /**
     * Gibt einen zufälligen Inspirationssatz aus der Datenquelle aus.
     */
    private void zeigeSaetze() {
        String satz = verwaltung.gibZufaelligenSatz();
        System.out.println("\n✨ " + satz);
    }
}
