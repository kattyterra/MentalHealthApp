package StimmungskalenderLogik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public class StimmungskalenderSpeicher implements StimmungskalenderRepository {
    private final String ordner = "Stimmungskalender/";

    public StimmungskalenderSpeicher() {
        File dir = new File(ordner);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("Ordner '" + ordner + "' konnte nicht erstellt werden!");
            }
        }
    }

    @Override
    public void speichern(Stimmungseintrag eintrag) {
        String dateiname = LocalDate.now().toString() + ".txt";
        String pfad = ordner + dateiname;

        try (FileWriter writer = new FileWriter(pfad, true)) {
            writer.write(eintrag.formatForFile() + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    @Override
    public void speichernEmotionen(List<Emotionseintrag> emotionen) {
        String dateiname = LocalDate.now().toString() + ".txt";
        String pfad = ordner + dateiname;

        try (FileWriter writer = new FileWriter(pfad, true)) {
            for (Emotionseintrag e : emotionen) {
                writer.write(e.formatForFile() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Emotionen: " + e.getMessage());
        }
    }

    @Override
    public List<String> lesenAlle() {
        try {
            File ordnerDateien = new File(ordner);
            StringBuilder alleEintraege = new StringBuilder();
            File[] dateien = ordnerDateien.listFiles((dir, name) -> name.endsWith(".txt"));
            if (dateien != null) {
                for (File datei : dateien) {
                    List<String> zeilen = Files.readAllLines(datei.toPath());
                    for (String zeile : zeilen) {
                        alleEintraege.append(zeile).append(System.lineSeparator());
                    }
                }
            }
            return List.of(alleEintraege.toString().split(System.lineSeparator()));
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen: " + e.getMessage());
            return List.of();
        }
    }
}
