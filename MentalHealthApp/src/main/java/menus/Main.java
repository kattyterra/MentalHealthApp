package menus;

import fortschrittsbericht_logik.FortschrittsberichtService;
import routinen_logik.RoutineException;
import uebungen.TextdateiUebungRepository;
import uebungen.*;
import gedanken_reflexion_logik.*;

import java.util.Scanner;

/**
 * Die Main-Klasse enthält das zentrale Hauptmenü der MentalHealthApp.
 * Von hier aus gelangt der Benutzer in alle Teilbereiche der Anwendung:
 * Tagebuch, Stimmungskalender, Routinen, Atemübungen, Achtsamkeit, Inspiration,
 * Gedankenreflexion sowie der Fortschrittsbericht.
 */
public class Main {

    /**
     * Leerer Konstruktor – notwendig für Instanziierung aus der App-Klasse.
     */
    public Main() {
    }

    /**
     * Zeigt das Hauptmenü und steuert die Navigation durch das gesamte Programm.
     * Alle Menüaufrufe sind textbasiert und modular strukturiert.
     */
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n🌟 Willkommen in deiner MentalHealthApp 🌟");
            System.out.println("Was möchtest du heute für dein Wohlbefinden tun?");
            System.out.println("────────────────────────────────────────────");
            System.out.println(" 1 - 📔  Tagebuch – Gedanken festhalten & reflektieren");
            System.out.println(" 2 - 😊  Stimmungskalender – Tagesstimmung & Emotionen tracken");
            System.out.println(" 3 - ✅  Routinen – Tagesstruktur & Selbstfürsorge planen");
            System.out.println(" 4 - 🫁  Atemübungen – zur Ruhe kommen & fokussieren");
            System.out.println(" 5 - 🧘  Achtsamkeitsübungen – im Moment ankommen");
            System.out.println(" 6 - 💡  Inspirationsecke – kleine Impulse für deinen Tag");
            System.out.println(" 7 - 🧠  Gedankenkarussell stoppen – klare Gedanken finden");
            System.out.println(" 8 - 📊  Monatsrückblick – dein Fortschritt im Überblick");
            System.out.println(" 9 - ❌  Programm beenden");
            System.out.println("────────────────────────────────────────────");
            System.out.print("👉 Deine Wahl: ");


            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }

            switch (choice) {
                case 1: {
                    // Tagebuchmodul aufrufen
                    Tagebuch tagebuch = new Tagebuch();
                    tagebuch.showMenu(scanner);
                    break;
                }
                case 2: {
                    // Stimmungskalender aufrufen
                    Stimmungskalender stimmungskalender = new Stimmungskalender();
                    stimmungskalender.showMenu(scanner);
                    break;
                }
                case 3: {
                    // Routinenverwaltung mit Fehlerbehandlung aufrufen
                    try {
                        RoutinenMenu routine = new RoutinenMenu();
                        routine.showMenu(scanner);
                    } catch (RoutineException e) {
                        System.out.println("⚠ Fehler beim Öffnen der Routinenverwaltung: " + e.getMessage());
                    }
                    break;
                }
                case 4: {
                    // Atemübungen anzeigen
                    UebungService service = new UebungService(
                            new TextdateiUebungRepository("Textvorlagen(nicht_ändern!)/Atemuebungen.txt"));
                    new UebungMenu(service.getAlleUebungen(), "🫁 Deine Atemübungen: ").showMenu(scanner);
                    break;
                }
                case 5: {
                    // Achtsamkeitsübungen anzeigen
                    UebungService service = new UebungService(
                            new TextdateiUebungRepository("Textvorlagen(nicht_ändern!)/Achtsamkeitsuebungen.txt"));
                    new UebungMenu(service.getAlleUebungen(), "🧘 Deine Achtsamkeitsübungen: ").showMenu(scanner);
                    break;
                }
                case 6: {
                    // Inspirationssätze anzeigen
                    Inspirationssaetze inspirationssaetze = new Inspirationssaetze();
                    inspirationssaetze.showMenu(scanner);
                    break;
                }
                case 7: {
                    // Gedankenkarussell stoppen (Reflexion starten oder Einträge ansehen)
                    GedankenReflexionMenu gedankenReflexionMenu = new GedankenReflexionMenu();
                    gedankenReflexionMenu.showMenu(scanner);
                    break;
                }
                case 8: {
                    // Monatsrückblick anzeigen
                    FortschrittsberichtService fortschrittsberichtService = new FortschrittsberichtService();
                    fortschrittsberichtService.monatsberichtAnzeigen();
                    break;
                }
                case 9: {
                    // Programm beenden
                    System.out.println("Programm wird beendet...");
                    running = false;
                    break;
                }
                default: {
                    // Ungültige Eingabe
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
                    break;
                }
            }
        }

        scanner.close();
    }
}
