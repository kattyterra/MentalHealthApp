package menus;

import routinen_logik.*;

import java.util.Scanner;

/**
 * Diese Klasse stellt ein Menü zur Verwaltung der Routinen bereit.
 * Benutzer können Routinen ansehen, abhaken, hinzufügen, bearbeiten oder löschen.
 */
public class RoutinenMenu {
    private final RoutinenVerwaltung verwaltung;

    /**
     * Konstruktor – initialisiert die RoutinenVerwaltung mit einem neuen RoutineManager.
     */
    public RoutinenMenu() {
        this.verwaltung = new RoutinenVerwaltung(new RoutineManager());
    }

    /**
     * Zeigt das Routinenverwaltungsmenü an und verarbeitet Benutzereingaben.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void showMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n📋 Routinenverwaltung:");
            System.out.println("1 - Meine Routinen ansehen/abhacken");
            System.out.println("2 - Routine hinzufügen");
            System.out.println("3 - Routine bearbeiten");
            System.out.println("4 - Routine löschen");
            System.out.println("5 - Zurück zum Hauptmenü");

            int auswahl = verwaltungReadInt(scanner);

            switch (auswahl) {
                case 1 -> verwaltung.checklisteVerwalten(scanner);
                case 2 -> verwaltung.routineHinzufuegen(scanner);
                case 3 -> verwaltung.routineBearbeiten(scanner);
                case 4 -> verwaltung.routineLoeschen(scanner);
                case 5 -> running = false;
                default -> System.out.println("Ungültige Auswahl.");
            }
        }
    }

    /**
     * Liest eine Ganzzahl vom Benutzer ein und gibt sie zurück.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     * @return die eingegebene Zahl oder -1 bei Fehler
     */
    private int verwaltungReadInt(Scanner scanner) {
        System.out.print("Deine Wahl: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}
