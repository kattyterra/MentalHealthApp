package menus;

import uebungen.*;
import utility.AnswerParser;

import java.util.List;
import java.util.Scanner;

/**
 * Menü zur Anzeige und Auswahl von Übungen (z.B. Atem- oder Achtsamkeitsübungen).
 */
public class UebungMenu {

    private final List<Uebung> uebungen;
    private final String titel;
    private final AnswerParser parser = new AnswerParser();

    public UebungMenu(List<Uebung> uebungen, String titel) {
        this.uebungen = uebungen;
        this.titel = titel;
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            zeigeMenuText();

            int choice = parser.parsen(scanner);
            if (choice == 99) continue;

            if (choice == uebungen.size() + 1) {
                return;
            }

            if (istGueltigeWahl(choice)) {
                zeigeUebung(uebungen.get(choice - 1), scanner);
            } else {
                System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
            }
        }
    }

    private void zeigeMenuText() {
        System.out.println("\n📋 " + titel);
        for (int i = 0; i < uebungen.size(); i++) {
            System.out.println((i + 1) + " - " + uebungen.get(i).name());
        }
        System.out.println((uebungen.size() + 1) + " - 🔙 Zurück zum Hauptmenü");
        System.out.print("Deine Wahl: ");
    }

    private boolean istGueltigeWahl(int wahl) {
        return wahl > 0 && wahl <= uebungen.size();
    }

    private void zeigeUebung(Uebung uebung, Scanner scanner) {
        System.out.println("\n🌿 " + uebung.name());
        System.out.println("🎯 Ziel: " + uebung.ziel());
        System.out.println("\n📖 Anleitung:");
        for (String schritt : uebung.anleitung()) {
            System.out.println("• " + schritt);
        }
        System.out.println("\n🔙 Drücke Enter, um zurückzukehren.");
        scanner.nextLine();
    }
}
