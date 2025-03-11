package Menus;

import StimmungskalenderLogik.StimmungskalenderSpeicher;
import StimmungskalenderLogik.StimmungskalenderVerwaltung;

import java.util.Scanner;

public class Stimmungskalender {

    private final StimmungskalenderVerwaltung verwaltung;

    public Stimmungskalender() {
        this.verwaltung = new StimmungskalenderVerwaltung(new StimmungskalenderSpeicher());
    }

    void showMenu(Scanner scanner){
        while (true) {
            System.out.println("\nHier kannst du dein Stimmungskalender verwalten: \n");
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
                case 1:
                    verwaltung.eintragHinzufuegen(scanner);
                    verwaltung.emotionenErfassen(scanner);
                    break;
                case 2:
                    verwaltung.eintraegeAlsGraphAnzeigen();
                    break;
                case 3:
                    verwaltung.eintraegeTextuellAnzeigen();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
