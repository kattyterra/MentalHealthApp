package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DateiSchreibHelfer {

    /**
     * Hängt beliebige Zeilen (einzeln oder mehrfach) an eine Datei an.
     *
     * @param ordner    Verzeichnis
     * @param dateiname Dateiname (inkl. ".txt")
     * @param inhalte   Iterable von Zeilen (z. B. List<String> oder List<Emotionseintrag>.map(...))
     */
    public void anhaengen(String ordner, String dateiname, Iterable<? extends CharSequence> inhalte) {
        String pfad = ordner + dateiname;
        try (FileWriter writer = new FileWriter(pfad, true)) {
            for (CharSequence zeile : inhalte) {
                writer.write(zeile.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in " + pfad + ": " + e.getMessage());
        }
    }

    /**
     * Hängt einen Text an das Ende einer Datei an.
     * Falls die Datei bereits existiert und nicht leer ist, wird vorher ein Zeilenumbruch eingefügt.
     *
     * @param pfad Der Pfad zur Datei, an die der Text angehängt werden soll.
     * @param text Der Text, der ans Ende der Datei geschrieben werden soll.
     */
    public void anhaengenMitLeerzeileWennNoetig(String pfad, String text) {
        File file = new File(pfad);
        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.exists() && file.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(text);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in " + pfad + ": " + e.getMessage());
        }
    }
}
