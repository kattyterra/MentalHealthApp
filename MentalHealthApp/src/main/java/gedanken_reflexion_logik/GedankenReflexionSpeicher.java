package gedanken_reflexion_logik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Konkrete Implementierung des {@link GedankenReflexionRepository}.
 *
 * Diese Klasse verwaltet das Speichern und Auslesen von Gedankenreflexionseinträgen
 * im Dateisystem. Jeder Eintrag wird in einer Textdatei abgelegt,
 * gruppiert nach dem jeweiligen Tagesdatum.
 */
public class GedankenReflexionSpeicher implements GedankenReflexionRepository {

    /** Ordnerpfad für alle Reflexionsdateien */
    private final String ordner = "Reflexionen/";

    /**
     * Konstruktor – prüft, ob der Speicherordner vorhanden ist,
     * und erstellt ihn bei Bedarf.
     */
    public GedankenReflexionSpeicher() {
        File dir = new File(ordner);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("Ordner '" + ordner + "' konnte nicht erstellt werden!");
            }
        }
    }

    /**
     * Speichert einen neuen Gedankenreflexionseintrag in einer Tagesdatei.
     * Falls bereits eine Datei für das heutige Datum existiert,
     * wird der Eintrag dort angehängt.
     *
     * @param eintrag Der zu speichernde GedankenReflexionEintrag
     */
    @Override
    public void speichern(GedankenReflexionEintrag eintrag) {
        String pfad = ordner + LocalDate.now() + ".txt";
        try (FileWriter w = new FileWriter(pfad, true)) {
            w.write(eintrag.formatForFile());
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    /**
     * Liest alle vorhandenen Gedankenreflexionseinträge aus dem Speicherordner.
     * Die Einträge werden nach Datum sortiert zurückgegeben und können direkt
     * für die Anzeige genutzt werden.
     *
     * @return Liste aller gespeicherten Einträge als Textzeilen
     */
    @Override
    public List<String> lesenAlle() {
        List<String> zeilen = new ArrayList<>();
        File[] dateien = new File(ordner).listFiles((d, name) -> name.endsWith(".txt"));

        if (dateien != null) {
            Arrays.sort(dateien);
            for (File f : dateien) {
                try {
                    zeilen.add("\n📅 " + f.getName().replace(".txt", "") + ":\n");
                    zeilen.addAll(java.nio.file.Files.readAllLines(f.toPath()));
                } catch (IOException e) {
                    zeilen.add("Fehler beim Lesen: " + f.getName());
                }
            }
        }

        return zeilen;
    }
}
