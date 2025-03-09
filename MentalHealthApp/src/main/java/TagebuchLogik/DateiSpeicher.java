package TagebuchLogik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DateiSpeicher implements TagebuchRepository{
    private final String ordner = "Tagebuch/";

    public DateiSpeicher() {
        File dir = new File(ordner);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void speichern(TagebuchEintrag eintrag) {
        String pfad = ordner + eintrag.getDatum() + ".txt";
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
        String string_pfad = ordner + datum + ".txt";
        Path path = Paths.get(string_pfad);
        if (!Files.exists(path)) {
            System.err.println("Fehler: Datei existiert nicht - " + string_pfad);
            return;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            String suchMuster = "Eingetragen um " + uhrzeit + ":";
            List<String> updatedLines = new ArrayList<>();
            boolean loescheZeilen = false;
            boolean eintragGefunden = false;

            for (String line : lines) {
                if (line.startsWith("Eingetragen um ")) {
                    loescheZeilen = line.startsWith(suchMuster);
                    if (loescheZeilen) {
                        eintragGefunden = true;
                        continue;
                    }
                }
                if (!loescheZeilen) {
                    updatedLines.add(line);
                }
            }

            if (!eintragGefunden) {
                System.err.println("Kein Eintrag mit Uhrzeit " + uhrzeit + " gefunden.");
                return;
            }

            if (updatedLines.isEmpty()) {
                Files.delete(path);
                System.out.println("Datei gelöscht, da keine Einträge mehr vorhanden sind.");
            } else {
                Files.write(path, updatedLines);
                System.out.println("Eintrag mit Uhrzeit " + uhrzeit + " gelöscht.");
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Löschen des Eintrags: " + e.getMessage());
        }
    }
}
