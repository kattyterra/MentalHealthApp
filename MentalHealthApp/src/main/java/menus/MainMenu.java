package menus;

import fortschrittsbericht_logik.FortschrittsberichtService;
import routinen_logik.RoutineException;
import uebungen.TextdateiUebungRepository;
import uebungen.*;
import utility.AnswerParser;

import java.util.Scanner;

/**
 * Die Main-Klasse enthält das zentrale Hauptmenü der MentalHealthApp.
 * Von hier aus gelangt der Benutzer in alle Teilbereiche der Anwendung:
 * Tagebuch, Stimmungskalender, Routinen, Atemübungen, Achtsamkeit, Inspiration,
 * Gedankenreflexion sowie der Fortschrittsbericht.
 */
public class MainMenu {

    /**
     * Leerer Konstruktor – notwendig für Instanziierung aus der App-Klasse.
     */
    public MainMenu() {
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
            System.out.println(" 9 - ✨ Ziele setzen & Fortschritte feiern");
            System.out.println(" 10 - ❌  Programm beenden");
            System.out.println("────────────────────────────────────────────");
            System.out.print("👉 Deine Wahl: ");

            AnswerParser answerParser = new AnswerParser();
            int choice = answerParser.parsen(scanner);
            if (choice == 99) continue;

            switch (choice) {
                case 1 -> starteTagebuch(scanner);
                case 2 -> starteStimmungskalender(scanner);
                case 3 -> starteRoutinen(scanner);
                case 4 -> zeigeUebungen("Textvorlagen(nicht_ändern!)/Atemuebungen.txt", "🫁 Deine Atemübungen: ", scanner);
                case 5 -> zeigeUebungen("Textvorlagen(nicht_ändern!)/Achtsamkeitsuebungen.txt", "🧘 Deine Achtsamkeitsübungen: ", scanner);
                case 6 -> starteInspiration(scanner);
                case 7 -> starteGedankenReflexion(scanner);
                case 8 -> zeigeMonatsrueckblick();
                case 9 -> starteZielMenu(scanner);
                case 10 -> {
                    System.out.println("Programm wird beendet...");
                    running = false;
                }
                default -> System.out.println("😅 Diese Eingabe kennt mein Menü nicht. Versuch’s nochmal!");
            }
        }

        scanner.close();
    }

    private void starteTagebuch(Scanner scanner) {
        new TagebuchMenu().showMenu(scanner);
    }

    private void starteStimmungskalender(Scanner scanner) {
        new StimmungskalenderMenu().showMenu(scanner);
    }

    private void starteRoutinen(Scanner scanner) {
        try {
            new RoutinenMenu().showMenu(scanner);
        } catch (RoutineException e) {
            System.out.println("⚠ Fehler beim Öffnen der Routinenverwaltung: " + e.getMessage());
        }
    }

    private void zeigeUebungen(String dateipfad, String titel, Scanner scanner) {
        UebungsVerwaltung service = new UebungsVerwaltung(new TextdateiUebungRepository(dateipfad));
        new UebungMenu(service.getAlleUebungen(), titel).showMenu(scanner);
    }

    private void starteInspiration(Scanner scanner) {
        new InspirationssaetzeMenu().showMenu(scanner);
    }

    private void starteGedankenReflexion(Scanner scanner) {
        new GedankenReflexionMenu().showMenu(scanner);
    }

    private void zeigeMonatsrueckblick() {
        new FortschrittsberichtService().monatsberichtAnzeigen();
    }

    private void starteZielMenu(Scanner scanner) {
        new ZielMenu().showMenu(scanner);
    }
}
