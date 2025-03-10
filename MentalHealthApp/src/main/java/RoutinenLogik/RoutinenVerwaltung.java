package RoutinenLogik;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class RoutinenVerwaltung {
    private final RoutineManager routineManager;

    public RoutinenVerwaltung(RoutineManager routineManager) {
        this.routineManager = routineManager;
    }

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



    public void checklisteVerwalten(Scanner scanner) {
        List<Routine> checklist = routineManager.getRoutinen();
        if (checklist.isEmpty()) {
            System.out.println("\nKeine Routinen vorhanden.");
            return;
        }

        checklist.sort(Comparator.comparingInt(r -> getSortIndex(r.getRoutinenArt())));

        boolean back = false;
        while (!back) {
            System.out.println("\nRoutinen ansehen/abhacken:");
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

    private void zeigeRoutineAuswahl(List<Routine> routinen) {
        System.out.println("\nHier sind alle deine Routinen:");
        for (int i = 0; i < routinen.size(); i++) {
            System.out.println((i + 1) + ". " + routinen.get(i));
        }
    }

    private boolean istGueltigerIndex(int index, List<Routine> list) {
        return index >= 0 && index < list.size();
    }

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