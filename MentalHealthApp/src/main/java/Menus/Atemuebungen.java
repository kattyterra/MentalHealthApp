package Menus;

import AtemuebungenLogik.*;

import java.util.List;
import java.util.Scanner;

public class Atemuebungen {

    private final String dateipfad = "Textvorlagen(nicht_ändern!)/Atemuebungen.txt";

    public void showMenu(Scanner scanner) {
        List<Atemuebung> uebungen = AtemuebungLoader.ladeUebungen(dateipfad);

        while (true) {
            System.out.println("\nWähle eine Atemübung:");
            for (int i = 0; i < uebungen.size(); i++) {
                System.out.println((i + 1) + " - " + uebungen.get(i).name());
            }
            System.out.println((uebungen.size() + 1) + " - Zurück zum Hauptmenü");
            System.out.print("Deine Wahl: ");

            int wahl;
            try {
                wahl = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }

            if (wahl == uebungen.size() + 1) {
                return;
            } else if (wahl > 0 && wahl <= uebungen.size()) {
                zeigeUebung(uebungen.get(wahl - 1), scanner);
            } else {
                System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }

    private void zeigeUebung(Atemuebung uebung, Scanner scanner) {
        System.out.println("\n🌿 " + uebung.name());
        System.out.println("🎯 Ziel: " + uebung.ziel());
        System.out.println("\n📖 Anleitung:");
        for (String schritt : uebung.anleitung()) {
            System.out.println("• " + schritt);
        }

        System.out.println("\nDrücke Enter, um zurück zur Auswahl der Übungen zu gelangen.");
        scanner.nextLine();
    }
}
