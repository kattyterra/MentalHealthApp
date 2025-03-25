package utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hilfsklasse zum Lesen aller Zeilen aus allen Textdateien in einem Ordner.
 */
public class DateiLeseHelfer {

    /**
     * Liest alle Zeilen aus allen `.txt`-Dateien eines Verzeichnisses und gibt sie als Liste zurück.
     *
     * @param ordnerPfad Pfad zum Verzeichnis
     * @return Liste aller Textzeilen aus allen Dateien (zeilenweise, ohne Gruppierung)
     */
    public List<String> leseAlleZeilen(String ordnerPfad) {
        List<String> alleZeilen = new ArrayList<>();
        File ordner = new File(ordnerPfad);

        File[] dateien = ordner.listFiles((dir, name) -> name.endsWith(".txt"));
        if (dateien != null) {
            Arrays.sort(dateien); // optional chronologisch sortieren
            for (File datei : dateien) {
                try {
                    alleZeilen.addAll(Files.readAllLines(datei.toPath()));
                } catch (IOException e) {
                    System.err.println("Fehler beim Lesen von " + datei.getName() + ": " + e.getMessage());
                }
            }
        }

        return alleZeilen;
    }

    /**
     * Liest den vollständigen Inhalt einer Textdatei und gibt ihn als zusammenhängenden Textblock zurück.
     *
     * @param pfad Der Pfad zur Datei, die gelesen werden soll.
     * @return Der gesamte Dateiinhalt als String. Falls die Datei nicht existiert, wird eine entsprechende Meldung zurückgegeben.
     *         Bei einem Fehler während des Lesens wird eine Fehlermeldung zurückgegeben.
     */
    public String leseTextblock(String pfad) {
        Path path = Paths.get(pfad);
        if (!Files.exists(path)) {
            return "Kein Eintrag für dieses Datum gefunden.";
        }
        try {
            return String.join(System.lineSeparator(), Files.readAllLines(path));
        } catch (IOException e) {
            return "Fehler beim Lesen: " + e.getMessage();
        }
    }
}
