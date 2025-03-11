package menus;

import routinen_logik.*;

import java.util.Scanner;

public class RoutinenMenu {
    private final RoutinenVerwaltung verwaltung;

    public RoutinenMenu() throws RoutineException {
        RoutineRepository repository = new FileBasedRoutineRepository();
        RoutineVorschlagsService vorschlagsService = new RoutineVorschlagsService("Textvorlagen(nicht_ändern!)/RoutinenVollVorschlaege.txt");
        this.verwaltung = new RoutinenVerwaltung(repository, vorschlagsService);
    }

    public void showMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n📋 Routinenverwaltung:");
            System.out.println("1 - Meine Routinen ansehen/abhaken");
            System.out.println("2 - Routinen-Statistik anzeigen");
            System.out.println("3 - Routine hinzufügen");
            System.out.println("4 - Routine bearbeiten");
            System.out.println("5 - Routine löschen");
            System.out.println("6 - Routine-Historie anzeigen");
            System.out.println("7 - Zurück zum Hauptmenü");

            int auswahl = readInt(scanner);

            try {
                switch (auswahl) {
                    case 1 -> verwaltung.checklisteVerwalten(scanner);
                    case 2 -> verwaltung.routinenStatistikAnzeigen();
                    case 3 -> verwaltung.routineHinzufuegen(scanner);
                    case 4 -> verwaltung.routineBearbeiten(scanner);
                    case 5 -> verwaltung.routineLoeschen(scanner);
                    case 6 -> verwaltung.routinenHistorieAnzeigen();
                    case 7 -> running = false;
                    default -> System.out.println("Ungültige Auswahl.");
                }
            } catch (RoutineException e) {
                System.out.println("⚠ Fehler: " + e.getMessage());
            }
        }
    }

    private int readInt(Scanner scanner) {
        System.out.print("Deine Wahl: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }


}
