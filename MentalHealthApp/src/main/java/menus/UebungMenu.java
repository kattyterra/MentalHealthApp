package menus;

import uebungen.*;
import java.util.List;
import java.util.Scanner;

/**
 * Diese Klasse stellt ein Menü für die Verwaltung und Anzeige von Übungen bereit.
 * Der Benutzer kann aus einer Liste von Übungen wählen und sich deren Details anzeigen lassen.
 */
public class UebungMenu {

    private final List<Uebung> uebungen;
    private final String titel;

    /**
     * Konstruktor – initialisiert das Menü mit einer Liste von Übungen und einem Titel.
     *
     * @param uebungen Liste der verfügbaren Übungen
     * @param titel    Titel des Menüs (z. B. „Deine Atemübungen“)
     */
    public UebungMenu(List<Uebung> uebungen, String titel) {
        this.uebungen = uebungen;
        this.titel = titel;
    }

    /**
     * Zeigt das Übungsmenü an und verarbeitet Benutzereingaben.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
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

    /**
     * Zeigt die Details der gewählten Übung an (Name, Ziel, Anleitung).
     *
     * @param uebung   Die ausgewählte Übung
     * @param scanner  Scanner-Objekt für Benutzereingaben
     */
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
