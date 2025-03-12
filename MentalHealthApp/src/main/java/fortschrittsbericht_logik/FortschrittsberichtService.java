package fortschrittsbericht_logik;

import zielverwaltung_logik.*;

import java.io.*;
import java.util.*;

/**
 * Diese Klasse erzeugt einen persönlichen Fortschrittsbericht (Monatsrückblick)
 * auf Grundlage der gespeicherten Daten in den Modulen Stimmung, Tagebuch,
 * Routinen, Emotionen und Gedankenreflexion.
 */
public class FortschrittsberichtService {

    /**
     * Hauptmethode zur Anzeige des Monatsberichts mit allen Teilstatistiken.
     * Ruft die einzelnen Auswertungsmethoden auf.
     */
    public void monatsberichtAnzeigen() {
        String monat = java.time.LocalDate.now().getMonth().toString();
        System.out.println("\n📊 Persönlicher Monatsrückblick – " + monat);
        System.out.println("────────────────────────────────");

        zeigeStimmungsauswertung();
        zeigeTagebuchEintraege();
        zeigeRoutineStatistik();
        zeigeEmotionenStatistik();
        zeigeGedankenReflexionEintraege();
        zeigeZielauswertung();
    }

    /**
     * Ermittelt den Durchschnitt aller Stimmungseinträge.
     * Grundlage sind alle Textdateien im Verzeichnis "Stimmungskalender".
     */
    private void zeigeStimmungsauswertung() {
        File ordner = new File("Stimmungskalender");
        File[] dateien = ordner.listFiles((d, name) -> name.endsWith(".txt"));
        int summe = 0, count = 0;

        if (dateien != null) {
            for (File f : dateien) {
                try (BufferedReader r = new BufferedReader(new FileReader(f))) {
                    String z;
                    while ((z = r.readLine()) != null) {
                        if (z.contains("Stimmung:")) {
                            int s = Integer.parseInt(z.split("Stimmung:")[1].trim());
                            summe += s;
                            count++;
                        }
                    }
                } catch (Exception ignored) {}
            }
        }

        double avg = (count > 0) ? (double) summe / count : 0.0;
        System.out.printf("• Stimmung Ø: %.1f (%d Einträge)\n", avg, count);
    }

    /**
     * Zählt die Anzahl vorhandener Tagebuchdateien.
     * Grundlage sind alle Textdateien im Verzeichnis "Tagebuch".
     */
    private void zeigeTagebuchEintraege() {
        File ordner = new File("Tagebuch");
        String[] files = ordner.list((d, n) -> n.endsWith(".txt"));
        int anzahl = (files != null) ? files.length : 0;
        System.out.println("• Einträge im Tagebuch: " + anzahl);
    }

    /**
     * Analysiert die Routineerfüllung über alle Routinen-Tagesdateien hinweg.
     * Grundlage ist jede 3. Zeile der Tagesdateien, welche den Erledigungsstatus enthält.
     */
    private void zeigeRoutineStatistik() {
        File ordner = new File("Routinen");
        File[] files = ordner.listFiles((d, n) -> n.endsWith(".txt") && !n.equals("stammliste.txt"));
        int gesamt = 0, erledigt = 0;

        if (files != null) {
            for (File f : files) {
                try (BufferedReader r = new BufferedReader(new FileReader(f))) {
                    String zeile;
                    int zeilenzähler = 0;
                    while ((zeile = r.readLine()) != null) {
                        zeilenzähler++;
                        // Jede 3. Zeile in Routine-Dateien = Status true/false
                        if (zeilenzähler % 3 == 0) {
                            if (Boolean.parseBoolean(zeile.trim())) erledigt++;
                            gesamt++;
                        }
                    }
                } catch (IOException ignored) {}
            }
        }

        double prozent = (gesamt > 0) ? (100.0 * erledigt / gesamt) : 0;
        System.out.printf("• Routinen erfüllt: %.1f%% (%d von %d)\n", prozent, erledigt, gesamt);
    }

    /**
     * Analysiert alle Emotionseinträge aus den Stimmungskalender-Dateien.
     * Pro Emotion werden Ø-Intensität und Häufigkeit berechnet.
     */
    private void zeigeEmotionenStatistik() {
        File ordner = new File("Stimmungskalender");
        File[] dateien = ordner.listFiles((d, name) -> name.endsWith(".txt"));
        Map<String, List<Integer>> emotionMap = new HashMap<>();

        if (dateien != null) {
            for (File f : dateien) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String zeile;
                    while ((zeile = reader.readLine()) != null) {
                        if (zeile.startsWith("- Emotion:")) {
                            // Zeilenformat: "- Emotion: Name | Intensität: Zahl"
                            String[] teile = zeile.split("\\|");
                            if (teile.length >= 2) {
                                String name = teile[0].split(":")[1].trim();
                                String intensitaetStr = teile[1].replace("Intensität:", "").trim();
                                try {
                                    int intensitaet = Integer.parseInt(intensitaetStr);
                                    emotionMap.computeIfAbsent(name, k -> new ArrayList<>()).add(intensitaet);
                                } catch (NumberFormatException ignored) {}
                            }
                        }
                    }
                } catch (IOException ignored) {}
            }
        }

        System.out.println("• Emotionen-Statistik:");
        for (Map.Entry<String, List<Integer>> entry : emotionMap.entrySet()) {
            double avg = entry.getValue().stream().mapToInt(i -> i).average().orElse(0);
            System.out.printf("  - %s (Ø Intensität: %.1f | Vorkommen: %d)\n", entry.getKey(), avg, entry.getValue().size());
        }
    }

    /**
     * Zählt die Anzahl an gespeicherten Gedankenreflexionseinträgen
     * (aus dem Modul „Gedankenkarussell stoppen“).
     */
    private void zeigeGedankenReflexionEintraege() {
        File ordner = new File("Reflexionen");
        File[] files = ordner.listFiles((d, n) -> n.endsWith(".txt"));
        int anzahl = (files != null) ? files.length : 0;
        System.out.println("• Gedankenreflexionen (Einträge): " + anzahl);
    }

    private void zeigeZielauswertung() {
        ZielRepository zielRepo = new DateibasierterZielRepository();
        List<Ziel> ziele = zielRepo.laden();
        long erledigt = ziele.stream().filter(Ziel::isErledigt).count();

        System.out.println("• Ziele insgesamt: " + ziele.size());
        System.out.println("• Davon erledigt: " + erledigt);
        if (ziele.size() > 0) {
            double prozent = (100.0 * erledigt / ziele.size());
            System.out.printf("• Zielerreichungsquote: %.1f%%\n", prozent);
        }

        System.out.println("• Offene Ziele als Motivation:");
        ziele.stream().filter(z -> !z.isErledigt()).forEach(z -> System.out.println("  - " + z.getBeschreibung()));
    }
}
