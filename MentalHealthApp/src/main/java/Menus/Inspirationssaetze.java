package Menus;

import InspirationsLogik.InspirationsSpeicher;
import InspirationsLogik.InspirationsVerwaltung;

import java.util.Scanner;

public class Inspirationssaetze {
    private final InspirationsVerwaltung verwaltung;

    public Inspirationssaetze() {
        this.verwaltung = new InspirationsVerwaltung(new InspirationsSpeicher());
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWähle, was du tun möchtest:");
            System.out.println("1 - Zufälligen Satz anzeigen");
            System.out.println("2 - Zurück zum Hauptmenü");
            System.out.print("Deine Wahl: ");

            int auswahl = Integer.parseInt(scanner.nextLine());
            switch (auswahl) {
                case 1: {
                    zeigeSaetze(scanner);
                    break;
                }
                case 2: {
                    return;
                }
                default: {
                    System.out.println("Ungültige Eingabe.");
                }
            }
        }
    }

    private void zeigeSaetze(Scanner scanner) {
        String satz = verwaltung.gibZufaelligenSatz();
        System.out.println("\n✨ " + satz);
    }
}

