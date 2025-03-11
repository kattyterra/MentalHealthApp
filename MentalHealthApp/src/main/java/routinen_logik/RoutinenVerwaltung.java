package routinen_logik;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Diese Klasse verwaltet alle Benutzerinteraktionen rund um Routinen.
 * Sie bietet Funktionen zum Hinzufügen, Bearbeiten, Löschen und Abhaken von Routinen
 * sowie zur Anzeige als sortierte Checkliste (z. B. nach Tageszeit).
 */
public class RoutinenVerwaltung {
    private final RoutineManager routineManager;

    /**
     * Konstruktor: Verknüpft die Verwaltungslogik mit dem RoutineManager.
     *
     * @param routineManager zentrale Routinen-Datenverwaltung
     */
    public RoutinenVerwaltung(RoutineManager routineManager) {
        this.routineManager = routineManager;
    }

    /**
     * Benutzer kann eine neue Routine hinzufügen (inkl. Routinenart und Beschreibung).
     *
     * @param scanner Scanner-Objekt für Benutzereingabe
     */
    public void routineHinzufuegen(Scanner scanner) {
        System.out.println("\nWähle die Routinenart:");
        System.out.println("1 - Morgen");
        System.out.println("2 - Mittag");
        System.out.println("3 - Abend");
        System.out.println("4 - Nacht");

        int art = readInt(scanner, "Deine Wahl: ");

        String routinenArt;
        switch (art) {
            case 1 -> routinenArt = "Morgen";
            case 2 -> routinenArt = "Mittag";
            case 3 -> routinenArt = "Abend";
            case 4 -> routinenArt = "Nacht";
            default -> {
                System.out.println("\nUngültige Auswahl.");
                return;
            }
        }

        System.out.print("📝 Beschreibung der Routine: ");
        String beschreibung = scanner.nextLine();

        routineManager.routineHinzufuegen(new Routine(routinenArt, beschreibung));
        System.out.println("✅ Routine hinzugefügt.");
    }

    /**
     * Bearbeitet eine vorhandene Routine (neue Art und Beschreibung).
     *
     * @param scanner Scanner-Objekt für Benutzereingabe
     */
    public void routineBearbeiten(Scanner scanner) {
        List<Routine> routinen = routineManager.getRoutinen();
        if (routinen.isEmpty()) {
            System.out.println("Keine Routinen vorhanden.");
            return;
        }

        zeigeRoutineAuswahl(routinen);
        int index = readInt(scanner, "Welche Routine bearbeiten? (0 = Abbrechen): ") - 1;

        if (index == -1) {
            System.out.println("Bearbeiten abgebrochen.");
            return;
        }

        if (istGueltigerIndex(index, routinen)) {
            // Neue Routinenart wählen
            System.out.println("\nWähle neue Routinenart:");
            System.out.println("1 - Morgen");
            System.out.println("2 - Mittag");
            System.out.println("3 - Abend");
            System.out.println("4 - Nacht");

            int art = readInt(scanner, "Deine Wahl: ");
            String neueArt;
            switch (art) {
                case 1 -> neueArt = "Morgen";
                case 2 -> neueArt = "Mittag";
                case 3 -> neueArt = "Abend";
                case 4 -> neueArt = "Nacht";
                default -> {
                    System.out.println("\nUngültige Auswahl.");
                    return;
                }
            }

            System.out.print("Neue Beschreibung: ");
            String neueBeschreibung = scanner.nextLine();

            routineManager.routineBearbeiten(index, neueArt, neueBeschreibung);
            System.out.println("\n✅ Routine aktualisiert.");
        } else {
            System.out.println("\nUngültige Auswahl.");
        }
    }

    /**
     * Löscht eine Routine aus der Tagesliste.
     *
     * @param scanner Scanner-Objekt für Benutzereingabe
     */
    public void routineLoeschen(Scanner scanner) {
        List<Routine> routinen = routineManager.getRoutinen();
        if (routinen.isEmpty()) {
            System.out.println("\nKeine Routinen vorhanden.");
            return;
        }

        zeigeRoutineAuswahl(routinen);
        int index = readInt(scanner, "\nWelche Routine löschen? (0 = Abbrechen) ") - 1;

        if (index == -1) {
            System.out.println("Löschen abgebrochen.");
            return;
        }

        if (istGueltigerIndex(index, routinen)) {
            routineManager.routineLoeschen(index);
            System.out.println("\n✅ Routine gelöscht.");
        } else {
            System.out.println("\n❌ Ungültige Auswahl.");
        }
    }

    /**
     * Zeigt die Routinen als Tages-Checkliste an und ermöglicht das Abhaken/Umschalten.
     *
     * @param scanner Scanner-Objekt für Benutzereingabe
     */
    public void checklisteVerwalten(Scanner scanner) {
        List<Routine> checklist = routineManager.getRoutinen();
        if (checklist.isEmpty()) {
            System.out.println("\nKeine Routinen vorhanden.");
            return;
        }

        // Routinen nach Tageszeit sortieren
        checklist.sort(Comparator.comparingInt(r -> getSortIndex(r.getRoutinenArt())));

        boolean back = false;
        while (!back) {
            System.out.println("\nRoutinen ansehen/abhaken:");
            for (int i = 0; i < checklist.size(); i++) {
                Routine r = checklist.get(i);
                String status = r.isErledigt() ? "[✓]" : "[ ]";
                System.out.println((i + 1) + ". " + status + " [" + r.getRoutinenArt() + "] – " + r.getBeschreibung());
            }

            int eingabe = readInt(scanner, "Nummer zum Abhaken/Umschalten (0 = zurück): ");
            if (eingabe == 0) {
                back = true;
            } else if (istGueltigerIndex(eingabe - 1, checklist)) {
                Routine r = checklist.get(eingabe - 1);
                r.setErledigt(!r.isErledigt());
                routineManager.speichereTagesdatei();
            } else {
                System.out.println("\n❌ Ungültige Auswahl.");
            }
        }
    }

    // ===== Hilfsmethoden =====

    /**
     * Gibt die Liste aller Routinen nummeriert in der Konsole aus.
     *
     * @param routinen Liste der vorhandenen Routinen
     */
    private void zeigeRoutineAuswahl(List<Routine> routinen) {
        System.out.println("\nHier sind alle deine Routinen:");
        for (int i = 0; i < routinen.size(); i++) {
            System.out.println((i + 1) + ". " + routinen.get(i));
        }
    }

    /**
     * Prüft, ob der Index in der Liste gültig ist.
     */
    private boolean istGueltigerIndex(int index, List<Routine> list) {
        return index >= 0 && index < list.size();
    }

    /**
     * Liest eine Ganzzahl vom Benutzer ein, inkl. Eingabeprüfung.
     *
     * @param scanner Scanner-Objekt
     * @param prompt  Anzeige der Eingabeaufforderung
     * @return gültiger Integer-Wert
     */
    private int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nBitte gib eine gültige Zahl ein.");
            }
        }
    }

    /**
     * Liefert den Sortierindex für Routinenarten zur Anzeige in Tagesreihenfolge.
     * @param art Routinenart
     * @return Sortierindex (0 = Morgen, 1 = Mittag, 2 = Abend, 3 = Nacht)
     */
    private int getSortIndex(String art) {
        return switch (art.toLowerCase()) {
            case "morgen" -> 0;
            case "mittag" -> 1;
            case "abend" -> 2;
            case "nacht" -> 3;
            default -> 99;
        };
    }
}
