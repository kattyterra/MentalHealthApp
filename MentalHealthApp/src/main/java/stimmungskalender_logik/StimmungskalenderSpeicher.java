package stimmungskalender_logik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

/**
 * Diese Klasse implementiert das StimmungskalenderRepository.
 * Sie übernimmt die Datei-basierte Speicherung und das Einlesen
 * von Stimmungseinträgen und Emotionen – jeweils in eine Tagesdatei.
 */
public class StimmungskalenderSpeicher implements StimmungskalenderRepository {
    private final String ordner = "Stimmungskalender/";

    /**
     * Konstruktor: Erstellt den Ordner „Stimmungskalender“, falls er noch nicht existiert.
     */
    public StimmungskalenderSpeicher() {
        File dir = new File(ordner);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("Ordner '" + ordner + "' konnte nicht erstellt werden!");
            }
        }
    }

    /**
     * Speichert einen einzelnen Stimmungseintrag in der heutigen Tagesdatei.
     * Falls die Datei bereits existiert, wird der Eintrag angehängt.
     * @param eintrag Der zu speichernde Stimmungseintrag
     */
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

    /**
     * Speichert eine Liste von Emotionseinträgen ebenfalls in der heutigen Tagesdatei.
     * Wird typischerweise direkt nach dem Stimmungseintrag gespeichert.
     * @param emotionen Liste von Emotionseinträgen
     */
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

    /**
     * Liest alle Einträge aus allen Tagesdateien im Ordner „Stimmungskalender“ ein.
     * Rückgabe erfolgt als Liste aller Zeilen über alle Dateien hinweg.
     * @return Liste aller Einträge (Stimmungen + Emotionen) als Textzeilen
     */
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

            // Aufsplitten in Liste pro Zeile
            return List.of(alleEintraege.toString().split(System.lineSeparator()));

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen: " + e.getMessage());
            return List.of();
        }
    }
}
