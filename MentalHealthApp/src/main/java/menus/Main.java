package menus;

import fortschrittsbericht_logik.FortschrittsberichtService;
import routinen_logik.RoutineException;
import uebungen.TextdateiUebungRepository;
import uebungen.*;

import java.util.Scanner;

/**
 * Die Main-Klasse enthält das zentrale Hauptmenü der Anwendung.
 * Von hier aus gelangt der Benutzer in alle Teilbereiche der App:
 * Tagebuch, Stimmungskalender, Routinen, Atemübungen, Achtsamkeit und Inspiration.
 */
public class Main {

    /**
     * Leerer Konstruktor – notwendig für Instanziierung aus der App-Klasse.
     */
    public Main() {
    }

    /**
     * Zeigt das Hauptmenü und steuert die Navigation durch das gesamte Programm.
     */
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Dein Tagebuch");
            System.out.println("2 - Dein Stimmungskalender");
            System.out.println("3 - Deine Routinen");
            System.out.println("4 - Atemübungen");
            System.out.println("5 - Achtsamkeitsübungen");
            System.out.println("6 - Deine kleine Inspirationsecke");
            System.out.println("7 - Gedankenkarussell stoppen (Selbstreflexion)");
            System.out.println("8 - Persönlicher Fortschrittsbericht / Monatsrückblick anzeigen");
            System.out.println("9 - Programm beenden");
            System.out.print("Deine Wahl: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }

            switch (choice) {
                case 1:
                {
                    Tagebuch tagebuch = new Tagebuch();
                    tagebuch.showMenu(scanner);
                    break;
                }
                case 2:
                {
                    Stimmungskalender stimmungskalender = new Stimmungskalender();
                    stimmungskalender.showMenu(scanner);
                    break;
                }
                case 3:
                    {
                    try {
                        RoutinenMenu routine = new RoutinenMenu();
                        routine.showMenu(scanner);
                    } catch (RoutineException e) {
                        System.out.println("⚠ Fehler beim Öffnen der Routinenverwaltung: " + e.getMessage());
                    };
                        break;
                }
                case 4:
                {
                    // Atemübungen laden und anzeigen
                    UebungService service = new UebungService(new TextdateiUebungRepository("Textvorlagen(nicht_ändern!)/Atemuebungen.txt"));
                    new UebungMenu(service.getAlleUebungen(), "🫁 Deine Atemübungen: ").showMenu(scanner);
                    break;
                }
                case 5:
                {
                    // Achtsamkeitsübungen laden und anzeigen
                    UebungService service = new UebungService(new TextdateiUebungRepository("Textvorlagen(nicht_ändern!)/Achtsamkeitsuebungen.txt"));
                    new UebungMenu(service.getAlleUebungen(), "🧘 Deine Achtsamkeitsübungen: ").showMenu(scanner);
                    break;
                }
                case 6:
                {
                    // Inspirationssätze anzeigen
                    Inspirationssaetze inspirationssaetze = new Inspirationssaetze();
                    inspirationssaetze.showMenu(scanner);
                    break;
                }
                case 7:
                {
                    GedankenReflexionMenu gedankenReflexionMenu = new GedankenReflexionMenu();
                    gedankenReflexionMenu.showMenu(scanner);
                    break;
                }
                case 8:
                {
                    FortschrittsberichtService fortschrittsberichtService = new FortschrittsberichtService();
                    fortschrittsberichtService.monatsberichtAnzeigen();
                    break;
                }
                case 9:
                {
                    System.out.println("Programm wird beendet...");
                    running = false;
                    break;
                }
                default:
                {
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
                    break;
                }
            }
        }

        scanner.close();
    }
}
