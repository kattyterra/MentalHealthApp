package TagebuchLogik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DateiSpeicher implements TagebuchRepository {
    private final String ordner = "Tagebuch/";

    public DateiSpeicher() {
        File dir = new File(ordner);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("Ordner '" + ordner + "' konnte nicht erstellt werden!");
            }
        }
    }

    @Override
    public void speichern(TagebuchEintrag eintrag) {
        String pfad = ordner + eintrag.datum() + ".txt";
        File file = new File(pfad);
        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.exists() && file.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(eintrag.formatForFile());
            writer.write(System.lineSeparator());
            System.out.println("Eintrag gespeichert: " + pfad);
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    @Override
    public void loeschen(String datum) {
        String pfad = ordner + datum + ".txt";
        try {
            Files.delete(Paths.get(pfad));
            System.out.println("Datei gelöscht: " + pfad);
        } catch (IOException e) {
            System.err.println("Fehler beim Löschen: " + e.getMessage());
        }
    }

    @Override
    public void loeschenEintrag(String datum, String uhrzeit) {
        String pfad = ordner + datum + ".txt";
        Path path = Paths.get(pfad);
        if (!Files.exists(path)) return;

        try {
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = baueAktualisierteZeilen(lines, uhrzeit);

            if (updatedLines.isEmpty()) {
                Files.delete(path);
            } else {
                Files.write(path, updatedLines);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Löschen des Eintrags: " + e.getMessage());
        }
    }

    @Override
    public String lesen(String datum) {
        String pfad = ordner + datum + ".txt";
        Path path = Paths.get(pfad);
        if (!Files.exists(path)) {
            return "Kein Eintrag für dieses Datum gefunden.";
        }

        try {
            List<String> lines = Files.readAllLines(path);
            return String.join("\n", lines);
        } catch (IOException e) {
            return "Fehler beim Lesen des Eintrags: " + e.getMessage();
        }
    }

    @Override
    public List<String> getVerfuegbareEintraege() {
        File verzeichnis = new File(ordner);
        File[] dateien = verzeichnis.listFiles((dir, name) -> name.endsWith(".txt"));

        List<String> verfuegbareEintraege = new ArrayList<>();
        if (dateien != null) {
            for (File datei : dateien) {
                verfuegbareEintraege.add(datei.getName().replace(".txt", ""));
            }
        }
        return verfuegbareEintraege;
    }

    @Override
    public boolean bearbeiten(String datum, String uhrzeit, String neuerText) {
        String pfad = ordner + datum + ".txt";
        Path path = Paths.get(pfad);
        if (!Files.exists(path)) return false;

        try {
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = aktualisierteZeilenFuerBearbeiten(lines, uhrzeit, neuerText);

            Files.write(path, updatedLines);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private List<String> baueAktualisierteZeilen(List<String> lines, String uhrzeit) {
        List<String> updatedLines = new ArrayList<>();
        boolean loescheZeilen = false;

        for (String line : lines) {
            if (line.startsWith("Eingetragen um " + uhrzeit + ":")) {
                loescheZeilen = true;
                continue;
            }
            if (loescheZeilen && !line.startsWith("Eingetragen um ")) {
                continue;
            }
            loescheZeilen = false;
            updatedLines.add(line);
        }
        return updatedLines;
    }

    private List<String> aktualisierteZeilenFuerBearbeiten(
            List<String> lines,
            String uhrzeit,
            String neuerText) {
        List<String> updatedLines = new ArrayList<>();
        boolean bearbeiten = false;

        for (String line : lines) {
            if (line.startsWith("Eingetragen um " + uhrzeit + ":")) {
                bearbeiten = true;
                updatedLines.add(line); // Uhrzeit bleibt erhalten
                continue;
            }
            if (bearbeiten && !line.startsWith("Eingetragen um ")) {
                updatedLines.add(neuerText); // Neuen Text speichern
                bearbeiten = false;
                continue;
            }
            updatedLines.add(line);
        }

        return updatedLines;
    }
}
