package Menus;

import RoutinenLogik.*;

import java.util.Scanner;

public class RoutinenMenu {
    private final RoutinenVerwaltung verwaltung;

    public RoutinenMenu() {
        this.verwaltung = new RoutinenVerwaltung(new RoutineManager());
    }

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

    private int verwaltungReadInt(Scanner scanner) {
        System.out.print("Deine Wahl: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}