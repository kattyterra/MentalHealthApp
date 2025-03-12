package menus;

import gedanken_reflexion_logik.*;

import java.util.Scanner;

public class GedankenReflexionMenu {
    private final GedankenReflexionVerwaltung verwaltung;

    public GedankenReflexionMenu() {
        this.verwaltung = new GedankenReflexionVerwaltung(new GedankenReflexionSpeicher());
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n===== Gedankenkarussell stoppen =====");
            System.out.println("1 - Neue Gedankenreflexion starten");
            System.out.println("2 - Vergangene Einträge anzeigen");
            System.out.println("0 - Zurück zum Hauptmenü");
            System.out.print("Deine Auswahl: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> verwaltung.neuerEintrag(scanner);
                case "2" -> verwaltung.alleEintraegeAnzeigen();
                case "0" -> { return; }
                default -> System.out.println("Ungültige Eingabe.");
            }
        }
    }
}
