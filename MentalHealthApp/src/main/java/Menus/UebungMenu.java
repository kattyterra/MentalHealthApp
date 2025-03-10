package Menus;
import Uebungen.*;
import java.util.List;
import java.util.Scanner;

public class UebungMenu {

    private final List<Uebung> uebungen;
    private final String titel;

    public UebungMenu(List<Uebung> uebungen, String titel) {
        this.uebungen = uebungen;
        this.titel = titel;
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n📋 " + titel);
            for (int i = 0; i < uebungen.size(); i++) {
                System.out.println((i + 1) + " - " + uebungen.get(i).name());
            }
            System.out.println((uebungen.size() + 1) + " - Zurück zum Hauptmenü");
            System.out.print("Deine Wahl: ");

            int wahl;
            try {
                wahl = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe.");
                continue;
            }

            if (wahl == uebungen.size() + 1) return;

            if (wahl > 0 && wahl <= uebungen.size()) {
                zeigeUebung(uebungen.get(wahl - 1), scanner);
            } else {
                System.out.println("Ungültige Auswahl.");
            }
        }
    }

    private void zeigeUebung(Uebung uebung, Scanner scanner) {
        System.out.println("\n🌿 " + uebung.name());
        System.out.println("🎯 Ziel: " + uebung.ziel());
        System.out.println("\n📖 Anleitung:");
        for (String s : uebung.anleitung()) {
            System.out.println("• " + s);
        }
        System.out.println("\nDrücke Enter, um zurückzukehren.");
        scanner.nextLine();
    }
}
