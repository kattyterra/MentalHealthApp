package inspirations_logik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Diese Klasse implementiert das {@link InspirationsRepository} und
 * lädt Inspirationssätze aus einer Textdatei.
 * Jeder Satz steht dabei in einer eigenen Zeile in der Datei „Inspo.txt“.
 */
public class InspirationsSpeicher implements InspirationsRepository {

    private final String dateipfad;

    public InspirationsSpeicher(String dateipfad) {
        this.dateipfad = dateipfad;
    }

    @Override
    public List<String> ladeSaetze() {
        try {
            return Files.readAllLines(Paths.get(dateipfad));
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Inspirationssätze: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
