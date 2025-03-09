package TagebuchLogik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dateioperationen {
    private final String ordner = "Tagebuch/";

    public void speichern(String datum, String text) {
        String pfad = ordner + datum + ".txt";
        File file = new File(pfad);
        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.exists() && file.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(text);
            System.out.println("Text gespeichert: " + pfad);
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben: " + e.getMessage());
        }
    }

    public void loeschen(String datum) {
        String pfad = ordner + datum + ".txt";
        try {
            Files.delete(Paths.get(pfad));
            System.out.println("Datei gelöscht: " + pfad);
        } catch (IOException e) {
            System.err.println("Fehler beim Löschen: " + e.getMessage());
        }
    }
}
