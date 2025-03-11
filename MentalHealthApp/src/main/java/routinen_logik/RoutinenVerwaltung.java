package routinen_logik;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RoutinenVerwaltung {
    private final RoutineRepository repository;
    private final RoutineVorschlagsService vorschlagsService;

    public RoutinenVerwaltung(RoutineRepository repository, RoutineVorschlagsService vorschlagsService) {
        this.repository = repository;
        this.vorschlagsService = vorschlagsService;
    }

    public void routineHinzufuegen(Scanner scanner) throws RoutineException {
        RoutinenArt art = waehleRoutinenArt(scanner);
        if (art == null) return;

        System.out.println("\n1 - Eigene Beschreibung eingeben");
        System.out.println("2 - Vorschlag aus Liste wählen");
        int auswahl = readInt(scanner, "Deine Wahl: ");

        String beschreibung;
        if (auswahl == 2) {
            List<String> vorschlaege = vorschlagsService.getVorschlaegeZuArt(art);
            if (vorschlaege.isEmpty()) {
                System.out.println("⚠ Keine Vorschläge verfügbar. Bitte manuell eingeben.");
                System.out.print("📝 Beschreibung: ");
                beschreibung = scanner.nextLine();
            } else {
                System.out.println("\nVorschläge für " + art + ":");
                for (int i = 0; i < vorschlaege.size(); i++) {
                    System.out.println((i + 1) + ". " + vorschlaege.get(i));
                }
                int index = readInt(scanner, "Welchen Vorschlag möchtest du übernehmen? (0 = Abbrechen): ");
                if (index <= 0 || index > vorschlaege.size()) {
                    System.out.println("❌ Abbruch oder ungültige Auswahl.");
                    return;
                }
                beschreibung = vorschlaege.get(index - 1);
            }
        } else {
            System.out.print("📝 Beschreibung: ");
            beschreibung = scanner.nextLine();
        }

        repository.hinzufuegen(new Routine(art, beschreibung));
        System.out.println("✅ Routine hinzugefügt.");
    }

    private RoutinenArt waehleRoutinenArt(Scanner scanner) {
        System.out.println("\nWähle die Routinenart:");
        System.out.println("1 - Morgen");
        System.out.println("2 - Mittag");
        System.out.println("3 - Abend");
        System.out.println("4 - Nacht");

        int eingabe = readInt(scanner, "Deine Wahl: ");
        try {
            return RoutinenArt.fromInt(eingabe);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Ungültige Auswahl.");
            return null;
        }
    }

    private int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Ungültige Eingabe. Bitte eine Zahl eingeben.");
            }
        }
    }

    public void checklisteVerwalten(Scanner scanner) throws RoutineException {
        List<Routine> checklist = repository.getRoutinen();
        if (checklist.isEmpty()) {
            System.out.println("\nKeine Routinen vorhanden.");
            return;
        }

        // Routinen nach Tageszeit sortieren
        checklist.sort(Comparator.comparingInt(r -> r.getArt().ordinal()));

        boolean back = false;
        while (!back) {
            System.out.println("\nRoutinen ansehen/abhaken:");
            for (int i = 0; i < checklist.size(); i++) {
                Routine r = checklist.get(i);
                String status = r.isErledigt() ? "[✓]" : "[ ]";
                System.out.println((i + 1) + ". " + status + " [" + r.getArt() + "] – " + r.getBeschreibung());
            }

            int eingabe = readInt(scanner, "Nummer zum Abhaken/Umschalten (0 = zurück): ");
            if (eingabe == 0) {
                back = true;
            } else if (istGueltigerIndex(eingabe - 1, checklist)) {
                Routine r = checklist.get(eingabe - 1);
                r.setErledigt(!r.isErledigt());
                repository.speichern();  // Tagesdatei aktualisieren
            } else {
                System.out.println("❌ Ungültige Auswahl.");
            }
        }
    }

    private boolean istGueltigerIndex(int index, List<?> list) {
        return index >= 0 && index < list.size();
    }

    public void routineBearbeiten(Scanner scanner) throws RoutineException {
        List<Routine> routinen = repository.getRoutinen();
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

        if (!istGueltigerIndex(index, routinen)) {
            System.out.println("❌ Ungültige Auswahl.");
            return;
        }

        RoutinenArt neueArt = waehleRoutinenArt(scanner);
        if (neueArt == null) return;

        System.out.println("\n1 - Eigene Beschreibung eingeben");
        System.out.println("2 - Vorschlag aus Liste wählen");
        int auswahl = readInt(scanner, "Deine Wahl: ");

        String neueBeschreibung;
        if (auswahl == 2) {
            List<String> vorschlaege = vorschlagsService.getVorschlaegeZuArt(neueArt);
            if (vorschlaege.isEmpty()) {
                System.out.println("⚠ Keine Vorschläge verfügbar. Bitte manuell eingeben.");
                System.out.print("Neue Beschreibung: ");
                neueBeschreibung = scanner.nextLine();
            } else {
                for (int i = 0; i < vorschlaege.size(); i++) {
                    System.out.println((i + 1) + ". " + vorschlaege.get(i));
                }
                int vorschlagIndex = readInt(scanner, "Vorschlag auswählen (0 = Abbrechen): ");
                if (vorschlagIndex <= 0 || vorschlagIndex > vorschlaege.size()) {
                    System.out.println("❌ Abbruch oder ungültige Auswahl.");
                    return;
                }
                neueBeschreibung = vorschlaege.get(vorschlagIndex - 1);
            }
        } else {
            System.out.print("Neue Beschreibung: ");
            neueBeschreibung = scanner.nextLine();
        }

        repository.bearbeiten(index, neueArt, neueBeschreibung);
        System.out.println("✅ Routine aktualisiert.");
    }

    public void routineLoeschen(Scanner scanner) throws RoutineException {
        List<Routine> routinen = repository.getRoutinen();
        if (routinen.isEmpty()) {
            System.out.println("Keine Routinen vorhanden.");
            return;
        }

        zeigeRoutineAuswahl(routinen);
        int index = readInt(scanner, "Welche Routine löschen? (0 = Abbrechen): ") - 1;
        if (index == -1) {
            System.out.println("Löschen abgebrochen.");
            return;
        }

        if (!istGueltigerIndex(index, routinen)) {
            System.out.println("❌ Ungültige Auswahl.");
            return;
        }

        repository.loeschen(index);
        System.out.println("✅ Routine gelöscht.");
    }

    private void zeigeRoutineAuswahl(List<Routine> routinen) {
        System.out.println("\nHier sind alle deine Routinen:");
        for (int i = 0; i < routinen.size(); i++) {
            Routine r = routinen.get(i);
            String status = r.isErledigt() ? "[✓]" : "[ ]";
            System.out.println((i + 1) + ". " + status + " [" + r.getArt() + "] – " + r.getBeschreibung());
        }
    }

    public void routinenStatistikAnzeigen() throws RoutineException {
        File ordner = new File("Routinen");
        File[] dateien = ordner.listFiles((dir, name) -> name.endsWith(".txt") && !name.equals("stammliste.txt"));

        if (dateien == null || dateien.length == 0) {
            System.out.println("❌ Keine Routinen-Statistik verfügbar.");
            return;
        }

        // Aktuelle Routinen aus Repository holen → nur diese zählen
        List<Routine> aktuelleRoutinen = repository.getRoutinen();

        // Map<RoutinenArt, Map<Beschreibung, [erledigt, gesamt]>>
        Map<RoutinenArt, Map<String, int[]>> statistik = new TreeMap<>(Comparator.comparingInt(Enum::ordinal));

        Arrays.sort(dateien); // chronologisch

        for (File datei : dateien) {
            try (BufferedReader reader = new BufferedReader(new FileReader(datei))) {
                String artZeile;
                while ((artZeile = reader.readLine()) != null) {
                    String beschreibung = reader.readLine();
                    boolean erledigt = Boolean.parseBoolean(reader.readLine());

                    RoutinenArt art;
                    try {
                        art = RoutinenArt.fromText(artZeile.trim());
                    } catch (IllegalArgumentException e) {
                        continue;
                    }

                    // ✅ Nur zählen, wenn diese Routine aktuell noch existiert
                    boolean istAktuell = aktuelleRoutinen.stream()
                            .anyMatch(r -> r.getArt() == art && r.getBeschreibung().equals(beschreibung));
                    if (!istAktuell) continue;

                    statistik.putIfAbsent(art, new LinkedHashMap<>());
                    Map<String, int[]> artMap = statistik.get(art);

                    artMap.putIfAbsent(beschreibung, new int[]{0, 0});
                    artMap.get(beschreibung)[1]++;
                    if (erledigt) artMap.get(beschreibung)[0]++;
                }
            } catch (IOException e) {
                System.out.println("⚠ Fehler beim Lesen von " + datei.getName());
            }
        }

        // 📊 Ausgabe nach Tageszeit
        System.out.println("\n📊 Routinen-Erfolgsstatistik:");
        for (RoutinenArt art : statistik.keySet()) {
            System.out.println("\n⏰ " + art.getAnzeigeName() + ":");
            Map<String, int[]> routines = statistik.get(art);
            for (Map.Entry<String, int[]> entry : routines.entrySet()) {
                int erledigt = entry.getValue()[0];
                int gesamt = entry.getValue()[1];
                String status = erledigt == gesamt ? "[✓]" : "[ ]";
                System.out.println("  " + status + " " + entry.getKey() + " – " + erledigt + " von " + gesamt + " Tagen erledigt");
            }
        }
    }

    public void routinenHistorieAnzeigen() {
        File ordner = new File("Routinen");
        File[] dateien = ordner.listFiles((dir, name) -> name.endsWith(".txt") && !name.equals("stammliste.txt"));

        if (dateien == null || dateien.length == 0) {
            System.out.println("❌ Keine Routinen-Historie verfügbar.");
            return;
        }

        Arrays.sort(dateien); // chronologisch sortieren

        for (File datei : dateien) {
            System.out.println("\n📅 Rückblick – Routinen am " + datei.getName().replace(".txt", "") + ":");

            try (BufferedReader reader = new BufferedReader(new FileReader(datei))) {
                String art;
                while ((art = reader.readLine()) != null) {
                    String beschreibung = reader.readLine();
                    boolean erledigt = Boolean.parseBoolean(reader.readLine());

                    String status = erledigt ? "[✓]" : "[ ]";
                    System.out.println(status + " " + art + " – " + beschreibung);
                }
            } catch (IOException e) {
                System.out.println("⚠ Fehler beim Lesen der Datei: " + datei.getName());
            }
        }
    }
}

