package fortschrittsbericht_logik;

import java.io.*;
import java.util.*;

public class FortschrittsberichtService {
    public void monatsberichtAnzeigen() {
        String monat = java.time.LocalDate.now().getMonth().toString();
        System.out.println("\n📊 Persönlicher Monatsrückblick – " + monat);
        System.out.println("────────────────────────────────");

        zeigeStimmungsauswertung();
        zeigeTagebuchEintraege();
        zeigeRoutineStatistik();
        zeigeEmotionenStatistik();
        zeigeGedankenReflexionEintraege();
    }

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

    private void zeigeTagebuchEintraege() {
        File ordner = new File("Tagebuch");
        String[] files = ordner.list((d, n) -> n.endsWith(".txt"));
        int anzahl = (files != null) ? files.length : 0;
        System.out.println("• Einträge im Tagebuch: " + anzahl);
    }

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
                        if (zeilenzähler % 3 == 0) { // jede 3. Zeile = erledigt-Status
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

    private void zeigeGedankenReflexionEintraege() {
        File ordner = new File("Reflexionen");
        File[] files = ordner.listFiles((d, n) -> n.endsWith(".txt"));
        int anzahl = (files != null) ? files.length : 0;
        System.out.println("• Gedankenreflexionen (Einträge): " + anzahl);
    }
}
