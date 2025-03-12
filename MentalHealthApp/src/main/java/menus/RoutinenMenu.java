package menus;

import routinen_logik.*;

import java.util.Scanner;

/**
 * Menüklasse für das Routinen-Modul der MentalHealthApp.
 *
 * Ermöglicht dem Benutzer den Zugriff auf alle Funktionen der Routinenverwaltung,
 * wie z. B. Routinen abhaken, hinzufügen, bearbeiten, löschen und Statistiken anzeigen.
 */
public class RoutinenMenu {

    /** Zentrale Verwaltungsklasse für alle Routine-Funktionen */
    private final RoutinenVerwaltung verwaltung;

    /**
     * Konstruktor – initialisiert die Routinenverwaltung mit
     * - einem dateibasierten Repository
     * - einem Vorschlagsservice für neue Routinen
     *
     * @throws RoutineException falls die Initialisierung fehlschlägt
     */
    public RoutinenMenu() throws RoutineException {
        RoutineRepository repository = new FileBasedRoutineRepository();
        RoutineVorschlagsService vorschlagsService =
                new RoutineVorschlagsService("Textvorlagen(nicht_ändern!)/RoutinenVollVorschlaege.txt");
        this.verwaltung = new RoutinenVerwaltung(repository, vorschlagsService);
    }

    /**
     * Zeigt das Routinenmenü in einer Schleife,
     * bis der Benutzer den Punkt „Zurück“ wählt.
     *
     * @param scanner Scanner für die Benutzereingabe
     */
    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n📋 Routinenverwaltung – Deine tägliche Selbstfürsorge im Blick:");
            System.out.println("──────────────────────────────────────────────────────────────");
            System.out.println(" 1 - ✅ Routinen ansehen & abhaken – was hast du heute schon geschafft?");
            System.out.println(" 2 - 📊 Routinen-Statistik – Überblick über deine Fortschritte");
            System.out.println(" 3 - ➕ Neue Routine hinzufügen – baue gesunde Gewohnheiten auf");
            System.out.println(" 4 - ✏️  Routine bearbeiten – passe deine Routinen individuell an");
            System.out.println(" 5 - 🗑️  Routine löschen – was nicht (mehr) zu dir passt, darf gehen");
            System.out.println(" 6 - 🕓 Rückblick – Routine-Historie anzeigen");
            System.out.println(" 7 - 🔙 Zurück zum Hauptmenü");
            System.out.println("──────────────────────────────────────────────────────────────");

            int auswahl = readInt(scanner);

            try {
                switch (auswahl) {
                    case 1:
                    {
                        verwaltung.checklisteVerwalten(scanner);
                        break;
                    }
                    case 2:
                    {
                        verwaltung.routinenStatistikAnzeigen();
                        break;
                    }
                    case 3:
                    {
                        verwaltung.routineHinzufuegen(scanner);
                        break;
                    }
                    case 4:
                    {
                        verwaltung.routineBearbeiten(scanner);
                        break;
                    }
                    case 5:
                    {
                        verwaltung.routineLoeschen(scanner);
                        break;
                    }
                    case 6:
                    {
                        verwaltung.routinenHistorieAnzeigen();
                        break;
                    }
                    case 7:
                    {
                        return;
                    }
                    default:
                    {
                        System.out.println("Ungültige Auswahl.");
                        break;
                    }
                }
            } catch (RoutineException e) {
                System.out.println("⚠ Fehler: " + e.getMessage());
            }
        }
    }

    /**
     * Hilfsmethode zur sicheren Ganzzahl-Eingabe.
     * Bei falscher Eingabe wird -1 zurückgegeben.
     *
     * @param scanner Scanner zur Eingabe
     * @return eingegebene Zahl oder -1 bei Fehler
     */
    private int readInt(Scanner scanner) {
        System.out.print("Deine Wahl: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}
