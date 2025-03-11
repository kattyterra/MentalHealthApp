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

    /**
     * Lädt alle Inspirationssätze aus der Datei „Textvorlagen(nicht_ändern!)/Inspo.txt“.
     *
     * @return Liste der geladenen Sätze, oder eine leere Liste bei Fehler
     */
    @Override
    public List<String> ladeSaetze() {
        try {
            String pfad = "Textvorlagen(nicht_ändern!)/Inspo.txt";
            return Files.readAllLines(Paths.get(pfad));
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Inspirationssätze: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
